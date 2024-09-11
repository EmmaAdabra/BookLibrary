/**
 * Interface that defines methods for validating user input.
 * Classes implementing this interface are expected to provide
 * specific implementations for validating various types of user input.
 */
public interface ValidateInput {
    boolean validateName(String name);
    boolean validateEmail(String email);
    boolean validatePassword(String password);

    boolean validateOption(byte option, byte min, byte max);
}
