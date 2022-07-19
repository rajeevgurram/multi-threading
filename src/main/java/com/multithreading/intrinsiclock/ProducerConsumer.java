package com.multithreading.intrinsiclock;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumer {
    public static void main(String[] args) {
        final Process process = new Process();

        Thread producerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process.producer();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread consumerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process.consumer();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        producerThread.start();
        consumerThread.start();
    }
}

// It produces 0 to 5 elements, and consumes it and reset the list.
class Process {
    private final List<Integer> data = new ArrayList<>();
    private final Object lock = new Object();

    private final int MIN_ELEMENTS = 0;
    private final int MAX_ELEMENTS = 5;

    private int index = 0;

    public void producer() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (data.size() == MAX_ELEMENTS) {
                    System.out.println("Reached Maximum Elements, Waiting for Consumer");
                    index = 0;
                    lock.wait();
                } else {
                    System.out.println("Adding Element: " + index);
                    data.add(index);
                    index++;
                    // After notify, it doesn't release the lock, it finishes the
                    // synchronized lock or until it reaches wait, it continues
                    lock.notify();
                }
                Thread.sleep(1000);
            }
        }
    }

    public void consumer() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (data.size() == MIN_ELEMENTS) {
                    System.out.println("No more elements at Consumer. Waiting for Producer");
                    lock.wait();
                } else {
                    Integer value = data.remove(data.size() - 1);
                    System.out.println("Consuming: " + value);
                    lock.notify();
                }
                Thread.sleep(1000);
            }
        }
    }
}
