package main.java.com.librarySystem.util;

public class StringUtility {
    public static IValidateInput validate = new ValidateUserInput();

    public static String maskEmail(String email) {
        String [] splitEmail = email.split("@");
        String emailName = splitEmail[0];
        String emailDomain = splitEmail[1];
        int emailNameLength = emailName.length();
        int maskLength = emailNameLength / 2;

        maskLength = emailNameLength % 2 > 0 ? maskLength + 1 : maskLength;
        String maskCharacter = "*".repeat(maskLength);

        return maskCharacter + emailName.substring(maskLength) + "@" + emailDomain;
    }

}
