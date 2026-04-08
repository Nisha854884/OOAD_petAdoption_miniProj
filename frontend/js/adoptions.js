// Adoption Management Functions

// Display adoption requests (Admin/Staff)
async function displayAdoptionRequests(containerId) {
    const container = document.getElementById(containerId);
    container.innerHTML = '<p>Loading adoption requests...</p>';
    
    try {
        const adoptions = await getAllAdoptions();
        
        if (!adoptions || adoptions.length === 0) {
            container.innerHTML = '<p>No adoption requests.</p>';
            return;
        }
        
        let html = '<table class="adoptions-table"><thead><tr>' +
            '<th>Pet</th><th>Adopter</th><th>Status</th><th>Request Date</th><th>Actions</th>' +
            '</tr></thead><tbody>';
        
        adoptions.forEach(adoption => {
            const status = adoption.status || adoption.adoptionStatus || 'Pending';
            const requestDate = adoption.adoptionDate || adoption.requestDate || 'N/A';
            html += `
                <tr>
                    <td>${adoption.pet?.name || 'N/A'}</td>
                    <td>${adoption.adopter?.name || 'N/A'}</td>
                    <td><span class="status ${String(status).toLowerCase()}">${status}</span></td>
                    <td>${requestDate}</td>
                    <td>
                        <button onclick="viewAdoptionDetails(${adoption.adoptionId})" class="btn-small">View</button>
                        ${status === 'Pending' ? `
                            <button onclick="approveAdoption(${adoption.adoptionId})" class="btn-small success">Approve</button>
                            <button onclick="rejectAdoption(${adoption.adoptionId})" class="btn-small danger">Reject</button>
                        ` : ''}
                    </td>
                </tr>
            `;
        });
        
        html += '</tbody></table>';
        container.innerHTML = html;
    } catch (error) {
        container.innerHTML = '<p class="error">Error loading adoption requests</p>';
    }
}

// Display adopter's adoptions
async function displayMyAdoptions(containerId) {
    const container = document.getElementById(containerId);
    container.innerHTML = '<p>Loading your adoptions...</p>';
    
    try {
        const adoptions = await getMyAdoptions();
        
        if (!adoptions || adoptions.length === 0) {
            container.innerHTML = '<p>You have not applied for any adoptions yet.</p>';
            return;
        }
        
        let html = '<div class="adoptions-list">';
        adoptions.forEach(adoption => {
            const status = adoption.status || adoption.adoptionStatus || 'Pending';
            const requestDate = adoption.adoptionDate || adoption.requestDate || 'N/A';
            html += `
                <div class="adoption-card">
                    <h3>${adoption.pet?.name || 'N/A'}</h3>
                    <p><strong>Species:</strong> ${adoption.pet?.species || 'N/A'}</p>
                    <p><strong>Status:</strong> <span class="status ${String(status).toLowerCase()}">${status}</span></p>
                    <p><strong>Request Date:</strong> ${requestDate}</p>
                    <button onclick="viewAdoptionDetails(${adoption.adoptionId})" class="btn-small">View Details</button>
                </div>
            `;
        });
        html += '</div>';
        container.innerHTML = html;
    } catch (error) {
        container.innerHTML = '<p class="error">Error loading adoptions</p>';
    }
}

// View adoption details
async function viewAdoptionDetails(adoptionId) {
    try {
        const adoption = await getAdoptionById(adoptionId);
        
        let html = `
            <div class="details-modal">
                <div class="modal-content">
                    <span class="close-modal" onclick="closeModal()">&times;</span>
                    <h2>Adoption Details</h2>
                    <div class="adoption-details">
                        <p><strong>Pet:</strong> ${adoption.pet?.name || 'N/A'}</p>
                        <p><strong>Adopter:</strong> ${adoption.adopter?.name || 'N/A'}</p>
                        <p><strong>Adoption Status:</strong> ${adoption.status || adoption.adoptionStatus || 'Pending'}</p>
                        <p><strong>Request Date:</strong> ${adoption.adoptionDate || adoption.requestDate || 'N/A'}</p>
                        ${adoption.notes ? `<p><strong>Notes:</strong> ${adoption.notes}</p>` : ''}
                    </div>
                </div>
            </div>
        `;
        
        document.body.insertAdjacentHTML('beforeend', html);
    } catch (error) {
        showError('Error loading adoption details');
    }
}

// Request adoption (Adopter)
async function adoptPet(petId) {
    try {
        const pet = await getPetById(petId);
        
        const html = `
            <div class="form-modal">
                <div class="modal-content">
                    <span class="close-modal" onclick="closeModal()">&times;</span>
                    <h2>Apply for ${pet.name}</h2>
                    <p>You are about to apply for adoption of <strong>${pet.name}</strong> (${pet.breed}).</p>
                    <p>Please confirm your adoption request.</p>
                    <div class="actions-modal">
                        <button onclick="confirmAdoption(${petId})" class="btn-primary">Confirm Request</button>
                        <button onclick="closeModal()" class="btn-secondary">Cancel</button>
                    </div>
                </div>
            </div>
        `;
        
        document.body.insertAdjacentHTML('beforeend', html);
    } catch (error) {
        showError('Error loading pet information');
    }
}

// Confirm adoption request
async function confirmAdoption(petId) {
    try {
        const user = getCurrentUser();
        const result = await requestAdoption(petId, user.adopterId);
        
        if (result) {
            showSuccess('Adoption request submitted successfully!');
            closeModal();
            // Reload available pets
            displayAvailablePets('available-pets-container');
        }
    } catch (error) {
        showError('Error submitting adoption request');
    }
}

// Approve adoption (Admin/Staff)
async function approveAdoption(adoptionId) {
    if (!confirm('Are you sure you want to approve this adoption?')) return;
    
    try {
        const result = await updateAdoptionStatus(adoptionId, 'Approved');
        
        if (result) {
            showSuccess('Adoption approved successfully!');
            displayAdoptionRequests('adoption-requests-container');
        }
    } catch (error) {
        showError('Error approving adoption');
    }
}

// Reject adoption (Admin/Staff)
async function rejectAdoption(adoptionId) {
    const reason = prompt('Enter reason for rejection:');
    
    if (!reason) return;
    
    try {
        const result = await updateAdoptionStatus(adoptionId, 'Rejected');
        
        if (result) {
            showSuccess('Adoption request rejected');
            displayAdoptionRequests('adoption-requests-container');
        }
    } catch (error) {
        showError('Error rejecting adoption');
    }
}

// Get available pets for adoption
async function getAllAvailablePetsForAdoption() {
    try {
        return await getAvailablePets();
    } catch (error) {
        showError('Error loading available pets');
        return [];
    }
}

// Get adoption by ID
async function getAdoptionById(adoptionId) {
    try {
        const adoptions = await getAllAdoptions();
        return adoptions.find(a => a.adoptionId === adoptionId);
    } catch (error) {
        showError('Error loading adoption');
        return null;
    }
}

// Display adoption statistics (Admin)
async function displayAdoptionStatistics(containerId) {
    const container = document.getElementById(containerId);
    container.innerHTML = '<p>Loading statistics...</p>';
    
    try {
        const adoptions = await getAllAdoptions();
        
        if (!adoptions || adoptions.length === 0) {
            container.innerHTML = '<p>No adoption data available.</p>';
            return;
        }
        
        const approved = adoptions.filter(a => (a.status || a.adoptionStatus) === 'Approved').length;
        const pending = adoptions.filter(a => (a.status || a.adoptionStatus) === 'Pending').length;
        const rejected = adoptions.filter(a => (a.status || a.adoptionStatus) === 'Rejected').length;
        
        const html = `
            <div class="statistics-grid">
                <div class="stat-card">
                    <h3>Total Adoptions</h3>
                    <p class="stat-number">${adoptions.length}</p>
                </div>
                <div class="stat-card success">
                    <h3>Approved</h3>
                    <p class="stat-number">${approved}</p>
                </div>
                <div class="stat-card warning">
                    <h3>Pending</h3>
                    <p class="stat-number">${pending}</p>
                </div>
                <div class="stat-card danger">
                    <h3>Rejected</h3>
                    <p class="stat-number">${rejected}</p>
                </div>
            </div>
        `;
        
        container.innerHTML = html;
    } catch (error) {
        container.innerHTML = '<p class="error">Error loading statistics</p>';
    }
}

// Export adoption report
async function exportAdoptionReport() {
    try {
        const report = await getAdoptionReport();
        
        if (!report) {
            showError('Error generating report');
            return;
        }
        
        // Create CSV content
        let csv = 'Pet Name,Species,Breed,Adopter Name,Status,Request Date,Approval Date\n';
        
        report.forEach(adoption => {
            csv += `${adoption.pet?.name || 'N/A'},${adoption.pet?.species || 'N/A'},${adoption.pet?.breed || 'N/A'},${adoption.adopter?.name || 'N/A'},${adoption.status || adoption.adoptionStatus || 'Pending'},${adoption.adoptionDate || adoption.requestDate || 'N/A'},${adoption.approvalDate || 'N/A'}\n`;
        });
        
        // Create download link
        const element = document.createElement('a');
        element.setAttribute('href', 'data:text/csv;charset=utf-8,' + encodeURIComponent(csv));
        element.setAttribute('download', 'adoption_report.csv');
        element.style.display = 'none';
        document.body.appendChild(element);
        element.click();
        document.body.removeChild(element);
        
        showSuccess('Report exported successfully');
    } catch (error) {
        showError('Error exporting report');
    }
}
