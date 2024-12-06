package bank;

import accounts.Account;
import accounts.AccountRON;
import accounts.ContEUR;

import java.util.ArrayList;
import java.util.List;

public class Client {
	private String name;
	private String address;
	private List<Account> accounts;    // LAB1 Task(List) - client holds multiple accounts

	public Client(String name, String address, Account.TYPE type, String accountCode, double amount) {
		this.name = name;
		this.address = address;
		this.accounts = new ArrayList<>();    // LAB1 Task(List)
		addAccount(type, accountCode, amount);
	}
	//client must be private and I have to add an inner class to create client objects()
	public void addAccount(Account.TYPE type, String accountCode, double amount) {
		Account account = (type == Account.TYPE.EUR) ? new ContEUR(accountCode, amount) : new AccountRON(accountCode, amount); // ABSTRACT FACTORY IMPLEMENTED
		/**
		 The addAccount method in the Client class uses an abstract factory design pattern to create instances of ContEUR or
		 AccountRON based on the account type (EUR or RON). This encapsulates the creation logic and makes the code more maintainable
		 and scalable for new account types.
		 */

		/**
		 * Logging for the entire application.
		 * Logs information to a file called bank_operations.log
		 */
		accounts.add(account);
	}

	public Account getAccount(String accountCode) {
		for (Account account : accounts) {
			if (account.getAccountNumber().equals(accountCode)) {
				return account;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "\n\tbank.Client [name=" + name + ", address=" + address + ", accounts=" + accounts + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String nume) {
		this.name = nume;
	}
}
