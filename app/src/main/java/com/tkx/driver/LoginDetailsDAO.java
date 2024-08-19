package com.tkx.driver;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.tkx.driver.models.ModelLogin;

import java.util.List;

@Dao
public interface LoginDetailsDAO {
    @Insert
    void insert(LoginDetails loginDetails);

    @Update
    void update(LoginDetails loginDetails);

    @Delete
    void delete(LoginDetails loginDetails);

    @Query("SELECT * FROM login WHERE access_token = :accessToken")
    boolean getLoginByAccessToken(String accessToken);

    @Query("SELECT * FROM login")
    List<LoginDetails> getAllLogins();
}
