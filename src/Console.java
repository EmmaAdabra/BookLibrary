import java.util.Scanner;

/**
 * This class is used to read users inputs
 */
public class Console {
    private static Scanner scanner = new Scanner(System.in);

    public static String readString(){
        return scanner.nextLine();
    }

    public static byte readByte(){
        return scanner.nextByte();
    }

    public static void clearBuffer() {
        scanner.nextLine();
    }
}
