package com.tkx.driver.offlineService;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.room.Room;

import com.tkx.driver.AppDatabase;
import com.tkx.driver.DataBeanRoomDao;
import com.tkx.driver.DatabeanTripDetailsScheduleDao;
import com.tkx.driver.DriverBeanRoomDao;
import com.tkx.driver.TripDataDao;
import com.tkx.driver.TripDataStatusDao;
import com.tkx.driver.TripDetailsDao;
import com.tkx.driver.UserDao;

public class OfflineDataService extends Service {
    private final IBinder binder = new LocalBinder();
    private AppDatabase db;
    private static final String TAG = "OfflineDataService";

    public class LocalBinder extends Binder {
        public OfflineDataService getService() {
            return OfflineDataService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "room_db").build();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void handleDataOffline(final Location location) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Log the location details
                Log.d(TAG, "Latitude teste: " + location.getLatitude());
                Log.d(TAG, "Longitude: " + location.getLongitude());
                Log.d(TAG, "Accuracy bruno: " + location.getAccuracy());
                Log.d(TAG, "Provider: " + location.getProvider());
                Log.d(TAG, "Time: " + location.getTime());

                // Here you can add more logging information if needed

                UserDao userDao = db.userDao();
                TripDetailsDao tripDetailsDao = db.tripDetails();
                TripDataStatusDao tripDataStatusDao = db.tripDataStatusDao();
                TripDataDao appDataDao = db.tripDataDao();
                DataBeanRoomDao dataBeanRoomDao = db.dataBeanRoomDao();
                DriverBeanRoomDao driverBeanRoomDao = db.driverBeanRoomDao();
                DatabeanTripDetailsScheduleDao databeanTripDetailsScheduleDao = db.databeanTripDetailsSchedule();

                // Seu código para manipular os dados offline
            }
        }).start();
    }
}
