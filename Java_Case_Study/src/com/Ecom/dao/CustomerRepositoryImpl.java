package com.Ecom.dao;

import com.Ecom.entity.Customer;
import java.sql.*;

public class CustomerRepositoryImpl implements CustomerDAO {
    private Connection conn;

    public CustomerRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    // Method to create a new customer
    public boolean createCustomer(Customer customer) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO customers(name, email, password) VALUES (?, ?, ?);")) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPassword());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a customer by ID
    public boolean deleteCustomer(int customerId) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM customers WHERE customer_id = ?")) {
            ps.setInt(1, customerId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to find a customer by ID
    public Customer findById(int customerId) throws SQLException {
        String query = "SELECT * FROM customers WHERE customer_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                // Assuming Customer class has a constructor that takes all required fields
                return new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }
        }
        return null; // Return null if customer is not found
    }
}
