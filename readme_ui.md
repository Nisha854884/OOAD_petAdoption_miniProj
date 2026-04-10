# Pet Adoption Management System - Frontend Documentation

## Overview

This is a vanilla HTML5, CSS3, and JavaScript frontend for the Pet Adoption Management System. It provides a complete user interface for adopters, staff, and administrators to interact with the pet adoption platform.

## 📁 Project Structure

```
frontend/
├── index.html                 # Landing page with automatic role-based routing
├── login.html                 # Authentication page (login/signup)
├── admin-dashboard.html       # Admin dashboard with full system management
├── staff-dashboard.html       # Staff dashboard for veterinary and pet management
├── adopter-dashboard.html     # Adopter portal for browsing and applying for pets
├── css/
│   └── style.css             # Comprehensive styling for all pages
├── js/
│   ├── api.js                # Centralized API communication wrapper (30+ functions)
│   ├── auth.js               # Authentication and form validation logic
│   ├── pets.js               # Pet management functions (display, add, edit, delete)
│   └── adoptions.js          # Adoption workflow functions
└── images/                   # Image assets directory

## 🚀 Getting Started

### Prerequisites
- A modern web browser (Chrome, Firefox, Safari, Edge)
- The Pet Adoption backend server running on `http://localhost:8080`
- Python or Node.js live server for local development (optional)

### Setup

1. **Navigate to frontend directory:**
   ```bash
   cd frontend
   ```

2. **Start a local web server** (optional, but recommended):
   
   **Using Python 3:**
   ```bash
   python -m http.server 8000
   ```
   
   **Using Python 2:**
   ```bash
   python -m SimpleHTTPServer 8000
   ```
   
   **Using Node.js (with http-server):**
   ```bash
   npm install -g http-server
   http-server
   ```

3. **Open in browser:**
   - Navigate to `http://localhost:8000` (or `http://localhost:3000` for Node.js)

## 📖 Features by Role

### 👥 Adopter Portal
**URL:** `adopter-dashboard.html` (auto-redirects from index.html)

**Features:**
- 🐾 Browse available pets with detailed information
- 🔍 Advanced search and filter by species, age, gender, health status
- 📋 Apply for pet adoption
- ❤️ Track adoption applications in real-time
- 💉 View pet vaccination and medical records
- ❓ FAQ section with adoption process information

**Key Pages:**
- Available Pets: Grid view of all adoptable pets
- My Applications: Track status of adoption requests
- Search Pets: Advanced search with multiple filters
- My Profile: User account information
- FAQ: Common questions about adoption

### 👨‍💼 Staff Dashboard
**URL:** `staff-dashboard.html` (auto-redirects from index.html)

**Features:**
- 🐕 Complete pet management (add, edit, delete)
- 📋 Manage medical records for pets
- 💉 Track and update pet vaccinations
- 🏠 View all shelters
- 📊 Quick statistics overview

**Key Sections:**
- Dashboard Overview: Quick statistics
- Pet Management: Full CRUD operations
- Medical Records: Add and edit medical records
- Vaccinations: Manage vaccination records
- Shelters: View shelter information

### ⚙️ Admin Dashboard
**URL:** `admin-dashboard.html` (auto-redirects from index.html)

**Features:**
- 🐕 Complete pet management
- 🏠 Manage shelters and capacity
- ⚕️ Manage veterinarians
- 📋 Manage medical records
- 💉 Manage vaccinations
- 👥 Manage staff members
- ⚙️ Manage admin accounts
- ❤️ Review and approve adoption requests
- 📈 Comprehensive reports and analytics

**Key Sections:**
- Dashboard Overview: System statistics
- Pets: Complete pet inventory management
- Shelters: Manage shelters and capacity
- Vets: Manage veterinarian information
- Medical Records: Full medical history
- Vaccinations: Track all vaccinations
- Staff: Manage staff accounts
- Admins: Manage admin accounts
- Adoptions: Review and approve/reject adoption requests
- Reports: Analytics including adoption statistics, pet status reports, and shelter capacity

## 🔐 Authentication

### Login Page (`login.html`)

**Features:**
- Username/Password login
- Account creation (signup)
- Role selection during signup
- Form validation with real-time error messages
- Automatic redirect to dashboard based on role

**Validation Rules:**
- **Username:** 3-50 characters
- **Password:** Minimum 6 characters
- **Email:** Valid email format
- **Phone:** 10-digit format (adopters)
- **Name:** 2-100 characters (adopters)
- **Address:** 5-255 characters (adopters)

## 📐 API Integration

### API Wrapper (`js/api.js`)

All API calls go through a centralized wrapper that handles:
- Authentication headers and token management
- Role-based parameter injection
- Error handling and user notifications
- Response parsing and validation

**Main Functions:**

#### Authentication
- `login(credentials)` - User login
- `signup(data)` - Create new account
- `logout()` - User logout
- `getCurrentUser()` - Get current user from localStorage
- `getUserRole()` - Get current user's role
- `isLoggedIn()` - Check if currently logged in

#### Pet Operations
- `getAllPets()` - Get all pets
- `getPetById(petId)` - Get specific pet
- `getAvailablePets()` - Get available pets for adoption
- `addPet(data)` - Create new pet
- `updatePet(petId, data)` - Update pet information
- `deletePet(petId)` - Delete pet

#### Adoption Operations
- `requestAdoption(data)` - Submit adoption request
- `getMyAdoptions()` - Get user's adoption applications
- `getAllAdoptions()` - Get all adoptions (admin/staff)
- `updateAdoptionStatus(adoptionId, data)` - Update adoption status

#### Medical & Vaccination
- `getAllMedicalRecords()` - Get all medical records
- `getMedicalRecordsByPet(petId)` - Get pet's medical records
- `addMedicalRecord(data)` - Create medical record
- `updateMedicalRecord(recordId, data)` - Update medical record
- `deleteMedicalRecord(recordId)` - Delete medical record
- `getAllVaccinations()` - Get all vaccinations
- `getVaccinationsByPet(petId)` - Get pet's vaccinations
- `addVaccination(data)` - Create vaccination record
- `updateVaccination(vacId, data)` - Update vaccination
- `deleteVaccination(vacId)` - Delete vaccination

#### Shelter Operations
- `getAllShelters()` - Get all shelters
- `getShelterById(shelterId)` - Get specific shelter
- `addShelter(data)` - Create shelter
- `updateShelter(shelterId, data)` - Update shelter
- `deleteShelter(shelterId)` - Delete shelter
- `getShelterCapacityUtilization()` - Get shelter capacity data

#### Veterinarian Operations
- `getAllVets()` - Get all vets
- `addVet(data)` - Create vet
- `updateVet(vetId, data)` - Update vet
- `deleteVet(vetId)` - Delete vet

#### Staff Operations
- `getAllStaff()` - Get all staff
- `addStaff(data)` - Add staff member
- `updateStaff(staffId, data)` - Update staff
- `deleteStaff(staffId)` - Delete staff

#### Admin Operations
- `getAllAdmins()` - Get all admins
- `addAdmin(data)` - Create admin
- `updateAdmin(adminId, data)` - Update admin

#### Reports
- `getPetStatusReport()` - Pet status statistics
- `getAdoptionReport()` - Adoption statistics
- `getAverageAgeReport()` - Average pet age data

#### Utilities
- `showSuccess(message)` - Display success notification
- `showError(message)` - Display error notification

### API Configuration

The API communicates with the backend at:
```javascript
API_BASE_URL = 'http://localhost:8080'
```

To change the backend URL, edit the `API_BASE_URL` in `js/api.js`.

## 📋 Form Management (`js/auth.js`)

### Validation Rules

```javascript
const validationRules = {
    username: { minLength: 3, maxLength: 50 },
    password: { minLength: 6 },
    email: { pattern: /^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/ },
    phone: { pattern: /^[0-9]{10}$/ },
    name: { minLength: 2, maxLength: 100 },
    address: { minLength: 5, maxLength: 255 }
};
```

### Key Functions

- `validateField(fieldName, value)` - Validate individual field
- `handleLogin(event)` - Process login form
- `handleSignup(event)` - Process signup form
- `toggleAuthForm(formType)` - Switch between login/signup tabs
- `checkAuthStatus()` - Verify user is authenticated
- `preventLoggedInAccess()` - Block logged-in users from login page

## 🎨 Styling (`css/style.css`)

### Design System

**Color Palette:**
- Primary: `#2c3e50` (Dark Blue)
- Secondary: `#3498db` (Light Blue)
- Success: `#27ae60` (Green)
- Danger: `#e74c3c` (Red)
- Warning: `#f39c12` (Orange)

**Components:**
- Cards with hover effects
- Responsive grid layouts
- Modal dialogs
- Status badges
- Tables with striped rows
- Form inputs with validation states
- Navigation with active states
- Responsive sidebar navigation

### Responsive Breakpoints

- Desktop: 1200px+
- Tablet: 768px - 1199px
- Mobile: < 768px

## 🐾 Pet Management (`js/pets.js`)

**Functions:**

- `displayAllPets(containerId)` - Show all pets in grid
- `displayAvailablePets(containerId)` - Show available pets only
- `viewPetDetails(petId)` - Show detailed pet information with medical records
- `showAddPetForm()` - Display pet creation form
- `editPetForm(petId)` - Show pet edit form
- `handleAddPet(event)` - Process pet creation
- `handleUpdatePet(event, petId)` - Process pet update
- `deletePetConfirm(petId)` - Confirm and delete pet
- `populateSheltersDropdown(elementId)` - Fill shelter select dropdown

## ❤️ Adoption Management (`js/adoptions.js`)

**Functions:**

- `displayAdoptionRequests(containerId)` - Show all adoption requests (admin/staff)
- `displayMyAdoptions(containerId)` - Show user's adoptions
- `viewAdoptionDetails(adoptionId)` - Show detailed adoption information
- `adoptPet(petId)` - Start adoption process
- `confirmAdoption(petId)` - Submit adoption request
- `approveAdoption(adoptionId)` - Admin approve adoption
- `rejectAdoption(adoptionId)` - Admin reject adoption
- `displayAdoptionStatistics(containerId)` - Show adoption stats
- `exportAdoptionReport()` - Export adoption data as CSV

## 🔒 Session Management

User session is stored in browser's `localStorage`:

```javascript
{
    userId: 123,
    username: "john_doe",
    email: "john@example.com",
    role: "ADOPTER"  // ADMIN, STAFF, or ADOPTER
}
```

## 🐛 Debugging

### Enable Console Logs

Open browser Developer Tools (F12) and check Console tab for:
- API request logs
- Validation messages
- Error traces
- Authentication status

### Common Issues

1. **API Connection Error**
   - Ensure backend server is running on http://localhost:8080
   - Check browser console for CORS errors
   - Verify API_BASE_URL in js/api.js

2. **Login Issues**
   - Clear localStorage and try again
   - Check that backend has the same validation rules
   - Verify role is correctly set in database

3. **Page Not Loading**
   - Check all .js and .css file paths are correct
   - Verify browser supports ES6 (modern Chrome/Firefox)
   - Check network tab for failed resource loadings

## 📱 Browser Support

- ✅ Chrome/Edge 90+
- ✅ Firefox 88+
- ✅ Safari 14+
- ⚠️ Internet Explorer - Not supported

## 🔄 Workflow Examples

### Adopter Workflow
1. Signup → Login → View Available Pets → Search/Filter → View Details → Apply → Track Status

### Staff Workflow
1. Login → Dashboard Overview → Select Task (Pets/Medical/Vaccines) → Perform CRUD Operations

### Admin Workflow
1. Login → Dashboard Overview → Select Management Section → Full CRUD + Reports + Approvals

## 📞 API Endpoints

Backend API uses these endpoints (defined in comments in api.js):

```
Authentication:
POST   /api/auth/login
POST   /api/auth/signup
GET    /api/auth/logout

Pets:
GET    /api/pets/all?role=ROLE
GET    /api/pets/available?role=ROLE
GET    /api/pets/{id}?role=ROLE
POST   /api/pets/add?role=ROLE
PUT    /api/pets/update/{id}?role=ROLE
DELETE /api/pets/delete/{id}?role=ROLE

... (and many more - see api.js for complete list)
```

## 🚀 Deployment

To deploy to production:

1. Update `API_BASE_URL` in `js/api.js` to production API endpoint
2. Use a production-grade web server (Nginx, Apache)
3. Enable HTTPS
4. Set appropriate CORS headers on backend
5. Minify CSS and JavaScript for faster load times

## 📚 Additional Resources

- [HTTP Status Codes](https://httpwg.org/specs/rfc7231.html#status.codes)
- [JavaScript Fetch API](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API)
- [CSS Grid Guide](https://css-tricks.com/snippets/css/complete-guide-grid/)
- [Form Validation Best Practices](https://www.smashingmagazine.com/2022/09/inline-validation-web-forms-ux/)

## 🤝 Contributing

To extend this frontend:

1. **Add new API functions** in `js/api.js`
2. **Create feature-specific JS files** following the pattern of `js/pets.js`
3. **Style new components** in `css/style.css` using the design system
4. **Update this README** with new features

## 📄 License

This project is part of the Pet Adoption Management System OOAD mini project.

## ✅ Completed Features

- ✅ Authentication (Login/Signup)
- ✅ Role-based dashboards (Admin, Staff, Adopter)
- ✅ Pet browsing and management
- ✅ Adoption workflow
- ✅ Medical records management
- ✅ Vaccination tracking
- ✅ Shelter management
- ✅ Staff and admin management
- ✅ Reports and analytics
- ✅ Form validation
- ✅ Responsive design
- ✅ Error handling

## 🎯 Future Enhancements

- 🔄 Real-time notifications for adoption approvals
- 📸 Pet photo gallery with image uploads
- 💬 Live chat support
- 📱 Mobile app version
- 🗺️ Shelter location map integration
- ⭐ Pet rating and review system
- 📧 Email notifications
- 🔔 Push notifications

---

**Last Updated:** 2024
**Version:** 1.0.0
