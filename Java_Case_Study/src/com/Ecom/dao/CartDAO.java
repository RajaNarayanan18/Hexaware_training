package com.Ecom.dao;

public interface CartDAO {
	

	    boolean addToCart(int customerId, int productId, int quantity);
	    boolean removeFromCart(int customerId, int productId);
	    void viewCart(int customerId);
	}


