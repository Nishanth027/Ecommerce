package com.ecommerce.service;

import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.Customer;
import com.ecommerce.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{
    
	@Autowired
	CustomerRepository repo;
	
	@Override
	public void saveCustomer(Customer c) {
		repo.save(c);		
	}

	@Override
	public List<Customer> fetchCustomerList() {
		List<Customer> list=repo.findAll();
		return list;
	}

	@Override
	public Customer updateCustomer(Customer c, String CustomerId) {
		Customer c1=repo.getById(CustomerId);
		c1.setCustomer_name(c.getCustomer_name());
		c1.setCustomer_address(c.getCustomer_address());
		c1.setCustomer_email(c.getCustomer_email());
		c1.setCustomer_phone_number(c.getCustomer_phone_number());
		c1.setCustomer_password(c.getCustomer_password());
		return c1;
	}

	@Override
	public void deleteCustomerById(String CustomerId) {
		  repo.deleteById(CustomerId);
		  
	}

	@Override
	public Customer getCustomerById(String CustomerId) {
		 Customer c1=repo.getById(CustomerId);
		 return c1;
	}

}
