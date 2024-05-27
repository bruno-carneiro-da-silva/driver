package com.tkx.driver.firebase;

import java.util.List;

public class ModelNewNoti {


    /**
     * data : {"service_type_id":1,"multiple_text":"multipleStop","drop_location":"13, Malibu Town, Sector 47, Gurugram, Haryana 122018, India","delivery_type_id":"","vehicle":"SEDAN","booking_id":1839,"additional_notes":"","driver_request_timeout":40,"estimate_distance":"2.6 km","estimate_time":"10 mins","pickup_latitude":"28.412354437452052","drop_latitude":"28.423206156819635","multiple_visable":false,"drop_longitude":"77.04648848623037","email":"hsjs@hsn.com","payment_method":"CASH","timestamp":1575037954,"multiple_stop_list":[],"user_rating":"0.0","ride_id":35,"vehicleimage":"https://s3.ap-south-1.amazonaws.com/apporiotaxi-bucket/apporio-taxi/vehicle/1566803985_5d63881133ffb_vehicle.png","pickup_longitude":"77.04407215118408","pickup_location":"Tower B, Spaze Itech Park 22A, Ground Floor, Tower, B2, Sohna Rd, Sector 49, Gurugram, Haryana 122018, India","user_image":"https://s3.ap-south-1.amazonaws.com/apporiotaxi-bucket/static-images/no-image.png","booking_status":1001,"packages":[],"outstation_msg":" There Is New Upcomming Booking","phone":"+916666666666","service":"Normal","estimate_bill":"3","package_name":"","booking_type":"1","username":"Visbsb Bshs"}
     * type : 1
     * title : New Booking
     */

    private DataBean data;
    private int type;
    private String title;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class DataBean {
        /**
         * service_type_id : 1
         * multiple_text : multipleStop
         * drop_location : 13, Malibu Town, Sector 47, Gurugram, Haryana 122018, India
         * delivery_type_id :
         * vehicle : SEDAN
         * booking_id : 1839
         * additional_notes :
         * driver_request_timeout : 40
         * estimate_distance : 2.6 km
         * estimate_time : 10 mins
         * pickup_latitude : 28.412354437452052
         * drop_latitude : 28.423206156819635
         * multiple_visable : false
         * drop_longitude : 77.04648848623037
         * email : hsjs@hsn.com
         * payment_method : CASH
         * timestamp : 1575037954
         * multiple_stop_list : []
         * user_rating : 0.0
         * ride_id : 35
         * vehicleimage : https://s3.ap-south-1.amazonaws.com/apporiotaxi-bucket/apporio-taxi/vehicle/1566803985_5d63881133ffb_vehicle.png
         * pickup_longitude : 77.04407215118408
         * pickup_location : Tower B, Spaze Itech Park 22A, Ground Floor, Tower, B2, Sohna Rd, Sector 49, Gurugram, Haryana 122018, India
         * user_image : https://s3.ap-south-1.amazonaws.com/apporiotaxi-bucket/static-images/no-image.png
         * booking_status : 1001
         * packages : []
         * outstation_msg :  There Is New Upcomming Booking
         * phone : +916666666666
         * service : Normal
         * estimate_bill : 3
         * package_name :
         * booking_type : 1
         * username : Visbsb Bshs
         */

        private int service_type_id;
        private String multiple_text;
        private String drop_location;
        private String delivery_type_id;
        private String vehicle;
        private int booking_id;
        private String additional_notes;
        private int driver_request_timeout;
        private String estimate_distance;
        private String estimate_time;
        private String pickup_latitude;
        private String drop_latitude;
        private boolean multiple_visable;
        private String drop_longitude;
        private String email;
        private String payment_method;
        private int timestamp;
        private String user_rating;
        private int ride_id;
        private String vehicleimage;
        private String pickup_longitude;
        private String pickup_location;
        private String user_image;
        private int booking_status;
        private String outstation_msg;
        private String phone;
        private String service;
        private String estimate_bill;
        private String package_name;
        private String booking_type;
        private String username;
        private List<?> multiple_stop_list;
        private List<?> packages;

        public int getService_type_id() {
            return service_type_id;
        }

        public void setService_type_id(int service_type_id) {
            this.service_type_id = service_type_id;
        }

        public String getMultiple_text() {
            return multiple_text;
        }

        public void setMultiple_text(String multiple_text) {
            this.multiple_text = multiple_text;
        }

        public String getDrop_location() {
            return drop_location;
        }

        public void setDrop_location(String drop_location) {
            this.drop_location = drop_location;
        }

        public String getDelivery_type_id() {
            return delivery_type_id;
        }

        public void setDelivery_type_id(String delivery_type_id) {
            this.delivery_type_id = delivery_type_id;
        }

        public String getVehicle() {
            return vehicle;
        }

        public void setVehicle(String vehicle) {
            this.vehicle = vehicle;
        }

        public int getBooking_id() {
            return booking_id;
        }

        public void setBooking_id(int booking_id) {
            this.booking_id = booking_id;
        }

        public String getAdditional_notes() {
            return additional_notes;
        }

        public void setAdditional_notes(String additional_notes) {
            this.additional_notes = additional_notes;
        }

        public int getDriver_request_timeout() {
            return driver_request_timeout;
        }

        public void setDriver_request_timeout(int driver_request_timeout) {
            this.driver_request_timeout = driver_request_timeout;
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

        public String getPickup_latitude() {
            return pickup_latitude;
        }

        public void setPickup_latitude(String pickup_latitude) {
            this.pickup_latitude = pickup_latitude;
        }

        public String getDrop_latitude() {
            return drop_latitude;
        }

        public void setDrop_latitude(String drop_latitude) {
            this.drop_latitude = drop_latitude;
        }

        public boolean isMultiple_visable() {
            return multiple_visable;
        }

        public void setMultiple_visable(boolean multiple_visable) {
            this.multiple_visable = multiple_visable;
        }

        public String getDrop_longitude() {
            return drop_longitude;
        }

        public void setDrop_longitude(String drop_longitude) {
            this.drop_longitude = drop_longitude;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPayment_method() {
            return payment_method;
        }

        public void setPayment_method(String payment_method) {
            this.payment_method = payment_method;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getUser_rating() {
            return user_rating;
        }

        public void setUser_rating(String user_rating) {
            this.user_rating = user_rating;
        }

        public int getRide_id() {
            return ride_id;
        }

        public void setRide_id(int ride_id) {
            this.ride_id = ride_id;
        }

        public String getVehicleimage() {
            return vehicleimage;
        }

        public void setVehicleimage(String vehicleimage) {
            this.vehicleimage = vehicleimage;
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

        public String getUser_image() {
            return user_image;
        }

        public void setUser_image(String user_image) {
            this.user_image = user_image;
        }

        public int getBooking_status() {
            return booking_status;
        }

        public void setBooking_status(int booking_status) {
            this.booking_status = booking_status;
        }

        public String getOutstation_msg() {
            return outstation_msg;
        }

        public void setOutstation_msg(String outstation_msg) {
            this.outstation_msg = outstation_msg;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getEstimate_bill() {
            return estimate_bill;
        }

        public void setEstimate_bill(String estimate_bill) {
            this.estimate_bill = estimate_bill;
        }

        public String getPackage_name() {
            return package_name;
        }

        public void setPackage_name(String package_name) {
            this.package_name = package_name;
        }

        public String getBooking_type() {
            return booking_type;
        }

        public void setBooking_type(String booking_type) {
            this.booking_type = booking_type;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<?> getMultiple_stop_list() {
            return multiple_stop_list;
        }

        public void setMultiple_stop_list(List<?> multiple_stop_list) {
            this.multiple_stop_list = multiple_stop_list;
        }

        public List<?> getPackages() {
            return packages;
        }

        public void setPackages(List<?> packages) {
            this.packages = packages;
        }
    }
}
