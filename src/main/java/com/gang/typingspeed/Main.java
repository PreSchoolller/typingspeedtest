package com.gang.typingspeed;

import com.gang.typingspeed.gui.Window;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Window window = new Window("Typing Speed Test");
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            window.setSize(1280, 720);
            window.setLocation(640, 360);
            window.setVisible(true);
        });
    }
}
