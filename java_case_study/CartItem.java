package java_case_study;

public class CartItem {
	
	    private int cartId;
	    private int customerId;
	    private Product product; // Correct class name
	    private int quantity;

	    public CartItem(int cartId, int customerId, Product product, int quantity) {
	        this.cartId = cartId;
	        this.customerId = customerId;
	        this.product = product;
	        this.quantity = quantity;
	    }

	    public int getCartId() { return cartId; }
	    public int getCustomerId() { return customerId; }
	    public Product getProduct() { return product; } // Correct return type
	    public int getQuantity() { return quantity; }

	    public void displayCartItem() {
	        System.out.println("Cart ID: " + cartId + ", Customer ID: " + customerId +
	                           ", Product: " + product.getName() + ", Quantity: " + quantity);
	    }
	}



