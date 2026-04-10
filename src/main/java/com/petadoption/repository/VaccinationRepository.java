package com.petadoption.repository;

import com.petadoption.model.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VaccinationRepository extends JpaRepository<Vaccination, Integer> {

    // Find vaccinations by pet
    List<Vaccination> findByPetPetId(int petId);

    // Find vaccinations by vaccine name
    List<Vaccination> findByVaccineName(String vaccineName);

    // Find vaccinations due for renewal
    @Query("SELECT v FROM Vaccination v WHERE v.nextDueDate < CURRENT_DATE AND v.status = 'Pending'")
    List<Vaccination> findVaccinationsDueForRenewal();

    // Find vaccinations by pet and status
    List<Vaccination> findByPetPetIdAndStatus(int petId, String status);

    // Get vaccination status summary
    @Query("SELECT v.pet.petId, v.pet.name, COUNT(v.vaccinationId) as totalVaccinations, " +
           "SUM(CASE WHEN v.status = 'Completed' THEN 1 ELSE 0 END) as completedVaccinations, " +
           "SUM(CASE WHEN v.status = 'Pending' THEN 1 ELSE 0 END) as pendingVaccinations " +
           "FROM Vaccination v GROUP BY v.pet.petId, v.pet.name")
    List<Object[]> getVaccinationStatusSummary();
}
