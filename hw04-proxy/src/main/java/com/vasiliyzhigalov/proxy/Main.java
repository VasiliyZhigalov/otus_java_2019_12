package com.vasiliyzhigalov.proxy;

public class Main {
    public static void main(String[] args) {
        TestLoggingInterface myClass = Logger.logger();
        myClass.calculation(12, 11);
        myClass.first(12.1);
        myClass.second(1.23);
        myClass.third();
    }
}
