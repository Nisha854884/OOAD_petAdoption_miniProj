package com.petadoption.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "adoption")
public class Adoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adoptionId;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "adopter_id")
    private Adopter adopter;

    private LocalDate adoptionDate;

    private String status = "Pending";

    public Adoption() {}

    public int getAdoptionId() { return adoptionId; }
    public void setAdoptionId(int adoptionId) { this.adoptionId = adoptionId; }

    public Pet getPet() { return pet; }
    public void setPet(Pet pet) { this.pet = pet; }

    public Adopter getAdopter() { return adopter; }
    public void setAdopter(Adopter adopter) { this.adopter = adopter; }

    public LocalDate getAdoptionDate() { return adoptionDate; }
    public void setAdoptionDate(LocalDate adoptionDate) { this.adoptionDate = adoptionDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}