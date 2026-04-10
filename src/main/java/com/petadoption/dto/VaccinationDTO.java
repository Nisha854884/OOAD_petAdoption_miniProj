package com.petadoption.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class VaccinationDTO {

    @NotNull(message = "Pet ID cannot be null")
    private int petId;

    @NotBlank(message = "Vaccine name cannot be blank")
    @Size(min = 2, max = 100, message = "Vaccine name must be between 2 and 100 characters")
    private String vaccineName;

    @NotNull(message = "Vaccination date cannot be null")
    private LocalDate vaccinationDate;

    private LocalDate nextDueDate;

    @Size(max = 50, message = "Status cannot exceed 50 characters")
    private String status; // "Completed", "Pending"

    public VaccinationDTO() {}

    public VaccinationDTO(int petId, String vaccineName, LocalDate vaccinationDate, LocalDate nextDueDate) {
        this.petId = petId;
        this.vaccineName = vaccineName;
        this.vaccinationDate = vaccinationDate;
        this.nextDueDate = nextDueDate;
    }

    public int getPetId() { return petId; }
    public void setPetId(int petId) { this.petId = petId; }

    public String getVaccineName() { return vaccineName; }
    public void setVaccineName(String vaccineName) { this.vaccineName = vaccineName; }

    public LocalDate getVaccinationDate() { return vaccinationDate; }
    public void setVaccinationDate(LocalDate vaccinationDate) { this.vaccinationDate = vaccinationDate; }

    public LocalDate getNextDueDate() { return nextDueDate; }
    public void setNextDueDate(LocalDate nextDueDate) { this.nextDueDate = nextDueDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
