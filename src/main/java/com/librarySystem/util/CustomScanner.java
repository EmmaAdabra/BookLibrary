package main.java.com.librarySystem.util;

import java.util.Scanner;

/**
 * This class is used to read users inputs
 */
public class CustomScanner {
    private static Scanner scanner = new Scanner(System.in);
    public static String readString(String prompt){
        String value;
        while(true) {
            System.out.print(prompt +": ");
            value = scanner.nextLine().toUpperCase().trim();
            if(value.isEmpty()){
                System.out.println(prompt + " can't be empty");
            }
            else {
                break;
            }
        }
        return value;
    }

    public static int readInt(String prompt){
        int option;
        while (true) {
            try {
                System.out.print(prompt +": ");
                String userOption = scanner.nextLine().trim();
                if(userOption.isEmpty()){
                    System.out.println(prompt + " can't be empty");
                    continue;
                }
                option = Integer.parseInt(userOption);
                break;
            } catch (NumberFormatException e) {
                System.out.println("only accept numbers");
            }
        }
        return option;
    }
}
