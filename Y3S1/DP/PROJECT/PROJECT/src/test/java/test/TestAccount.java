package test;

import accounts.Account;
import accounts.AccountRON;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestAccount {

    @Test
    public void testDeposePositiveAmount() {
        Account account = new AccountRON("RON1234", 100);
        account.depose(50);
        assertEquals(150, account.getAmount());  // Use getAmount() to exclude interest
    }

    @Test
    public void testDeposeNegativeAmount() {
        Account account = new AccountRON("RON1234", 100);
        assertThrows(IllegalArgumentException.class, () -> account.depose(-50));
    }

    @Test
    public void testRetrieveExceedingBalance() {
        Account account = new AccountRON("RON1234", 100);
        assertThrows(IllegalArgumentException.class, () -> account.retrieve(200));
    }

    @Test
    public void testRetrieveWithinBalance() {
        Account account = new AccountRON("RON1234", 100);
        account.retrieve(50);
        assertEquals(50, account.getAmount());  // Use getAmount() to exclude interest
    }
}
