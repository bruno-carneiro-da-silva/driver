package com.tkx.driver.offlineService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.room.Room;

import com.tkx.driver.AppDatabase;
import com.tkx.driver.DataBeanRoom;
import com.tkx.driver.DataBeanRoomDao;
import com.tkx.driver.DatabeanTripDetailsSchedule;
import com.tkx.driver.DatabeanTripDetailsScheduleDao;
import com.tkx.driver.DriverBeanRoom;
import com.tkx.driver.DriverBeanRoomDao;
import com.tkx.driver.TripDataDao;
import com.tkx.driver.TripDataStatusDao;
import com.tkx.driver.TripDetails;
import com.tkx.driver.TripDetailsDao;
import com.tkx.driver.User;
import com.tkx.driver.UserDao;
import com.tkx.driver.database.DatabaseClient;

public class OfflineDataService extends Service {
    private AppDatabase db;
    private UserDao userDao;
    private TripDetailsDao tripDetailsDao;
    private TripDataStatusDao tripDataStatusDao;
    private TripDataDao appDataDao;
    private DataBeanRoomDao dataBeanRoomDao;
    private DriverBeanRoomDao driverBeanRoomDao;
    private DatabeanTripDetailsScheduleDao databeanTripDetailsScheduleDao;

    @Override
    public void onCreate() {
        super.onCreate();
        db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
        tripDetailsDao = db.tripDetails();
        tripDataStatusDao = db.tripDataStatusDao();
        appDataDao = db.tripDataDao();
        dataBeanRoomDao = db.dataBeanRoomDao();
        driverBeanRoomDao = db.driverBeanRoomDao();
        databeanTripDetailsScheduleDao = db.databeanTripDetailsSchedule();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleDataOffline();
        return START_NOT_STICKY;
    }

    public void handleDataOffline() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Implement your offline data handling logic here
            }
        }).start();
    }

    public void insertUser(final User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                userDao.insertrecord(user);
            }
        }).start();
    }

    public void insertTripDetails(final TripDetails tripDetails) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                tripDetailsDao.insert(tripDetails);
            }
        }).start();
    }

    public void saveDriverBean(final DriverBeanRoom driverBeanRoom) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean existingDriverBean = driverBeanRoomDao.existsById(driverBeanRoom.getId());
                if (existingDriverBean) {
                    driverBeanRoomDao.update(driverBeanRoom);
                } else {
                    driverBeanRoomDao.insert(driverBeanRoom);
                }
            }
        }).start();
    }

    public void saveTripDetailsSchedule(final DatabeanTripDetailsSchedule dataBeanRoom) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (databeanTripDetailsScheduleDao) {
                    if (databeanTripDetailsScheduleDao.existsById(dataBeanRoom.getBooking_id())) {
                        databeanTripDetailsScheduleDao.update(dataBeanRoom);
                    } else {
                        databeanTripDetailsScheduleDao.insert(dataBeanRoom);
                    }
                }
            }
        }).start();
    }
}
