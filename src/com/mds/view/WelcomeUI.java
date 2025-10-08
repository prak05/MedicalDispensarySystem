package com.mds.view;

import javax.swing.*;
import java.awt.*;

public class WelcomeUI extends JFrame {
    public WelcomeUI() {
        setTitle("Medical Dispensary System - Welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Theme.BACKGROUND_COLOR);

        // Header
        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(Theme.PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(600, 100));
        JLabel titleLabel = new JLabel("Medical Dispensary System");
        titleLabel.setFont(Theme.TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Center
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Theme.BACKGROUND_COLOR);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel welcomeLabel = new JLabel("Select Your Role to Continue");
        welcomeLabel.setFont(Theme.HEADER_FONT);
        welcomeLabel.setForeground(Theme.DARK_COLOR);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton doctorButton = createRoleButton("Doctor");
        JButton patientButton = createRoleButton("Patient");
        JButton dispensaryButton = createRoleButton("Dispensary");

        doctorButton.addActionListener(e -> openLogin("doctor"));
        patientButton.addActionListener(e -> openLogin("patient"));
        dispensaryButton.addActionListener(e -> openLogin("dispensary"));

        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(doctorButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(patientButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(dispensaryButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JButton createRoleButton(String role) {
        JButton button = new JButton(role);
        button.setFont(Theme.HEADER_FONT);
        button.setBackground(Theme.SECONDARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(250, 50));
        return button;
    }

    private void openLogin(String role) {
        new LoginUI(role).setVisible(true);
        this.dispose();
    }
}
