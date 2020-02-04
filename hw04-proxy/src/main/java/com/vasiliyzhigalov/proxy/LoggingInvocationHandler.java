package com.vasiliyzhigalov.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoggingInvocationHandler implements InvocationHandler {

    private final TestLoggingInterface myClass;
    private List<String> annotatedMethodsName;

    public LoggingInvocationHandler(TestLoggingInterface myClass) {
        this.myClass = myClass;
        getAnnotatedMethodsName();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        if (annotatedMethodsName.contains(name)) {
            System.out.println("executed method: " + name + " | param: " + Arrays.toString(args));
        }
        return method.invoke(myClass, args);
    }

    private void getAnnotatedMethodsName() {
        Method[] methods = TestLogging.class.getMethods();
        annotatedMethodsName = new ArrayList<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Log.class)) {
                annotatedMethodsName.add(method.getName());
            }
        }
    }
}
