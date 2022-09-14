package weiss_mark;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class will test your output against the given file list. This class will be OVERWRITTEN on our end.
 */
public class RunTests {

    public static final boolean RUN_TESTS = true;
    public static final boolean CHECK_CAP = false;
    public static final boolean CHECK_LEFT_SPACING = false;
    public static final boolean CHECK_RIGHT_SPACING = false;
    public static final boolean CHECK_NEW_LINE = false;
    public static final int MAX_ERROR_LINES = 5;
    public static final boolean CLEAN_UP = false;
    public static final String INPUT_PATTERN = ":>";
    public static  final ColorText.Color DEFAULT_COLOR = ColorText.Color.WHITE;
    public static  final ColorText.Color PASSED_COLOR = ColorText.Color.GREEN;

    public static void main(String[] args) {

        //no argument try once to get it from the user
        if (RUN_TESTS) {
            try {
                // "tier1", "tier2", "tier3", "tier4", "tier5", "tier6", "tier7", "tier8"
                String[] tests = {"tier1", "tier2", "tier3", "tier4", "tier5", "tier6", "tier7", "tier8"};
                int err_count = 0;

                for (String base : tests) {

                    String inFile = base + ".txt";
                    String runOutFile = base + "-student.out";
                    String answerFile = base + "-solution.out";

                    String runOutFileParsed = base + "-studentInputAdded.out";
                    String answerFileParsed = base + "-InputAdded.out";

                    //redirect input and output
                    PrintStream output = new PrintStream(runOutFile);
                    System.setOut(output);
                    FileInputStream is = new FileInputStream(inFile);
                    System.setIn(is);

                    //run program
                    OceanStart.main(args);

                    //close files
                    output.close();
                    is.close();

                    //postProcess to add input
                    Scanner answer = new Scanner(Paths.get(answerFile));
                    Scanner input = new Scanner(Paths.get(inFile));
                    addInput(answer, input, answerFileParsed);

                    Scanner result = new Scanner(Paths.get(runOutFile));
                    input = new Scanner(Paths.get(inFile));
                    addInput(result, input, runOutFileParsed);

                    //check answers--------------------------------------------------------------------------------
                    System.err.println("\n\n\n\n\n\n" +
                            "-----------------------------" + base + "-----------------------------");
                    boolean error = checkErrors(answerFileParsed, runOutFileParsed);

                    //output pass, or the file with the problem lines
                    if (!error) {
                        System.err.println(ColorText.colorString("Passed",PASSED_COLOR));
                    } else {
                        printErrors(answerFileParsed, runOutFileParsed);
                    }

                    if (CLEAN_UP) {
                        Files.deleteIfExists(Paths.get(runOutFileParsed));
                        Files.deleteIfExists(Paths.get(answerFileParsed));
                    }
                }


            } catch (Exception e) {
                //Any error, use normal console input
                System.err.println("Error redirecting input. Continue with console input...");
                System.err.println("Error was: " + e.toString());
                e.printStackTrace();
            }
        } else {
            //run normally
            OceanStart.main(args); //TODO change this to be your entry class
        }

    }

    private static boolean checkErrors(String answerFileParsed, String runOutFileParsed) throws IOException {
       boolean error = false;
        Scanner answer = new Scanner(Paths.get(answerFileParsed));
        Scanner result = new Scanner(Paths.get(runOutFileParsed));

        //check for a mismatch pass
        while (answer.hasNext() && result.hasNext()) {
            String ansLine = answer.nextLine();
            String resultLine = result.nextLine();

            ansLine = cleanInput(answer, ansLine);
            resultLine = cleanInput(result, resultLine);

            if (!ansLine.equals(resultLine)) {
                error = true;
            }
        }

        if (answer.hasNext() || result.hasNext())
            error = true;

        return error;
    }

    private static void printErrors(String answerFileParsed, String runOutFileParsed) throws IOException {
        int err_count = 0;
        //check answers
        Scanner answer = new Scanner(Paths.get(answerFileParsed));
        Scanner result = new Scanner(Paths.get(runOutFileParsed));

        while (answer.hasNext() && result.hasNext()) {
            String ansLine = answer.nextLine();
            String resultLine = result.nextLine();

            ansLine = cleanInput(answer, ansLine);
            resultLine = cleanInput(result, resultLine);

            if (ansLine.equals(resultLine)) {
                if (err_count < MAX_ERROR_LINES)
                    System.err.println(ColorText.colorString(resultLine, DEFAULT_COLOR ));
            } else {
                if (err_count < MAX_ERROR_LINES)
                    printError(resultLine, ansLine);
                err_count += 1;
            }
        }

        //output remaining answer file
        while (answer.hasNext()) {
            String ansLine = answer.nextLine();
            if (err_count < MAX_ERROR_LINES)
                printError("", ansLine);
            err_count += 1;
        }

        //output remaining student result file
        while (result.hasNext()) {
            String resultLine = result.nextLine();
            if (err_count < MAX_ERROR_LINES)
                printError(resultLine, "");
            err_count += 1;
        }

        if (err_count >= MAX_ERROR_LINES)
            System.err.println(ColorText.colorString("More than " + MAX_ERROR_LINES + " lines of errors", ColorText.Color.CYAN, true, true));

    }


    public static void printError(String resultLine, String ansLine) {
        System.err.println(">>>>>>>Error>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.err.print("Got.....|");
        System.err.println(resultLine+"|");
        System.err.print("Needed..|");
        System.err.println(ansLine+"|");
    }

    public static void addInput(Scanner in, Scanner input, String fileOut) {
        ArrayList<String> lines = new ArrayList<>();

        while (in.hasNext()) {
            String line = in.nextLine();
            String[] tokens = line.split(INPUT_PATTERN);

            StringBuilder newLine = new StringBuilder(tokens[0]);
            for (int x = 1; x < tokens.length; x++) {
                String next = "";
                try{
                    //some spacing in input to readability, strip it
                    while(next.length()==0)
                        next = input.nextLine();
                }catch (Exception e){
                    //something went wrong, usually this mean too many prompts occured
                    next = "NULL";
                }
                newLine.append(INPUT_PATTERN + " ").append(ColorText.colorString(
                        next, ColorText.Color.BLUE, true));
                newLine.append("\n" + tokens[x]);
            }

            //somehow the space at the end was stripped.
            //Still need the input
            int index = line.lastIndexOf(INPUT_PATTERN);
            int ifHasPromptLoc = line.length()-INPUT_PATTERN.length();
            if( index == ifHasPromptLoc )
            {
                String next = "";
                try{
                    //some spacing in input to readability, strip it
                    while(next.length()==0)
                        next = input.nextLine();

                }catch (Exception e){
                    //something went wrong, usually this mean too many prompts occured
                    next = "NULL";
                }
                newLine.append(INPUT_PATTERN + " ").append(ColorText.colorString(
                        next, ColorText.Color.BLUE, true));
                newLine.append("\n");
            }

            lines.add(newLine.toString());
        }

        try {
            PrintWriter print = new PrintWriter(fileOut);
            for(int x = 0; x < lines.size(); x++)
            {
                String t = lines.get(x);
                print.println(t);
            }
            print.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static String cleanInput(Scanner in, String line) {
        if (!CHECK_NEW_LINE) {
            while (line.trim().length() == 0 && in.hasNext())
                line = in.nextLine();
        }

        if (!CHECK_CAP) {
            line = line.toLowerCase();
        }

        if (!CHECK_LEFT_SPACING) {
            line = line.stripLeading();
        }

        if (!CHECK_RIGHT_SPACING) {
            line = line.stripTrailing();
        }

        return line;
    }
}
