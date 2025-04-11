package dao;
import entity.*;
import exception.*;
import java.sql.*;
	

	public class AccountDAOImpl implements AccountDAO {
	    private Connection conn;

	    public AccountDAOImpl(Connection conn) {
	        this.conn = conn;
	    }

	    public void createAccount(Account account) throws SQLException {
	        String sql = "INSERT INTO accounts(accNum, accType, balance) VALUES (?, ?, ?)";
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, account.getAccNum());
	            stmt.setString(2, account.getAccType());
	            stmt.setDouble(3, account.getBalance());
	            stmt.executeUpdate();
	        }
	    }

	    public void deposit(String accNum, double amount) throws SQLException, AccountNotFoundException {
	        String sql = "UPDATE accounts SET balance = balance + ? WHERE accNum = ?";
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setDouble(1, amount);
	            stmt.setString(2, accNum);
	            int rows = stmt.executeUpdate();
	            if (rows == 0) throw new AccountNotFoundException("Account not found: " + accNum);
	        }
	    }

	    public void withdraw(String accNum, double amount) throws Exception {
	        String checkSql = "SELECT balance, accType FROM accounts WHERE accNum = ?";
	        try (PreparedStatement stmt = conn.prepareStatement(checkSql)) {
	            stmt.setString(1, accNum);
	            ResultSet rs = stmt.executeQuery();
	            if (!rs.next()) throw new AccountNotFoundException("Account not found: " + accNum);
	            double currentBalance = rs.getDouble("balance");
	            String accType = rs.getString("accType");

	            if (accType.equals("Current")) {
	                double overdraftLimit = 2000;
	                if (amount > currentBalance + overdraftLimit)
	                    throw new InsufficientBalanceException("Overdraft limit exceeded");
	            } else {
	                if (currentBalance < amount)
	                    throw new InsufficientBalanceException("Not enough balance");
	            }
	        }

	        String sql = "UPDATE accounts SET balance = balance - ? WHERE accNum = ?";
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setDouble(1, amount);
	            stmt.setString(2, accNum);
	            stmt.executeUpdate();
	        }
	    }

	    public void calculateInterest(String accNum) throws SQLException {
	        String sql = "UPDATE accounts SET balance = balance + (balance * 0.045) WHERE accNum = ? AND accType = 'Savings'";
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, accNum);
	            stmt.executeUpdate();
	        }
	    }
	}


