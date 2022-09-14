package Basic

import scala.io.StdIn
import scala.util.control.Breaks._

object Repetition extends App {


    //while and do-whiles----------------------------
    {
        //same as Java!
        println("While loop")
        var x = 1
        while (x != 0) {
            print(s"Input a integer (stop with 0): ")
            x = StdIn.readInt
        }

        println("do-While loop")
        x = 1
        do {
            print(s"Input a integer (stop with 0): ")
            x = StdIn.readInt
        } while (x != 0)

    }


    //for loop is a range loop
    {
        //adds Python like range/counting loops------------------------
        println("\n\nfor loop as a counting loop, inclusive")
        for (i <- 0 to 4) {
            print(s"$i ")
        }
        println()

        println("for loop as a counting loop, exclusive of end")
        for (i <- 0 until 4) {
            print(s"$i ")
        }
        println()

        println("for loop as a counting loop, exclusive of end, by 2")
        for (i <- 0 until 4 by 2) {
            print(s"$i ")
        }
        println()

        println("for loop with doubles")
        for (i <- BigDecimal(0.5) until 4.5 by 2) { //deprecated work with regular floats
            print(s"$i ")
        }
        println()

        println("It is possible to break out. Needs scala.util.control.Breaks._")
        breakable { //mark the block as permitting break
            for (i <- 0 until 10) {
                print(s"$i ")

                if (i == 4)
                    break //break

            }
        }
        println()

        /*
        QUESTION: Why do you think they made this so hard?




        Answer:
            A for loop's proper usage is "supposed" to have a set number of iterations!
         */
    }


    //for each
    {
        val nums = List((Math.random() * 100).toInt,
            (Math.random() * 100).toInt,
            (Math.random() * 100).toInt,
            (Math.random() * 100).toInt,
            (Math.random() * 100).toInt)
        println(s"\n\nRandom numbers for testing next loops: $nums")

        println("for each loop")
        for (i <- nums) {
            print(s"$i ")
        }
        println()

        println("for each loop with indices")
        for ((value, index) <- nums.zipWithIndex) {
            println(s"$index is $value")
        }
    }


    //fancy for loops (starting with functional programming)
    {
        val nums = List((Math.random() * 100).toInt,
            (Math.random() * 100).toInt,
            (Math.random() * 100).toInt,
            (Math.random() * 100).toInt,
            (Math.random() * 100).toInt)

        println("for each loop with even filtering")
        for (i <- nums if i % 2 == 0) {
            print(s"$i ")
        }
        println()

        println("for each loop with even filtering, AND returning the values!")
        val evens = for (i <- nums if i % 2 == 0) yield i

        print(s"Evens variable contains: $evens ")
        println()


    }

    /*
    QUESTION
    Write a for loop that returns the cube of all items in a list called x.




    ANSWER:
    val x2 = List(1,2,3,4,5,6)
    val cubes = for (i <- x2 ) yield i*i*i
    print(cubes)

    OR

    val x2 = List(1,2,3,4,5,6)
    var cubes = List[Int]()
    for (i <- x2 ){
        cubes :+= i*i*i
    }
    print(cubes)



    REMINDER: while Scala will allow you to use imperative programming,
    it is NOT where it shines. This means try to use list comprehensions*
    whenever possible


    *e.g. newList = list.someFunc(…)

     */



}
