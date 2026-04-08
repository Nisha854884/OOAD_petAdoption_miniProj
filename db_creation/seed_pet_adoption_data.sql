USE pet_adoption_system;

-- ------------------------------------------------------------
-- Seed baseline shelters
-- ------------------------------------------------------------
INSERT INTO shelter (name, capacity, contact_no)
SELECT 'Northside Rescue Hub', 120, '9001001001' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shelter WHERE name = 'Northside Rescue Hub');

INSERT INTO shelter (name, capacity, contact_no)
SELECT 'Lakeside Animal Care', 95, '9001001002' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shelter WHERE name = 'Lakeside Animal Care');

INSERT INTO shelter (name, capacity, contact_no)
SELECT 'Greenfield Pet Haven', 80, '9001001003' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shelter WHERE name = 'Greenfield Pet Haven');

INSERT INTO shelter (name, capacity, contact_no)
SELECT 'City Paws Transit Center', 70, '9001001004' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM shelter WHERE name = 'City Paws Transit Center');

-- ------------------------------------------------------------
-- Seed user accounts by role
-- ------------------------------------------------------------
INSERT INTO users (username, password, role, created_at)
SELECT 'seed_admin_ops', 'Pass@123', 'Admin', NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'seed_admin_ops');

INSERT INTO users (username, password, role, created_at)
SELECT 'seed_admin_reports', 'Pass@123', 'Admin', NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'seed_admin_reports');

INSERT INTO users (username, password, role, created_at)
SELECT 'seed_staff_intake', 'Pass@123', 'Staff', NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'seed_staff_intake');

INSERT INTO users (username, password, role, created_at)
SELECT 'seed_staff_medical', 'Pass@123', 'Staff', NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'seed_staff_medical');

INSERT INTO users (username, password, role, created_at)
SELECT 'seed_staff_outreach', 'Pass@123', 'Staff', NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'seed_staff_outreach');

INSERT INTO users (username, password, role, created_at)
SELECT 'seed_adopter_anna', 'Pass@123', 'Adopter', NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'seed_adopter_anna');

INSERT INTO users (username, password, role, created_at)
SELECT 'seed_adopter_nisha', 'Pass@123', 'Adopter', NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'seed_adopter_nisha');

INSERT INTO users (username, password, role, created_at)
SELECT 'seed_adopter_rahul', 'Pass@123', 'Adopter', NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'seed_adopter_rahul');

INSERT INTO users (username, password, role, created_at)
SELECT 'seed_adopter_isha', 'Pass@123', 'Adopter', NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'seed_adopter_isha');

INSERT INTO users (username, password, role, created_at)
SELECT 'seed_adopter_kevin', 'Pass@123', 'Adopter', NOW() FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'seed_adopter_kevin');

-- ------------------------------------------------------------
-- Seed admin profiles
-- ------------------------------------------------------------
INSERT INTO admin (user_id, department, permissions)
SELECT u.user_id, 'Operations', 'MANAGE_PETS,MANAGE_SHELTERS,MANAGE_STAFF,REVIEW_ADOPTIONS'
FROM users u
WHERE u.username = 'seed_admin_ops'
  AND NOT EXISTS (SELECT 1 FROM admin a WHERE a.user_id = u.user_id);

INSERT INTO admin (user_id, department, permissions)
SELECT u.user_id, 'Reporting', 'VIEW_REPORTS,EXPORT_REPORTS,REVIEW_ADOPTIONS'
FROM users u
WHERE u.username = 'seed_admin_reports'
  AND NOT EXISTS (SELECT 1 FROM admin a WHERE a.user_id = u.user_id);

-- ------------------------------------------------------------
-- Seed staff profiles
-- ------------------------------------------------------------
INSERT INTO staff (user_id, shelter_id, position, phone, email)
SELECT u.user_id, s.shelter_id, 'Intake Coordinator', '9002001001', 'intake@northside.org'
FROM users u
JOIN shelter s ON s.name = 'Northside Rescue Hub'
WHERE u.username = 'seed_staff_intake'
  AND NOT EXISTS (SELECT 1 FROM staff st WHERE st.user_id = u.user_id);

INSERT INTO staff (user_id, shelter_id, position, phone, email)
SELECT u.user_id, s.shelter_id, 'Veterinary Assistant', '9002001002', 'medical@lakeside.org'
FROM users u
JOIN shelter s ON s.name = 'Lakeside Animal Care'
WHERE u.username = 'seed_staff_medical'
  AND NOT EXISTS (SELECT 1 FROM staff st WHERE st.user_id = u.user_id);

INSERT INTO staff (user_id, shelter_id, position, phone, email)
SELECT u.user_id, s.shelter_id, 'Adoption Counselor', '9002001003', 'outreach@greenfield.org'
FROM users u
JOIN shelter s ON s.name = 'Greenfield Pet Haven'
WHERE u.username = 'seed_staff_outreach'
  AND NOT EXISTS (SELECT 1 FROM staff st WHERE st.user_id = u.user_id);

-- ------------------------------------------------------------
-- Seed adopter profiles
-- ------------------------------------------------------------
INSERT INTO adopter (user_id, name, phone, address, experience_level)
SELECT u.user_id, 'Anna George', '9100000001', '12 Palm Street, Bangalore', 'Beginner'
FROM users u
WHERE u.username = 'seed_adopter_anna'
  AND NOT EXISTS (SELECT 1 FROM adopter a WHERE a.user_id = u.user_id);

INSERT INTO adopter (user_id, name, phone, address, experience_level)
SELECT u.user_id, 'Nisha Menon', '9100000002', '44 Lake View, Kochi', 'Intermediate'
FROM users u
WHERE u.username = 'seed_adopter_nisha'
  AND NOT EXISTS (SELECT 1 FROM adopter a WHERE a.user_id = u.user_id);

INSERT INTO adopter (user_id, name, phone, address, experience_level)
SELECT u.user_id, 'Rahul Das', '9100000003', '8 Park Avenue, Pune', 'Experienced'
FROM users u
WHERE u.username = 'seed_adopter_rahul'
  AND NOT EXISTS (SELECT 1 FROM adopter a WHERE a.user_id = u.user_id);

INSERT INTO adopter (user_id, name, phone, address, experience_level)
SELECT u.user_id, 'Isha Kapoor', '9100000004', '90 Green Lane, Mumbai', 'Beginner'
FROM users u
WHERE u.username = 'seed_adopter_isha'
  AND NOT EXISTS (SELECT 1 FROM adopter a WHERE a.user_id = u.user_id);

INSERT INTO adopter (user_id, name, phone, address, experience_level)
SELECT u.user_id, 'Kevin Roy', '9100000005', '21 Hill Road, Chennai', 'Intermediate'
FROM users u
WHERE u.username = 'seed_adopter_kevin'
  AND NOT EXISTS (SELECT 1 FROM adopter a WHERE a.user_id = u.user_id);

-- ------------------------------------------------------------
-- Seed pets (mixed species for realistic adoption portal usage)
-- ------------------------------------------------------------
INSERT INTO pet (name, species, breed, age, gender, health_status, adoption_status, shelter_id)
SELECT 'Buddy', 'Dog', 'Labrador Retriever', 3, 'Male', 'Healthy', 'Available', s.shelter_id
FROM shelter s
WHERE s.name = 'Northside Rescue Hub'
  AND NOT EXISTS (SELECT 1 FROM pet p WHERE p.name = 'Buddy' AND p.species = 'Dog');

INSERT INTO pet (name, species, breed, age, gender, health_status, adoption_status, shelter_id)
SELECT 'Mochi', 'Cat', 'Persian', 2, 'Female', 'Healthy', 'Adopted', s.shelter_id
FROM shelter s
WHERE s.name = 'Lakeside Animal Care'
  AND NOT EXISTS (SELECT 1 FROM pet p WHERE p.name = 'Mochi' AND p.species = 'Cat');

INSERT INTO pet (name, species, breed, age, gender, health_status, adoption_status, shelter_id)
SELECT 'Kiwi', 'Bird', 'Parakeet', 1, 'Female', 'Healthy', 'Available', s.shelter_id
FROM shelter s
WHERE s.name = 'Greenfield Pet Haven'
  AND NOT EXISTS (SELECT 1 FROM pet p WHERE p.name = 'Kiwi' AND p.species = 'Bird');

INSERT INTO pet (name, species, breed, age, gender, health_status, adoption_status, shelter_id)
SELECT 'Clover', 'Rabbit', 'Mini Lop', 2, 'Male', 'Healthy', 'Pending', s.shelter_id
FROM shelter s
WHERE s.name = 'City Paws Transit Center'
  AND NOT EXISTS (SELECT 1 FROM pet p WHERE p.name = 'Clover' AND p.species = 'Rabbit');

INSERT INTO pet (name, species, breed, age, gender, health_status, adoption_status, shelter_id)
SELECT 'Atlas', 'Dog', 'German Shepherd', 4, 'Male', 'Recovering', 'Available', s.shelter_id
FROM shelter s
WHERE s.name = 'Northside Rescue Hub'
  AND NOT EXISTS (SELECT 1 FROM pet p WHERE p.name = 'Atlas' AND p.species = 'Dog');

INSERT INTO pet (name, species, breed, age, gender, health_status, adoption_status, shelter_id)
SELECT 'Luna', 'Cat', 'Siamese', 1, 'Female', 'Healthy', 'Available', s.shelter_id
FROM shelter s
WHERE s.name = 'Lakeside Animal Care'
  AND NOT EXISTS (SELECT 1 FROM pet p WHERE p.name = 'Luna' AND p.species = 'Cat');

INSERT INTO pet (name, species, breed, age, gender, health_status, adoption_status, shelter_id)
SELECT 'Pebble', 'Turtle', 'Red-Eared Slider', 5, 'Female', 'Healthy', 'Available', s.shelter_id
FROM shelter s
WHERE s.name = 'Greenfield Pet Haven'
  AND NOT EXISTS (SELECT 1 FROM pet p WHERE p.name = 'Pebble' AND p.species = 'Turtle');

INSERT INTO pet (name, species, breed, age, gender, health_status, adoption_status, shelter_id)
SELECT 'Nibbles', 'Guinea Pig', 'Abyssinian', 2, 'Male', 'Healthy', 'Available', s.shelter_id
FROM shelter s
WHERE s.name = 'City Paws Transit Center'
  AND NOT EXISTS (SELECT 1 FROM pet p WHERE p.name = 'Nibbles' AND p.species = 'Guinea Pig');

INSERT INTO pet (name, species, breed, age, gender, health_status, adoption_status, shelter_id)
SELECT 'Sunny', 'Bird', 'Cockatiel', 3, 'Male', 'Healthy', 'Available', s.shelter_id
FROM shelter s
WHERE s.name = 'Greenfield Pet Haven'
  AND NOT EXISTS (SELECT 1 FROM pet p WHERE p.name = 'Sunny' AND p.species = 'Bird');

INSERT INTO pet (name, species, breed, age, gender, health_status, adoption_status, shelter_id)
SELECT 'Oreo', 'Hamster', 'Syrian', 1, 'Male', 'Healthy', 'Available', s.shelter_id
FROM shelter s
WHERE s.name = 'City Paws Transit Center'
  AND NOT EXISTS (SELECT 1 FROM pet p WHERE p.name = 'Oreo' AND p.species = 'Hamster');

INSERT INTO pet (name, species, breed, age, gender, health_status, adoption_status, shelter_id)
SELECT 'Hazel', 'Rabbit', 'Netherland Dwarf', 2, 'Female', 'Healthy', 'Adopted', s.shelter_id
FROM shelter s
WHERE s.name = 'Northside Rescue Hub'
  AND NOT EXISTS (SELECT 1 FROM pet p WHERE p.name = 'Hazel' AND p.species = 'Rabbit');

INSERT INTO pet (name, species, breed, age, gender, health_status, adoption_status, shelter_id)
SELECT 'Max', 'Dog', 'Indie', 2, 'Male', 'Healthy', 'Available', s.shelter_id
FROM shelter s
WHERE s.name = 'Lakeside Animal Care'
  AND NOT EXISTS (SELECT 1 FROM pet p WHERE p.name = 'Max' AND p.species = 'Dog');

INSERT INTO pet (name, species, breed, age, gender, health_status, adoption_status, shelter_id)
SELECT 'Simba', 'Cat', 'Maine Coon', 4, 'Male', 'Healthy', 'Available', s.shelter_id
FROM shelter s
WHERE s.name = 'Greenfield Pet Haven'
  AND NOT EXISTS (SELECT 1 FROM pet p WHERE p.name = 'Simba' AND p.species = 'Cat');

INSERT INTO pet (name, species, breed, age, gender, health_status, adoption_status, shelter_id)
SELECT 'Coco', 'Bird', 'Lovebird', 1, 'Female', 'Healthy', 'Available', s.shelter_id
FROM shelter s
WHERE s.name = 'City Paws Transit Center'
  AND NOT EXISTS (SELECT 1 FROM pet p WHERE p.name = 'Coco' AND p.species = 'Bird');

-- ------------------------------------------------------------
-- Seed veterinarians
-- ------------------------------------------------------------
INSERT INTO vet (name, clinic_name, phone, specialization, shelter_id)
SELECT 'Dr. Rhea Menon', 'Northside Animal Clinic', '9201001001', 'General Medicine', s.shelter_id
FROM shelter s
WHERE s.name = 'Northside Rescue Hub'
  AND NOT EXISTS (SELECT 1 FROM vet v WHERE v.name = 'Dr. Rhea Menon');

INSERT INTO vet (name, clinic_name, phone, specialization, shelter_id)
SELECT 'Dr. Arjun Nair', 'Lakeside Vet Care', '9201001002', 'Surgery', s.shelter_id
FROM shelter s
WHERE s.name = 'Lakeside Animal Care'
  AND NOT EXISTS (SELECT 1 FROM vet v WHERE v.name = 'Dr. Arjun Nair');

INSERT INTO vet (name, clinic_name, phone, specialization, shelter_id)
SELECT 'Dr. Siya Thomas', 'Greenfield Exotics', '9201001003', 'Avian & Exotics', s.shelter_id
FROM shelter s
WHERE s.name = 'Greenfield Pet Haven'
  AND NOT EXISTS (SELECT 1 FROM vet v WHERE v.name = 'Dr. Siya Thomas');

INSERT INTO vet (name, clinic_name, phone, specialization, shelter_id)
SELECT 'Dr. Kabir Shah', 'City Paws Medical', '9201001004', 'Dermatology', s.shelter_id
FROM shelter s
WHERE s.name = 'City Paws Transit Center'
  AND NOT EXISTS (SELECT 1 FROM vet v WHERE v.name = 'Dr. Kabir Shah');

-- ------------------------------------------------------------
-- Seed medical records
-- ------------------------------------------------------------
INSERT INTO medical_record (pet_id, vet_id, visit_date, diagnosis, treatment, additional_notes)
SELECT p.pet_id, v.vet_id, '2026-02-10', 'Routine wellness check', 'Deworming and nutrition plan', 'Healthy and active.'
FROM pet p
JOIN vet v ON v.name = 'Dr. Rhea Menon'
WHERE p.name = 'Buddy'
  AND NOT EXISTS (
      SELECT 1 FROM medical_record m
      WHERE m.pet_id = p.pet_id AND m.visit_date = '2026-02-10'
  );

INSERT INTO medical_record (pet_id, vet_id, visit_date, diagnosis, treatment, additional_notes)
SELECT p.pet_id, v.vet_id, '2026-01-22', 'Skin irritation', 'Topical medication for 14 days', 'Follow-up required in 2 weeks.'
FROM pet p
JOIN vet v ON v.name = 'Dr. Kabir Shah'
WHERE p.name = 'Atlas'
  AND NOT EXISTS (
      SELECT 1 FROM medical_record m
      WHERE m.pet_id = p.pet_id AND m.visit_date = '2026-01-22'
  );

INSERT INTO medical_record (pet_id, vet_id, visit_date, diagnosis, treatment, additional_notes)
SELECT p.pet_id, v.vet_id, '2026-03-05', 'Feather stress', 'Vitamin supplements and calm enclosure', 'Improving steadily.'
FROM pet p
JOIN vet v ON v.name = 'Dr. Siya Thomas'
WHERE p.name = 'Kiwi'
  AND NOT EXISTS (
      SELECT 1 FROM medical_record m
      WHERE m.pet_id = p.pet_id AND m.visit_date = '2026-03-05'
  );

INSERT INTO medical_record (pet_id, vet_id, visit_date, diagnosis, treatment, additional_notes)
SELECT p.pet_id, v.vet_id, '2026-02-18', 'Dental overgrowth risk', 'Diet adjustment and chew enrichment', 'Monitor monthly.'
FROM pet p
JOIN vet v ON v.name = 'Dr. Arjun Nair'
WHERE p.name = 'Hazel'
  AND NOT EXISTS (
      SELECT 1 FROM medical_record m
      WHERE m.pet_id = p.pet_id AND m.visit_date = '2026-02-18'
  );

-- ------------------------------------------------------------
-- Seed vaccinations
-- ------------------------------------------------------------
INSERT INTO vaccination (pet_id, vaccine_name, vaccination_date, next_due_date, status)
SELECT p.pet_id, 'Rabies', '2026-01-15', '2027-01-15', 'Completed'
FROM pet p
WHERE p.name = 'Buddy'
  AND NOT EXISTS (
      SELECT 1 FROM vaccination v
      WHERE v.pet_id = p.pet_id AND v.vaccine_name = 'Rabies' AND v.vaccination_date = '2026-01-15'
  );

INSERT INTO vaccination (pet_id, vaccine_name, vaccination_date, next_due_date, status)
SELECT p.pet_id, 'FVRCP', '2026-02-02', '2027-02-02', 'Completed'
FROM pet p
WHERE p.name = 'Luna'
  AND NOT EXISTS (
      SELECT 1 FROM vaccination v
      WHERE v.pet_id = p.pet_id AND v.vaccine_name = 'FVRCP' AND v.vaccination_date = '2026-02-02'
  );

INSERT INTO vaccination (pet_id, vaccine_name, vaccination_date, next_due_date, status)
SELECT p.pet_id, 'Bordetella', '2026-02-26', '2026-08-26', 'Completed'
FROM pet p
WHERE p.name = 'Atlas'
  AND NOT EXISTS (
      SELECT 1 FROM vaccination v
      WHERE v.pet_id = p.pet_id AND v.vaccine_name = 'Bordetella' AND v.vaccination_date = '2026-02-26'
  );

INSERT INTO vaccination (pet_id, vaccine_name, vaccination_date, next_due_date, status)
SELECT p.pet_id, 'Avian Polyomavirus', '2026-03-08', '2027-03-08', 'Completed'
FROM pet p
WHERE p.name = 'Sunny'
  AND NOT EXISTS (
      SELECT 1 FROM vaccination v
      WHERE v.pet_id = p.pet_id AND v.vaccine_name = 'Avian Polyomavirus' AND v.vaccination_date = '2026-03-08'
  );

INSERT INTO vaccination (pet_id, vaccine_name, vaccination_date, next_due_date, status)
SELECT p.pet_id, 'RHDV2', '2026-01-30', '2027-01-30', 'Completed'
FROM pet p
WHERE p.name = 'Hazel'
  AND NOT EXISTS (
      SELECT 1 FROM vaccination v
      WHERE v.pet_id = p.pet_id AND v.vaccine_name = 'RHDV2' AND v.vaccination_date = '2026-01-30'
  );

-- ------------------------------------------------------------
-- Seed adoption history (completed + pending + rejected)
-- ------------------------------------------------------------
INSERT INTO adoption (adopter_id, pet_id, adoption_date, status)
SELECT a.adopter_id, p.pet_id, '2026-02-20', 'Completed'
FROM adopter a
JOIN users u ON u.user_id = a.user_id
JOIN pet p ON p.name = 'Mochi'
WHERE u.username = 'seed_adopter_nisha'
  AND NOT EXISTS (
      SELECT 1 FROM adoption ad
      WHERE ad.adopter_id = a.adopter_id AND ad.pet_id = p.pet_id
  );

INSERT INTO adoption (adopter_id, pet_id, adoption_date, status)
SELECT a.adopter_id, p.pet_id, '2026-03-02', 'Completed'
FROM adopter a
JOIN users u ON u.user_id = a.user_id
JOIN pet p ON p.name = 'Hazel'
WHERE u.username = 'seed_adopter_rahul'
  AND NOT EXISTS (
      SELECT 1 FROM adoption ad
      WHERE ad.adopter_id = a.adopter_id AND ad.pet_id = p.pet_id
  );

INSERT INTO adoption (adopter_id, pet_id, adoption_date, status)
SELECT a.adopter_id, p.pet_id, '2026-03-18', 'Pending'
FROM adopter a
JOIN users u ON u.user_id = a.user_id
JOIN pet p ON p.name = 'Clover'
WHERE u.username = 'seed_adopter_anna'
  AND NOT EXISTS (
      SELECT 1 FROM adoption ad
      WHERE ad.adopter_id = a.adopter_id AND ad.pet_id = p.pet_id
  );

INSERT INTO adoption (adopter_id, pet_id, adoption_date, status)
SELECT a.adopter_id, p.pet_id, '2026-03-25', 'Rejected'
FROM adopter a
JOIN users u ON u.user_id = a.user_id
JOIN pet p ON p.name = 'Max'
WHERE u.username = 'seed_adopter_isha'
  AND NOT EXISTS (
      SELECT 1 FROM adoption ad
      WHERE ad.adopter_id = a.adopter_id AND ad.pet_id = p.pet_id
  );

-- Keep pet adoption_status in sync for seeded records.
UPDATE pet SET adoption_status = 'Adopted' WHERE name IN ('Mochi', 'Hazel');
UPDATE pet SET adoption_status = 'Pending' WHERE name IN ('Clover');
UPDATE pet SET adoption_status = 'Available' WHERE name IN ('Max');
