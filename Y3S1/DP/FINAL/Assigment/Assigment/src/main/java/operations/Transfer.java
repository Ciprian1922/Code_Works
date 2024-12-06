package operations;

import accounts.Account;
import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;

public interface Transfer {
	void transfer(Account target, double amount) throws InsufficientFundsException, InvalidAmountException;
}
