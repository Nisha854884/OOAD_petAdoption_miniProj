package com.petadoption.controller;

import com.petadoption.model.Vet;
import com.petadoption.service.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vets")
public class VetController {

    @Autowired
    private VetService vetService;

    // GET ALL VETS (Admin/Staff)
    @GetMapping("/all")
    public List<Vet> getAllVets(@RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return null;
        }
        return vetService.getAllVets();
    }

    // GET VET BY ID (Admin/Staff)
    @GetMapping("/{id}")
    public Optional<Vet> getVetById(@PathVariable int id, @RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return Optional.empty();
        }
        return vetService.getVetById(id);
    }

    // GET VETS BY SPECIALIZATION (Admin/Staff)
    @GetMapping("/specialization/{spec}")
    public List<Vet> getVetsBySpecialization(@PathVariable String spec, @RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return null;
        }
        return vetService.getVetsBySpecialization(spec);
    }

    // GET VETS BY SHELTER (Admin/Staff)
    @GetMapping("/shelter/{shelterId}")
    public List<Vet> getVetsByShelterId(@PathVariable int shelterId, @RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return null;
        }
        return vetService.getVetsByShelterId(shelterId);
    }

    // ADD VET (Admin)
    @PostMapping("/add")
    public String addVet(@RequestBody Vet vet, @RequestParam String role) {
        if (!role.equals("Admin")) {
            return "Access Denied";
        }
        vetService.addVet(vet);
        return "Vet added successfully";
    }

    // UPDATE VET (Admin)
    @PutMapping("/update/{id}")
    public Vet updateVet(@PathVariable int id,
                         @RequestBody Vet vet,
                         @RequestParam String role) {
        if (!role.equals("Admin")) {
            return null;
        }
        return vetService.updateVet(id, vet);
    }

    // DELETE VET (Admin)
    @DeleteMapping("/delete/{id}")
    public String deleteVet(@PathVariable int id, @RequestParam String role) {
        if (!role.equals("Admin")) {
            return "Access Denied";
        }
        if (vetService.deleteVet(id)) {
            return "Vet deleted successfully";
        }
        return "Vet not found";
    }
}
