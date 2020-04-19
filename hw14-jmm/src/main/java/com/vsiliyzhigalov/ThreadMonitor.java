package com.vsiliyzhigalov;

public class ThreadMonitor {
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


    Sequence sequence1 = new Sequence();
    Sequence sequence2 = new Sequence();

    Thread sequenceThread1 = new Thread(sequence1);
    Thread sequenceThread2 = new Thread(sequence2);

    static void run() {
        new ThreadMonitor().sequenceThread1.start();
        new ThreadMonitor().sequenceThread2.start();
    }

    public static void main(String[] args) {
        run();
    }
}
