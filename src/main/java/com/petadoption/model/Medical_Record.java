package com.petadoption.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "medical_record")
public class Medical_Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recordId;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    @JsonIgnoreProperties({"adoptions", "medicalRecords", "vaccinations"})
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "vet_id", nullable = false)
    @JsonIgnoreProperties({"medicalRecords", "shelter"})
    private Vet vet;

    @Column(name = "visit_date", nullable = false)
    @NotNull(message = "Visit date cannot be null")
    private LocalDate visitDate;

    @Column(nullable = false)
    @NotBlank(message = "Diagnosis cannot be blank")
    @Size(min = 3, max = 255, message = "Diagnosis must be between 3 and 255 characters")
    private String diagnosis;

    @Column(nullable = false)
    @NotBlank(message = "Treatment cannot be blank")
    @Size(min = 3, max = 500, message = "Treatment must be between 3 and 500 characters")
    private String treatment;

    private String additionalNotes;

    // Constructors
    public Medical_Record() {}

    public Medical_Record(Pet pet, Vet vet, LocalDate visitDate, String diagnosis, String treatment) {
        this.pet = pet;
        this.vet = vet;
        this.visitDate = visitDate;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    // Getters & Setters
    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Vet getVet() {
        return vet;
    }

    public void setVet(Vet vet) {
        this.vet = vet;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }
}
