package decorator;

import accounts.Account;
import accounts.AccountDecorator;
import bank.Client;
import exceptions.InvalidAmountException;

public class CashbackDecorator extends AccountDecorator {
    private final double cashbackPercentage;

    public CashbackDecorator(Client client, double cashbackPercentage) {
        super(client);
        this.cashbackPercentage = cashbackPercentage;
    }


    // Method to apply cashback (without re-depositing the base amount)
    public void deposeWithCashback(String accountNumber, double amount) throws InvalidAmountException {
        Account account = wrappedClient.getAccount(accountNumber);  // Retrieve the account
        if (account != null) {
            double cashback = amount * cashbackPercentage; // Calculate cashback
            account.depose(amount); // Apply the base deposit first
            account.depose(cashback); // Apply cashback to the account
            account.getTransactions().add("Cashback earned: " + cashback); // Log cashback transaction
        } else {
            throw new IllegalArgumentException("accounts.Account not found: " + accountNumber);
        }
    }

    @Override
    public String toString() {
        return super.toString() + " (decorator.CashbackDecorator applied)";
    }
}













