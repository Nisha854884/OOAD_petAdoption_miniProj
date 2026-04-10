package com.petadoption.repository;

import com.petadoption.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Integer> {

    // Find staff by user ID
    Optional<Staff> findByUserUserId(int userId);

    // Find staff by shelter
    List<Staff> findByShelterShelterId(int shelterId);

    // Find staff by position
    List<Staff> findByPosition(String position);
}
