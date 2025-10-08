package com.mds.view.patient;

import com.mds.model.Prescription;
import com.mds.model.PrescriptionItem;
import com.mds.model.User;
import com.mds.service.PrescriptionManager;
import com.mds.view.BaseDashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class PatientDashboard extends BaseDashboard {
    private int patientId;
    private PrescriptionManager prescriptionManager;
    private DefaultTableModel prescriptionTableModel, itemsTableModel;

    public PatientDashboard(User patientUser, int patientId) {
        super(patientUser, "Patient Dashboard");
        this.patientId = patientId;
        this.prescriptionManager = new PrescriptionManager();
        loadPrescriptions();
    }

    @Override
    protected void initializeComponents() {
        centerPanel.setLayout(new BorderLayout());

        // Tables
        prescriptionTableModel = new DefaultTableModel(new String[]{"ID", "Doctor", "Date", "Status"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable prescriptionTable = new JTable(prescriptionTableModel);

        itemsTableModel = new DefaultTableModel(new String[]{"Medicine", "Qty", "Dosage", "Instructions"}, 0);
        JTable itemsTable = new JTable(itemsTableModel);

        // Selection Listener
        prescriptionTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && prescriptionTable.getSelectedRow() != -1) {
                int id = (int) prescriptionTableModel.getValueAt(prescriptionTable.getSelectedRow(), 0);
                loadPrescriptionItems(id);
            }
        });

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(prescriptionTable), new JScrollPane(itemsTable));
        splitPane.setResizeWeight(0.5);
        centerPanel.add(splitPane, BorderLayout.CENTER);
    }

    private void loadPrescriptions() {
        prescriptionTableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Prescription> prescriptions = prescriptionManager.getPatientPrescriptions(patientId);
        for (Prescription p : prescriptions) {
            prescriptionTableModel.addRow(new Object[]{p.getPrescriptionId(), p.getDoctorName(), sdf.format(p.getPrescriptionDate()), p.getStatus()});
        }
    }

    private void loadPrescriptionItems(int prescriptionId) {
        itemsTableModel.setRowCount(0);
        List<PrescriptionItem> items = prescriptionManager.getPrescriptionItems(prescriptionId);
        for (PrescriptionItem item : items) {
            itemsTableModel.addRow(new Object[]{item.getMedicineName(), item.getQuantity(), item.getDosage(), item.getInstructions()});
        }
    }
}
