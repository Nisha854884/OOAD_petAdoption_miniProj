package com.petadoption.controller;

import com.petadoption.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // 1. COUNT PETS BY STATUS
    @GetMapping("/pet-status")
    public List<Object[]> countPets() {
        return reportService.countPetsByStatus();
    }

    // 2. ADOPTER + PET
    @GetMapping("/adoptions")
    public List<Object[]> adopterPets() {
        return reportService.adopterPetDetails();
    }

    // 3. PENDING ADOPTIONS
    @GetMapping("/pending")
    public List<?> pending() {
        return reportService.pendingAdoptions();
    }

    // 4. AVG AGE BY BREED
    @GetMapping("/avg-age")
    public List<Object[]> avgAge() {
        return reportService.avgAgeByBreed();
    }
}