package io.github.preschoolller.typingspeed.common.interfaces;

public interface LineSource {
    String getRandomLine();
    String getRandomLine(int offset);
    String getLine(int lineNumber);
}
