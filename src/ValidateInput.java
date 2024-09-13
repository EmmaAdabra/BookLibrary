import java.util.HashMap;

/**
 * Interface that defines methods for validating user input.
 * Classes implementing this interface are expected to provide
 * specific implementations for validating various types of user input.
 */
public interface ValidateInput {
    HashMap<String, String> validateName(String name);
    HashMap<String, String> validateEmail(String email);
    HashMap<String, String> validatePassword(String password);

    HashMap<String, String> validateOption(byte option, byte min, byte max);
}
