
package com.Ecom.dao;

import java.sql.SQLException;

public interface OrderDAO {
    boolean placeOrder(int customerId, String shippingAddress) throws SQLException;
    void getOrdersByCustomer(int customerId);
}
