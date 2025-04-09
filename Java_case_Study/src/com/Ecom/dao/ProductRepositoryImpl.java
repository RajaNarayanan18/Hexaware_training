package com.Ecom.dao;
import com.Ecom.entity.Product;
import java.sql.*;

public class ProductRepositoryImpl implements ProductDAO {
	
	    private Connection conn;

	    public ProductRepositoryImpl(Connection conn) {
	        this.conn = conn;
	    }

	    public boolean createProduct(Product product) {
	        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO products(name, price, description, stockQuantity) VALUES (?, ?, ?, ?);")) {
	            ps.setString(1, product.getName());
	            ps.setDouble(2, product.getPrice());
	            ps.setString(3, product.getDescription());
	            ps.setInt(4, product.getStockQuantity());
	            return ps.executeUpdate() > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }

	    public boolean deleteProduct(int productId) {
	        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM products WHERE product_id = ?")) {
	            ps.setInt(1, productId);
	            return ps.executeUpdate() > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }

	    // Method to find a product by its ID
	    public Product findById(int productId) throws SQLException {
	        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM products WHERE product_id = ?")) {
	            ps.setInt(1, productId);
	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	                // If the product is found, create a Product object and return it
	                Product product = new Product(
	                    rs.getInt("product_id"),
	                    rs.getString("name"),
	                    rs.getDouble("price"),
	                    rs.getString("description"),
	                    rs.getInt("stockQuantity")
	                );
	                return product;
	            } else {
	                // Return null if no product is found
	                return null;
	            }
	        }
	    }}
	    

