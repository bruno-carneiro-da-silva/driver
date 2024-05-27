package com.tkx.driver.NewEarningModule;

import java.util.List;

public class ModelCashoutHistory {

    /**
     * result : 1
     * message : Cashout Request By Driver
     * data : [{"id":1,"amount":"INR 5","cashout_status":"Rejected","action_by":"Bhuvanesh","transaction_id":"#311","comment":"asdfg","created_at":"2020-05-02 10:00:40","updated_at":"2020-05-02 05:35:20"},{"id":2,"amount":"INR 12","cashout_status":"Success","action_by":"Bhuvanesh","transaction_id":"#1234567","comment":"sdhfyutsdfhn","created_at":"2020-05-02 10:42:01","updated_at":"2020-05-02 05:18:34"},{"id":3,"amount":"INR 25","cashout_status":"Success","action_by":"Bhuvanesh","transaction_id":"$0987","comment":"dsfhjk","created_at":"2020-05-02 11:08:35","updated_at":"2020-05-02 05:43:06"},{"id":4,"amount":"INR 11","cashout_status":"Rejected","action_by":"Bhuvanesh","transaction_id":"#2345","comment":"adfg","created_at":"2020-05-02 11:10:32","updated_at":"2020-05-02 05:42:34"},{"id":5,"amount":"INR 15","cashout_status":"Pending","action_by":null,"transaction_id":null,"comment":null,"created_at":"2020-05-02 18:44:21","updated_at":"2020-05-02 18:44:21"},{"id":8,"amount":"INR 11","cashout_status":"Pending","action_by":null,"transaction_id":null,"comment":null,"created_at":"2020-05-14 12:33:09","updated_at":"2020-05-14 12:33:09"},{"id":9,"amount":"INR 11","cashout_status":"Pending","action_by":null,"transaction_id":null,"comment":null,"created_at":"2020-05-14 13:15:09","updated_at":"2020-05-14 13:15:09"}]
     */

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
        /**
         * id : 1
         * amount : INR 5
         * cashout_status : Rejected
         * action_by : Bhuvanesh
         * transaction_id : #311
         * comment : asdfg
         * created_at : 2020-05-02 10:00:40
         * updated_at : 2020-05-02 05:35:20
         */

        private int id;
        private String amount;
        private String cashout_status;
        private String action_by;
        private String transaction_id;
        private String comment;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCashout_status() {
            return cashout_status;
        }

        public void setCashout_status(String cashout_status) {
            this.cashout_status = cashout_status;
        }

        public String getAction_by() {
            return action_by;
        }

        public void setAction_by(String action_by) {
            this.action_by = action_by;
        }

        public String getTransaction_id() {
            return transaction_id;
        }

        public void setTransaction_id(String transaction_id) {
            this.transaction_id = transaction_id;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
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
