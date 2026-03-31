package com.petadoption.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import java.util.List;

@Entity
@Table(name = "pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int petId;

    @NotBlank(message = "Pet name cannot be blank")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Species cannot be blank")
    @Size(min = 2, max = 50, message = "Species must be between 2 and 50 characters")
    private String species;

    @NotBlank(message = "Breed cannot be blank")
    @Size(min = 2, max = 100, message = "Breed must be between 2 and 100 characters")
    private String breed;

    @Min(value = 0, message = "Age cannot be negative")
    private Integer age;

    @NotBlank(message = "Gender cannot be blank")
    private String gender;

    @NotBlank(message = "Health status cannot be blank")
    private String healthStatus;

    @NotBlank(message = "Adoption status cannot be blank")
    private String adoptionStatus = "Available";

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    private List<Adoption> adoptions;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    private List<Medical_Record> medicalRecords;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    private List<Vaccination> vaccinations;

    public Pet() {}

    public int getPetId() { return petId; }
    public void setPetId(int petId) { this.petId = petId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getHealthStatus() { return healthStatus; }
    public void setHealthStatus(String healthStatus) { this.healthStatus = healthStatus; }

    public String getAdoptionStatus() { return adoptionStatus; }
    public void setAdoptionStatus(String adoptionStatus) { this.adoptionStatus = adoptionStatus; }

    public Shelter getShelter() { return shelter; }
    public void setShelter(Shelter shelter) { this.shelter = shelter; }

    public List<Adoption> getAdoptions() { return adoptions; }
    public void setAdoptions(List<Adoption> adoptions) { this.adoptions = adoptions; }

    public List<Medical_Record> getMedicalRecords() { return medicalRecords; }
    public void setMedicalRecords(List<Medical_Record> medicalRecords) { this.medicalRecords = medicalRecords; }

    public List<Vaccination> getVaccinations() { return vaccinations; }
    public void setVaccinations(List<Vaccination> vaccinations) { this.vaccinations = vaccinations; }
}