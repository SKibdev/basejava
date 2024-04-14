package ru.javawebinar.basejava;

public class DeadlockExample {
    private int counter0;
    private int counter1;

    private final static String LOCK_0 = "LOCK_0";
    private final static String LOCK_1 = "LOCK_1";

    public static void main(String[] args) throws InterruptedException {
        final DeadlockExample deadlockExample = new DeadlockExample();

        Thread thread0 = new Thread(deadlockExample::inc0);
        Thread thread1 = new Thread(deadlockExample::inc1);

        thread0.start();
        thread1.start();
    }

    private void inc0() {
        synchronizeCounter(LOCK_0, LOCK_1);
    }

    private synchronized void inc1() {
        synchronizeCounter(LOCK_1, LOCK_0);
    }

    private void synchronizeCounter(Object lock0, Object lock1) {
        synchronized (lock0) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            counter0++;
            System.out.println(Thread.currentThread().getName() + " получил блокировку " + lock0 + " " + counter0);
            synchronized (lock1) {
                counter1++;
                System.out.println(Thread.currentThread().getName() + " получил блокировку " + lock1 + " " + counter1);
            }
        }
    }
}