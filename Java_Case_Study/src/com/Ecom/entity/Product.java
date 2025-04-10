package com.Ecom.entity;

public class Product {

    private int productId;
    private String name;
    private double price;
    private String description;
    private int stockQuantity;

    public Product() {}

    public Product(int productId, String name, double price, String description, int stockQuantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stockQuantity = stockQuantity;
    }

    // Getters
    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    // Setters
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
