package com.petadoption.service;

import com.petadoption.model.Medical_Record;
import com.petadoption.repository.Medical_RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class Medical_RecordService {

    @Autowired
    private Medical_RecordRepository medicalRecordRepository;

    // Get all medical records
    public List<Medical_Record> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    // Get medical record by ID
    public Optional<Medical_Record> getMedicalRecordById(int id) {
        return medicalRecordRepository.findById(id);
    }

    // Get medical records for a pet
    public List<Medical_Record> getRecordsByPetId(int petId) {
        return medicalRecordRepository.findByPetPetId(petId);
    }

    // Get medical records by vet
    public List<Medical_Record> getRecordsByVetId(int vetId) {
        return medicalRecordRepository.findByVetVetId(vetId);
    }

    // Get medical records in date range
    public List<Medical_Record> getRecordsByDateRange(LocalDate startDate, LocalDate endDate) {
        return medicalRecordRepository.findByVisitDateBetween(startDate, endDate);
    }

    // Get medical records by diagnosis
    public List<Medical_Record> getRecordsByDiagnosis(String diagnosis) {
        return medicalRecordRepository.findByDiagnosisContainingIgnoreCase(diagnosis);
    }

    // Add new medical record
    public Medical_Record addMedicalRecord(Medical_Record record) {
        return medicalRecordRepository.save(record);
    }

    // Update medical record
    public Medical_Record updateMedicalRecord(int id, Medical_Record updatedRecord) {
        Optional<Medical_Record> recordOpt = medicalRecordRepository.findById(id);

        if (recordOpt.isPresent()) {
            Medical_Record record = recordOpt.get();
            record.setPet(updatedRecord.getPet());
            record.setVet(updatedRecord.getVet());
            record.setVisitDate(updatedRecord.getVisitDate());
            record.setDiagnosis(updatedRecord.getDiagnosis());
            record.setTreatment(updatedRecord.getTreatment());
            record.setAdditionalNotes(updatedRecord.getAdditionalNotes());

            return medicalRecordRepository.save(record);
        }
        return null;
    }

    // Delete medical record
    public boolean deleteMedicalRecord(int id) {
        if (medicalRecordRepository.existsById(id)) {
            medicalRecordRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Get pets pending adoption with vaccination details
    public List<Object[]> getAvailablePetsWithVaccinationDetails() {
        return medicalRecordRepository.getAvailablePetsWithVaccinationDetails();
    }
}
