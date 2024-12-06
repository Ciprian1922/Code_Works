package accounts;

import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;
import operations.Operations;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public abstract class Account implements Operations {
    protected String accountNumber;
    protected double balance;
    protected List<String> transactions;
    protected LocalDate lastInterestApplied;

    public enum TYPE { EUR, RON }

    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        this.lastInterestApplied = LocalDate.now();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public void depose(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive");
        }
        balance += amount;
        transactions.add("Deposited: " + amount);
    }

    @Override
    public void retrieve(double amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        balance -= amount;
        transactions.add("Withdrew: " + amount);
    }

    public void applyInterest() {
        LocalDate now = LocalDate.now();
        long daysElapsed = ChronoUnit.DAYS.between(lastInterestApplied, now);
        if (daysElapsed > 0) {
            double interestGained = balance * getInterest() * daysElapsed / 365;
            balance += interestGained;
            lastInterestApplied = now;
            transactions.add("Interest applied for " + daysElapsed + " days: " + interestGained);
        }
    }

    @Override
    public double getTotalAmount() {
        return balance;
    }

    public List<String> getTransactions() {
        return transactions;
    }

    @Override
    public abstract double getInterest();
}

