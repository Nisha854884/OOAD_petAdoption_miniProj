package com.petadoption.service;

import com.petadoption.model.Vaccination;
import com.petadoption.repository.VaccinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VaccinationService {

    @Autowired
    private VaccinationRepository vaccinationRepository;

    // Get all vaccinations
    public List<Vaccination> getAllVaccinations() {
        return vaccinationRepository.findAll();
    }

    // Get vaccination by ID
    public Optional<Vaccination> getVaccinationById(int id) {
        return vaccinationRepository.findById(id);
    }

    // Get vaccinations for a pet
    public List<Vaccination> getVaccinationsByPetId(int petId) {
        return vaccinationRepository.findByPetPetId(petId);
    }

    // Get vaccinations by vaccine name
    public List<Vaccination> getVaccinationsByName(String vaccineName) {
        return vaccinationRepository.findByVaccineName(vaccineName);
    }

    // Get vaccinations due for renewal
    public List<Vaccination> getVaccinationsDueForRenewal() {
        return vaccinationRepository.findVaccinationsDueForRenewal();
    }

    // Get vaccinations by pet and status
    public List<Vaccination> getVaccinationsByPetAndStatus(int petId, String status) {
        return vaccinationRepository.findByPetPetIdAndStatus(petId, status);
    }

    // Add new vaccination
    public Vaccination addVaccination(Vaccination vaccination) {
        return vaccinationRepository.save(vaccination);
    }

    // Update vaccination
    public Vaccination updateVaccination(int id, Vaccination updatedVaccination) {
        Optional<Vaccination> vaccOpt = vaccinationRepository.findById(id);

        if (vaccOpt.isPresent()) {
            Vaccination vaccination = vaccOpt.get();
            vaccination.setPet(updatedVaccination.getPet());
            vaccination.setVaccineName(updatedVaccination.getVaccineName());
            vaccination.setVaccinationDate(updatedVaccination.getVaccinationDate());
            vaccination.setNextDueDate(updatedVaccination.getNextDueDate());
            vaccination.setStatus(updatedVaccination.getStatus());

            return vaccinationRepository.save(vaccination);
        }
        return null;
    }

    // Delete vaccination
    public boolean deleteVaccination(int id) {
        if (vaccinationRepository.existsById(id)) {
            vaccinationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Get vaccination status summary
    public List<Object[]> getVaccinationStatusSummary() {
        return vaccinationRepository.getVaccinationStatusSummary();
    }
}
