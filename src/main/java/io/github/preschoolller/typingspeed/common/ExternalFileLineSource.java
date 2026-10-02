package io.github.preschoolller.typingspeed.common;

import io.github.preschoolller.typingspeed.common.interfaces.LineSource;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExternalFileLineSource implements LineSource {

    private List<Long> lines;
    private RandomAccessFile externalFile;
    private Integer lastRandomLine;

    public ExternalFileLineSource() {
        lines = new ArrayList<Long>();
        try {
            externalFile = new RandomAccessFile("text-source.txt", "r");
            while (true) {
                long filePointer = externalFile.getFilePointer();
                if (externalFile.readLine() == null) {
                    break;
                }
                lines.add(filePointer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getRandomLine() {
        int lineNumber = new Random(System.currentTimeMillis()).nextInt(0, lines.size()) + 1;
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
        String str;
        try {
            externalFile.seek(lines.get(lineNumber - 1));
            str = externalFile.readLine();
            str = new String(str.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return str;
    }
}
