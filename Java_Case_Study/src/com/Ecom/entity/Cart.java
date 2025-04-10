package com.Ecom.entity;

public class Cart {

    private int cartId;
    private int customerId;
    private int productId;
    private int quantity;

    public Cart() {}

    public Cart(int cartId, int customerId, int productId, int quantity) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters
    public int getCartId() {
        return cartId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters
    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
