package java_case_study;

import java.sql.*;
import java.util.*;

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
// table creation for all the class
    private void createTables() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS customers (customer_id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), email VARCHAR(100), password VARCHAR(50))");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS products (product_id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), price DOUBLE, description TEXT, stockQuantity INT)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS cart (cart_id INT PRIMARY KEY AUTO_INCREMENT, customer_id INT, product_id INT, quantity INT, FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE, FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS orders (order_id INT PRIMARY KEY AUTO_INCREMENT, customer_id INT, order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, total_price DOUBLE, shipping_address TEXT, FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS order_items (order_item_id INT PRIMARY KEY AUTO_INCREMENT, order_id INT, product_id INT, quantity INT, FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE, FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE)");
            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCustomer(Customer customer) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO customers (name, email, password) VALUES (?, ?, ?)")) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPassword());
            ps.executeUpdate();
            System.out.println("Customer created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createProduct(Product product) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO products (name, price, description, stockQuantity) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setString(3, product.getDescription());
            ps.setInt(4, product.getStockQuantity());
            ps.executeUpdate();
            System.out.println("Product created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int productId) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM products WHERE product_id = ?")) {
            ps.setInt(1, productId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Product not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(int customerId) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM customers WHERE customer_id = ?")) {
            ps.setInt(1, customerId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer deleted successfully.");
            } else {
                System.out.println("Customer not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addToCart(int customerId, int productId, int quantity) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO cart (customer_id, product_id, quantity) VALUES (?, ?, ?)")) {
            ps.setInt(1, customerId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.executeUpdate();
            System.out.println("Item added to cart.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeFromCart(int customerId, int productId) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM cart WHERE customer_id = ? AND product_id = ?")) {
            ps.setInt(1, customerId);
            ps.setInt(2, productId);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("Item removed from cart.");
            else System.out.println("Item not found in cart.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewCart(int customerId) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT c.cart_id, p.name, c.quantity FROM cart c JOIN products p ON c.product_id = p.product_id WHERE c.customer_id = ?")) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            System.out.println("Cart Items:");
            while (rs.next()) {
                System.out.println("Cart ID: " + rs.getInt("cart_id") + ", Product: " + rs.getString("name") + ", Quantity: " + rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void placeOrder(int customerId, String shippingAddress) {
        try {
            conn.setAutoCommit(false);
            double total = 0;
            List<int[]> items = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT product_id, quantity FROM cart WHERE customer_id = ?")) {
                ps.setInt(1, customerId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int pid = rs.getInt("product_id");
                    int qty = rs.getInt("quantity");
                    items.add(new int[]{pid, qty});
                    try (PreparedStatement ps2 = conn.prepareStatement("SELECT price FROM products WHERE product_id = ?")) {
                        ps2.setInt(1, pid);
                        ResultSet rs2 = ps2.executeQuery();
                        if (rs2.next()) {
                            total += rs2.getDouble("price") * qty;
                        }
                    }
                }
            }
            if (items.isEmpty()) {
                System.out.println("Cart is empty. Cannot place order.");
                return;
            }
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO orders (customer_id, total_price, shipping_address) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, customerId);
                ps.setDouble(2, total);
                ps.setString(3, shippingAddress);
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int orderId = rs.getInt(1);
                    for (int[] item : items) {
                        try (PreparedStatement ps2 = conn.prepareStatement("INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)")) {
                            ps2.setInt(1, orderId);
                            ps2.setInt(2, item[0]);
                            ps2.setInt(3, item[1]);
                            ps2.executeUpdate();
                        }
                    }
                    try (PreparedStatement ps3 = conn.prepareStatement("DELETE FROM cart WHERE customer_id = ?")) {
                        ps3.setInt(1, customerId);
                        ps3.executeUpdate();
                    }
                    conn.commit();
                    System.out.println("Order placed successfully.");
                }
            }
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public void getOrdersByCustomer(int customerId) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT o.order_id, o.order_date, o.total_price, o.shipping_address, p.name, oi.quantity FROM orders o JOIN order_items oi ON o.order_id = oi.order_id JOIN products p ON oi.product_id = p.product_id WHERE o.customer_id = ?")) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            System.out.println("Orders for Customer ID: " + customerId);
            while (rs.next()) {
                System.out.println("Order ID: " + rs.getInt("order_id") + ", Date: " + rs.getTimestamp("order_date") + ", Product: " + rs.getString("name") + ", Quantity: " + rs.getInt("quantity") + ", Total: $" + rs.getDouble("total_price") + ", Address: " + rs.getString("shipping_address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EcomApp app = new EcomApp();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nChoose operation:");
            System.out.println("1. Create Customer");
            System.out.println("2. Create Product");
            System.out.println("3. Delete Customer");
            System.out.println("4. Delete Product");
            System.out.println("5. Add to Cart");
            System.out.println("6. Remove from Cart");
            System.out.println("7. View Cart");
            System.out.println("8. Place Order");
            System.out.println("9. Get Orders by Customer");
            System.out.println("10. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter Customer Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();
                    Customer customer = new Customer(0, name, email, password);
                    app.createCustomer(customer);
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
                    Product product = new Product(0, pname, price, desc, stock);
                    app.createProduct(product);
                    break;
                case 3:
                    System.out.print("Enter Customer ID to delete: ");
                    int delCustId = scanner.nextInt();
                    app.deleteCustomer(delCustId);
                    break;
                case 4:
                    System.out.print("Enter Product ID to delete: ");
                    int delProdId = scanner.nextInt();
                    app.deleteProduct(delProdId);
                    break;
                case 5:
                    System.out.print("Enter Customer ID: ");
                    int custId = scanner.nextInt();
                    System.out.print("Enter Product ID: ");
                    int prodId = scanner.nextInt();
                    System.out.print("Enter Quantity: ");
                    int qty = scanner.nextInt();
                    app.addToCart(custId, prodId, qty);
                    break;
                case 6:
                    System.out.print("Enter Customer ID: ");
                    int custIdRemove = scanner.nextInt();
                    System.out.print("Enter Product ID to remove: ");
                    int prodIdRemove = scanner.nextInt();
                    app.removeFromCart(custIdRemove, prodIdRemove);
                    break;
                case 7:
                    System.out.print("Enter Customer ID to view cart: ");
                    int viewCust = scanner.nextInt();
                    app.viewCart(viewCust);
                    break;
                case 8:
                    System.out.print("Enter Customer ID to place order: ");
                    int orderCust = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Shipping Address: ");
                    String shipAddr = scanner.nextLine();
                    app.placeOrder(orderCust, shipAddr);
                    break;
                case 9:
                    System.out.print("Enter Customer ID to get orders: ");
                    int orderViewCust = scanner.nextInt();
                    app.getOrdersByCustomer(orderViewCust);
                    break;
                case 10:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}