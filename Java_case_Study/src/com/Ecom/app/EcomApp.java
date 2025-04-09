package com.Ecom.app;

import java.sql.*;
import java.util.*;
import com.Ecom.entity.*;
import com.Ecom.myexceptions.CustomerNotFoundException;
import com.Ecom.myexceptions.ProductNotFoundException;
import com.Ecom.dao.*;

public class EcomApp {
    private Connection conn;
    private CustomerDAO customerDAO;
    private ProductDAO productDAO;
    private CartDAO cartDAO;
    private OrderDAO orderDAO;

    public EcomApp() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/EcomAPP", "root", "Raja@2003");
            System.out.println("Connection Established.");
            createTables();
            customerDAO = new CustomerRepositoryImpl(conn);
            productDAO = new ProductRepositoryImpl(conn);
            cartDAO = new CartRepositoryImpl(conn);
            orderDAO = new OrderDAOImpl(conn);

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error establishing database connection: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void createTables() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS customers (customer_id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), email VARCHAR(100), password VARCHAR(50))");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS products (product_id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), price DOUBLE, description TEXT, stockQuantity INT)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS cart (cart_id INT PRIMARY KEY AUTO_INCREMENT, customer_id INT, product_id INT, quantity INT, FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE, FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS orders (order_id INT PRIMARY KEY AUTO_INCREMENT, customer_id INT, order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, total_price DOUBLE, shipping_address TEXT, FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS order_items (order_item_id INT PRIMARY KEY AUTO_INCREMENT, order_id INT, product_id INT, quantity INT, FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE, FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE)");
            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }

    public boolean createCustomer(Customer customer) throws SQLException {
        return customerDAO.createCustomer(customer);
    }

    public boolean createProduct(Product product) throws SQLException {
        return productDAO.createProduct(product);
    }

    public boolean deleteCustomer(int customerId) throws SQLException {
        try {
            if (customerId <= 0) {
                throw new IllegalArgumentException("Invalid customer ID");
            }
            return customerDAO.deleteCustomer(customerId);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteProduct(int productId) throws SQLException {
        try {
            if (productId <= 0) {
                throw new IllegalArgumentException("Invalid product ID");
            }
            return productDAO.deleteProduct(productId);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean addToCart(int customerId, int productId, int quantity) throws SQLException {
        try {
            if (customerId <= 0 || productId <= 0 || quantity <= 0) {
                throw new IllegalArgumentException("Invalid input values");
            }
            
            // You may want to validate that the customer and product exist
            Customer customer = customerDAO.findById(customerId);
            if (customer == null) {
                throw new CustomerNotFoundException("Customer not found in database");
            }
            
            Product product = productDAO.findById(productId);
            if (product == null) {
                throw new ProductNotFoundException("Product not found in database");
            }
            
            // If customer and product exist, proceed to add the product to the cart
            return cartDAO.addToCart(customerId, productId, quantity);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        } catch (CustomerNotFoundException | ProductNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        } catch (SQLException e) {
            System.err.println("Failed to add to cart: " + e.getMessage());
            return false;
        }
    }


    public boolean removeFromCart(int customerId, int productId) throws SQLException {
        try {
            if (customerId <= 0 || productId <= 0) {
                throw new IllegalArgumentException("Invalid input values");
            }
            return cartDAO.removeFromCart(customerId, productId);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    public void viewCart(int customerId) throws SQLException {
        cartDAO.viewCart(customerId);
    }

    public boolean placeOrder(int customerId, String address) throws CustomerNotFoundException {
        try {
            if (customerId <= 0 || address == null || address.isEmpty()) {
                throw new IllegalArgumentException("Invalid input values");
            }

            // Check if the customer exists before placing the order
            Customer customer = customerDAO.findById(customerId);
            if (customer == null) {
                throw new CustomerNotFoundException("Customer not found in database");
            }

            // Proceed to place the order
            return orderDAO.placeOrder(customerId, address);

        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        } catch (CustomerNotFoundException e) {
            System.err.println("Customer error: " + e.getMessage());
            return false;
        } catch (SQLException e) {
            System.err.println("Failed to place order: " + e.getMessage());
            return false;
        }
    }

    public void getOrdersByCustomer(int customerId) throws SQLException {
        orderDAO.getOrdersByCustomer(customerId);
    }

    public static void main(String[] args) throws SQLException, CustomerNotFoundException {
        EcomApp app = new EcomApp();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Create Customer\n2. Create Product\n3. Delete Customer\n4. Delete Product\n5. Add to Cart\n6. Remove from Cart\n7. View Cart\n8. Place Order\n9. View Orders\n10. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Name: "); String name = sc.nextLine();
                    System.out.print("Email: "); String email = sc.nextLine();
                    System.out.print("Password: "); String pwd = sc.nextLine();
                    if (app.createCustomer(new Customer(0, name, email, pwd))) {
                        System.out.println("Customer created successfully!");
                    } else {
                        System.out.println("Failed to create customer.");
                    }
                    break;

                case 2:
                    System.out.print("Product Name: "); String pname = sc.nextLine();
                    System.out.print("Price: "); double price = sc.nextDouble(); sc.nextLine();
                    System.out.print("Description: "); String desc = sc.nextLine();
                    System.out.print("Stock: "); int stock = sc.nextInt(); sc.nextLine();
                    if (app.createProduct(new Product(0, pname, price, desc, stock))) {
                        System.out.println("Product created successfully!");
                    } else {
                        System.out.println("Failed to create product.");
                    }
                    break;

                case 3:
                    System.out.print("Customer ID: "); int cid = sc.nextInt(); sc.nextLine();
                    if (app.deleteCustomer(cid)) {
                        System.out.println("Customer deleted successfully!");
                    } else {
                        System.out.println("Failed to delete customer.");
                    }
                    break;

                case 4:
                    System.out.print("Product ID: "); int pid = sc.nextInt(); sc.nextLine();
                    if (app.deleteProduct(pid)) {
                        System.out.println("Product deleted successfully!");
                    } else {
                        System.out.println("Failed to delete product.");
                    }
                    break;

                case 5:
                    System.out.print("Customer ID: "); int cartCid = sc.nextInt();
                    System.out.print("Product ID: "); int cartPid = sc.nextInt();
                    System.out.print("Quantity: "); int qty = sc.nextInt(); sc.nextLine();
                    if (app.addToCart(cartCid, cartPid, qty)) {
                        System.out.println("Item added to cart successfully!");
                    } else {
                        System.out.println("Failed to add item to cart.");
                    }
                    break;

                case 6:
                    System.out.print("Customer ID: "); int rmCid = sc.nextInt();
                    System.out.print("Product ID: "); int rmPid = sc.nextInt(); sc.nextLine();
                    if (app.removeFromCart(rmCid, rmPid)) {
                        System.out.println("Item removed from cart successfully!");
                    } else {
                        System.out.println("Failed to remove item from cart.");
                    }
                    break;

                case 7:
                    System.out.print("Customer ID: "); int vCid = sc.nextInt(); sc.nextLine();
                    app.viewCart(vCid);
                    break;

                case 8:
                    System.out.print("Customer ID: "); int oCid = sc.nextInt(); sc.nextLine();
                    System.out.print("Shipping Address: "); String address = sc.nextLine();
                    if (app.placeOrder(oCid, address)) {
                        System.out.println("Order placed successfully!");
                    } else {
                        System.out.println("Failed to place order.");
                    }
                    break;

                case 9:
                    System.out.print("Customer ID: "); int ordCid = sc.nextInt(); sc.nextLine();
                    app.getOrdersByCustomer(ordCid);
                    break;

                case 10:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
