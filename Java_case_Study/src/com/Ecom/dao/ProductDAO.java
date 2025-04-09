package com.Ecom.dao;
import java.sql.SQLException;

import com.Ecom.entity.Product;
public interface ProductDAO {
	//package com.Ecom.dao;
	
	    boolean createProduct(Product product);
	    boolean deleteProduct(int productId);
	    
	    // Method declaration to find a product by its ID
	    Product findById(int productId) throws SQLException;
	}

	

		



