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
		Client cl1 = new Client("Ionescu Ion", "Timisoara", Account.TYPE.EUR, "EUR124", 200.9);
		bcr.addClient(cl1);
		cl1.addAccount(Account.TYPE.RON, "RON1234", 400);
		// creare client Marinecu cu un cont in RON
		Client cl2 = new Client("Marinescu Marin", "Timisoara", Account.TYPE.RON, "RON126", 100);
		bcr.addClient(cl2);
		System.out.println(bcr);

		/**
		 * Create bank CEC with one client
		 */
		Bank cec = new Bank("Banca CEC");
		Client clientCEC = new Client("Vasilescu Vasile", "Brasov", Account.TYPE.EUR, "EUR128", 700);
		cec.addClient(clientCEC);
		System.out.println(cec);

		//depose in account RON126 of client Marinescu
		Client cl = bcr.getClient("Marinescu Marin");
		if (cl != null) {
			cl.getAccount("RON126").depose(400);
			System.out.println(cl);
		}

		//retrieve from account RON126 of Marinescu
		if (cl != null) {
			cl.getAccount("RON126").retrieve(67);
			System.out.println(cl);
		}

		//tranfer between accounts RON126 si RON1234
		AccountRON c1 = (AccountRON) cl.getAccount("RON126");
		AccountRON c2 = (AccountRON) bcr.getClient("Ionescu Ion").getAccount("RON1234");
		c1.transfer(c2, 40);
		System.out.println(bcr);

	}

}
