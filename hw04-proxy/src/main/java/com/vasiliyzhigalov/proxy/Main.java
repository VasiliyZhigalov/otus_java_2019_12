package com.vasiliyzhigalov.proxy;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        TestLoggingInterface myClass = Logger.logger();
        myClass.calculation(12,11);
        myClass.first(12.1);
        myClass.second(1.23);
        myClass.third();
    }
}
