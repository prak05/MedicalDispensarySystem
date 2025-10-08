-- Medical Dispensary System Database Schema (MySQL)

-- Create database
CREATE DATABASE IF NOT EXISTS medical_dispensary;
USE medical_dispensary;

-- Drop tables if they exist (for clean setup)
DROP TABLE IF EXISTS prescription_items;
DROP TABLE IF EXISTS prescriptions;
DROP TABLE IF EXISTS medicines;
DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS users;

-- Create Users table for authentication and role management
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(200) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    mobile VARCHAR(20),
    role VARCHAR(20) NOT NULL CHECK (role IN ('doctor', 'patient', 'dispensary')),
    status VARCHAR(20) DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'INACTIVE')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Patients table for patient information
CREATE TABLE patients (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    name VARCHAR(100) NOT NULL,
    age INT,
    gender VARCHAR(10) CHECK (gender IN ('Male', 'Female', 'Other')),
    contact_number VARCHAR(20),
    address VARCHAR(300),
    medical_history TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create Medicines table for medicine inventory
CREATE TABLE medicines (
    medicine_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(300),
    manufacturer VARCHAR(100),
    unit_price DECIMAL(10,2) NOT NULL,
    stock_quantity INT DEFAULT 0,
    expiry_date DATE,
    medicine_type VARCHAR(50) DEFAULT 'Tablet',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Prescriptions table for doctor prescriptions
CREATE TABLE prescriptions (
    prescription_id INT AUTO_INCREMENT PRIMARY KEY,
    doctor_id INT,
    patient_id INT,
    prescription_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'PARTIALLY_DISPENSED', 'DISPENSED', 'CANCELLED')),
    notes TEXT,
    FOREIGN KEY (doctor_id) REFERENCES users(user_id),
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id)
);

-- Create Prescription Items table for individual medicines in prescriptions
CREATE TABLE prescription_items (
    prescription_id INT,
    medicine_id INT,
    quantity INT NOT NULL,
    dosage VARCHAR(100),
    instructions VARCHAR(300),
    date_dispensed TIMESTAMP NULL,
    status VARCHAR(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'DISPENSED')),
    PRIMARY KEY (prescription_id, medicine_id),
    FOREIGN KEY (prescription_id) REFERENCES prescriptions(prescription_id) ON DELETE CASCADE,
    FOREIGN KEY (medicine_id) REFERENCES medicines(medicine_id)
);

-- Insert sample users
INSERT INTO users (username, password, name, email, mobile, role) VALUES
('dr_smith', 'password123', 'Dr. John Smith', 'dr.smith@hospital.com', '9876543210', 'doctor'),
('dr_jones', 'password123', 'Dr. Sarah Jones', 'dr.jones@hospital.com', '9876543211', 'doctor'),
('patient_john', 'password123', 'John Doe', 'john.doe@email.com', '9876543212', 'patient'),
('patient_mary', 'password123', 'Mary Johnson', 'mary.j@email.com', '9876543213', 'patient'),
('patient_robert', 'password123', 'Robert Brown', 'robert.b@email.com', '9876543214', 'patient'),
('pharmacy_admin', 'password123', 'Pharmacy Admin', 'admin@pharmacy.com', '9876543215', 'dispensary');

-- Insert sample patients
INSERT INTO patients (user_id, name, age, gender, contact_number, address, medical_history) VALUES
(3, 'John Doe', 35, 'Male', '9876543212', '123 Main Street, City', 'No major medical history'),
(4, 'Mary Johnson', 42, 'Female', '9876543213', '456 Oak Avenue, Town', 'Diabetes Type 2, Hypertension'),
(5, 'Robert Brown', 28, 'Male', '9876543214', '789 Pine Road, Village', 'Asthma, Allergies to Penicillin');

-- Insert sample medicines
INSERT INTO medicines (name, description, manufacturer, unit_price, stock_quantity, expiry_date, medicine_type) VALUES
('Paracetamol 500mg', 'Pain reliever and fever reducer', 'ABC Pharma', 5.00, 500, '2026-12-31', 'Tablet'),
('Amoxicillin 250mg', 'Antibiotic for bacterial infections', 'XYZ Pharma', 12.50, 300, '2026-06-30', 'Capsule'),
('Ibuprofen 400mg', 'Anti-inflammatory pain reliever', 'MED Corp', 8.00, 400, '2026-10-15', 'Tablet'),
('Cough Syrup', 'For dry and wet cough', 'HealthMed', 15.75, 150, '2026-09-15', 'Syrup'),
('Cetirizine 10mg', 'Antihistamine for allergies', 'AllerCare', 6.50, 350, '2027-01-20', 'Tablet'),
('Metformin 500mg', 'Diabetes medication', 'DiabCare', 18.00, 250, '2026-11-30', 'Tablet'),
('Amlodipine 5mg', 'Blood pressure medication', 'CardioMed', 22.00, 200, '2026-08-25', 'Tablet');

-- Insert sample prescriptions
INSERT INTO prescriptions (doctor_id, patient_id, status, notes) VALUES
(1, 1, 'PENDING', 'Patient has fever and body ache. Rest for 2-3 days.'),
(1, 2, 'DISPENSED', 'Regular diabetes check-up. Continue current medication.'),
(2, 3, 'PENDING', 'Allergic reaction - avoid outdoor activities for a week.');

-- Insert sample prescription items
INSERT INTO prescription_items (prescription_id, medicine_id, quantity, dosage, instructions, status) VALUES
(1, 1, 10, '1 tablet twice daily', 'Take after meals with water', 'PENDING'),
(1, 3, 6, '1 tablet when needed', 'Take if pain persists, max 3 per day', 'PENDING'),
(2, 6, 60, '1 tablet twice daily', 'Take before breakfast and dinner', 'DISPENSED'),
(2, 7, 30, '1 tablet once daily', 'Take in the morning', 'DISPENSED'),
(3, 5, 14, '1 tablet once daily', 'Take before bedtime', 'PENDING'),
(3, 4, 1, '10ml three times daily', 'Shake well before use', 'PENDING');

COMMIT;
