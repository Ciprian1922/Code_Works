package accounts;


//refactor account using dp
public class AccountFactory {
    public static Account createAccount(Account.TYPE type, String accountNumber, double initialBalance) {
        switch (type) {
            case EUR:
                return new AccountEUR(accountNumber, initialBalance);
            case RON:
                return new AccountRON(accountNumber, initialBalance);
            default:
                throw new IllegalArgumentException("Unsupported account type: " + type);
        }
    }
}


