package com.petadoption.service;

import com.petadoption.model.Adoption;
import com.petadoption.repository.PetRepository;
import com.petadoption.repository.AdoptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private AdoptionRepository adoptionRepository;

    // 1. COUNT PETS BY STATUS
    public List<Object[]> countPetsByStatus() {
        return petRepository.countPetsByStatus();
    }

    // 2. ADOPTER + PET (JOIN)
    public List<Object[]> adopterPetDetails() {
        return adoptionRepository.getAdopterPetDetails();
    }

    // 3. PENDING ADOPTIONS
    public List<Adoption> pendingAdoptions() {
        return adoptionRepository.getPendingAdoptions();
    }

    // 4. AVG AGE BY BREED
    public List<Object[]> avgAgeByBreed() {
        return petRepository.avgAgeByBreed();
    }
}