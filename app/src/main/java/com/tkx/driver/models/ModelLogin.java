package com.tkx.driver.models;

public class ModelLogin {


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
        private String access_token;
        private String refresh_token;
        private boolean taxi_company;
        private PushNotificationBean push_notification;

        public boolean isTaxi_company() {
            return taxi_company;
        }

        public void setTaxi_company(boolean taxi_company) {
            this.taxi_company = taxi_company;
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