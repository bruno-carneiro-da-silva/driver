//package com.tkx.driver.network;
//
//import android.content.Context;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.util.Log;
//
//import androidx.work.Worker;
//import androidx.work.WorkerParameters;
//
//public class NetworkWorker extends Worker {
//    private static final String TAG = "NetworkWorker";
//
//    public NetworkWorker(
//            @NonNull Context context,
//            @NonNull WorkerParameters params) {
//        super(context, params);
//    }
//
//    @Override
//    public Result doWork() {
//        Log.d(TAG, "doWork called");
//        if (isNetworkAvailable(getApplicationContext())) {
//            // Network is available, process queued events
//            Log.d(TAG, "Network is available, processing queued events.");
//            EventQueueManager.processQueue();
//            return Result.success();
//        } else {
//            Log.d(TAG, "Network is not available.");
//            return Result.retry();
//        }
//    }
//
//    private boolean isNetworkAvailable(Context context) {
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
//    }
//}