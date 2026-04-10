package com.petadoption.repository;

import com.petadoption.model.Medical_Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface Medical_RecordRepository extends JpaRepository<Medical_Record, Integer> {

    // Find medical records by pet
    List<Medical_Record> findByPetPetId(int petId);

    // Find medical records by vet
    List<Medical_Record> findByVetVetId(int vetId);

    // Find medical records in a date range
    List<Medical_Record> findByVisitDateBetween(LocalDate startDate, LocalDate endDate);

    // Find medical records with specific diagnosis
    List<Medical_Record> findByDiagnosisContainingIgnoreCase(String diagnosis);

    // Pets pending adoption with vaccination details (NESTED + JOIN)
    @Query("SELECT DISTINCT p.petId, p.name, p.species, p.breed, COUNT(v.vaccinationId) as vaccinationCount " +
           "FROM Pet p LEFT JOIN p.vaccinations v " +
           "WHERE p.adoptionStatus = 'Available' " +
           "GROUP BY p.petId, p.name, p.species, p.breed " +
           "ORDER BY p.name")
    List<Object[]> getAvailablePetsWithVaccinationDetails();
}
