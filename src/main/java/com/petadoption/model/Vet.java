package com.petadoption.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "vet")
public class Vet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vetId;

    @Column(nullable = false)
    @NotBlank(message = "Vet name cannot be blank")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Column(name = "clinic_name")
    @NotBlank(message = "Clinic name cannot be blank")
    @Size(min = 2, max = 100, message = "Clinic name must be between 2 and 100 characters")
    private String clinicName;

    @Column(nullable = false)
    @NotBlank(message = "Phone cannot be blank")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be a 10-digit number")
    private String phone;

    @Column(nullable = false)
    @NotBlank(message = "Specialization cannot be blank")
    @Size(min = 2, max = 100, message = "Specialization must be between 2 and 100 characters")
    private String specialization;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    @OneToMany(mappedBy = "vet", cascade = CascadeType.ALL)
    private List<Medical_Record> medicalRecords;

    // Constructors
    public Vet() {}

    public Vet(String name, String clinicName, String phone, String specialization) {
        this.name = name;
        this.clinicName = clinicName;
        this.phone = phone;
        this.specialization = specialization;
    }

    // Getters & Setters
    public int getVetId() {
        return vetId;
    }

    public void setVetId(int vetId) {
        this.vetId = vetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public List<Medical_Record> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(List<Medical_Record> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }
}
