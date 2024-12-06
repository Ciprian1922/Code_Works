import accounts.Account;
import accounts.AccountRON;
import bank.Bank;
import bank.BankMediator;
import bank.Client;
import commands.TransferCommand;
import decorator.CashbackDecorator;
import decorator.LifeInsuranceDecorator;
import commands.AddAccountCommand;
import operations.Command;
import commands.DepositCommand;
import operations.Mediator;

public class Test {
	public static void main(String[] args) {
		try {
			//create the bank and mediator
			Bank bank = new Bank("Banca BCR");
			Mediator mediator = new BankMediator(bank); // Create mediator

			// bank.Client 1: Ionescu Ion //builder usage
			Client client1 = new Client.Builder()
					.withName("Ionescu Ion")
					.withAddress("Timisoara")//factory
					.withInitialAccount(Account.TYPE.EUR, 200.9)
					.build();
			mediator.addClient(client1);  // Add client via mediator
			System.out.println(client1);

			//create commands and let mediator perform them
			//apply Cashback decorator to client1's account
			CashbackDecorator cashbackClient = new CashbackDecorator(client1, 0.05); // 5% cashback
			String accountNumber = client1.getAccounts().get(0).getAccountNumber(); // Get the account number

			//create and add Deposit operations.Command with Cashback
			Command depositWithCashback = new DepositCommand(client1.getAccount(accountNumber), 100);
			mediator.performOperation(depositWithCashback); // Perform via mediator

			//create Life Insurance Decorator command for bonus deposit
			LifeInsuranceDecorator insuredClient = new LifeInsuranceDecorator(client1);
			Command depositWithBonus = new DepositCommand(client1.getAccount(accountNumber), 200);
			mediator.performOperation(depositWithBonus); // Perform via mediator

			//check if the transaction contains the cashback log
			Account account = client1.getAccount(accountNumber); // Get the account of the client
			System.out.println("Transactions after life insurance deposit: " + account.getTransactions());

			//cdd an RON account for client1 (Ionescu Ion)
			Command addRonAccount = new AddAccountCommand(client1, Account.TYPE.RON, 300);
			mediator.performOperation(addRonAccount); // Add via mediator

			//perform a deposit on EUR account
			Command deposit150EUR = new DepositCommand(client1.getAccount(accountNumber), 150);
			mediator.performOperation(deposit150EUR); // Deposit via mediator

			//builder implementation usage demo(task2)
			// bank.Client 2: Marinescu Marin
			Client client2 = new Client.Builder()
					.withName("Marinescu Marin")
					.withAddress("Timisoara")
					.withInitialAccount(Account.TYPE.RON, 100)
					.build();
			mediator.addClient(client2); //add client via mediator
			System.out.println(client2);

			// Add an account for client2 (RON)
			Command addRONAccountClient2 = new AddAccountCommand(client2, Account.TYPE.RON, 200);
			mediator.performOperation(addRONAccountClient2);

			// Perform a deposit for client2's RON account
			Command depositRONClient2 = new DepositCommand(client2.getAccount(client2.getAccounts().get(0).getAccountNumber()), 50);
			mediator.performOperation(depositRONClient2);

			System.out.println("RON account balance (after deposit) for client2: " + client2.getAccount(client2.getAccounts().get(0).getAccountNumber()).getTotalAmount());

			// bank.Client 3: Evelin Miller
			Client client3 = new Client.Builder()
					.withName("Evelin Miller")
					.withAddress("Timisoara")
					.withInitialAccount(Account.TYPE.RON, 1000)
					.build();
			mediator.addClient(client3);  // Add client via mediator
			System.out.println(client3);

			// Remove client3 Evelin Miller
			mediator.removeClient(client3);

			// Add RON account for client1 (Ionescu Ion)
			Command addRonAccountClient1 = new AddAccountCommand(client1, Account.TYPE.RON, 300);
			mediator.performOperation(addRonAccountClient1);

			// Perform some operations on client1's EUR account
			Account client1EURAccount = client1.getAccount(client1.getAccounts().get(0).getAccountNumber());
			Command deposit150Client1EUR = new DepositCommand(client1EURAccount, 150);
			mediator.performOperation(deposit150Client1EUR);

			System.out.println("EUR account balance (after deposit): " + client1EURAccount.getTotalAmount());

			// operations.Transfer between RON accounts of clients Ionescu and Marinescu
			if (client2 != null) {
				Account account1RON = getRonAccount(client2);
				Account account2RON = getRonAccount(client1);

				// operations.Transfer 200 RON, assuming client2 has insufficient funds
				Command transferRON = new TransferCommand(account1RON, account2RON, 200);
				mediator.performOperation(transferRON);

				System.out.println("operations.Transfer complete. Updated balances:");
				System.out.println("bank.Client Ionescu Ion RON balance: " + account2RON.getTotalAmount());
				System.out.println("bank.Client Marinescu Marin RON balance: " + account1RON.getTotalAmount());
			}

			// Generate reports at the end of the day via mediator
			mediator.generateReports();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Account getRonAccount(Client client) {
		return client.getAccounts().stream()
				.filter(account -> account instanceof AccountRON)
				.findFirst()
				.orElse(null);
	}
}











