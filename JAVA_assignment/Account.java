package test1;

//import test1.java_oops.Customer;

public class Account {
	
	 protected String accNum, accType;
	    protected double balance;
	    private static final double INTEREST_RATE = 4.5;

	    public Account() {}
	    public Account(String accNum, String accType, double balance) {
	        this.accNum = accNum;
	        this.accType = accType;
	        this.balance = balance;
	    }

	    public void deposit(double amt) {
	        balance += amt;
	        System.out.println("Deposited: $" + amt + " | New Balance: $" + balance);
	    }

	    public void withdraw(double amt) {
	        if (amt > balance) {
	            System.out.println("Insufficient Balance.");
	        } else {
	            balance -= amt;
	            System.out.println("Withdrawn: $" + amt + " | New Balance: $" + balance);
	        }
	    }

	    public void calculateInterest() {
	        double interest = balance * (INTEREST_RATE / 100);
	        balance += interest;
	        System.out.printf("Interest Added: $%.2f | New Balance: $%.2f\n", interest, balance);
	    }

	    public void displayInfo() {
	        System.out.println("Account: " + accNum + " (" + accType + ") | Balance: $" + balance);
	    }
	}

	class SavingsAccount extends Account {
	    public SavingsAccount(String accNum, double balance) {
	        super(accNum, "Savings", balance);
	    }
	    @Override
	    public void calculateInterest() {
	        super.calculateInterest();
	    }
	}

	class CurrentAccount extends Account {
	    private static final double OVERDRAFT_LIMIT = 2000;

	    public CurrentAccount(String accNum, double balance) {
	        super(accNum, "Current", balance);
	    }
	    @Override
	    public void withdraw(double amt) {
	        if (amt > balance + OVERDRAFT_LIMIT) {
	            System.out.println("Overdraft limit exceeded.");
	        } else {
	            balance -= amt;
	            System.out.println("Withdrawn: $" + amt + " | New Balance: $" + balance);
	        }
	    }
	

}
