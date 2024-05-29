package com.tkx.driver;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface TripDetailsDao {
    @Insert
    void insert(TripDetails tripDetails);
}

