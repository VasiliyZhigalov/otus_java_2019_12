package com.vasiliyzhigalov.testframework;

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
        this.testResult = new TestResult(this.testMethod.getName());
        this.testObj = testObj;
    }

    public void run() {
        try {
            beforeMethodsInvoke(testObj, beforeMethods);
        } catch (InvocationTargetException | IllegalAccessException e) {
            testResult.setResultAndMessage(false, "@before test: " + e.getCause().getMessage());
            e.printStackTrace();
            return;
        }
        try {
            testMethod.invoke(testObj);
            testResult.setResultAndMessage(true, "passed");
        } catch (IllegalAccessException e) {
            testResult.setResultAndMessage(false, e.getCause().getMessage());
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof AssertionFailedError) {
                testResult.setResultAndMessage(false, e.getCause().getMessage());
            } else {
                testResult.setResultAndMessage(false, e.getCause().getMessage());
                e.printStackTrace();
            }
        }
        afterMethodsInvoke(testObj, afterMethods);
    }

    private void beforeMethodsInvoke(Object obj, Set<Method> methods) throws InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            method.invoke(obj);

        }
    }

    private void afterMethodsInvoke(Object obj, Set<Method> methods) {
        for (Method method : methods) {
            try {
                method.invoke(obj);
            } catch (IllegalAccessException | InvocationTargetException e) {
                testResult.setResultAndMessage(false, "@after test: " + e.getCause().getMessage());
                e.printStackTrace();
            }
        }
    }

    public TestResult getTestResult() {
        return testResult;
    }
}

