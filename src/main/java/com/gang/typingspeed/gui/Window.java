package com.gang.typingspeed.gui;

import com.gang.typingspeed.common.GenerateText;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JFrame {

    private final JPanel innerPanel = new JPanel();
    private JPanel textPanel = new JPanel(new GridLayout(2, 1, 10, 10));
    private JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

    private final JTextArea givenTextArea = new JTextArea();
    private final JTextArea typingTextArea = new JTextArea();

    private final JButton typingCountButton = new JButton();
    private final JButton typingTimeButton = new JButton();
    private final JButton typingSpeedButton = new JButton();
    private final JButton freshButton = new JButton();

    private final GenerateText textGenerator = new GenerateText();

    private Color backgroundColor = Color.WHITE;
    private Color textAreaBackgroundColor = Color.WHITE;

    private String typingText = "";
    private static String DEFAULT_GIVEN_TEXT = "This is a typing speed test example";
    private boolean timerStart = false;
    private boolean timerShouldStop = false;
    private long startTime;
    private final Timer timer = new Timer(100, e -> fresh());

    public Window(String title) throws HeadlessException {
        super(title);

        initGivenTextArea();
        initTypingTextArea();
        initButtons();

        innerPanel.setBackground(Color.WHITE);
        innerPanel.setBackground(backgroundColor);
        innerPanel.setLayout(new BorderLayout(10, 10)); // 垂直布局
        innerPanel.setBorder(BorderFactory.createTitledBorder("Attention is all you need")); // 给面板加个边框和标题
        innerPanel.add(textPanel, BorderLayout.CENTER);
        innerPanel.add(statsPanel, BorderLayout.SOUTH);

        textPanel.setLayout(new GridLayout(2, 1, 10, 10));
        textPanel.add(givenTextArea);
        textPanel.add(typingTextArea);

        statsPanel.setBackground(Color.WHITE);
        statsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // 水平排列，带间距
        statsPanel.add(typingCountButton);
        statsPanel.add(typingTimeButton);
        statsPanel.add(typingSpeedButton);
        statsPanel.add(freshButton);

        freshButton.addActionListener(e -> freshGivenText());

        add(innerPanel);
    }

    private void initButtons() {
        typingCountButton.setText("字数：");
        typingCountButton.setBackground(Color.WHITE); // 模仿手绘图的空白感
        typingCountButton.setPreferredSize(new Dimension(200, 40));

        typingTimeButton.setText("用时：");
        typingTimeButton.setBackground(Color.WHITE); // 模仿手绘图的空白感
        typingTimeButton.setPreferredSize(new Dimension(200, 40));

        typingSpeedButton.setText("速度：");
        typingSpeedButton.setBackground(Color.WHITE); // 模仿手绘图的空白感
        typingSpeedButton.setPreferredSize(new Dimension(200, 40));

        freshButton.setText("刷新");
        freshButton.setBackground(Color.WHITE); // 模仿手绘图的空白感
        freshButton.setPreferredSize(new Dimension(200, 40));
    }

    private void initTypingTextArea() {
        typingTextArea.setEditable(true);
        typingTextArea.setLineWrap(true);
        typingTextArea.setBackground(textAreaBackgroundColor);
        typingTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        typingTextArea.setFont(new Font("宋体", Font.BOLD, 20));
        typingTextArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        typingTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        typingTextArea.setToolTipText("typing here to start test");
        typingTextArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (timerShouldStop) {
                    timer.stop();
                    timerShouldStop = false;
                    timerStart = false;
                } else {
                    timer.start();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void initGivenTextArea() {
        givenTextArea.setEditable(false);
        givenTextArea.setLineWrap(true);
        givenTextArea.setBackground(textAreaBackgroundColor);
        givenTextArea.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        givenTextArea.setFont(new Font("宋体", Font.BOLD, 20));
        givenTextArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80)); // 限制高度
        givenTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // 黑色边框
        givenTextArea.setText(DEFAULT_GIVEN_TEXT);
    }

    public void fresh() {
        typingText = typingTextArea.getText();
        if (!timerStart) {
            if (typingText != null && !typingText.isEmpty()) {
                startTime = System.currentTimeMillis();
                timerStart = true;
            } else {
                return;
            }
        }
        int charNums = maxCommonPrefixLength(givenTextArea.getText(), typingText);
        long timeCost = System.currentTimeMillis() - startTime;
        double timeCostInMinute = (double) timeCost / 1000 / 60;
        typingCountButton.setText("字数：" + charNums);
        typingTimeButton.setText("用时：" + timeCost / 1000 + " s");
        int speed = (int) (charNums / timeCostInMinute);
        if (timeCostInMinute > 0) {
            typingSpeedButton.setText("速度：" + speed + " /m");
        }
        if (givenTextArea.getText().length() == charNums) {
            timerShouldStop = true;
            JOptionPane.showMessageDialog(this, "用时：" + (timeCost / 1000) + "s 速度：" + speed + " c/s");
            typingTextArea.setText("");
            timer.stop();
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

    private void freshGivenText() {
        String givenText = textGenerator.generate();
        if (givenText.isEmpty()) {
            givenText = DEFAULT_GIVEN_TEXT;
        }
        givenTextArea.setText(givenText);
        typingTextArea.setText("");
        timerStart = false;
        startTime = 0;
        initButtons();
    }
}
