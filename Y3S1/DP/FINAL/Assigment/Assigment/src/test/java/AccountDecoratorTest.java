import accounts.Account;
import bank.Bank;
import bank.Client;
import decorator.CashbackDecorator;
import decorator.LifeInsuranceDecorator;
import exceptions.InvalidAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountDecoratorTest {

    private Bank bank;
    private Client client;

    //initialize the client and bank in the setup method
    @BeforeEach
    public void setUp() {
        bank = new Bank("Test bank.Bank");
        client = new Client.Builder()
                .withName("John Doe")
                .withAddress("Somewhere")
                .withInitialAccount(Account.TYPE.RON, 100)  // Create an account of type RON with 100 balance
                .build();
        bank.addClient(client);  //add client to the bank
    }

    //test Life Insurance Decorator
    @Test
    public void testLifeInsuranceDecorator() throws InvalidAmountException {
        LifeInsuranceDecorator insuredClient = new LifeInsuranceDecorator(client);
        String accountNumber = client.getAccounts().get(0).getAccountNumber();

        //correctly using the 'deposeWithBonus' method
        insuredClient.deposeWithBonus(accountNumber, 100); // This applies the life insurance bonus

        //assuming you want to check if the transaction was recorded correctly:
        Account account = insuredClient.getAccount(accountNumber);
        assertTrue(account.getTransactions().contains("Life Insurance bonus applied: 10.0"));

    }

    //test Cashback Decorator
    @Test
    public void testCashbackDecorator() throws InvalidAmountException {
        //initialize decorator.CashbackDecorator with 5% cashback
        CashbackDecorator cashbackClient = new CashbackDecorator(client, 0.05); // 5% cashback
        String accountNumber = client.getAccounts().get(0).getAccountNumber();

        //correctly use the 'deposeWithCashback' method
        cashbackClient.deposeWithCashback(accountNumber, 100); // Deposit amount with cashback

        //get the account to check the transactions
        Account account = cashbackClient.getAccount(accountNumber);

        //check if the transaction contains the cashback log
        assertTrue(account.getTransactions().contains("Cashback earned: 5.0"));
    }






}





