package com.petadoption.service;

import com.petadoption.dto.PetDTO;
import com.petadoption.model.Pet;
import com.petadoption.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;
    // VIEW ALL PETS (Admin/Staff)
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    // VIEW AVAILABLE PETS (Adopter)
    public List<Pet> getAvailablePets() {
        return petRepository.findByAdoptionStatus("Available");
    }

    // ADD PET
    public Pet addPet(Pet pet) {
        return petRepository.save(pet);
    }

    // UPDATE PET
    public Pet updatePet(int id, Pet updatedPet) {
        Pet pet = petRepository.findById(id).orElse(null);

        if (pet != null) {
            pet.setName(updatedPet.getName());
            pet.setSpecies(updatedPet.getSpecies());
            pet.setBreed(updatedPet.getBreed());
            pet.setAge(updatedPet.getAge());
            pet.setGender(updatedPet.getGender());
            pet.setHealthStatus(updatedPet.getHealthStatus());
            pet.setShelter(updatedPet.getShelter());

            return petRepository.save(pet);
        }
        return null;
    }

    // DELETE PET
    public void deletePet(int id) {
        petRepository.deleteById(id);
    }

    public List<PetDTO> getAvailablePetDTOs() {
        return petRepository.findByAdoptionStatus("Available")
                .stream()
                .map(p -> new PetDTO(p.getName(), p.getSpecies(), p.getBreed(), p.getAge(), 
                                     p.getGender(), p.getHealthStatus(), p.getAdoptionStatus(), 
                                     p.getShelter() != null ? p.getShelter().getShelterId() : 0))
                .toList();
    }
}