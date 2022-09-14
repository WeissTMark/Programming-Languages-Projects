package basics;

import java.util.ArrayList;
import java.util.Scanner;

public class SelectAndLoop {

    public static void main(String[] args) {
        //ifs
        {
            Scanner in = new Scanner(System.in);
            System.out.print("Enter a int: ");
            int num = in.nextInt();
            if (num < 0) {
                System.out.println("Number is negative");
            } else if (num < 10) {
                System.out.println("Number is between 0 and 10");
            } else {
                System.out.println("Number is over 10");
            }
        }

        //switch
        {
            Scanner in = new Scanner(System.in);
            System.out.print("Enter a letter: ");
            //no nextChar option. Also could use
            //in.next().charAt(0);
            //char letter = (char)in.nextByte();
            byte letter = in.nextByte();

            //switch works with primitives, string, enums, and number wrappers
            switch (letter) {
                case 'A':
                    System.out.println("Got an A");
                    break;
                case 'B':
                    System.out.println("Got an B");
                    break;
                case 'C':
                    System.out.println("Got an C");
                    break;
                default:
                    System.out.println("Not an A, B, or C");
                    break;
            }
        }

        //basic for loop, and for each loop
        {
            int size = 10;
            //ArrayList --> c++ vector
            //only accepts reference objects inside of <>, no primitives
            //note size is it CAPACITY, not its # of elements
            // NOTE empty <>
            //start here-----------------------------------
            ArrayList<Integer> list = new ArrayList<>(size);

            //basic for loop identical to C++
            for (int i = 0; i < size; i++) {
                list.add((int) (Math.random() * 100)); //random --> [0,1)
            }


            //for each loop
            for (int i : list) {
                if (i < 50) {
                    System.out.print("Small: ");
                    System.out.println(i);
                } else {
                    System.out.print("Large: ");
                    System.out.println(i);
                }
            }

        }

        //basic while
        {
            int size = 10;
            String[] temp = new String[size];
            int x = 0;
            while (x < size) {
                int i = (int) (Math.random() * 5); //Java random

                //switch syntax the same, but more types supported
                switch (i) { //works with strings, java style enums
                    case 0:
                        temp[x] = "Zero";
                        break;
                    case 1:
                        temp[x] = "One";
                        break;
                    case 2:
                        temp[x] = "Two";
                        break;
                    case 3:
                        temp[x] = "Three";
                        break;
                    case 4:
                        temp[x] = "Four";
                        break;
                }
                x++;
            }

            x = 0;
            while (x < size) {
                System.out.println(temp[x]);
                x++;
            }
        }
    }




     //comments----------------------------------------

    /**
     * Java doc!
     * Typing /** and then enter above a function will autofill some of this
     * Example !!!
     * @param x stuff
     * @param b stuff 2!
     * @return zero
     */
    public int example(int x, String b) {
        //commenting
        //single line
        /*
        multi line
        stuff
        stuff
         */
        return 0;
    }
}

/*
Last tips:

1) to build documentation: ToolsGenerate JavaDoc
2) check for empty tags in functions: AnalyzeInspect code
   2a) then in the output window under JavaJavaDoc
    note: Inspect code outputs WAY to much
3) For REALLY nice style check, add the Checkstyle plugin (OPTIONAL)
      (FileSettingsPlugintype in Checkstyle in the search box)
 */
