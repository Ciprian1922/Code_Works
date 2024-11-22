package exceptions;

public class RetrieveAmountException extends IllegalArgumentException {
    public RetrieveAmountException(String message) {
        super(message); // Pass the message to the superclass (IllegalArgumentException)
    }
}
