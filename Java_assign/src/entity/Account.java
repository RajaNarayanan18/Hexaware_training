package entity;

public class Account {
    private String accNum;
    private String accType;
    private double balance;
    private static final double INTEREST_RATE = 4.5;

    public Account() {}

    public Account(String accNum, String accType, double balance) {
        this.accNum = accNum;
        this.accType = accType;
        this.balance = balance;
    }

    public String getAccNum() { return accNum; }
    public void setAccNum(String accNum) { this.accNum = accNum; }

    public String getAccType() { return accType; }
    public void setAccType(String accType) { this.accType = accType; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: " + amount + ", New Balance: " + balance);
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount + ", New Balance: " + balance);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void calculateInterest() {
        if (accType.equalsIgnoreCase("Savings")) {
            double interest = balance * INTEREST_RATE / 100;
            balance += interest;
            System.out.printf("Interest of %.2f added. New Balance: %.2f\n", interest, balance);
        } else {
            System.out.println("Interest calculation applicable only for Savings account.");
        }
    }

    public void displayInfo() {
        System.out.println("Account Number: " + accNum);
        System.out.println("Account Type: " + accType);
        System.out.println("Balance: " + balance);
    }
}



