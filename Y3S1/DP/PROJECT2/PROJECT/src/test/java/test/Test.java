package test;

import accounts.Account;
import accounts.AccountRON;
import bank.Bank;
import bank.Client;

public class Test {

	public static void main(String[] args) {
		/**
		 * Create BCR bank with 2 clients
		 */
		Bank bcr = new Bank("Banca BCR");
		// creare client Ionescu cu 2 conturi unul in EUR si unul in RON
		Client cl1 = new Client("Ionescu Ion", "Timisoara", Account.TYPE.EUR, "EUR124", 200.9); // BUILDER PATTERN IMPLEMENTED
		bcr.addClient(cl1);
		cl1.addAccount(Account.TYPE.RON, "RON1234", 400);
		// creare client Marinecu cu un cont in RON
		Client cl2 = new Client("Marinescu Marin", "Timisoara", Account.TYPE.RON, "RON126", 100); // BUILDER PATTERN IMPLEMENTED
		bcr.addClient(cl2);
		System.out.println(bcr);

		/**
		 * Create bank CEC with one client
		 */
		Bank cec = new Bank("Banca CEC");
		Client clientCEC = new Client("Vasilescu Vasile", "Brasov", Account.TYPE.EUR, "EUR128", 700); // BUILDER PATTERN IMPLEMENTED
		cec.addClient(clientCEC);
		System.out.println(cec);

		/**
		 * Lab2(Implementaiton) - The builder pattern is used in the Client constructor to simplify the creation of Client
		 * objects with required details such as name, address, account type, account code, and an initial balance.
		 * This ensures clients are created with valid attributes and initialized accounts.
		 */
		// depose in account RON126 of client Marinescu
		Client cl = bcr.getClient("Marinescu Marin");
		if (cl != null) {
			cl.getAccount("RON126").depose(400); //OPERATIONS INTERFACE IMPLEMENTED
			System.out.println(cl);
		}

		// retrieve from account RON126 of Marinescu
		if (cl != null) {
			cl.getAccount("RON126").retrieve(67); // EXCEPTION HANDLING FOR RETRIEVAL IMPLEMENTED
			System.out.println(cl);
		}

		// tranfer between accounts RON126 si RON1234
		AccountRON c1 = (AccountRON) cl.getAccount("RON126");
		AccountRON c2 = (AccountRON) bcr.getClient("Ionescu Ion").getAccount("RON1234");
		c1.transfer(c2, 40); // TRANSFER FUNCTIONALITY IMPLEMENTED
		/**
		 The transfer method in the Transfer interface allows moving funds between two accounts.
		 This implementation ensures proper validation of amounts, updates balances for both accounts,
		 and adheres to the defined contract in the interface.
		 */
		System.out.println(bcr);
	}
}
