package com.tkx.driver;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {AppData.class}, version = 1, exportSchema = false)
public abstract class AppDataDatabase extends RoomDatabase {
    public abstract TripDataDao tripDataDao();
}