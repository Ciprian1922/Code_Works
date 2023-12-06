public class Mainn {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000);

        // Create multiple clients (threads)
        BankClient client1 = new BankClient(account);
        BankClient client2 = new BankClient(account);

        // Start the threads
        client1.start();
        client2.start();

        // Wait for threads to finish
        try {
            client1.join();
            client2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print final balance
        System.out.println("Final Balance: " + account.getBalance());
    }
}
