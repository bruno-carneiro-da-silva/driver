package com.tkx.driver;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {
        User.class,
        TripDetails.class,
        TripDataStatus.class,
        AppData.class,
        DataBeanRoom.class,
        DriverBeanRoom.class,
        DatabeanTripDetailsSchedule.class,
        LoginDetails.class
}, version = 1, exportSchema = false)
@TypeConverters(DataBeanConverters.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract TripDetailsDao tripDetails();
    public abstract TripDataStatusDao tripDataStatusDao();
    public abstract TripDataDao tripDataDao();
    public abstract DataBeanRoomDao dataBeanRoomDao();
    public abstract  DriverBeanRoomDao driverBeanRoomDao();
    public abstract  DatabeanTripDetailsScheduleDao databeanTripDetailsSchedule();
    public abstract LoginDetailsDAO loginDetailsDAO();
}
