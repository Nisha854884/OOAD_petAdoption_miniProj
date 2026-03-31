package com.petadoption.controller;

import com.petadoption.model.Staff;
import com.petadoption.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    // GET ALL STAFF (Admin only)
    @GetMapping("/all")
    public List<Staff> getAllStaff(@RequestParam String role) {
        if (!role.equals("Admin")) {
            return null;
        }
        return staffService.getAllStaff();
    }

    // GET STAFF BY ID (Admin only)
    @GetMapping("/{id}")
    public Optional<Staff> getStaffById(@PathVariable int id, @RequestParam String role) {
        if (!role.equals("Admin")) {
            return Optional.empty();
        }
        return staffService.getStaffById(id);
    }

    // GET STAFF BY USER ID (Admin/Staff)
    @GetMapping("/user/{userId}")
    public Optional<Staff> getStaffByUserId(@PathVariable int userId, @RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return Optional.empty();
        }
        return staffService.getStaffByUserId(userId);
    }

    // GET STAFF BY SHELTER (Admin/Staff)
    @GetMapping("/shelter/{shelterId}")
    public List<Staff> getStaffByShelterId(@PathVariable int shelterId, @RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return null;
        }
        return staffService.getStaffByShelterId(shelterId);
    }

    // GET STAFF BY POSITION (Admin/Staff)
    @GetMapping("/position/{position}")
    public List<Staff> getStaffByPosition(@PathVariable String position, @RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return null;
        }
        return staffService.getStaffByPosition(position);
    }

    // ADD STAFF (Admin only)
    @PostMapping("/add")
    public String addStaff(@RequestBody Staff staff, @RequestParam String role) {
        if (!role.equals("Admin")) {
            return "Access Denied";
        }
        staffService.addStaff(staff);
        return "Staff added successfully";
    }

    // UPDATE STAFF (Admin only)
    @PutMapping("/update/{id}")
    public Staff updateStaff(@PathVariable int id,
                             @RequestBody Staff staff,
                             @RequestParam String role) {
        if (!role.equals("Admin")) {
            return null;
        }
        return staffService.updateStaff(id, staff);
    }

    // DELETE STAFF (Admin only)
    @DeleteMapping("/delete/{id}")
    public String deleteStaff(@PathVariable int id, @RequestParam String role) {
        if (!role.equals("Admin")) {
            return "Access Denied";
        }
        if (staffService.deleteStaff(id)) {
            return "Staff deleted successfully";
        }
        return "Staff not found";
    }
}
