package test;

import accounts.Account;
import accounts.AccountRON;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestAccount {

    @Test
    public void testDeposePositiveAmount() {
        Account account = new AccountRON("RON1234", 100);
        account.depose(50); //OPERATIONS INTERFACE TEST
        assertEquals(150, account.getAmount());
    }

    @Test
    public void testDeposeNegativeAmount() {
        Account account = new AccountRON("RON1234", 100);
        assertThrows(IllegalArgumentException.class, () -> account.depose(-50)); // EXCEPTION HANDLING TEST
    }

    @Test
    public void testRetrieveExceedingBalance() {
        Account account = new AccountRON("RON1234", 100);
        assertThrows(IllegalArgumentException.class, () -> account.retrieve(200)); // EXCEPTION HANDLING TEST
    }

    /**
     Exception handling is added to ensure withdrawals (retrieve) only succeed if the amount is within the available balance.
     If the withdrawal exceeds the balance or is invalid, an IllegalArgumentException (or a custom exception like RetrieveAmountException)
     is thrown to prevent inconsistent state. Refering to // EXCEPTION HANDLING TEST and // EXCEPTION HANDLING FOR RETRIEVAL IMPLEMENTED
     */
    @Test
    public void testRetrieveWithinBalance() {
        Account account = new AccountRON("RON1234", 100);
        account.retrieve(50); //OPERATIONS INTERFACE TEST
        assertEquals(50, account.getAmount());
    }

    /**
     The Operations interface provides methods like depose and retrieve for account transactions.
     These methods ensure consistent behavior for depositing or withdrawing money across different
     account types (AccountRON, ContEUR).
     Refering to //OPERATIONS INTERFACE TEST and //OPERATIONS INTERFACE IMPLEMENTED
     */
}
