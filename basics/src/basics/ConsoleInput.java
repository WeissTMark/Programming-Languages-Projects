package basics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ConsoleInput {
    // main() - execution begins here
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in); //Console --> System.in

        //basics
        {
            System.out.print("What is your name? ");
            String name = in.nextLine();
            name = name.trim(); //trims leading and trailing spaces
            System.out.println("Hello " + name + "!");


            System.out.print("Enter a integer: ");
            String temp = in.next();
            int m = Integer.parseInt(temp); //how to convert from text to a number

            System.out.print("Enter a float: ");
            double n = in.nextDouble();

            // print results
            System.out.println("\nThe sum of " + m + " + " + n + " is " + (m + n));
        }

        //warnings\tips!!!!!!!!!!!!!!!!
        /*
            1) do NOT mix nextX() and nextLine()
                Same issue as mixing getline() and >> in C++
                 nextX() ignores the new line, nextLine() does not
            2) txt.trim(); is a handy way to remove leading and trailing spaces from text

         */

        //tokenize a string
        {
            //scanner works with strings too
            System.out.print("Input a line of text with three items (anything, int, float): ");
            in.skip("\n"); //same buffer issue as C++ when mixing lines and individual elements
            String line = in.nextLine();
            Scanner finerRead = new Scanner(line);
            System.out.println(finerRead.next() );
            int x;
            float y;

            x = finerRead.nextInt();
            y = finerRead.nextFloat();

            //can check if there is anything left with:
            //line.hasNext()

            System.out.println(x + y);
        }



        //old way--------------------------------------------------------------------------
        {
            //Java loves to build up classes
            BufferedReader in2 =
                    new BufferedReader(new InputStreamReader(System.in));

            try {
                System.out.println("Input a line of text: ");

                //no readInt() etc
                String line = in2.readLine();

                //break it the string
                StringTokenizer tokens = new StringTokenizer(line);

                //pull in order, only. Cast if needed
                while (tokens.hasMoreTokens()) {
                    System.out.println(tokens.nextToken());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
/*
Question:
What is the code necessary to read in the following text
"Hi 1 3.5"

 */






/*
Answer:

Scanner in = new Scanner(text);
String x = in.next();
int y = in.nextInt();
double z = in.nextDouble();
 */

