package com.mds.service;

import com.mds.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionManager {
    private Connection getConnection() {
        return DatabaseManager.getInstance().getConnection();
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM patients ORDER BY name";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Patient patient = new Patient();
                patient.setPatientId(rs.getInt("patient_id"));
                patient.setUserId(rs.getInt("user_id"));
                patient.setName(rs.getString("name"));
                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public List<Medicine> getAllMedicines() {
        List<Medicine> medicines = new ArrayList<>();
        String query = "SELECT * FROM medicines WHERE stock_quantity > 0 ORDER BY name";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Medicine medicine = new Medicine();
                medicine.setMedicineId(rs.getInt("medicine_id"));
                medicine.setName(rs.getString("name"));
                medicine.setStockQuantity(rs.getInt("stock_quantity"));
                medicines.add(medicine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicines;
    }

    public boolean createPrescription(Prescription prescription, List<PrescriptionItem> items) {
        Connection conn = getConnection();
        try {
            conn.setAutoCommit(false);
            String prescQuery = "INSERT INTO prescriptions (doctor_id, patient_id, notes) VALUES (?, ?, ?)";
            try (PreparedStatement prescStmt = conn.prepareStatement(prescQuery, Statement.RETURN_GENERATED_KEYS)) {
                prescStmt.setInt(1, prescription.getDoctorId());
                prescStmt.setInt(2, prescription.getPatientId());
                prescStmt.setString(3, prescription.getNotes());
                prescStmt.executeUpdate();

                try (ResultSet generatedKeys = prescStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int prescriptionId = generatedKeys.getInt(1);
                        String itemQuery = "INSERT INTO prescription_items (prescription_id, medicine_id, quantity, dosage, instructions) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement itemStmt = conn.prepareStatement(itemQuery)) {
                            for (PrescriptionItem item : items) {
                                itemStmt.setInt(1, prescriptionId);
                                itemStmt.setInt(2, item.getMedicineId());
                                itemStmt.setInt(3, item.getQuantity());
                                itemStmt.setString(4, item.getDosage());
                                itemStmt.setString(5, item.getInstructions());
                                itemStmt.addBatch();
                            }
                            itemStmt.executeBatch();
                        }
                    }
                }
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Prescription> getPendingPrescriptions() {
        List<Prescription> prescriptions = new ArrayList<>();
        String query = "SELECT p.*, u.name as doctor_name, pt.name as patient_name " +
                       "FROM prescriptions p " +
                       "JOIN users u ON p.doctor_id = u.user_id " +
                       "JOIN patients pt ON p.patient_id = pt.patient_id " +
                       "WHERE p.status IN ('PENDING', 'PARTIALLY_DISPENSED') " +
                       "ORDER BY p.prescription_date DESC";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Prescription p = new Prescription();
                p.setPrescriptionId(rs.getInt("prescription_id"));
                p.setPatientName(rs.getString("patient_name"));
                p.setDoctorName(rs.getString("doctor_name"));
                p.setPrescriptionDate(rs.getTimestamp("prescription_date"));
                p.setStatus(rs.getString("status"));
                prescriptions.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prescriptions;
    }

    public boolean dispenseMedicine(int prescriptionId) {
        Connection conn = getConnection();
        String updateItems = "UPDATE prescription_items SET status = 'DISPENSED', date_dispensed = NOW() WHERE prescription_id = ?";
        String updatePrescription = "UPDATE prescriptions SET status = 'DISPENSED' WHERE prescription_id = ?";
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt1 = conn.prepareStatement(updateItems);
                 PreparedStatement stmt2 = conn.prepareStatement(updatePrescription)) {
                stmt1.setInt(1, prescriptionId);
                stmt1.executeUpdate();
                stmt2.setInt(1, prescriptionId);
                stmt2.executeUpdate();
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Prescription> getPatientPrescriptions(int patientId) {
        List<Prescription> prescriptions = new ArrayList<>();
        String query = "SELECT p.*, u.name as doctor_name FROM prescriptions p " +
                       "JOIN users u ON p.doctor_id = u.user_id " +
                       "WHERE p.patient_id = ? ORDER BY p.prescription_date DESC";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, patientId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Prescription p = new Prescription();
                    p.setPrescriptionId(rs.getInt("prescription_id"));
                    p.setDoctorName(rs.getString("doctor_name"));
                    p.setPrescriptionDate(rs.getTimestamp("prescription_date"));
                    p.setStatus(rs.getString("status"));
                    p.setNotes(rs.getString("notes"));
                    prescriptions.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prescriptions;
    }

    public List<PrescriptionItem> getPrescriptionItems(int prescriptionId) {
        List<PrescriptionItem> items = new ArrayList<>();
        String query = "SELECT pi.*, m.name as medicine_name FROM prescription_items pi " +
                       "JOIN medicines m ON pi.medicine_id = m.medicine_id " +
                       "WHERE pi.prescription_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, prescriptionId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PrescriptionItem item = new PrescriptionItem();
                    item.setMedicineName(rs.getString("medicine_name"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setDosage(rs.getString("dosage"));
                    item.setInstructions(rs.getString("instructions"));
                    item.setDateDispensed(rs.getTimestamp("date_dispensed"));
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public int getPatientIdByUserId(int userId) {
        String query = "SELECT patient_id FROM patients WHERE user_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("patient_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
