package entity;


	public class SavingsAccount extends Account {
	    private double interestRate;

	    public SavingsAccount(String accNum, double balance, double interestRate) {
	        super(accNum, "Savings", balance);
	        this.interestRate = interestRate;
	    }

	    public double getInterestRate() { return interestRate; }
	}



