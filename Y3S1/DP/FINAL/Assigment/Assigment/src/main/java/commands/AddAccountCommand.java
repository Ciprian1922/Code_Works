package commands;

import accounts.Account;
import bank.Client;
import operations.Command;

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


//the Commander Pattern encapsulates requests as objects

// the sender (e.g., mediator) doesn't need to know the logic
// of the operation; it just executes commands

//commands can be stored, replayed, or rolled back,
// making them ideal for transactional systems

// adding new commands doesn't require modifying existing code—just
// create a new command class