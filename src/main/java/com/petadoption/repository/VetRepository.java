package com.petadoption.repository;

import com.petadoption.model.Vet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VetRepository extends JpaRepository<Vet, Integer> {

    // Find vets by specialization
    java.util.List<Vet> findBySpecialization(String specialization);

    // Find vets by shelter
    java.util.List<Vet> findByShelterShelterId(int shelterId);
}
