package br.com.alura.schedule.services;

import java.util.concurrent.ExecutorService;

public class ExecutorServiceSingleton {
    private static ExecutorServiceSingleton instance = null;
    private static ExecutorService executor = null;

    private ExecutorServiceSingleton() {
    }

    public static ExecutorServiceSingleton getInstance() {
        if (instance == null) {
            instance = new ExecutorServiceSingleton();
        }
        return instance;
    }

    public static void setExecutorService(ExecutorService executorService) {
        executor = executorService;
    }

    public ExecutorService getExecutorService() {
        return executor;
    }
}
