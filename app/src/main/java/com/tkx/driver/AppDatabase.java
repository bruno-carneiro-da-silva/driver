package com.tkx.driver;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, TripDetails.class, TripDataStatus.class, AppData.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract TripDetailsDao tripDetails();
    public abstract TripDataStatusDao tripDataStatusDao();
    public abstract TripDataDao tripDataDao();
}
