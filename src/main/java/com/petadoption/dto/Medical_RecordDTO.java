package com.petadoption.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class Medical_RecordDTO {

    @NotNull(message = "Pet ID cannot be null")
    private int petId;

    @NotNull(message = "Vet ID cannot be null")
    private int vetId;

    @NotNull(message = "Visit date cannot be null")
    private LocalDate visitDate;

    @NotBlank(message = "Diagnosis cannot be blank")
    @Size(min = 3, max = 255, message = "Diagnosis must be between 3 and 255 characters")
    private String diagnosis;

    @NotBlank(message = "Treatment cannot be blank")
    @Size(min = 3, max = 500, message = "Treatment must be between 3 and 500 characters")
    private String treatment;

    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    private String additionalNotes;

    public Medical_RecordDTO() {}

    public Medical_RecordDTO(int petId, int vetId, LocalDate visitDate, String diagnosis, String treatment) {
        this.petId = petId;
        this.vetId = vetId;
        this.visitDate = visitDate;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    public int getPetId() { return petId; }
    public void setPetId(int petId) { this.petId = petId; }

    public int getVetId() { return vetId; }
    public void setVetId(int vetId) { this.vetId = vetId; }

    public LocalDate getVisitDate() { return visitDate; }
    public void setVisitDate(LocalDate visitDate) { this.visitDate = visitDate; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public String getTreatment() { return treatment; }
    public void setTreatment(String treatment) { this.treatment = treatment; }

    public String getAdditionalNotes() { return additionalNotes; }
    public void setAdditionalNotes(String additionalNotes) { this.additionalNotes = additionalNotes; }
}
