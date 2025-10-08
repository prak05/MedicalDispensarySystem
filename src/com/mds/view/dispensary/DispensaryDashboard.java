package com.mds.view.dispensary;

import com.mds.model.Prescription;
import com.mds.model.User;
import com.mds.service.PrescriptionManager;
import com.mds.view.BaseDashboard;
import com.mds.view.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class DispensaryDashboard extends BaseDashboard {
    private PrescriptionManager prescriptionManager;
    private DefaultTableModel tableModel;

    public DispensaryDashboard(User dispensaryUser) {
        super(dispensaryUser, "Dispensary Dashboard");
        this.prescriptionManager = new PrescriptionManager();
        loadPendingPrescriptions();
    }

    @Override
    protected void initializeComponents() {
        centerPanel.setLayout(new BorderLayout(10, 10));

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Patient", "Doctor", "Date", "Status"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable pendingPrescriptionsTable = new JTable(tableModel);
        centerPanel.add(new JScrollPane(pendingPrescriptionsTable), BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadPendingPrescriptions());
        JButton dispenseButton = new JButton("Mark as Dispensed");
        dispenseButton.setBackground(Theme.SUCCESS_COLOR);
        dispenseButton.setForeground(Color.WHITE);
        dispenseButton.addActionListener(e -> dispenseSelected(pendingPrescriptionsTable));
        buttonPanel.add(refreshButton);
        buttonPanel.add(dispenseButton);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadPendingPrescriptions() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<Prescription> prescriptions = prescriptionManager.getPendingPrescriptions();
        for (Prescription p : prescriptions) {
            tableModel.addRow(new Object[]{p.getPrescriptionId(), p.getPatientName(), p.getDoctorName(), sdf.format(p.getPrescriptionDate()), p.getStatus()});
        }
    }

    private void dispenseSelected(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a prescription.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Dispense prescription ID: " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = prescriptionManager.dispenseMedicine(id);
            if (success) {
                JOptionPane.showMessageDialog(this, "Prescription dispensed.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadPendingPrescriptions();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to dispense.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
