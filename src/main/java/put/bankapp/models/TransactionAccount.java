package put.bankapp.models;

public class TransactionAccount extends Account {

    private final static double PERCENTAGE = 2.5;

    public TransactionAccount(String accountOwner, String accountOwnerAge, double balance, CardID cardID) {
        super(accountOwner, accountOwnerAge, 1, balance, cardID);
    }

    @Override
    public boolean withdraw(double money) {
        if (this.getBalance() - money < 0) {
            return false;
        } else {
            this.setBalance(this.getBalance() - money);
            return true;
        }
    }

    @Override
    public boolean deposit(double money) {
        this.setBalance(this.getBalance() + money);
        return true;
    }

    public static double getPERCENTAGE() {
        return PERCENTAGE;
    }

}
