package com.petadoption.grasp;

import com.petadoption.model.Adoption;
import com.petadoption.model.Pet;
import com.petadoption.model.Vaccination;
import com.petadoption.patterns.singleton.ApplicationConfig;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * GRASP PRINCIPLE: Information Expert
 *
 * PetDomainExpert centralises all domain knowledge and business rules that
 * concern a single Pet.  By placing this logic in the class that has the
 * most information about pets, we avoid scattering condition checks across
 * controllers, services, and JSP pages.
 *
 * Responsibilities:
 *  - Deciding whether a pet is eligible for adoption
 *  - Deciding whether a pet's vaccination record is complete
 *  - Producing a human-readable summary of a pet
 *
 * Why this satisfies Information Expert:
 *  - Pet is the information holder; the Expert for Pet-related queries
 *    should live as close to that data as possible.
 *  - Controllers stay thin; they call this expert rather than duplicating
 *    eligibility logic.
 *
 * GRASP PRINCIPLE: High Cohesion (secondary)
 *  - Every method here is related to pet domain decisions — nothing unrelated
 *    (e.g., sending emails, querying the DB) is mixed in.
 */
@Component
public class PetDomainExpert {

    /**
     * Returns {@code true} when the pet may be adopted:
     * the pet's status must be "Available" and it must have no active
     * pending adoptions.
     */
    public boolean isEligibleForAdoption(Pet pet, List<Adoption> existingAdoptions) {
        if (!ApplicationConfig.DEFAULT_PET_STATUS.equals(pet.getAdoptionStatus())) {
            return false;
        }
        return existingAdoptions.stream()
                .noneMatch(a -> "Pending".equals(a.getStatus()) ||
                                "Approved".equals(a.getStatus()));
    }

    /**
     * Returns {@code true} if the pet has at least one completed vaccination.
     * A fully vaccinated pet is more attractive to adopters and reflects well
     * on the shelter.
     */
    public boolean hasCompletedVaccinations(List<Vaccination> vaccinations) {
        if (vaccinations == null || vaccinations.isEmpty()) {
            return false;
        }
        return vaccinations.stream()
                .anyMatch(v -> "Completed".equalsIgnoreCase(v.getStatus()));
    }

    /**
     * Produces a concise, human-readable summary of the pet for display
     * in listings or notification emails.
     *
     * Example output: "Buddy (Dog / Labrador, Age: 3, Male) — Healthy [Available]"
     */
    public String buildSummary(Pet pet) {
        return String.format("%s (%s / %s, Age: %s, %s) — %s [%s]",
                pet.getName(),
                pet.getSpecies(),
                pet.getBreed(),
                pet.getAge() != null ? pet.getAge() : "?",
                pet.getGender(),
                pet.getHealthStatus(),
                pet.getAdoptionStatus());
    }
}
