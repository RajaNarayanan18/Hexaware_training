package com.Ecom.dao;
import java.sql.SQLException;

import com.Ecom.entity.Customer;

public interface CustomerDAO {
	//package com.Ecom.dao;

	
	    boolean createCustomer(Customer customer);
	    boolean deleteCustomer(int customerId);
	    Customer findById(int customerId) throws SQLException;  // Add this method to the interface
	}






