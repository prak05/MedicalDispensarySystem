package com.mds.model;

import java.util.Date;

public class PrescriptionItem {
    private int prescriptionId;
    private int medicineId;
    private String medicineName;
    private int quantity;
    private String dosage;
    private String instructions;
    private Date dateDispensed;
    private String status;

    public PrescriptionItem() {}

    public PrescriptionItem(int prescriptionId, int medicineId, int quantity, String dosage, String instructions) {
        this.prescriptionId = prescriptionId;
        this.medicineId = medicineId;
        this.quantity = quantity;
        this.dosage = dosage;
        this.instructions = instructions;
        this.status = "PENDING";
    }

    // Getters and Setters
    public int getPrescriptionId() { return prescriptionId; }
    public void setPrescriptionId(int prescriptionId) { this.prescriptionId = prescriptionId; }
    public int getMedicineId() { return medicineId; }
    public void setMedicineId(int medicineId) { this.medicineId = medicineId; }
    public String getMedicineName() { return medicineName; }
    public void setMedicineName(String medicineName) { this.medicineName = medicineName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    public Date getDateDispensed() { return dateDispensed; }
    public void setDateDispensed(Date dateDispensed) { this.dateDispensed = dateDispensed; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
