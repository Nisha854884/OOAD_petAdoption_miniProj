package com.petadoption.controller;

import com.petadoption.model.Adoption;
import com.petadoption.service.AdoptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adoption")
public class AdoptionController {

    @Autowired
    private AdoptionService adoptionService;

    // VIEW ALL ADOPTIONS (Admin/Staff)
    @GetMapping("/all")
    public List<Adoption> getAllAdoptions(@RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return List.of();
        }
        return adoptionService.getAllAdoptions();
    }

    // VIEW ONE ADOPTION (Admin/Staff)
    @GetMapping("/{id}")
    public Adoption getAdoptionById(@PathVariable int id, @RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return null;
        }
        return adoptionService.getAdoptionById(id);
    }

    // REQUEST ADOPTION (Adopter only)
    @PostMapping("/request")
    public String requestAdoption(@RequestParam int petId,
                                 @RequestParam int adopterId,
                                 @RequestParam String role) {

        if (!role.equals("Adopter")) {
            return "Only adopters can request adoption";
        }

        adoptionService.requestAdoption(petId, adopterId);
        return "Adoption request submitted";
    }

    // VIEW OWN ADOPTIONS
    @GetMapping("/my/{adopterId}")
    public List<Adoption> getMyAdoptions(@PathVariable int adopterId) {
        return adoptionService.getAdoptionsByAdopter(adopterId);
    }

    // APPROVE / REJECT (Admin only)
    @PutMapping("/update/{id}")
    public String updateStatus(@PathVariable int id,
                              @RequestParam String status,
                              @RequestParam String role) {

        if (!role.equals("Admin")) {
            return "Only admin can approve/reject";
        }

        adoptionService.updateStatus(id, status);
        return "Status updated";
    }
}