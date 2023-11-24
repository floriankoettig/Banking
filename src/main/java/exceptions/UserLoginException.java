package exceptions;
public class UserLoginException extends Exception {
    public UserLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}