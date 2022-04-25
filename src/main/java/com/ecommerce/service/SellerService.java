package com.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.model.Seller;

@Service
public interface SellerService {

	void saveSeller(Seller seller);

	List<Seller> fetchSellerList();

	Seller updateSeller(Seller seller, String seller_id);

	void deleteSellerById(String seller_id);

	Seller getSellerById(String seller_id);
}
