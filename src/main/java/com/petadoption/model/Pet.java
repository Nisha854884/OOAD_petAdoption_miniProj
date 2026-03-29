package com.petadoption.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int petId;

    private String name;
    private String species;
    private String breed;
    private Integer age;
    private String gender;
    private String healthStatus;

    private String adoptionStatus = "Available";

    private Integer shelterId;

    @OneToMany(mappedBy = "pet")
    private List<Adoption> adoptions;

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

    public Integer getShelterId() { return shelterId; }
    public void setShelterId(Integer shelterId) { this.shelterId = shelterId; }

    public List<Adoption> getAdoptions() { return adoptions; }
    public void setAdoptions(List<Adoption> adoptions) { this.adoptions = adoptions; }
}