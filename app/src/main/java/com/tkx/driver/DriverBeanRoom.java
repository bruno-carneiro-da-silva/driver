package com.tkx.driver;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "driver_bean")
public class DriverBeanRoom {
        @PrimaryKey(autoGenerate = true)
        private int id;
        private String merchant_id;
        private String unique_number;
        private String driver_gender;
        private String fullName;
        private String email;
        private String password;
        private String home_location_active;
        private String pool_ride_active;
        private String status_for_pool;
        private int avail_seats;
        private int occupied_seats;
        private boolean pick_exceed;
        private String pool_user_id;
        private String phoneNumber;
        private String profile_image;
        private double wallet_money;
        private int total_trips;
        private double total_earnings;
        private double total_comany_earning;
        private double outstand_amount;
        private String current_latitude;
        private String current_longitude;
        private String last_location_update_time;
        private String bearing;
        private String accuracy;
        private String player_id;
        private String rating;
        private String country_area_id;
        private String login_logout;
        private String online_offline;
        private String free_busy;
        private String bank_name;
        private String account_holder_name;
        private String account_number;
        private String driver_verify_status;
        private String signupFrom;
        private String signupStep;
        private String driver_verification_date;
        private String driver_admin_status;
        private String access_token_id;
        private String driver_delete;
        private String created_at;
        private String updated_at;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getUnique_number() {
        return unique_number;
    }

    public void setUnique_number(String unique_number) {
        this.unique_number = unique_number;
    }

    public String getDriver_gender() {
        return driver_gender;
    }

    public void setDriver_gender(String driver_gender) {
        this.driver_gender = driver_gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHome_location_active() {
        return home_location_active;
    }

    public void setHome_location_active(String home_location_active) {
        this.home_location_active = home_location_active;
    }

    public String getPool_ride_active() {
        return pool_ride_active;
    }

    public void setPool_ride_active(String pool_ride_active) {
        this.pool_ride_active = pool_ride_active;
    }

    public String getStatus_for_pool() {
        return status_for_pool;
    }

    public void setStatus_for_pool(String status_for_pool) {
        this.status_for_pool = status_for_pool;
    }

    public int getAvail_seats() {
        return avail_seats;
    }

    public void setAvail_seats(int avail_seats) {
        this.avail_seats = avail_seats;
    }

    public int getOccupied_seats() {
        return occupied_seats;
    }

    public void setOccupied_seats(int occupied_seats) {
        this.occupied_seats = occupied_seats;
    }

    public boolean isPick_exceed() {
        return pick_exceed;
    }

    public void setPick_exceed(boolean pick_exceed) {
        this.pick_exceed = pick_exceed;
    }

    public String getPool_user_id() {
        return pool_user_id;
    }

    public void setPool_user_id(String pool_user_id) {
        this.pool_user_id = pool_user_id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public double getWallet_money() {
        return wallet_money;
    }

    public void setWallet_money(double wallet_money) {
        this.wallet_money = wallet_money;
    }

    public int getTotal_trips() {
        return total_trips;
    }

    public void setTotal_trips(int total_trips) {
        this.total_trips = total_trips;
    }

    public double getTotal_earnings() {
        return total_earnings;
    }

    public void setTotal_earnings(double total_earnings) {
        this.total_earnings = total_earnings;
    }

    public double getTotal_comany_earning() {
        return total_comany_earning;
    }

    public void setTotal_comany_earning(double total_comany_earning) {
        this.total_comany_earning = total_comany_earning;
    }

    public double getOutstand_amount() {
        return outstand_amount;
    }

    public void setOutstand_amount(double outstand_amount) {
        this.outstand_amount = outstand_amount;
    }

    public String getCurrent_latitude() {
        return current_latitude;
    }

    public void setCurrent_latitude(String current_latitude) {
        this.current_latitude = current_latitude;
    }

    public String getCurrent_longitude() {
        return current_longitude;
    }

    public void setCurrent_longitude(String current_longitude) {
        this.current_longitude = current_longitude;
    }

    public String getLast_location_update_time() {
        return last_location_update_time;
    }

    public void setLast_location_update_time(String last_location_update_time) {
        this.last_location_update_time = last_location_update_time;
    }

    public String getBearing() {
        return bearing;
    }

    public void setBearing(String bearing) {
        this.bearing = bearing;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(String player_id) {
        this.player_id = player_id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCountry_area_id() {
        return country_area_id;
    }

    public void setCountry_area_id(String country_area_id) {
        this.country_area_id = country_area_id;
    }

    public String getLogin_logout() {
        return login_logout;
    }

    public void setLogin_logout(String login_logout) {
        this.login_logout = login_logout;
    }

    public String getOnline_offline() {
        return online_offline;
    }

    public void setOnline_offline(String online_offline) {
        this.online_offline = online_offline;
    }

    public String getFree_busy() {
        return free_busy;
    }

    public void setFree_busy(String free_busy) {
        this.free_busy = free_busy;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getAccount_holder_name() {
        return account_holder_name;
    }

    public void setAccount_holder_name(String account_holder_name) {
        this.account_holder_name = account_holder_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getDriver_verify_status() {
        return driver_verify_status;
    }

    public void setDriver_verify_status(String driver_verify_status) {
        this.driver_verify_status = driver_verify_status;
    }

    public String getSignupFrom() {
        return signupFrom;
    }

    public void setSignupFrom(String signupFrom) {
        this.signupFrom = signupFrom;
    }

    public String getSignupStep() {
        return signupStep;
    }

    public void setSignupStep(String signupStep) {
        this.signupStep = signupStep;
    }

    public String getDriver_verification_date() {
        return driver_verification_date;
    }

    public void setDriver_verification_date(String driver_verification_date) {
        this.driver_verification_date = driver_verification_date;
    }

    public String getDriver_admin_status() {
        return driver_admin_status;
    }

    public void setDriver_admin_status(String driver_admin_status) {
        this.driver_admin_status = driver_admin_status;
    }

    public String getAccess_token_id() {
        return access_token_id;
    }

    public void setAccess_token_id(String access_token_id) {
        this.access_token_id = access_token_id;
    }

    public String getDriver_delete() {
        return driver_delete;
    }

    public void setDriver_delete(String driver_delete) {
        this.driver_delete = driver_delete;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

}
