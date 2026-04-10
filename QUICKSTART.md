# Quick Start Guide - Pet Adoption System

## 🚀 Get Running in 5 Minutes

### Prerequisites Installed?
- ✅ Java JDK installed
- ✅ Maven installed  
- ✅ MySQL running
- ✅ Modern web browser

If not, see `DEPLOYMENT.md` for setup.

## Step 1: Start Backend (2 min)

```bash
cd OOAD_petAdoption_miniProj

# Update database credentials in src/main/resources/application.properties
# Then run (recommended):
chmod +x run_backend.sh
./run_backend.sh

# Alternative (direct Maven):
# ./mvnw spring-boot:run
```

**Wait for:** `Started PetadoptionApplication in X.XXX seconds`

## Step 2: Frontend Access (1 min)

The frontend is served by Spring Boot from the `frontend/` folder.

```bash
open http://localhost:8081/api/
```

Optional fallback (only if you want a separate static server):

```bash
cd frontend

# Using Python 3:
python3 -m http.server 8010

# OR Node.js:
http-server -p 8010
```

## Step 3: Access Application (2 min)

Open browser to: **`http://localhost:8081/api/`**

### First Time?
1. Click "Sign Up" on login page
2. Create test account:
   - Username: `testadmin`
   - Email: `admin@test.com`
   - Password: `password123`
   - Role: `ADMIN`
3. Click "Login"
4. You're in!

## 📊 What You Can Do

### As Admin
- View `http://localhost:8081/api/admin-dashboard.html`
- Add shelters, pets, vets
- Manage staff and admins
- Approve/reject adoptions
- View reports

### As Staff
- View `http://localhost:8081/api/staff-dashboard.html`
- Manage pets
- Add medical records
- Track vaccinations

### As Adopter
- View `http://localhost:8081/api/adopter-dashboard.html`
- Browse available pets
- Apply for adoption
- Track applications

## 🐛 Troubleshooting

**Backend won't start?**
- Check MySQL is running
- Update database credentials in `application.properties`
- Try: `./run_backend.sh`
- If needed: `./mvnw clean install -DskipTests && ./mvnw spring-boot:run`

**Frontend shows "Cannot GET"?**
- Make sure you're on `http://localhost:8081/api/`
- Not `http://localhost` or `http://localhost:8080`

**Login not working?**
- Check browser console (F12) for errors
- Verify backend is running: `curl http://localhost:8081/api/pets/all?role=Admin`

## 📚 Full Documentation

- **Backend Setup:** See `DEPLOYMENT.md`
- **Frontend Guide:** See `frontend/README.md`
- **Architecture:** See project structure in `DEPLOYMENT.md`

## ✅ Quick Checklist

- [ ] Backend running on `http://localhost:8081/api`
- [ ] Frontend reachable at `http://localhost:8081/api/`
- [ ] Can access landing page
- [ ] Can sign up new account
- [ ] Can login
- [ ] Redirected to correct dashboard
- [ ] Can view available pets/data

## 🎯 Next: Test Features

### Test Adoption Workflow
1. **As Admin:**
   - Add a shelter
   - Add a vet
   - Add a pet
2. **As Adopter:**
   - Login to adopter account
   - Apply for pet
3. **As Admin:**
   - Approve adoption
   - Check reports

### Test Medical Records
1. **As Admin:**
   - Add medical record for pet
   - Add vaccination
2. **As Staff:**
   - View and update records
3. **As Adopter:**
   - See vaccination status

## 💡 Pro Tips

- Use browser DevTools (F12) to debug
- Check browser console for API errors
- Use "Preview on existing port" in VS Code for local development
- Database is auto-created on first run

## 🆘 Need Help?

1. Check `DEPLOYMENT.md` for detailed setup
2. Check `frontend/README.md` for frontend docs
3. Check browser console for error messages
4. Restart both backend and frontend fresh

---

**You're all set! Happy adoption management! 🐾**

See `frontend/README.md` and `DEPLOYMENT.md` for complete documentation.
