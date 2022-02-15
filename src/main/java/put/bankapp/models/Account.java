package put.bankapp.models;

public abstract class Account {
    private String accountOwner;
    private String accountOwnerAge;
    private int accountType; //1- transact, 2- saving
    private double balance;
    private CardID cardID;

    public Account(String accountOwner, String accountOwnerAge, int accountType, double balance, CardID cardID) {
        this.accountOwner = accountOwner;
        this.accountOwnerAge = accountOwnerAge;
        this.accountType = accountType;
        this.balance = balance;
        this.cardID = cardID;
    }

    public abstract boolean withdraw(double money);

    public abstract boolean deposit(double money);

    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    public void setAccountOwnerAge(String accountOwnerAge) {
        this.accountOwnerAge = accountOwnerAge;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setCardID(CardID cardID) {
        this.cardID = cardID;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public String getAccountOwnerAge() {
        return accountOwnerAge;
    }

    public int getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public CardID getCardID() {
        return cardID;
    }


}
