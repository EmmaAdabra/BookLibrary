import java.util.List;

public class LoginUser {
    private static final ValidateInput VALIDATE = Main.validate;
    static List<User> users = Library.users;

    public static void getLoginDetails(){
        System.out.println();
        System.out.println("--------------- Login Details ---------------");
        String email = IterateInput.stringInput("email", VALIDATE::validateEmail);
        String password = IterateInput.stringInput("password", VALIDATE::validatePassword);
        if(!(users.size() == 0)) {
            User user = validateLoginDetails(email, password);
            if(!(user == null)) {
                user.login();
            }
            else
                System.out.println("Invalid login detail");
        }
        else
            System.out.println("No user is registered yet");
    }

    private static User validateLoginDetails(String email, String password){
        if (users.size() > 0) {
            for(User user : users) {
                if(email.equalsIgnoreCase(user.getUserEmail()) && password.equals(user.getUserPassword()))
                    return user;
            }
        }
        return null;
    }
}
