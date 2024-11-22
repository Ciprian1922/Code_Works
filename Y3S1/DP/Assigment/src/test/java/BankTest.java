import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BankTest {
    private Bank bank;
    private Client client;

    @BeforeEach
    public void setUp() {
        bank = new Bank("Test Bank");
        client = new Client.Builder()
                .withName("John Doe")
                .withAddress("Somewhere")
                .withInitialAccount(Account.TYPE.RON, 100)
                .build();
        bank.addClient(client);
    }

    @Test
    public void testDeposit() throws InvalidAmountException {
        Account account = client.getAccount(client.getAccounts().get(0).getAccountNumber());
        account.depose(50);
        assertEquals(150, account.getTotalAmount());
    }

    @Test
    public void testWithdraw() throws InsufficientFundsException, InvalidAmountException {
        Account account = client.getAccount(client.getAccounts().get(0).getAccountNumber());
        account.retrieve(50);
        assertEquals(50, account.getTotalAmount());
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        Account account = client.getAccount(client.getAccounts().get(0).getAccountNumber());
        assertThrows(InsufficientFundsException.class, () -> account.retrieve(200));
    }

    @Test
    public void testTransactionHistory() throws InvalidAmountException, InsufficientFundsException {
        Account account = client.getAccount(client.getAccounts().get(0).getAccountNumber());
        account.depose(100);
        account.retrieve(50);
        assertTrue(account.getTransactions().contains("Deposited: 100.0"));
        assertTrue(account.getTransactions().contains("Withdrew: 50.0"));
    }

    @Test
    public void testUniqueAccountNumber() {
        Client client2 = new Client.Builder()
                .withName("Jane Doe")
                .withAddress("Elsewhere")
                .withInitialAccount(Account.TYPE.EUR, 200)
                .build();
        assertNotEquals(client.getAccount(client.getAccounts().get(0).getAccountNumber()),
                client2.getAccount(client2.getAccounts().get(0).getAccountNumber()));
    }
}

