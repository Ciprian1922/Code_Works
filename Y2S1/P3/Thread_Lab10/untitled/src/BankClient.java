public class BankClient extends Thread {
    private BankAccount bankAccount;

    public BankClient(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public void run() {
        // Simulate some deposit and retrieve operations
        for (int i = 0; i < 5; i++) {
            double amount = Math.random() * 100; // Random amount for deposit/retrieve
            if (Math.random() < 0.5) {
                bankAccount.deposit(amount);
            } else {
                bankAccount.retrieve(amount);
            }
            try {
                Thread.sleep(100); // Simulate some processing time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

