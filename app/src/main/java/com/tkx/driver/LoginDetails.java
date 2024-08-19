package com.tkx.driver;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "login")
public class LoginDetails {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String access_token;
    private String refresh_token;
    private boolean taxi_company;
    private boolean fire_base;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public boolean isTaxi_company() {
        return taxi_company;
    }

    public void setTaxi_company(boolean taxi_company) {
        this.taxi_company = taxi_company;
    }

    public boolean isFire_base() {
        return fire_base;
    }

    public void setFire_base(boolean fire_base) {
        this.fire_base = fire_base;
    }
}
