package com.tkx.driver;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TripDataStatus.class}, version = 1, exportSchema = false)
public abstract class AppTripStatusDatabase extends RoomDatabase {
    public abstract TripDataStatusDao tripDataStatusDao();
}