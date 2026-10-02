package io.github.preschoolller.typingspeed.common.interfaces;

/**
 * line number start from 1 for first line, and it should be real line number even if it is blank.
 */
public interface LineSource {
    /**
     *
     * @return content of a random line
     */
    String getRandomLine();

    /**
     * should be called only after getRandomLine() call
     * @param offset based on last line number used by getRandomLine()
     * @return content of the line
     */
    String getRandomLine(int offset);

    /**
     * get content of specified line number
     * @param lineNumber
     * @return
     */
    String getLine(int lineNumber);
}
