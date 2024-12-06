package commands;

import accounts.Account;
import exceptions.InvalidAmountException;
import operations.Command;

public class DepositCommand implements Command {
    private Account account;
    private double amount;

    public DepositCommand(Account account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void execute() {
        try {
            account.depose(amount);
        } catch (InvalidAmountException e) {
            System.err.println("Deposit failed: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Deposit: " + amount + " into account " + account.getAccountNumber();
    }
}
