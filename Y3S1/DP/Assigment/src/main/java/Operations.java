public interface Operations {
	double getTotalAmount();
	double getInterest();
	void depose(double amount) throws InvalidAmountException;
	void retrieve(double amount) throws InsufficientFundsException, InvalidAmountException;
}


