package ru.notificator.sirius.siriusnotificator;


import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import androidx.work.Worker;

public class MyWorker extends Worker {

    static final String TAG = "workmng";

    @NonNull
    @Override
    public WorkerResult doWork() {
        Log.d(TAG, "doWork: start");

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "doWork: end");

        return Worker.WorkerResult.SUCCESS;
    }
}