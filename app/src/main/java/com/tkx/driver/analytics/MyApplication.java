package com.tkx.driver.analytics;

import android.app.Application;
import android.util.Log;

import com.tkx.driver.BuildConfig;
import com.tkx.driver.R;
import com.tkx.driver.manager.SessionManager;
import com.apporioinfolabs.ats_sdk.ATS;
import com.hypertrack.hyperlog.HyperLog;

import dev.b3nedikt.restring.Restring;
import dev.b3nedikt.reword.RewordInterceptor;
import dev.b3nedikt.viewpump.ViewPump;


public class MyApplication extends Application {

    private final String ATS_TOKEN = ""+BuildConfig.ATS_TOKEN;

    public static final String TAG = MyApplication.class.getSimpleName();

    private static MyApplication mInstance;

    SessionManager sessionManager;

    @Override
    public void onCreate() {

        HyperLog.initialize(this);
        HyperLog.setLogLevel(Log.VERBOSE);
        HyperLog.setURL(BuildConfig.LOG_URL);

        super.onCreate();
        mInstance = this;
        sessionManager = new SessionManager(this);

        Restring.init(this);
        ViewPump.init(RewordInterceptor.INSTANCE);


        ATS.startInit(this)
            .setAppId(ATS_TOKEN)
            .fetchLocationWhenVehicleIsStop(false)
            .enableLogs(true)
            .setLocationInterval(6000)
            .setDeveloperMode(false)
            // .setConnectedStateColor(Color.rgb(0, 102, 0, 204))
            // .setDisconnectedColor(Color.rgb(0, 255, 255, 102))                
            .setNotificationTittle("Tkx")                
            .setNotificationContent(""+getString(R.string.you_are_online_now))
            .setNotificationIcon(R.drawable.driver_notification_logo)
            .init();
    }


    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

}
