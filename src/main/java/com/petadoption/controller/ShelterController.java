package com.petadoption.controller;

import com.petadoption.model.Shelter;
import com.petadoption.service.ShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shelters")
public class ShelterController {

    @Autowired
    private ShelterService shelterService;

    // GET ALL SHELTERS (Admin/Staff)
    @GetMapping("/all")
    public List<Shelter> getAllShelters(@RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return null;
        }
        return shelterService.getAllShelters();
    }

    // GET SHELTER BY ID (Admin/Staff)
    @GetMapping("/{id}")
    public Optional<Shelter> getShelterById(@PathVariable int id, @RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return Optional.empty();
        }
        return shelterService.getShelterById(id);
    }

    // ADD SHELTER (Admin)
    @PostMapping("/add")
    public String addShelter(@RequestBody Shelter shelter, @RequestParam String role) {
        if (!role.equals("Admin")) {
            return "Access Denied";
        }
        shelterService.addShelter(shelter);
        return "Shelter added successfully";
    }

    // UPDATE SHELTER (Admin)
    @PutMapping("/update/{id}")
    public Shelter updateShelter(@PathVariable int id,
                                 @RequestBody Shelter shelter,
                                 @RequestParam String role) {
        if (!role.equals("Admin")) {
            return null;
        }
        return shelterService.updateShelter(id, shelter);
    }

    // DELETE SHELTER (Admin)
    @DeleteMapping("/delete/{id}")
    public String deleteShelter(@PathVariable int id, @RequestParam String role) {
        if (!role.equals("Admin")) {
            return "Access Denied";
        }
        if (shelterService.deleteShelter(id)) {
            return "Shelter deleted successfully";
        }
        return "Shelter not found";
    }

    // GET SHELTER CAPACITY UTILIZATION
    @GetMapping("/capacity/utilization")
    public List<Object[]> getShelterCapacityUtilization(@RequestParam String role) {
        if (!role.equals("Admin")) {
            return null;
        }
        return shelterService.getShelterCapacityUtilization();
    }

    // GET SHELTERS WITH AVAILABLE CAPACITY
    @GetMapping("/capacity/available")
    public List<Shelter> getSheltersWithAvailableCapacity(@RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return null;
        }
        return shelterService.findSheltersWithAvailableCapacity();
    }
}
