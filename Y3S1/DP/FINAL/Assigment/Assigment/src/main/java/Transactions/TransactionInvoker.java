package Transactions;

import operations.Command;

import java.util.ArrayList;
import java.util.List;
public class TransactionInvoker {
    private List<Command> commandHistory = new ArrayList<>();

    public void addCommand(Command command) {
        commandHistory.add(command);
    }

    public void executeCommands() {
        for (Command command : commandHistory) {
            command.execute();
        }
    }

    public void clearCommands() {
        commandHistory.clear();
    }
}
