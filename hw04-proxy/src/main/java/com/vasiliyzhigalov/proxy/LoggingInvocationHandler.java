package com.vasiliyzhigalov.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class LoggingInvocationHandler implements InvocationHandler {

    private final TestLoggingInterface myClass;

    public LoggingInvocationHandler(TestLoggingInterface myClass) {
        this.myClass = myClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Log.class)) {
            System.out.println("executed method: " + method.getName() + " | param: " + Arrays.toString(args));
        }
        return method.invoke(myClass, args);
    }
}
