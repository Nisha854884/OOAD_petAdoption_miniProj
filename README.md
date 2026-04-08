# Pet Adoption System - Full Implementation Guide

## 1. Project Summary
This project is a Spring Boot + MySQL + Vanilla JavaScript pet adoption system with role-based portals:
- Admin portal
- Staff portal
- Adopter portal

Backend runs on:
- `http://localhost:8081/api`

Frontend static files are served directly by Spring Boot from the `frontend/` folder.

---

## 2. What Has Been Applied So Far

### Backend and API fixes
- Standardized runtime config to use port `8081` and context path `/api`.
- Added static frontend mapping in Spring so pages are available from backend.
- Improved auth responses so login returns structured JSON (`message`, `username`, `role`, `userId`, `adopterId`).
- Added missing adoption and pet API support used by frontend (`/adoption/all`, `/adoption/{id}`, `/pets/{id}`).
- Fixed frontend/backend route mismatches (notably `/adoption/*` vs `/adoptions/*`).
- Added better duplicate signup handling (`409` for existing username).
- Added cache-control and cache-busting support to reduce stale browser script issues.

### Data and serialization fixes
- Fixed circular entity serialization loops causing malformed JSON by adding Jackson guards in model relationships.
- Added realistic idempotent seed script:
  - `db_creation/seed_pet_adoption_data.sql`
- Populated role users, shelters, pets, adoptions, vets, medical and vaccination records.
- Repaired missing adopter profile rows for adopter-role users when needed.

### Frontend behavior fixes
- Fixed adopter dashboard script inclusion and event flows.
- Fixed modal close/cancel behavior to remove both modal types.
- Fixed adopter adoption-details lookup to use role-appropriate data source.
- Fixed button flows for apply/confirm/view in adopter dashboard.
- Improved section switching robustness (no reliance on implicit browser `event` object).

### Operations fixes
- Added safe backend run script:
  - `run_backend.sh`
- Script kills any process on `8081`, then starts backend cleanly.

---

## 3. Design Patterns Implemented

## 3.1 Facade Pattern
Purpose: provide one orchestration entrypoint for auth workflows.

File:
- `src/main/java/com/petadoption/patterns/auth/facade/AuthFacade.java`

Functions:
- `login(String username, String password)`
  - Delegates login flow to `AuthService`.
- `signup(SignupRequestDTO request)`
  - Runs validation chain.
  - Builds `User` entity.
  - Resolves role profile factory.
  - Attaches role-specific profile.
  - Persists user via `AuthService`.
- `parseRole(String role)`
  - Converts input role text to `User.Role` enum.

## 3.2 Chain of Responsibility Pattern
Purpose: split signup validation into independent, composable steps.

Files:
- `src/main/java/com/petadoption/patterns/auth/chain/SignupValidationHandler.java`
- `src/main/java/com/petadoption/patterns/auth/chain/UsernameValidationHandler.java`
- `src/main/java/com/petadoption/patterns/auth/chain/PasswordValidationHandler.java`
- `src/main/java/com/petadoption/patterns/auth/chain/RoleValidationHandler.java`
- `src/main/java/com/petadoption/patterns/auth/chain/RoleSpecificValidationHandler.java`
- `src/main/java/com/petadoption/patterns/auth/chain/SignupValidationChain.java`

How it works:
1. `UsernameValidationHandler`
   - username required
   - length 3-50
   - uniqueness check via repository
2. `PasswordValidationHandler`
   - password required
   - minimum length 6
3. `RoleValidationHandler`
   - allows only ADMIN/STAFF/ADOPTER (default allowed when missing)
4. `RoleSpecificValidationHandler`
   - adopter field validation (phone/name/address)
   - staff input completeness validation when staff profile fields are partially provided

`SignupValidationChain.validate(...)` starts the chain and executes handlers in order.

## 3.3 Factory (Creational) Pattern
Purpose: create role-specific profile entities without hardcoding role logic in controller.

Files:
- `src/main/java/com/petadoption/patterns/auth/factory/RoleProfileFactory.java`
- `src/main/java/com/petadoption/patterns/auth/factory/AdopterProfileFactory.java`
- `src/main/java/com/petadoption/patterns/auth/factory/AdminProfileFactory.java`
- `src/main/java/com/petadoption/patterns/auth/factory/StaffProfileFactory.java`
- `src/main/java/com/petadoption/patterns/auth/factory/RoleProfileFactoryProvider.java`

How it works:
- Provider selects the matching factory by role.
- Adopter factory creates and attaches `Adopter` profile with defaults.
- Admin factory creates and attaches `Admin` profile with defaults.
- Staff factory optionally creates `Staff` profile only when complete staff fields and `shelterId` are supplied.

---

## 4. Pattern Integration Into Existing Auth Flow

Updated files:
- `src/main/java/com/petadoption/controller/AuthController.java`
- `src/main/java/com/petadoption/dto/SignupRequestDTO.java`
- `src/main/java/com/petadoption/repository/UserRepository.java`

What changed:
- `AuthController` now delegates login/signup orchestration to `AuthFacade`.
- Signup request moved to dedicated DTO (`SignupRequestDTO`).
- Repository gained `existsByUsername(...)` for chain validation.

---

## 5. Function-by-Function Reference
This section documents business functions (controllers/services/frontend modules and orchestration classes).

Note: standard POJO/JPA getter/setter methods are not listed because they are simple data accessors.

## 5.1 Auth and Pattern Functions

### `AuthController`
- `login(username, password)`: authenticates credentials; returns structured session payload.
- `signup(request)`: passes signup request through facade orchestration and returns success/failure.

### `AuthService`
- `login(username, password)`: checks repository user and plaintext password equality.
- `signup(user)`: applies default role if missing and saves user entity graph.

### `AuthFacade`
- `login(...)`: façade entry for login.
- `signup(...)`: façade entry for signup orchestration.
- `parseRole(...)`: role text to enum converter.

### Chain classes
- `SignupValidationHandler.setNext(...)`: links next handler.
- `SignupValidationHandler.handle(...)`: executes current validation then next.
- `UsernameValidationHandler.validate(...)`: username constraints and uniqueness.
- `PasswordValidationHandler.validate(...)`: password constraints.
- `RoleValidationHandler.validate(...)`: role normalization and whitelist check.
- `RoleSpecificValidationHandler.validate(...)`: adopter/staff role field checks.
- `RoleSpecificValidationHandler.validateAdopterFields(...)`: adopter specific validations.
- `RoleSpecificValidationHandler.validateStaffFields(...)`: staff specific validations.
- `RoleSpecificValidationHandler.valueOrEmpty(...)`: null-safe trim helper.
- `SignupValidationChain.validate(...)`: starts chain execution.

### Factory classes
- `RoleProfileFactory.supports(role)`: role matching contract.
- `RoleProfileFactory.attachProfile(user, request)`: creation/attachment contract.
- `AdopterProfileFactory.supports(...)`: matches `Adopter` role.
- `AdopterProfileFactory.attachProfile(...)`: creates adopter profile with defaults.
- `AdopterProfileFactory.hasText(...)`: null/blank helper.
- `AdminProfileFactory.supports(...)`: matches `Admin` role.
- `AdminProfileFactory.attachProfile(...)`: creates admin profile with defaults.
- `AdminProfileFactory.hasText(...)`: null/blank helper.
- `StaffProfileFactory.supports(...)`: matches `Staff` role.
- `StaffProfileFactory.attachProfile(...)`: creates staff profile only when full staff data exists.
- `StaffProfileFactory.hasText(...)`: null/blank helper.
- `RoleProfileFactoryProvider.getFactory(role)`: resolves correct concrete factory.

## 5.2 Backend Controller Functions

### `AdoptionController`
- `getAllAdoptions(role)`: returns all adoptions for Admin/Staff.
- `getAdoptionById(id, role)`: returns single adoption for Admin/Staff.
- `requestAdoption(petId, adopterId, role)`: creates a new adopter request.
- `getMyAdoptions(adopterId)`: returns adopter-specific applications.
- `updateStatus(id, status, role)`: updates adoption decision/status.

### `AdminController`
- `getAllAdmins(role)`, `getAdminById(id, role)`, `getAdminByUserId(userId, role)`, `getAdminsByDepartment(dept, role)`, `addAdmin(admin, role)`, `updateAdmin(id, admin, role)`, `deleteAdmin(id, role)`.

### `StaffController`
- `getAllStaff(role)`, `getStaffById(id, role)`, `getStaffByUserId(userId, role)`, `getStaffByShelterId(shelterId, role)`, `getStaffByPosition(position, role)`, `addStaff(staff, role)`, `updateStaff(id, staff, role)`, `deleteStaff(id, role)`.

### `PetController`
- `getAllPets(role)`: admin/staff list.
- `getAvailablePets()`: adopter-facing available list.
- `getPetById(id)`: fetch one pet.
- `addPet(pet, role)`, `updatePet(id, pet, role)`, `deletePet(id, role)`.
- `getAvailablePetDTOs()`: lightweight DTO list for adopter browsing.

### `ShelterController`
- `getAllShelters(role)`, `getShelterById(id, role)`, `addShelter(shelter, role)`, `updateShelter(id, shelter, role)`, `deleteShelter(id, role)`, `getShelterCapacityUtilization(role)`, `getSheltersWithAvailableCapacity(role)`.

### `VetController`
- `getAllVets(role)`, `getVetById(id, role)`, `getVetsBySpecialization(spec, role)`, `getVetsByShelterId(shelterId, role)`, `addVet(vet, role)`, `updateVet(id, vet, role)`, `deleteVet(id, role)`.

### `Medical_RecordController`
- `getAllMedicalRecords(role)`, `getMedicalRecordById(id, role)`, `getRecordsByPetId(petId, role)`, `getRecordsByVetId(vetId, role)`, `addMedicalRecord(record, role)`, `updateMedicalRecord(id, record, role)`, `deleteMedicalRecord(id, role)`, `getAvailablePetsWithVaccinationDetails(role)`.

### `VaccinationController`
- `getAllVaccinations(role)`, `getVaccinationById(id)`, `getVaccinationsByPetId(petId)`, `getVaccinationsByName(vaccineName, role)`, `getVaccinationsDueForRenewal(role)`, `addVaccination(vaccination, role)`, `updateVaccination(id, vaccination, role)`, `deleteVaccination(id, role)`, `getVaccinationStatusSummary(role)`.

### `ReportController`
- `countPets()`: grouped pet status metrics.
- `adopterPets()`: adopter-pet report rows.
- `pending()`: pending adoptions.
- `avgAge()`: average age by breed report.

### `GlobalExceptionHandler`
- `handleException(e)`: returns HTTP 500 with error body.

## 5.3 Backend Service Functions

### `AdoptionService`
- `requestAdoption(petId, adopterId)`: creates pending request with current date.
- `getAdoptionsByAdopter(adopterId)`: adopter application list.
- `getAllAdoptions()`: full list.
- `getAdoptionById(adoptionId)`: single item lookup.
- `updateStatus(adoptionId, status)`: updates status.

### `AdminService`
- `getAllAdmins()`, `getAdminById(id)`, `getAdminByUserId(userId)`, `getAdminsByDepartment(department)`, `addAdmin(admin)`, `updateAdmin(id, admin)`, `deleteAdmin(id)`.

### `AuthService`
- `login(username, password)`: validates credentials.
- `signup(user)`: persists new user graph.

### `Medical_RecordService`
- `getAllMedicalRecords()`, `getMedicalRecordById(id)`, `getRecordsByPetId(petId)`, `getRecordsByVetId(vetId)`, `getRecordsByDateRange(startDate, endDate)`, `getRecordsByDiagnosis(diagnosis)`, `addMedicalRecord(record)`, `updateMedicalRecord(id, updatedRecord)`, `deleteMedicalRecord(id)`, `getAvailablePetsWithVaccinationDetails()`.

### `PetService`
- `getAllPets()`, `getAvailablePets()`, `getPetById(id)`, `addPet(pet)`, `updatePet(id, pet)`, `deletePet(id)`, `getAvailablePetDTOs()`.

### `ReportService`
- `countPetsByStatus()`, `adopterPetDetails()`, `pendingAdoptions()`, `avgAgeByBreed()`.

### `ShelterService`
- `getAllShelters()`, `getShelterById(id)`, `findByName(name)`, `addShelter(shelter)`, `updateShelter(id, shelter)`, `deleteShelter(id)`, `getShelterCapacityUtilization()`, `findSheltersWithAvailableCapacity()`.

### `StaffService`
- `getAllStaff()`, `getStaffById(id)`, `getStaffByUserId(userId)`, `getStaffByShelterId(shelterId)`, `getStaffByPosition(position)`, `addStaff(staff)`, `updateStaff(id, staff)`, `deleteStaff(id)`.

### `VaccinationService`
- `getAllVaccinations()`, `getVaccinationById(id)`, `getVaccinationsByPetId(petId)`, `getVaccinationsByName(vaccineName)`, `getVaccinationsDueForRenewal()`, `getVaccinationsByPetAndStatus(petId, status)`, `addVaccination(vaccination)`, `updateVaccination(id, vaccination)`, `deleteVaccination(id)`, `getVaccinationStatusSummary()`.

### `VetService`
- `getAllVets()`, `getVetById(id)`, `getVetsBySpecialization(specialization)`, `getVetsByShelterId(shelterId)`, `addVet(vet)`, `updateVet(id, vet)`, `deleteVet(id)`.

## 5.4 Frontend Function Reference

### `frontend/js/auth.js`
- `validateField(fieldName, value)`: validates auth/profile fields against configured rules.
- `handleLogin(event)`: handles login form, saves session, redirects by role.
- `handleSignup(event)`: handles signup form and basic role/profile defaults.
- `toggleAuthForm(tab)`: toggles login/signup UI cards.
- `checkAuthStatus()`: protects dashboard routes.
- `preventLoggedInAccess()`: redirects already-logged-in users away from login page.

### `frontend/js/api.js`
- `getCurrentUser()`: reads `currentUser` from localStorage.
- `getUserRole()`: returns role from current session.
- `normalizeRoleForApi(role)`: normalizes role casing for query params.
- `apiCall(endpoint, method, data)`: centralized fetch + error handling.
- `login(username, password)`: auth login request.
- `signup(username, password, role, profile)`: auth signup request.
- `getAllPets()`, `getAvailablePets()`, `getPetById(id)`, `addPet(petData)`, `updatePet(id, petData)`, `deletePet(id)`.
- `getAllShelters()`, `addShelter(data)`, `updateShelter(id, data)`, `deleteShelter(id)`, `getShelterCapacityUtilization()`.
- `getAllVets()`, `addVet(data)`, `updateVet(id, data)`, `deleteVet(id)`.
- `getMedicalRecordsByPet(petId)`, `addMedicalRecord(data)`, `updateMedicalRecord(id, data)`, `deleteMedicalRecord(id)`.
- `getVaccinationsByPet(petId)`, `addVaccination(data)`, `updateVaccination(id, data)`, `deleteVaccination(id)`.
- `requestAdoption(petId, adopterId)`, `getMyAdoptions()`, `updateAdoptionStatus(adoptionId, status)`, `getAllAdoptions()`, `getAdoptionById(adoptionId)`.
- `getAllStaff()`, `addStaff(data)`, `updateStaff(id, data)`, `deleteStaff(id)`.
- `getAllAdmins()`, `addAdmin(data)`.
- `getPetStatusReport()`, `getAdoptionReport()`, `getAvegeAgeReport()`, `getAllMedicalRecords()`, `getAllVaccinations()`.
- `showError(message)`, `showSuccess(message)`, `isLoggedIn()`, `logout()`.

### `frontend/js/pets.js`
- `displayAllPets(containerId)`: renders full pet cards list.
- `displayAvailablePets(containerId)`: renders adopter-facing available pets list.
- `viewPetDetails(petId)`: opens detailed modal including medical and vaccination info.
- `closeModal()`: closes open modal overlays.
- `showAddPetForm()`: opens add-pet form modal.
- `handleAddPet(event)`: submits add pet flow.
- `deletePetConfirm(petId)`: confirmation wrapper for delete.
- `deletePetFromDB(petId)`: executes delete and refresh.
- `editPetForm(petId)`: opens edit modal with pet data prefilled.
- `handleUpdatePet(event, petId)`: submits edit flow.
- `populateSheltersDropdown(elementId)`: fills shelter select options.

### `frontend/js/adoptions.js`
- `displayAdoptionRequests(containerId)`: admin/staff request table renderer.
- `displayMyAdoptions(containerId)`: adopter application cards renderer.
- `viewAdoptionDetails(adoptionId)`: detailed adoption modal.
- `adoptPet(petId)`: opens apply confirmation modal.
- `confirmAdoption(petId)`: submits adoption request and refreshes sections.
- `approveAdoption(adoptionId)`: approve pending request.
- `rejectAdoption(adoptionId)`: reject pending request.
- `getAllAvailablePetsForAdoption()`: returns available pets set.
- `getAdoptionByIdForCurrentUser(adoptionId)`: finds adoption from role-scoped data source.
- `displayAdoptionStatistics(containerId)`: calculates and renders aggregate stats.
- `exportAdoptionReport()`: exports CSV adoption report.

---

## 6. Run Instructions

## Backend
```bash
cd /root/OOAD_petAdoption_miniProj
chmod +x run_backend.sh
./run_backend.sh
```

## URL
- `http://localhost:8081/api/`

## Optional compile check
```bash
./mvnw -DskipTests compile
```

---

## 7. Demo Accounts (Seeded)
Password for seeded users: `Pass@123`
- `seed_admin_ops`
- `seed_admin_reports`
- `seed_staff_intake`
- `seed_staff_medical`
- `seed_staff_outreach`
- `seed_adopter_anna`
- `seed_adopter_nisha`
- `seed_adopter_rahul`
- `seed_adopter_isha`
- `seed_adopter_kevin`

---

## 8. Notes
- Staff profile creation in signup is optional; it only creates a `Staff` row if full staff fields are provided (`position`, `phone`, `email`, `shelterId`).
- If staff onboarding is done only by admins, role-only staff user creation remains supported.
- This README is intentionally comprehensive to serve as both architecture notes and maintenance documentation.
