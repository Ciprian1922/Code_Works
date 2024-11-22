public class AccountEUR extends Account implements Transfer {
    public AccountEUR(String accountNumber, double initialBalance) {
        super(accountNumber, initialBalance);
    }

    @Override
    public double getInterest() {
        return 0.02;  // 2% interest rate
    }

    @Override
    public void transfer(Account target, double amount) throws InsufficientFundsException, InvalidAmountException {
        this.retrieve(amount);
        target.depose(amount);
        transactions.add("Transferred: " + amount + " to " + target.getAccountNumber());
    }

}
