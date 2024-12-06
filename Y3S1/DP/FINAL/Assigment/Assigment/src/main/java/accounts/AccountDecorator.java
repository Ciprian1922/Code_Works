package accounts;

import bank.Client;

import java.util.List;
public abstract class AccountDecorator {
    protected final Client wrappedClient;

    public AccountDecorator(Client client) {
        this.wrappedClient = client;
    }

    public void addAccount(Account.TYPE type, double balance) {
        wrappedClient.addAccount(type, balance);
    }

    public Account getAccount(String accountNumber) {
        return wrappedClient.getAccount(accountNumber);
    }

    public List<Account> getAccounts() {
        return wrappedClient.getAccounts();
    }

    public String getName() {
        return wrappedClient.getName();
    }

    public String getAddress() {
        return wrappedClient.getAddress();
    }

    @Override
    public String toString() {
        return wrappedClient.toString();
    }


}


