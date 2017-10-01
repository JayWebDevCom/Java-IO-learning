package com.company.exception;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
//        int x = getIntLBYL();
        int x = getIntEAFP();
        System.out.println("integer received is " + x);
    }

    private static int getIntLBYL() {

        System.out.println("In lbyl - provide an int please >>");
        String received = sc.next();

        boolean isValid = true;
        for (int i = 0; i < received.length(); i++) {
            if (!Character.isDigit(received.charAt(i))) {
                isValid = false; // if there is a non-digit in the received string...
                break;
            }
            if (isValid) { // if there is NO non-digit in the received string...
                return Integer.parseInt(received);
            }
        }
        return 0;
    }

    private static int getIntEAFP() {
        System.out.println("In EAFP - provide an int please >>");
        try {
            int x = sc.nextInt();
            return x;
        } catch (InputMismatchException e) { // specify the specific exception
            System.out.println(e.getMessage());
            System.out.println("Exception caught...");
            return 0;
        }
    }
}
