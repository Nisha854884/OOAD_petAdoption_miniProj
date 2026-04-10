package com.petadoption.patterns.prototype;

import com.petadoption.model.Pet;
import com.petadoption.model.Shelter;

/**
 * DESIGN PATTERN: Prototype
 *
 * CloneablePet wraps a {@link Pet} entity and implements Java's {@link Cloneable}
 * interface to provide deep-copy semantics.
 *
 * Problem solved:
 *  - When adding multiple similar pets to the system (e.g. a litter of kittens)
 *    staff should be able to clone an existing pet template and adjust only the
 *    fields that differ, rather than filling in every field from scratch.
 *  - Avoids re-querying the database just to get a base object.
 *
 * GRASP principle satisfied:
 *  - Low Coupling : CloneablePet holds its own copy logic; Pet entity stays clean.
 *  - Protected Variations : changes to Pet fields are isolated in one clone method.
 *
 * Example usage:
 * <pre>
 *   CloneablePet template = new CloneablePet(existingPet);
 *   Pet kittenTwo = template.clone().toPet();
 *   kittenTwo.setName("Whiskers Jr.");
 *   petService.addPet(kittenTwo);
 * </pre>
 */
public class CloneablePet implements Cloneable {

    private String  name;
    private String  species;
    private String  breed;
    private Integer age;
    private String  gender;
    private String  healthStatus;
    private String  adoptionStatus;
    private Shelter shelter;   // shallow reference — intentional (shared shelter)

    /** Construct from an existing Pet entity. */
    public CloneablePet(Pet source) {
        this.name           = source.getName();
        this.species        = source.getSpecies();
        this.breed          = source.getBreed();
        this.age            = source.getAge();
        this.gender         = source.getGender();
        this.healthStatus   = source.getHealthStatus();
        this.adoptionStatus = source.getAdoptionStatus();
        this.shelter        = source.getShelter();
    }

    /** Deep-copies all primitive/String fields; shelter reference is shared. */
    @Override
    public CloneablePet clone() {
        try {
            return (CloneablePet) super.clone();
        } catch (CloneNotSupportedException e) {
            // Should never happen — this class implements Cloneable
            throw new AssertionError("CloneablePet clone failed", e);
        }
    }

    /** Convert back to a JPA-managed Pet (ID is left as 0 — new entity). */
    public Pet toPet() {
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

    // -----------------------------------------------------------------------
    // Fluent mutators so callers can adjust clone before calling toPet()
    // -----------------------------------------------------------------------
    public CloneablePet withName(String name)                 { this.name = name;                 return this; }
    public CloneablePet withAge(Integer age)                   { this.age = age;                   return this; }
    public CloneablePet withHealthStatus(String healthStatus) { this.healthStatus = healthStatus; return this; }
    public CloneablePet withAdoptionStatus(String status)     { this.adoptionStatus = status;     return this; }
    public CloneablePet withGender(String gender)             { this.gender = gender;             return this; }
}
