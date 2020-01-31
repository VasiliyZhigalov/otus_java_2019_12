package com.vasiliyzhigalov;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class GarbageCollectorUsing {
    public static void main(String[] args) throws InterruptedException {
        switchOnMonitoring();
        int size = 1000000;
        int loop = 10000000;
        long startTime = System.currentTimeMillis();
        long curTime;
        for (int out = 0; out < loop; out++) {
            Object[] objects = new Object[size];
            for (int i = 0; i < size; i++) {
                objects[i] = new byte[896];
                if (i % 2 == 0)
                    objects[i] = null;
            }
            curTime = (System.currentTimeMillis() - startTime);
            System.out.println("iter:" + out);
            System.out.println("time: " + curTime + "ms");
            if (curTime > 5 * 60 * 1000)
                break;
            Thread.sleep(100);

        }
    }

    private static void switchOnMonitoring() {
        AtomicInteger majorGCIer = new AtomicInteger();
        AtomicLong majorGCDuration = new AtomicLong();
        AtomicInteger minorGCIer = new AtomicInteger();
        AtomicLong minorGCDuration = new AtomicLong();
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();
                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();
                    if (gcAction.contains("minor")) {
                        minorGCIer.getAndIncrement();
                        minorGCDuration.set(minorGCDuration.get() + duration);
                    } else if (gcAction.contains("major")) {
                        majorGCIer.getAndIncrement();
                        majorGCDuration.set(majorGCDuration.get() + duration);
                    }
                    System.out.println(majorGCDuration + " ms major GC (" + majorGCIer + " collection)");
                    System.out.println(minorGCDuration + " ms minor GC (" + minorGCIer + " collection)");
                    //System.out.println("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }
}
