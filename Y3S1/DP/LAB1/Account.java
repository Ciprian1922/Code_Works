package ro.uvt.dp;

public abstract class Account implements Operations {

	protected String accountCode = null;
	protected double amount = 0;

	public static enum TYPE {
		EUR, RON
	};

	protected Account(String numarCont, double suma) {
		this.accountCode = numarCont;
		depose(suma);
	}

	@Override
	public double getTotalAmount() {

		return amount + amount * getInterest();
	}

	@Override    //LAB1(THROW EXCEPTION)
	public void depose(double amount) throws IllegalArgumentException {
		if (amount <= 0) {
			throw new IllegalArgumentException("Deposit amount must be positive.");
		}
		this.amount += amount;
	}

	@Override    //LAB1(THROW EXCEPTION)
	public void retrieve(double amount) throws IllegalArgumentException {
		if (amount <= 0) {
			throw new IllegalArgumentException("Retrieve amount must be positive.");
		} else if (amount > this.amount) {
			throw new IllegalArgumentException("Insufficient balance for this operation.");
		}
		this.amount -= amount;
	}

	public String toString() {
		return "Account: code=" + accountCode + ", amount=" + amount;
	}

	public String getAccountNumber() {
		return accountCode;
	}

}
