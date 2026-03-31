package com.petadoption.controller;

import com.petadoption.model.Admin;
import com.petadoption.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // GET ALL ADMINS (Admin only)
    @GetMapping("/all")
    public List<Admin> getAllAdmins(@RequestParam String role) {
        if (!role.equals("Admin")) {
            return null;
        }
        return adminService.getAllAdmins();
    }

    // GET ADMIN BY ID (Admin only)
    @GetMapping("/{id}")
    public Optional<Admin> getAdminById(@PathVariable int id, @RequestParam String role) {
        if (!role.equals("Admin")) {
            return Optional.empty();
        }
        return adminService.getAdminById(id);
    }

    // GET ADMIN BY USER ID (Admin only)
    @GetMapping("/user/{userId}")
    public Optional<Admin> getAdminByUserId(@PathVariable int userId, @RequestParam String role) {
        if (!role.equals("Admin")) {
            return Optional.empty();
        }
        return adminService.getAdminByUserId(userId);
    }

    // GET ADMINS BY DEPARTMENT (Admin only)
    @GetMapping("/department/{dept}")
    public List<Admin> getAdminsByDepartment(@PathVariable String dept, @RequestParam String role) {
        if (!role.equals("Admin")) {
            return null;
        }
        return adminService.getAdminsByDepartment(dept);
    }

    // ADD ADMIN (Admin only)
    @PostMapping("/add")
    public String addAdmin(@RequestBody Admin admin, @RequestParam String role) {
        if (!role.equals("Admin")) {
            return "Access Denied";
        }
        adminService.addAdmin(admin);
        return "Admin added successfully";
    }

    // UPDATE ADMIN (Admin only)
    @PutMapping("/update/{id}")
    public Admin updateAdmin(@PathVariable int id,
                             @RequestBody Admin admin,
                             @RequestParam String role) {
        if (!role.equals("Admin")) {
            return null;
        }
        return adminService.updateAdmin(id, admin);
    }

    // DELETE ADMIN (Admin only)
    @DeleteMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable int id, @RequestParam String role) {
        if (!role.equals("Admin")) {
            return "Access Denied";
        }
        if (adminService.deleteAdmin(id)) {
            return "Admin deleted successfully";
        }
        return "Admin not found";
    }
}
