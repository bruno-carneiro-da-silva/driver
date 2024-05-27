package com.tkx.driver;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.tkx.driver.baseClass.BaseActivity;
import com.tkx.driver.currentwork.API_S;
import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.fcmclasses.OneSignalServiceClass;
import com.tkx.driver.location.SamLocationRequestService;
import com.tkx.driver.log.ApporioLog;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.models.ModelCurrentTimeStamp;
import com.tkx.driver.models.ModelNotificationOnLocation;
import com.tkx.driver.models.ModelRideInfo;
import com.tkx.driver.models.ModelRideNotifications;
import com.tkx.driver.others.Constants;
import com.tkx.driver.samwork.ApiManager;
import com.apporioinfolabs.ats_sdk.ATS;
import com.apporioinfolabs.ats_sdk.AtsOnTagSetListener;
import com.bumptech.glide.Glide;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.onesignal.OneSignal;
import com.sam.placer.PlaceHolderView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import customviews.PulsatorLayout;
import customviews.progresswheel.ProgressWheel;
import de.hdodenhof.circleimageview.CircleImageView;

public class ReceivePassengerActivity extends BaseActivity implements ApiManager.APIFETCHER {

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 858;

    Location userLocation;
    public static MediaPlayer mediaPlayer;
    long MAXTIME = 30000;
    long STARTTIME = 30000;
    int maxProgress = 360;
    int progress_quadrant;
    @BindView(R.id.car_ty_image)
    ImageView carTyImage;
    @BindView(R.id.rental_package_heaig)
    TextView rentalPackageHeaig;
    @BindView(R.id.rental_package_name)
    TextView rentalPackageName;
    private ProgressDialog progressDialog;
    public static boolean RIDE_BTN_CLICKED = false;
    private String TAG = "ReceivePassengerActivity";
    public static Activity activity;
    private SamLocationRequestService samLocationRequestService;
    private ApiManager apiManager;
    private SessionManager sessionManager;
    private ModelRideNotifications modelRideNotifications;
    private ModelNotificationOnLocation modelNotificationOnLocation;
    private ProgressDialog locationProgressWheel;
    private Vibrator vibrator;


    CountDownTimer SoundTimer, ProgressTimer;
    @BindView(R.id.request_type)
    TextView requestType;
    @BindView(R.id.tvPaymentMethod)
    TextView tvPaymentMethod;
    @BindView(R.id.progress)
    ProgressWheel progress;
    @BindView(R.id.map_image)
    CircleImageView mapImage;
    @BindView(R.id.pulsator)
    PulsatorLayout pulsator;
    @BindView(R.id.time_txt)
    TextView timeTxt;
    @BindView(R.id.main_layout_pickup_txt)
    TextView mainLayoutPickupTxt;
    @BindView(R.id.main_layout)
    LinearLayout mainLayout;
    @BindView(R.id.ride_expire_pick_address_txt)
    TextView rideExpirePickAddressTxt;
    @BindView(R.id.ride_expire_drop_address_txt)
    TextView rideExpireDropAddressTxt;
    @BindView(R.id.expire_msg)
    TextView expireMsg;
    @BindView(R.id.ride_expire_ok_btn)
    LinearLayout rideExpireOkBtn;
    @BindView(R.id.ride_expire_layout)
    LinearLayout rideExpireLayout;
    @BindView(R.id.accept_ride_btn)
    LinearLayout acceptRideBtn;
    @BindView(R.id.cancel_btn)
    CardView cancelBtn;
    @BindView(R.id.root)
    RelativeLayout root;
    @BindView(R.id.time_and_distance_text)
    TextView timeAndDistanceText;
    @BindView(R.id.map_image_background)
    CircleImageView mapImageBackground;
    @BindView(R.id.drop)
    TextView drop;
    @BindView(R.id.more_destination)
    ImageView more_destination;
    @BindView(R.id.main_layout_drop_txt)
    TextView main_layout_drop_txt;
    @BindView(R.id.est_fare)
    TextView est_fare;
    @BindView(R.id.note)
    TextView note;
    @BindView(R.id.estimation_fare)
    LinearLayout estimation_fare;
    String value;
    // Desativado em 12/12/2023
    // @BindView(R.id.vehicle_type)
    // TextView vehicle_type;    
    @BindView(R.id.no_riders)
    TextView no_riders;

    private FusedLocationProviderClient mFusedLocationClient;

    private static Socket mSocket = null;

    HashMap<String, String> data = new HashMap<>();


    @SuppressLint("LongLogTag")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_receive_passenger);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ButterKnife.bind(this);

        OneSignal.startInit(ReceivePassengerActivity.this)
                .autoPromptLocation(true)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        try {
            //  if(sessionManager.getFirebaseNotification()==true){
            //      MyFirebaseMessagingService.openScreen = 1;
            //  }else {
            //      OneSignalServiceClass.openScreen = 1;
            //  }
            OneSignalServiceClass.openScreen = 1;

        } catch (Exception e) {
            Log.e(TAG, "" + e.getMessage());
        }

        ApporioLog.HYPER_LOG_DEBUG(TAG, Constants.PASS_8 + " " + "ReceivePassengerScreenOpen");
        Log.d(TAG + "******ReceiveActivityOpen_4", "" + "ReceivePassengerOpen");

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(this.getResources().getString(R.string.loading));
        locationProgressWheel = new ProgressDialog(this);
        locationProgressWheel.setMessage(this.getResources().getString(R.string.fetching_locaton));
        locationProgressWheel.setCancelable(false);
        activity = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        apiManager = new ApiManager(this, this);
        samLocationRequestService = new SamLocationRequestService(this);
        sessionManager = new SessionManager(this);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        value = getIntent().getStringExtra(IntentKeys.NAVIGATION);


        if (value.equals("2")) {
            Log.e("***Hello", "" + getIntent().getExtras().getString(IntentKeys.SCRIPT));
            modelNotificationOnLocation = SingletonGson.getInstance().fromJson("" + getIntent().getExtras().getString(IntentKeys.SCRIPT), ModelNotificationOnLocation.class);
            Log.e("***Hello000", "" + modelNotificationOnLocation.getData().getBooking_id());

        } else {
            modelRideNotifications = SingletonGson.getInstance().fromJson("" + getIntent().getExtras().getString(IntentKeys.SCRIPT), ModelRideNotifications.class);
        }

        try {
            setMediaSound();
        } catch (Exception e) {
        }


        more_destination.setOnClickListener(view -> {

            if (value.equals("2")) {

            } else {
                onMultipleDestination();
            }
        });

        if (value.equals("2")) {
            mainLayoutPickupTxt.setText("" + modelNotificationOnLocation.getData().getPickup_location());

            if (modelNotificationOnLocation.getData().getService().equals("")) {
                requestType.setText("" + modelNotificationOnLocation.getData().getVehicle());
            } else {
                requestType.setText("" + modelNotificationOnLocation.getData().getService());
            }
            tvPaymentMethod.setText("" + modelNotificationOnLocation.getData().getPayment_method());

            if (modelNotificationOnLocation.getData().getNumber_of_rider()!= null){
                if (Integer.parseInt(modelNotificationOnLocation.getData().getNumber_of_rider()) > 0){
                    no_riders.setVisibility(View.VISIBLE);
                    no_riders.setText("No of riders "+": "+modelNotificationOnLocation.getData().getNumber_of_rider());
                }
                else {
                    no_riders.setVisibility(View.GONE);
                }

            }else {
                no_riders.setVisibility(View.GONE);
            }

            // largar show ou não
            if (sessionManager.getAppConfig().getData().getReceiving().isDrop_point()) {
                drop.setVisibility(View.VISIBLE);
                main_layout_drop_txt.setText("" + modelNotificationOnLocation.getData().getDrop_location());
            } else {

                drop.setVisibility(View.GONE);
                main_layout_drop_txt.setVisibility(View.GONE);

            }

            more_destination.setVisibility(modelNotificationOnLocation.getData().isMultiple_visable() ? View.GONE : View.GONE);

            // estimativa mostrar ou não
            if (sessionManager.getAppConfig().getData().getReceiving().isEstimate_fare()) {
                // comentado em 14-12-2023 fatal error
                // est_fare.setText("R$ " + modelRideNotifications.getData().getEstimate_bill());
            } else {
                estimation_fare.setVisibility(View.GONE);
            }

            // dados adicionais
            if (modelNotificationOnLocation.getData().getAdditional_notes().equals("")) {
                note.setVisibility(View.GONE);
            } else {

                note.setText("" + modelNotificationOnLocation.getData().getAdditional_notes());

                Log.d("note", "" + modelNotificationOnLocation.getData().getAdditional_notes());
            }
            
            // Tempo e distância da viagem
            if (!modelNotificationOnLocation.getData().getDrop_location().equals("")) {
                timeAndDistanceText.setText("Temp/dist viagem: " + "  " + modelNotificationOnLocation.getData().getEstimate_time() + "  " + " | " + "  " + modelNotificationOnLocation.getData().getEstimate_distance());
            }


            rentalPackageName.setText("" + modelNotificationOnLocation.getData().getPackage_name());

            if (modelNotificationOnLocation.getData().getService_type_id() == 1) {
                rentalPackageHeaig.setVisibility(View.GONE);
                rentalPackageName.setVisibility(View.GONE);
            } else {
                rentalPackageHeaig.setVisibility(View.VISIBLE);
                rentalPackageName.setVisibility(View.VISIBLE);
            }

            // if (value.equals("2")) {
            //     setTimeInterval("" + modelNotificationOnLocation.getData().getDriver_request_timeout(), "" + get_diffrence_time_from_CurrentTimeStampFromApi(modelNotificationOnLocation));
            //     // setTimeInterval("" + modelNotificationOnLocation.getData().getDriver_request_timeout(), "5");
            // } else {
            //     setTimeInterval("" + modelRideNotifications.getData().getDriver_request_timeout(), "" + get_diffrence_time_from_CurrentTimeStamp(modelRideNotifications));
            //     // setTimeInterval("" + modelRideNotifications.getData().getDriver_request_timeout(), "5");
            // }
            // //   setTimeInterval("" +modelNotificationOnLocation.getData().getDriver_request_timeout(), "" + get_diffrence_time_from_CurrentTimeStamp(modelRideNotifications));
            // //setTimeInterval("" +modelNotificationOnLocation.getData().getDriver_request_timeout(), "" + get_diffrence_time_from_CurrentTimeStamp(modelRideNotifications));
            // setTimeInterval("" + modelNotificationOnLocation.getData().getDriver_request_timeout(), "5");

            HashMap<String, String> data = new HashMap<>();
            data.put("booking_id", "" + modelNotificationOnLocation.getData().getBooking_id());
            try {
                apiManager._post(API_S.Tags.CURRENT_TIMESTAMP, API_S.Endpoints.TIME_STAMP, data, sessionManager);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // apiManager.execution_method_get(API_S.Tags.CURRENT_TIMESTAMP, API_S.Endpoints.TIME_STAMP);

        } else {
            mainLayoutPickupTxt.setText("" + modelRideNotifications.getData().getPickup_location());

            if (modelRideNotifications.getData().getService().equals("")) {
                requestType.setText("" + modelRideNotifications.getData().getVehicle());
            } else {
                requestType.setText("" + modelRideNotifications.getData().getService());
            }

            tvPaymentMethod.setText("" + modelRideNotifications.getData().getPayment_method());

            if (modelRideNotifications.getData().getNumber_of_rider() != null){
                if (Integer.parseInt(modelRideNotifications.getData().getNumber_of_rider()) > 0){
                    no_riders.setVisibility(View.VISIBLE);
                    no_riders.setText("No of riders "+": "+modelRideNotifications.getData().getNumber_of_rider());
                }
                else {
                    no_riders.setVisibility(View.GONE);
                }

            }else {
                no_riders.setVisibility(View.GONE);
            }


            //drop show or not
            if (sessionManager.getAppConfig().getData().getReceiving().isDrop_point()) {

                drop.setVisibility(View.VISIBLE);
                main_layout_drop_txt.setText("" + modelRideNotifications.getData().getDrop_location());
            } else {

                drop.setVisibility(View.GONE);
                main_layout_drop_txt.setVisibility(View.GONE);

            }

            // estimativa mostrar ou não
            if (sessionManager.getAppConfig().getData().getReceiving().isEstimate_fare()) {
                est_fare.setText("R$ " + modelRideNotifications.getData().getEstimate_bill());
            } else {
                estimation_fare.setVisibility(View.GONE);
            }

            // dados adicionais
            if (modelRideNotifications.getData().getAdditional_notes().equals("")) {
                note.setVisibility(View.GONE);
            } else {

                note.setText("" + modelRideNotifications.getData().getAdditional_notes());

            }


            more_destination.setVisibility(modelRideNotifications.getData().isMultiple_visable() ? View.VISIBLE : View.GONE);
            if (modelRideNotifications.getData().isMultiple_visable()) {

                if (modelRideNotifications.getData().getMultiple_stop_list() == null || modelRideNotifications.getData().getMultiple_stop_list().size() == 0) {
                    more_destination.setVisibility(View.GONE);
                } else {
                    more_destination.setVisibility(View.VISIBLE);
                }
            } else {

                more_destination.setVisibility(View.GONE);
            }

            // verificação de local de entrega

            if (!modelRideNotifications.getData().getDrop_location().equals("") || modelRideNotifications.getData().getDrop_location() != null) {
                timeAndDistanceText.setText("Temp/dist viagem: " + "  " + modelRideNotifications.getData().getEstimate_time() + "  "+ " | " + "  " + modelRideNotifications.getData().getEstimate_distance());
            }


            rentalPackageName.setText("" + modelRideNotifications.getData().getPackage_name());

            if (modelRideNotifications.getData().getService_type_id().equals("1")) {
                rentalPackageHeaig.setVisibility(View.GONE);
                rentalPackageName.setVisibility(View.GONE);
            } else {
                rentalPackageHeaig.setVisibility(View.VISIBLE);
                rentalPackageName.setVisibility(View.VISIBLE);
            }

            try {
                HashMap<String, String> data = new HashMap<>();
                data.put("booking_id", "" + modelRideNotifications.getData().getBooking_id());
                apiManager._post(API_S.Tags.CURRENT_TIMESTAMP, API_S.Endpoints.TIME_STAMP, data, sessionManager);

            } catch (Exception e) {
                Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                Log.d("" + TAG, "Exception caught while calling API " + e.getMessage());
            }

            // apiManager.execution_method_get(API_S.Tags.CURRENT_TIMESTAMP, API_S.Endpoints.TIME_STAMP);
            // if (value.equals("2")) {
            //     setTimeInterval("" + modelNotificationOnLocation.getData().getDriver_request_timeout(), "" +             get_diffrence_time_from_CurrentTimeStampFromApi(modelNotificationOnLocation));
            //     // setTimeInterval("" + modelNotificationOnLocation.getData().getDriver_request_timeout(), "5");
            // } else {
            // setTimeInterval("" + modelRideNotifications.getData().getDriver_request_timeout(), "" +          get_diffrence_time_from_CurrentTimeStamp(modelRideNotifications));
            //     // setTimeInterval("" + modelRideNotifications.getData().getDriver_request_timeout(), "5");
            // }

        }


        rideExpireOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cancelBtn.setOnClickListener(v -> {
            try {

                if (value.equals("2")) {
                    HashMap<String, String> data = new HashMap<>();
                    data.put("booking_id", "" + modelNotificationOnLocation.getData().getBooking_id());
                    apiManager._post(API_S.Tags.REJECT_RIDE, API_S.Endpoints.REJECT_RIDE, data, sessionManager);
                    SoundTimer.cancel();
                    ProgressTimer.cancel();
                } else {
                    HashMap<String, String> data = new HashMap<>();
                    data.put("booking_id", "" + modelRideNotifications.getData().getBooking_id());
                    apiManager._post(API_S.Tags.REJECT_RIDE, API_S.Endpoints.REJECT_RIDE, data, sessionManager);
                    SoundTimer.cancel();
                    ProgressTimer.cancel();
                }


            } catch (Exception e) {
                Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                Log.d("" + TAG, "Exception caught while calling API " + e.getMessage());
            }

        });

        acceptRideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    SoundTimer.cancel();
                    ProgressTimer.cancel();
                    try {
                        locationProgressWheel.show();
                    } catch (Exception e) {

                    }
                    if (checkPermission()) {
                        onLocationPermissionGranted();
                    }
                } catch (Exception e) {

                }

                // samLocationRequestService.executeService(new SamLocationRequestService.SamLocationListener() {
                //     @Override
                //     public void onLocationUpdate(Location location) {
                //     }
                // });


            }
        });


        try {
            mapImageBackground.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int width = mapImageBackground.getMeasuredWidth();
                    int height = mapImageBackground.getMeasuredHeight();

                    if (value.equals("2")) {
                        String mapImage = "" + "https://maps.googleapis.com/maps/api/staticmap?&center=" + modelNotificationOnLocation.getData().getPickup_latitude() + "," + modelNotificationOnLocation.getData().getPickup_longitude() + "&size=" + width + "x" + height + "&zoom=15&maptype=roadmap&key=";
                    } else {
                        String mapImage = "" + "https://maps.googleapis.com/maps/api/staticmap?&center=" + modelRideNotifications.getData().getPickup_latitude() + "," + modelRideNotifications.getData().getPickup_longitude() + "&size=" + width + "x" + height + "&zoom=15&maptype=roadmap&key=";

                    }
                    Log.d("" + TAG, "" + mapImage);
                    // comentado em 14-12-2023 
                    // Glide.with(ReceivePassengerActivity.this).load(mapImage).into(mapImageBackground);

                }
            });

        } catch (Exception e) {

        }

        if (value.equals("2")) {
            Glide.with(this).load(modelNotificationOnLocation.getData().getVehicleimage()).into(carTyImage);
        } else {
            Glide.with(this).load(modelRideNotifications.getData().getVehicleimage()).into(carTyImage);

        }


        Log.d("AUTOACCEPT", "" + sessionManager.getUserDetails().get(SessionManager.KEY_AUTO_ACCEPT));
        if (sessionManager.getUserDetails().get(SessionManager.KEY_AUTO_ACCEPT).equals("1")) {

            try {
                SoundTimer.cancel();
                ProgressTimer.cancel();
            } catch (Exception e) {

            }

            locationProgressWheel.show();

            if (checkPermission()) {
                onLocationPermissionGranted();
            }
            // samLocationRequestService.executeService(new SamLocationRequestService.SamLocationListener() {
            //     @Override
            //     public void onLocationUpdate(Location location) {
            //         if (locationProgressWheel.isShowing()) {
            //             try {
            //                 if (value.equals("2")) {
            //                     HashMap<String, String> data = new HashMap<>();
            //                     data.put("booking_id", "" + modelNotificationOnLocation.getData().getBooking_id());
            //                     data.put("latitude", "" + location.getLatitude());
            //                     data.put("longitude", "" + location.getLongitude());
            //                     data.put("accuracy", "" + location.getAccuracy());
            //                     apiManager._post(API_S.Tags.ACCEPT_RIDE, API_S.Endpoints.ACCEPT_RIDE, data, sessionManager);
            //                    } else {
            //                        HashMap<String, String> data = new HashMap<>();
            //                        data.put("booking_id", "" + modelRideNotifications.getData().getBooking_id());
            //                        data.put("latitude", "" + location.getLatitude());
            //                        data.put("longitude", "" + location.getLongitude());
            //                        data.put("accuracy", "" + location.getAccuracy());
            //                        apiManager._post(API_S.Tags.ACCEPT_RIDE, API_S.Endpoints.ACCEPT_RIDE, data, sessionManager);
            //                }
            //            } catch (Exception e) {
            //                Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            //            }
            //        }
            //    }
            //});
        }

    }

    private void connectSocketMethod() {

        try {
            mSocket = IO.socket("http://3.9.91.62:8080/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if (!mSocket.connected()) {
            mSocket.connect();

            mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d("**Socket Connected", args.toString());

                }
            });

            mSocket.on("newconnection", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d("**Socket Connected", args.toString());

                }
            });

            mSocket.connect();

        }


        mSocket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("**Socket DisConnected", args.toString());

            }
        });
    }

    private void startTimer() {
        SoundTimer = new CountDownTimer(STARTTIME, 2000) {
            @Override
            public void onTick(long l) {
                mediaPlayer.start();
                pulsator.start();
            }

            @Override
            public void onFinish() {
            }
        }.start();


        ProgressTimer = new CountDownTimer(STARTTIME, 1000) {
            @Override
            public void onTick(long l) {
                try {
                    int vaaal = Integer.parseInt("" + timeTxt.getText().toString());
                    if ((vaaal - 1) < 10) {
                        timeTxt.setTextColor(Color.parseColor("#e74c3c"));
                    } else {
                        timeTxt.setTextColor(Color.parseColor("#2ecc71"));
                    }
                    timeTxt.setText("" + (vaaal - 1));
                } catch (Exception e) {
                }

                maxProgress = maxProgress - progress_quadrant;
                Log.d(TAG, "max progress" + maxProgress);
                progress.setProgress(maxProgress);
            }

            @Override
            public void onFinish() {
                HashMap<String, String> data = new HashMap<>();
                if (value.equals("2")) {
                    data.put("booking_id", "" + modelNotificationOnLocation.getData().getBooking_id());
                } else {
                    data.put("booking_id", "" + modelRideNotifications.getData().getBooking_id());
                }

                try {
                    apiManager._post(API_S.Tags.REJECT_RIDE, API_S.Endpoints.REJECT_RIDE, data, sessionManager);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    SoundTimer.cancel();
                    ProgressTimer.cancel();
                } catch (Exception e) {

                }
            }
        }.start();
    }

    private void setMediaSound() throws Exception {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(this, Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.message_pops));
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
        mediaPlayer.prepare();

        // 12/12/2023
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        long[] pattern = { 0, 100, 1000, 100, 500, 500, 100, 100, 1000 };
        vibrator.vibrate(pattern, -1);

    }

    private void setprogressQuadAndMaxProgress(long maxtime, long startTime) {
        progress_quadrant = (int) (6 * (60000 / maxtime));
        int val = (int) (maxtime / startTime);
        maxProgress = 360 / val;
        progress.setProgress(maxProgress);
        startTimer();
    }

    private void setTimeInterval(String maxtime, String differencetime) {
        try {
            Log.e("***Driver Request Time", "" + maxtime);
            Log.e("***Difference Time", "" + differencetime);
            MAXTIME = (Long.parseLong("" + maxtime) * 1000);
            long difference_time = (Long.parseLong("" + differencetime) * 1000);
            STARTTIME = MAXTIME - difference_time;
            if (STARTTIME <= 1) {
                HashMap<String, String> data = new HashMap<>();
                if (value.equals("2")) {
                    data.put("booking_id", "" + modelNotificationOnLocation.getData().getBooking_id());
                } else {
                    data.put("booking_id", "" + modelRideNotifications.getData().getBooking_id());
                }

                apiManager._post(API_S.Tags.REJECT_RIDE, API_S.Endpoints.REJECT_RIDE, data, sessionManager);
                try {
                    SoundTimer.cancel();
                    ProgressTimer.cancel();
                } catch (Exception e) {

                }
            } else {
                timeTxt.setText("" + (STARTTIME / 1000));
                setprogressQuadAndMaxProgress(MAXTIME, STARTTIME);
            }
        } catch (Exception e) {
            Log.d("" + TAG, "Exception Caught while taking time for progress timer -->" + e.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onStart() {
        super.onStart();
        try {
        // if(sessionManager.getFirebaseNotification()==true){
        //     MyFirebaseMessagingService.openScreen = 1;
        // }else {
        //     OneSignalServiceClass.openScreen = 1;
        // }

            OneSignalServiceClass.openScreen = 1;
            Config.ReceiverPassengerActivity = true;
        } catch (Exception e) {

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        // vibrator.cancel();
        try {
            ApporioLog.HYPER_LOG_DEBUG(TAG, "ReceiveActivity Destroy");

        // if(sessionManager.getFirebaseNotification()==true){
        //     MyFirebaseMessagingService.openScreen = 0;
        // }else {
        //     OneSignalServiceClass.openScreen = 0;
        // }
            OneSignalServiceClass.openScreen = 0;
            Config.ReceiverPassengerActivity = false;
        } catch (Exception e) {

        }
    }

    public void setViewAccordingToRideStatus(String ride_status, String message) {

        if (ride_status.equals("1")) {
            mainLayout.setVisibility(View.VISIBLE);
            acceptRideBtn.setVisibility(View.VISIBLE);
            // rideExpireLayout.setVisibility(View.GONE);


        } // Ride is still live for driver
        else {  // some thing went wrong
            // mainLayout.setVisibility(View.GONE);
            acceptRideBtn.setVisibility(View.GONE);
            //  rideExpireLayout.setVisibility(View.VISIBLE);
            //  expireMsg.setText("" + message);
        }
    }

    @Override
    public void onAPIRunningState(int a, String APINAME) {

        try {
            if (a == ApiManager.APIFETCHER.KEY_API_IS_STARTED) {
                progressDialog.show();
            } else if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {

        }

    }

    @SuppressLint({"LongLogTag", "NewApi"})
    @Override
    public void onFetchComplete(Object script, String APINAME) {
        try {
            switch (APINAME) {
                case API_S.Tags.ACCEPT_RIDE:
                    ModelRideInfo modelAcceptRide = SingletonGson.getInstance().fromJson("" + script, ModelRideInfo.class);

                    if (sessionManager.getAppConfig().getData().getGeneral_config().getLat_long_storing_at() == 2) {
                        ATS.removeTag(new AtsOnTagSetListener() {
                            @Override
                            public void onSuccess(String message) {

                            }
                            @Override
                            public void onFailed(String message) {

                            }
                        });
                    }
                    //  Toast.makeText(activity, "" + modelAcceptRide.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, TrackerActivity.class).putExtra("" + IntentKeys.BOOKING_ID, "" + modelAcceptRide.getData().getId()));
                    finish();
                    break;
                case API_S.Tags.REJECT_RIDE:
                    try {
                        try {

                            Config.ReceiverPassengerActivity = false;
                            // if(sessionManager.getFirebaseNotification()==true){
                            //     MyFirebaseMessagingService.openScreen = 0;
                            // }else {
                            //     OneSignalServiceClass.openScreen = 0;
                            // }
                            OneSignalServiceClass.openScreen = 0;
                            ReceivePassengerActivity.this.finish();

                        } catch (Exception e) {

                        }
                    } catch (Exception e) {

                    }
                    finish();
                    break;

                case API_S.Tags.CURRENT_TIMESTAMP:
                    try {
                        ModelCurrentTimeStamp modelCurrentTimeStamp = SingletonGson.getInstance().fromJson("" + script, ModelCurrentTimeStamp.class);
                        if (modelCurrentTimeStamp.getResult().equals("1")) {
                            if (value.equals("2")) {
                                ApporioLog.HYPER_LOG_DEBUG(TAG, Constants.PASS_9 + " " + modelNotificationOnLocation.getData().getDriver_request_timeout());
                                setTimeInterval("" + modelNotificationOnLocation.getData().getDriver_request_timeout(), "" + modelCurrentTimeStamp.getData().getDiffrance());
                            } else {
                                ApporioLog.HYPER_LOG_DEBUG(TAG, Constants.PASS_9 + " " + modelRideNotifications.getData().getDriver_request_timeout());
                                setTimeInterval("" + modelRideNotifications.getData().getDriver_request_timeout(), "" + modelCurrentTimeStamp.getData().getDiffrance());
                            }
                            // Desativado em 12/12/2023
                            // vehicle_type.setText("" + modelRideNotifications.getData().getVehicle());
                        }
                    } catch (Exception e) {

                    }
                    break;
            }

        } catch (Exception e) {
            Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {
        Toast.makeText(this, "" + script, Toast.LENGTH_SHORT).show();
        ApporioLog.HYPER_LOG_DEBUG(TAG, "Result Zero" + " " + script);

        // Snackbar.make(root, "" + script, Snackbar.LENGTH_SHORT).show();
        // Intent intent = new Intent(ReceivePassengerActivity.this, SplashActivity.class);
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // startActivity(intent);
        ReceivePassengerActivity.this.finish();
    }

    private void fireEventForAcceptRide(String booking_id) {
        data.put("booking_id", "" + booking_id);
        Log.e("DATA", "" + data);
        mSocket.emit("BookingAccept", data);
    }

    private boolean checkPermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //Ask for the permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            Toast.makeText(this, "De permissão de localização", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    private void onLocationPermissionGranted() {
        if (!checkPermission()) return;

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {
                            userLocation = location;

                            if (locationProgressWheel.isShowing()) {
                                try {
                                    if (value.equals("2")) {
                                        HashMap<String, String> data = new HashMap<>();
                                        data.put("booking_id", "" + modelNotificationOnLocation.getData().getBooking_id());
                                        data.put("latitude", "" + location.getLatitude());
                                        data.put("longitude", "" + location.getLongitude());
                                        data.put("accuracy", "" + location.getAccuracy());
                                        apiManager._post(API_S.Tags.ACCEPT_RIDE, API_S.Endpoints.ACCEPT_RIDE, data, sessionManager);
                                    } else {
                                        HashMap<String, String> data = new HashMap<>();
                                        data.put("booking_id", "" + modelRideNotifications.getData().getBooking_id());
                                        data.put("latitude", "" + location.getLatitude());
                                        data.put("longitude", "" + location.getLongitude());
                                        data.put("accuracy", "" + location.getAccuracy());
                                        apiManager._post(API_S.Tags.ACCEPT_RIDE, API_S.Endpoints.ACCEPT_RIDE, data, sessionManager);
                                    }
                                } catch (Exception e) {
                                    Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                }
                            }

                        } else {
                            userLocation = null;
                        }
                    }
                });
    }


    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    // evento para verificar a viagem ativa, se a viagem estiver sendo aceita por outra operadora ou cancelada, simplesmente cancelará a chamada
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String data) {
        Log.d("" + TAG, "" + data);
        if (value.equals("2")) {
            ModelNotificationOnLocation modelNotificationOnLocation = SingletonGson.getInstance().fromJson("" + data, ModelNotificationOnLocation.class);
            if (modelNotificationOnLocation.getData().getBooking_status() == 1111) { // Ride related notification
                try {
                    try {
                        // HashMap<String, String> data1 = new HashMap<>();
                        // data1.put("booking_id", "" + modelNotificationOnLocation.getData().getBooking_id());
                        // apiManager._post(API_S.Tags.REJECT_RIDE, API_S.Endpoints.REJECT_RIDE, data1, sessionManager);

                        try {
                            SoundTimer.cancel();
                            ProgressTimer.cancel();
                            ReceivePassengerActivity.this.finish();
                        } catch (Exception e) {

                        }
                        //  vibrator.cancel();
                    } catch (Exception e) {
                        // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        // vibrator.cancel();
                        Log.d("" + TAG, "Exception caught while calling API " + e.getMessage());
                    }
                } catch (Exception e) {
                    // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }
        } else {
            ModelRideNotifications modelRideNotifications = SingletonGson.getInstance().fromJson("" + data, ModelRideNotifications.class);
            if (modelRideNotifications.getData().getBooking_status().equals("1111")) { // Ride related notification
                try {
                    try {
                        // HashMap<String, String> data1 = new HashMap<>();
                        // data1.put("booking_id", "" + modelRideNotifications.getData().getBooking_id());
                        // apiManager._post(API_S.Tags.REJECT_RIDE, API_S.Endpoints.REJECT_RIDE, data1, sessionManager);
                        try {
                            SoundTimer.cancel();
                            ProgressTimer.cancel();
                            ReceivePassengerActivity.this.finish();
                        } catch (Exception e) {

                        }

                        // vibrator.cancel();
                    } catch (Exception e) {
                        //   Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        // vibrator.cancel();
                        Log.d("" + TAG, "Exception caught while calling API " + e.getMessage());
                    }
                } catch (Exception e) {
                    // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Long get_diffrence_time_from_CurrentTimeStamp(ModelRideNotifications modelRideNotifications, int current) {
        Long difference_time;

        // String ts = String.valueOf(Instant.now().getEpochSecond());
        String ts = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

        int time = Integer.parseInt(ts);

        difference_time = Long.valueOf(current - modelRideNotifications.getData().getTimestamp());

        return difference_time;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Long get_diffrence_time_from_CurrentTimeStampFromApi(ModelNotificationOnLocation modelRideNotifications, int current) {


        Long difference_time;
        //String ts = String.valueOf(Instant.now().getEpochSecond());

        String ts = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

        int time = Integer.parseInt(ts);

        difference_time = Long.valueOf(current - modelRideNotifications.getData().getTimestamp());

        return difference_time;
    }

    public void onMultipleDestination() {

        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        dialog.setCancelable(true);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_multiple_destnation);

        PlaceHolderView placeHolderView = (PlaceHolderView) dialog.findViewById(R.id.pl_stops);
        RelativeLayout iv_cross = (RelativeLayout) dialog.findViewById(R.id.iv_cross);

        iv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        for (int i = 0; i < modelRideNotifications.getData().getMultiple_stop_list().size(); i++) {

            placeHolderView.addView(new MultipleStopAdapter(ReceivePassengerActivity.this, modelRideNotifications.getData().getMultiple_stop_list().get(i).getDrop_location()));
        }

        dialog.show();
    }

    public void onMultipleDestinationLOcation() {

        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        dialog.setCancelable(true);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_multiple_destnation);

        PlaceHolderView placeHolderView = (PlaceHolderView) dialog.findViewById(R.id.pl_stops);
        RelativeLayout iv_cross = (RelativeLayout) dialog.findViewById(R.id.iv_cross);

        iv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //  for (int i = 0 ; i < modelNotificationOnLocation.getData().getMultiple_stop_list().size() ; i++){        
        //  placeHolderView.addView(new MultipleStopAdapter(ReceivePassengerActivity.this, modelRideNotifications.getData().getMultiple_stop_list().get(i).getDrop_location()));
        //  }

        dialog.show();
    }
}