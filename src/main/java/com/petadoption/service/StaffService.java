package com.petadoption.service;

import com.petadoption.model.Staff;
import com.petadoption.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    // Get all staff
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    // Get staff by ID
    public Optional<Staff> getStaffById(int id) {
        return staffRepository.findById(id);
    }

    // Get staff by user ID
    public Optional<Staff> getStaffByUserId(int userId) {
        return staffRepository.findByUserUserId(userId);
    }

    // Get staff by shelter
    public List<Staff> getStaffByShelterId(int shelterId) {
        return staffRepository.findByShelterShelterId(shelterId);
    }

    // Get staff by position
    public List<Staff> getStaffByPosition(String position) {
        return staffRepository.findByPosition(position);
    }

    // Add new staff
    public Staff addStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    // Update staff
    public Staff updateStaff(int id, Staff updatedStaff) {
        Optional<Staff> staffOpt = staffRepository.findById(id);

        if (staffOpt.isPresent()) {
            Staff staff = staffOpt.get();
            staff.setPosition(updatedStaff.getPosition());
            staff.setPhone(updatedStaff.getPhone());
            staff.setEmail(updatedStaff.getEmail());
            staff.setShelter(updatedStaff.getShelter());

            return staffRepository.save(staff);
        }
        return null;
    }

    // Delete staff
    public boolean deleteStaff(int id) {
        if (staffRepository.existsById(id)) {
            staffRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
