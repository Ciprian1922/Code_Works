package operations;

import bank.Bank;
import bank.Client;

public interface Mediator {
    void addClient(Client client);
    void removeClient(Client client);
    void performOperation(Command command);
    void generateReports();

    Bank getBank();
}
