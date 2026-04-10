# Pet Adoption System - Complete Setup & Deployment Guide

## 🎯 Overview

This guide will help you set up and run the complete Pet Adoption Management System with both backend (Spring Boot) and frontend (Vanilla JavaScript).

## ⚙️ Prerequisites

### System Requirements
- **Java:** JDK 8 or higher
- **Maven:** 3.6 or higher (for building backend)
- **MySQL:** 5.7 or higher
- **Web Browser:** Chrome, Firefox, Safari, or Edge (modern version)
- **Terminal/Command Prompt:** For running commands

### Verification
```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Check MySQL version
mysql --version
```

## 🗄️ Database Setup

### Step 1: Create Database

```bash
# Connect to MySQL
mysql -u root -p

# Create database
CREATE DATABASE pet_adoption_system;

# Create user (optional, for security)
CREATE USER 'petadmin'@'localhost' IDENTIFIED BY 'password123';
GRANT ALL PRIVILEGES ON pet_adoption_system.* TO 'petadmin'@'localhost';
FLUSH PRIVILEGES;

# Exit MySQL
EXIT;
```

## 🧪 Backend Setup

### Step 1: Configure Database Connection

Edit `src/main/resources/application.properties`:

```properties
# MySQL Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/pet_adoption_system?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=123456789
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true

# Server Configuration
server.port=8080
server.servlet.context-path=/api

# Logging
logging.level.root=INFO
logging.level.com.petadoption=DEBUG
```

### Step 2: Build Backend

```bash
# Navigate to project root
cd /Users/nilay/Documents/Projects/OOAD-Project/OOAD_petAdoption_miniProj

# Build with Maven
mvn clean install

# Or skip tests for faster build
mvn clean install -DskipTests
```

### Step 3: Run Backend Server

```bash
# Run Spring Boot application
mvn spring-boot:run

# Alternative: Run JAR directly (if built)
java -jar target/PetadoptionApplication.jar
```

**Expected Output:**
```
Started PetadoptionApplication in X.XXX seconds
```

**Server URL:** `http://localhost:8080`

### Verify Backend Is Running

```bash
# In a new terminal, test an endpoint
curl http://localhost:8080/api/auth

# Or open in browser
# http://localhost:8080/api/pets/all?role=ADMIN
```

## 🎨 Frontend Setup

### Step 1: Navigate to Frontend Directory

```bash
cd /Users/nilay/Documents/Projects/OOAD-Project/frontend
```

### Step 2: Start Local Web Server

Choose one option based on your system:

**Option A: Python 3 (Most common)**
```bash
python -m http.server 8000
```

**Option B: Python 2**
```bash
python -m SimpleHTTPServer 8000
```

**Option C: Node.js (with http-server)**
```bash
npm install -g http-server
http-server
```

**Option D: Live Server (VS Code Extension)**
- Install "Live Server" extension in VS Code
- Right-click on `index.html` → "Open with Live Server"

### Step 3: Access Frontend

Open in browser:
**`http://localhost:8000`**

**Alternative URLs:**
- `http://localhost:3000` (if using Node.js http-server)
- `http://127.0.0.1:5500` (if using VS Code Live Server)

## ✅ System Verification

### 1. Check Backend is Running

```bash
curl -X GET http://localhost:8080/api/pets/all?role=ADMIN \
  -H "Content-Type: application/json"
```

Expected: Returns JSON array of pets (initially empty)

### 2. Check Frontend Loads

Navigate to `http://localhost:8000` in browser
- Should see landing page with login button
- No console errors in DevTools (F12)

### 3. Test Authentication

1. Go to `http://localhost:8000/login.html`
2. Try signup with new account:
   - Username: `testuser`
   - Email: `test@example.com`
   - Password: `password123`
   - Role: `ADOPTER`
3. Should see "Account created successfully!" message

### 4. Test Login

1. Go to `http://localhost:8000/login.html`
2. Login with credentials from step 3
3. Should redirect to `adopter-dashboard.html`
4. Should see "Available Pets" section

## 📝 Default Test Data

After database creation, you can add test data:

### Inserting Test Data via API

```bash
# Add test adopter
curl -X POST http://localhost:8080/api/adopters/add \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "phone": "1234567890",
    "address": "123 Main St, City, State",
    "experienceLevel": "Beginner"
  }' \
  -G -d "role=ADMIN"

# Add test shelter
curl -X POST http://localhost:8080/api/shelters/add \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Happy Paws Shelter",
    "capacity": 50,
    "contactNo": "5551234567"
  }' \
  -G -d "role=ADMIN"

# Add test pet
curl -X POST http://localhost:8080/api/pets/add \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Buddy",
    "species": "Dog",
    "breed": "Golden Retriever",
    "age": 3,
    "gender": "Male",
    "healthStatus": "Healthy",
    "adoptionStatus": "Available",
    "shelterId": 1
  }' \
  -G -d "role=ADMIN"
```

## 🐛 Troubleshooting

### Backend Issues

**Port 8080 Already in Use**
```bash
# Find process using port 8080
lsof -i :8080

# Kill process (on Mac/Linux)
kill -9 <PID>

# Or use different port in application.properties
server.port=8081
```

**Database Connection Error**
```
Error: com.mysql.jdbc.exceptions.jdbc4.CommunicationsException
```
**Solution:**
- Check MySQL is running: `mysql -u root -p`
- Verify database credentials in `application.properties`
- Ensure database `pet_adoption_system` exists

**Maven Build Fails**
```bash
# Clean and retry
mvn clean
mvn install -DskipTests

# Or update Maven
mvn --version
```

### Frontend Issues

**CORS Error in Console**
```
Access to XMLHttpRequest at 'http://localhost:8080/...' from origin 
'http://localhost:8000' has been blocked by CORS policy
```
**Solution:**
Add to backend `application.properties`:
```properties
# CORS Configuration
cors.allowed.origins=http://localhost:8000,http://localhost:3000
cors.allowed.methods=GET,POST,PUT,DELETE,OPTIONS
```

**API Not Connecting**
- Check backend is running: `curl http://localhost:8080`
- Verify API_BASE_URL in `frontend/js/api.js` is correct
- Check browser console (F12) for detailed errors

**Page Not Loading**
- Clear browser cache (Ctrl+Shift+Delete)
- Hard refresh (Ctrl+Shift+R or Cmd+Shift+R)
- Check all file paths in HTML (css/style.css, js/api.js, etc.)

**Login Not Working**
- Check browser console for validation errors
- Verify backend is accepting authentication requests
- Check localStorage isn't corrupted (DevTools → Application → Storage → Clear All)

## 🔄 Complete Workflow

### First Time Setup

```bash
# Terminal 1: Backend
cd /Users/nilay/Documents/Projects/OOAD-Project/OOAD_petAdoption_miniProj
mvn spring-boot:run

# Terminal 2: Frontend
cd /Users/nilay/Documents/Projects/OOAD-Project/frontend
python -m http.server 8000

# Terminal 3: Open browser
# http://localhost:8000
```

### Daily Development

```
1. Start Backend (if not running)
2. Start Frontend (if not running)
3. Open http://localhost:8000
4. Make code changes
5. Refresh browser to see changes

# For backend changes, restart mvn spring-boot:run
# For frontend changes, just refresh browser
```

## 📊 Available Roles

### Admin (`ADMIN`)
- Full system access
- Manage all entities (pets, shelters, vets, staff, admins)
- Review and approve adoptions
- View reports
- Dashboard: `admin-dashboard.html`

### Staff (`STAFF`)
- Manage pets, medical records, vaccinations
- View shelter information
- Limited to operations
- Dashboard: `staff-dashboard.html`

### Adopter (`ADOPTER`)
- Browse available pets
- Apply for adoption
- Track applications
- View pet details and vaccinations
- Dashboard: `adopter-dashboard.html`

## 🎯 Quick Test Scenarios

### Scenario 1: Create and Approve Adoption

1. **Admin:** Add a shelter and pet
2. **Admin:** Create an adopter account
3. **Adopter:** Login and apply for the pet
4. **Admin:** Approve the adoption request
5. **Adopter:** See "Approved" status

### Scenario 2: Medical Records Workflow

1. **Admin:** Add a vet
2. **Staff:** Add a medical record for a pet
3. **Admin:** View medical records in reports
4. **Staff:** Update vaccination status
5. **Adopter:** See vaccination records before adopting

### Scenario 3: Multi-Shelter Management

1. **Admin:** Create multiple shelters
2. **Staff:** Distribute pets across shelters
3. **Admin:** View capacity utilization report
4. **Adopter:** See pet availability by location

## 📈 Performance Optimization

### Frontend
```bash
# Minify CSS and JavaScript for production
# Use production build tools (Webpack, Gulp, Parcel)
```

### Backend
```properties
# Enable query optimization
spring.jpa.properties.hibernate.jdbc.batch_size=20
spring.jpa.properties.hibernate.order_inserts=true
spring.jpa.properties.hibernate.order_updates=true
```

## 🔒 Security Checklist

- [ ] Change default MySQL password
- [ ] Use HTTPS in production
- [ ] Enable CORS only for allowed origins
- [ ] Use environment variables for sensitive data
- [ ] Validate all user inputs
- [ ] Implement rate limiting
- [ ] Use prepared statements to prevent SQL injection
- [ ] Hash passwords securely

## 📚 API Documentation

API endpoints are documented in:
- Backend: `src/main/java/com/petadoption/controller/`
- Frontend: `frontend/js/api.js`

## 🐞 Debug Mode

### Enable Debug Logging

Edit `application.properties`:
```properties
logging.level.com.petadoption=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

### Browser Console Debugging

```javascript
// In browser console (F12)
getCurrentUser()     // View current user
localStorage         // View all stored data
// Check Network tab for API calls
```

## 📞 Support

If you encounter issues:

1. Check the logs:
   - Backend: Terminal output
   - Frontend: Browser Console (F12)

2. Verify prerequisites are installed

3. Try the troubleshooting section above

4. Review the README files in each directory

## 🎉 Success!

Once everything is running:
- ✅ Backend server running on `http://localhost:8080`
- ✅ Frontend accessible on `http://localhost:8000`
- ✅ Database created and tables initialized
- ✅ Can login and navigate dashboards
- ✅ API calls working properly

## 📋 Next Steps

1. Explore the Admin Dashboard
2. Create test data (shelters, vets, pets)
3. Test user workflows
4. Customize styling in `frontend/css/style.css`
5. Extend API functionality in backend
6. Add more validation and error handling

---

**Version:** 1.0.0  
**Last Updated:** 2024  
**Questions?** Check frontend/README.md and run backend tests with `mvn test`
