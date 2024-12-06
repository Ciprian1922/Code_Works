package bank;

import operations.Command;
import operations.Mediator;
import singleton.DailyOperationsReport;
import singleton.DailyClientChangesReport;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bank {
	private String name;
	private List<Client> clients;
	private DailyOperationsReport operationsReport;
	private DailyClientChangesReport clientChangesReport;
	private Logger logger;
	private Mediator mediator;

	//usage for the singleton logger
	public Bank(String name) {
		this.name = name;
		this.clients = new ArrayList<>();
		this.operationsReport = new DailyOperationsReport();
		this.clientChangesReport = new DailyClientChangesReport();
		this.logger = Logger.getLogger(Bank.class.getName()); // Initialize Logger
	}

	public void setMediator(Mediator mediator) {
		this.mediator = mediator; //allow setting the mediator after bank.Bank initialization
	}

	public void addClient(Client client) {
		clients.add(client);
		clientChangesReport.addClientChange("Added client: " + client.getName());
		logger.info("Added client: " + client.getName()); //use Logger's info method
		operationsReport.addOperation("Added client: " + client.getName());
	}

	public void removeClient(Client client) {
		clients.remove(client);
		clientChangesReport.addClientChange("Removed client: " + client.getName());
		logger.info("Removed client: " + client.getName()); //use Logger's info method
		operationsReport.addOperation("Removed client: " + client.getName());
	}

	public void performOperation(Command command) {
		try {
			command.execute();
			operationsReport.addOperation("Executed command: " + command.toString());
			logger.info("Performed operation: " + command.toString()); //use Logger's info method
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error performing operation: " + e.getMessage(), e); //log with level and exception
		}
	}

	public void generateReports() {
		operationsReport.generateReport();
		clientChangesReport.generateReport();
	}

	public List<Client> getClients() {
		return clients;
	}

	@Override
	public String toString() {
		return "Bank [name=" + name + ", clients=" + clients + "]";
	}
}
