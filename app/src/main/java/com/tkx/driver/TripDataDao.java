package com.tkx.driver;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TripDataDao {
    @Insert
    void insert(AppData appData);

    @Query("SELECT * FROM trip_data")
    List<AppData> getAll();

    @Query("SELECT EXISTS(SELECT * FROM trip_data WHERE id = :id)")
    Boolean is_exists(int id);

    @Update
    void update(AppData appData);

    @Delete
    void delete(AppData appData);
}