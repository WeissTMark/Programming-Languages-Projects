
package basics;

import java.text.DecimalFormat;

public class FormatOutput
{
    public static void main( String [] args )
    {
        int x = 100;
        double y = 3.14159;
        String s = "Values:";

        // string concatenation
        System.out.println( s + " x = " + x + ", y = " + y +x );

        // String.format() method
        String f = String.format( "%-10s x = %8d, y = %8.4f\n", s, x, y );
        System.out.print( f );
        
        // PrintStream format() and printf() methods (which are equivalent)
        System.out.format( "%-10s x = %8d, y = %8.4f\n", s, x, y );
        System.out.printf( "%-10s x = %8d, y = %8.4f\n", s, x, y );

        System.out.println("To convert from a string to number, use Wrapper.parseWrapper()");
        System.out.print( "Converted from a \"3.14\": " +  Double.parseDouble("3.14") );

        /*
        Decimal format is a pattern based formatter.

        Key rules:
        1) 0 shows 0 no matter what
        2) # skips 0 if possible
        3) you cannot place 0 after a # starting at the . (.e.g, #0. is OK, 0#. is NOT)

         */
        DecimalFormat formater = new DecimalFormat("000,000.000");
        System.out.println("\nDecimalFormat is a fast way to convert numbers to string: " + formater.format(23456.89));
        
    }
}

/*
Question:
What is the pattern needed for output $3,456.890 from 3456.89023?
A. …= new DecimalFormat(“000,###.000");
B. …= new DecimalFormat(“###,###.##0");
C. …= new DecimalFormat(“###,###.000");
D. …= new DecimalFormat(“######.000");
E. …= new DecimalFormat(“######.###");
 */


/*
Answer:
C. …= new DecimalFormat(“###,###.000");
 */