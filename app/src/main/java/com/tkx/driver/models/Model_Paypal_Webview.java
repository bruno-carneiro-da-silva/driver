package com.tkx.driver.models;

public class Model_Paypal_Webview {
    /**
     * result : 1
     * message : Payment URL
     * data : https://shipnowpanel.com/Delivery/public/api/paypal?client_id=AQumyNHAmqUuMmr9FlgRZhKKOCxP7B3p43aYVZJ_hqZC59JFG7U8fK043z1wFW_T2ksptcjo-2dLkuj5&amount=1.36&currency=USD&success_url=https://shipnowpanel.com/Delivery/public/api/paypal/success&fail_url=https://shipnowpanel.com/Delivery/public/api/paypal/fail&notify_url=https://shipnowpanel.com/Delivery/public/api/paypal/notify&order_id=1608532374
     */

    private int result;
    private String message;
    private String data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
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


