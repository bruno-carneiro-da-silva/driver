import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@@Entity(tableName = "trip_data_status")
public class TripDataStatus {
    @PrimaryKey
    public int id;
    public String result;
    public String message;
    public Data data;
}

public class Data {
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
    public String delivery_type_id;
    public boolean send_meter_image;
    public boolean send_meter_value;
    public boolean sos_visibility;
    public String[] sos;
    public boolean onride_pause_button;
    public PolyData polydata;
    public StillMarker still_marker;
    public MovableMarker movable_marker;
    public Location location;
    public boolean cancelable;
    public FamilyMemberDetails family_member_details;
    public PaymentMethod payment_method;
    public VehicleType vehicle_type;
    public User user;
    public BookingDetail booking_detail;
    public boolean manual_toll_enable;
}

public class PolyData {
    public String polyline_width;
    public String polyline_color;
    public String polyline;
}

public class StillMarker {
    public String marker_type;
    public String marker_lat;
    public String marker_long;
}

public class MovableMarker {
    public String driver_marker_name;
    public String driver_marker_type;
    public String driver_marker_lat;
    public String driver_marker_long;
    public String driver_marker_bearing;
}

public class Location {
    public String location_message;
    public boolean location_action;
    public String trip_status_text;
    public String location_headline;
    public String location_text;
    public String location_color;
    public boolean location_editable;
}

public class FamilyMemberDetails {
    public boolean family_visibility;
}

public class PaymentMethod {
    public int id;
    public String payment_method;
    public int payment_method_type;
    public int payment_settlement;
    public int payment_method_status;
    public String payment_icon;
}

public class VehicleType {
    public int id;
    public int merchant_id;
    public String vehicleTypeImage;
    public String vehicleTypeDeselectImage;
    public String vehicleTypeMapImage;
    public int vehicleTypeRank;
    public int vehicleTypeStatus;
    public int pool_enable;
    public int sequence;
    public String rating;
    public String delivery_type_id;
    public int ride_now;
    public int ride_later;
    public int vehicle_for;
    public String created_at;
    public String updated_at;
}

public class User {
    public int id;
    public int merchant_id;
    public String first_name;
    public String last_name;
    public String UserPhone;
    public String email;
    public String rating;
    public String UserProfileImage;
}

public class BookingDetail {
    public int id;
    public int booking_id;
    public String collection_vicinity;
    public String delivery_vicinity;
    public String delivery_receiver_name;
    public String delivery_receipt_image;
    public String receipt_latitude;
    public String receipt_longitude;
    public String receipt_date;
    public String start_meter_image;
    public String start_meter_value;
    public String end_meter_value;
    public String end_meter_image;
    public String accept_timestamp;
    public String accept_latitude;
    public String accept_longitude;
    public String accuracy_at_accept;
    public String arrive_timestamp;
    public String arrive_latitude;
    public String arrive_longitude;
    public String accuracy_at_arrive;
    public String start_timestamp;
    public String start_latitude;
    public String start_longitude;
    public String start_location;
    public String accuracy_at_start;
    public String dead_milage_distance;
    public String end_timestamp;
    public String end_latitude;
    public String end_longitude;
    public String end_location;
    public String accuracy_at_end;
    public String wait_time;
    public String bill_details;
    public String total_amount;
    public String promo_discount;
    public String wallet_deduct_money;
    public String pending_amount;
    public String tip_amount;
    public String payment_failure;
    public String created_at;
    public String updated_at;
}