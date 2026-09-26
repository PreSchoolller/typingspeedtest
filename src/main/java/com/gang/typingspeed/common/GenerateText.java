package com.gang.typingspeed.common;

import com.gang.typingspeed.common.interfaces.LineSource;

import java.io.File;

public class GenerateText {

    private final LineSource lineSource;

    public GenerateText() {
        File extraFile = new File("text-source.txt");
        if (extraFile.exists()) {
            lineSource = new ExternalFileLineSource();
        } else {
            lineSource = new ClassPathLineResource();
        }
    }

    public String generate() {
        String randomLine = lineSource.getRandomLine();
        int offset = -1;
        while (randomLine.isBlank()) {
            randomLine = lineSource.getRandomLine();
            randomLine = lineSource.getRandomLine(offset);
            offset--;
        }
        return randomLine;
    }
}
