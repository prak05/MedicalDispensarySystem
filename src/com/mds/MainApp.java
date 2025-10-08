package com.mds;

import com.mds.view.WelcomeUI;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MainApp {
    public static void main(String[] args) {
        try {
            // Corrected method call
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new WelcomeUI().setVisible(true));
    }
}
