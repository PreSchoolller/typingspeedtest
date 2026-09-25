package com.gang.typingspeed.common;

import com.gang.typingspeed.common.interfaces.LineSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClassPathLineResource implements LineSource {

    private List<String> lines;

    public ClassPathLineResource() {
        String data;
        try (InputStream textStream = this.getClass().getResourceAsStream("/text-source.txt")) {
            lines = new ArrayList<>();
            data = "";
            try {
                if (textStream != null) {
                    data = new String(textStream.readAllBytes());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        lines = List.of(data.split("\n"));
    }

    @Override
    public String getRandomLine() {
        int lineNumber = new Random(System.currentTimeMillis()).nextInt(0, lines.size());
        return getLine(lineNumber);
    }

    @Override
    public String getLine(int lineNumber) {
        return lines.get(lineNumber);
    }
}
