package com.tkx.driver;

public class Modelnhorapayment {

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
         * reference : 509530000
         */

        private String reference;

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }
    }
}
