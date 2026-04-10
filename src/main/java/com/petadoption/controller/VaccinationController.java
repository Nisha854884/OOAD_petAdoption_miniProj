package com.petadoption.controller;

import com.petadoption.model.Vaccination;
import com.petadoption.service.VaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vaccinations")
public class VaccinationController {

    @Autowired
    private VaccinationService vaccinationService;

    // GET ALL VACCINATIONS (Admin/Staff)
    @GetMapping("/all")
    public List<Vaccination> getAllVaccinations(@RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return null;
        }
        return vaccinationService.getAllVaccinations();
    }

    // GET VACCINATION BY ID (Admin/Staff/Adopter)
    @GetMapping("/{id}")
    public Optional<Vaccination> getVaccinationById(@PathVariable int id) {
        return vaccinationService.getVaccinationById(id);
    }

    // GET VACCINATIONS BY PET ID (Admin/Staff/Adopter)
    @GetMapping("/pet/{petId}")
    public List<Vaccination> getVaccinationsByPetId(@PathVariable int petId) {
        return vaccinationService.getVaccinationsByPetId(petId);
    }

    // GET VACCINATIONS BY NAME (Admin/Staff)
    @GetMapping("/name/{vaccineName}")
    public List<Vaccination> getVaccinationsByName(@PathVariable String vaccineName, @RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return null;
        }
        return vaccinationService.getVaccinationsByName(vaccineName);
    }

    // GET VACCINATIONS DUE FOR RENEWAL (Admin/Staff)
    @GetMapping("/renewal/due")
    public List<Vaccination> getVaccinationsDueForRenewal(@RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return null;
        }
        return vaccinationService.getVaccinationsDueForRenewal();
    }

    // ADD VACCINATION (Admin/Staff)
    @PostMapping("/add")
    public String addVaccination(@RequestBody Vaccination vaccination, @RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return "Access Denied";
        }
        vaccinationService.addVaccination(vaccination);
        return "Vaccination record added successfully";
    }

    // UPDATE VACCINATION (Admin/Staff)
    @PutMapping("/update/{id}")
    public Vaccination updateVaccination(@PathVariable int id,
                                         @RequestBody Vaccination vaccination,
                                         @RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return null;
        }
        return vaccinationService.updateVaccination(id, vaccination);
    }

    // DELETE VACCINATION (Admin/Staff)
    @DeleteMapping("/delete/{id}")
    public String deleteVaccination(@PathVariable int id, @RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return "Access Denied";
        }
        if (vaccinationService.deleteVaccination(id)) {
            return "Vaccination record deleted successfully";
        }
        return "Vaccination record not found";
    }

    // GET VACCINATION STATUS SUMMARY (Admin/Staff)
    @GetMapping("/summary/status")
    public List<Object[]> getVaccinationStatusSummary(@RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return null;
        }
        return vaccinationService.getVaccinationStatusSummary();
    }
}
