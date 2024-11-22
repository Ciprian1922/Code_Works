package ro.uvt.dp;

import java.util.Arrays;

import ro.uvt.dp.Account.TYPE;

public class Client {
	public static final int NUMAR_MAX_CONTURI = 5;

	private String name;
	private String address;
	private Account accounts[];
	private int accountsNr = 0;

	public Client(String nume, String adresa, TYPE tip, String numarCont, double suma) {
		this.name = nume;
		this.address = adresa;
		accounts = new Account[NUMAR_MAX_CONTURI];
		addAccount(tip, numarCont, suma);
	}

	public void addAccount(TYPE tip, String numarCont, double suma) {
		Account c = null;
		if (tip == Account.TYPE.EUR)
			c = new ContEUR(numarCont, suma);
		else if (tip == Account.TYPE.RON)
			c = new AccountRON(numarCont, suma);
		accounts[accountsNr++] = c;
	}

	public Account getAccount(String accountCode) {
		for (int i = 0; i < accountsNr; i++) {
			if (accounts[i].getAccountNumber().equals(accountCode)) {
				return accounts[i];
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "\n\tClient [name=" + name + ", address=" + address + ", acounts=" + Arrays.toString(accounts) + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String nume) {
		this.name = nume;
	}
}
