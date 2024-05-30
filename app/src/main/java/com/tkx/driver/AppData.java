package com.tkx.driver;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trip_data")
public class AppData {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int merchant_id;
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
    public String drop_latitude;
    public String drop_longitude;
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
    // Continue com os outros campos...
}