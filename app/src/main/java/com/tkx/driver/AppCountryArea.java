package com.tkx.driver;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "country_areas")
public class AppCountryArea {
    @PrimaryKey
    public int id;
    public int merchant_id;
    public int country_id;
    public int is_geofence;
    public int auto_upgradetion;
    public String timezone;
    public String minimum_wallet_amount;
    public int pool_postion;
    public int status;
    public int driver_earning_duration;
    public String manual_toll_price;
    public String created_at;
    public String updated_at;
    public String AreaName;

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public int getIs_geofence() {
        return is_geofence;
    }

    public void setIs_geofence(int is_geofence) {
        this.is_geofence = is_geofence;
    }

    public int getAuto_upgradetion() {
        return auto_upgradetion;
    }

    public void setAuto_upgradetion(int auto_upgradetion) {
        this.auto_upgradetion = auto_upgradetion;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getMinimum_wallet_amount() {
        return minimum_wallet_amount;
    }

    public void setMinimum_wallet_amount(String minimum_wallet_amount) {
        this.minimum_wallet_amount = minimum_wallet_amount;
    }

    public int getPool_postion() {
        return pool_postion;
    }

    public void setPool_postion(int pool_postion) {
        this.pool_postion = pool_postion;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDriver_earning_duration() {
        return driver_earning_duration;
    }

    public void setDriver_earning_duration(int driver_earning_duration) {
        this.driver_earning_duration = driver_earning_duration;
    }

    public String getManual_toll_price() {
        return manual_toll_price;
    }

    public void setManual_toll_price(String manual_toll_price) {
        this.manual_toll_price = manual_toll_price;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }
}
