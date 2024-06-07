package com.tkx.driver;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface TripDataStatusDao {
    @Insert
    void insert(TripDataStatus tripDataStatus);

    @Query("SELECT * FROM trip_data_status")
    List<TripDataStatus> getAll();

    @Query("SELECT EXISTS(SELECT * FROM trip_data_status WHERE id = :id)")
    Boolean is_exists(int id);

    @Update
    void update(TripDataStatus tripDataStatus);

    @Delete
    void delete(TripDataStatus tripDataStatus);
}