package com.ecommerce.controller;

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
import com.ecommerce.repository.AdminRepository;
import com.ecommerce.service.AdminService;
import com.ecommerce.service.CustomerService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.SellerService;
import com.ecommerce.model.Customer;
import com.ecommerce.model.Product;
import com.ecommerce.model.Seller;


@RestController
@RequestMapping("/Admin")
public class AdminController {

	@Autowired
	AdminService service;
	
	@Autowired
	CustomerService service1;
	
	@Autowired
	SellerService service2;
	
	@Autowired
	ProductService service3;
	
	@Autowired
	AdminRepository repo;
	
	@Autowired
	SellerService repo1;
	
	private String status="";
	private String admin="";
	@RequestMapping("/test")
	public String test() {
		return "Test Admin Controller";
	}
	@RequestMapping("/register")
	public ResponseEntity<?> registerAdmin(@RequestBody Admin a){
	    try{
	    	if(!repo.existsById(a.getAdmin_id())){
	    		if(a.getAdmin_id().matches("[a-zA-Z]{5}[0-9]{3}")) {
	    		if(a.getAdmin_phone_number().length()==10 && a.getAdmin_phone_number().matches("[0-9]{10}")){
	    			if(a.getAdmin_email().matches("[A-za-z0-9~!@#$%^&*()_+{}:<>?|]{3,10}"+"@gmail.com")) {
	    				if(a.getAdmin_password().matches("^(?=.*[0-9])"+"(?=.*[a-z])(?=.*[A-Z])"+"(?=.*[@#$%^&+=])"+"(?=\\S+$).{8,20}$") && a.getAdmin_password().length()==8) {
	    					service.saveAdmin(a);
	    		    		return new ResponseEntity<>("Registered Successfully",HttpStatus.OK);	
	    				}
	    				else {
	    					throw new Exception("Enter the vaild Password");	
	    				}
	    			}
	    			else {
	    				throw new Exception("Enter the vaild Email Id");
	    			}
	    		}
	    		else {
	    			throw new Exception("Enter the valid phonenumber");
	    		}
	    	}
	    	 else {
	    			throw new Exception("Enter the valid AdminId. eg:Abcde123");
	    		}
	    	}
	    	else {
	    		throw new Exception("This Admin Id is already exist.Create another");
	    	}
	    }catch(Exception e) 
	    {
	    	System.out.println(e.getMessage());
	    	return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	    }
	}
	@GetMapping("/login")
	public ResponseEntity<?> loginAdmin(@RequestBody Admin a){
	    try{
	    	if(repo.existsById(a.getAdmin_id())) {
	    		if(repo.getById(a.getAdmin_id()).getAdmin_password().matches(a.getAdmin_password())){
	             System.out.println("Login Successful");
	             status="Login";
	             admin=a.getAdmin_id();
		    	 return new ResponseEntity<>("Login Successful",HttpStatus.OK);	
	    		}else 
	            {
	              throw new Exception("Admin Password is Incorrect.");
		        }
	    	}else {
	    		throw new Exception("AdminId is not existed.");
	    	}
	    }catch(Exception e) {
	    	System.out.println(e.getMessage());
	    	return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	    }
	}
	@RequestMapping("/view/admins")
	public ResponseEntity<?> listAdmin(){
		try{
			if(status.matches("Login"))
		    {
	        	List<Admin> adlist=service.fetchAdminList();
	        	for(Admin a:adlist)
	        	System.out.println(a+"\n");
	        	return new ResponseEntity<>(adlist,HttpStatus.OK);
		    }
			else 
	        {
				throw new Exception("You have to Login.");
			}
		}catch(Exception e) {
			  System.out.println(e.getMessage());
		      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getadmin/{adminid}")
	public ResponseEntity<?> findAdmin(@PathVariable("adminid") String adminid){
		try{
			if(status.matches("Login"))
		    {
				Admin a=service.getAdminById(adminid);
				if(a!=null) 
				  return new ResponseEntity<>(a,HttpStatus.OK);
			    else 		   
				  return new ResponseEntity<>("AdminId is not found",HttpStatus.NOT_FOUND);
		    }else 
	        {
				throw new Exception("You have to Login.");
			}
		}catch(Exception e) {
			  System.out.println(e.getMessage());
		      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	@PutMapping("/updateadmin")
	public ResponseEntity<?> updateAdmin(@RequestBody Admin a){
		try{
			if(status.matches("Login"))
		    {
		     service.saveAdmin(a);
		     return new ResponseEntity<>(a,HttpStatus.OK);
		    }
		    else 
		    {
			  throw new Exception("You have to Login.");
		    }
	       }catch(Exception e) {
	    	    System.out.println(e.getMessage());
		    	return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	       }
	}

	@DeleteMapping("/deleteadmin")
	public ResponseEntity<?> deleteAdmin() {
		try {
			if (status.matches("Login")) {
				service.deleteAdminById(admin);
				status="";
				admin="";
				return new ResponseEntity<>("Admin Id:" + admin + " is deleted.", HttpStatus.OK);
			} else {
				throw new Exception("You have to Login.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	@RequestMapping("/view/customers")
	public ResponseEntity<?> listCustomers(){
		try{
			if(status.matches("Login"))
		    {
				List<Customer> cuslist=service1.fetchCustomerList();
				for(Customer c:cuslist)
					System.out.println(c+"\n");
				return new ResponseEntity<>(cuslist,HttpStatus.OK);
		    }else {
		    	throw new Exception("You have to Login.");
		    }
		}catch(Exception e) {
			  System.out.println(e.getMessage());
		      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping("/getcustomer/{CustomerId}")
	public ResponseEntity<?> findCustomer(@PathVariable("CustomerId") String CustomerId){
		try{
			if(status.matches("Login"))
		    {
				Customer c=service1.getCustomerById(CustomerId);
				if(c!=null) {
					return new ResponseEntity<>(c,HttpStatus.OK);
		        }else {
			    return new ResponseEntity<>("CustomerId is not found.",HttpStatus.NOT_FOUND);
		        }
		    }else {
		    	throw new Exception("You have to Login.");
		    }
		}catch(Exception e) {
			  System.out.println(e.getMessage());
		      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/view/sellers")
	public ResponseEntity<?> viewsellerproduct(){
		try{
			if(status.matches("Login"))
		    {
				List<Seller> slist=service2.fetchSellerList();
				for(Seller s:slist)
					System.out.println(s+"\n");
				return new ResponseEntity<>(slist,HttpStatus.OK);
		    }else 
	        {
				throw new Exception("You have to Login.");
			}
		}catch(Exception e) {
			  System.out.println(e.getMessage());
		      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/addseller")
	public ResponseEntity<?> addseller(@RequestBody Seller s) {
		try {
			if(!repo.existsById(s.getSeller_id())){
	    		if(s.getSeller_id().matches("[a-zA-Z]{5}[0-9]{3}")) {
	    		if(s.getSeller_phone_number().length()==10 && s.getSeller_phone_number().matches("[0-9]{10}")){
	    			if(s.getSeller_email().matches("[A-za-z0-9~!@#$%^&*()_+{}:<>?|]{3,10}"+"@gmail.com")) {
	    				if(s.getSeller_password().matches("^(?=.*[0-9])"+"(?=.*[a-z])(?=.*[A-Z])"+"(?=.*[@#$%^&+=])"+"(?=\\S+$).{8,20}$") && s.getSeller_password().length()==8) {
	    					service2.saveSeller(s);
	    		    		return new ResponseEntity<>("Seller addedd Successfully",HttpStatus.OK);	
	    				}
	    				else {
	    					throw new Exception("Enter the vaild Password");	
	    				}
	    			}
	    			else {
	    				throw new Exception("Enter the vaild Email Id");
	    			}
	    		}
	    		else {
	    			throw new Exception("Enter the valid phonenumber");
	    		}
	    	}
	    	 else {
	    			throw new Exception("Enter the valid SellerId. eg:Abcde123");
	    		}
	    	}
	    	else {
	    		throw new Exception("This Seller Id is already exist.Create another");
	    	}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/getseller/{seller_id}")
	public ResponseEntity<?> getProduct(@PathVariable("seller_id") String seller_id){
		try{
			if(status.matches("Login"))
		    {
				Seller s=service2.getSellerById(seller_id); 
				return new ResponseEntity<>(s,HttpStatus.OK);
		    }else 
	        {
				throw new Exception("You have to Login.");
			}
		}catch(Exception e) {
			  System.out.println(e.getMessage());
		      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/view/products")
	public ResponseEntity<?> viewProduct(){
		try{
			if(status.matches("Login"))
		    {
				List<Product> plist=service3.fetchProductList();
	        	for(Product p:plist)
	        	  System.out.println(p+"\n");
	        	return new ResponseEntity<>(plist,HttpStatus.OK);
		    }else 
	        {
				throw new Exception("You have to Login.");
			}
		}catch(Exception e) {
			  System.out.println(e.getMessage());
		      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/view/sellerproduct/{seller_id}")
	public ResponseEntity<?> viewsellerproduct(@PathVariable("seller_id") String seller_id){
		try{
			if(status.matches("Login"))
		    {
			    List<Product> plist=service3.fetchProductList().stream().filter(p->p.getSeller_id()==seller_id).toList();
			    for(Product p:plist)
		        	 System.out.println(p+"\n");
		        return new ResponseEntity<>(plist,HttpStatus.OK);
		    }else 
	        {
				throw new Exception("You have to Login.");
			}
		}catch(Exception e) {
			  System.out.println(e.getMessage());
		      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	@DeleteMapping("/deleteseller/{seller_id}")
	public ResponseEntity<?> deleteteProduct(@PathVariable("seller_id") String seller_id){
		try{
			if(status.matches("Login"))
		    {
				service2.deleteSellerById(seller_id);
				return new ResponseEntity<>("Seller Id:"+seller_id+" is deleted.",HttpStatus.OK);
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
	public ResponseEntity<?> logoutAdmin(){
		try{
			if(status.matches("Login"))
		    {
			         System.out.println("Logout Successful");
		             status="";
			    	 return new ResponseEntity<>("Logout Successful",HttpStatus.OK);
		    }else {
		    	throw new Exception("You have to Login.");
		    }
		}catch(Exception e) {
			  System.out.println(e.getMessage());
		      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
      	
	}
}
