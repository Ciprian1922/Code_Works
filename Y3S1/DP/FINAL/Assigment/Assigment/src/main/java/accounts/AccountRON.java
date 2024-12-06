package accounts;

import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;
import operations.Transfer;

public class AccountRON extends Account implements Transfer {
	public AccountRON(String accountNumber, double initialBalance) {
		super(accountNumber, initialBalance);
	}

	@Override
	public double getInterest() {
		return 0.01;  // 1% interest rate
	}

	@Override
	public void transfer(Account target, double amount) throws InsufficientFundsException, InvalidAmountException {
		this.retrieve(amount);
		target.depose(amount);
		transactions.add("Transferred: " + amount + " to " + target.getAccountNumber());
	}
}