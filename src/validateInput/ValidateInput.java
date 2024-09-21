package validateInput;
import response.Response;

import java.util.HashMap;

/**
 * Interface that defines methods for validating user input.
 * Classes implementing this interface are expected to provide
 * specific implementations for validating various types of user input.
 */
public interface ValidateInput {
    Response validateName(String name);
    Response validateEmail(String email);
    Response validatePassword(String password);
    Response validateOption(int option, int min, int max);
}
