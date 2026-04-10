package com.petadoption.grasp;

import com.petadoption.model.Adoption;
import com.petadoption.model.Pet;
import com.petadoption.repository.AdoptionRepository;
import com.petadoption.repository.PetRepository;
import org.springframework.stereotype.Component;

/**
 * GRASP PRINCIPLE: Protected Variations
 *
 * AdoptionStatusPolicy isolates the rules around changing an adoption's status.
 * All external code (controllers, services) calls this policy object instead
 * of embedding status-change logic inline.
 *
 * Why this satisfies Protected Variations:
 *  - Status transition rules (e.g., "Pending → Approved also sets pet to Adopted")
 *    are volatile — they change as business requirements evolve.
 *  - By wrapping them in this class, changes propagate from one place and
 *    the rest of the codebase is shielded ("protected") from variation.
 *
 * GRASP PRINCIPLE: Low Coupling (secondary)
 *  - Controllers receive a pre-validated result rather than needing to know
 *    which pet status updates are required for each adoption status.
 *
 * GRASP PRINCIPLE: Polymorphism (via interface readiness)
 *  - Future implementations (e.g., TrialAdoptionStatusPolicy) can replace this
 *    via the same interface without touching callers.
 */
@Component
public class AdoptionStatusPolicy {

    private final AdoptionRepository adoptionRepository;
    private final PetRepository      petRepository;

    public AdoptionStatusPolicy(AdoptionRepository adoptionRepository,
                                PetRepository petRepository) {
        this.adoptionRepository = adoptionRepository;
        this.petRepository      = petRepository;
    }

    /**
     * Applies the status transition to the given adoption and enforces any
     * side-effects (e.g., marking the pet as Adopted when approved).
     *
     * @param adoptionId  the adoption to update
     * @param newStatus   "Approved" | "Rejected" | "Pending"
     * @return the saved Adoption entity, or {@code null} if not found
     * @throws IllegalArgumentException for unrecognised status values
     */
    public Adoption applyStatusChange(int adoptionId, String newStatus) {
        validateStatus(newStatus);

        Adoption adoption = adoptionRepository.findById(adoptionId).orElse(null);
        if (adoption == null) {
            return null;
        }

        adoption.setStatus(newStatus);

        // Side-effect: synchronise the pet's adoption status
        Pet pet = adoption.getPet();
        if (pet != null) {
            if ("Approved".equalsIgnoreCase(newStatus)) {
                pet.setAdoptionStatus("Adopted");
                petRepository.save(pet);
            } else if ("Rejected".equalsIgnoreCase(newStatus)) {
                // Only revert to Available if no other Approved adoption exists
                boolean hasApprovedAdoption = adoptionRepository
                        .findAll()
                        .stream()
                        .filter(a -> a.getPet() != null &&
                                     a.getPet().getPetId() == pet.getPetId())
                        .anyMatch(a -> "Approved".equalsIgnoreCase(a.getStatus()));
                if (!hasApprovedAdoption) {
                    pet.setAdoptionStatus("Available");
                    petRepository.save(pet);
                }
            }
        }

        return adoptionRepository.save(adoption);
    }

    // -----------------------------------------------------------------------
    // Private
    // -----------------------------------------------------------------------

    private void validateStatus(String status) {
        if (status == null ||
            (!status.equalsIgnoreCase("Approved") &&
             !status.equalsIgnoreCase("Rejected") &&
             !status.equalsIgnoreCase("Pending"))) {
            throw new IllegalArgumentException(
                "Invalid adoption status: '" + status +
                "'. Allowed values: Approved, Rejected, Pending");
        }
    }
}
