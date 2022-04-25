package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {

}
