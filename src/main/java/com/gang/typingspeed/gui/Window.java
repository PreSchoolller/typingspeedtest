package com.gang.typingspeed.gui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;

public class Window extends JFrame {

    private final JPanel innerPanel = new JPanel();
    private JPanel textPanel = new JPanel(new GridLayout(2, 1, 10, 10));
    private JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

    private final JTextArea givenTextArea = new JTextArea();
    private final JTextArea typingTextArea = new JTextArea();

    private final JButton typingCount = new JButton();
    private final JButton typingTime = new JButton();
    private final JButton typingSpeed = new JButton();
    private final JButton placeHolder = new JButton();

    private Color backgroundColor = Color.WHITE;
    private Color textAreaBackgroundColor = Color.WHITE;

    public Window(String title) throws HeadlessException {
        super(title);

        initGivenTextArea();
        initTypingTextArea();
        initButtons();

        innerPanel.setBackground(Color.WHITE);
        innerPanel.setBackground(backgroundColor);
        innerPanel.setLayout(new BorderLayout(10, 10)); // 垂直布局
        innerPanel.setBorder(BorderFactory.createTitledBorder("内层窗口")); // 给面板加个边框和标题
        innerPanel.add(textPanel, BorderLayout.CENTER);
        innerPanel.add(statsPanel, BorderLayout.SOUTH);

        textPanel.setLayout(new GridLayout(2, 1, 10, 10));
        textPanel.add(givenTextArea);
        textPanel.add(typingTextArea);

        statsPanel.setBackground(Color.WHITE);
        statsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // 水平排列，带间距
        statsPanel.add(typingCount);
        statsPanel.add(typingTime);
        statsPanel.add(typingSpeed);
        statsPanel.add(placeHolder);

        add(innerPanel);
    }

    private void initButtons() {
        typingCount.setText("字符数：");
        typingCount.setBackground(Color.WHITE); // 模仿手绘图的空白感
        typingCount.setPreferredSize(new Dimension(80, 40));

        typingTime.setText("总用时：");
        typingTime.setBackground(Color.WHITE); // 模仿手绘图的空白感
        typingTime.setPreferredSize(new Dimension(80, 40));

        typingSpeed.setText("速度：");
        typingSpeed.setBackground(Color.WHITE); // 模仿手绘图的空白感
        typingSpeed.setPreferredSize(new Dimension(80, 40));

        placeHolder.setText("占位：");
        placeHolder.setBackground(Color.WHITE); // 模仿手绘图的空白感
        placeHolder.setPreferredSize(new Dimension(80, 40));
    }

    private void initTypingTextArea() {
        givenTextArea.setEditable(true);
        givenTextArea.setBackground(textAreaBackgroundColor);
        typingTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        typingTextArea.setFont(new Font("宋体", Font.BOLD, 20));
        typingTextArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        typingTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        typingTextArea.setToolTipText("typing here to start test");
    }

    private void initGivenTextArea() {
        givenTextArea.setEditable(false);
        givenTextArea.setBackground(textAreaBackgroundColor);
        givenTextArea.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        givenTextArea.setFont(new Font("宋体", Font.BOLD, 20));
        givenTextArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80)); // 限制高度
        givenTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // 黑色边框
        givenTextArea.setText("This is a typing speed test example");
    }

    @Override
    public void update(Graphics g) {
        while (true) {

        }
    }
}
