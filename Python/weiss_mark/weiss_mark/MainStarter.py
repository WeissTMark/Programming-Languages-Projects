from weiss_mark.Stretch import Stretch
from weiss_mark.gondola import Gondola
from weiss_mark.station import Station

"""
1. Initial Show system	
Menu working			____✓
Shows system properly	____✓

2. Menu framework	
Prompts correct 		____✓
Invalid input working 	____✓

3. Add Car	
Able to add short car to one end		____✓
Car at station shows 					____✓
Car str overridden						____✓

4. One Short Car Update (one way)	
Car time starts and decreases properly					____✓
Car can move to next stretch at the right time			____✓
Car displays and moves forward correctly on stretch		____✓
Car enters next station at correct time and location	____✓
Next station handles time and release properly			____✓
Car reaches the end station properly					____✓
Display is correct and done with iterator**				____?

5. One Long Car Update  (one way)	
Repeat of prior tier with long car ignoring iterator 	____✓
Strategy pattern used properly**						____X
			
6. Set people and Adapting (one way)	
Updates station on/off count properly					____✓
Updates short/long car people count properly			____✓
Update adapting car properly with strategy**			____✓

7. Multi short/long car (one way) and station details	
Waiting list works properly at start					____✓
Waiting list works properly at later stations			____✓
Show station details working properly**					____✓	
Later added cars work									____✓

8. One short car, round trip	
Able to return to cable at end							____✓
Returns at correct speed to start 						____✓
Multiple cycles permitted								____✓
		
9. Stress test	
multiple, varied cars									____✓


Grading tags completes : All
"""


def main():
    menu = "\n" \
           "1) Show Gondola System\n" \
           "2) Add Car\n" \
           "3) Update with Debug Info\n" \
           "4) Set Station People\n" \
           "5) Show Station Details\n" \
           "0) Quit\n"

    choice = -1
    gone = Gondola()
    while choice != 0 and choice != '0':
        print(menu)
        choice = input("Choice:> ")

        # strips out blank lines in input
        while choice == '':
            choice = input()
        inp = catch(choice)

        if inp[1] == 1:
            showSys(gone)
        elif inp[1] == 2:
            # Add car
            addCar(gone)
        elif inp[1] == 3:
            # Update with Debug
            gone.stepTime()
            showSys(gone)
        elif inp[1] == 4:
            # Set station People
            setPeople(gone)
        elif inp[1] == 5:
            stationDeets(gone)
        # Station Deets
        elif inp[1] == 0:
            # Quit
            break
        else:
            if not inp[0]:
                print("Invalid menu option")
            if inp[0]:
                print("Invalid menu option")


def catch(inpu):
    try:
        temp = int(inpu)
    except ValueError as e:
        return False, e
    return True, temp


def showSys(gone):
    minutes = gone.time
    stations = gone.th.track
    if minutes == 0:
        print("Time: 0 min")
    else:
        print("Time: " + str(minutes) + " min")
    for v in stations:
        if type(v) == Station:
            car = v.currCar
            carl = v.leftCar
            print("--------" + '{0: <10}'.format(v.name), end="")
            status = ""
            if carl:
                status += 'Left: ' + str(carl) + "(" + str(len(v.leftList)) + ")   "
            if car:
                status += 'Right: ' + str(car) + "(" + str(len(v.waitList)) + ")"
            print(status)
        elif type(v) == Stretch:
            car = v.currCar
            carl = v.leftCar
            if carl:
                print("    Traveling Left: " + str(carl))
            else:
                print("    Traveling Left: ")
            if car:
                print("    Traveling Right: " + str(car))
            else:
                print("    Traveling Right: ")


def addCar(gone):
    print("Which type: 0-->Short, 1-->Long, 2-->Adapting:>")
    choice = ''
    while choice == '':
        choice = input()
    inp = catch(choice)
    if inp[0] and 0 <= inp[1] < 3:
        gone.th.addCar(inp[1])
    else:
        print("Invalid Input")


def setPeople(gone):
    stations = gone.th.stations
    for s in stations:
        print(s.name)
        print("    Getting on:> ")
        on = input()
        on = catch(on)
        if not on[0] or on[1] < 0 or on[1] > 20:
            print("Invalid Input")
            break
        print("    Getting off:> ")
        off = input()
        off = catch(off)
        if not off[0] or off[1] < 0 or off[1] > 20:
            print("Invalid Input")
            break
        s.setPeopleMove(on[1], off[1])


def stationDeets(gone):
    for v in gone.th.stations:
        car = v.currCar
        carl = v.leftCar
        print("--------" + '{0: <10}'.format(v.name), end="")
        status = ""
        if carl:
            status += 'Left: ' + str(carl) + "(" + str(len(v.leftList)) + ")   "
        if car:
            status += 'Right: ' + str(car) + "(" + str(len(v.waitList)) + ")"
        print(status)
        print("    People getting on/off: " + str(v.peopleOn) + "/" + str(v.peopleOff))
        print("    Delayed on...")
        if len(v.waitList) == 0:
            print("      Left Side: ")
            print("      Right Side: ")
        else:
            lis = ""
            for item in v.waitList:
                lis += "[ ID:" + str(item.id).ljust(4) + "0.0 ] "
            print("      Left Side: " + lis)
            print("      Right Side: ")
