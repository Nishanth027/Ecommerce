package com.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.model.Admin;

@Service
public interface AdminService {

	void saveAdmin(Admin a);

	List<Admin> fetchAdminList();

	Admin updateAdmin(Admin a, String admin_id);

	void deleteAdminById(String admin_id);

	Admin getAdminById(String admin_id);
}
