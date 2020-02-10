package com.vasiliyzhigalov.testframework;

public class TestRunner {
    public static void run(Class testClass) {
        int failTestCounter = 0;
        TestCase tests = new TestCase(testClass);
        tests.addAllTests();
        for (Test test : tests.getTests()) {
            test.run();
            if (!test.getTestResult().getResult()) {
                failTestCounter++;
            }
            System.out.println(test.getTestResult().getTestName() + ":");
            System.out.println(test.getTestResult().getResult());
            System.out.println(test.getTestResult().getMessage());
        }
        int passedTestCont = tests.size() - failTestCounter;
        System.out.println();
        System.out.println("______________________\n" + "completed tests: " + tests.size());
        System.out.println("passed tests: " + passedTestCont + " | failed tests: " + failTestCounter);
    }
}
