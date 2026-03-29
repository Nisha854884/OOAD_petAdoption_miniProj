package com.petadoption.repository;

import com.petadoption.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // ✅ IMPORTANT

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Integer> {

    // Only available pets (for adopters)
    List<Pet> findByAdoptionStatus(String status);

    // COUNT PETS BY STATUS
    @Query("SELECT p.adoptionStatus, COUNT(p) FROM Pet p GROUP BY p.adoptionStatus")
    List<Object[]> countPetsByStatus();

    // AVG AGE BY BREED
    @Query("SELECT p.breed, AVG(p.age) FROM Pet p GROUP BY p.breed")
    List<Object[]> avgAgeByBreed();
}