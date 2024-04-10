package ru.javawebinar.basejava;

public class DeadlockExample {
    private int counter0;
    private int counter1;

    private final static Object LOCK_0 = new Object();
    private final static Object LOCK_1 = new Object();

    public static void main(String[] args) throws InterruptedException {
        final DeadlockExample deadlockExample = new DeadlockExample();

        Thread thread0 = new Thread(deadlockExample::inc0);
        Thread thread1 = new Thread(deadlockExample::inc1);

        thread0.start();
        thread1.start();
    }

    private void inc0() {
        synchronized (LOCK_0) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            counter0++;
            System.out.println(Thread.currentThread().getName() + " получил блокировку LOCK_0 " + counter0);
            synchronized (LOCK_1) {
                counter1++;
                System.out.println(Thread.currentThread().getName() + " получил блокировку LOCK_1 " + counter1);
            }
        }
    }

    private synchronized void inc1() {
        synchronized (LOCK_1) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            counter1++;
            System.out.println(Thread.currentThread().getName() + " получил блокировку LOCK_1 " + counter1);
            synchronized (LOCK_0) {
                counter0++;
                System.out.println(Thread.currentThread().getName() + " получил блокировку LOCK_0 " + counter0);
            }
        }
    }
}