package com.tkx.driver;

public class ModelAddMoneyPaypal {
    /**
     * result : 1
     * message : Money Added in your Wallet
     * data : 998.00
     */

    private String result;
    private String message;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
