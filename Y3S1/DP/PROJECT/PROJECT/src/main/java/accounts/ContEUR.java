package accounts;

public class ContEUR extends Account {

	public ContEUR(String numarCont, double suma) {
		super(numarCont, suma);
	}

	public double getInterest() {
		return 0.01;  //STATIC INTEREST RATE FOR EUR ACCOUNTS
	}

	@Override
	public String toString() {
		return "accounts.Account EUR [" + super.toString() + "]";
	}
}
