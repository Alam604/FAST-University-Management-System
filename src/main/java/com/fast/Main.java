package com.fast;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.fast.ui.gui.WelcomeLoginFrame;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("================================");
            System.out.println("Starting FAST University System");
            System.out.println("================================");
            
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            System.out.println("✓ UI Look and Feel set");
            
            // Launch GUI
            SwingUtilities.invokeLater(() -> {
                try {
                    System.out.println("✓ Creating Welcome/Login Frame...");
                    WelcomeLoginFrame frame = new WelcomeLoginFrame();
                    frame.setVisible(true);
                    System.out.println("✓ GUI Window displayed!");
                } catch (Exception e) {
                    System.err.println("✗ Error creating GUI: " + e.getMessage());
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            System.err.println("✗ Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}