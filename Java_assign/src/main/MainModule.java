package main;

import dao.*;
import entity.*;
import util.*;
import java.util.*;
import java.sql.*;
import java.util.Scanner;
public class MainModule {
	
	    public static void main(String[] args) {
	        // Create DB Tables
	        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");
	             Statement stmt = conn.createStatement()) {

	            String customerTable = "CREATE TABLE IF NOT EXISTS Customer ("
	                    + "customerId VARCHAR(20) PRIMARY KEY,"
	                    + "firstName VARCHAR(50),"
	                    + "lastName VARCHAR(50),"
	                    + "email VARCHAR(100),"
	                    + "phone VARCHAR(15),"
	                    + "address VARCHAR(255))";

	            String accountTable = "CREATE TABLE IF NOT EXISTS Account ("
	                    + "accNum VARCHAR(20) PRIMARY KEY,"
	                    + "accType VARCHAR(20),"
	                    + "balance DOUBLE,"
	                    + "customerId VARCHAR(20),"
	                    + "FOREIGN KEY (customerId) REFERENCES Customer(customerId))";

	            stmt.execute(customerTable);
	            stmt.execute(accountTable);

	            System.out.println("Tables created successfully.");

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        // Input data at runtime
	        Scanner sc = new Scanner(System.in);

	        System.out.println("Enter Customer Details:");
	        System.out.print("Customer ID: ");
	        String custId = sc.nextLine();
	        System.out.print("First Name: ");
	        String fname = sc.nextLine();
	        System.out.print("Last Name: ");
	        String lname = sc.nextLine();
	        System.out.print("Email: ");
	        String email = sc.nextLine();
	        System.out.print("Phone: ");
	        String phone = sc.nextLine();
	        System.out.print("Address: ");
	        String address = sc.nextLine();

	        Customer customer = new Customer(custId, fname, lname, email, phone, address);
	        customer.displayInfo();

	        System.out.println("\nEnter Account Details:");
	        System.out.print("Account Number: ");
	        String accNum = sc.nextLine();
	        System.out.print("Account Type (Savings/Current): ");
	        String accType = sc.nextLine();
	        System.out.print("Initial Balance: ");
	        double balance = sc.nextDouble();

	        Account account = new Account(accNum, accType, balance);
	        account.displayInfo();

	        System.out.print("\nEnter deposit amount: ");
	        double deposit = sc.nextDouble();
	        account.deposit(deposit);

	        System.out.print("Enter withdrawal amount: ");
	        double withdraw = sc.nextDouble();
	        account.withdraw(withdraw);

	        account.calculateInterest();
	        account.displayInfo();
	    }
	}


