package main.java.com.librarySystem.util;

/**
 * Interface that defines methods for validating user input.
 * Classes implementing this interface are expected to provide
 * specific implementations for validating various types of user input.
 */
public interface IValidateInput {
    Response validateName(String name);
    Response validateEmail(String email);
    Response validatePassword(String password);
    Response validateUserOption(int option, int min, int max);
}
