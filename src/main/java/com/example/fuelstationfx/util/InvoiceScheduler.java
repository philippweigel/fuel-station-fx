package com.example.fuelstationfx.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InvoiceScheduler {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        scheduler.scheduleAtFixedRate(command, initialDelay, period, unit);
    }
}
