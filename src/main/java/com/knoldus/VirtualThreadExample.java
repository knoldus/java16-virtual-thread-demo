package com.knoldus;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * VirtualThreadExample class that show how to make virtual thread through
 * newVirtualThreadExecutor() method.
 */
public class VirtualThreadExample {

    public static void main(String[] args) {
        /*
        ExecutorService that runs each task in its own virtual thread.
         */
        try (ExecutorService executor = Executors.newVirtualThreadExecutor()) {
            executor.submit(() -> {
                try {
                    System.out.println(foo().get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
            /* Submits a Runnable task for execution and returns a Future
             representing that task. */
            executor.submit(() -> bar());
            System.out.println("Doing something");
        }

        System.out.println("This is the end");
    }

    /**
     * Create the virtual thread and execute the task.
     *
     * @return future of string.
     */
    static Future<String> foo() {
        try (ExecutorService executor = Executors.newVirtualThreadExecutor()) {
            Callable<String> callableTask = () -> {
                TimeUnit.MILLISECONDS.sleep(30);
                return "Task's execution 1";
            };
            return executor.submit(callableTask);
        }
    }

    /**
     * Create the virtual thread and execute the task.
     */
    static void bar() {
        try (ExecutorService executor = Executors.newVirtualThreadExecutor()) {
            Callable<String> callableTask = () -> {
                TimeUnit.MILLISECONDS.sleep(300);
                System.out.println("Hello");
                return "Task's execution 2";
            };
            executor.submit(callableTask);
        }

    }
}
