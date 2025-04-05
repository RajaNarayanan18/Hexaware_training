
	
	package java_case_study;

	import java.util.Date;
	import java.util.List;

	public class Order {
	    private int orderId;
	    private int customerId;
	    private double totalPrice;
	    private Date orderDate;
	    private String shippingAddress;
	    private List<OrderItem> orderItems;

	    public Order(int orderId, int customerId, double totalPrice, Date orderDate, String shippingAddress, List<OrderItem> orderItems) {
	        this.orderId = orderId;
	        this.customerId = customerId;
	        this.totalPrice = totalPrice;
	        this.orderDate = orderDate;
	        this.shippingAddress = shippingAddress;
	        this.orderItems = orderItems;
	    }

	    public int getOrderId() { return orderId; }
	    public int getCustomerId() { return customerId; }
	    public double getTotalPrice() { return totalPrice; }
	    public Date getOrderDate() { return orderDate; }
	    public String getShippingAddress() { return shippingAddress; }
	    public List<OrderItem> getOrderItems() { return orderItems; }

	    public void displayOrder() {
	        System.out.println("Order ID: " + orderId + ", Customer ID: " + customerId +
	                ", Total Price: $" + totalPrice + ", Order Date: " + orderDate +
	                ", Shipping Address: " + shippingAddress);
	        System.out.println("Order Items:");
	        for (OrderItem item : orderItems) {
	            item.displayOrderItem();
	        }
	    }
	}



