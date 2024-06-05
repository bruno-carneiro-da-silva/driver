package com.tkx.driver.location;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import com.tkx.driver.BuildConfig;
import com.tkx.driver.Config;
import com.tkx.driver.LocationSession;
import com.tkx.driver.ReceivePassengerActivity;
import com.tkx.driver.SingletonGson;
import com.tkx.driver.currentwork.API_S;
import com.tkx.driver.currentwork.EventLocation;
import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.fcmclasses.OneSignalServiceClass;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.models.ModelNotificationOnLocation;
import com.tkx.driver.others.Constants;
import com.tkx.driver.samwork.ApiManager;
import com.apporioinfolabs.ats_sdk.AtsLocationServiceClass;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;


public class UpdateServiceClass extends AtsLocationServiceClass implements ApiManager.APIFETCHER {

    private static final String TAG = "UpdateService";


    SessionManager sessionManager;
    LocationSession app_location_mamanger;
    ApiManager apiManager;
    HashMap<String, String> data = new HashMap<>();
    private boolean isApiRunnign = false;
    Double latitude = 0.0;
    Double longitude = 0.0;

    public static int openScreen = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        app_location_mamanger = new LocationSession(this);
        apiManager = new ApiManager(this, this);
        sessionManager = new SessionManager(this);
    }


    @Override
    public void onReceiveLocation(Location location) {

        if(sessionManager.getAppConfig().getData().getGeneral_config().getLat_long_storing_at()==1){
            Log.e("Obter lat Lng", "" + location.getLatitude() + " " + location.getLongitude());
            //  Toast.makeText(this, "" + location.getLatitude(), Toast.LENGTH_SHORT).show();

            if (Config.isConnectingToInternet(UpdateServiceClass.this)) {
                updateLocation(location);
            }else {
                
                Toast.makeText(getApplicationContext(), "aqui atualizarei no offline", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    private void updateLocation(Location location) {

        if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("1")) {
            if (latitude == 0.0) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
            updateLocationToSession(location);
        }

        if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("1")) {
            try {
                if (sessionManager.getbooking_Id() != null || !sessionManager.getbooking_Id().equals("")) {
                    try {
                        if (Double.parseDouble(sessionManager.getDistance()) > 20.0) {
                            callApiForUpdateDriverLocation(location);
                        } else {
                            callApiForUpdateDriverLocation(location);
                        }
                    } catch (Exception e) {
                        if (Double.parseDouble(sessionManager.getDistance()) > 20.0) {
                            callApiForUpdateDriverLocation(location);
                        } else {
                            callApiForUpdateDriverLocation(location);
                        }
                    }
                } else {
                    callApiForUpdateDriverLocation(location);
                }
            } catch (Exception e) {
                callApiForUpdateDriverLocation(location);
            }
            callApiForUpdateDriverLocation(location);

        }

        if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("1")) {
            LatLng targetLocation = new LatLng(location.getLatitude(), location.getLongitude()); // o nome do provedor é desnecessário
            LatLng targetLocation1 = new LatLng(latitude, longitude); // o nome do provedor é desnecessário
            getDistance(targetLocation, targetLocation1);

            Log.e("SessionDistance", "" + getDistance(targetLocation, targetLocation1));
            sessionManager.setDistance(String.valueOf(getDistance(targetLocation, targetLocation1)));

            if (getDistance(targetLocation, targetLocation1) > 20) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            } else {
                Log.e("*** Distância", "Não atualizado");
            }

        }

        // if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("1")) {
        //     callApiForUpdateDriverLocation(location);
        // }

    }

    @Override
    public void onAPIRunningState(int a, String APINAME) {
        if (a == ApiManager.APIFETCHER.KEY_API_IS_STARTED) {
            isApiRunnign = true;
        } else {
            isApiRunnign = false;
        }
    }

    @Override
    public void onFetchComplete(Object script, String APINAME) {
        try {
            ModelNotificationOnLocation modelNotificationOnLocation = SingletonGson.getInstance().fromJson("" + script, ModelNotificationOnLocation.class);
            if (modelNotificationOnLocation.getResult().equals("2")) {
                try {
                    // if(sessionManager.getFirebaseNotification()==true){
                    //     if (MyFirebaseMessagingService.openScreen == 0) {
                    //         if (openScreen == 0) {
                    //             Log.e("####Step_1", "STEP_!");
                    //             checkTimeStampAndOpenScreen(script, modelNotificationOnLocation.getData().getDriver_request_timeout());
                    //         }
                    //     }
                    // }else {
                    //     if (OneSignalServiceClass.openScreen == 0) {
                    //         if (openScreen == 0) {
                    //             Log.e("####Step_1", "STEP_!");
                    //             checkTimeStampAndOpenScreen(script, modelNotificationOnLocation.getData().getDriver_request_timeout());
                    //         }
                    //     }
                    // }

                    if (OneSignalServiceClass.openScreen == 0) {
                        if (openScreen == 0) {
                            Log.e("#### Etapa_1", "ETAPA_!");
                            checkTimeStampAndOpenScreen(script, modelNotificationOnLocation.getData().getDriver_request_timeout());
                        }
                    }

                } catch (Exception e) {
                    if (openScreen == 0) {
                        Log.e("#### Etapa_1", "ETAPA_!");
                        checkTimeStampAndOpenScreen(script, modelNotificationOnLocation.getData().getDriver_request_timeout());
                    }
                }
            }

        } catch (Exception e) {

        }
    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {

    }

    private void updateLocationToSession(Location location) {
        Log.d("****" + TAG, "UpdatingLocationToSession");
        if (location.getBearing() != 0.0) {
            app_location_mamanger.setBearingFactor("" + location.getBearing());
        }
        app_location_mamanger.setLocationLatLong(location);
    }

    private void callApiForUpdateDriverLocation(Location location) {
        EventBus.getDefault().post(new EventLocation(location));
        Log.e("APP1", "App1");
        try {
            if (!isApiRunnign) {
                data.clear();
                Log.e("APP2", "App2");
                try {
                    if (!Constants.IS_TRACKING_ACTIVITY_OPEN) {
                        Log.e("APP3", "App3");
                        if (sessionManager.getbooking_Id() != null) {
                            Log.e("APP4", "App4");
                            data.put("app_in_background", "1");
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exceção detectada durante a execução da API isApiRunnign " + e.getMessage());
                }

                data.put("latitude", "" + location.getLatitude());
                data.put("longitude", "" + location.getLongitude());
                data.put("bearing", "" + location.getBearing());
                data.put("accuracy", "" + location.getAccuracy());
                apiManager._postForTracking(API_S.Tags.DRIVER_LOCATION_UPDATER, API_S.Endpoints.DRIVER_LOCATION_UPDATER, data, sessionManager);
            }
        } catch (Exception e) {
            Log.e(TAG, "Exceção detectada durante a execução da API " + e.getMessage());
        }


    }

    public double getDistance(LatLng LatLng1, LatLng LatLng2) {
        double distance = 0;
        Location locationA = new Location("A");
        locationA.setLatitude(LatLng1.latitude);
        locationA.setLongitude(LatLng1.longitude);
        Location locationB = new Location("B");
        locationB.setLatitude(LatLng2.latitude);
        locationB.setLongitude(LatLng2.longitude);
        distance = locationA.distanceTo(locationB);

        return distance;
    }

    private void checkTimeStampAndOpenScreen(final Object script, double driverTimeOutValue) {
        final ModelNotificationOnLocation modelNotificationOnLocation = SingletonGson.getInstance().fromJson("" + script, ModelNotificationOnLocation.class);
        if (modelNotificationOnLocation.getData().getBooking_status() == 1001) {

            try {
                openReceivePassengerScreen("" + script);
            } catch (Exception e) {
                openReceivePassengerScreen("" + script);
            }

            // AndroidNetworking.get(API_S.Endpoints.SERVER_TIMESTAMP)
            //         .setTag(this).setPriority(Priority.HIGH)
            //         .build().getAsString(new StringRequestListener() {
            //     @Override
            //     public void onResponse(String response) {
            //         try {
            //             Log.e("####Step_1","STEP_!");
            //             ModelTimeStamp modelTimeStamp = SingletonGson.getInstance().fromJson("" + response, ModelTimeStamp.class);
            // if ((Long.parseLong("" + modelTimeStamp.getTime()) - Long.parseLong("" + modelNotificationOnLocation.getData().getTimestamp())) <= driverTimeOutValue) {  // time stamp criteria matches
            //             } else {
            //                 Log.d("" + TAG, "Notification bypass as it delayed by 60 sec");
            //             }
            //         } catch (Exception e) {
            //             Log.d("" + TAG, "Exception caught while parsing response " + e.getMessage());
            //         }
            //     }
            //     @Override
            //     public void onError(ANError anError) {
            //     }
            // });
        }
    }

    private void openReceivePassengerScreen(String script) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            openScreen = 1;
            Config.ReceiverPassengerActivity = true;
            Intent broadcastIntent = new Intent();
            broadcastIntent.putExtra(IntentKeys.NAVIGATION, "2");
            broadcastIntent.putExtra("" + IntentKeys.SCRIPT, "" + script);
            broadcastIntent.setAction("" + BuildConfig.APPLICATION_ID);
            sendBroadcast(broadcastIntent);
        } else {
            openScreen = 1;
            Config.ReceiverPassengerActivity = true;
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "" + TAG);
            wl.acquire(30 * 1000);
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock(Context.KEYGUARD_SERVICE);
            lock.disableKeyguard();
            startActivity(new Intent(getApplicationContext(), ReceivePassengerActivity.class)
                    .putExtra(IntentKeys.NAVIGATION, "2")
                    .putExtra("" + IntentKeys.SCRIPT, script)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }
}
