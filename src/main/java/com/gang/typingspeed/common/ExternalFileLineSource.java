package com.gang.typingspeed.common;

import com.gang.typingspeed.common.interfaces.LineSource;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExternalFileLineSource implements LineSource {

    private List<Long> lines;
    private RandomAccessFile externalFile;

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
        int lineNumber = new Random(System.currentTimeMillis()).nextInt(0, lines.size());
        return getLine(lineNumber + 1);
    }

    @Override
    public String getLine(int lineNumber) {
        String str;
        try {
            externalFile.seek(lines.get(lineNumber - 1));
            str = externalFile.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return str;
    }
}
