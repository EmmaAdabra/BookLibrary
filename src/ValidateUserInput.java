import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class implements the ValidateInput interface to validate user input
 *  for various criteria such as names, email addresses, passwords, and options.
 */
public class ValidateUserInput implements ValidateInput{

    @Override
    public boolean validateName(String name) {
        if(!(name.length() >= 3))
            return false;
        return true;
    }

    @Override
    public boolean validateEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        var pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches())
            return false;
        return true;
    }

    @Override
    public boolean validatePassword(String password) {
        if(!(password.length() >= 4))
            return false;
        return true;
    }

    @Override
    public boolean validateOption(byte option, byte min, byte max) {
        if(!(option >= min && option <= max))
            return false;
        return true;
    }
}
