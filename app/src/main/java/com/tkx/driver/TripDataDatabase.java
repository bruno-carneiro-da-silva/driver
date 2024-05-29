package com.tkx.driver;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TripData.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TripDataDao tripDataDao();
}