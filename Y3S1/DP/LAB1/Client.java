package ro.uvt.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ro.uvt.dp.Account.TYPE;

public class Client {
	private String name;
	private String address;
	private List<Account> accounts;    //LAB1 Task(List)

	public Client(String name, String address, TYPE type, String accountCode, double amount) {
		this.name = name;
		this.address = address;
		this.accounts = new ArrayList<>();    //LAB1 Task(List)
		addAccount(type, accountCode, amount);
	}

	public void addAccount(TYPE type, String accountCode, double amount) {
		Account account = (type == TYPE.EUR) ? new ContEUR(accountCode, amount) : new AccountRON(accountCode, amount);
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
		return "\n\tClient [name=" + name + ", address=" + address + ", accounts=" + accounts + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String nume) {
		this.name = nume;
	}
}
