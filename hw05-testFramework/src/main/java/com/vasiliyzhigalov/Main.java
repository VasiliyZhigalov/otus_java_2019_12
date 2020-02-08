package com.vasiliyzhigalov;

import com.vasiliyzhigalov.testfamework.TestRunner;

public class Main {
    public static void main(String[] args) {
        SomeTest someTest = new SomeTest();
        TestRunner.run(someTest);
    }
}
