package com.tkx.driver.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SyncData extends Worker {
    public SyncData(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        try {
            Log.e("TAG", "Worker started blur");
            return Result.success();
        }catch (Throwable throwable){
            Log.e("TAG", "Worker started blur", throwable);
            return Result.failure();
        }
    }
}
