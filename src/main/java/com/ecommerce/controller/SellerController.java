package com.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.Admin;
import com.ecommerce.model.Cart;
import com.ecommerce.model.Product;
import com.ecommerce.model.Seller;
import com.ecommerce.repository.SellerRepository;
import com.ecommerce.service.CartService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.SellerService;

@RestController
@RequestMapping("/Seller")
public class SellerController {
	@Autowired
	SellerRepository repo;
	
	@Autowired
	SellerService service;
	
	@Autowired
	ProductService service1;
	
	@Autowired
	CartService service2;
	
	private String status = "";
	private String user = "";
	@GetMapping("/login")
	public ResponseEntity<?> loginSeller(@RequestBody Seller s) {
		try {
			if (repo.existsById(s.getSeller_id())) {
				if(repo.getById(s.getSeller_id()).getSeller_password()
					.matches(s.getSeller_password())) {
				System.out.println("Login Successful");
				status = "Login";
				user=s.getSeller_id();
				return new ResponseEntity<>("Login Successful", HttpStatus.OK);
			    } else {
				throw new Exception("Seller Password is Incorrect");
			    }
			}else {
				throw new Exception("SellerId is not existed.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/view")
	public ResponseEntity<?> viewseller() {
		try {
			if (status.matches("Login")) {
				Seller s = service.getSellerById(user);
				return new ResponseEntity<>(s, HttpStatus.OK);
			} else {
				throw new Exception("You have to Login.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	@PutMapping("/update")
	public ResponseEntity<?> updateSeller(@RequestBody Seller s) {
		try {
			if (status.matches("Login")) {
				if (s.getSeller_id().matches(user)) {
					service.saveSeller(s);
					return new ResponseEntity<>(s, HttpStatus.OK);
				} else {
					throw new Exception("Your Customer Id is wrong.");
				}
			} else {
				throw new Exception("You have to Login.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/view/products")
	public ResponseEntity<?> viewProduct(){
		try{
			if(status.matches("Login"))
		    {
				List<Product> plist=service1.fetchProductList();
				List<Product> plist1= new ArrayList<>();
				if(plist!=null) {
					for(Product p:plist) {
					  if(p.getSeller_id().matches(user)) {
			              System.out.println(p+"\n");
			              plist1.add(p);}
			          }}
	        	return new ResponseEntity<>(plist1,HttpStatus.OK);
		    }else 
	        {
				throw new Exception("You have to Login.");
			}
		}catch(Exception e) {
			  System.out.println(e.getMessage());
		      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/addproduct")
	public ResponseEntity<?> addproduct(@RequestBody Product p) {
		try {
			if (status.matches("Login")) {
				 p.setSeller_id(user);
			     service1.saveProduct(p);
				return new ResponseEntity<>("Your Product is added.", HttpStatus.OK);
			} else {
				throw new Exception("You have to Login.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/getproduct/{product_id}")
	public ResponseEntity<?> getProduct(@PathVariable("product_id") String product_id){
		try{
			if(status.matches("Login"))
		    {
				Product p=service1.getProductById(product_id); 
				return new ResponseEntity<>(p,HttpStatus.OK);
		    }else 
	        {
				throw new Exception("You have to Login.");
			}
		}catch(Exception e) {
			  System.out.println(e.getMessage());
		      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	@PutMapping("/updateproduct/{product_id}")
	public ResponseEntity<?> updateProduct(@PathVariable("product_id") String product_id,@RequestBody Product p){
		try{
			if(status.matches("Login"))
		    {
				service1.updateProduct(p,product_id); 
				return new ResponseEntity<>("Product Id:"+product_id+" is updated.",HttpStatus.OK);
		    }else 
	        {
				throw new Exception("You have to Login.");
			}
		}catch(Exception e) {
			  System.out.println(e.getMessage());
		      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	@DeleteMapping("/deleteproduct/{product_id}")
	public ResponseEntity<?> deleteteProduct(@PathVariable("product_id") String product_id){
		try{
			if(status.matches("Login"))
		    {
				service1.deleteProductById(product_id);
				return new ResponseEntity<>("Product Id:"+product_id+" is deleted.",HttpStatus.OK);
		    }else 
	        {
				throw new Exception("You have to Login.");
			}
		}catch(Exception e) {
			  System.out.println(e.getMessage());
		      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/viewbuyerscart")
	public ResponseEntity<?> viewbuyers(){
		try{
			if(status.matches("Login"))
		    {
				List<Cart> clist=service2.fetchCartList();
				List<Cart> clist1=new ArrayList<>();
				if(clist!=null) {
					for(Cart c:clist) {
					  if(c.getSeller_id().matches(user)) {
			              System.out.println(c+"\n");
			              clist1.add(c);
			          }}
			        return new ResponseEntity<>(clist1,HttpStatus.OK);
				}else {
					throw new Exception("Buyers cart is empty");
				}
		    }else 
	        {
				throw new Exception("You have to Login.");
			}
		}catch(Exception e) {
			  System.out.println(e.getMessage());
		      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/logout")
	public ResponseEntity<?> logoutSeller() {
		try {
			if (status.matches("Login")) {
				System.out.println("Logout Successful");
				status = "";
				user="";
				return new ResponseEntity<>("Logout Successful", HttpStatus.OK);
			} else {
				throw new Exception("You have to Login.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
}
