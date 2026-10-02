package io.github.preschoolller.typingspeed.gui;

import javax.swing.JOptionPane;

public class Logic {

    Logic() {}

    public void fresh(Window window) {
        window.typingText = window.typingTextArea.getText();
        if (!window.timerStart) {
            if (window.typingText != null && !window.typingText.isEmpty()) {
                window.startTime = System.currentTimeMillis();
                window.timerStart = true;
            } else {
                return;
            }
        }
        int charNums = maxCommonPrefixLength(window.givenTextArea.getText(), window.typingText);
        long timeCost = System.currentTimeMillis() - window.startTime;
        double timeCostInMinute = (double) timeCost / 1000 / 60;
        window.typingCountButton.setText("字数：" + charNums);
        window.typingTimeButton.setText("用时：" + timeCost / 1000 + " s");
        int speed = (int) (charNums / timeCostInMinute);
        if (timeCostInMinute > 0) {
            window.typingSpeedButton.setText("速度：" + speed + " /m");
        }
        if (window.givenTextArea.getText().length() == charNums) {
            window.timerShouldStop = true;
            JOptionPane.showMessageDialog(window, "用时：" + (timeCost / 1000) + "s 速度：" + speed + " c/s");
            window.typingTextArea.setText("");
            window.timer.stop();
        }
    }

    private int maxCommonPrefixLength(String given, String typing) {
        int i = 0;
        int tl = typing.length();
        while (i < tl) {
            try {
                if (given.charAt(i) != typing.charAt(i)) {
                    return i;
                }
            } catch (IndexOutOfBoundsException exception) {
                return i;
            }
            i++;
        }
        return i;
    }
}
