package test1;
import java.util.*;
import test1.Account;
import test1.Customer;


// Task 7 and 8
//Task 8: Inheritance and polymorphism


public class Banking_system {
	 public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);
	        // getting details of customer
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
	        sc.nextLine(); 
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
