package commands;

import accounts.Account;
import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;
import operations.Command;

public class WithdrawalCommand implements Command {
    private Account account;
    private double amount;

    public WithdrawalCommand(Account account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void execute() {
        try {
            account.retrieve(amount);
        } catch (InsufficientFundsException | InvalidAmountException e) {
            System.err.println("Withdrawal failed: " + e.getMessage());
        }
    }
}