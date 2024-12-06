package commands;

import accounts.Account;
import bank.Client;
import operations.Command;

// operations.Command to add a new account for a client
public class AddAccountCommand implements Command {
    private Client client;
    private Account.TYPE accountType;
    private double initialDeposit;

    public AddAccountCommand(Client client, Account.TYPE accountType, double initialDeposit) {
        this.client = client;
        this.accountType = accountType;
        this.initialDeposit = initialDeposit;
    }

    @Override
    public void execute() {
        client.addAccount(accountType, initialDeposit);
    }
}
