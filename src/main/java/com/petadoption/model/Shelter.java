package com.petadoption.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "shelter")
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shelterId;

    @Column(nullable = false)
    @NotBlank(message = "Shelter name cannot be blank")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Column(nullable = false)
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    @Column(name = "contact_no")
    @NotBlank(message = "Contact number cannot be blank")
    @Pattern(regexp = "^[0-9]{10}$", message = "Contact number must be a 10-digit number")
    private String contactNo;

    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pet> pets;

    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Staff> staff;

    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Vet> vets;

    // Constructors
    public Shelter() {}

    public Shelter(String name, Integer capacity, String contactNo) {
        this.name = name;
        this.capacity = capacity;
        this.contactNo = contactNo;
    }

    // Getters & Setters
    public int getShelterId() {
        return shelterId;
    }

    public void setShelterId(int shelterId) {
        this.shelterId = shelterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Staff> getStaff() {
        return staff;
    }

    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }

    public List<Vet> getVets() {
        return vets;
    }

    public void setVets(List<Vet> vets) {
        this.vets = vets;
    }
}
