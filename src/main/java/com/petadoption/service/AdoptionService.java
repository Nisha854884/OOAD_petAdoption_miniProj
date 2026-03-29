package com.petadoption.service;

import com.petadoption.model.Adoption;
import com.petadoption.model.Pet;
import com.petadoption.model.Adopter;
import com.petadoption.repository.AdoptionRepository;
import com.petadoption.repository.PetRepository;
import com.petadoption.repository.AdopterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdoptionService {

    @Autowired
    private AdoptionRepository adoptionRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private AdopterRepository adopterRepository;

    // REQUEST ADOPTION (Adopter)
    public Adoption requestAdoption(int petId, int adopterId) {

        Pet pet = petRepository.findById(petId).orElse(null);
        Adopter adopter = adopterRepository.findById(adopterId).orElse(null);

        if (pet == null || adopter == null) {
            return null;
        }

        Adoption adoption = new Adoption();
        adoption.setPet(pet);
        adoption.setAdopter(adopter);
        adoption.setAdoptionDate(LocalDate.now());
        adoption.setStatus("Pending");

        return adoptionRepository.save(adoption);
    }

    // VIEW OWN ADOPTIONS (Adopter)
    public List<Adoption> getAdoptionsByAdopter(int adopterId) {
        return adoptionRepository.findByAdopter_AdopterId(adopterId);
    }

    // APPROVE / REJECT (Admin)
    public Adoption updateStatus(int adoptionId, String status) {

        Adoption adoption = adoptionRepository.findById(adoptionId).orElse(null);

        if (adoption != null) {
            adoption.setStatus(status);
            return adoptionRepository.save(adoption);
        }
        return null;
    }
}