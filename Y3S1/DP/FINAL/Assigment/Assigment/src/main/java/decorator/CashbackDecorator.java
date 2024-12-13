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


    //way to apply cashback(without re-depositing the base amount)
    public void deposeWithCashback(String accountNumber, double amount) throws InvalidAmountException {
        Account account = wrappedClient.getAccount(accountNumber);  //retrieve the account
        if (account != null) {
            double cashback = amount * cashbackPercentage; //calculate cashback
            account.depose(amount); //apply the base deposit first
            account.depose(cashback); //apply cashback to the account
            account.getTransactions().add("Cashback earned: " + cashback); //log cashback transaction
        } else {
            throw new IllegalArgumentException("accounts.Account not found: " + accountNumber);
        }
    }

    @Override
    public String toString() {
        return super.toString() + " (decorator.CashbackDecorator applied)";
    }
}



// add a cashback bonus when a deposit is made (e.g., 5% cashback on
// deposited amounts)









