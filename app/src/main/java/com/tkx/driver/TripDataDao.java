package com.tkx.driver;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface TripDataDao {
    @Insert
    void insert(TripData tripData);
}