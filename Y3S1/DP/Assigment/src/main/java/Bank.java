import java.util.ArrayList;
import java.util.List;

public class Bank {
	private String name;
	private List<Client> clients;
	private DailyOperationsReport operationsReport;
	private DailyClientChangesReport clientChangesReport;
	private Logger logger;

	public Bank(String name) {
		this.name = name;
		this.clients = new ArrayList<>();
		this.operationsReport = new DailyOperationsReport();
		this.clientChangesReport = new DailyClientChangesReport();
		this.logger = Logger.getInstance();
	}

	public void addClient(Client client) {
		clients.add(client);
		clientChangesReport.addClientChange("Added client: " + client.getName());
		logger.log("Added client: " + client.getName());
	}

	public void removeClient(Client client) {
		clients.remove(client);
		clientChangesReport.addClientChange("Removed client: " + client.getName());
		logger.log("Removed client: " + client.getName());
	}

	public void performOperation(String operation) {
		operationsReport.addOperation(operation);
		logger.log(operation);
	}

	public void generateReports() {
		operationsReport.generateReport();
		clientChangesReport.generateReport();
	}

	@Override
	public String toString() {
		return "Bank [name=" + name + ", clients=" + clients + "]";
	}
}
