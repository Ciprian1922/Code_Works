package bank;

import accounts.Account;
import accounts.AccountFactory;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private String name;
    private String address;
    private List<Account> accounts;
    private static int accountCounter = 1;

    private Client(Builder builder) {
        this.name = builder.name;
        this.address = builder.address;
        this.accounts = new ArrayList<>();
        if (builder.initialAccountType != null) {//factory account refactor for account creation
            Account initialAccount = AccountFactory.createAccount(builder.initialAccountType,
                    "ACC" + (accountCounter++), builder.initialBalance);
            this.accounts.add(initialAccount);
        }
    }
    //builder implementation within the private client class(task2)
    public static class Builder {
        private String name;
        private String address;
        private Account.TYPE initialAccountType;
        private double initialBalance;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder withInitialAccount(Account.TYPE accountType, double balance) {
            this.initialAccountType = accountType;
            this.initialBalance = balance;
            return this;
        }

        public Client build() {
            return new Client(this);
        }
    }

    public void addAccount(Account.TYPE type, double balance) {
        String accountNumber = "ACC" + (accountCounter++);
        Account account = AccountFactory.createAccount(type, accountNumber, balance);
        accounts.add(account);
    }

    public Account getAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    @Override
    public String toString() {
        return "bank.Client{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}






