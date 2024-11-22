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

	@Override
	public void depose(double suma) {

		this.amount += suma;
	}

	@Override
	public void retrieve(double suma) {

		this.amount -= suma;
	}

	public String toString() {
		return "Account: code=" + accountCode + ", amount=" + amount;
	}

	public String getAccountNumber() {
		return accountCode;
	}

}
