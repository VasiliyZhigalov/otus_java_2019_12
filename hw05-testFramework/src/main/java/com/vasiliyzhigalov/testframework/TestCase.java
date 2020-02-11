package com.vasiliyzhigalov.testframework;

import com.vasiliyzhigalov.testframework.annotations.After;
import com.vasiliyzhigalov.testframework.annotations.Before;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestCase {
    private List<Test> tests = new ArrayList<>();
    private Set<Method> beforeMethods = new HashSet<>();
    private Set<Method> afterMethods = new HashSet<>();
    private Set<Method> testMethods = new HashSet<>();
    private Class testClass;

    public TestCase(Class testClass) {
        this.testClass = testClass;
        findAnnotationMethod(testClass);
    }

    public void addAllTests() {
        for (Method testMethod : testMethods) {
            Object testObj = null;
            try {
                testObj = testClass.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            tests.add(new Test(testObj, testMethod, beforeMethods, afterMethods));
        }
    }

    private void findAnnotationMethod(Class testClass) {
        Method[] methods = testClass.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                afterMethods.add(method);
            } else if (method.isAnnotationPresent(com.vasiliyzhigalov.testframework.annotations.Test.class)) {
                testMethods.add(method);
            }
        }
    }

    public List<Test> getTests() {
        return tests;
    }

    public int size() {
        return tests.size();
    }
}
