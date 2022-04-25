package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.Seller;
import com.ecommerce.repository.SellerRepository;

@Service
public class SellerServiceImpl implements SellerService{

	@Autowired
	SellerRepository repo;
	
	@Override
	public void saveSeller(Seller s) {
		repo.save(s);
	}

	@Override
	public List<Seller> fetchSellerList() {
		List<Seller> list=repo.findAll();
		return list;
	}

	@Override
	public Seller updateSeller(Seller s, String seller_id) {
		Seller s1=repo.getById(seller_id);
		s1.setSeller_name(s.getSeller_name());
		s1.setSeller_email(s.getSeller_email());
		s1.setSeller_phone_number(s.getSeller_phone_number());
		s1.setSeller_address(s.getSeller_address());
		return s1;
	}

	@Override
	public void deleteSellerById(String seller_id) {
		  repo.deleteById(seller_id);
	}

	@Override
	public Seller getSellerById(String seller_id) {
		 Seller s1=repo.getById(seller_id);
		 return s1;
	}

}
