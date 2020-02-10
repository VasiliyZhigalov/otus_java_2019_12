package com.vasiliyzhigalov.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class LoggingInvocationHandler implements InvocationHandler {

    private final TestLoggingInterface myClass;
    private Set<Method> annotatedMethods;

    public LoggingInvocationHandler(TestLoggingInterface myClass) {
        this.myClass = myClass;
        getAnnotatedMethodsName();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        for (Method annotatedMethod : annotatedMethods) {
            if (annotatedMethod.getName().equals(name) &&
                    Arrays.equals(annotatedMethod.getParameterTypes(), method.getParameterTypes())) {
                System.out.println("executed method: " + name + " | param: " + Arrays.toString(args));
            }
        }
        return method.invoke(myClass, args);
    }

    private void getAnnotatedMethodsName() {
        Class myClazz = TestLogging.class;
        Set<Method> methods = new HashSet<>(Arrays.asList(myClazz.getMethods()));
        annotatedMethods = new HashSet<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Log.class)) {
                annotatedMethods.add(method);
            }
        }
    }
}
