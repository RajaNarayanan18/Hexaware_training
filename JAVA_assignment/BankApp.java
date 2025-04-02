package test1;
import java.sql.*;
import java.util.Scanner;

public class BankApp {
	

	    private static final String DB_URL = "jdbc:mysql://localhost:3306/bankdb";
	    private static final String USER = "root";  
	    private static final String PASSWORD = "Raja@2003"; 

	    public static Connection getDBConn() throws SQLException {
	        return DriverManager.getConnection("jdbc:mysql://localhost:3306/banking","root","Raja@2003");
	    }

	    public static void createTables() {
	        String createCustomers = "CREATE TABLE IF NOT EXISTS customers (" +
	                "custID VARCHAR(10) PRIMARY KEY, fName VARCHAR(50), " +
	                "lName VARCHAR(50), email VARCHAR(100), phone VARCHAR(15), address VARCHAR(255))";
	        
	        String createAccounts = "CREATE TABLE IF NOT EXISTS accounts (" +
	                "accNum BIGINT PRIMARY KEY AUTO_INCREMENT, accType VARCHAR(20), balance DOUBLE, " +
	                "custID VARCHAR(10), FOREIGN KEY (custID) REFERENCES customers(custID))";

	        try (Connection conn = getDBConn();
	             Statement stmt = conn.createStatement()) {
	            stmt.execute(createCustomers);
	            stmt.execute(createAccounts);
	            System.out.println("Tables created.");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void addCustomer(String id, String fName, String lName, String email, String phone, String address) {
	        String sql = "INSERT INTO customers (custID, fName, lName, email, phone, address) VALUES (?, ?, ?, ?, ?, ?)";
	        try (Connection conn = getDBConn();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, id);
	            stmt.setString(2, fName);
	            stmt.setString(3, lName);
	            stmt.setString(4, email);
	            stmt.setString(5, phone);
	            stmt.setString(6, address);
	            stmt.executeUpdate();
	            System.out.println("Customer added.");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void addAccount(String accType, double balance, String custID) {
	        String sql = "INSERT INTO accounts (accType, balance, custID) VALUES (?, ?, ?)";
	        try (Connection conn = getDBConn();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, accType);
	            stmt.setDouble(2, balance);
	            stmt.setString(3, custID);
	            stmt.executeUpdate();
	            System.out.println("Account created.");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void deposit(long accNum, double amount) {
	        String sql = "UPDATE accounts SET balance = balance + ? WHERE accNum = ?";
	        try (Connection conn = getDBConn();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setDouble(1, amount);
	            stmt.setLong(2, accNum);
	            int rows = stmt.executeUpdate();
	            if (rows > 0) System.out.println("Deposit successful.");
	            else System.out.println("Account not found.");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void withdraw(long accNum, double amount) {
	        String checkBalance = "SELECT balance FROM accounts WHERE accNum = ?";
	        String sql = "UPDATE accounts SET balance = balance - ? WHERE accNum = ?";

	        try (Connection conn = getDBConn();
	             PreparedStatement checkStmt = conn.prepareStatement(checkBalance);
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            
	            checkStmt.setLong(1, accNum);
	            ResultSet rs = checkStmt.executeQuery();

	            if (rs.next()) {
	                double currentBalance = rs.getDouble("balance");
	                if (currentBalance >= amount) {
	                    stmt.setDouble(1, amount);
	                    stmt.setLong(2, accNum);
	                    stmt.executeUpdate();
	                    System.out.println("Withdrawal successful.");
	                } else {
	                    System.out.println("Insufficient balance.");
	                }
	            } else {
	                System.out.println("Account not found.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void getAccountDetails(long accNum) {
	        String sql = "SELECT a.accNum, a.accType, a.balance, c.fName, c.lName, c.email " +
	                     "FROM accounts a JOIN customers c ON a.custID = c.custID WHERE a.accNum = ?";
	        try (Connection conn = getDBConn();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setLong(1, accNum);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                System.out.println("Account No: " + rs.getLong("accNum"));
	                System.out.println("Type: " + rs.getString("accType"));
	                System.out.println("Balance: " + rs.getDouble("balance"));
	                System.out.println("Customer: " + rs.getString("fName") + " " + rs.getString("lName"));
	                System.out.println("Email: " + rs.getString("email"));
	            } else {
	                System.out.println("Account not found.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void listAllAccounts() {
	        String sql = "SELECT a.accNum, a.accType, a.balance, c.fName, c.lName " +
	                     "FROM accounts a JOIN customers c ON a.custID = c.custID";
	        try (Connection conn = getDBConn();
	             PreparedStatement stmt = conn.prepareStatement(sql);
	             ResultSet rs = stmt.executeQuery()) {
	            System.out.println("\nAll Accounts:");
	            while (rs.next()) {
	                System.out.println("Acc No: " + rs.getLong("accNum") +
	                                   " | Type: " + rs.getString("accType") +
	                                   " | Balance: " + rs.getDouble("balance") +
	                                   " | Customer: " + rs.getString("fName") + " " + rs.getString("lName"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void main(String[] args) {
	        createTables();
	        Scanner sc = new Scanner(System.in);
	        
	        while (true) {
	            System.out.println("\nBANK MENU");
	            System.out.println("1. Create Customer");
	            System.out.println("2. Create Account");
	            System.out.println("3. Deposit");
	            System.out.println("4. Withdraw");
	            System.out.println("5. View Account");
	            System.out.println("6. List Accounts");
	            System.out.println("7. Exit");
	            System.out.print("Enter choice: ");
	            
	            int choice = sc.nextInt();
	            switch (choice) {
	                case 1:
	                    System.out.print("Enter Customer ID: ");
	                    String id = sc.next();
	                    System.out.print("First Name: ");
	                    String fName = sc.next();
	                    System.out.print("Last Name: ");
	                    String lName = sc.next();
	                    System.out.print("Email: ");
	                    String email = sc.next();
	                    System.out.print("Phone: ");
	                    String phone = sc.next();
	                    System.out.print("Address: ");
	                    sc.nextLine();
	                    String address = sc.nextLine();
	                    addCustomer(id, fName, lName, email, phone, address);
	                    break;
	                case 2:
	                    System.out.print("Enter Customer ID: ");
	                    String custID = sc.next();
	                    System.out.print("Enter Account Type (Savings/Current): ");
	                    String accType = sc.next();
	                    System.out.print("Enter Initial Balance: ");
	                    double balance = sc.nextDouble();
	                    addAccount(accType, balance, custID);
	                    break;
	                case 3:
	                    System.out.print("Enter Account No: ");
	                    long accNum = sc.nextLong();
	                    System.out.print("Enter Amount: ");
	                    deposit(accNum, sc.nextDouble());
	                    break;
	                case 4:
	                    System.out.print("Enter Account No: ");
	                    long accNo = sc.nextLong();
	                    System.out.print("Enter Amount: ");
	                    withdraw(accNo, sc.nextDouble());
	                    break;
	                case 5:
	                    System.out.print("Enter Account No: ");
	                    getAccountDetails(sc.nextLong());
	                    break;
	                case 6:
	                    listAllAccounts();
	                    break;
	                case 7:
	                    System.out.println("Exiting...");
	                    sc.close();
	                    return;
	                default:
	                    System.out.println("Invalid Choice.");
	            }
	        }
	    }
	}



