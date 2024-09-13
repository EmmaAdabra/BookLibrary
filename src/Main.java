public class Main {
    public static void main(String []args){
        System.out.println("------------------ Virtual Library ----------------");
        var validate = new ValidateUserInput();
        var registerUser = new RegisterUser(validate);
        registerUser.registrationUI();
    }
}
