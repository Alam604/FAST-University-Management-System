package com.fast.ui.gui;

import javax.swing.*;

public class GUITest extends JFrame {
    public GUITest() {
        setTitle("GUI Test - If you see this, Swing works!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        JLabel label = new JLabel("✓ GUI is working!");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);
    }
    
    public static void main(String[] args) {
        try {
            System.out.println("Starting GUI...");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            SwingUtilities.invokeLater(() -> {
                System.out.println("Creating test window...");
                GUITest frame = new GUITest();
                frame.setVisible(true);
                System.out.println("Window should be visible now!");
            });
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
