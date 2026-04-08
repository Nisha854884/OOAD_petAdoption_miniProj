// API Configuration and Reusable Functions
const API_BASE_URL = 'http://localhost:8081/api';

// Get current user from localStorage
function getCurrentUser() {
    return JSON.parse(localStorage.getItem('currentUser') || '{}');
}

// Get current user role
function getUserRole() {
    const user = getCurrentUser();
    return user.role || null;
}

// Make API call with error handling
async function apiCall(endpoint, method = 'GET', data = null) {
    const user = getCurrentUser();
    const url = new URL(`${API_BASE_URL}${endpoint}`, window.location.origin);
    
    // Add role parameter for authorization
    if (user.role) {
        url.searchParams.append('role', user.role);
    }
    
    const options = {
        method: method,
        headers: {
            'Content-Type': 'application/json',
        }
    };
    
    if (data) {
        options.body = JSON.stringify(data);
    }
    
    try {
        const response = await fetch(url.toString(), options);
        
        if (!response.ok) {
            const errorText = await response.text();
            if (response.status === 403) {
                showError('Access Denied: You do not have permission to perform this action');
                return null;
            }
            throw new Error(errorText || `HTTP ${response.status}`);
        }
        
        const contentType = response.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
            return await response.json();
        } else {
            return await response.text();
        }
    } catch (error) {
        console.error('API Error:', error);
        showError(`API Error: ${error.message}`);
        return null;
    }
}

// Authentication APIs
async function login(username, password) {
    const response = await fetch(`${API_BASE_URL}/auth/login?username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`, {
        method: 'POST'
    });
    return await response.text();
}

async function signup(username, password) {
    return apiCall('/auth/signup', 'POST', { username, password });
}

// Pet APIs
async function getAllPets() {
    return apiCall('/pets/all');
}

async function getAvailablePets() {
    return apiCall('/pets/available');
}

async function getPetById(id) {
    return apiCall(`/pets/${id}`);
}

async function addPet(petData) {
    return apiCall('/pets/add', 'POST', petData);
}

async function updatePet(id, petData) {
    return apiCall(`/pets/update/${id}`, 'PUT', petData);
}

async function deletePet(id) {
    return apiCall(`/pets/delete/${id}`, 'DELETE');
}

// Shelter APIs
async function getAllShelters() {
    return apiCall('/shelters/all');
}

async function addShelter(shelterData) {
    return apiCall('/shelters/add', 'POST', shelterData);
}

async function updateShelter(id, shelterData) {
    return apiCall(`/shelters/update/${id}`, 'PUT', shelterData);
}

async function deleteShelter(id) {
    return apiCall(`/shelters/delete/${id}`, 'DELETE');
}

async function getShelterCapacityUtilization() {
    return apiCall('/shelters/capacity/utilization');
}

// Vet APIs
async function getAllVets() {
    return apiCall('/vets/all');
}

async function addVet(vetData) {
    return apiCall('/vets/add', 'POST', vetData);
}

async function updateVet(id, vetData) {
    return apiCall(`/vets/update/${id}`, 'PUT', vetData);
}

async function deleteVet(id) {
    return apiCall(`/vets/delete/${id}`, 'DELETE');
}

// Medical Record APIs
async function getMedicalRecordsByPet(petId) {
    return apiCall(`/medical-records/pet/${petId}`);
}

async function addMedicalRecord(recordData) {
    return apiCall('/medical-records/add', 'POST', recordData);
}

async function updateMedicalRecord(id, recordData) {
    return apiCall(`/medical-records/update/${id}`, 'PUT', recordData);
}

async function deleteMedicalRecord(id) {
    return apiCall(`/medical-records/delete/${id}`, 'DELETE');
}

// Vaccination APIs
async function getVaccinationsByPet(petId) {
    return apiCall(`/vaccinations/pet/${petId}`);
}

async function addVaccination(vaccinationData) {
    return apiCall('/vaccinations/add', 'POST', vaccinationData);
}

async function updateVaccination(id, vaccinationData) {
    return apiCall(`/vaccinations/update/${id}`, 'PUT', vaccinationData);
}

async function deleteVaccination(id) {
    return apiCall(`/vaccinations/delete/${id}`, 'DELETE');
}

// Adoption APIs
async function requestAdoption(petId, adopterId) {
    return apiCall('/adoptions/request', 'POST', { petId, adopterId });
}

async function getMyAdoptions() {
    return apiCall('/adoptions/view');
}

async function updateAdoptionStatus(adoptionId, status) {
    return apiCall(`/adoptions/approve/${adoptionId}`, 'PUT', { status });
}

// Staff APIs
async function getAllStaff() {
    return apiCall('/staff/all');
}

async function addStaff(staffData) {
    return apiCall('/staff/add', 'POST', staffData);
}

async function updateStaff(id, staffData) {
    return apiCall(`/staff/update/${id}`, 'PUT', staffData);
}

async function deleteStaff(id) {
    return apiCall(`/staff/delete/${id}`, 'DELETE');
}

// Admin APIs
async function getAllAdmins() {
    return apiCall('/admins/all');
}

async function addAdmin(adminData) {
    return apiCall('/admins/add', 'POST', adminData);
}

// Report APIs
async function getPetStatusReport() {
    return apiCall('/reports/pet-status');
}

async function getAdoptionReport() {
    return apiCall('/reports/adoptions');
}

async function getAvegeAgeReport() {
    return apiCall('/reports/avg-age');
}

async function getAllMedicalRecords() {
    return apiCall('/medical-records/all');
}

async function getAllVaccinations() {
    return apiCall('/vaccinations/all');
}

// Utility functions
function showError(message) {
    const errorDiv = document.createElement('div');
    errorDiv.className = 'alert alert-error';
    errorDiv.textContent = message;
    document.body.insertBefore(errorDiv, document.body.firstChild);
    setTimeout(() => errorDiv.remove(), 5000);
}

function showSuccess(message) {
    const successDiv = document.createElement('div');
    successDiv.className = 'alert alert-success';
    successDiv.textContent = message;
    document.body.insertBefore(successDiv, document.body.firstChild);
    setTimeout(() => successDiv.remove(), 3000);
}

function isLoggedIn() {
    return !!localStorage.getItem('currentUser');
}

function logout() {
    localStorage.removeItem('currentUser');
    window.location.href = 'login.html';
}
