package com.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.Cart;
import com.ecommerce.model.Customer;
import com.ecommerce.model.Product;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.CustomerRepository;
import com.ecommerce.service.CartService;
import com.ecommerce.service.CustomerService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.SellerService;
import com.tourism.model.TPackages;

@RestController
@RequestMapping("/Customer")
public class CustomerController {

	@Autowired
	CustomerRepository repo;

	@Autowired
	CartRepository repo1;

	@Autowired
	CustomerService service;

	@Autowired
	ProductService service1;

	@Autowired
	CartService service2;

	@Autowired
	SellerService service3;
	
	@Autowired
	JdbcTemplate jd;
	
	private String status = "";
	private String user = "";

	@RequestMapping("/test")
	public String test() {
		return "Test Customer Controller";
	}

	@GetMapping("/register")
	public ResponseEntity<?> registerCustomer(@RequestBody Customer c) throws Exception {
		try {
			if (!repo.existsById(c.getCustomer_id())) {
				if (c.getCustomer_id().matches("[a-zA-Z]{5}[0-9]{3}")) {
					if (c.getCustomer_phone_number().length() == 10
							&& c.getCustomer_phone_number().matches("[0-9]{10}")) {
						if (c.getCustomer_email().matches("[A-za-z0-9~!@#$%^&*()_+{}:<>?|]{3,10}" + "@gmail.com")) {
							if (c.getCustomer_password().matches(
									"^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8}$")
									&& c.getCustomer_password().length() == 8) {
								service.saveCustomer(c);
								return new ResponseEntity<>("Registered Successfully", HttpStatus.OK);
							} else {
								throw new Exception("Enter the vaild Password. eg:Abc@1def");
							}
						} else {
							throw new Exception("Enter the vaild Email Id");
						}
					} else {
						throw new Exception("Enter the valid phonenumber");
					}
				} else {
					throw new Exception("Enter the valid CustomerId. eg:Abcde123");
				}
			} else {
				throw new Exception("This Customer Id is already exist.Create another");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/login")
	public ResponseEntity<?> loginCustomer(@RequestBody Customer c) {

		try {
			if (repo.existsById(c.getCustomer_id())) {
				if (repo.getById(c.getCustomer_id()).getCustomer_password().matches(c.getCustomer_password())) {
					System.out.println("Login Successful");
					status = "Login";
					user = c.getCustomer_id();
					return new ResponseEntity<>("Login Successful", HttpStatus.OK);
				} else {
					throw new Exception("Customer Password is Incorrect.");
				}
			} else {
				throw new Exception("CustomerId is not existed.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/view")
	public ResponseEntity<?> viewcustomer() {
		try {
			if (status.matches("Login")) {
				Customer cus = service.getCustomerById(user);
				return new ResponseEntity<>(cus, HttpStatus.OK);
			} else {
				throw new Exception("You have to Login.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateCustomer(@RequestBody Customer c) {
		try {
			if (status.matches("Login")) {
				if (c.getCustomer_id().matches(user)) {
					service.saveCustomer(c);
					return new ResponseEntity<>(c, HttpStatus.OK);
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

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteCsutomer() {
		try {
			if (status.matches("Login")) {
				service.deleteCustomerById(user);
				status = "";
				user = "";
				return new ResponseEntity<>("Admin Id:" + user + " is deleted.", HttpStatus.OK);
			} else {
				throw new Exception("You have to Login.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/view/products")
	public ResponseEntity<?> viewProduct() {
		try {
			if (status.matches("Login")) {
				List<Product> plist = service1.fetchProductList();
				for (Product p : plist)
					System.out.println(p + "\n");
				return new ResponseEntity<>(plist, HttpStatus.OK);
			} else {
				throw new Exception("You have to Login.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/selectproduct/{product_id}")
	public ResponseEntity<?> selectProduct(@PathVariable("product_id") String product_id) {
		try {
			if (status.matches("Login")) {
				int count=0;
				String str="select * from cart where customer_id='"+user+"' and product_id='"+product_id+"'";
				List<Cart> clist= jd.query(str,new BeanPropertyRowMapper(Cart.class));
				if(!clist.isEmpty()) {
					count=clist.get(0).getProduct_quantity();
				}
				Product p = service1.getProductById(product_id);
				service2.saveCart(product_id, p.getSeller_id(), p.getProduct_name(), p.getProduct_price(), user,
						service.getCustomerById(user).getCustomer_name(),
						service3.getSellerById(p.getSeller_id()).getSeller_name(),count+1);
				 return new ResponseEntity<>("Your selected product is selected",HttpStatus.OK);
			} else {
				throw new Exception("You have to Login.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/view/cart")
	public ResponseEntity<?> findbdetails() {
		try {
			if (status.matches("Login")) {
				List<Cart> clist = service2.fetchCartList();
				List<Cart> clist1 = new ArrayList<>();
				if (clist != null) {
					for (Cart c : clist) {
						if (c.getCustomer_id().matches(user)) {
							System.out.println(c + "\n");
							clist1.add(c);
						}
					}
					return new ResponseEntity<>(clist1, HttpStatus.OK);
				} else {
					return new ResponseEntity<>("No product in your cart.", HttpStatus.NOT_FOUND);
				}
			} else {
				throw new Exception("You have to Login.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/logout")
	public ResponseEntity<?> logoutCustomer() {
		try {
			if (status.matches("Login")) {
				System.out.println("Logout Successful");
				status = "";
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
