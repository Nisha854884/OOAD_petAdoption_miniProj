package com.petadoption.controller;

import com.petadoption.model.Medical_Record;
import com.petadoption.service.Medical_RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medical-records")
public class Medical_RecordController {

    @Autowired
    private Medical_RecordService medicalRecordService;

    // GET ALL MEDICAL RECORDS (Admin/Staff)
    @GetMapping("/all")
    public List<Medical_Record> getAllMedicalRecords(@RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return null;
        }
        return medicalRecordService.getAllMedicalRecords();
    }

    // GET MEDICAL RECORD BY ID (Admin/Staff)
    @GetMapping("/{id}")
    public Optional<Medical_Record> getMedicalRecordById(@PathVariable int id, @RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return Optional.empty();
        }
        return medicalRecordService.getMedicalRecordById(id);
    }

    // GET RECORDS BY PET ID (Admin/Staff)
    @GetMapping("/pet/{petId}")
    public List<Medical_Record> getRecordsByPetId(@PathVariable int petId, @RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return null;
        }
        return medicalRecordService.getRecordsByPetId(petId);
    }

    // GET RECORDS BY VET ID (Admin/Staff)
    @GetMapping("/vet/{vetId}")
    public List<Medical_Record> getRecordsByVetId(@PathVariable int vetId, @RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return null;
        }
        return medicalRecordService.getRecordsByVetId(vetId);
    }

    // ADD MEDICAL RECORD (Admin/Staff)
    @PostMapping("/add")
    public String addMedicalRecord(@RequestBody Medical_Record record, @RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return "Access Denied";
        }
        medicalRecordService.addMedicalRecord(record);
        return "Medical record added successfully";
    }

    // UPDATE MEDICAL RECORD (Admin/Staff)
    @PutMapping("/update/{id}")
    public Medical_Record updateMedicalRecord(@PathVariable int id,
                                              @RequestBody Medical_Record record,
                                              @RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return null;
        }
        return medicalRecordService.updateMedicalRecord(id, record);
    }

    // DELETE MEDICAL RECORD (Admin)
    @DeleteMapping("/delete/{id}")
    public String deleteMedicalRecord(@PathVariable int id, @RequestParam String role) {
        if (!role.equals("Admin")) {
            return "Access Denied";
        }
        if (medicalRecordService.deleteMedicalRecord(id)) {
            return "Medical record deleted successfully";
        }
        return "Medical record not found";
    }

    // GET AVAILABLE PETS WITH VACCINATION DETAILS
    @GetMapping("/pets/available/vaccinations")
    public List<Object[]> getAvailablePetsWithVaccinationDetails(@RequestParam String role) {
        if (!role.equals("Admin") && !role.equals("Staff")) {
            return null;
        }
        return medicalRecordService.getAvailablePetsWithVaccinationDetails();
    }
}
