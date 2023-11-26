package exceptions;
public class WithdrawalException extends Exception {
    public WithdrawalException(String message, Throwable cause) {
        super(message, cause);
    }
}