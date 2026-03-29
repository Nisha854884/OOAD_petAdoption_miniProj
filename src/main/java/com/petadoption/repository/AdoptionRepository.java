package com.petadoption.repository;

import com.petadoption.model.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdoptionRepository extends JpaRepository<Adoption, Integer> {

    // Find adoptions by adopter (using relationship)
    List<Adoption> findByAdopter_AdopterId(int adopterId);

    // JOIN: Adopter name + Pet name + Status
    @Query("SELECT a.adopter.name, a.pet.name, a.status FROM Adoption a")
    List<Object[]> getAdopterPetDetails();

    // Pending adoptions
    @Query("SELECT a FROM Adoption a WHERE a.status = 'Pending'")
    List<Adoption> getPendingAdoptions();
}