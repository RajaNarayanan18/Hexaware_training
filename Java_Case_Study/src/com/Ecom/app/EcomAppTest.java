package com.Ecom.app;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import com.Ecom.entity.Product;
import com.Ecom.myexceptions.CustomerNotFoundException;
import com.Ecom.myexceptions.ProductNotFoundException;

//import com.Ecom.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EcomAppTest {
	
	    private EcomApp app;

	    @BeforeEach
	    void setUp() {
	        app = new EcomApp(); // Initialize EcomApp before each test
	    }

	    // Test case 1: Test if a product is created successfully
	    @Test
	    void testCreateProduct() throws SQLException {
	        Product product = new Product(1,"Laptop", 50000, "High-end Laptop", 10);
	        boolean result = app.createProduct(product);
	        assertTrue(result, "Product should be created successfully.");
	    }

	    // Test case 2: Test if a product is added to the cart successfully
	    @Test
	    void testAddToCart() throws SQLException, CustomerNotFoundException, ProductNotFoundException {
	        int customerId = 1;
	        int productId = 1;
	        int quantity = 2;
	        
	        boolean result = app.addToCart(customerId, productId, quantity);
	        assertTrue(result, "Product should be added to cart successfully.");
	    }

	    // Test case 3: Test if a product is ordered successfully
	    @Test
	    void testPlaceOrder() throws CustomerNotFoundException {
	        
	        int customerId = 1;
	        String address = "palla street";
	        
	        boolean result = app.placeOrder(customerId, address);
	        assertTrue(result, "Order should be placed successfully.");
	    }

	    // Test case 4: Test if an exception is thrown when customer or product ID is not found
	    @Test
	    void testCustomerNotFoundException() {
	        int nonExistentCustomerId = 999;
	        String address = "Non-existent address";

	        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
	            app.placeOrder(nonExistentCustomerId, address);
	        });

	        assertEquals("Customer not found in database", exception.getMessage());
	    }
	    
	    @Test
	    void testProductNotFoundException() {
	        int nonExistentProductId = 100; // Product that does not exist in the database
	        int customerId = 1; //  customer exists
	        int quantity = 1;

	        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
	            app.addToCart(customerId, nonExistentProductId, quantity);
	        });

	        assertEquals("Product not found in database", exception.getMessage());
	    }




}
