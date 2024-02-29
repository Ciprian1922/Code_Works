public class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public synchronized void deposit(double amount) {
        balance += amount;
        System.out.println("Deposit: " + amount + ", New Balance: " + balance);
    }

    public synchronized void retrieve(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Retrieve: " + amount + ", New Balance: " + balance);
        } else {
            System.out.println("Insufficient funds for retrieve: " + amount);
        }
    }

    public double getBalance() {
        return balance;
    }
}
