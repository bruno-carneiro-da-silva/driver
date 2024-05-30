package com.tkx.driver;

import androidx.room.Dao;
import androidx.room.Insert;


@Dao
public interface TripDataStatusDao {
    @Insert
    void insert(TripDataStatus tripDataStatus);

}