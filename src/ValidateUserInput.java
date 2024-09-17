import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class implements the ValidateInput interface to validate user input
 *  for various criteria such as names, email addresses, passwords, and options.
 */
public class ValidateUserInput implements ValidateInput{

    @Override
    public HashMap<String, String> validateName(String name) {
        HashMap<String, String> response = new HashMap<>();
        if(!(name.length() >= 3 && name.length() <= 20)){
            response.put("code", "false");
            response.put("message", "username should be min 3 and max 20 character");
            return response;
        }
        response.put("code", "true");
        response.put("message", "success");
        return response;
    }

    @Override
    public HashMap<String, String> validateEmail(String email) {
        HashMap<String, String> response = new HashMap<>();
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        var pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()) {
            response.put("code", "false");
            response.put("message", "Email should follow this format \"example@example.com\" ");
            return response;
        }
        response.put("code", "true");
        response.put("message", "success");
        return response;
    }

    @Override
    public HashMap<String, String> validatePassword(String password) {
        HashMap<String, String> response = new HashMap<>();
        if(!(password.length() >= 4)) {
            response.put("code", "false");
            response.put("message", "password shouldn't be less than 4");
            return response;
        }
        response.put("code", "true");
        response.put("message", "success");
        return response;
    }

    @Override
    public HashMap<String, String> validateOption(int option, int min, int max) {
        HashMap<String, String> response = new HashMap<>();
        if(!(option >= min && option <= max)){
            response.put("code", "false");
            response.put("message", "option should be between " + min + " and " + max);
            return response;
        }
        response.put("code", "true");
        response.put("message", "success");
        return response;
    }
}
