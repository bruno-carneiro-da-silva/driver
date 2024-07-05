package com.tkx.driver;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
@Dao
public interface DataBeanRoomDao {
        @Insert
        void insert(DataBeanRoom dataBeanRooms);

        @Update
        void update(DataBeanRoom dataBeanRoom);

        @Delete
        void delete(DataBeanRoom dataBeanRoom);

        @Query("SELECT * FROM data_bean")
        List<DataBeanRoom> getAllDataBeans();

        @Query("SELECT EXISTS(SELECT * FROM data_bean WHERE id = :id)")
        DataBeanRoom getDataBeanById(int id);

}
