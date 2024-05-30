package com.tkx.driver;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "trip_data_status")
public class TripDataStatus {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String result;
    public String message;
}

