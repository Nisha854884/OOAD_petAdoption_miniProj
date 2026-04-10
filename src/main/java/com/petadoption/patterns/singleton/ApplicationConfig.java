package com.petadoption.patterns.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * DESIGN PATTERN: Singleton
 *
 * ApplicationConfig acts as a Singleton that holds system-wide configuration
 * constants for the Pet Adoption System. Spring's @Component ensures a single
 * bean instance is shared across the entire application context, making this a
 * Spring-managed Singleton.
 *
 * Why Singleton here?
 *  - Configuration values must be consistent everywhere — no two callers
 *    should see different defaults.
 *  - Avoids creating redundant configuration objects for every request.
 *
 * GRASP principle satisfied: Information Expert — the class that owns the
 * configuration data is the one that exposes it.
 */
@Component
public class ApplicationConfig {

    private static final Logger log = LoggerFactory.getLogger(ApplicationConfig.class);

    // -----------------------------------------------------------------------
    // Adoption defaults
    // -----------------------------------------------------------------------
    public static final String DEFAULT_ADOPTION_STATUS  = "Pending";
    public static final String APPROVED_ADOPTION_STATUS = "Approved";
    public static final String REJECTED_ADOPTION_STATUS = "Rejected";

    // -----------------------------------------------------------------------
    // Pet defaults
    // -----------------------------------------------------------------------
    public static final String DEFAULT_PET_STATUS = "Available";
    public static final String ADOPTED_PET_STATUS = "Adopted";

    // -----------------------------------------------------------------------
    // User / role defaults
    // -----------------------------------------------------------------------
    public static final String DEFAULT_ROLE             = "Adopter";
    public static final String DEFAULT_EXPERIENCE_LEVEL = "Beginner";
    public static final String DEFAULT_DEPARTMENT       = "General Administration";
    public static final String DEFAULT_PERMISSIONS      = "manage-system";

    // -----------------------------------------------------------------------
    // Validation constants
    // -----------------------------------------------------------------------
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MIN_USERNAME_LENGTH = 3;
    public static final int MAX_USERNAME_LENGTH = 50;

    /**
     * Private constructor to reinforce Singleton intent.
     * Spring instantiates this exactly once; logging here confirms it.
     */
    public ApplicationConfig() {
        log.info("ApplicationConfig Singleton instantiated — " +
                 "system configuration is now available application-wide.");
    }

    // -----------------------------------------------------------------------
    // Convenience accessor (useful in non-Spring contexts / tests)
    // -----------------------------------------------------------------------
    public String getDefaultAdoptionStatus()  { return DEFAULT_ADOPTION_STATUS; }
    public String getDefaultPetStatus()        { return DEFAULT_PET_STATUS; }
    public String getDefaultRole()             { return DEFAULT_ROLE; }
    public int    getMinPasswordLength()        { return MIN_PASSWORD_LENGTH; }
}
