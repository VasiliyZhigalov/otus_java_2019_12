package com.vasiliyzhigalov.POJOs;

import java.util.List;
import java.util.Objects;

public class DemoPOJO {
        private String strVar;
    private Integer integerVar;
    private Character characterVar;
     private List<String> strList;
    private String[] strArray;
    private Integer[] IntArray;
    private DemoPOJO2 testPOJO2;

    public DemoPOJO(String strVar, Integer integerVar, Character characterVar, List<String> strList, String[] strArray) {
        this.strVar = strVar;
        this.integerVar = null;
        this.characterVar = characterVar;
         this.strList = strList;
        this.strArray = strArray;
        this.IntArray = new Integer[]{1, 2, 3};
        this.testPOJO2 = new DemoPOJO2();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        DemoPOJO pojo = (DemoPOJO) object;
        return Objects.equals(characterVar, pojo.characterVar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(characterVar);
    }
}
