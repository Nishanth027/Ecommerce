package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Seller;

public interface SellerRepository extends JpaRepository<Seller, String> {

}
