package com.tkx.driver;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DatabeanTripDetailsScheduleDao {
    @Insert
    void insert(DatabeanTripDetailsSchedule databeanTripDetailsSchedule);

    @Update
    void update(DatabeanTripDetailsSchedule databeanTripDetailsSchedule);

    @Delete
    void delete(DatabeanTripDetailsSchedule databeanTripDetailsSchedule);

    @Query("SELECT * FROM room_schedule_trip")
    List<DatabeanTripDetailsSchedule> getAllDataBeans();

    @Query("SELECT EXISTS(SELECT 1 FROM room_schedule_trip WHERE booking_id = :bookingId)")
    boolean getById(String bookingId);
}