package commands;

import accounts.Account;
import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;
import operations.Command;

public class TransferCommand implements Command {
    private Account source;
    private Account target;
    private double amount;

    public TransferCommand(Account source, Account target, double amount) {
        this.source = source;
        this.target = target;
        this.amount = amount;
    }

    @Override
    public void execute() {
        try {
            source.retrieve(amount);
            target.depose(amount);
        } catch (InsufficientFundsException | InvalidAmountException e) {
            System.err.println("operations.Transfer failed: " + e.getMessage());
        }
    }
}