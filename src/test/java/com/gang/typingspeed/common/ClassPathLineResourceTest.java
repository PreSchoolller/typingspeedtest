package com.gang.typingspeed.common;

import org.junit.jupiter.api.Test;

public class ClassPathLineResourceTest {

    @Test
    public void testGetRandomLine() {
        ClassPathLineResource classPathLineResource = new ClassPathLineResource();
        String randomLine = classPathLineResource.getRandomLine();
        System.out.println(randomLine);
    }
}
