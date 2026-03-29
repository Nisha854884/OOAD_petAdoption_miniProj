package com.petadoption.controller;

import com.petadoption.dto.PetDTO;
import com.petadoption.model.Pet;
import com.petadoption.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    // GET ALL PETS (Admin/Staff)
    @GetMapping("/all")
    public List<Pet> getAllPets(@RequestParam String role) {

        if (!role.equals("Admin") && !role.equals("Staff")) {
            return null;
        }

        return petService.getAllPets();
    }

    // GET AVAILABLE PETS (Adopter)
    @GetMapping("/available")
    public List<Pet> getAvailablePets() {
        return petService.getAvailablePets();
    }

    // ADD PET (Admin/Staff)
    @PostMapping("/add")
    public String addPet(@RequestBody Pet pet,
                         @RequestParam String role) {

        if (!role.equals("Admin") && !role.equals("Staff")) {
            return "Access Denied";
        }

        petService.addPet(pet);
        return "Pet added successfully";
    }

    // UPDATE PET (Admin/Staff)
    @PutMapping("/update/{id}")
    public Pet updatePet(@PathVariable int id,
                         @RequestBody Pet pet,
                         @RequestParam String role) {

        if (!role.equals("Admin") && !role.equals("Staff")) {
            return null;
        }

        return petService.updatePet(id, pet);
    }

    // DELETE PET (Admin/Staff)
    @DeleteMapping("/delete/{id}")
    public String deletePet(@PathVariable int id,
                            @RequestParam String role) {

        if (!role.equals("Admin") && !role.equals("Staff")) {
            return "Access Denied";
        }

        petService.deletePet(id);
        return "Pet deleted successfully";
    }

    @GetMapping("/available-dto")
    public List<PetDTO> getAvailablePetDTOs() {
        return petService.getAvailablePetDTOs();
    }
}