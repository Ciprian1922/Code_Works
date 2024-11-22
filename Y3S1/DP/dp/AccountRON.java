package ro.uvt.dp;

public class AccountRON extends Account implements Transfer {

	public AccountRON(String numarCont, double suma) {
		super(numarCont, suma);
	}

	public double getInterest() {
		if (amount < 500)
			return 0.03;
		else
			return 0.08;

	}

	@Override
	public String toString() {
		return "Account RON [" + super.toString() + "]";
	}

	@Override
	public void transfer(Account c, double s) {
		c.retrieve(s);
		depose(s);
	}
}
