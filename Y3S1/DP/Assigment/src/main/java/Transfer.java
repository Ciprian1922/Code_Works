public interface Transfer {
	void transfer(Account target, double amount) throws InsufficientFundsException, InvalidAmountException;
}
