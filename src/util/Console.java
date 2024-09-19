package util;

import java.util.Scanner;
/**
 * This class is used to read users inputs
 */
public class Console {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static String readString(String prompt){
        String value;
        while(true) {
            System.out.print(prompt +": ");
            value = SCANNER.nextLine().toUpperCase().trim();
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
                String userOption = SCANNER.nextLine().trim();
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
