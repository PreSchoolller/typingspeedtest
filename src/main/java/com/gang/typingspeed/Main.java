package com.gang.typingspeed;

import com.gang.typingspeed.gui.Window;
import javax.swing.WindowConstants;

public class Main {
    public static void main(String[] args) {
        Window window = new Window("Typing Speed Test");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(1280, 720);
        window.setLocation(640, 360);
        window.setVisible(true);

        while (true) {
            if (window.isActive()) {
                window.fresh();
            } else {
                Thread.yield();
            }
        }
    }
}
