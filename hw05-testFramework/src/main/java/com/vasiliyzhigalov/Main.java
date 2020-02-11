package com.vasiliyzhigalov;

import com.vasiliyzhigalov.testframework.TestRunner;

public class Main {
    public static void main(String[] args) {
        SomeTest someTest = new SomeTest();
        TestRunner.run(someTest.getClass());
    }
}
