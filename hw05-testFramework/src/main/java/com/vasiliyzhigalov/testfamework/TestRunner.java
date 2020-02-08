package com.vasiliyzhigalov.testfamework;

public class TestRunner {
    public static void run(Object testClass) {
        int testCounter = 0;
        int failTestCounter = 0;

        TestCase tests = new TestCase(testClass);
        tests.addAllTests();
        for (Test test : tests.getTests()) {
            testCounter++;
            test.run();
            if (!test.getTestResult().getResult()) {
                failTestCounter++;
            }
            System.out.println(test.getTestResult().getTestName() + ":");
            System.out.println(test.getTestResult().getMessage());
        }
        int passedTestCont = testCounter - failTestCounter;
        System.out.println();
        System.out.println("______________________\n"+"completed tests: " + testCounter);
        System.out.println("passed tests: " + passedTestCont + " | failed tests: " + failTestCounter);

    }


}
