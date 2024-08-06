package com.tkx.driver.database;
import android.annotation.SuppressLint;
import android.content.Context;
import androidx.room.Room;
import com.tkx.driver.AppDatabase;

public class DatabaseClient {
    @SuppressLint("StaticFieldLeak")
    private static DatabaseClient instance;

    private final AppDatabase appDatabase;

    private DatabaseClient(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "room_db").build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
