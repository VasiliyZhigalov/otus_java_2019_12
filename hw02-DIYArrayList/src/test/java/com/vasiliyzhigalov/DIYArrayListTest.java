package com.vasiliyzhigalov;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;


public class DIYArrayListTest {
    ArrayList<String> arrayList = new ArrayList<>();
    DIYArrayList<String> diyArrayList = new DIYArrayList<>();

    @Before
    public void init() {
        int N = 24;
        String strArr = "arr_";
        for (int i = 0; i < N; i++) {
            strArr += i;
            arrayList.add(strArr);
            diyArrayList.add(strArr);
        }
    }

    @Test
    public void collectionsAddAllTest() {
        Collections.addAll(arrayList, "new", "new1", "new2");
        Collections.addAll(diyArrayList, "new", "new1", "new2");
        Assert.assertArrayEquals(arrayList.toArray(), diyArrayList.toArray());
    }

    @Test
    public void collectionsCopyTest() {
        DIYArrayList<String> scr = new DIYArrayList<>();
        scr.add("first");
        scr.add("second");
        Collections.copy(diyArrayList, scr);
        Collections.copy(arrayList, scr);
        Assert.assertArrayEquals(arrayList.toArray(), diyArrayList.toArray());
    }

    @Test
    public void collectionsSortTest() {
        DIYArrayList<Integer> diy = new DIYArrayList<>();
        diy.add(2);
        diy.add(1);
        diy.add(4);
        diy.add(3);
        diy.add(0);
        ArrayList<Integer> arr = new ArrayList<>(diy);
        Collections.sort(diy);
        Collections.sort(arr);
        Assert.assertEquals(arr, diy);
    }

    @Test
    public void containsNull() {
        List<String> list = new DIYArrayList<>();
        list.add(null);
        Assert.assertTrue(list.contains(null));
    }
}
