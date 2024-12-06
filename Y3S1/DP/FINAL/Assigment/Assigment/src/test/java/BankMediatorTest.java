import accounts.Account;
import bank.Bank;
import bank.BankMediator;
import bank.Client;
import commands.TransferCommand;
import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;
import operations.Command;
import commands.DepositCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BankMediatorTest {

    private Bank bank;
    private BankMediator bankMediator;
    private Client client1;
    private Client client2;

    @BeforeEach
    public void setUp() {
        // Initialize a bank.Bank instance and add it to the bank.BankMediator
        bank = new Bank("Test bank.Bank");
        bankMediator = new BankMediator(bank);

        // Create and add a client to the bank
        client1 = new Client.Builder()
                .withName("John Doe")
                .withAddress("Somewhere")
                .withInitialAccount(Account.TYPE.RON, 100)
                .build();
        bank.addClient(client1);

        // Create another client for testing
        client2 = new Client.Builder()
                .withName("Jane Doe")
                .withAddress("Elsewhere")
                .withInitialAccount(Account.TYPE.EUR, 200)
                .build();
        bank.addClient(client2);
    }

    @Test
    public void testAddClient() {
        // Create a new client
        Client newClient = new Client.Builder()
                .withName("Mark Smith")
                .withAddress("New York")
                .withInitialAccount(Account.TYPE.RON, 500)
                .build();

        // Add the new client using the mediator
        bankMediator.addClient(newClient);

        // Verify that the client was added by checking the bank's client list
        assertTrue(bank.getClients().contains(newClient), "The client should be added to the bank.");
    }

    @Test
    public void testRemoveClient() {
        // Remove the client1 using the mediator
        bankMediator.removeClient(client1);

        // Verify that the client was removed by checking the bank's client list
        assertFalse(bank.getClients().contains(client1), "Client1 should be removed from the bank.");
    }

    @Test
    public void testPerformOperation_Deposit() throws InvalidAmountException {
        // Create a deposit command for client1's account
        Account account = client1.getAccount(client1.getAccounts().get(0).getAccountNumber());
        Command depositCommand = new DepositCommand(account, 100);

        // Perform the operation (deposit) using the mediator
        bankMediator.performOperation(depositCommand);

        // Verify that the account balance has increased after the deposit
        assertEquals(200, account.getTotalAmount(), "The account balance should be updated after the deposit.");
    }

    @Test
    public void testPerformOperation_Transfer() throws InvalidAmountException, InsufficientFundsException {
        // Create a transfer command to transfer money between client1 and client2
        Account account1 = client1.getAccount(client1.getAccounts().get(0).getAccountNumber());
        Account account2 = client2.getAccount(client2.getAccounts().get(0).getAccountNumber());
        Command transferCommand = new TransferCommand(account1, account2, 50);

        // Perform the transfer operation using the mediator
        bankMediator.performOperation(transferCommand);

        // Verify the updated account balances
        assertEquals(50, account1.getTotalAmount(), "Client1's account balance should be updated.");
        assertEquals(250, account2.getTotalAmount(), "Client2's account balance should be updated.");
    }

    @Test
    public void testGenerateReports() {
        // Generate reports using the mediator
        bankMediator.generateReports();

        // Since reports are generated, we can't directly test the output in this case,
        // but we can check if the reports are generated without throwing exceptions.
        assertDoesNotThrow(() -> bank.generateReports(), "Report generation should not throw any exceptions.");
    }
}
