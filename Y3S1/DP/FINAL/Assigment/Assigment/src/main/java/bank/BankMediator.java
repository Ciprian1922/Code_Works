package bank;

import Transactions.TransactionInvoker;
import operations.Command;
import operations.Mediator;

public class BankMediator implements Mediator {
    private Bank bank;
    private TransactionInvoker invoker;

    public BankMediator(Bank bank) {
        this.bank = bank;
        this.invoker = new TransactionInvoker();  // Invoker for commands
    }

    @Override
    public void addClient(Client client) {
        bank.addClient(client);
        System.out.println("bank.Client " + client.getName() + " added.");
    }

    @Override
    public void removeClient(Client client) {
        bank.removeClient(client);
        System.out.println("bank.Client " + client.getName() + " removed.");
    }

    @Override
    public void performOperation(Command command) {
        invoker.addCommand(command);
        invoker.executeCommands();
    }

    @Override
    public void generateReports() {
        bank.generateReports();
        System.out.println("Reports generated.");
    }
}

// centralizes communication between components, handles interactions between the
// bank, clients, and operations