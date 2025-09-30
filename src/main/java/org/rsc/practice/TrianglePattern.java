package org.rsc.practice;

/*
 * triangle pattern
 */
public class TrianglePattern {

    public static void main(String [] args) {
        int number = 7 ;
        printTriangle(number) ;
        number = 6 ;
        printTriangle(number) ;
    }

    public static void printTriangle(int n) {
        if (n <= 0)
            return ;

        System.out.println("Printing pattern for number - " + n) ;
        boolean isOdd = n%2 != 0 ;
        int peak = (n+1)/2 ;

        // Top portion
        for (int i = 1 ; i <= peak; i++) {
            for (int j = 0; j < i; j++) {
                System.out.print("#") ;
            }
            System.out.println() ;
        }

        // Bottom portion
        int start = isOdd ? peak - 1 : peak ;
        for (int i = start; i >= 1; i--) {
            for (int j=0; j < i; j++) {
                System.out.print("#") ;
            }
            System.out.println() ;
        }
    }

}
