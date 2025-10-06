package org.example.util;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputValidator {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getString(String message) {
        String input;
        while (true) {
            System.out.println(message);
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty, try again!");
        }
    }

    public static String getEmail(String message){
        String input;
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        while (true) {
            System.out.println(message);
            input = scanner.nextLine().trim();
            if (input.matches(regex)) {
                return input;
            }
            System.out.println("Invalid email format, please try again!");
        }
    }

    public static int getInt(String message) {
        while (true) {
            System.out.println(message);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, please try again!");
            }
        }
    }


    public static BigDecimal getBigDecimal(String message) {
        while (true) {
            System.out.println(message);
            try {
                return new BigDecimal(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid decimal value, please try again!");
            }
        }
    }

    public static boolean getBoolean(String message) {
        while (true) {
            System.out.println(message);
            try {
                return Boolean.parseBoolean(scanner.nextLine().trim());
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input, please try again!");
            }
        }
    }

}
