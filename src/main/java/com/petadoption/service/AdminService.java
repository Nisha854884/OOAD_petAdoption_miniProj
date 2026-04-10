package com.petadoption.service;

import com.petadoption.model.Admin;
import com.petadoption.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // Get all admins
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // Get admin by ID
    public Optional<Admin> getAdminById(int id) {
        return adminRepository.findById(id);
    }

    // Get admin by user ID
    public Optional<Admin> getAdminByUserId(int userId) {
        return adminRepository.findByUserUserId(userId);
    }

    // Get admins by department
    public List<Admin> getAdminsByDepartment(String department) {
        return adminRepository.findByDepartment(department);
    }

    // Add new admin
    public Admin addAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    // Update admin
    public Admin updateAdmin(int id, Admin updatedAdmin) {
        Optional<Admin> adminOpt = adminRepository.findById(id);

        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            admin.setDepartment(updatedAdmin.getDepartment());
            admin.setPermissions(updatedAdmin.getPermissions());

            return adminRepository.save(admin);
        }
        return null;
    }

    // Delete admin
    public boolean deleteAdmin(int id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
