package exceptions;

public class DepositAmountException extends IllegalArgumentException {
    public DepositAmountException(String message) {
        super(message); // Pass the message to the superclass (IllegalArgumentException)
    }
}
