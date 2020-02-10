package com.vasiliyzhigalov.testframework;

public class TestResult {
    private String testName;
    private boolean result;
    private String message;

    public TestResult(String testName, boolean result, String massege) {
        this.result = result;
        this.message = massege;
        this.testName = testName;
    }

    public TestResult(String testName) {
        this.testName = testName;
    }

    public void setResultAndMessage(boolean result, String message) {
        this.result = result;
        this.message = message;
    }


    public boolean getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public String getTestName() {
        return testName;
    }
}
