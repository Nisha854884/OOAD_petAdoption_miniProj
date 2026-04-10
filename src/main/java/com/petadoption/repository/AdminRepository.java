package com.petadoption.repository;

import com.petadoption.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    // Find admin by user ID
    Optional<Admin> findByUserUserId(int userId);

    // Find admins by department
    java.util.List<Admin> findByDepartment(String department);
}
