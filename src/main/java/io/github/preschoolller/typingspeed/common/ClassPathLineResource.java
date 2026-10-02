package io.github.preschoolller.typingspeed.common;

import io.github.preschoolller.typingspeed.common.interfaces.LineSource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClassPathLineResource implements LineSource {

    private List<String> lines;
    private Integer lastRandomLine;
    private final Random random = new Random(System.currentTimeMillis());

    public ClassPathLineResource() {
        String data;
        try (InputStream textStream = this.getClass().getResourceAsStream("/text-source.txt")) {
            lines = new ArrayList<>();
            data = "";
            try {
                if (textStream != null) {
                    data = new String(textStream.readAllBytes(), StandardCharsets.UTF_8);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        lines = List.of(data.split("\\R"));
    }

    @Override
    public String getRandomLine() {
        int lineNumber = random.nextInt(0, lines.size()) + 1;
        lastRandomLine = lineNumber;
        return getLine(lineNumber);
    }

    @Override
    public String getRandomLine(int offset) {
        if (lastRandomLine == null) {
            return getRandomLine();
        }
        if (lastRandomLine + offset >= 1 && lastRandomLine + offset < lines.size() + 1) {
            return getLine(lastRandomLine + offset);
        }
        return getLine(1);
    }

    @Override
    public String getLine(int lineNumber) {
        return lines.get(lineNumber - 1);
    }
}
