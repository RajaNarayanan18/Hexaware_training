package test1;
import java.util.*;

public class java_oops {

	class Customer {
	    private String custID, fName, lName, email, phone, address;

	    public Customer() {}
	    public Customer(String custID, String fName, String lName, String email, String phone, String address) {
	        this.custID = custID;
	        this.fName = fName;
	        this.lName = lName;
	        this.email = email;
	        this.phone = phone;
	        this.address = address;
	    }
	    public void displayInfo() {
	        System.out.println("Customer: " + fName + " " + lName + ", ID: " + custID);
	    }
	}

	class Account {
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
	
	    public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);
	        
	        System.out.print("Enter Customer ID: ");
	        String custID = sc.next();
	        System.out.print("Enter First Name: ");
	        String fName = sc.next();
	        System.out.print("Enter Last Name: ");
	        String lName = sc.next();
	        System.out.print("Enter Email: ");
	        String email = sc.next();
	        System.out.print("Enter Phone: ");
	        String phone = sc.next();
	        System.out.print("Enter Address: ");
	        sc.nextLine(); // consume newline
	        String address = sc.nextLine();

	        Customer customer = new Customer(custID, fName, lName, email, phone, address);
	        customer.displayInfo();

	        System.out.println("Choose Account Type: 1. Savings 2. Current");
	        int choice = sc.nextInt();
	        System.out.print("Enter Acc Number: ");
	        String accNum = sc.next();
	        System.out.print("Enter Initial Balance: ");
	        double balance = sc.nextDouble();

	        Account acc;
	        if (choice == 1) acc = new SavingsAccount(accNum, balance);
	        else acc = new CurrentAccount(accNum, balance);

	        while (true) {
	            System.out.println("1. Deposit 2. Withdraw 3. Interest 4. Exit");
	            int ch = sc.nextInt();
	            if (ch == 1) {
	                System.out.print("Deposit Amt: ");
	                acc.deposit(sc.nextDouble());
	            } else if (ch == 2) {
	                System.out.print("Withdraw Amt: ");
	                acc.withdraw(sc.nextDouble());
	            } else if (ch == 3 && acc instanceof SavingsAccount) {
	                acc.calculateInterest();
	            } else if (ch == 4) break;
	            else System.out.println("Invalid Option.");
	        }
	    }
	}


	

	
	


