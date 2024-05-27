package com.tkx.driver;

import android.location.Location;

import com.apporioinfolabs.ats_sdk.AtsLocationServiceClass;

public class MyService extends AtsLocationServiceClass {
    @Override
    public void onReceiveLocation(Location location) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    {
    }
}
