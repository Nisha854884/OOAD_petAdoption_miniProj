package com.petadoption.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class AdoptionDTO {

    @NotNull(message = "Pet ID cannot be null")
    private int petId;

    @NotNull(message = "Adopter ID cannot be null")
    private int adopterId;

    @NotNull(message = "Adoption date cannot be null")
    private LocalDate adoptionDate;

    @Size(max = 50, message = "Status cannot exceed 50 characters")
    private String status; // "Pending", "Approved", "Rejected", "Completed"

    public AdoptionDTO() {}

    public AdoptionDTO(int petId, int adopterId, LocalDate adoptionDate, String status) {
        this.petId = petId;
        this.adopterId = adopterId;
        this.adoptionDate = adoptionDate;
        this.status = status;
    }

    public int getPetId() { return petId; }
    public void setPetId(int petId) { this.petId = petId; }

    public int getAdopterId() { return adopterId; }
    public void setAdopterId(int adopterId) { this.adopterId = adopterId; }

    public LocalDate getAdoptionDate() { return adoptionDate; }
    public void setAdoptionDate(LocalDate adoptionDate) { this.adoptionDate = adoptionDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
