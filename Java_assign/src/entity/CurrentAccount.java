package entity;


public class CurrentAccount extends Account {
    private double overdraftLimit;

    public CurrentAccount(String accNum, double balance, double overdraftLimit) {
        super(accNum, "Current", balance);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() { return overdraftLimit; }
}


