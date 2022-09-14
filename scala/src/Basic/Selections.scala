package Basic

import scala.io.StdIn

object Selections extends App {

  //If, if-else, is identical to Java
  {
    print("Enter x (integer): ")
    val x = StdIn.readInt
    print("Enter y (integer): ")
    val y = StdIn.readInt

    println("if..................")
    if (x > 10) {
      println(s"x ($x) is greater than 10")
    }

    println("if-else..................")
    if (y > 10) {
      println(s"y ($y) is greater than 10")
    } else {
      println(s"y ($y) is NOT greater than 10")
    }

    println("if, else if, else..................")
    if (x > y) {
      println(s"x ($x) is greater than y ($y)")
    } else if (x == y) {
      println(s"x ($x) equal to y ($y)")
    }
    else {
      println(s"x ($x) is NOT greater than y ($y)")
    }

  }


  //Switch--> match... sort of
  {
    /*
     Note, Scala calls this pattern matching instead for GOOD reason.
     I will come back to its advanced features in specialties

     Big differences for now:
      - no break!
      - if no case is matched, it will crash!
     */

    print("\nInput a character (lower case): ")
    val z = StdIn.readChar()
    z match {
      case 'a' | 'i' | 'e' => println(s"$z is vowel")
      case 'o' => println(s"$z is vowel")
      case 'u' => println(s"$z is vowel")
      case 'y' => println(s"$z is a sometimes vowel")
      case _ => println(s"$z is NOT a vowel") //_ is the wild card/default
    }
    /*
    QUESTION:
    Write a Scala "switch" to determine if a number is 1 or 2 and print their word.
    It may not crash if a value other than 1 or 2 is input





    Answer:
    var z2 : Int = _
    z2 = StdIn.readInt()
    z2 match {
      case 1 => println(s"one")
      case 2 => println(s"two")
      case _ => //yes, it does NOTHING
     }

     */
  }



}
