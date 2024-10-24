package main.java.com.librarySystem.auth;

import main.java.com.librarySystem.repository.IUserRepository;
import main.java.com.librarySystem.user.RUser;
import main.java.com.librarySystem.util.Response;

public class AuthServices implements IAuthServices{
    IUserRepository userRepository;

    public AuthServices(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Response verifyLogin(String email, String password) {
        RUser user = userRepository.getUserByEmail(email);
        if(user == null || !user.getUserPassword().equals(password)) {
            return new Response(false, "Invalid login details", null);
        }

        return new Response(true, "success", user);
    }
}
