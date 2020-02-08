package com.vasiliyzhigalov.testfamework;

import com.vasiliyzhigalov.testfamework.annotations.TestResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class Test {
    private Method testMethod;
    private Set<Method> beforeMethods;
    private Set<Method> afterMethods;
    private Object testObj;
    private TestResult testResult;

    public Test(Object testObj, Method testMethod, Set<Method> beforeMethods, Set<Method> afterMethods) {
        this.testMethod = testMethod;
        this.beforeMethods = beforeMethods;
        this.afterMethods = afterMethods;
        this.testObj = testObj;
    }

    public void run() {
        methodsInvoke(testObj, beforeMethods);
        try {
            testMethod.invoke(testObj);
            testResult = new TestResult(testMethod.getName(), true, "passed");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof AssertionFailedError) {
                testResult = new TestResult(testMethod.getName(), false, e.getCause().getMessage());
            } else {
                testResult = new TestResult(testMethod.getName(), false, e.getCause().getMessage());
                e.printStackTrace();

            }
        }
        methodsInvoke(testObj, afterMethods);
    }

    private void methodsInvoke(Object obj, Set<Method> methods) {
        methods.forEach(before -> {
            try {
                before.invoke(obj);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    public TestResult getTestResult() {
        return testResult;
    }
}
