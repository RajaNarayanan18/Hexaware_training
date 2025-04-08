package java_case_study;

public class OrderItem {
    private int orderItemId;
    private int orderId;
    private Product product;
    private int quantity;

    public OrderItem(int orderItemId, int orderId, Product product, int quantity) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
    }

    public int getOrderItemId() { return orderItemId; }
    public int getOrderId() { return orderId; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    public void displayOrderItem() {
        System.out.println("Order Item ID: " + orderItemId + ", Order ID: " + orderId +
                ", Product: " + product.getName() + ", Quantity: " + quantity);
    }
}
