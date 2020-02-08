package com.vasiliyzhigalov.testfamework;

import com.vasiliyzhigalov.testfamework.annotations.After;
import com.vasiliyzhigalov.testfamework.annotations.Before;

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
    private Object testObj;
    public TestCase(Object testObj) {
        this.testObj = testObj;
        findAnnotationMethod(this.testObj.getClass());
    }
    public void addAllTests() {
        for (Method testMethod : testMethods) {
            tests.add(new Test(this.testObj,testMethod, beforeMethods, afterMethods));
        }
    }

    private void findAnnotationMethod(Class testClass) {
        Method[] methods = testClass.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                afterMethods.add(method);
            } else if (method.isAnnotationPresent(com.vasiliyzhigalov.testfamework.annotations.Test.class)) {
                testMethods.add(method);
            }
        }
    }

    public List<Test> getTests() {
        return tests;
    }
}
