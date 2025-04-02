package test1;
import java.util.Scanner;
import test1.Banking_system;

//TASK 9 ABSTRACTION

	// Abstract class BankAccount
	abstract class BankAccount {
	    protected String accountNumber;
	    protected String customerName;
	    protected double balance;

	    // Constructor
	    public BankAccount(String accountNumber, String customerName, double balance) {
	        this.accountNumber = accountNumber;
	        this.customerName = customerName;
	        this.balance = balance;
	    }

	    // Getter and Setter
	    public String getAccountNumber() { return accountNumber; }
	    public String getCustomerName() { return customerName; }
	    public double getBalance() { return balance; }

	    public void setBalance(double balance) { this.balance = balance; }

	    // Abstract methods
	    public abstract void deposit(double amount);
	    public abstract void withdraw(double amount);
	    public abstract void calculateInterest();
	    
	    public void displayAccountInfo() {
	        System.out.println("Account Number: " + accountNumber);
	        System.out.println("Customer Name: " + customerName);
	        System.out.println("Balance: " + balance);
	    }
	}

	// SavingsAccount class
	class SavingAccount extends BankAccount {
	    private double interestRate;

	    public SavingAccount(String accountNumber, String customerName, double balance, double interestRate) {
	        super(accountNumber, customerName, balance);
	        this.interestRate = interestRate;
	    }

	    @Override
	    public void deposit(double amount) {
	        balance += amount;
	        System.out.println("Deposited " + amount + " into Savings Account. New Balance: " + balance);
	    }

	    @Override
	    public void withdraw(double amount) {
	        if (amount <= balance) {
	            balance -= amount;
	            System.out.println("Withdrawn " + amount + " from Savings Account. New Balance: " + balance);
	        } else {
	            System.out.println("Insufficient Balance!");
	        }
	    }

	    @Override
	    public void calculateInterest() {
	        double interest = balance * (interestRate / 100);
	        balance += interest;
	        System.out.println("Interest added: " + interest + ". New Balance: " + balance);
	    }
	}

	// CurrentAccount class
	class CurAccount extends BankAccount {
	    private static final double OVERDRAFT_LIMIT = 5000;

	    public CurAccount(String accountNumber, String customerName, double balance) {
	        super(accountNumber, customerName, balance);
	    }

	    @Override
	    public void deposit(double amount) {
	        balance += amount;
	        System.out.println("Deposited " + amount + " into Current Account. New Balance: " + balance);
	    }

	    @Override
	    public void withdraw(double amount) {
	        if (balance - amount >= -OVERDRAFT_LIMIT) {
	            balance -= amount;
	            System.out.println("Withdrawn " + amount + " from Current Account. New Balance: " + balance);
	        } else {
	            System.out.println("Overdraft limit exceeded!");
	        }
	    }

	    @Override
	    public void calculateInterest() {
	        System.out.println("Current Accounts do not earn interest.");
	    }
	}

	// Bank class for operations
	class Bank {
	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        BankAccount account = null;

	        System.out.println("Choose Account Type: 1. Savings Account 2. Current Account");
	        int choice = scanner.nextInt();
	        scanner.nextLine();

	        System.out.print("Enter Account Number: ");
	        String accNo = scanner.nextLine();
	        System.out.print("Enter Customer Name: ");
	        String name = scanner.nextLine();
	        System.out.print("Enter Initial Balance: ");
	        double balance = scanner.nextDouble();

	        if (choice == 1) {
	            System.out.print("Enter Interest Rate: ");
	            double rate = scanner.nextDouble();
	            account = new SavingAccount(accNo, name, balance, rate);
	        } else if (choice == 2) {
	            account = new CurAccount(accNo, name, balance);
	        }

	        if (account != null) {
	            System.out.println("Account created successfully!");
	            account.displayAccountInfo();
	        }

	        while (true) {
	            System.out.println("\nChoose Operation: 1. Deposit 2. Withdraw 3. Calculate Interest 4. Exit");
	            int op = scanner.nextInt();

	            if (op == 1) {
	                System.out.print("Enter deposit amount: ");
	                double amount = scanner.nextDouble();
	                account.deposit(amount);
	            } else if (op == 2) {
	                System.out.print("Enter withdrawal amount: ");
	                double amount = scanner.nextDouble();
	                account.withdraw(amount);
	            } else if (op == 3) {
	                account.calculateInterest();
	            } else if (op == 4) {
	                System.out.println("Exiting... Thank you!");
	                break;
	            }
	        }
	        scanner.close();
	    }
	}



