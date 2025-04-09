package com.Ecom.dao;

import java.sql.*;

public class CartRepositoryImpl implements CartDAO {
    private Connection conn;

    public CartRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    public boolean addToCart(int customerId, int productId, int quantity) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO cart(customer_id, product_id, quantity) VALUES (?, ?, ?)")) {
            ps.setInt(1, customerId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFromCart(int customerId, int productId) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM cart WHERE customer_id = ? AND product_id = ?")) {
            ps.setInt(1, customerId);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void viewCart(int customerId) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM cart WHERE customer_id = ?")) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("Product ID: " + rs.getInt("product_id") + ", Quantity: " + rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
