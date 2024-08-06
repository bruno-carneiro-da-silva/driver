package com.tkx.driver;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.tkx.driver.models.ModelDriverVehicles;

import java.io.Serializable;

@Entity(tableName = "data_bean")
public class DataBeanRoom implements Serializable {
        @PrimaryKey(autoGenerate = true)
        private int id;
        private String merchant_id;
        private String driver_id;
        private String owner_id;
        private String vehicle_type_id;
        private String shareCode;
        private String vehicle_make_id;
        private String vehicle_model_id;
        private String vehicle_number;
        private String vehicle_color;
        private String vehicle_image;
        private String vehicle_number_plate_image;
        private String vehicle_active_status;
        private String vehicle_verification_status;
        private String reject_reason_id;
        private String created_at;
        private String updated_at;
        private String vehicle_type;
        private String vehicleTypeImage;
        private String vehicleTypeMapImage;
        private String pool_enable;
        private String vehicle_make;
        private String vehicleMakeLogo;
        private String vehicle_model;
        private int ready_for_live;
        private String show_msg;
        private String service_types;

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

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getVehicle_type_id() {
        return vehicle_type_id;
    }

    public void setVehicle_type_id(String vehicle_type_id) {
        this.vehicle_type_id = vehicle_type_id;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public String getVehicle_make_id() {
        return vehicle_make_id;
    }

    public void setVehicle_make_id(String vehicle_make_id) {
        this.vehicle_make_id = vehicle_make_id;
    }

    public String getVehicle_model_id() {
        return vehicle_model_id;
    }

    public void setVehicle_model_id(String vehicle_model_id) {
        this.vehicle_model_id = vehicle_model_id;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public String getVehicle_color() {
        return vehicle_color;
    }

    public void setVehicle_color(String vehicle_color) {
        this.vehicle_color = vehicle_color;
    }

    public String getVehicle_image() {
        return vehicle_image;
    }

    public void setVehicle_image(String vehicle_image) {
        this.vehicle_image = vehicle_image;
    }

    public String getVehicle_number_plate_image() {
        return vehicle_number_plate_image;
    }

    public void setVehicle_number_plate_image(String vehicle_number_plate_image) {
        this.vehicle_number_plate_image = vehicle_number_plate_image;
    }

    public String getVehicle_active_status() {
        return vehicle_active_status;
    }

    public void setVehicle_active_status(String vehicle_active_status) {
        this.vehicle_active_status = vehicle_active_status;
    }

    public String getVehicle_verification_status() {
        return vehicle_verification_status;
    }

    public void setVehicle_verification_status(String vehicle_verification_status) {
        this.vehicle_verification_status = vehicle_verification_status;
    }

    public String getReject_reason_id() {
        return reject_reason_id;
    }

    public void setReject_reason_id(String reject_reason_id) {
        this.reject_reason_id = reject_reason_id;
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

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getVehicleTypeImage() {
        return vehicleTypeImage;
    }

    public void setVehicleTypeImage(String vehicleTypeImage) {
        this.vehicleTypeImage = vehicleTypeImage;
    }

    public String getVehicleTypeMapImage() {
        return vehicleTypeMapImage;
    }

    public void setVehicleTypeMapImage(String vehicleTypeMapImage) {
        this.vehicleTypeMapImage = vehicleTypeMapImage;
    }

    public String getPool_enable() {
        return pool_enable;
    }

    public void setPool_enable(String pool_enable) {
        this.pool_enable = pool_enable;
    }

    public String getVehicle_make() {
        return vehicle_make;
    }

    public void setVehicle_make(String vehicle_make) {
        this.vehicle_make = vehicle_make;
    }

    public String getVehicleMakeLogo() {
        return vehicleMakeLogo;
    }

    public void setVehicleMakeLogo(String vehicleMakeLogo) {
        this.vehicleMakeLogo = vehicleMakeLogo;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public void setVehicle_model(String vehicle_model) {
        this.vehicle_model = vehicle_model;
    }

    public int getReady_for_live() {
        return ready_for_live;
    }

    public void setReady_for_live(int ready_for_live) {
        this.ready_for_live = ready_for_live;
    }

    public String getShow_msg() {
        return show_msg;
    }

    public void setShow_msg(String show_msg) {
        this.show_msg = show_msg;
    }

    public String getService_types() {
        return service_types;
    }

    public void setService_types(String service_types) {
        this.service_types = service_types;
    }
}
