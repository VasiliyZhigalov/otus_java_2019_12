package com.vasiliyzhigalov;

public class ThreadsMonitor {
    static final Object monitor = new Object();
    static final int LIMIT = 10;

    class Sequence implements Runnable {

        @Override
        public void run() {
            boolean forward = true;
            int counter = 1;
            while (true) {
                synchronized (monitor) {
                    System.out.println("Thread: " + Thread.currentThread().getName() + ", number: " + counter);
                    forward = counter != LIMIT && forward;
                    forward = counter == 1 || forward;
                    counter = forward ? counter + 1 : counter - 1;
                    monitor.notify();

                    try {
                        monitor.wait();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    Thread sequence1 = new Thread(new Sequence());
    Thread sequence2 = new Thread(new Sequence());

    static void run() {
        new ThreadsMonitor().sequence1.start();
        new ThreadsMonitor().sequence2.start();
    }

    public static void main(String[] args) {
        run();
    }
}