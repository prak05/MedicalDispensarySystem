package com.mds.view;

import com.mds.model.User;
import com.mds.service.AuthenticationService;
import com.mds.service.PrescriptionManager;
import com.mds.view.dispensary.DispensaryDashboard;
import com.mds.view.doctor.DoctorDashboard;
import com.mds.view.patient.PatientDashboard;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {
    private String expectedRole;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginUI(String role) {
        this.expectedRole = role;
        String roleTitle = role.substring(0, 1).toUpperCase() + role.substring(1);
        setTitle(roleTitle + " Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Theme.BACKGROUND_COLOR);

        // Header
        JLabel titleLabel = new JLabel(roleTitle + " Login", SwingConstants.CENTER);
        titleLabel.setFont(Theme.TITLE_FONT);
        titleLabel.setForeground(Theme.DARK_COLOR);

        // Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Theme.BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        formPanel.add(passwordField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Theme.BACKGROUND_COLOR);
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Theme.PRIMARY_COLOR);
        loginButton.setForeground(Color.WHITE);
        JButton backButton = new JButton("Back");
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        loginButton.addActionListener(e -> performLogin());
        passwordField.addActionListener(e -> performLogin());
        backButton.addActionListener(e -> {
            new WelcomeUI().setVisible(true);
            this.dispose();
        });

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        AuthenticationService authService = new AuthenticationService();
        User user = authService.authenticateUser(username, password);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        } else if (!user.getRole().equalsIgnoreCase(expectedRole)) {
            JOptionPane.showMessageDialog(this, "You do not have permission to log in as a " + expectedRole + ".", "Access Denied", JOptionPane.ERROR_MESSAGE);
        } else {
            openDashboard(user);
        }
    }

    private void openDashboard(User user) {
        switch (user.getRole().toLowerCase()) {
            case "doctor":
                new DoctorDashboard(user).setVisible(true);
                break;
            case "patient":
                int patientId = new PrescriptionManager().getPatientIdByUserId(user.getUserId());
                if (patientId != -1) {
                    new PatientDashboard(user, patientId).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Could not find a patient profile for this user.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                break;
            case "dispensary":
                new DispensaryDashboard(user).setVisible(true);
                break;
        }
        this.dispose();
    }
}
