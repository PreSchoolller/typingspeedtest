package io.github.preschoolller.typingspeed.common;

import org.junit.jupiter.api.Test;

public class ExternalFileLineSourceTest {

    @Test
    public void testGetRandomLine() {
        ExternalFileLineSource externalFileLineSource = new ExternalFileLineSource();
        String randomLine = externalFileLineSource.getRandomLine();
        System.out.println(randomLine);
    }
}
