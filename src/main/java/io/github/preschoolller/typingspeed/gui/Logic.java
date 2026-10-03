package io.github.preschoolller.typingspeed.gui;

import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import java.awt.Color;

public class Logic {

    boolean timerStart = false;
    boolean timerShouldStop = false;
    long startTime;
    String typingText = "";
    int lastCharNum;

    final Window window;
    final Timer timer;
    final StyledDocument styledDocument;
    final SimpleAttributeSet TYPED_RIGHT = new SimpleAttributeSet();
    final SimpleAttributeSet TYPED_RIGHT_NEXT = new SimpleAttributeSet();
    final Style BASE_STYLE;

    public Logic(Window window) {
        this.window = window;
        timer = new Timer(100, e -> fresh());
        styledDocument = window.givenTextArea.getStyledDocument();
        StyleConstants.setForeground(TYPED_RIGHT, new Color(0, 128, 0));
        StyleConstants.setForeground(TYPED_RIGHT_NEXT, new Color(219, 205, 0));

        BASE_STYLE = styledDocument.getStyle(StyleContext.DEFAULT_STYLE);
    }

    public void fresh() {
        typingText = window.typingTextArea.getText();
        String givenTextAreaText = window.givenTextArea.getText();
        if (!timerStart) {
            if (typingText != null && !typingText.isEmpty()) {
                startTime = System.currentTimeMillis();
                timerStart = true;
            } else {
                return;
            }
        }
        int charNums = maxCommonPrefixLength(givenTextAreaText, typingText);
        long timeCost = System.currentTimeMillis() - startTime;
        double timeCostInMinute = (double) timeCost / 1000 / 60;
        window.typingCountButton.setText("字数：" + charNums);
        window.typingTimeButton.setText("用时：" + timeCost / 1000 + " s");
        int speed = (int) (charNums / timeCostInMinute);
        if (timeCostInMinute > 0) {
            window.typingSpeedButton.setText("速度：" + speed + " c/m");
        }
        if (lastCharNum == charNums) {
            return;
        }
        lastCharNum = charNums;
        window.givenTextArea.setText(givenTextAreaText);
        styledDocument.setCharacterAttributes(0, charNums, TYPED_RIGHT, false);
        if (givenTextAreaText.length() == charNums) {
            timerShouldStop = true;
            JOptionPane.showMessageDialog(window, "用时：" + (timeCost / 1000) + "s 速度：" + speed + " c/m");
            window.typingTextArea.setText("");
            styledDocument.setCharacterAttributes(0, charNums, BASE_STYLE, true);
            timer.stop();
            return;
        }
        styledDocument.setCharacterAttributes(charNums, 1, TYPED_RIGHT_NEXT, false);
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
