package com.petadoption.model;

import jakarta.persistence.*;

@Entity
@Table(name = "adopter")
public class Adopter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adopterId;

    private String name;
    private String phone;
    private String address;
    private String experienceLevel;

    public Adopter() {}

    // Getters & Setters
    public int getAdopterId() { return adopterId; }
    public void setAdopterId(int adopterId) { this.adopterId = adopterId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }
}