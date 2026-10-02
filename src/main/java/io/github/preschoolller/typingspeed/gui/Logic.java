package io.github.preschoolller.typingspeed.gui;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Logic {

    boolean timerStart = false;
    boolean timerShouldStop = false;
    long startTime;
    String typingText = "";

    final Window window;
    final Timer timer;

    public Logic(Window window) {
        this.window = window;
        this.timer = new Timer(100, e -> fresh());
    }

    public void fresh() {
        typingText = window.typingTextArea.getText();
        if (!timerStart) {
            if (typingText != null && !typingText.isEmpty()) {
                startTime = System.currentTimeMillis();
                timerStart = true;
            } else {
                return;
            }
        }
        int charNums = maxCommonPrefixLength(window.givenTextArea.getText(), typingText);
        long timeCost = System.currentTimeMillis() - startTime;
        double timeCostInMinute = (double) timeCost / 1000 / 60;
        window.typingCountButton.setText("字数：" + charNums);
        window.typingTimeButton.setText("用时：" + timeCost / 1000 + " s");
        int speed = (int) (charNums / timeCostInMinute);
        if (timeCostInMinute > 0) {
            window.typingSpeedButton.setText("速度：" + speed + " c/m");
        }
        if (window.givenTextArea.getText().length() == charNums) {
            timerShouldStop = true;
            JOptionPane.showMessageDialog(window, "用时：" + (timeCost / 1000) + "s 速度：" + speed + " c/m");
            window.typingTextArea.setText("");
            timer.stop();
        }
    }

    private int maxCommonPrefixLength(String given, String typing) {
        int i = 0;
        int tl = typing.length();
        while (i < tl && i < given.length()) {
            if (given.charAt(i) != typing.charAt(i)) {
                return i;
            }
            i++;
        }
        return i;
    }

    void timerControl() {
        if (timerShouldStop) {
            timer.stop();
            timerShouldStop = false;
            timerStart = false;
        } else {
            timer.start();
        }
    }

    void stopTimer() {
        timerStart = false;
        startTime = 0;
        timer.stop();
    }
}
