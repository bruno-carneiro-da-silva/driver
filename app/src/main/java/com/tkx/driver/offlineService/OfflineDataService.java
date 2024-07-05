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
    public void DataBaseService(){}

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Aqui você pode extrair extras do intent, se necessário.
        handleDataOffline();
        // Se o sistema matar o serviço após o retorno de onStartCommand, ele não recria o serviço,
        // a menos que haja pendências de intents para serem entregues. Isso é adequado para tarefas que
        // devem ser executadas apenas em resposta a uma intenção explícita.
        return START_NOT_STICKY;
    }

    public void handleDataOffline(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "room_db" ).build();
                UserDao userDao = db.userDao();
                TripDetailsDao tripDetailsDao = db.tripDetails();
                TripDataStatusDao tripDataStatusDao = db.tripDataStatusDao();
                TripDataDao appDataDao = db.tripDataDao();
                DataBeanRoomDao dataBeanRoomDao = db.dataBeanRoomDao();
                DriverBeanRoomDao driverBeanRoomDao = db.driverBeanRoomDao();
                DatabeanTripDetailsScheduleDao databeanTripDetailsScheduleDao = db.databeanTripDetailsSchedule();

//                TripDetails tripDetails = new TripDetails(
//
////                )
//                tripDetailsDao.insert(tripDetails);
//                db.close();


            }
        }).start();
    }
}