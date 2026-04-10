package com.petadoption.patterns.builder;

import com.petadoption.model.Pet;
import com.petadoption.model.Shelter;

/**
 * DESIGN PATTERN: Builder
 *
 * PetBuilder provides a fluent API for constructing {@link Pet} objects.
 * Instead of calling a constructor with many parameters (error-prone and
 * hard to read), callers chain setter methods and finish with {@code build()}.
 *
 * Problem solved:
 *  - Pet has 8+ fields; optional fields (age, shelter) lead to multiple
 *    overloaded constructors or null-heavy code.
 *  - Builder separates construction from representation.
 *
 * GRASP principles satisfied:
 *  - Creator  : PetBuilder creates Pet instances; it is the natural factory
 *               for the Pet product.
 *  - High Cohesion : All pet-construction logic lives in one place.
 *
 * Example usage:
 * <pre>
 *   Pet pet = new PetBuilder()
 *       .name("Buddy")
 *       .species("Dog")
 *       .breed("Labrador")
 *       .age(3)
 *       .gender("Male")
 *       .healthStatus("Healthy")
 *       .build();
 * </pre>
 */
public class PetBuilder {

    private String  name;
    private String  species;
    private String  breed;
    private Integer age;
    private String  gender;
    private String  healthStatus;
    private String  adoptionStatus = "Available";   // sensible default
    private Shelter shelter;

    public PetBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PetBuilder species(String species) {
        this.species = species;
        return this;
    }

    public PetBuilder breed(String breed) {
        this.breed = breed;
        return this;
    }

    public PetBuilder age(Integer age) {
        this.age = age;
        return this;
    }

    public PetBuilder gender(String gender) {
        this.gender = gender;
        return this;
    }

    public PetBuilder healthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
        return this;
    }

    public PetBuilder adoptionStatus(String adoptionStatus) {
        this.adoptionStatus = adoptionStatus;
        return this;
    }

    public PetBuilder shelter(Shelter shelter) {
        this.shelter = shelter;
        return this;
    }

    /**
     * Validates required fields and constructs the {@link Pet}.
     *
     * @return a fully initialised Pet object
     * @throws IllegalStateException if any required field is missing
     */
    public Pet build() {
        if (name   == null || name.isBlank())   throw new IllegalStateException("Pet name is required");
        if (species == null || species.isBlank()) throw new IllegalStateException("Pet species is required");
        if (breed  == null || breed.isBlank())  throw new IllegalStateException("Pet breed is required");
        if (gender == null || gender.isBlank())  throw new IllegalStateException("Pet gender is required");
        if (healthStatus == null || healthStatus.isBlank()) throw new IllegalStateException("Health status is required");

        Pet pet = new Pet();
        pet.setName(name);
        pet.setSpecies(species);
        pet.setBreed(breed);
        pet.setAge(age);
        pet.setGender(gender);
        pet.setHealthStatus(healthStatus);
        pet.setAdoptionStatus(adoptionStatus);
        pet.setShelter(shelter);
        return pet;
    }
}
