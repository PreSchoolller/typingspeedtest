package com.gang.typingspeed.common;

import com.gang.typingspeed.common.interfaces.LineSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
        lines = Arrays.stream(data.split("\n"))
                .map(String::trim)
                .filter(trimd -> !trimd.isEmpty())
                .collect(Collectors.toList());
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

    public static void main(String[] args) {
        String randomLine = new ClassPathLineResource().getRandomLine();
        System.out.println(randomLine);
    }
}
