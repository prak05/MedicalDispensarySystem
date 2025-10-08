package com.mds.view;

import com.mds.model.User;
import javax.swing.*;
import java.awt.*;

public abstract class BaseDashboard extends JFrame {
    protected User currentUser;
    protected JPanel centerPanel;

    public BaseDashboard(User user, String title) {
        this.currentUser = user;
        setTitle(title + " - Welcome, " + user.getName());
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Theme.BACKGROUND_COLOR);

        mainPanel.add(createHeaderPanel(title), BorderLayout.NORTH);

        centerPanel = new JPanel();
        centerPanel.setBackground(Theme.BACKGROUND_COLOR);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
        initializeComponents();
    }

    private JPanel createHeaderPanel(String title) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Theme.DARK_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(Theme.TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(logoutButton, BorderLayout.EAST);
        return headerPanel;
    }

    private void logout() {
        this.dispose();
        new WelcomeUI().setVisible(true);
    }

    protected abstract void initializeComponents();
}
