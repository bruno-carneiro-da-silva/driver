package com.tkx.driver;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TripDetails.class}, version = 1, exportSchema = false)
public abstract class AppTripDetailsDatabase extends RoomDatabase {
    public abstract TripDetailsDao tripDetailsDao();
}