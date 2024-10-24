package main.java.com.librarySystem.util;

public class NewResponse {
    public Boolean status;
    public String message;
    public Object obj;

    public NewResponse(Boolean status, String message, Object obj) {
        this.status = status;
        this.message = message;
        this.obj = obj;
    }
}
