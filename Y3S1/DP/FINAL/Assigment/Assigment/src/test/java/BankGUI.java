import accounts.Account;
import bank.Bank;
import bank.BankMediator;
import bank.Client;
import commands.AddAccountCommand;
import commands.DepositCommand;
import commands.TransferCommand;
import operations.Command;
import operations.Mediator;
import singleton.DailyClientChangesReport;
import singleton.DailyOperationsReport;
import singleton.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BankGUI extends JFrame {
    private Mediator mediator;
    private JTextArea outputArea;
    private List<String> operationHistory;
    private Logger logger;

    public BankGUI() {
        Bank bank = new Bank("Banca BCR");
        mediator = new BankMediator(bank);
        operationHistory = new ArrayList<>();
        logger = Logger.getInstance();

        setTitle("Bank Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        JButton addClientButton = new JButton("Add Client");
        addClientButton.addActionListener(e -> handleAddClient());
        controlPanel.add(addClientButton);

        JButton addAccountButton = new JButton("Add Account");
        addAccountButton.addActionListener(e -> handleAddAccount());
        controlPanel.add(addAccountButton);

        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(e -> handleDeposit());
        controlPanel.add(depositButton);

        JButton transferButton = new JButton("Transfer");
        transferButton.addActionListener(e -> handleTransfer());
        controlPanel.add(transferButton);

        JButton generateReportsButton = new JButton("Generate Reports");
        generateReportsButton.addActionListener(e -> handleGenerateReports());
        controlPanel.add(generateReportsButton);

        add(controlPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private void handleAddClient() {
        String name = JOptionPane.showInputDialog(this, "Enter Client Name:");
        String address = JOptionPane.showInputDialog(this, "Enter Client Address:");

        if (name == null || address == null || name.trim().isEmpty() || address.trim().isEmpty()) {
            showMessage("Error: Name and address are required.");
            return;
        }

        Client client = new Client.Builder()
                .withName(name)
                .withAddress(address)
                .build();

        mediator.addClient(client);
        String message = "Client added: " + client.getName();
        showMessage(message);
        logOperation(message);
    }

    private void handleAddAccount() {
        String clientName = JOptionPane.showInputDialog(this, "Enter Client Name:");
        Client client = findClientByName(clientName);

        if (client == null) {
            showMessage("Error: Client not found.");
            return;
        }

        String accountType = JOptionPane.showInputDialog(this, "Enter Account Type (RON/EUR):");
        if (!"RON".equalsIgnoreCase(accountType) && !"EUR".equalsIgnoreCase(accountType)) {
            showMessage("Error: Invalid account type.");
            return;
        }

        double initialDeposit = 0;
        try {
            initialDeposit = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter Initial Deposit:"));
        } catch (NumberFormatException e) {
            showMessage("Error: Invalid deposit amount.");
            return;
        }

        Command addAccountCommand = new AddAccountCommand(client, Account.TYPE.valueOf(accountType.toUpperCase()), initialDeposit);
        mediator.performOperation(addAccountCommand);

        Account newAccount = client.getAccounts().get(client.getAccounts().size() - 1);
        String message = "Account added for client: " + client.getName() + ". Account Number: " + newAccount.getAccountNumber() + ", Balance: " + newAccount.getTotalAmount();
        showMessage(message);
        logOperation(message);
    }

    private void handleDeposit() {
        String clientName = JOptionPane.showInputDialog(this, "Enter Client Name:");
        Client client = findClientByName(clientName);

        if (client == null) {
            showMessage("Error: Client not found.");
            return;
        }

        String accountNumber = JOptionPane.showInputDialog(this, "Enter Account Number:");
        Account account = client.getAccount(accountNumber);

        if (account == null) {
            showMessage("Error: Account not found.");
            return;
        }

        double amount = 0;
        try {
            amount = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter Deposit Amount:"));
        } catch (NumberFormatException e) {
            showMessage("Error: Invalid deposit amount.");
            return;
        }

        Command depositCommand = new DepositCommand(account, amount);
        mediator.performOperation(depositCommand);

        String message = "Deposited " + amount + " into account " + accountNumber + ". Current Balance: " + account.getTotalAmount();
        showMessage(message);
        logOperation(message);
    }

    private void handleTransfer() {
        String fromClientName = JOptionPane.showInputDialog(this, "Enter Source Client Name:");
        Client fromClient = findClientByName(fromClientName);

        if (fromClient == null) {
            showMessage("Error: Source client not found.");
            return;
        }

        String toClientName = JOptionPane.showInputDialog(this, "Enter Destination Client Name:");
        Client toClient = findClientByName(toClientName);

        if (toClient == null) {
            showMessage("Error: Destination client not found.");
            return;
        }

        String fromAccountNumber = JOptionPane.showInputDialog(this, "Enter Source Account Number:");
        Account fromAccount = fromClient.getAccount(fromAccountNumber);

        String toAccountNumber = JOptionPane.showInputDialog(this, "Enter Destination Account Number:");
        Account toAccount = toClient.getAccount(toAccountNumber);

        double amount = 0;
        try {
            amount = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter Transfer Amount:"));
        } catch (NumberFormatException e) {
            showMessage("Error: Invalid transfer amount.");
            return;
        }

        double fromInitialBalance = fromAccount.getTotalAmount();
        double toInitialBalance = toAccount.getTotalAmount();

        Command transferCommand = new TransferCommand(fromAccount, toAccount, amount);
        mediator.performOperation(transferCommand);

        double fromNewBalance = fromAccount.getTotalAmount();
        double toNewBalance = toAccount.getTotalAmount();

        String message = "Transferred " + amount + " from " + fromAccountNumber + " to " + toAccountNumber + ".\n" +
                "Source Account Balance: Before: " + fromInitialBalance + ", After: " + fromNewBalance + ".\n" +
                "Destination Account Balance: Before: " + toInitialBalance + ", After: " + toNewBalance;
        showMessage(message);
        logOperation(message);
    }

    private void handleGenerateReports() {
        if (operationHistory.isEmpty()) {
            showMessage("No operations performed yet.");
        } else {
            showMessage("Operations History:");
            for (String operation : operationHistory) {
                logger.log(operation);
            }
        }
    }

    private void logOperation(String operation) {
        operationHistory.add(operation);
        logger.log(operation);
    }

    private Client findClientByName(String name) {
        return mediator instanceof BankMediator ?
                mediator.getBank().getClients().stream()
                        .filter(client -> client.getName().equalsIgnoreCase(name))
                        .findFirst()
                        .orElse(null)
                : null;
    }

    private void showMessage(String message) {
        outputArea.append(message + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankGUI gui = new BankGUI();
            gui.setVisible(true);
        });
    }
}
