package com.mds.view.doctor;

import com.mds.model.*;
import com.mds.service.PrescriptionManager;
import com.mds.view.BaseDashboard;
import com.mds.view.Theme;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDashboard extends BaseDashboard {
    private PrescriptionManager prescriptionManager;
    private JComboBox<Patient> patientComboBox;
    private JComboBox<String> medicineComboBox;
    private JTextField quantityField, dosageField;
    private JTextArea instructionsArea, notesArea;
    private DefaultTableModel tableModel;
    private List<Medicine> availableMedicines;
    private List<PrescriptionItem> currentItems = new ArrayList<>();

    public DoctorDashboard(User doctor) {
        super(doctor, "Doctor Dashboard");
        this.prescriptionManager = new PrescriptionManager();
        loadInitialData();
    }

    @Override
    protected void initializeComponents() {
        centerPanel.setLayout(new BorderLayout(10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(null, "Prescription Details", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, Theme.HEADER_FONT, Theme.PRIMARY_COLOR));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Select Patient:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        patientComboBox = new JComboBox<>();
        formPanel.add(patientComboBox, gbc);

        gbc.gridy = 1; gbc.gridx = 0; gbc.gridwidth = 1;
        formPanel.add(new JLabel("Select Medicine:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        medicineComboBox = new JComboBox<>();
        formPanel.add(medicineComboBox, gbc);

        gbc.gridy = 2; gbc.gridx = 0; gbc.gridwidth = 1;
        formPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        quantityField = new JTextField(5);
        formPanel.add(quantityField, gbc);
        gbc.gridx = 2;
        formPanel.add(new JLabel("Dosage:"), gbc);
        gbc.gridx = 3;
        dosageField = new JTextField(15);
        formPanel.add(dosageField, gbc);

        gbc.gridy = 3; gbc.gridx = 0;
        formPanel.add(new JLabel("Instructions:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        instructionsArea = new JTextArea(3, 20);
        formPanel.add(new JScrollPane(instructionsArea), gbc);

        gbc.gridy = 4; gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
        JButton addMedicineButton = new JButton("Add Medicine to Prescription");
        addMedicineButton.addActionListener(e -> addMedicineItem());
        formPanel.add(addMedicineButton, gbc);

        // Table
        tableModel = new DefaultTableModel(new String[]{"Medicine", "Qty", "Dosage"}, 0);
        JTable prescriptionItemsTable = new JTable(tableModel);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        notesArea = new JTextArea(4, 20);
        JButton submitButton = new JButton("Submit Prescription");
        submitButton.addActionListener(e -> submitPrescription());
        bottomPanel.add(new JScrollPane(notesArea), BorderLayout.CENTER);
        bottomPanel.add(submitButton, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formPanel, new JScrollPane(prescriptionItemsTable));
        splitPane.setResizeWeight(0.5);
        centerPanel.add(splitPane, BorderLayout.CENTER);
        centerPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadInitialData() {
        prescriptionManager.getAllPatients().forEach(patientComboBox::addItem);
        availableMedicines = prescriptionManager.getAllMedicines();
        availableMedicines.forEach(m -> medicineComboBox.addItem(m.getName() + " (Stock: " + m.getStockQuantity() + ")"));
    }

    private void addMedicineItem() {
        int selectedIndex = medicineComboBox.getSelectedIndex();
        if (selectedIndex < 0) return;
        Medicine selectedMedicine = availableMedicines.get(selectedIndex);
        try {
            int quantity = Integer.parseInt(quantityField.getText().trim());
            String dosage = dosageField.getText().trim();
            if (quantity <= 0 || dosage.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Quantity and Dosage are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            tableModel.addRow(new Object[]{selectedMedicine.getName(), quantity, dosage});
            currentItems.add(new PrescriptionItem(0, selectedMedicine.getMedicineId(), quantity, dosage, instructionsArea.getText().trim()));
            quantityField.setText("");
            dosageField.setText("");
            instructionsArea.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void submitPrescription() {
        Patient selectedPatient = (Patient) patientComboBox.getSelectedItem();
        if (selectedPatient == null || currentItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a patient and add medicines.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean success = prescriptionManager.createPrescription(new Prescription(currentUser.getUserId(), selectedPatient.getPatientId(), notesArea.getText().trim()), currentItems);
        if (success) {
            JOptionPane.showMessageDialog(this, "Prescription created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            tableModel.setRowCount(0);
            currentItems.clear();
            notesArea.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create prescription.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
