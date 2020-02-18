package com.vasiliyzhigalov.atm;

import java.util.Comparator;
import java.util.Objects;

public class Banknote {
    private String name;
    private Integer value;

    public Banknote(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Banknote banknote = (Banknote) o;
        return value == banknote.value &&
                Objects.equals(name, banknote.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public String toString() {
        return name;
    }
}
