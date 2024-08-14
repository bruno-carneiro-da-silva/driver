package com.tkx.driver;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trip_data")
public class AppData {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int merchant_id;
    public int merchant_booking_id;
    public int vehicle_type_id;
    public int country_area_id;
    public int price_card_id;
    public String ride_otp;
    public int ride_otp_verify;
    public int total_drop_location;
    public int service_type_id;
    public String ploy_points;
    public int driver_id;
    public int booking_status;
    public String pickup_latitude;
    public String pickup_longitude;
    public String pickup_location;
    public String drop_latitude;
    public String drop_longitude;
    public String drop_location;
    public String map_image;
    private String estimate_distance;
    private String estimate_time;
    public String additional_notes;
    public int family_member_id;
    public int onride_waiting_type;
    public int delivery_type_id;
    public boolean send_meter_image;
    public boolean send_meter_value;
    public boolean sos_visibility;
    public boolean onride_pause_button;
    public boolean cancelable;
    // PolyData fields
    public String polyline_width;
    public String polyline_color;
    public String polyline;
    // StillMarker fields
    public String marker_type;
    public String marker_lat;
    public String marker_long;
    private String bill_details;
    private String created_at;
    private String updated_at;
    // Continue com os outros campos...

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

    public int getMerchant_booking_id() {
        return merchant_booking_id;
    }

    public void setMerchant_booking_id(int merchant_booking_id) {
        this.merchant_booking_id = merchant_booking_id;
    }

    public int getVehicle_type_id() {
        return vehicle_type_id;
    }

    public void setVehicle_type_id(int vehicle_type_id) {
        this.vehicle_type_id = vehicle_type_id;
    }

    public int getCountry_area_id() {
        return country_area_id;
    }

    public void setCountry_area_id(int country_area_id) {
        this.country_area_id = country_area_id;
    }

    public int getPrice_card_id() {
        return price_card_id;
    }

    public void setPrice_card_id(int price_card_id) {
        this.price_card_id = price_card_id;
    }

    public String getRide_otp() {
        return ride_otp;
    }

    public void setRide_otp(String ride_otp) {
        this.ride_otp = ride_otp;
    }

    public int getRide_otp_verify() {
        return ride_otp_verify;
    }

    public void setRide_otp_verify(int ride_otp_verify) {
        this.ride_otp_verify = ride_otp_verify;
    }

    public int getTotal_drop_location() {
        return total_drop_location;
    }

    public void setTotal_drop_location(int total_drop_location) {
        this.total_drop_location = total_drop_location;
    }

    public int getService_type_id() {
        return service_type_id;
    }

    public void setService_type_id(int service_type_id) {
        this.service_type_id = service_type_id;
    }

    public String getPloy_points() {
        return ploy_points;
    }

    public void setPloy_points(String ploy_points) {
        this.ploy_points = ploy_points;
    }

    public int getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(int driver_id) {
        this.driver_id = driver_id;
    }

    public int getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(int booking_status) {
        this.booking_status = booking_status;
    }

    public String getPickup_latitude() {
        return pickup_latitude;
    }

    public void setPickup_latitude(String pickup_latitude) {
        this.pickup_latitude = pickup_latitude;
    }

    public String getPickup_longitude() {
        return pickup_longitude;
    }

    public void setPickup_longitude(String pickup_longitude) {
        this.pickup_longitude = pickup_longitude;
    }

    public String getPickup_location() {
        return pickup_location;
    }

    public void setPickup_location(String pickup_location) {
        this.pickup_location = pickup_location;
    }

    public String getDrop_latitude() {
        return drop_latitude;
    }

    public void setDrop_latitude(String drop_latitude) {
        this.drop_latitude = drop_latitude;
    }

    public String getDrop_longitude() {
        return drop_longitude;
    }

    public void setDrop_longitude(String drop_longitude) {
        this.drop_longitude = drop_longitude;
    }

    public String getDrop_location() {
        return drop_location;
    }

    public void setDrop_location(String drop_location) {
        this.drop_location = drop_location;
    }

    public String getMap_image() {
        return map_image;
    }

    public void setMap_image(String map_image) {
        this.map_image = map_image;
    }

    public String getEstimate_distance() {
        return estimate_distance;
    }

    public void setEstimate_distance(String estimate_distance) {
        this.estimate_distance = estimate_distance;
    }
    public String getEstimate_time() {
        return estimate_time;
    }

    public void setEstimate_time(String estimate_time) {
        this.estimate_time = estimate_time;
    }

    public String getAdditional_notes() {
        return additional_notes;
    }

    public void setAdditional_notes(String additional_notes) {
        this.additional_notes = additional_notes;
    }

    public int getFamily_member_id() {
        return family_member_id;
    }

    public void setFamily_member_id(int family_member_id) {
        this.family_member_id = family_member_id;
    }

    public int getOnride_waiting_type() {
        return onride_waiting_type;
    }

    public void setOnride_waiting_type(int onride_waiting_type) {
        this.onride_waiting_type = onride_waiting_type;
    }

    public int getDelivery_type_id() {
        return delivery_type_id;
    }

    public void setDelivery_type_id(int delivery_type_id) {
        this.delivery_type_id = delivery_type_id;
    }

    public boolean isSend_meter_image() {
        return send_meter_image;
    }

    public void setSend_meter_image(boolean send_meter_image) {
        this.send_meter_image = send_meter_image;
    }

    public boolean isSend_meter_value() {
        return send_meter_value;
    }

    public void setSend_meter_value(boolean send_meter_value) {
        this.send_meter_value = send_meter_value;
    }

    public boolean isSos_visibility() {
        return sos_visibility;
    }

    public void setSos_visibility(boolean sos_visibility) {
        this.sos_visibility = sos_visibility;
    }

    public boolean isOnride_pause_button() {
        return onride_pause_button;
    }

    public void setOnride_pause_button(boolean onride_pause_button) {
        this.onride_pause_button = onride_pause_button;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public String getPolyline_width() {
        return polyline_width;
    }

    public void setPolyline_width(String polyline_width) {
        this.polyline_width = polyline_width;
    }

    public String getPolyline_color() {
        return polyline_color;
    }

    public void setPolyline_color(String polyline_color) {
        this.polyline_color = polyline_color;
    }

    public String getPolyline() {
        return polyline;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }

    public String getMarker_type() {
        return marker_type;
    }

    public void setMarker_type(String marker_type) {
        this.marker_type = marker_type;
    }

    public String getMarker_lat() {
        return marker_lat;
    }

    public void setMarker_lat(String marker_lat) {
        this.marker_lat = marker_lat;
    }

    public String getMarker_long() {
        return marker_long;
    }

    public void setMarker_long(String marker_long) {
        this.marker_long = marker_long;
    }

    public String getBill_details() {
        return bill_details;
    }

    public void setBill_details(String bill_details) {
        this.bill_details = bill_details;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

}