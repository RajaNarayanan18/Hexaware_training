
package com.Ecom.dao;


import com.Ecom.dao.OrderDAO;
import com.Ecom.entity.Order;
import com.Ecom.entity.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private Connection conn;

    public OrderDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean placeOrder(int customerId, String address) {
        try {
            conn.setAutoCommit(false);

            // Get cart items
            PreparedStatement ps1 = conn.prepareStatement(
                "SELECT c.product_id, c.quantity, p.price FROM cart c JOIN products p ON c.product_id = p.product_id WHERE c.customer_id = ?");
            ps1.setInt(1, customerId);
            ResultSet rs = ps1.executeQuery();

            List<OrderItem> items = new ArrayList<>();
            double total = 0.0;

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                total += price * quantity;
                items.add(new OrderItem(0, 0, productId, quantity));
            }

            if (items.isEmpty()) {
                System.out.println("Cart is empty. Cannot place order.");
                return false;
            }

            // Insert into orders
            PreparedStatement ps2 = conn.prepareStatement(
                "INSERT INTO orders (customer_id, total_price, shipping_address) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
            ps2.setInt(1, customerId);
            ps2.setDouble(2, total);
            ps2.setString(3, address);
            ps2.executeUpdate();

            ResultSet keys = ps2.getGeneratedKeys();
            int orderId = 0;
            if (keys.next()) {
                orderId = keys.getInt(1);
            }

            // Insert into order_items
            for (OrderItem item : items) {
                PreparedStatement ps3 = conn.prepareStatement(
                    "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)");
                ps3.setInt(1, orderId);
                ps3.setInt(2, item.getProductId());
                ps3.setInt(3, item.getQuantity());
                ps3.executeUpdate();
            }

            // Clear cart
            PreparedStatement ps4 = conn.prepareStatement("DELETE FROM cart WHERE customer_id = ?");
            ps4.setInt(1, customerId);
            ps4.executeUpdate();

            conn.commit();
            conn.setAutoCommit(true);

            System.out.println("Order placed successfully with Order ID: " + orderId);
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void getOrdersByCustomer(int customerId) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM orders WHERE customer_id = ?");
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                Date date = rs.getTimestamp("order_date");
                double total = rs.getDouble("total_price");
                String address = rs.getString("shipping_address");

                System.out.println("\nOrder ID: " + orderId + ", Date: " + date + ", Total: $" + total + ", Address: " + address);
                
                PreparedStatement itemStmt = conn.prepareStatement(
                    "SELECT oi.product_id, oi.quantity, p.name FROM order_items oi JOIN products p ON oi.product_id = p.product_id WHERE oi.order_id = ?");
                itemStmt.setInt(1, orderId);
                ResultSet itemRs = itemStmt.executeQuery();

                while (itemRs.next()) {
                    System.out.println(" - Product: " + itemRs.getString("name") + ", Quantity: " + itemRs.getInt("quantity"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
