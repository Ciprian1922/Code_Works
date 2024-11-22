public class Test {
	public static void main(String[] args) {
		try {
			Bank bank = new Bank("Banca BCR");

			Client client1 = new Client.Builder()
					.withName("Ionescu Ion")
					.withAddress("Timisoara")
					.withInitialAccount(Account.TYPE.EUR, 200.9)
					.build();
			bank.addClient(client1);
			bank.performOperation("Client Ionescu Ion added.");
			System.out.println(client1);

			Client client2 = new Client.Builder()
					.withName("Marinescu Marin")
					.withAddress("Timisoara")
					.withInitialAccount(Account.TYPE.RON, 100)
					.build();
			bank.addClient(client2);
			bank.performOperation("Client Marinescu Marin added.");
			System.out.println(client2);

			Client client3 = new Client.Builder()
					.withName("Evelin Miller")
					.withAddress("Timisoara")
					.withInitialAccount(Account.TYPE.RON, 1000)
					.build();
			bank.addClient(client3);
			bank.performOperation("Client Evelin Miller added.");
			System.out.println(client3);

			// Remove client3 Evelin Miller
			bank.removeClient(client3);
			bank.performOperation("Client Evelin Miller removed");

			// Add an RON account for client1 (Ionescu Ion)
			client1.addAccount(Account.TYPE.RON, 300);
			bank.performOperation("Added RON account for client Ionescu Ion.");

			// Perform some operations on client1
			client1.getAccount(client1.getAccounts().get(0).getAccountNumber()).depose(150);
			bank.performOperation("Deposited 150 to EUR account of client Ionescu Ion.");

			client1.getAccount(client1.getAccounts().get(0).getAccountNumber()).retrieve(50);
			bank.performOperation("Withdrew 50 from EUR account of client Ionescu Ion.");

			// Transfer between RON accounts of clients Ionescu and Marinescu
			if (client2 != null) {
				try {
					Account account1 = getRonAccount(client2); // Get an RON account of client2
					Account account2 = getRonAccount(client1); // Get an RON account of client1

					// Check if both accounts are of type AccountRON before transferring
					if (account1 instanceof AccountRON && account2 instanceof AccountRON) {
						AccountRON c1 = (AccountRON) account1;
						AccountRON c2 = (AccountRON) account2;
						c1.transfer(c2, 40); // Transfer 40 RON from Marinescu to Ionescu
						bank.performOperation("Transferred 40 RON from client Marinescu Marin to client Ionescu Ion.");
						System.out.println(bank);
					} else {
						System.err.println("Error: One or both accounts are not of type AccountRON.");
					}
				} catch (InsufficientFundsException | InvalidAmountException e) {
					System.err.println("Error: " + e.getMessage());
				}
			}

			// Generate reports at the end of the day
			bank.generateReports();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Account getRonAccount(Client client) {
		// Find the first RON account or return null if not found
		return client.getAccounts().stream()
				.filter(account -> account instanceof AccountRON)
				.findFirst()
				.orElse(null);
	}
}



