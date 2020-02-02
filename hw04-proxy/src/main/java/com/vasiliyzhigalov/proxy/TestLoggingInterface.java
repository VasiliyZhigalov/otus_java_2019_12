package com.vasiliyzhigalov.proxy;

public interface TestLoggingInterface {
    @Log
    int calculation(int x, int y);

    @Log
    void first(double q);

    void second(double q);
    @Log
    void third();
}