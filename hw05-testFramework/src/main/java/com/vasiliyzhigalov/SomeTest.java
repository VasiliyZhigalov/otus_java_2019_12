package com.vasiliyzhigalov;

import com.vasiliyzhigalov.testfamework.Assert;
import com.vasiliyzhigalov.testfamework.annotations.After;
import com.vasiliyzhigalov.testfamework.annotations.Before;
import com.vasiliyzhigalov.testfamework.annotations.Test;

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

    @Test
    public void test1() {
        Assert.assertTrue(testList1, testList2);
    }

    @Test
    public void test2() {
        Assert.assertFalse(testList1, testList2);
    }

    @Test
    public void test3() {
        Integer[] arr = new Integer[3];
        Assert.assertTrue(arr[4], arr[0]);
    }

    @After
    public void afterTest() {
        testList1.remove(0);
    }
}
