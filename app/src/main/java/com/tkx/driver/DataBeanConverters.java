package com.tkx.driver;
import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DataBeanConverters {
    private static Gson gson = new Gson();

    @TypeConverter
    public static String fromDataBeanRoomToString(DataBeanRoom dataBeanRoom) {
        return gson.toJson(dataBeanRoom);
    }

    @TypeConverter
    public static DataBeanRoom fromStringToDataBeanRoom(String data) {
        return gson.fromJson(data, DataBeanRoom.class);
    }

    @TypeConverter
    public static String fromDriverBeanRoomToString(DriverBeanRoom driverBeanRoom) {
        return gson.toJson(driverBeanRoom);
    }

    @TypeConverter
    public static DriverBeanRoom fromStringToDriverBeanRoom(String data) {
        return gson.fromJson(data, DriverBeanRoom.class);
    }
}
