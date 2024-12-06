import static org.junit.jupiter.api.Assertions.*;

import Transactions.TransactionInvoker;
import accounts.Account;
import accounts.AccountEUR;
import accounts.AccountRON;
import commands.TransferCommand;
import commands.WithdrawalCommand;
import operations.Command;
import commands.DepositCommand;
import org.junit.jupiter.api.Test;

public class CommandPatternTest {

    @Test
    public void testDepositCommand() {
        Account account = new AccountEUR("ACC123", 500);
        Command depositCommand = new DepositCommand(account, 100);
        depositCommand.execute();

        assertEquals(600, account.getTotalAmount(), "Deposit command failed");
    }

    @Test
    public void testWithdrawalCommand() {
        Account account = new AccountEUR("ACC123", 500);
        Command withdrawalCommand = new WithdrawalCommand(account, 100);
        withdrawalCommand.execute();

        assertEquals(400, account.getTotalAmount(), "Withdrawal command failed");
    }

    @Test
    public void testTransferCommand() {
        Account account1 = new AccountEUR("ACC123", 500);
        Account account2 = new AccountRON("ACC124", 100);
        Command transferCommand = new TransferCommand(account1, account2, 50);
        transferCommand.execute();

        assertEquals(450, account1.getTotalAmount(), "operations.Transfer command failed for account 1");
        assertEquals(150, account2.getTotalAmount(), "operations.Transfer command failed for account 2");
    }

    @Test
    public void testCommandInvoker() {
        Account account = new AccountEUR("ACC123", 500);
        Command depositCommand = new DepositCommand(account, 200);
        Command withdrawalCommand = new WithdrawalCommand(account, 50);
        TransactionInvoker invoker = new TransactionInvoker();

        invoker.addCommand(depositCommand);
        invoker.addCommand(withdrawalCommand);

        invoker.executeCommands();

        assertEquals(650, account.getTotalAmount(), "Invoker did not execute commands correctly");
    }
}
