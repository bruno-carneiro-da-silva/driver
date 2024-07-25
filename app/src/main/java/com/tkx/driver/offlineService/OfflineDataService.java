package com.tkx.driver.offlineService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.room.Room;

import com.tkx.driver.AppDatabase;
import com.tkx.driver.DataBeanRoom;
import com.tkx.driver.DataBeanRoomDao;
import com.tkx.driver.DatabeanTripDetailsScheduleDao;
import com.tkx.driver.DriverBeanRoom;
import com.tkx.driver.DriverBeanRoomDao;
import com.tkx.driver.TripDataDao;
import com.tkx.driver.TripDataStatusDao;
import com.tkx.driver.TripDetails;
import com.tkx.driver.TripDetailsDao;
import com.tkx.driver.UserDao;

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
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "room_db").build();
        userDao = db.userDao();
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
                // Exemplo de inserção de dados
                User user = new User();
                user.uid = intent.getStringExtra("uid");
                user.firstName = "John";
                user.lastName = "Doe";
                userDao.insertrecord(user);

                // Exemplo de recuperação de dados
                User retrievedUser = userDao.getAllUsers().get(0);
                Log.d("OfflineDataService", "User: " + retrievedUser.firstName + " " + retrievedUser.lastName);

                // Outras operações de banco de dados...
            }
        }).start();
    }

    // Métodos públicos para operações de banco de dados
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

    // Adicione mais métodos conforme necessário para outras operações de banco de dados
}