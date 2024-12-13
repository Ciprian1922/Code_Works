package decorator;

import accounts.Account;
import accounts.AccountDecorator;
import bank.Client;
import exceptions.InvalidAmountException;

public class LifeInsuranceDecorator extends AccountDecorator {

    public LifeInsuranceDecorator(Client client) {
        super(client);
    }

    //way to apply life insurance bonus (without re-depositing the base amount)
    public void deposeWithBonus(String accountNumber, double amount) throws InvalidAmountException {
        Account account = wrappedClient.getAccount(accountNumber); //retrieve the account
        if (account != null) {
            double bonus = amount * 0.1; //10% life insurance bonus
            account.depose(bonus); //apply bonus to the account
            account.getTransactions().add("Life Insurance bonus applied: " + bonus); // Log bonus
        } else {
            throw new IllegalArgumentException("accounts.Account not found: " + accountNumber);
        }
    }

    @Override
    public String toString() {
        return super.toString() + " (decorator.LifeInsuranceDecorator applied)";
    }
}


// when a deposit is made into the account, the system applies an additional
// "bonus" as if it were a reward or interest associated with a life insurance
// benefit


// a decorator pattern adds additional functionality to an object dynamically
// without modifying its structureit allows for flexible and reusable code
// by "wrapping" objects with new behaviors.












