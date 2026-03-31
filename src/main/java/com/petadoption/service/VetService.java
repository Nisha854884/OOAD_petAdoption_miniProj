package com.petadoption.service;

import com.petadoption.model.Vet;
import com.petadoption.repository.VetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VetService {

    @Autowired
    private VetRepository vetRepository;

    // Get all vets
    public List<Vet> getAllVets() {
        return vetRepository.findAll();
    }

    // Get vet by ID
    public Optional<Vet> getVetById(int id) {
        return vetRepository.findById(id);
    }

    // Get vets by specialization
    public List<Vet> getVetsBySpecialization(String specialization) {
        return vetRepository.findBySpecialization(specialization);
    }

    // Get vets by shelter
    public List<Vet> getVetsByShelterId(int shelterId) {
        return vetRepository.findByShelterShelterId(shelterId);
    }

    // Add new vet
    public Vet addVet(Vet vet) {
        return vetRepository.save(vet);
    }

    // Update vet
    public Vet updateVet(int id, Vet updatedVet) {
        Optional<Vet> vetOpt = vetRepository.findById(id);

        if (vetOpt.isPresent()) {
            Vet vet = vetOpt.get();
            vet.setName(updatedVet.getName());
            vet.setClinicName(updatedVet.getClinicName());
            vet.setPhone(updatedVet.getPhone());
            vet.setSpecialization(updatedVet.getSpecialization());
            vet.setShelter(updatedVet.getShelter());

            return vetRepository.save(vet);
        }
        return null;
    }

    // Delete vet
    public boolean deleteVet(int id) {
        if (vetRepository.existsById(id)) {
            vetRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
