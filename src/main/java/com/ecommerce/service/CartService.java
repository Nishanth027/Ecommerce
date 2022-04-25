package com.ecommerce.service;

import java.util.List; 

import org.springframework.stereotype.Service;

import com.ecommerce.model.Cart;

@Service
public interface CartService {

	List<Cart> fetchCartList();

	void deleteCartById(String pid);

	Cart getCartById(String pid);

	void saveCart(String product_id, String seller_id, String product_name, double product_price, String user,
			String customer_name, String seller_name,int count);
}
