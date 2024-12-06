import accounts.Account;
import accounts.AccountEUR;
import accounts.AccountFactory;
import accounts.AccountRON;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


//reafactor account unit test
public class AccountFactoryTest {

    @Test
    public void testCreateAccount() {
        Account eurAccount = AccountFactory.createAccount(Account.TYPE.EUR, "ACC1", 100);
        assertTrue(eurAccount instanceof AccountEUR);
        assertEquals(100, eurAccount.getTotalAmount());

        Account ronAccount = AccountFactory.createAccount(Account.TYPE.RON, "ACC2", 200);
        assertTrue(ronAccount instanceof AccountRON);
        assertEquals(200, ronAccount.getTotalAmount());
    }
}

