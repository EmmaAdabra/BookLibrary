/**
 * Interface that defines methods for validating user input.
 * Classes implementing this interface are expected to provide
 * specific implementations for validating various types of user input.
 */
public interface ValidateInput {
    String validateName(String name);
    String validateEmail(String email);
    String validatePassword(String password);
    byte validateOption(byte userName);
}
