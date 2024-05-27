package com.tkx.driver.models;

import java.util.List;

public class ModelTraction {

    private String result;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        
        private int id;
        private String merchant_id;
        private String locale;
        private String VehicleTractionName;
        private String deleted_at;        
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

        public String getLocale() {
            return locale;
        }

        public String locale() {
            return locale;
        }

        public void setlocale(String locale) {
            this.locale = locale;
        }

        public String getVehicleTractionName() {
            return VehicleTractionName;
        }

        public String VehicleTractionName() {
            return VehicleTractionName;
        }


        public void setVehicleTractionName(String VehicleTractionName) {
            this.VehicleTractionName = VehicleTractionName;
        }

        public String getdeleted_at() {
            return deleted_at;
        }

        public void setdeleted_at(String deleted_at) {
            this.deleted_at = deleted_at;
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
}
