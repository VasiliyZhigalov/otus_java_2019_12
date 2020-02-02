package com.vasiliyzhigalov.proxy;

import java.lang.reflect.*;

class Logger {
    public static TestLoggingInterface logger() {
        InvocationHandler handler = new LoggingInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(Logger.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }
}
