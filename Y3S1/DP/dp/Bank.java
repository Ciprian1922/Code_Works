package ro.uvt.dp;

import java.util.Arrays;

public class Bank {

	private final static int MAX_CLIENTS_NUMBER = 100;
	private Client clients[];
	private int clientsNumber;
	private String bankCode = null;

	public Bank(String codBanca) {
		this.bankCode = codBanca;
		clients = new Client[MAX_CLIENTS_NUMBER];
	}

	public void addClient(Client c) {
		clients[clientsNumber++] = c;
	}

	
	public Client getClient(String nume) {
		for (int i = 0; i < clientsNumber; i++) {
			if (clients[i].getName().equals(nume)) {
				return clients[i];
			}
		}
		return null;
	}
	@Override
	public String toString() {
		return "Bank [code=" + bankCode + ", clients=" + Arrays.toString(clients) + "]";
	}

}
