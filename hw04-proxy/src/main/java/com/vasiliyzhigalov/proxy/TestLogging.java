package com.vasiliyzhigalov.proxy;

public class TestLogging implements TestLoggingInterface {
     @Log
    public void first(double q) {}
    public void first(int q) {}
    @Log
    public void second(double q) { }
    public void third() {}
    @Log
    public int calculation(int x, int y) {
        return x + y;
    }
}
