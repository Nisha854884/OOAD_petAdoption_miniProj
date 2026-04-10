package com.petadoption.repository;

import com.petadoption.model.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShelterRepository extends JpaRepository<Shelter, Integer> {

    // Find shelter by name
    Shelter findByName(String name);

    // Shelter capacity utilization (CORRELATED SUBQUERY)
    @Query("SELECT s.shelterId, s.name, s.capacity, COUNT(p) as totalPets, " +
           "ROUND(CAST(COUNT(p) AS FLOAT) / s.capacity * 100, 2) as utilizationPercent " +
           "FROM Shelter s LEFT JOIN s.pets p GROUP BY s.shelterId, s.name, s.capacity")
    List<Object[]> getShelterCapacityUtilization();

    // Find shelters with available capacity
    @Query("SELECT s FROM Shelter s WHERE s.capacity > (SELECT COUNT(p) FROM Pet p WHERE p.shelter.shelterId = s.shelterId)")
    List<Shelter> findSheltersWithAvailableCapacity();
}
