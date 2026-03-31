package com.petadoption.service;

import com.petadoption.model.Shelter;
import com.petadoption.repository.ShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShelterService {

    @Autowired
    private ShelterRepository shelterRepository;

    // Get all shelters
    public List<Shelter> getAllShelters() {
        return shelterRepository.findAll();
    }

    // Get shelter by ID
    public Optional<Shelter> getShelterById(int id) {
        return shelterRepository.findById(id);
    }

    // Find shelter by name
    public Shelter findByName(String name) {
        return shelterRepository.findByName(name);
    }

    // Add new shelter
    public Shelter addShelter(Shelter shelter) {
        return shelterRepository.save(shelter);
    }

    // Update shelter
    public Shelter updateShelter(int id, Shelter updatedShelter) {
        Optional<Shelter> shelterOpt = shelterRepository.findById(id);

        if (shelterOpt.isPresent()) {
            Shelter shelter = shelterOpt.get();
            shelter.setName(updatedShelter.getName());
            shelter.setCapacity(updatedShelter.getCapacity());
            shelter.setContactNo(updatedShelter.getContactNo());

            return shelterRepository.save(shelter);
        }
        return null;
    }

    // Delete shelter
    public boolean deleteShelter(int id) {
        if (shelterRepository.existsById(id)) {
            shelterRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Get shelter capacity utilization
    public List<Object[]> getShelterCapacityUtilization() {
        return shelterRepository.getShelterCapacityUtilization();
    }

    // Find shelters with available capacity
    public List<Shelter> findSheltersWithAvailableCapacity() {
        return shelterRepository.findSheltersWithAvailableCapacity();
    }
}
