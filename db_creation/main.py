import mysql.connector
# ---------- CONNECT TO MYSQL ----------
conn = mysql.connector.connect(
    host="localhost",
    user="root",
    password="123Qwerty"
)
cursor = conn.cursor()

# ---------- CREATE DATABASE ----------
cursor.execute("CREATE DATABASE IF NOT EXISTS PetAdoptionDB")
cursor.execute("USE PetAdoptionDB")

# ---------- CREATE TABLES ----------
cursor.execute("""
CREATE TABLE IF NOT EXISTS Shelter (
    shelter_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    capacity INT,
    contact_no VARCHAR(15)
)
""")

cursor.execute("""
CREATE TABLE IF NOT EXISTS Pet (
    pet_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    species VARCHAR(50),
    breed VARCHAR(50),
    age INT,
    gender VARCHAR(10),
    health_status VARCHAR(100),
    adoption_status VARCHAR(20) DEFAULT 'Available',
    shelter_id INT,
    FOREIGN KEY (shelter_id) REFERENCES Shelter(shelter_id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
)
""")

cursor.execute("""
CREATE TABLE IF NOT EXISTS Adopter (
    adopter_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    address VARCHAR(200),
    experience_level VARCHAR(50)
)
""")

cursor.execute("""
CREATE TABLE IF NOT EXISTS Adoption (
    adoption_id INT AUTO_INCREMENT PRIMARY KEY,
    pet_id INT NOT NULL,
    adopter_id INT NOT NULL,
    adoption_date DATE,
    status VARCHAR(50) DEFAULT 'Pending',
    FOREIGN KEY (pet_id) REFERENCES Pet(pet_id)
               ON DELETE CASCADE
               ON UPDATE CASCADE,
    FOREIGN KEY (adopter_id) REFERENCES Adopter(adopter_id)
               ON DELETE CASCADE
               ON UPDATE CASCADE
)
""")

cursor.execute("""
CREATE TABLE IF NOT EXISTS Vet (
    vet_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    clinic_name VARCHAR(100),
    phone VARCHAR(15),
    specialization VARCHAR(100)
)
""")

cursor.execute("""
CREATE TABLE IF NOT EXISTS Medical_Record (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    pet_id INT NOT NULL,
    vet_id INT NOT NULL,
    visit_date DATE,
    diagnosis VARCHAR(200),
    treatment VARCHAR(200),
    FOREIGN KEY (pet_id) REFERENCES Pet(pet_id)
               ON DELETE CASCADE
               ON UPDATE CASCADE,
    FOREIGN KEY (vet_id) REFERENCES Vet(vet_id)
               ON DELETE CASCADE
               ON UPDATE CASCADE
)
""")

cursor.execute("""
CREATE TABLE IF NOT EXISTS Vaccination (
    vaccination_id INT AUTO_INCREMENT PRIMARY KEY,
    pet_id INT NOT NULL,
    vaccine_name VARCHAR(100),
    vaccination_date DATE,
    next_due_date DATE,
    FOREIGN KEY (pet_id) REFERENCES Pet(pet_id)
               ON DELETE CASCADE
               ON UPDATE CASCADE
)
""")

cursor.execute("""
CREATE TABLE IF NOT EXISTS Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('Admin', 'Staff', 'Adopter') NOT NULL,
    adopter_id INT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (adopter_id) REFERENCES Adopter(adopter_id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
)
""")
print(f"ALL tables created")

cursor.execute("DROP TRIGGER IF EXISTS after_adoption_insert")
cursor.execute("""
CREATE TRIGGER after_adoption_insert
AFTER INSERT ON Adoption
FOR EACH ROW
BEGIN
    UPDATE Pet
    SET adoption_status = 'Adopted'
    WHERE pet_id = NEW.pet_id;
END
""")

cursor.execute("DROP TRIGGER IF EXISTS after_adoption_delete")
cursor.execute("""
CREATE TRIGGER after_adoption_delete
AFTER DELETE ON Adoption
FOR EACH ROW
BEGIN
    UPDATE Pet
    SET adoption_status = 'Available'
    WHERE pet_id = OLD.pet_id;
END
""")

cursor.execute("DROP PROCEDURE IF EXISTS GetAdopterProfile")
cursor.execute("""
CREATE PROCEDURE GetAdopterProfile(IN p_adopter_id INT)
BEGIN
    -- 1) Adopter profile
    SELECT 
        a.adopter_id,
        a.name         AS adopter_name,
        a.phone,
        a.address,
        a.experience_level
    FROM Adopter a
    WHERE a.adopter_id = p_adopter_id;

    -- 2) Pets adopted by this adopter
    SELECT
        p.pet_id,
        p.name         AS pet_name,
        p.species,
        p.breed,
        p.age,
        p.gender,
        p.health_status,
        p.adoption_status,
        ad.adoption_date,
        ad.status      AS adoption_status_record
    FROM Adoption ad
    JOIN Pet p ON ad.pet_id = p.pet_id
    WHERE ad.adopter_id = p_adopter_id;

    -- 3) Vaccinations for those pets
    SELECT
        v.vaccination_id,
        v.pet_id,
        v.vaccine_name,
        v.vaccination_date,
        v.next_due_date
    FROM Vaccination v
    JOIN Adoption ad2 ON v.pet_id = ad2.pet_id
    WHERE ad2.adopter_id = p_adopter_id;
END 
""")


cursor.execute("DROP FUNCTION IF EXISTS IsAdoptionAllowed")
cursor.execute("""
CREATE FUNCTION IsAdoptionAllowed(p_adopter_id INT)
RETURNS BOOLEAN
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE count_pets INT;
    SELECT COUNT(*) INTO count_pets
    FROM Adoption
    WHERE adopter_id = p_adopter_id AND status = 'Completed';
    RETURN count_pets < 3;
END
""")

cursor.execute("DROP FUNCTION IF EXISTS IsPetAvailable")
cursor.execute(""" 
CREATE FUNCTION IsPetAvailable(p_pet_id INT)
RETURNS BOOLEAN
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE pet_status VARCHAR(20);
    SELECT adoption_status INTO pet_status
    FROM Pet
    WHERE pet_id = p_pet_id;
    RETURN pet_status = 'Available';
END
""")

cursor.execute("DROP TRIGGER IF EXISTS before_adoption_insert ")
cursor.execute("""
CREATE TRIGGER before_adoption_insert
BEFORE INSERT ON Adoption
FOR EACH ROW
BEGIN
    IF NOT IsAdoptionAllowed(NEW.adopter_id) THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Adopter cannot adopt more than 3 pets.';
    END IF;

    IF NOT IsPetAvailable(NEW.pet_id) THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Pet is already adopted or unavailable.';
    END IF;

    UPDATE Pet
    SET adoption_status = 'Adopted'
    WHERE pet_id = NEW.pet_id;
END 
""")


cursor.execute("DROP FUNCTION IF EXISTS ValidateLogin")
cursor.execute("""CREATE FUNCTION ValidateLogin(p_username VARCHAR(50), p_password VARCHAR(255))
RETURNS VARCHAR(20)
DETERMINISTIC
BEGIN
    DECLARE user_role VARCHAR(20);

    SELECT role INTO user_role
    FROM Users
    WHERE username = p_username AND password = p_password;

    RETURN user_role;
END""")
print(f"All functions,procedures and triggers created ")

cursor.execute("DROP PROCEDURE IF EXISTS RecordVaccination")
cursor.execute("""
CREATE PROCEDURE RecordVaccination(
    IN p_pet_id INT,
    IN p_vaccine_name VARCHAR(100),
    IN p_vaccination_date DATE,
    IN p_next_due_date DATE
)
BEGIN
    IF p_next_due_date <= p_vaccination_date THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Next due date must be after vaccination date.';
    END IF;

    IF EXISTS (
        SELECT 1 FROM Vaccination
        WHERE pet_id = p_pet_id AND vaccine_name = p_vaccine_name
          AND vaccination_date = p_vaccination_date
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Duplicate vaccination record.';
    END IF;

    INSERT INTO Vaccination (pet_id, vaccine_name, vaccination_date, next_due_date)
    VALUES (p_pet_id, p_vaccine_name, p_vaccination_date, p_next_due_date);
END"""
)



# ---------- INSERT DATA ----------
def insert_if_empty(table, insert_sql, values):
    cursor.execute(f"SELECT COUNT(*) FROM {table}")
    count = cursor.fetchone()[0]
    if count == 0:
        cursor.executemany(insert_sql, values)
        print(f"✅ Inserted sample data into {table}")
    else:
        print(f"⚡ {table} already has data — skipped insert.")

# Shelter
insert_if_empty("Shelter",
    "INSERT INTO Shelter (name, capacity, contact_no) VALUES (%s, %s, %s)",
    [
        ('Downtown', 50, '9876543210'),
        ('North City', 40, '9123456780'),
        ('West End', 60, '9988776655')
    ]
)

# Pet
insert_if_empty("Pet",
    """INSERT INTO Pet 
       (name, species, breed, age, gender, health_status, adoption_status, shelter_id)
       VALUES (%s,%s,%s,%s,%s,%s,%s,%s)""",
    [
        ('Buddy', 'Dog', 'Labrador', 3, 'Male', 'Healthy', 'Adopted', 1),
        ('Misty', 'Cat', 'Persian', 2, 'Female', 'Vaccinated', 'Adopted', 2),
        ('Rocky', 'Dog', 'Bulldog', 4, 'Male', 'Minor injury', 'Available', 1),
        ('Luna', 'Cat', 'Siamese', 1, 'Female', 'Healthy', 'Available', 3),
        ('Max', 'Dog', 'Beagle', 2, 'Male', 'Healthy', 'Adopted', 1)
    ]
)

# Adopter
insert_if_empty("Adopter",
    "INSERT INTO Adopter (name, phone, address, experience_level) VALUES (%s,%s,%s,%s)",
    [
        ('Alice Johnson', '9001112233', '123 Main St', 'Experienced'),
        ('Bob Smith', '9112223344', '45 Elm Ave', 'Beginner'),
        ('Carol Davis', '9123456678', '78 Pine St', 'Intermediate')
    ]
)

# Adoption
insert_if_empty("Adoption",
    "INSERT INTO Adoption (pet_id, adopter_id, adoption_date, status) VALUES (%s,%s,%s,%s)",
    [
        (2, 1, '2025-02-10', 'Completed'),
        (5, 3, '2025-03-15', 'Completed'),
        (1, 2, '2025-07-15', 'Completed')

    ]
)

# Vet
insert_if_empty("Vet",
    "INSERT INTO Vet (name, clinic_name, phone, specialization) VALUES (%s,%s,%s,%s)",
    [
        ('Dr. Patel', 'Happy Paws Clinic', '9098765432', 'Surgery'),
        ('Dr. Mehta', 'PetCare Clinic', '9123456789', 'Dermatology'),
        ('Dr. Rao', 'City Animal Hospital', '9876501234', 'General Checkup')
    ]
)

# Medical Record
insert_if_empty("Medical_Record",
    "INSERT INTO Medical_Record (pet_id, vet_id, visit_date, diagnosis, treatment) VALUES (%s,%s,%s,%s,%s)",
    [
        (3, 1, '2025-03-12', 'Skin Allergy', 'Medication'),
        (1, 2, '2025-04-05', 'Regular Checkup', 'None'),
        (4, 3, '2025-05-22', 'Vaccination', 'None')
    ]
)

# Vaccination
insert_if_empty("Vaccination",
    "INSERT INTO Vaccination (pet_id, vaccine_name, vaccination_date, next_due_date) VALUES (%s,%s,%s,%s)",
    [
        (1, 'Rabies', '2025-02-01', '2026-02-01'),
        (2, 'Feline Distemper', '2025-01-15', '2026-01-15'),
        (3, 'Parvovirus', '2025-03-10', '2026-03-10')
    ]
)

# Users
insert_if_empty("Users",
    "INSERT INTO Users (username, password, role, adopter_id) VALUES (%s, %s, %s, %s)",
    [
        ('admin', '12345', 'Admin', None),
        ('staff1', '12345', 'Staff', None),
        ('alice_user', 'alice123', 'Adopter', 1),
        ('bob_user', 'bob123', 'Adopter', 2),
        ('carol_user', 'carol123', 'Adopter', 3)
    ]
)

# ---------- SAVE AND CLOSE ----------
conn.commit()
cursor.close()
conn.close()

