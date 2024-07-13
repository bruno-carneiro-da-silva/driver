package com.tkx.driver;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface DriverBeanRoomDao {
    @Insert
    void insert(DriverBeanRoom driverBeanRooms);

    @Update
    void update(DriverBeanRoom driverBeanRoom);

    @Delete
    void delete(DriverBeanRoom driverBeanRoom);

    @Query("SELECT * FROM driver_bean")
    List<DriverBeanRoom> getAllDriverBeans();

    @Query("SELECT EXISTS(SELECT * FROM driver_bean WHERE id = :id)")
    DriverBeanRoom getDriverBeanById(int id);
}
