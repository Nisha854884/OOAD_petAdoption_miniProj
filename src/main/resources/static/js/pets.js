// Pet Management Functions

// Display all pets
async function displayAllPets(containerId) {
    const container = document.getElementById(containerId);
    container.innerHTML = '<p>Loading pets...</p>';
    
    try {
        const pets = await getAllPets();
        
        if (!pets || pets.length === 0) {
            container.innerHTML = '<p>No pets found.</p>';
            return;
        }
        
        let html = '<div class="pets-grid">';
        pets.forEach(pet => {
            html += `
                <div class="pet-card">
                    <h3>${pet.name}</h3>
                    <p><strong>Species:</strong> ${pet.species}</p>
                    <p><strong>Breed:</strong> ${pet.breed}</p>
                    <p><strong>Age:</strong> ${pet.age} years</p>
                    <p><strong>Gender:</strong> ${pet.gender}</p>
                    <p><strong>Health Status:</strong> <span class="status ${pet.healthStatus.toLowerCase()}">${pet.healthStatus}</span></p>
                    <p><strong>Adoption Status:</strong> <span class="status ${pet.adoptionStatus.toLowerCase()}">${pet.adoptionStatus}</span></p>
                    <div class="actions">
                        <button onclick="viewPetDetails(${pet.petId})" class="btn-small">View Details</button>
                        <button onclick="editPetForm(${pet.petId})" class="btn-small">Edit</button>
                        <button onclick="deletePetConfirm(${pet.petId})" class="btn-small danger">Delete</button>
                    </div>
                </div>
            `;
        });
        html += '</div>';
        container.innerHTML = html;
    } catch (error) {
        container.innerHTML = '<p class="error">Error loading pets</p>';
    }
}

// Display available pets for adopters
async function displayAvailablePets(containerId) {
    const container = document.getElementById(containerId);
    container.innerHTML = '<p>Loading available pets...</p>';
    
    try {
        const pets = await getAvailablePets();
        
        if (!pets || pets.length === 0) {
            container.innerHTML = '<p>No available pets at the moment.</p>';
            return;
        }
        
        let html = '<div class="pets-grid">';
        pets.forEach(pet => {
            html += `
                <div class="pet-card featured">
                    <h3>${pet.name}</h3>
                    <p><strong>Species:</strong> ${pet.species}</p>
                    <p><strong>Breed:</strong> ${pet.breed}</p>
                    <p><strong>Age:</strong> ${pet.age} years</p>
                    <p><strong>Gender:</strong> ${pet.gender}</p>
                    <p><strong>Health Status:</strong> <span class="status ${pet.healthStatus.toLowerCase()}">${pet.healthStatus}</span></p>
                    <div class="actions">
                        <button onclick="viewPetDetails(${pet.petId})" class="btn-small">View Details</button>
                        <button onclick="adoptPet(${pet.petId})" class="btn-small success">Apply for Adoption</button>
                    </div>
                </div>
            `;
        });
        html += '</div>';
        container.innerHTML = html;
    } catch (error) {
        container.innerHTML = '<p class="error">Error loading pets</p>';
    }
}

// View pet details with medical records
async function viewPetDetails(petId) {
    try {
        const pet = await getPetById(petId);
        const medicalRecords = await getMedicalRecordsByPet(petId);
        const vaccinations = await getVaccinationsByPet(petId);
        
        let html = `
            <div class="details-modal">
                <div class="modal-content">
                    <span class="close-modal" onclick="closeModal()">&times;</span>
                    <h2>${pet.name}</h2>
                    <div class="pet-details">
                        <p><strong>Species:</strong> ${pet.species}</p>
                        <p><strong>Breed:</strong> ${pet.breed}</p>
                        <p><strong>Age:</strong> ${pet.age} years</p>
                        <p><strong>Gender:</strong> ${pet.gender}</p>
                        <p><strong>Health Status:</strong> ${pet.healthStatus}</p>
                        <p><strong>Adoption Status:</strong> ${pet.adoptionStatus}</p>
                    </div>
                    
                    <h3>Medical Records</h3>
        `;
        
        if (medicalRecords && medicalRecords.length > 0) {
            html += '<ul>';
            medicalRecords.forEach(record => {
                html += `
                    <li>
                        <strong>Date:</strong> ${record.visitDate}<br>
                        <strong>Diagnosis:</strong> ${record.diagnosis}<br>
                        <strong>Treatment:</strong> ${record.treatment}
                    </li>
                `;
            });
            html += '</ul>';
        } else {
            html += '<p>No medical records available.</p>';
        }
        
        html += `<h3>Vaccinations</h3>`;
        
        if (vaccinations && vaccinations.length > 0) {
            html += '<ul>';
            vaccinations.forEach(vac => {
                html += `
                    <li>
                        <strong>${vac.vaccineName}</strong><br>
                        <strong>Date:</strong> ${vac.vaccinationDate}<br>
                        <strong>Next Due:</strong> ${vac.nextDueDate || 'N/A'}<br>
                        <strong>Status:</strong> ${vac.status}
                    </li>
                `;
            });
            html += '</ul>';
        } else {
            html += '<p>No vaccination records available.</p>';
        }
        
        html += '</div></div>';
        
        document.body.insertAdjacentHTML('beforeend', html);
    } catch (error) {
        showError('Error loading pet details');
    }
}

// Close modal
function closeModal() {
    document.querySelectorAll('.details-modal, .form-modal').forEach(modal => modal.remove());
}

// Add pet form (Admin/Staff)
function showAddPetForm() {
    const html = `
        <div class="form-modal">
            <div class="modal-content">
                <span class="close-modal" onclick="closeModal()">&times;</span>
                <h2>Add New Pet</h2>
                <form onsubmit="handleAddPet(event)">
                    <input type="text" id="pet-name" placeholder="Pet Name" required>
                    <input type="text" id="pet-species" placeholder="Species (e.g., Dog)" required>
                    <input type="text" id="pet-breed" placeholder="Breed" required>
                    <input type="number" id="pet-age" placeholder="Age (years)" min="0" required>
                    <select id="pet-gender" required>
                        <option value="">Select Gender</option>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                    </select>
                    <select id="pet-health" required>
                        <option value="">Select Health Status</option>
                        <option value="Healthy">Healthy</option>
                        <option value="Injured">Injured</option>
                        <option value="Sick">Sick</option>
                        <option value="Recovering">Recovering</option>
                    </select>
                    <select id="pet-shelter" required>
                        <option value="">Select Shelter</option>
                    </select>
                    <button type="submit" class="btn-primary">Add Pet</button>
                    <button type="button" class="btn-secondary" onclick="closeModal()">Cancel</button>
                </form>
            </div>
        </div>
    `;
    
    document.body.insertAdjacentHTML('beforeend', html);
    populateSheltersDropdown('pet-shelter');
}

// Handle add pet
async function handleAddPet(event) {
    event.preventDefault();
    
    const petData = {
        name: document.getElementById('pet-name').value,
        species: document.getElementById('pet-species').value,
        breed: document.getElementById('pet-breed').value,
        age: parseInt(document.getElementById('pet-age').value),
        gender: document.getElementById('pet-gender').value,
        healthStatus: document.getElementById('pet-health').value,
        adoptionStatus: 'Available',
        shelterId: parseInt(document.getElementById('pet-shelter').value)
    };
    
    try {
        const result = await addPet(petData);
        if (result) {
            showSuccess('Pet added successfully!');
            closeModal();
            // Reload pets display
            displayAllPets('pets-container');
        }
    } catch (error) {
        showError('Error adding pet');
    }
}

// Delete pet confirmation
function deletePetConfirm(petId) {
    if (confirm('Are you sure you want to delete this pet?')) {
        deletePetFromDB(petId);
    }
}

// Delete pet
async function deletePetFromDB(petId) {
    try {
        const result = await deletePet(petId);
        showSuccess('Pet deleted successfully!');
        displayAllPets('pets-container');
    } catch (error) {
        showError('Error deleting pet');
    }
}

// Edit pet form
async function editPetForm(petId) {
    try {
        const pet = await getPetById(petId);
        
        const html = `
            <div class="form-modal">
                <div class="modal-content">
                    <span class="close-modal" onclick="closeModal()">&times;</span>
                    <h2>Edit Pet: ${pet.name}</h2>
                    <form onsubmit="handleUpdatePet(event, ${petId})">
                        <input type="text" id="pet-name" value="${pet.name}" required>
                        <input type="text" id="pet-species" value="${pet.species}" required>
                        <input type="text" id="pet-breed" value="${pet.breed}" required>
                        <input type="number" id="pet-age" value="${pet.age}" min="0" required>
                        <select id="pet-gender" required>
                            <option value="Male" ${pet.gender === 'Male' ? 'selected' : ''}>Male</option>
                            <option value="Female" ${pet.gender === 'Female' ? 'selected' : ''}>Female</option>
                        </select>
                        <select id="pet-health" required>
                            <option value="Healthy" ${pet.healthStatus === 'Healthy' ? 'selected' : ''}>Healthy</option>
                            <option value="Injured" ${pet.healthStatus === 'Injured' ? 'selected' : ''}>Injured</option>
                            <option value="Sick" ${pet.healthStatus === 'Sick' ? 'selected' : ''}>Sick</option>
                            <option value="Recovering" ${pet.healthStatus === 'Recovering' ? 'selected' : ''}>Recovering</option>
                        </select>
                        <select id="pet-adoption" required>
                            <option value="Available" ${pet.adoptionStatus === 'Available' ? 'selected' : ''}>Available</option>
                            <option value="Adopted" ${pet.adoptionStatus === 'Adopted' ? 'selected' : ''}>Adopted</option>
                            <option value="Pending" ${pet.adoptionStatus === 'Pending' ? 'selected' : ''}>Pending</option>
                        </select>
                        <button type="submit" class="btn-primary">Update Pet</button>
                        <button type="button" class="btn-secondary" onclick="closeModal()">Cancel</button>
                    </form>
                </div>
            </div>
        `;
        
        document.body.insertAdjacentHTML('beforeend', html);
    } catch (error) {
        showError('Error loading pet details');
    }
}

// Handle update pet
async function handleUpdatePet(event, petId) {
    event.preventDefault();
    
    const petData = {
        name: document.getElementById('pet-name').value,
        species: document.getElementById('pet-species').value,
        breed: document.getElementById('pet-breed').value,
        age: parseInt(document.getElementById('pet-age').value),
        gender: document.getElementById('pet-gender').value,
        healthStatus: document.getElementById('pet-health').value,
        adoptionStatus: document.getElementById('pet-adoption').value
    };
    
    try {
        const result = await updatePet(petId, petData);
        if (result) {
            showSuccess('Pet updated successfully!');
            closeModal();
            displayAllPets('pets-container');
        }
    } catch (error) {
        showError('Error updating pet');
    }
}

// Populate shelters dropdown
async function populateSheltersDropdown(elementId) {
    try {
        const shelters = await getAllShelters();
        const select = document.getElementById(elementId);
        
        shelters.forEach(shelter => {
            const option = document.createElement('option');
            option.value = shelter.shelterId;
            option.textContent = shelter.name;
            select.appendChild(option);
        });
    } catch (error) {
        console.error('Error loading shelters');
    }
}
