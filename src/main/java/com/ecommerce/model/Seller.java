package com.ecommerce.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
public class Seller implements Serializable{

	@Id
	String seller_id;
	String seller_name;
	String seller_email;
	String seller_phone_number;
	String seller_address;
	String seller_password;
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public String getSeller_name() {
		return seller_name;
	}
	public void setSeller_name(String seller_name) {
		this.seller_name = seller_name;
	}
	public String getSeller_email() {
		return seller_email;
	}
	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}
	public String getSeller_phone_number() {
		return seller_phone_number;
	}
	public void setSeller_phone_number(String seller_phone_number) {
		this.seller_phone_number = seller_phone_number;
	}
	public String getSeller_address() {
		return seller_address;
	}
	public void setSeller_address(String seller_address) {
		this.seller_address = seller_address;
	}
	public String getSeller_password() {
		return seller_password;
	}
	public void setSeller_password(String seller_password) {
		this.seller_password = seller_password;
	}
	@Override
	public String toString() {
		return "Seller [seller_id=" + seller_id + ", seller_name=" + seller_name + ", seller_email=" + seller_email
				+ ", seller_phone_number=" + seller_phone_number + ", seller_address=" + seller_address
				+ ", seller_password=" + seller_password + "]";
	}
}
