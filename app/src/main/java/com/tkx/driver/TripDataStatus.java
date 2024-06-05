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

    public TripDataStatus(int id, String result, String message) {
        this.id = id;
        this.result = result;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

