package java_case_study;

import java.sql.*;
import java.util.Scanner;

public class EcomApp {
    private Connection conn;

    public EcomApp() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/EcomAPP", "root", "Raja@2003");
            System.out.println("Connection Established.");
            createTables();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTables() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS customers (customer_id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), email VARCHAR(100), password VARCHAR(50))");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS products (product_id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), price DOUBLE, description TEXT, stockQuantity INT)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS cart (cart_id INT PRIMARY KEY AUTO_INCREMENT, customer_id INT, product_id INT, quantity INT, FOREIGN KEY (customer_id) REFERENCES customers(customer_id), FOREIGN KEY (product_id) REFERENCES products(product_id))");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS orders (order_id INT PRIMARY KEY AUTO_INCREMENT, customer_id INT, order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, total_price DOUBLE, shipping_address TEXT, FOREIGN KEY (customer_id) REFERENCES customers(customer_id))");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS order_items (order_item_id INT PRIMARY KEY AUTO_INCREMENT, order_id INT, product_id INT, quantity INT, FOREIGN KEY (order_id) REFERENCES orders(order_id), FOREIGN KEY (product_id) REFERENCES products(product_id))");
            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCustomer(String name, String email, String password) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO customers (name, email, password) VALUES (?, ?, ?)")) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.executeUpdate();
            System.out.println("Customer added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProduct(String name, double price, String description, int stockQuantity) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO products (name, price, description, stockQuantity) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setString(3, description);
            ps.setInt(4, stockQuantity);
            ps.executeUpdate();
            System.out.println("Product added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void placeOrder(int customerId, String shippingAddress) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO orders (customer_id, total_price, shipping_address) VALUES (?, 0, ?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, customerId);
            ps.setString(2, shippingAddress);
            ps.executeUpdate();
            System.out.println("Order placed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayProducts() {
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {
            while (rs.next()) {
                System.out.println("Product ID: " + rs.getInt("product_id") + ", Name: " + rs.getString("name") + ", Price: " + rs.getDouble("price") + ", Stock: " + rs.getInt("stockQuantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EcomApp app = new EcomApp();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Customer\n2. Add Product\n3. Place Order\n4. Display Products\n5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();
                    app.addCustomer(name, email, password);
                    break;
                case 2:
                    System.out.print("Enter Product Name: ");
                    String pname = scanner.nextLine();
                    System.out.print("Enter Price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Enter Description: ");
                    String desc = scanner.nextLine();
                    System.out.print("Enter Stock Quantity: ");
                    int stock = scanner.nextInt();
                    scanner.nextLine();
                    app.addProduct(pname, price, desc, stock);
                    break;
                case 3:
                    System.out.print("Enter Customer ID: ");
                    int customerId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Shipping Address: ");
                    String address = scanner.nextLine();
                    app.placeOrder(customerId, address);
                    break;
                case 4:
                    app.displayProducts();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
