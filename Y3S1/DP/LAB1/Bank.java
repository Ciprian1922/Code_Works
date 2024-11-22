package ro.uvt.dp;

import java.util.ArrayList;

import java.util.List;


public class Bank {
	private List<Client> clients;
	private String bankCode;

	public Bank(String codBanca) {
		this.bankCode = codBanca;
		this.clients = new ArrayList<>();   //LAB1 Task(List)
	}

	public void addClient(Client c) {
		clients.add(c);
	}

	public Client getClient(String name) {
		for (Client client : clients) {
			if (client.getName().equals(name)) {
				return client;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "Bank [code=" + bankCode + ", clients=" + clients + "]";
	}
}
