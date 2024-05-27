package com.tkx.driver.models;

public class ModelRegister {


    /**
     * result : 1
     * message : Signup Sucessfully
     * data : {"merchant_id":1,"first_name":"Dhdhh","last_name":"Djjdj","email":"jdjd@jdjd.com","phoneNumber":"+919226626666","country_area_id":"1","driver_gender":null,"password":"$2y$10$WEy0AOIfwLDVe7153QrjW./I/jNgN5VAisQDFa4ktHkxx5KvxLWL6","profile_image":"https://s3.ap-south-1.amazonaws.com/apporiotaxi-bucket/apporio-taxi/driver/1580119970_5e2eb7a2169fd_driver.jpg","signupStep":1,"player_id":"41f3efbd-505c-47a6-b058-bc1df606dc8e","last_ride_request_timestamp":"2020-01-27 10:12:50","driver_referralcode":"S6AYQ","dob":null,"driver_cpf_number":null,"driver_address":null,"subscription_wise_commission":"2","driver_additional_data":null,"wallet_money":0,"network_code":null,"merchant_driver_id":2865,"updated_at":"2020-01-27 10:12:50","created_at":"2020-01-27 10:12:50","id":3131,"referred_by":null,"smoker_type":"","allow_other_smoker":"","push_notification":{"fire_base":true}}
     */

    private String result;
    private String message;
    private DataBean data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * merchant_id : 1
         * first_name : Dhdhh
         * last_name : Djjdj
         * email : jdjd@jdjd.com
         * phoneNumber : +919226626666
         * country_area_id : 1
         * driver_gender : null
         * password : $2y$10$WEy0AOIfwLDVe7153QrjW./I/jNgN5VAisQDFa4ktHkxx5KvxLWL6
         * profile_image : https://s3.ap-south-1.amazonaws.com/apporiotaxi-bucket/apporio-taxi/driver/1580119970_5e2eb7a2169fd_driver.jpg
         * signupStep : 1
         * player_id : 41f3efbd-505c-47a6-b058-bc1df606dc8e
         * last_ride_request_timestamp : 2020-01-27 10:12:50
         * driver_referralcode : S6AYQ
         * dob : null
         * driver_cpf_number : null
         * driver_address : null
         * subscription_wise_commission : 2
         * driver_additional_data : null
         * wallet_money : 0
         * network_code : null
         * merchant_driver_id : 2865
         * updated_at : 2020-01-27 10:12:50
         * created_at : 2020-01-27 10:12:50
         * id : 3131
         * referred_by : null
         * smoker_type :
         * allow_other_smoker :
         * push_notification : {"fire_base":true}
         */

        private int merchant_id;
        private String first_name;
        private String last_name;
        private String email;
        private String phoneNumber;
        private String country_area_id;
        private Object driver_gender;
        private String password;
        private String profile_image;
        private int signupStep;
        private String player_id;
        private String last_ride_request_timestamp;
        private String driver_referralcode;
        private Object dob;
        private Object driver_cpf_number;
        private Object driver_address;
        private String subscription_wise_commission;
        private Object driver_additional_data;
        private int wallet_money;
        private Object network_code;
        private int merchant_driver_id;
        private String updated_at;
        private String created_at;
        private int id;
        private Object referred_by;
        private String smoker_type;
        private String allow_other_smoker;
        private PushNotificationBean push_notification;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState_code() {
            return state_code;
        }

        public void setState_code(String state_code) {
            this.state_code = state_code;
        }

        public String getPostCode_cep() {
            return postCode_cep;
        }

        public void setPostCode_cep(String postCode_cep) {
            this.postCode_cep = postCode_cep;
        }

        private String number;
        private String street;
        private String city;
        private String state_code;
        private String postCode_cep;

        public int getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(int merchant_id) {
            this.merchant_id = merchant_id;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getCountry_area_id() {
            return country_area_id;
        }

        public void setCountry_area_id(String country_area_id) {
            this.country_area_id = country_area_id;
        }

        public Object getDriver_gender() {
            return driver_gender;
        }

        public void setDriver_gender(Object driver_gender) {
            this.driver_gender = driver_gender;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }

        public int getSignupStep() {
            return signupStep;
        }

        public void setSignupStep(int signupStep) {
            this.signupStep = signupStep;
        }

        public String getPlayer_id() {
            return player_id;
        }

        public void setPlayer_id(String player_id) {
            this.player_id = player_id;
        }

        public String getLast_ride_request_timestamp() {
            return last_ride_request_timestamp;
        }

        public void setLast_ride_request_timestamp(String last_ride_request_timestamp) {
            this.last_ride_request_timestamp = last_ride_request_timestamp;
        }

        public String getDriver_referralcode() {
            return driver_referralcode;
        }

        public void setDriver_referralcode(String driver_referralcode) {
            this.driver_referralcode = driver_referralcode;
        }

        public Object getDob() {
            return dob;
        }

        public void setDob(Object dob) {
            this.dob = dob;
        }

        public Object getDriver_cpf_number() {
            return driver_cpf_number;
        }

        public void setDriver_cpf_number(Object driver_cpf_number) {
            this.driver_cpf_number = driver_cpf_number;
        }

        public Object getDriver_address() {
            return driver_address;
        }

        public void setDriver_address(Object driver_address) {
            this.driver_address = driver_address;
        }

        public String getSubscription_wise_commission() {
            return subscription_wise_commission;
        }

        public void setSubscription_wise_commission(String subscription_wise_commission) {
            this.subscription_wise_commission = subscription_wise_commission;
        }

        public Object getDriver_additional_data() {
            return driver_additional_data;
        }

        public void setDriver_additional_data(Object driver_additional_data) {
            this.driver_additional_data = driver_additional_data;
        }

        public int getWallet_money() {
            return wallet_money;
        }

        public void setWallet_money(int wallet_money) {
            this.wallet_money = wallet_money;
        }

        public Object getNetwork_code() {
            return network_code;
        }

        public void setNetwork_code(Object network_code) {
            this.network_code = network_code;
        }

        public int getMerchant_driver_id() {
            return merchant_driver_id;
        }

        public void setMerchant_driver_id(int merchant_driver_id) {
            this.merchant_driver_id = merchant_driver_id;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getReferred_by() {
            return referred_by;
        }

        public void setReferred_by(Object referred_by) {
            this.referred_by = referred_by;
        }

        public String getSmoker_type() {
            return smoker_type;
        }

        public void setSmoker_type(String smoker_type) {
            this.smoker_type = smoker_type;
        }

        public String getAllow_other_smoker() {
            return allow_other_smoker;
        }

        public void setAllow_other_smoker(String allow_other_smoker) {
            this.allow_other_smoker = allow_other_smoker;
        }

        public PushNotificationBean getPush_notification() {
            return push_notification;
        }

        public void setPush_notification(PushNotificationBean push_notification) {
            this.push_notification = push_notification;
        }

        public static class PushNotificationBean {
            /**
             * fire_base : true
             */

            private boolean fire_base;

            public boolean isFire_base() {
                return fire_base;
            }

            public void setFire_base(boolean fire_base) {
                this.fire_base = fire_base;
            }
        }
    }
}
