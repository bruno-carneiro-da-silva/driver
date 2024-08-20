package com.tkx.driver.offlineService;

import com.androidnetworking.error.ANError;

import org.json.JSONObject;

public interface ApiCallback {
    void onSuccess(JSONObject response);
    void onError(ANError error);
}
