from weiss_mark import MainStarter
from ColorText import ColorText

import io
import sys
import os


DEBUG = True
MAX_ERROR_LINES = 5
CHECK_CAP = False
CHECK_LEFT_SPACING = True
CHECK_RIGHT_SPACING = False
CHECK_NEW_LINE = False
INPUT_PATTERN = ":>"
DEFAULT_COLOR = ColorText.fg.black
PASSED_COLOR = ColorText.fg.green
CLEAN_UP = False

def printError(resultLine, ansLine):
    """
    Prints a line that was marked as an error.

    Uses the right spacing and new line flags when printing

    :param resultLine: student line
    :param ansLine: solutions line
    """

    sys.stderr.write(">>>>>>>Error>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n")
    sys.stderr.write("Got.....|")
    sys.stderr.write(resultLine.rstrip() +"|")
    if (not CHECK_RIGHT_SPACING or not CHECK_NEW_LINE):
        sys.stderr.write("\n")
    sys.stderr.write("Needed..|")
    sys.stderr.write(ansLine.rstrip()+"|")
    if (not CHECK_RIGHT_SPACING or not CHECK_NEW_LINE):
        sys.stderr.write("\n")


def cleanInput(lines, i):
    """
    Cleans up the line given the flags currently in use.

    :param lines: the lines read in froma output file
    :param i: the index ofhte line to clean up
    :return: returns the new line and the index if newlines were to be skipped
    """
    if (not CHECK_NEW_LINE):
        while ( i < len(lines) and len(lines[i].strip()) == 0):
            i = i+1
    if(i == len(lines) ):
        return "", i

    line = lines[i]
    if (not CHECK_CAP):
        line = line.lower()

    if (not CHECK_LEFT_SPACING):
        line = line.lstrip()

    if (not CHECK_RIGHT_SPACING):
        line = line.rstrip()

    return line, i

def addInput(redirectedOut, redirectedIn, fileOutName) :
    """
    Reads the output from a run, and add the input
    from the redirected input file to the input marker
    given in the flag variable. The input will be in bold.

    :param redirectedOut: the name of a file that was produced with redirected output
    :param redirectedIn: the name of a file that gave input via redirection
    :param fileOutName: Where to save the results
    """
    inFileLines = open(redirectedIn).readlines()
    result = open(redirectedOut).readlines()

    i = 0
    j = 0

    lines = []
    while i < len(result):
        line = result[i]
        tokens = line.split(INPUT_PATTERN)
        newLine = tokens[0]
        for  x in range(1, len(tokens) ):
            next = ""
            try:
                #some spacing in input to readability, strip it
                while len(next)==0:
                    next = inFileLines[j].strip()
                    j += 1
            except:
                #something went wrong, usually this mean too many prompts occured
                next = "NULL"

            newLine += INPUT_PATTERN + " "+ ColorText.fg.blue + ColorText.bold + next + ColorText.reset
            newLine+= "\n"+tokens[x][1:len(tokens[x])]

        #somehow the space at the end was stripped.
        #Still need the input
        index = line.rfind(INPUT_PATTERN)
        ifHasPromptLoc = len(line)-len(INPUT_PATTERN)
        if index == ifHasPromptLoc and index != -1:
            next = ""
            try:
                #some spacing in input to readability, strip it
                while len(next)==0:
                    next = inFileLines[j].strip()
                    j += 1

            except:
                #something went wrong, usually this mean too many prompts occurred
                next = "NULL"

            newLine += INPUT_PATTERN + " " + ColorText.fg.blue + ColorText.bold + next + ColorText.reset
            newLine += "\n"

        lines.append(newLine)
        i += 1


    try:
        print = open(fileOutName, 'w')
        for x in range(0, len(lines)):

            t = lines[x]
            print.write(t)

        print.close()
    except:
        sys.stderr.write("Could not create output file with prompts added")

def checkErrors(answerFile, outFile):
    """
    Opens two files and checks for differences ignoring items noted in the flags.
    If there are any differences, it return True

    :param answerFile: The solution file
    :param outFile: The student file
    :return: True is there is a mismatch
    """
    answer = open(answerFile)
    result = open(outFile)
    error = False

    # check for a mismatch pass
    ansLines = answer.readlines()
    resultLines = result.readlines()

    # check if any lines are different
    i = 0
    j = 0
    while i < len(ansLines) and j < len(resultLines):
        ansLine, i = cleanInput(ansLines, i)
        resultLine, j = cleanInput(resultLines, j)

        i = i + 1
        j = j + 1

        if ansLine != resultLine:
            error = True
            break

    # if I didn't complete parsing, there is extra lines in one file
    if (i < len(ansLines) or j < len(resultLines)):
        error = True

    return error

def printErrors(answerFile, outFile):
    """
    Walkthrough two files and output matching lines in normal coloring,
    and outputs mistmatched lines in different coloring.
    The number of mismatch lined is maxed on the flag value.

    :param answerFile: The solution file
    :param outFile: The student file
    """
    answer = open(answerFile)
    result = open(outFile)

    # check for a mismatch pass
    ansLines = answer.readlines()
    resultLines = result.readlines()

    i = 0
    j = 0
    errorCnt = 0
    while i < len(ansLines) and j < len(resultLines):

        ansLine, i = cleanInput(ansLines, i)
        resultLine, j = cleanInput(resultLines, j)


        if ansLine == resultLine and i < len(ansLines) :
            # match
            sys.stderr.write(DEFAULT_COLOR+ ansLines[i] + ColorText.reset + "")
        else:
            #error
            if MAX_ERROR_LINES < errorCnt:
                sys.stderr.write(ColorText.fg.cyan +
                                 "Max number of error lines printed"
                                 + ColorText.reset + "")
                break
            #only print is error count is low enough
            if i < len(ansLines) and j < len(resultLines):
                printError(resultLines[j], ansLines[i])
                errorCnt += 1

        i = i + 1
        j = j + 1
    if MAX_ERROR_LINES > errorCnt:
        while j < len(resultLines):
            if MAX_ERROR_LINES < errorCnt:
                sys.stderr.write(ColorText.fg.cyan +
                                 "Max number of error lines printed"
                                 + ColorText.reset + "")
                break

            printError(resultLines[j], "")
            j = j + 1
            errorCnt += 1

        while i < len(ansLines):
            if MAX_ERROR_LINES < errorCnt:
                sys.stderr.write(ColorText.fg.cyan +
                                 "Max number of error lines printed"
                                 + ColorText.reset + "")
                break

            printError("", ansLines[i])
            i = i + 1
            errorCnt += 1

if(DEBUG):
    tests = ["../tier1","../tier2","../tier3","../tier4",
             "../tier5","../tier6","../tier7","../tier8" ,"../tier9"]


    for base in tests:
        file = base + ".txt"
        outFile = base + "-student.out"
        answerFile = base + "-solution.out"

        runOutFileParsed = base + "-studentInputAdded.out"
        answerFileParsed = base + "-solutionInputAdded.out"


        # redirect input and output
        output = open(outFile, "w")
        sys.stdout = output
        inf = open(file)
        input = "".join(inf.readlines())
        sys.stdin = io.StringIO(input)

        #Main_Mine.main()
        MainStarter.main()

        inf.close()
        output.close()

        # postProcess to add input
        addInput(answerFile, file, answerFileParsed)
        addInput(outFile, file, runOutFileParsed)


        sys.stderr.write(DEFAULT_COLOR + "\n\n\n\n\n\n" + ColorText.reset)
        sys.stderr.write(DEFAULT_COLOR +
                          "----------------------------" + base + "-----------------------------" + ColorText.reset + "\n")

        error = checkErrors(answerFileParsed, runOutFileParsed)


        # output pass, or the file with the problem lines
        if not error:
            sys.stderr.write(PASSED_COLOR + "Passed" + ColorText.reset + "")
        else:
            printErrors(answerFileParsed, runOutFileParsed)


        if CLEAN_UP:
            del inf
            del output
            if os.path.exists(runOutFileParsed):
                os.remove(runOutFileParsed)
            if os.path.exists(answerFileParsed):
                os.remove(answerFileParsed)
            if os.path.exists(outFile ):
                os.remove(outFile )

    sys.exit( )

else:
    #Main_Mine.main()
    MainStarter.main()
