package basics;

public class ArrayExamples {

    public static void main(String[] args) {

        //Basics
        {
            // allocate array of n primitives
            int[] array1 = new int[10]; //classes

            // stuff some values inside the array
            //length property comes with!
            for (int i = 0; i < array1.length; i++) {
                array1[i] = i + 1;
            }

            ////automatically checks bounds! so this wil fail
            //array1[20] = 2;

            //these do not work, stack allocation is forbidden...Can you guess why?
            //int sizedArray[10];
            //int[10] sizedArray2;

            // print them out
            System.out.print("array1 (ints): ");
            for (int i = 0; i < array1.length; i++) {
                System.out.print(" " + array1[i]);
            }
            System.out.println();
        }


        //Arrays with objects
        {
            // allocate array of n objects
            //Object is the ancestor of ALL classes in Java (more on this later)
            Object[] array2 = new Object[10];

            // assign array values (all Objects, but not necessarily the same type)
            // note that you must create a new object for each array element
            // in this case we'll create several different types (chars, strings, ints, floats)
            int k = 0;
            for (; k < array2.length / 4; k++) {
                array2[k] = (char) ((int) 'A' + k * 2);
            }
            for (; k < array2.length / 2; k++) {
                array2[k] = k * 2;
            }
            for (; k < array2.length * 3 / 4; k++) {
                array2[k] = k * 2;
            }
            array2[k++] = "playing";
            array2[k++] = "in";
            array2[k++] = "the";

            // print them out
            System.out.print("array2 (objects): ");
            for (int i = 0; i < array2.length; i++) {
                System.out.print(" " + array2[i]);
            }
            System.out.println();
        }


        //2D arrays
        {
            // allocate a rectangular 2-D array (3 rows and 4 columns)
            //Integer is an "reference" version of int
            Integer[][] a = new Integer[3][4];

            // stuff some values inside the array
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a[i].length; j++) {
                    a[i][j] = i + j + 1;
                }
            }

            // print them out
            System.out.println("Rectangular array: ");
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a[i].length; j++) {
                    System.out.print(" " + a[i][j]);
                }
                System.out.println();
            }
            System.out.println();
        }


        //Jagged arrays
        {
            // allocate a jagged 2-D array (3 rows, unspecified columns)
            Integer[][] b = new Integer[3][];

            // stuff some values inside the array
            // rows 0,1,2 will have 3,4,5 column elements
            for (int i = 0; i < b.length; i++) {
                b[i] = new Integer[i + 3];
                for (int j = 0; j < b[i].length; j++) {
                    b[i][j] = i + j + 1;
                }
            }

            // print them out
            System.out.println("Jagged array: ");
            for (int i = 0; i < b.length; i++) {
                for (int j = 0; j < b[i].length; j++) {
                    System.out.print(" " + b[i][j]);
                }
                System.out.println();
            }
            System.out.println();
        }

        //yes this still works:
        int[][] jag = { {1,2}, {3}, {4,5,6} };
        System.out.println("Initialized Jagged array: ");
        for (int i = 0; i < jag .length; i++) {
            for (int j = 0; j < jag [i].length; j++) {
                System.out.print(" " + jag [i][j]);
            }
            System.out.println();
        }
        System.out.println();

    }
}

