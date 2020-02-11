package com.vasiliyzhigalov;

import com.vasiliyzhigalov.testframework.Assert;
import com.vasiliyzhigalov.testframework.annotations.After;
import com.vasiliyzhigalov.testframework.annotations.Before;
import com.vasiliyzhigalov.testframework.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SomeTest {
    List<String> testList1;
    List<String> testList2;

    @Before
    public void beforeTest() {
        testList1 = new ArrayList<>(Arrays.asList("first", "second", "third"));
        testList2 = new ArrayList<>(Arrays.asList("first", "second", "third"));
    }

    public void beforeTest1() {
        System.out.println("before test 1");
    }

    public void beforeTest2() {
        System.out.println("before test 2");
    }

    @Test
    public void test1() {
        Assert.assertTrue(testList1, testList2);
    }
    @Test
    public void test2() {
        Assert.assertFalse(testList1, testList2);
    }
    @After
    public void afterTest1() {
        testList2.remove(0);
    }

}
