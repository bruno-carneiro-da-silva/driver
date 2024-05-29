package com.tkx.driver;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TripDetails.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TripDetailsDao tripDetailsDao();
}