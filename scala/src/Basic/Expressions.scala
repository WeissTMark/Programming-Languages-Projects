package Basic



object Expressions extends App {
    //close to java
    val y = 5
    val z = 8
    var x = y + z
    x = y * z
    x = y / z // (same integer division problems as before)

    //compound still works
    x *= y

    //increment does NOT
    //x++
    x += 1

    //if you use it in an expression, the result is NOT returned. () (void) is
    println("x+=y (does NOT work)= " + (x += y))
    var ans = x
    ans += y
    println("x+=y (fixed)= " + ans)

    //Math library automatically loaded, and works the same
    {
        println("x^y (correct with power function)= " + Math.pow(x, y))
        println("PI%y with doubles = " + Math.IEEEremainder(Math.PI, y))
        println("min(x,y,z) = " + Math.min(x, Math.min(y, z)))
        println("\nRandom 1: " + (Math.random()))
    }

    //logical operators same as before
    {
        println("equal " + (2 == 2))
        println("or " + (2 == 2 || 3 < 2))
        println("and " + (2 == 2 && 3 < 2))
    }

    /*
    ORDER OF PRECEDENCE IS DIFFERENT
    Based on first letter (except =)!
        (all other special characters)
        * / %
        + -
        :
        < >
        == !
        &
        ^
        |
        (All letters)
        =

     */
}
