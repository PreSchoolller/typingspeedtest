package io.github.preschoolller.typingspeed.gui;

import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.Color;

public class Logic {

    boolean timerStart = false;
    boolean timerShouldStop = false;
    long startTime;
    String typingText = "";

    final Window window;
    final Timer timer;
    final Highlighter.HighlightPainter TYPED_RIGHT = new DefaultHighlighter.DefaultHighlightPainter(new Color(0, 128, 0));
    final Highlighter.HighlightPainter TYPED_RIGHT_NEXT = new DefaultHighlighter.DefaultHighlightPainter(new Color(254, 242, 3));

    public Logic(Window window) {
        this.window = window;
        this.timer = new Timer(100, e -> fresh());
    }

    public void fresh() {
        window.givenTextArea.getHighlighter().removeAllHighlights();
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
        try {
            window.givenTextArea
                    .getHighlighter()
                    .addHighlight(0, charNums, TYPED_RIGHT);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
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
            window.givenTextArea.getHighlighter().removeAllHighlights();
            timer.stop();
        }
        try {
            window.givenTextArea.getHighlighter().addHighlight(charNums, charNums + 1, TYPED_RIGHT_NEXT);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
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
