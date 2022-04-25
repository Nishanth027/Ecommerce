package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.Admin;
import com.ecommerce.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRepository repo;

	@Override
	public void saveAdmin(Admin a) {
		repo.save(a);
	}

	@Override
	public List<Admin> fetchAdminList() {
		return repo.findAll();
	}

	@Override
	public Admin updateAdmin(Admin a, String admin_id) {
		Admin a1 = repo.getById(admin_id);
		a1.setAdmin_name(a.getAdmin_name());
		a1.setAdmin_email(a.getAdmin_email());
		a1.setAdmin_phone_number(a.getAdmin_phone_number());
		a1.setAdmin_password(a.getAdmin_password());
		return a1;
	}

	@Override
	public void deleteAdminById(String admin_id) {
		repo.deleteById(admin_id);
	}

	@Override
	public Admin getAdminById(String admin_id) {
		return repo.getById(admin_id);
	}
}
