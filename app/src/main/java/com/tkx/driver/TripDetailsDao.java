package com.tkx.driver;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TripDetailsDao {
    @Insert
    void insert(TripDetails tripDetails);

    @Query("SELECT * FROM trip_details")
    List<TripDetails> getAll();

    @Query("SELECT EXISTS(SELECT 1 FROM trip_details WHERE pick_text = :pickText)")
    Boolean existsByPickText(String pickText);


    @Update
    void update(TripDetails tripDetails);

    @Delete
    void delete(TripDetails tripDetails);
}

