package com.petadoption.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;

public class PetDTO {

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
    private String gender; // "Male", "Female"

    @NotBlank(message = "Health status cannot be blank")
    private String healthStatus; // "Healthy", "Injured", "Sick", "Recovering"

    @NotBlank(message = "Adoption status cannot be blank")
    private String adoptionStatus; // "Available", "Adopted", "Pending"

    private int shelterId;

    public PetDTO() {}

    public PetDTO(String name, String species, String breed, Integer age, String gender, 
                  String healthStatus, String adoptionStatus, int shelterId) {
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.healthStatus = healthStatus;
        this.adoptionStatus = adoptionStatus;
        this.shelterId = shelterId;
    }

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

    public int getShelterId() { return shelterId; }
    public void setShelterId(int shelterId) { this.shelterId = shelterId; }
}