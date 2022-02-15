package put.bankapp.models;

public class SavingAccount extends Account {
    private final static double interestRate = 0.12;

    public SavingAccount(String accountOwner, String accountOwnerAge, double balance, CardID cardID) {
        super(accountOwner, accountOwnerAge, 2, balance, cardID);
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

    public static double getInterestRate() {
        return interestRate;
    }

}
