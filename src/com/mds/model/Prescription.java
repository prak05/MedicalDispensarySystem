package com.mds.model;

import java.util.Date;

public class Prescription {
    private int prescriptionId;
    private int doctorId;
    private int patientId;
    private Date prescriptionDate;
    private String status;
    private String notes;
    private String doctorName;
    private String patientName;

    public Prescription() {}

    public Prescription(int doctorId, int patientId, String notes) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.prescriptionDate = new Date();
        this.status = "PENDING";
        this.notes = notes;
    }

    public int getPrescriptionId() { return prescriptionId; }
    public void setPrescriptionId(int prescriptionId) { this.prescriptionId = prescriptionId; }
    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public Date getPrescriptionDate() { return prescriptionDate; }
    public void setPrescriptionDate(Date prescriptionDate) { this.prescriptionDate = prescriptionDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
}
