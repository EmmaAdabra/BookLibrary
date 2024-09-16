import java.util.HashMap;

public class Main {

    public static ValidateInput validate = new ValidateUserInput();
    public static void main(String []args){
        System.out.println("**** Virtual Library ****");
        var registerUser = new RegisterUser();
        registerUser.registrationUI();
    }
}
