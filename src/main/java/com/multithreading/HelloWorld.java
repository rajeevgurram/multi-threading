package com.multithreading;

/**
 * Hello world!
 *
 */
public class HelloWorld 
{
    public static void main( String[] args ) throws InterruptedException
    {
        System.out.println("Running from the Thread: " + Thread.currentThread().getName());     
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Running from the Thread: " + Thread.currentThread().getName());     
            }
            
        });

        thread.start();
        thread.join();
        System.out.println("End of everything");
    }
}
