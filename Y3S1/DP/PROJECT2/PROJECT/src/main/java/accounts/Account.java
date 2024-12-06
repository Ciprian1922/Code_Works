package accounts;

import exceptions.DepositAmountException;
import exceptions.RetrieveAmountException;
import operations.Operations;

public abstract class Account implements Operations {

	protected String accountCode = null;
	protected double amount = 0;

	public static enum TYPE {  //ENUM FOR ACCOUNT TYPES
		EUR, RON
	};

	protected Account(String numarCont, double suma) {
		this.accountCode = numarCont;
		depose(suma);
	}

	@Override
	public double getTotalAmount() {
		return amount + amount * getInterest(); //RETURNS AMOUNT WITH INTEREST
	}

	// Added method to return the balance without interest
	public double getAmount() {
		return this.amount;       //RETURNS BALANCE WITHOUT INTEREST
	}

	@Override // LAB1 (THROW EXCEPTION)
	public void depose(double amount) throws DepositAmountException {
		if (amount <= 0) {
			throw new DepositAmountException("Deposit amount must be positive.");
		}
		this.amount += amount;
	}

	@Override // LAB1 (THROW EXCEPTION)
	public void retrieve(double amount) throws RetrieveAmountException {
		if (amount <= 0) {
			throw new RetrieveAmountException("Retrieve amount must be positive.");
		} else if (amount > this.amount) {
			throw new RetrieveAmountException("Insufficient balance for this operation.");
		}
		this.amount -= amount;
	}

	@Override
	public String toString() {
		return "accounts.Account: code=" + accountCode + ", amount=" + amount;
	}

	public String getAccountNumber() {
		return accountCode;
	}

	//ABSTRACT METHOD FOR INTEREST CALCULATION
	public abstract double getInterest();
}
