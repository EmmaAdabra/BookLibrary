import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to read users inputs
 */
public class Console {
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
        String option;
        while (true) {
            try {
                System.out.print(prompt +": ");
                option = scanner.nextLine().trim();
                if(option.isEmpty()){
                    System.out.println(prompt + " can't be empty");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("only accept numbers");
                scannerNext();
            }
        }
        return Integer.parseInt(option);
    }

    public static void clearBuffer() {
        scanner.nextLine();
    }
    public static void scannerNext(){
        scanner.next();
    }
}
