package java_case_study;

public class Product {
	
	    private int productId;
	    private String name;
	    private double price;
	    private String description;
	    private int stockQuantity;

	    public Product(int productId, String name, double price, String description, int stockQuantity) {
	        this.productId = productId;
	        this.name = name;
	        this.price = price;
	        this.description = description;
	        this.stockQuantity = stockQuantity;
	    }

	    public int getProductId() { return productId; }
	    public String getName() { return name; }
	    public double getPrice() { return price; }
	    public String getDescription() { return description; }
	    public int getStockQuantity() { return stockQuantity; }

	    public void updateStock(int quantity) {
	        if (quantity <= stockQuantity) {
	            stockQuantity -= quantity;
	        } else {
	            System.out.println("Not enough stock available.");
	        }
	    }

	    public void displayProduct() {
	        System.out.println("Product ID: " + productId + ", Name: " + name +
	                           ", Price: $" + price + ", Stock: " + stockQuantity);
	    }


}
