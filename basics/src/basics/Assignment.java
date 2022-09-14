package basics;

public class Assignment {
    public static void main(String[] args) {
        //assignments same as c++
        int x = 0;
        double y = 0;

        //number (and class) casting the same as c-style casting
        int x2 = (int)3.5;

        //conversion from string to a number
        //use a wrapper with the format of DataTypeName.parseDateType( "string");
        int x3 = Integer.parseInt( "11" );
        float f1 = Float.parseFloat( "3.14" );

        //strings are built in, and concatenation is simply
        //no operator overloading
        String temp = "hi" + 2;

        //same math as C++
        x = 2+3;
        y = 2/3 %6;

        //math library is built in
        x = Math.abs(-2);

        //NOTE: again no operator overloading!

    }
}
