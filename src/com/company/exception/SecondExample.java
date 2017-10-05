package com.company.exception;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SecondExample {
    //    ^D - close the programs input stream on windows and linux

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            int result = divide();
            System.out.println("The result is - " + result);
        } catch (ArithmeticException e) {
            System.out.println("error toString() - " + e.toString());
            System.out.println("Unable to perform division...");
        }
    }

    private static int divide() {
        int x, y;
        try {
            x = getInt();
            y = getInt();
            System.out.println("x is " + x + " and y is " + y);
            return x / y;
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("No suitable input received...");
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Attempt to divide by 0...");
        }
    }

    private static int getInt() {
        System.out.println("Please enter an integer...");
        while (true) {
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                // start again and read past the end of line...
                System.out.println("Error message - " + e.getMessage());
                System.out.println("Error cause - " + e.getCause());
                System.out.println("There was an error, please enter a number 0-9 :)  ");
                sc.nextLine();
            }
        }
    }
}
