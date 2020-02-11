package com.vasiliyzhigalov.testframework;

public class Assert {
    public static void assertTrue(Object expected, Object actual)  {
        if (!isEquals(expected, actual)) {
            throw new AssertionFailedError(message("assertTrue",expected.toString(), actual.toString()));
        }
    }

    public static void assertFalse(Object expected, Object actual) {
        if (isEquals(expected, actual)) {
            throw new AssertionFailedError(message("assertFalse",expected.toString(), actual.toString()));
        }
    }



    private static boolean isEquals(Object expected, Object actual) {
        return expected.equals(actual);
    }

    private static String message(String assertion, String expected, String actual) {
        return  assertion+":\n" +
                "expected: " + expected + "\n" +
                "actual: " + actual;
    }
}
