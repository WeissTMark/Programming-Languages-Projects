
package Basic

import scala.io.StdIn

object IO extends App {

    val sampleOut = 4.5

    //basics of s"" string in Scala output
    {
        println("Awesome feature of Scala: it gets fancy with string creation")
        println("prefacing with an s, means allow embedded variables and expressions")

        //the format ${...} evaluations whatever is inside the {}
        //need a double $ to output $ in a s string...similar to \\ in print or %% in printf
        println(s"use $$ to specify a embedded item: $sampleOut") //{} not needed if a single variable
        println(s"use $${...} for expressions. Does 2 = 2? ${2 == 2}")
    }

    //input...basically Java style with Scala syntax
    {
        println("\n\nThere are multiple ways to read in values in Scala")

        //input, string
        print("The simplest console input, that grabs a string. Type anything: ")
        val input = StdIn.readLine()
        println(s"Input: $input")

        //input, one at a time
        println("Supports readX for all basic data types ")
        print("Input a integer: ")
        val inputInt = io.StdIn.readInt() //without the import, io.StdIn still works
        print("input a double: ")
        val inputDouble = StdIn.readDouble()
        println(s"Input: $inputInt, $inputDouble")
    }


    //fancier output
    {
        println("\n\n........................................\n");
        val pizza: String = "pepperoni"
        val cheese: String = "mozzarella"

        //string combination
        println("We can combine stings with +:")
        println(pizza + " pizza")
        println("Or with , (put outputs a tuple):")
        println(pizza, " pizza")

        println("\nOR we can combine stings with an 's' " +
          "before the string, and $ to signal the word is a variable")
        println(s"$pizza and $cheese pizza")
        println("\n we can put in a raw string with triple \"")
        println("""No escape "characters" (\) here!""")

        println("Scala has printf (same rules as Java\\C++), but has a more Scala way")
        println(f"$pizza%20s $cheese ${Math.PI}%-20.3fshow the %%")
        /*
        $x means grab that variable
        % means what comes next is the formatting
        %% means print the %
         */

        /*
        QUESTION:
        Using Scala's fancy output options. Take the following variables
            val a = 3
            val b = 1.25
            val tag = "Result"
         and output the following string
            3/1.25 Result:   =                2.40%
         Where
            -Result (with the colon) has a left justification and width 10
            -2.4 is 3/1.25 formatted to 2 decimal places, right justified with width 20




        Answer:
            println(f"$a/$b ${tag+":"}%-10s=${a/b}%20.2f%%")
         */


        //I copied this in after recording this file
        //more fancy output
        //the """ PLUS the | PLUS the stripMargin says align to |
        //There is more to it, but this is still quite handy!
        println(
            """
              |Menu with built in alignment!
              |1) A
                     |2) B
          |3) C
        """.stripMargin)
    }



}

