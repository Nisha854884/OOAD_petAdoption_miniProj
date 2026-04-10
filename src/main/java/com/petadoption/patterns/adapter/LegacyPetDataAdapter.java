package com.petadoption.patterns.adapter;

import com.petadoption.model.Pet;
import com.petadoption.model.Shelter;

/**
 * DESIGN PATTERN: Adapter (Object Adapter)
 *
 * LegacyPetDataAdapter bridges the gap between a legacy flat-record format
 * (CSV / plain string map) and the system's {@link Pet} domain model.
 *
 * Problem solved:
 *  - External shelters or import tools may provide pet data as flat strings
 *    (e.g., CSV exports) that don't directly match the Pet entity fields.
 *  - Without an Adapter, every import point would need its own translation
 *    logic, scattering conversion code across the codebase.
 *
 * GRASP principles satisfied:
 *  - Low Coupling  : Pet entity knows nothing about legacy formats.
 *  - Indirection   : The Adapter stands between the legacy source and the
 *                    domain model, absorbing format changes in one place.
 *  - Pure Fabrication : Adapter is not part of the domain; it is a technical
 *                       helper class invented to keep the design clean.
 *
 * LegacyRecord format (comma-separated):
 *   name, species, breed, age, gender, healthStatus, shelterName, shelterCapacity, shelterContact
 *
 * Example:
 * <pre>
 *   String csv = "Max,Dog,Labrador,3,Male,Healthy,City Shelter,50,9876543210";
 *   Pet pet = new LegacyPetDataAdapter(csv).toPet();
 *   petService.addPet(pet);
 * </pre>
 */
public class LegacyPetDataAdapter {

    /** Raw legacy string record. */
    private final String legacyRecord;

    public LegacyPetDataAdapter(String legacyRecord) {
        if (legacyRecord == null || legacyRecord.isBlank()) {
            throw new IllegalArgumentException("Legacy record cannot be null or blank");
        }
        this.legacyRecord = legacyRecord;
    }

    /**
     * Converts the legacy flat record to a {@link Pet} domain object.
     * The caller is responsible for persisting the returned Pet.
     *
     * @return a new Pet instance populated from the legacy record
     * @throws IllegalArgumentException if the record format is invalid
     */
    public Pet toPet() {
        String[] fields = legacyRecord.split(",", -1);
        if (fields.length < 6) {
            throw new IllegalArgumentException(
                "Legacy record must have at least 6 fields: " +
                "name, species, breed, age, gender, healthStatus");
        }

        Pet pet = new Pet();
        pet.setName(fields[0].trim());
        pet.setSpecies(fields[1].trim());
        pet.setBreed(fields[2].trim());
        pet.setAge(parseAge(fields[3].trim()));
        pet.setGender(fields[4].trim());
        pet.setHealthStatus(fields[5].trim());
        pet.setAdoptionStatus("Available");   // new import = always available

        // Optional shelter data (fields 6–8)
        if (fields.length >= 9) {
            Shelter shelter = new Shelter();
            shelter.setName(fields[6].trim());
            shelter.setCapacity(parseCapacity(fields[7].trim()));
            shelter.setContactNo(fields[8].trim());
            pet.setShelter(shelter);
        }

        return pet;
    }

    // -----------------------------------------------------------------------
    // Private helpers
    // -----------------------------------------------------------------------

    private Integer parseAge(String raw) {
        try {
            int age = Integer.parseInt(raw);
            return age >= 0 ? age : 0;
        } catch (NumberFormatException e) {
            return null;   // age is optional
        }
    }

    private Integer parseCapacity(String raw) {
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            return 10;   // sensible default
        }
    }
}
