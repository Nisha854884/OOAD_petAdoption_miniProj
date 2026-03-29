package com.petadoption.dto;

public class PetDTO {

    private String name;
    private String breed;
    private String adoptionStatus;

    public PetDTO(String name, String breed, String adoptionStatus) {
        this.name = name;
        this.breed = breed;
        this.adoptionStatus = adoptionStatus;
    }

    public String getName() { return name; }
    public String getBreed() { return breed; }
    public String getAdoptionStatus() { return adoptionStatus; }
}