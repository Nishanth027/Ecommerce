package com.ecommerce.service;

import java.util.List;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.Cart;
import com.ecommerce.repository.CartRepository;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	CartRepository repo;
	
//	@Autowired
//	JdbcTemplate jd;
	
	@Override
	public List<Cart> fetchCartList() {
		List<Cart> list=repo.findAll();
		return list;
	}

	@Override
	public void deleteCartById(String pid) {
		  repo.deleteById(pid);

	}

	@Override
	public Cart getCartById(String pid) {
		 Cart c1=repo.getById(pid);
		 return c1;
	}

	@Override
	public void saveCart(String product_id, String seller_id, String product_name, 
			double product_price, String customer_id,String customer_name, String seller_name,int count) {
//		String str="insert into cart values("+product_id+",'"+seller_id+"'"+",'"+customer_id+"'"
//			+",'"+customer_name+"'"+",'"+seller_name+"'"+",'"+product_name+"',"+product_price+")";
//	    jd.execute(str);
		Cart c= new Cart();
		c.setProduct_id(product_id);
		c.setProduct_name(product_name);
		c.setCustomer_id(customer_id);
		c.setCustomer_name(customer_name);
		c.setSeller_id(seller_id);
		c.setSeller_name(seller_name);
		c.setProduct_quantity(count);
		c.setProduct_price(product_price*count);
		repo.save(c);
	}

}
