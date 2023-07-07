package br.com.alura.schedule;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.alura.schedule.services.ExecutorServiceSingleton;

public class ScheduleApplication extends Application {
    ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    public void onCreate() {
        super.onCreate();
        ExecutorServiceSingleton.setExecutorService(executorService);
    }
}
