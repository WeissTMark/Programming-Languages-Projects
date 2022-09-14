package weiss_mark;

import weiss_mark.visitor.*;

import java.util.Objects;
import java.util.Scanner;

//Visitor framework requirements checked	24 		Complete
//        Tier 1: menu working	12
//        Displays a 5 by 5 empty grid properly	   	Complete
//        Displays the rest properly 	   			Complete
//        Tier 2: set and unset at 0, 0	16
//        Can set empty 	    					Complete
//        Can set food 	    						Complete
//        Can set habitat 	    					Complete
//        Can set fish 	    						Complete
//        Tier 3: handles bad input at this point	15
//        Handles bad input at the menu level	  	Complete
//        Handles bad input at the object level	  	Complete
//        Handles bad input at location level	  	Complete
//        Handles bad input with 1 try-catch	  	Complete
//        Tier 4: set and unset at x, y	12
//        Can set empty at given location	    	Complete
//        Can set food at given location	        Complete
//        Can set habitat at given location	    	Complete
//        Can set fish at given location	    	Complete
//        Tier 5: default grid displays properly 	15  	Complete
//        Tier 6: count types* 20							Complete
//        Tier 7: color fish*	18                  Fails on blah blah blah
//        Colors any fish, any color	   			? Color function given in write up
//        Colors all fish, any color in one command	? Only requires color to chane fishes color based off # of
//        Colors all fish correctly   				? Fish and Habitats.
//        Tier 8: Feed Fish	18
//        Any fish\food changed	  					Complete
//        Fish lives\dies correctly	  				Complete
//        Any food removed next to a fish	  		Complete
//        Correct food removed for each fish	  	Complete
//        Uses a second visitor*					Complete
//
//
//        * These have their tags added				Complete
public class OceanStart {

    public static Scanner cin;

    public static void main(String[] args) {
        System.out.println();
        cin = new Scanner(System.in);
        String menu =
                "1) Set Area\n" +
                        "2) Make Default Grid\n" +
                        "3) Count Types\n" +
                        "4) Color Fish\n" +
                        "5) Feed Fish\n" +
                        "0) Quit\n";

        grid aquarium;
        aquarium = new grid();

        boolean loop = true;

        while (loop) {
            aquarium.printGrid();
            System.out.print(menu);
            System.out.print("\nChoice:> ");
            String input = cin.nextLine();
            while (Objects.equals(input, "")) {
                input = cin.nextLine();
            }
            int choice = getInput(input);
            switch (choice) {
                case 0:
                    loop = false;
                    break;
                case 1:
                    // Set Area
                    setArea(aquarium);
                    break;
                case 2:
                    aquarium.setDefault();
                    // Make Default Grid
                    break;
                case 3:
                    // Count Types
                    countTypes(aquarium);
                    break;
                case 4:
                    // Color Fish
                    colorFish(aquarium);
                    break;
                case 5:
                    // Feed Fish
                    feedFish(aquarium);
                    break;
                case -2:
                    System.out.print("Please input an integer");
                    break;
                default:
                    System.out.print("Unknown menu option");
                    break;
            }

        }
    }

    private static int getInput(String in) {
        try {
            return Integer.parseInt(in);
        } catch (NumberFormatException e) {
            return -2;
        }
    }

    private static void setArea(grid aquarium) {
        int choice = -2;
        System.out.print("Input area type 1) habitat 2) fish 3) food #) empty:> ");
        while (choice < -1) {
            String input = cin.nextLine();
            if (!Objects.equals(input, "#")) {
                choice = getInput(input);
                if (choice == -2) {
                    System.out.print("Please input an integer");
                    return;
                }
            } else {
                choice = 6;
            }
        }

        int x = -1;
        int y = -1;
        System.out.print("Input location (x y):> ");
        String input = cin.nextLine();
        String[] loc = input.split(" ");
        if (loc.length != 2 && getInput(loc[0]) != -2) {
            loc = new String[]{loc[0], cin.nextLine()};
        } else if (getInput(loc[0]) == -2) {
            System.out.print("Please input an integer");
            return;
        }
        if (loc.length == 2) {
            x = getInput(loc[1]);
            y = getInput(loc[0]);
            if (x == -2 || y == -2) {
                System.out.print("Please input an integer");
                return;
            }
        }


        if (x < 0 || y < 0) {
            System.out.print("Please input a number between 0 and 5");
            return;
        } else if (x >= 5 || y >= 5) {
            System.out.print("Please input a number between 0 and 5");
            return;
        }

        if (choice == 1) {
            aquarium.setSquarea(x, y, "habitat");
        } else if (choice == 2) {
            aquarium.setSquarea(x, y, "fish");
        } else if (choice == 3) {
            aquarium.setSquarea(x, y, "food");
        } else {
            aquarium.setSquarea(x, y, "");
        }
    }

    private static void countTypes(grid aquarium) {
        count cnt = new count();
        //GRADING: COUNT
        aquarium.accept(cnt);

        int[] counts = cnt.getCount();
        if (counts.length > 0) {
            System.out.print("Empty: " + counts[0]);
            System.out.print("\nHabitats: " + counts[1]);
            System.out.print("\nFood: " + counts[2]);
            System.out.print("\nFish: " + counts[3]);
        }
    }

    private static void feedFish(grid aquarium) {
        feed fis = new feed();
        aquarium.accept(fis);

    }

    private static void colorFish(grid aquarium) {
        count cnt = new count();
        aquarium.accept(cnt);
        //GRADING: COLOR
        color col = new color();
        col.setCounts(cnt.getCount());
        aquarium.accept(col);
    }

}
