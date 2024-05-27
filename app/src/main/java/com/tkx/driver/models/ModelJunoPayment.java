package com.tkx.driver.models;


import com.google.gson.annotations.SerializedName;

public class ModelJunoPayment{

    @SerializedName("result")
    private String result;

    @SerializedName("data")
    private String data;

    @SerializedName("message")
    private String message;

    @SerializedName("fail_url")
    private String failUrl;

    @SerializedName("success_url")
    private String successUrl;

    public void setResult(String result){
        this.result = result;
    }

    public String getResult(){
        return result;
    }

    public void setData(String data){
        this.data = data;
    }

    public String getData(){
        return data;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setFailUrl(String failUrl){
        this.failUrl = failUrl;
    }

    public String getFailUrl(){
        return failUrl;
    }

    public void setSuccessUrl(String successUrl){
        this.successUrl = successUrl;
    }

    public String getSuccessUrl(){
        return successUrl;
    }
}
