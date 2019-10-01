package com.example.sber_app1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordHelperTest {

    private static final String[] RUS = {"й", "ц", "у", "к", "е", "н"};
    private static final String [] ENG = {"q", "w", "e", "r", "t", "y"};

    public static final String[] SOURCES = {
            "",
            "некуцй",
            "про"
    };

    public static final String[] RESULTS = {
            "",
            "ytrewq",
            "про"
    };
    private PasswordHelper helper;

    @Before
    public void setUp() throws Exception {
        helper = new PasswordHelper(RUS, ENG);
    }

    @Test
    public void convert() {
        assertEquals("", helper.convert(""));
    }

    @Test
    public void convertIsThrows(){
        assertTrue("Error in test case", SOURCES.length == RESULTS.length);

        for (int i = 0; i < SOURCES.length; i++) {
            assertEquals(RESULTS[i], helper.convert(SOURCES[i]));
        }
    }
}