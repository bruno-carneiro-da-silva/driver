package com.tkx.driver;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;

import com.amalbit.trail.Route;
import com.amalbit.trail.RouteOverlayView;
import com.tkx.driver.baseClass.BaseActivity;
import com.tkx.driver.currentwork.API_S;
import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.currentwork.STATUS;
import com.tkx.driver.location.SamLocationRequestService;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.models.ModelCancelReasion;
import com.tkx.driver.models.ModelGoogleDirection;
import com.tkx.driver.models.ModelNotificationChat;
import com.tkx.driver.models.ModelNotificationType;
import com.tkx.driver.models.ModelRideInfo;
import com.tkx.driver.models.ModelTrackRide;
import com.tkx.driver.others.AppUtils;
import com.tkx.driver.others.Maputils;
import com.tkx.driver.routedrawer.FetchUrl;
import com.tkx.driver.samwork.ApiManager;
import com.apporioinfolabs.apporiotaxislidingbutton.ApporioTaxiSlidingButton;
import com.apporioinfolabs.ats_sdk.ATS;
import com.apporioinfolabs.ats_sdk.AtsOnTripSetListener;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.android.PolyUtil;
import com.onesignal.OneSignal;
import com.sampermissionutils.AfterPermissionGranted;
import com.sampermissionutils.EasyPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import customviews.typefacesviews.TypeFaceGoogle;
import customviews.typefacesviews.TypeFaceGoogleBold;
import de.hdodenhof.circleimageview.CircleImageView;

import android.os.Vibrator;
import android.media.MediaPlayer;

public class TrackerActivity extends BaseActivity implements OnMapReadyCallback, ApiManager.APIFETCHER {

    @BindView(R.id.events_button)
    ApporioTaxiSlidingButton eventsButton;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.customer_name_txt)
    TypeFaceGoogleBold customerNameTxt;
    @BindView(R.id.rating)
    RatingBar rating;
    @BindView(R.id.customer_phone_txt)
    TypeFaceGoogleBold customerPhoneTxt;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.customer_image)
    CircleImageView customerImage;
    // @BindView(R.id.payment_method_name_txt)
    // TypeFaceGoogleBold paymentMethodNameTxt;
    @BindView(R.id.customer_layout)
    LinearLayout customerLayout;
    @BindView(R.id.address_layout)
    LinearLayout addressLayout;
    @BindView(R.id.location_text)
    TypeFaceGoogle locationText;
    @BindView(R.id.location_edit_icon)
    ImageView locationEditIcon;
    @BindView(R.id.action_layout)
    LinearLayout actionLayout;
    @BindView(R.id.mapOverlayView)
    RouteOverlayView routeOverlayView;
    @BindView(R.id.phone_button)
    ImageView phoneButton;
    @BindView(R.id.chat_counter)
    TextView chatCounter;
    @BindView(R.id.chat_button)
    FrameLayout chatButton;
    @BindView(R.id.navigation_button)
    ImageView navigationButton;
    @BindView(R.id.sos_button)
    ImageView sosButton;
    int FLAG = 0;

    private static final String TAG = "TrackerActivity";
    @BindView(R.id.childe_button)
    ImageView childeButton;
    @BindView(R.id.info_button)
    ImageView infoButton;
    @BindView(R.id.wait_button)
    ImageView waitButton;
    
    private GoogleMap mGoogleMap;
    private ModelRideInfo modelRideInfo = null;
    private Polyline stillPolyline = null;
    private Route animatedPolyLine;
    private Marker stillMarker = null;
    private Marker drivermarker = null;
    private int WAIT_TYPE;
    private int METER_IMAGE_ACTIVITY = 4635;

    private int OTP_REQUEST_CODE = 4639;
    private String METER_VALUE = "";
    private String METER_IMAGE = "";
    private String TOLL_AMOUNT = "";

    private int DELIVERY_PLACE_ACTIVITY = 4635;
    private String DELIVERY_PLACE_VALUE = "";
    private String DELIVERY_PLACE_IMAGE = "";
    

    ApiManager apiManager_new;
    View locationButtonView;
    private String MARKER_TYPE = "", DRIVER_MARKER = "";
    private Marker STILL_MARKER = null, MOVABLE_MARKER = null;
    private Polyline polyline = null;
    private List<LatLng> polydata = new ArrayList<>();

    ModelTrackRide modelTrackRide;
    private int width, height;
    private HashMap<String, String> data = new HashMap<>();
    private SamLocationRequestService samLocationRequestService;
    private Handler mHandeler = new Handler();
    private Runnable mRunnable;
    int callTracking = 0;
    private Vibrator vibrator;
    Polyline mPolyline;
    @BindView(R.id.cancel_btn)
    TextView cancel_btn;

    String booking_id = "";

    ApporioTaxiSlidingButton.OnTaxiSlidngListener onTaxiSlidngListener = new ApporioTaxiSlidingButton.OnTaxiSlidngListener() {
        @Override
        public void onAction() {
            try {
                sessionManager.setBooking_Id(booking_id);
                callStatusChangerApi(
                    modelRideInfo.getData().getBooking_status(), getIntent().getExtras().getString("" + IntentKeys.BOOKING_ID));
            } catch (Exception e) {
                Log.e("CallStatusChangerApi", "" + e.getMessage());
                //   Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancel() {
            eventsButton.startLoading("Cancelling Now");
            try {
                callCancelReasonsApi();
            } catch (Exception e) {
                //  Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            }

        }
    };

    private ApiManager apiManager;
    private SessionManager sessionManager;

    private void setMediaVibratorStart() throws Exception {
        // 05/12/2023
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        long[] pattern = {
            0,100,100,  0,100,100,  0,100,100,  0,100,100,  100,100,100,
            0,2000,100,
            0,100,100,  0,100,100,  0,100,100,  0,100,100,  100,100,100,
        };
        vibrator.vibrate(pattern, -1);
    }

    private void setMediaSoundStart() throws Exception {
        MediaPlayer player = MediaPlayer.create(this, R.raw.cancellation_sound);
        player.start();
    }

    private void setMediaChat() throws Exception {
        MediaPlayer player = MediaPlayer.create(this, R.raw.chat_sound);
        player.start();
    }

    ApiManager.APIFETCHER rideInfoApiFetcher = new ApiManager.APIFETCHER() {
        @Override
        public void onAPIRunningState(int a, String APINAME) {
            hideMainContent();
        }

        @Override
        public void onFetchComplete(Object script, String APINAME) {
            eventsButton.stopLoading();

            if (APINAME.equals("" + API_S.Tags.END_RIDE)) {
                try {
                    modelRideInfo = parseRideInfo(script);
                    startActivity(new Intent(TrackerActivity.this, FareActivity.class).putExtra("" + IntentKeys.BOOKING_ID, "" + modelRideInfo.getData().getId()).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                } catch (Exception e) {
                    //  Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_INDEFINITE).show();
                }
            } 
            // else if (APINAME.equals("" + API_S.Tags.DELIVERY_PLACE)) {
            //     showmainContent();
            // }
            // else if (APINAME.equals("" + API_S.Tags.DELIVERY_PLACE)) {
            //     try {
            //         startActivityForResult(new Intent(TrackerActivity.this, MeterImageActivity.class),
            //                 METER_IMAGE_ACTIVITY);
            //     } catch (Exception e) {
            //         // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_INDEFINITE).show();
            //     }
            // }
            else {
                try {
                    modelRideInfo = parseRideInfo(script);
                    if (modelRideInfo != null) {
                        if (modelRideInfo.getData().getBooking_status().equals("1003")
                                || modelRideInfo.getData().getBooking_status().equals("1006")
                                || modelRideInfo.getData().getBooking_status().equals("1007")
                                || modelRideInfo.getData().getBooking_status().equals("1008")
                                || modelRideInfo.getData().getBooking_status().equals("1111")) {
                            if (modelRideInfo.getData().getBooking_status().equals("1006")
                                || modelRideInfo.getData().getBooking_status().equals("1007")
                                || modelRideInfo.getData().getBooking_status().equals("1008")) {
                                setMediaSoundStart();
                                setMediaVibratorStart();
                            }
                            sessionManager.setStartTag(0);
                            sessionManager.setEndTag(0);
                            sessionManager.setPolylineAts("");
                        }
                        if (modelRideInfo.getData().getBooking_status().equals("1004")){
                            // sessionManager.setEndTag(1);
                        }
                        if (modelRideInfo.getData().getBooking_status().equals("1444")) {                            
                            sessionManager.setEndTag(2);
                            waitButton.setVisibility(View.VISIBLE);
                            showmainContent();
                        }
                        setViewAccordingToRideinfo(modelRideInfo);
                        // listenTrackingResponse();
                        if(sessionManager.getAppConfig().getData().getGeneral_config().getLat_long_storing_at()==1){
                            try {
                                startScreenRefreshInterval();
                            } catch (Exception e) {

                            }
                        }
                    }
                } catch (Exception e) {
                    //     Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFetchResultZero(String script, String APINAME) {
            eventsButton.stopLoading();
            //    Snackbar.make(root, "" + script, Snackbar.LENGTH_INDEFINITE).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_tracker);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ButterKnife.bind(this);
        apiManager = new ApiManager(rideInfoApiFetcher, this);
        apiManager_new = new ApiManager(this,this);
        sessionManager = new SessionManager(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationButtonView = mapFragment.getView();

        OneSignal.startInit(TrackerActivity.this)
                .autoPromptLocation(true)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        // eventsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


        eventsButton.setCancelable(true);
        eventsButton.setText("Arrived");

        eventsButton.setText("Delivery Place");

        if (sessionManager.getAppConfig().getData().getRide_config().isSlide_button()) {
            eventsButton.setButtonType(eventsButton.SLIDING_BUTTON);
        } else {
            eventsButton.setButtonType(eventsButton.STRICT_BUTTON);
        }
        //   eventsButton.setButtonType(eventsButton.SLIDING_BUTTON);
        eventsButton.setListeners(onTaxiSlidngListener);
        samLocationRequestService = new SamLocationRequestService(this);
        try {
            callRideInfoApi();
        } catch (Exception e) {
            //   Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT);
        }
        addressLayout.setOnClickListener((View view) -> {
            try {
                openGooglePlaceAPiDialoge();
            } catch (Exception e) {
                // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_INDEFINITE).show();
            }
        });
        //  phoneButton.setOnClickListener((View view) -> {       
        //  });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    callCancelReasonsApi();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // AppDatabase db = Room.databaseBuilder(getApplicationContext(),
        //         AppDatabase.class, "room_db").build();
        // HolderBookingDescriptionDao holderBookingDescriptionDao = db.holderBookingDescriptionDao();
        // userDao.insertrecord(new User(1, "Saulo", "Diniz"));




        back.setOnClickListener(v -> TrackerActivity.this.finish());

        chatButton.setOnClickListener((View view) -> {
            startActivity(new Intent(TrackerActivity.this, ChatActivity.class)
                .putExtra("" + IntentKeys.USER_IMAGE, "" + modelRideInfo.getData().getUser().getUserProfileImage())
                .putExtra("" + IntentKeys.USER_NAME, "" + modelRideInfo.getData().getUser().getFirst_name()+" "+modelRideInfo.getData().getUser().getLast_name())
                .putExtra("" + IntentKeys.STATUS, "Status ... ")
                .putExtra("" + IntentKeys.BOOKING_ID, "" + modelRideInfo.getData().getId()));

        });
        phoneButton.setOnClickListener((View view) -> {
            try {
                callingTask();
            } catch (Exception e) {
                //  Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
        navigationButton.setOnClickListener((View view) -> {
            try {
                if (modelRideInfo != null) {
                    if (Maputils.isPackageExisted("com.waze", TrackerActivity.this)) {
                        try {
                            showDialogForWazeMap();
                        } catch (Exception e) {
                            //  Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/maps?saddr=" + ATS.getLastLocation().getLatitude() + "," + ATS.getLastLocation().getLongitude() + "&daddr=" + modelRideInfo.getData().getStill_marker().getMarker_lat() + "," +  modelRideInfo.getData().getStill_marker().getMarker_long()));
                        intent.setPackage("com.google.android.apps.maps");
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    }
                }
            } catch (Exception e) {
                Log.d("" + TAG, "" + e.getMessage());
            }
        });
        sosButton.setOnClickListener((View view) -> {
            try {
                String SOS_IDS = "", SOS_NAMES = "", SOS_NUMBERS = "";
                for (int i = 0; i < modelRideInfo.getData().getSos().size(); i++) {
                    SOS_IDS = SOS_IDS + modelRideInfo.getData().getSos().get(i).getId() + "__";
                    SOS_NAMES = SOS_NAMES + modelRideInfo.getData().getSos().get(i).getName() + "__";
                    SOS_NUMBERS = SOS_NUMBERS + modelRideInfo.getData().getSos().get(i).getNumber() + "__";
                }
                startActivity(new Intent(TrackerActivity.this, SosActivity.class)
                        .putExtra("" + IntentKeys.SOS_ID, "" + SOS_IDS)
                        .putExtra("" + IntentKeys.SOS_NAMES, "" + SOS_NAMES)
                        .putExtra("" + IntentKeys.SOS_NUMBERS, "" + SOS_NUMBERS)
                        .putExtra("" + IntentKeys.BOOKING_ID, "" + modelRideInfo.getData().getId()));
            } catch (Exception e) {
                // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                Log.d("" + TAG, "Exception caught while calling API " + e.getMessage());
            }
        });
        childeButton.setOnClickListener((View view) -> {
            showDialogForChildDetails();
        });
        
        infoButton.setOnClickListener((View view) -> {
            showAlertDialogNote("" + modelRideInfo.getData().getAdditional_notes());
        });
        
        waitButton.setOnClickListener((View view) -> {
            HashMap<String, String> mData = new HashMap<>();
            mData.clear();
            mData.put("booking_id", "" + modelRideInfo.getData().getId());
            mData.put("latitude", "" + ATS.getLastLocation().getLatitude());
            mData.put("longitude", "" + ATS.getLastLocation().getLongitude());

            if (WAIT_TYPE == 0) {
                mData.put("type", "2");
            } else {
                mData.put("type", "" + WAIT_TYPE);
                Toast.makeText(TrackerActivity.this, "Tempo em espera", Toast.LENGTH_LONG).show();
            }

            try {
                new ApiManager(new ApiManager.APIFETCHER() {
                    @Override
                    public void onAPIRunningState(int a, String APINAME) {
                        hideMainContent();
                    }

                    @Override
                    public void onFetchComplete(Object script, String APINAME) {
                        ModelWaitRide modelWaitRide = SingletonGson.getInstance().fromJson("" + script, ModelWaitRide.class);
                        if (modelWaitRide.getResult().equals("1")) {
                            WAIT_TYPE = modelWaitRide.getType();
                            eventsButton.stopLoading();
                            if (modelWaitRide.getType() == 0) {
                                showmainContent();
                                Toast.makeText(TrackerActivity.this, "Não aplicado agora.", Toast.LENGTH_LONG).show();
                            }
                            if (modelWaitRide.getType() == 1) {
                                Toast.makeText(TrackerActivity.this, "Cronômetro inicializado..", Toast.LENGTH_LONG).show();
                                eventsButton.startLoading("" + getString(R.string.waiting_now));
                                hideMainContent();
                            }
                            if (modelWaitRide.getType() == 2) {
                                Toast.makeText(TrackerActivity.this, "Cronômetro finalizado..", Toast.LENGTH_LONG).show();
                                showmainContent();
                                eventsButton.stopLoading();
                            }
                        }
                    }

                    @Override
                    public void onFetchResultZero(String script, String APINAME) {
                        //   Snackbar.make(root, "" + script, Snackbar.LENGTH_INDEFINITE).show();
                    }
                }, this)._post(API_S.Tags.WAIT_RIDE, API_S.Endpoints.WAIT_RIDE, mData, sessionManager);
            } catch (Exception e) {
                //   Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_LONG).show();
            }            
            // AlertDialog.Builder builder = new AlertDialog.Builder(TrackerActivity.this);
            // // builder.setCancelable(false);
            // builder.setTitle(getResources().getString(R.string.waiting_now));
            // builder.setMessage(getResources().getString(R.string.starting_your_trip)).setPositiveButton(R.string.ok, (dialog, id) -> dialog.dismiss());
            // builder.create().show();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Location location) {
        try {
            if(sessionManager.getAppConfig().getData().getGeneral_config().getLat_long_storing_at()==2){

                if(sessionManager.getAppConfig().getData().getShow_polyline()){
                    settleUpDriverMarker(new LatLng(location.getLatitude(), location.getLongitude()), location.getBearing(),Config.getMarkerIcon(modelRideInfo.getData().getMovable_marker().getDriver_marker_name()));
                }else {
                    settleUpDriverMarker(new LatLng(location.getLatitude(), location.getLongitude()), location.getBearing(),Config.getMarkerIcon(modelRideInfo.getData().getMovable_marker().getDriver_marker_name()));

                    // CameraPosition cameraPosition = new CameraPosition.Builder()
                    //     .target(new LatLng(Double.parseDouble(""+location.getLatitude()), Double.parseDouble(""+location.getLongitude())))
                    //     .zoom(17)
                    //     .build();
                    //     mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(new LatLng(Double.parseDouble("" + modelRideInfo.getData().getStill_marker().getMarker_lat()), Double.parseDouble("" + "" + modelRideInfo.getData().getStill_marker().getMarker_long())));
                    builder.include(new LatLng(location.getLatitude(), location.getLongitude()));
                    LatLngBounds bounds = builder.build();

                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width - 100, height - 900, 300));
                }

                if (sessionManager.getAppConfig().getData().getShow_polyline()) {editPolyLine(new LatLng(location.getLatitude(), location.getLongitude()), new LatLng(Double.parseDouble("" + modelRideInfo.getData().getStill_marker().getMarker_lat()), Double.parseDouble("" + modelRideInfo.getData().getStill_marker().getMarker_long())), location.getBearing(), apiManager);
                }
            }
        } catch (Exception e) {
            // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String data) {
        ModelNotificationType modelNotificationType = SingletonGson.getInstance().fromJson("" + data, ModelNotificationType.class);

        if (modelNotificationType.getType() == 1) { // Ride related notification
            try {
                callRideInfoApi();
            } catch (Exception e) {
                //  Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        }

        if (modelNotificationType.getType() == 5) {
            try {
                ModelNotificationChat modelNotificationChat = SingletonGson.getInstance().fromJson("" + data, ModelNotificationChat.class);
                chatCounter.setVisibility(View.VISIBLE);
                if (!chatCounter.getText().toString().isEmpty()) {
                    chatCounter.setText("" + (Integer.parseInt("" + chatCounter.getText().toString()) + 1));
                    // mediaPlayer.start();
                    setMediaChat();
                } else {
                    chatCounter.setText("1");
                    // mediaPlayer.start();
                    setMediaChat();
                }
            } catch (Exception e) {
                //   Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                Log.d("" + TAG, "Exception caught while calling API " + e.getMessage());
            }
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setBuildingsEnabled(false);
        //  mGoogleMap.setMyLocationEnabled(false);
        mGoogleMap.setMyLocationEnabled(true);

        View locationButton = ((View) locationButtonView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        // and next place it, on bottom right (as Google Maps app)
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                locationButton.getLayoutParams();
        // position on right bottom
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        layoutParams.setMargins(0, 0, 30, 200);
        mGoogleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.lighter_weight_map));
        googleMap.setOnCameraMoveListener(() -> {
            routeOverlayView.onCameraMove(googleMap.getProjection(), googleMap.getCameraPosition());
        });

        try {
            Maputils.moveCameraToSpecificLocation(googleMap, new LatLng(ATS.getLastLocation().getLatitude(), ATS.getLastLocation().getLongitude()));
        } catch (Exception e) {
        }
    }

    //Room Database vai mandar isso aqui
    private void callRideInfoApi() throws Exception {
        HashMap<String, String> data = new HashMap<>();
        // data.put("booking_id", "518");
        data.put("booking_id", "" + getIntent().getExtras().getString("" + IntentKeys.BOOKING_ID));
        eventsButton.startLoading("" + getString(R.string.fetching_ride_details));
        apiManager._post("" + API_S.Tags.RIDE_INFO, "" + API_S.Endpoints.RIDE_INFO, data, sessionManager);
    }
    //Room Database vai mandar isso aqui
    private void callStatusChangerApi(String ride_status, String booking_id) throws Exception {
        this.booking_id = booking_id;
        sessionManager.setBooking_Id(booking_id);
        
        if (ride_status.equals("1002")) { // Viagem aceita
            callRideArriveApi(booking_id);
        }
        
        // if (ride_status.equals("1006") || ride_status.equals("1007") || ride_status.equals("1008")) { // Viagem aceita
        //     setMediaSoundStart();
        // }

        if (ride_status.equals("1003")) { // motorista chegou
            if (modelRideInfo.getData().isSend_meter_image()) {
                startActivityForResult(new Intent(TrackerActivity.this, MeterImageActivity.class), METER_IMAGE_ACTIVITY);
            } else {
                if (modelRideInfo.getData().getRide_otp_verify().equalsIgnoreCase("1")) {
                    Intent intent = new Intent(TrackerActivity.this, Ride_otp_Activity.class)
                            .putExtra("value", "2")
                            .putExtra("ride_otp", modelRideInfo.getData().getUser().getRide_otp()).putExtra("booking_id", "" + modelRideInfo.getData().getId());
                    startActivityForResult(intent, OTP_REQUEST_CODE);
                }else{
                    if (sessionManager.getAppConfig().getData().getGeneral_config().getLat_long_storing_at() == 2) {
                        if(sessionManager.getStartTag()==1){
                            try {
                                callRideStartApi(booking_id);
                            } catch (Exception e) {
                                // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            }
                        }else {
                            ATS.startTrip("" + booking_id+""+getPackageName(), new AtsOnTripSetListener() {
                                @Override
                                public void onSuccess(String message) {
                                    try {
                                        sessionManager.setStartTag(1);
                                        callRideStartApi(booking_id);
                                    } catch (Exception e) {
                                        // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailed(String message) {
                                    // Snackbar.make(root, "ATS SERVER ERROR: " + message, Snackbar.LENGTH_INDEFINITE).show();
                                }
                            });
                        }
                    } else {
                        try {
                            callRideStartApi(booking_id);
                        } catch (Exception e) {
                            // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        }
        
        if (ride_status.equals("1004")) {
            callRideDeliveryPlaceApi(booking_id);            
            // essionManager.setEndTag(1);
            // eventsButton.setText("" + "Finalizar viagem");            
            // actionLayout.setVisibility(View.VISIBLE);            
            // waitButton.setVisibility(View.VISIBLE);
        }

        if (ride_status.equals("1444")) {
            // no local de coleta            
            if (modelRideInfo.getData().isSend_meter_image()) {
                startActivityForResult(new Intent(TrackerActivity.this, MeterImageActivity.class), METER_IMAGE_ACTIVITY);
            } else if (sessionManager.getEndTag() == 2) {
                startActivityForResult(new Intent(TrackerActivity.this, MeterImageActivity.class), DELIVERY_PLACE_ACTIVITY);
            } else {
                if (sessionManager.isShow_toll_dialog()) {
                    dialogTollAmount();
                } else {
                    eventsButton.startLoading("" + getString(R.string.ending_your_trip));
                    if (sessionManager.getAppConfig().getData().getGeneral_config().getLat_long_storing_at() == 2) {
                        if(sessionManager.getEndTag() == 1){
                            try {
                                callRideEndApi("" + booking_id, "" + sessionManager.getPolylineAts());
                            } catch (Exception e) {
                                eventsButton.stopLoading();
                                // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            }
                        } else {
                            ATS.endTrip("" + booking_id + "" + getPackageName(), new AtsOnTripSetListener() {
                                @Override
                                public void onSuccess(String message) {
                                    Log.e("EndTrip", "" + message);
                                    try {
                                        sessionManager.setPolylineAts(message);
                                        sessionManager.setEndTag(1);
                                        callRideEndApi("" + booking_id, "" + message);
                                    } catch (Exception e) {
                                        eventsButton.stopLoading();
                                        // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailed(String message) {
                                    eventsButton.startLoading("" + getString(R.string.cancelling_your_ride));
                                    hideMainContent();
                                    //  Snackbar.make(root, "ATS SERVER ERROR: " + message, Snackbar.LENGTH_INDEFINITE).show();
                                }
                            });
                        }
                    } else {
                        try {
                            callRideEndApi("" + booking_id, "");
                        } catch (Exception e) {
                            eventsButton.stopLoading();
                            //  Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        }
    }
     //Room Database vai mandar isso aqui
    public void editPolyLine(LatLng currentLocation, LatLng destinationLocation, float bearing, ApiManager apiManager) {

        if (stillPolyline == null) {
            fetchRoute(currentLocation, destinationLocation, apiManager);


        } else {
            int indexOnPath = PolyUtil.locationIndexOnPath(currentLocation, stillPolyline.getPoints(), true, 10);
            if (indexOnPath != -1) {
                
                // if(ANIMATE_CAMERA){
                //     CameraPosition.Builder b = CameraPosition.builder()
                //             .zoom(15.0F)
                //             .target(currentLocation).bearing(bearing);
                //     CameraUpdate cu = CameraUpdateFactory.newCameraPosition(b.build());
                //     mGoogleMap.animateCamera(cu);
                //                 
                // }
                // mGoogleMap.clear();
                
                List<LatLng> previousPoints = stillPolyline.getPoints();
                for (int i = 0; i <= indexOnPath; i++) {
                    previousPoints.remove(i);
                }
                previousPoints.set(0, currentLocation);
                stillPolyline.remove();
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.width(8);
                polylineOptions.color(Color.BLACK);
                for (LatLng polypoint : previousPoints) {
                    polylineOptions.add(polypoint);
                }
                stillPolyline = mGoogleMap.addPolyline(polylineOptions);
            } else {
                fetchRoute(currentLocation, destinationLocation, apiManager);
            }

        }
    }
     //Room Database vai mandar isso aqui
    private void fetchRoute(LatLng currentLocation, LatLng destination, ApiManager apiManager) {
        // Toast.makeText(this, "Re-Routing . . .", Toast.LENGTH_SHORT).show();
        HashMap<String, String> data = new HashMap<>();
        data.put("from_latitude", "" + currentLocation.latitude);
        data.put("from_longitude", "" + currentLocation.longitude);
        data.put("to_latitude", "" + destination.latitude);
        data.put("to_longitude", "" + destination.longitude);
        data.put("type", "2");

        try {
            apiManager_new._postForTracking(API_S.Tags.POLYLINE_API, API_S.Endpoints.POLYLINE_API, data, sessionManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 //Room Database vai mandar isso aqui
    private void callRideArriveApi(String booking_id) throws Exception {
        HashMap<String, String> body = new HashMap<>();
        body.put("booking_id", "" + booking_id);
        body.put("latitude", "" + ATS.getLastLocation().getLatitude());
        body.put("longitude", "" + ATS.getLastLocation().getLongitude());
        body.put("accuracy", "" + ATS.getLastLocation().getAccuracy());
        eventsButton.startLoading("" + getString(R.string.arriving_now));
        apiManager._post(API_S.Tags.ARRIVE_RIDE, API_S.Endpoints.ARRIVE_RIDE, body, sessionManager);
    }
 //Room Database vai mandar isso aqui
    private void callRideStartApi(String bookin_id) throws Exception {
        HashMap<String, String> body = new HashMap<String, String>();
        body.put("booking_id", "" + bookin_id);
        body.put("latitude", "" + ATS.getLastLocation().getLatitude());
        body.put("longitude", "" + ATS.getLastLocation().getLongitude());
        body.put("accuracy", "" + ATS.getLastLocation().getAccuracy());
        eventsButton.startLoading("" + getString(R.string.starting_your_trip));
        apiManager._post(API_S.Tags.START_RIDE, API_S.Endpoints.START_RIDE, body, sessionManager);
    }
 //Room Database vai mandar isso aqui
    private void callRideEndApi(String booking_id, String polyline) throws Exception {
        HashMap<String, String> data = new HashMap<>();
        data.put("booking_id", "" + booking_id);
        data.put("latitude", "" + ATS.getLastLocation().getLatitude());
        data.put("longitude", "" + ATS.getLastLocation().getLongitude());
        data.put("accuracy", "" + ATS.getLastLocation().getAccuracy());
        data.put("manual_toll_charge", "" + TOLL_AMOUNT);
        if(sessionManager.getAppConfig().getData().getGeneral_config().getLat_long_storing_at() == 2){
            data.put("booking_polyline", "" + polyline);
        }
        eventsButton.startLoading("" + getString(R.string.ending_your_trip));
        apiManager._post(API_S.Tags.END_RIDE, API_S.Endpoints.END_RIDE, data, sessionManager);
    }
 //Room Database vai mandar isso aqui
    private void callRideDeliveryPlaceApi(String booking_id) throws Exception {
        try {
            HashMap<String, String> data = new HashMap<>();
            data.put("booking_id", "" + booking_id);
            data.put("latitude", "" + ATS.getLastLocation().getLatitude());
            data.put("longitude", "" + ATS.getLastLocation().getLongitude());
            data.put("accuracy", "" + ATS.getLastLocation().getAccuracy());
            data.put("manual_toll_charge", "" + TOLL_AMOUNT);
            // if (sessionManager.getAppConfig().getData().getGeneral_config().getLat_long_storing_at() == 2) {
            //     data.put("booking_polyline", "" + polyline);
            // }
            eventsButton.startLoading("" + getString(R.string.arriving_now));
            apiManager._post(API_S.Tags.DELIVERY_PLACE, API_S.Endpoints.DELIVERY_PLACE, data, sessionManager);
        } catch (Exception e) {
        }
    }


 //Room Database vai mandar isso aqui
    private void callRideStartWithMeterImage(String booking_id, String meter_value, String meter_image) throws Exception {
        HashMap<String, String> mData = new HashMap<>();
        mData.put("booking_id", "" + booking_id);
        mData.put("latitude", "" + ATS.getLastLocation().getLatitude());
        mData.put("longitude", "" + ATS.getLastLocation().getLongitude());
        mData.put("accuracy", "" + ATS.getLastLocation().getAccuracy());
        mData.put("send_meter_value", "" + meter_value);
        HashMap<String, File> mFile = new HashMap<>();
        mFile.put("send_meter_image", new File("" + meter_image));

        if (sessionManager.getAppConfig().getData().getGeneral_config().getLat_long_storing_at() == 2) {
            if (sessionManager.getStartTag() == 1){
                try {
                    eventsButton.startLoading("" + getString(R.string.starting_your_trip));
                    apiManager._post_image(API_S.Tags.START_RIDE, API_S.Endpoints.START_RIDE, mData, mFile, sessionManager);
                } catch (Exception e) {
                    // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_INDEFINITE).show();
                }
            } else {
                ATS.startTrip("" + modelRideInfo.getData().getId() + "" + getPackageName(), new AtsOnTripSetListener() {
                    @Override
                    public void onSuccess(String message) {
                        try {
                            sessionManager.setStartTag(1);
                            eventsButton.startLoading("" + getString(R.string.starting_your_trip));
                            apiManager._post_image(API_S.Tags.START_RIDE, API_S.Endpoints.START_RIDE, mData, mFile, sessionManager);
                        } catch (Exception e) {
                            // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_INDEFINITE).show();
                        }
                    }

                    @Override
                    public void onFailed(String message) {
                        //  Snackbar.make(root, "" + message, Snackbar.LENGTH_INDEFINITE).show();
                    }
                });
            }
        } else {
            try {
                eventsButton.startLoading("" + getString(R.string.starting_your_trip));
                apiManager._post_image(API_S.Tags.START_RIDE, API_S.Endpoints.START_RIDE, mData, mFile, sessionManager);
            } catch (Exception e) {
                //  Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_INDEFINITE).show();
            }
        }

    }
 //Room Database vai mandar isso aqui
    private void callRideEndApiWithImage(String booking_id, String polyline, String meter_value, String meter_image) throws Exception {
        HashMap<String, String> mData = new HashMap<>();
        mData.clear();
        mData.put("booking_id", "" + booking_id);
        mData.put("latitude", "" + ATS.getLastLocation().getLatitude());
        mData.put("longitude", "" + ATS.getLastLocation().getLongitude());
        mData.put("accuracy", "" + ATS.getLastLocation().getAccuracy());
        mData.put("manual_toll_charge", "" + TOLL_AMOUNT);
        mData.put("send_meter_value", "" + meter_value);
        if(sessionManager.getAppConfig().getData().getGeneral_config().getLat_long_storing_at() == 2){
            mData.put("booking_polyline", "" + polyline);
        }
        HashMap<String, File> mFile = new HashMap<>();
        mFile.put("send_meter_image", new File("" + meter_image));
        apiManager._post_image(API_S.Tags.END_RIDE, API_S.Endpoints.END_RIDE, mData, mFile, sessionManager);

        // if (driverDeliveryOtp == true) {
        //     data.put("receiver_name", etReceiverName.getText().toString());
        //     data.put("receiver_otp", etOTP.getText().toString());
        //     mFile.put("receiver_image", new File(imagePathCompressed));
        //     System.out.println("IMAGE IMAGE" + mFile.toString());
        // }
        
        // if (openFareActivityClass == 0) {
        //     callRideEndApi(mData, mFile);
        //     openFareActivityClass = 1;
        // }


    }
 //Room Database vai mandar isso aqui
    private void callDeliveryPlaceImage(String booking_id, String polyline, String meter_value, String meter_image) throws Exception {
        HashMap<String, String> mData = new HashMap<>();
        mData.clear();
        mData.put("booking_id", "" + booking_id);
        mData.put("latitude", "" + ATS.getLastLocation().getLatitude());
        mData.put("longitude", "" + ATS.getLastLocation().getLongitude());
        mData.put("accuracy", "" + ATS.getLastLocation().getAccuracy());
        mData.put("manual_toll_charge", "" + TOLL_AMOUNT);
        mData.put("delivery_receiver_name", "" + meter_value);
        if (sessionManager.getAppConfig().getData().getGeneral_config().getLat_long_storing_at() == 2) {
            mData.put("booking_polyline", "" + polyline);
        }
        HashMap<String, File> mFile = new HashMap<>();
        mFile.put("send_delivery_receipt_image", new File("" + meter_image));
        apiManager._post_image(API_S.Tags.END_RIDE, API_S.Endpoints.END_RIDE, mData, mFile, sessionManager);
    }
 //Room Database vai mandar isso aqui
    private void callCancelReasonsApi() throws Exception {
        new ApiManager(new ApiManager.APIFETCHER() {
            @Override
            public void onAPIRunningState(int a, String APINAME) {
                cancel_btn.setVisibility(View.GONE);
                eventsButton.startLoading("" + getString(R.string.cancelling_your_ride));
                hideMainContent();
            }

            @Override
            public void onFetchComplete(Object script, String APINAME) {
                try {
                    ModelCancelReasion modelCancelReasion = SingletonGson.getInstance().fromJson("" + script, ModelCancelReasion.class);
                    showCancelReasonsListDialoge(modelCancelReasion);
                } catch (Exception e) {
                    //   Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFetchResultZero(String script, String APINAME) {
                eventsButton.stopLoading();
                customerLayout.setVisibility(View.VISIBLE);
                addressLayout.setVisibility(View.VISIBLE);
                actionLayout.setVisibility(View.VISIBLE);
                //    Snackbar.make(root, "" + script, Snackbar.LENGTH_INDEFINITE).show();
            }
        }, this)._post(API_S.Tags.CANCEL_REASION, API_S.Endpoints.CANCLE_REASION, null, sessionManager);
    }
 //Room Database vai mandar isso aqui
    private void calCancelRideApi(String bookid_id, String cancel_reasn_id) throws Exception {
        HashMap<String, String> data = new HashMap<>();
        data.put("booking_id", "" + bookid_id);
        data.put("cancel_reason_id", "" + cancel_reasn_id);
        new ApiManager(new ApiManager.APIFETCHER() {
            @Override
            public void onAPIRunningState(int a, String APINAME) {
                eventsButton.startLoading("" + getString(R.string.cancelling_your_ride));
                hideMainContent();
            }

            @Override
            public void onFetchComplete(Object script, String APINAME) {
                sessionManager.setStartTag(0);
                sessionManager.setEndTag(0);
                sessionManager.setPolylineAts("");
                finish();
            }

            @Override
            public void onFetchResultZero(String script, String APINAME) {
                eventsButton.stopLoading();
                customerLayout.setVisibility(View.VISIBLE);
                addressLayout.setVisibility(View.VISIBLE);
                actionLayout.setVisibility(View.VISIBLE);
                //  Snackbar.make(root, "" + script, Snackbar.LENGTH_INDEFINITE).show();
            }
        }, this)._post(API_S.Tags.CANCEL_RIDE, API_S.Endpoints.CANCEL_RIDE, data, sessionManager);
    }
 //Room Database vai mandar isso aqui
    private void callChangeAddressApi(HashMap<String, String> mdata) throws Exception {
        eventsButton.startLoading("" + getString(R.string.updating_location));
        apiManager._post(API_S.Tags.CHANGE_ADDRESS, API_S.Endpoints.CHANGE_ADDRESS, mdata, sessionManager);
    }
 //Room Database vai mandar isso aqui
    private void callPolyLineApiAccordingtoStatus(String rideStatus, LatLng destinationLocation) throws Exception {
        if (rideStatus.equals("1002")) { // viagem aceita
            callPolyLineApiWithoutAnimation(new LatLng(ATS.getLastLocation().getLatitude(), ATS.getLastLocation().getLongitude()), destinationLocation);
        }
        if (rideStatus.equals("1003")) { // motorista chegou
            // callPolyLineApiWithoutAnimation;hAnmation(new LatLng(ATS.getLastLocation().getLatitude(), ATS.getLastLocation().getLongitude()), destinationLocation);
            callPolyLineApiWithoutAnimation(new LatLng(ATS.getLastLocation().getLatitude(), ATS.getLastLocation().getLongitude()), destinationLocation);
        }
        if (rideStatus.equals("1004")) {
            // viagem começou
            callPolyLineApiWithoutAnimation(new LatLng(ATS.getLastLocation().getLatitude(), ATS.getLastLocation().getLongitude()), destinationLocation);
        }
        if (rideStatus.equals("1444")) {
            // no local de coleta
            callPolyLineApiWithoutAnimation(
                    new LatLng(ATS.getLastLocation().getLatitude(), ATS.getLastLocation().getLongitude()),
                    destinationLocation);
        }
    }
 //Room Database vai mandar isso aqui
    private void callPolylineApiWithAnmation(LatLng pickLocation, LatLng dropLocation) {


        new ApiManager(new ApiManager.APIFETCHER() {
            @Override
            public void onAPIRunningState(int a, String APINAME) {
                Log.d(TAG, "onAPIRunningState: ");
            }

            @Override
            public void onFetchComplete(Object script, String APINAME) {
                clearPolyLine();
                ModelGoogleDirection modelGoogleDirection = SingletonGson.getInstance().fromJson("" + script, ModelGoogleDirection.class);
                if (modelGoogleDirection.getRoutes().size() > 0) {
                    List<LatLng> points = PolyUtil.decode(modelGoogleDirection.getRoutes().get(0).getOverview_polyline().getPoints());
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (LatLng polypoint : points) {
                        builder.include(polypoint);
                    }
                    LatLngBounds bounds = builder.build();

                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
                    animatedPolyLine = new Route.Builder(routeOverlayView)
                            .setRouteType(RouteOverlayView.RouteType.PATH)
                            .setCameraPosition(mGoogleMap.getCameraPosition())
                            .setProjection(mGoogleMap.getProjection())
                            .setLatLngs(points)
                            .setBottomLayerColor(Color.GRAY)
                            .setTopLayerColor(Color.BLACK)
                            .create();
                }
            }

            @Override
            public void onFetchResultZero(String script, String APINAME) {

            }
        }, this)._getgoogleAPI("DIRECTION_API", "" + FetchUrl.getUrl(pickLocation, dropLocation, this));

    }
 //Room Database vai mandar isso aqui
    public void CreatePolyLine(String polylineCode){


        if (!polylineCode.equals("")) {
            // setting polyline
            clearPolyLine();
            PolylineOptions polylineOptions = new PolylineOptions();
            // polylineOptions.color(Color.parseColor("#" + polylineColor));
            // polylineOptions.width(Float.parseFloat("" + Polylinewidth));

            polylineOptions.color(Color.BLACK);
            polylineOptions.width(Float.parseFloat("8"));

            polydata = PolyUtil.decode(polylineCode);
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (LatLng polypoint : polydata) {
                polylineOptions.add(polypoint);
                builder.include(polypoint);
            }
            LatLngBounds bounds = builder.build();
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));

            stillPolyline = mGoogleMap.addPolyline(polylineOptions);
            // for (int i = 0; i < polydata.size(); i++) {
            //     polylineOptions.add(polydata.get(i));
            // }
            // if (polyline != null) {
            //     polyline.remove();
            //     polyline = googleMap.addPolyline(polylineOptions);
            // } else {
            //     polyline = googleMap.addPolyline(polylineOptions);
            // }

        }


    }
     //Room Database vai mandar isso aqui
    private void callPolyLineApiWithoutAnimation(LatLng pickLocation, LatLng dropLocation) {
        new ApiManager(new ApiManager.APIFETCHER() {
            @Override
            public void onAPIRunningState(int a, String APINAME) {
            }

            @Override
            public void onFetchComplete(Object script, String APINAME) {
                clearPolyLine();
                ModelGoogleDirection modelGoogleDirection = SingletonGson.getInstance().fromJson("" + script, ModelGoogleDirection.class);
                if (modelGoogleDirection.getRoutes().size() > 0) {
                    PolylineOptions polylineOptions = new PolylineOptions();
                    polylineOptions.width(8);
                    polylineOptions.color(Color.BLACK);
                    List<LatLng> points = PolyUtil.decode(modelGoogleDirection.getRoutes().get(0).getOverview_polyline().getPoints());
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (LatLng polypoint : points) {
                        polylineOptions.add(polypoint);
                        builder.include(polypoint);
                    }
                    LatLngBounds bounds = builder.build();
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));

                    stillPolyline = mGoogleMap.addPolyline(polylineOptions);
                }
            }

            @Override
            public void onFetchResultZero(String script, String APINAME) {
                //   Snackbar.make(root, "" + script, Snackbar.LENGTH_LONG).show();
            }
        }, this)._getgoogleAPI("DIRECTION_API", "" + FetchUrl.getUrl(pickLocation, dropLocation, this));

    }

    // definir informações de visualização de acordo com a viagem
    private void setViewAccordingToRideinfo(ModelRideInfo modelRideInfo) throws Exception {

        this.modelRideInfo = modelRideInfo;
        this.WAIT_TYPE = modelRideInfo.getData().getOnride_waiting_type();
        
        if (modelRideInfo.getData().getBooking_status().equals("1006")){
            infodialog(getResources().getString(R.string.user_has_cancel_the_ride));
        }
        if (modelRideInfo.getData().getBooking_status().equals("1007")){
            infodialog(getResources().getString(R.string.cancel_by_driver));
        }
        if (modelRideInfo.getData().getBooking_status().equals("1008")){
            infodialog("" + modelRideInfo.getData().getLocation().getTrip_status_text());
        }

        customerNameTxt.setText("Contato - " + modelRideInfo.getData().getUser().getFirst_name());
        // customerPhoneTxt.setText("" + modelRideInfo.getData().getUser().getUserPhone() + " " + modelRideInfo.getData().getId());
        customerPhoneTxt.setText("" + "Ordem de coleta - " + modelRideInfo.getData().getId());
        // paymentMethodNameTxt.setText("" + modelRideInfo.getData().getPayment_method().getPayment_method());
        Glide.with(this).load("" + modelRideInfo.getData().getUser().getUserProfileImage()).into(customerImage);
        rating.setRating(Float.parseFloat("" + modelRideInfo.getData().getUser().getRating()));
        locationText.setText("" + modelRideInfo.getData().getLocation().getLocation_text());
        
        eventsButton.setText("" + modelRideInfo.getData().getLocation().getTrip_status_text());
        // eventsButton.setCancelable(modelRideInfo.getData().isCancelable());
        
        eventsButton.setCancelable(false);        
        if (modelRideInfo.getData().isCancelable()){
            cancel_btn.setVisibility(View.VISIBLE);
        }else {
            // cancel_btn.setVisibility(View.GONE); // não mostrar o botão de ocorrências
        }
        locationEditIcon.setVisibility(modelRideInfo.getData().getLocation().isLocation_editable() ? View.VISIBLE : View.GONE);
        addressLayout.setClickable(modelRideInfo.getData().getLocation().isLocation_editable());
        if (stillMarker != null) {
            stillMarker.remove();
        }
        if(sessionManager.getAppConfig().getData().getGeneral_config().getLat_long_storing_at() == 2){
            if (modelRideInfo.getData().getLocation().getLocation_text().equals("")) {
                clearPolyLine();
            }
            stillMarker = Maputils.setNormalmarker(mGoogleMap, new LatLng(Double.parseDouble("" + modelRideInfo.getData().getStill_marker().getMarker_lat()), Double.parseDouble("" + "" + modelRideInfo.getData().getStill_marker().getMarker_long())), R.drawable.destonation_marker);
            if (sessionManager.getAppConfig().getData().getShow_polyline()) {
                callPolyLineApiAccordingtoStatus(modelRideInfo.getData().getBooking_status(), new LatLng(Double.parseDouble("" + modelRideInfo.getData().getStill_marker().getMarker_lat()), Double.parseDouble("" + modelRideInfo.getData().getStill_marker().getMarker_long())));
            }
        }


        // infoButton.setVisibility(modelRideInfo.getData().getAdditional_notes() == null ? View.GONE : View.VISIBLE);
        infoButton.setVisibility(View.VISIBLE);
        
        // childeButton.setVisibility(modelRideInfo.getData().getFamily_member_details().isFamily_visibility() ? View.VISIBLE : View.GONE);
        childeButton.setVisibility(View.GONE);

        if (WAIT_TYPE == 1) {
            hideMainContent();
        } else {
            showmainContent();
        }
    }

    private synchronized ModelRideInfo parseRideInfo(Object script) throws Exception {
        ModelRideInfo mModelRideInfo = SingletonGson.getInstance().fromJson("" + script, ModelRideInfo.class);
        return mModelRideInfo;
    }

    private void settleUpDriverMarker(LatLng latLng, float bearing, int markerImage) throws Exception {
        if (drivermarker == null) {
            drivermarker = Maputils.setNormalmarker(mGoogleMap, latLng, markerImage);
            Maputils.animateMarker(latLng, mGoogleMap, drivermarker, bearing, ""+markerImage);
        } else {
            Maputils.animateMarker(latLng, mGoogleMap, drivermarker, bearing, ""+markerImage);
        }
    }

    private void hideMainContent() {
        // customerLayout.setVisibility(View.GONE);
        addressLayout.setVisibility(View.GONE);
        // actionLayout.setVisibility(View.GONE);
    }


    private void showmainContent() {
        customerLayout.setVisibility(View.VISIBLE);
        addressLayout.setVisibility(View.GONE); // não mostrar o endereço do local de entrega - destino
        actionLayout.setVisibility(View.VISIBLE);
    }

    private void clearPolyLine() {
        try {
            if (stillPolyline != null) {
                stillPolyline.remove();
            }
            if (animatedPolyLine != null) {
                animatedPolyLine.remove();
            }
        } catch (Exception e) {
            //   Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    private void showCancelReasonsListDialoge(ModelCancelReasion modelCancelReasion) {
        // String[] arr = new String[modelCancelReasion.getData().size()];
        // for (int i = 0; i < modelCancelReasion.getData().size(); i++) {
        //     arr[i] = "" + modelCancelReasion.getData().get(i).getReason();
        // }
        // AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // builder.setTitle("" + getResources().getString(R.string.cancel_ride))
        //         .setItems(arr, new DialogInterface.OnClickListener() {
        //             public void onClick(DialogInterface dialog, int which) {
        //                 try {
        //                     calCancelRideApi("" + getIntent().getExtras().getString("" + IntentKeys.BOOKING_ID), "" + modelCancelReasion.getData().get(which).getId());
        // } catch (Exception e) {
        // // Toast.makeText(TrackerActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        // }
        // }
        // }).setNegativeButton("" + getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
        // @Override
        //  public void onClick(DialogInterface dialog, int which) {
        //      customerLayout.setVisibility(View.VISIBLE);
        //      addressLayout.setVisibility(View.VISIBLE);
        //      actionLayout.setVisibility(View.VISIBLE);
        //      eventsButton.stopLoading();
        // }
        // }).setCancelable(false).create().show();

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(TrackerActivity.this);
        builderSingle.setTitle(""+getResources().getString(R.string.cancel_ride));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(TrackerActivity.this, android.R.layout.select_dialog_singlechoice);
        for (int i = 0; i < modelCancelReasion.getData().size(); i++) {
            arrayAdapter.add("" + modelCancelReasion.getData().get(i).getReason());
        }
        builderSingle.setNegativeButton(TrackerActivity.this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                customerLayout.setVisibility(View.VISIBLE);
                addressLayout.setVisibility(View.VISIBLE);
                actionLayout.setVisibility(View.VISIBLE);
                eventsButton.stopLoading();
                cancel_btn.setVisibility(View.VISIBLE);
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // try {
                //     data.clear();
                //     data.put("booking_id", "" + modelRideInfo.getData().getId());
                //     data.put("cancel_reason_id", "" + modelCancelReasion.getData().get(which).getId())
                //     data.put("cancel_charges", "" + modelCancelReasion.getCancel_charges());
                //     apiManager._post(API_S.Tags.CANCEL_RIDE, API_S.Endpoints.CANCEL_RIDE, data, sessionManager);
                //  } catch (Exception e) {
                //      // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                //      ApporioLog.logE("" + TAG, "Exception caught while calling API " + e.getMessage());
                // }

                try {
                    calCancelRideApi("" + getIntent().getExtras().getString("" + IntentKeys.BOOKING_ID), "" + modelCancelReasion.getData().get(which).getId());
                    customerLayout.setVisibility(View.VISIBLE);
                    addressLayout.setVisibility(View.VISIBLE);
                    actionLayout.setVisibility(View.VISIBLE);
                    eventsButton.stopLoading();
                    eventsButton.setCancelable(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });


        builderSingle.show();




    }

    private void openGooglePlaceAPiDialoge() throws Exception {
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), Config.getEncodedApiKey(getApplicationContext()));
        }
        List<Place.Field> fields = Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this);
        startActivityForResult(intent, 1909);
    }

    public void showDialogForWazeMap() throws Exception {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        dialog.setCancelable(true);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_for_navigation_types);
        dialog.findViewById(R.id.google_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/maps?saddr=" + ATS.getLastLocation().getLatitude() + "," + ATS.getLastLocation().getLongitude() + "&daddr=" + modelRideInfo.getData().getStill_marker().getMarker_lat() + "," + modelRideInfo.getData().getStill_marker().getMarker_long()));
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.waze_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Iniciar Waze
                    String mapRequest = "https://waze.com/ul?" + "q=" + modelRideInfo.getData().getStill_marker().getMarker_lat() + "," + modelRideInfo.getData().getStill_marker().getMarker_long() + "&navigate=yes&zoom=17";
                    Log.d("**waze url=>", mapRequest);
                    Uri gmmIntentUri = Uri.parse(mapRequest);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.waze");
                    startActivity(mapIntent);

                } catch (ActivityNotFoundException e) {
                    // Se o Waze não estiver instalado, abra-o no Google Play
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
                    startActivity(intent);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showDialogForChildDetails() {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Setting dialogview
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);

        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
        dialog.setTitle(null);
        dialog.setCancelable(false);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_for_child_details);

        TextView tvChildName = dialog.findViewById(R.id.tvChildName);
        TextView tvChildPhone = dialog.findViewById(R.id.tvChildPhone);
        LinearLayout llChildPhoneNumber = dialog.findViewById(R.id.llChildPhoneNumber);

        tvChildName.setText("" + modelRideInfo.getData().getFamily_member_details().getFamily_member_name());
        tvChildPhone.setText("" + modelRideInfo.getData().getFamily_member_details().getFamily_member_phoneNumber());

        Button btnOkay = dialog.findViewById(R.id.btnOkay);

        llChildPhoneNumber.setOnClickListener(v -> {
            try {
                callingTaskForChild();
            } catch (Exception e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        });

        btnOkay.setOnClickListener(v -> {
            dialog.dismiss();
        });


        dialog.show();
    }

    private void showAlertDialogNote(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TrackerActivity.this);
        builder.setCancelable(false);
        builder.setTitle(getResources().getString(R.string.additional_note_text));
        builder.setMessage(message)
                .setPositiveButton(R.string.ok, (dialog, id) -> dialog.dismiss());
        builder.create().show();
    }

    private void dialogTollAmount() {
        Window window;
        Dialog dialog1;
        dialog1 = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        window = dialog1.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.popup_toll_amount);


        EditText editText = dialog1.findViewById(R.id.edit_text);
        LinearLayout ll_ok_accept = dialog1.findViewById(R.id.ll_ok_accept);
        Button any_toll = dialog1.findViewById(R.id.any_toll);
        Button no_toll = dialog1.findViewById(R.id.no_toll);

        any_toll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setVisibility(View.VISIBLE);
                any_toll.setVisibility(View.GONE);
                no_toll.setVisibility(View.GONE);
                ll_ok_accept.setVisibility(View.VISIBLE);

            }
        });




        no_toll.setOnClickListener(view -> {
            TOLL_AMOUNT = "";
            dialog1.dismiss();
        });

        ll_ok_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(TrackerActivity.this, "insira um valor válido", Toast.LENGTH_SHORT).show();
                } else {
                    TOLL_AMOUNT = "" + editText.getText().toString();
                    dialog1.dismiss();
                }
            }
        });
        dialog1.setCancelable(false);
        dialog1.show();
        dialog1.setOnDismissListener((DialogInterface dialog) -> {

            if (sessionManager.getAppConfig().getData().getGeneral_config().getLat_long_storing_at() == 2) {
                if(sessionManager.getEndTag() == 1){
                    try {
                        if (modelRideInfo.getData().isSend_meter_image()) {
                            eventsButton.startLoading("" + getString(R.string.ending_your_trip));
                            callRideEndApiWithImage("" + modelRideInfo.getData().getId(), "" + sessionManager.getPolylineAts(), METER_VALUE, METER_IMAGE);
                        } else {
                            callRideEndApi("" + modelRideInfo.getData().getId(), "" + sessionManager.getPolylineAts());
                        }

                    } catch (Exception e) {
                        eventsButton.startLoading("" + getString(R.string.cancelling_your_ride));
                        hideMainContent();
                    }
                }else {
                    ATS.endTrip("" + modelRideInfo.getData().getId()+""+getPackageName(), new AtsOnTripSetListener() {
                        @Override
                        public void onSuccess(String message) {
                            Log.e("EndTrip", "" + message);
                            try {
                                sessionManager.setEndTag(1);
                                sessionManager.setPolylineAts(message);
                                if (modelRideInfo.getData().isSend_meter_image()) {
                                    eventsButton.startLoading("" + getString(R.string.ending_your_trip));
                                    callRideEndApiWithImage("" + modelRideInfo.getData().getId(), "" + message, METER_VALUE, METER_IMAGE);
                                } else {
                                    callRideEndApi("" + modelRideInfo.getData().getId(), "" + message);
                                }

                            } catch (Exception e) {
                                eventsButton.startLoading("" + getString(R.string.cancelling_your_ride));
                                hideMainContent();
                                //      Snackbar.make(root, "ATS SERVER ERROR: " + message, Snackbar.LENGTH_INDEFINITE).show();
                            }
                        }

                        @Override
                        public void onFailed(String message) {
                            hideMainContent();
                            //   Snackbar.make(root, "" + message, Snackbar.LENGTH_INDEFINITE).show();
                        }
                    });
                }
            } else {
                try {
                    if (modelRideInfo.getData().isSend_meter_image()) {
                        eventsButton.startLoading("" + getString(R.string.ending_your_trip));
                        callRideEndApiWithImage("" + modelRideInfo.getData().getId(), "", METER_VALUE, METER_IMAGE);
                    } else {
                        callRideEndApi("" + modelRideInfo.getData().getId(), "");
                    }

                } catch (Exception e) {
                    eventsButton.startLoading("" + getString(R.string.cancelling_your_ride));
                    hideMainContent();
                    //   Snackbar.make(root, "ATS SERVER ERROR: " + "message", Snackbar.LENGTH_INDEFINITE).show();
                }
            }


        });
    }

    @AfterPermissionGranted(657)
    public void callingTask() throws Exception {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CALL_PHONE)) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + modelRideInfo.getData().getUser().getUserPhone()));
            if (ActivityCompat.checkSelfPermission(TrackerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(callIntent);
        } else {
            if (FLAG <= 1){
                FLAG = FLAG +1;
                EasyPermissions.requestPermissions(this, getString(R.string.this_app_need_telephony_permission), 657, Manifest.permission.CALL_PHONE);
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(TrackerActivity.this);
                builder.setCancelable(false);
                builder.setMessage(""+getResources().getString(R.string.info_new))
                        .setPositiveButton(R.string.okay_9, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }

        }
    }

    @AfterPermissionGranted(657)
    public void callingTaskForChild() throws Exception {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CALL_PHONE)) {
            try { // Have permission, do the thing!
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + modelRideInfo.getData().getFamily_member_details().getFamily_member_phoneNumber()));
                if (ActivityCompat.checkSelfPermission(TrackerActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            } catch (Exception e) {
            }
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.this_app_need_telephony_permission), 657, Manifest.permission.CALL_PHONE);
        }
    }


    @SuppressLint({"LongLogTag", "MissingSuperCall"})
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent intentdata) {
        try {
            if (requestCode == 1909) {
                if (resultCode == RESULT_OK) {
                    Place place = Autocomplete.getPlaceFromIntent(intentdata);
                    HashMap<String, String> mdata = new HashMap<>();
                    mdata.put("booking_id", "" + getIntent().getExtras().getString(IntentKeys.BOOKING_ID));
                    mdata.put("location", "" + place.getName());
                    mdata.put("latitude", "" + String.valueOf(place.getLatLng().latitude));
                    mdata.put("longitude", "" + String.valueOf(place.getLatLng().longitude));
                    callChangeAddressApi(mdata);
                }
            }

            if (requestCode == OTP_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {

                    if(modelRideInfo.getData().isSend_meter_image()){
                        try {
                            callRideStartWithMeterImage("" + modelRideInfo.getData().getId(), METER_VALUE, METER_IMAGE);
                        } catch (Exception e) {
                            Log.e("CallStatusChangerApi", "" + e.getMessage());
                        }
                    }else{
                        try {
                            if (sessionManager.getAppConfig().getData().getGeneral_config().getLat_long_storing_at() == 2) {
                                if(sessionManager.getStartTag()==1){
                                    try {
                                        callRideStartApi(String.valueOf(modelRideInfo.getData().getId()));
                                    } catch (Exception e) {
                                        // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                    }
                                }else {
                                    ATS.startTrip("" + modelRideInfo.getData().getId()+""+getPackageName(), new AtsOnTripSetListener() {
                                        @Override
                                        public void onSuccess(String message) {
                                            try {
                                                sessionManager.setStartTag(1);
                                                callRideStartApi(String.valueOf(modelRideInfo.getData().getId()));
                                            } catch (Exception e) {
                                                // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailed(String message) {
                                            // Snackbar.make(root, "ATS SERVER ERROR: " + message, Snackbar.LENGTH_INDEFINITE).show();
                                        }
                                    });
                                }
                            } else {
                                try {
                                    callRideStartApi(String.valueOf(modelRideInfo.getData().getId()));
                                } catch (Exception e) {
                                    // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        }catch (Exception e){

                        }
                    }

                }
            }


            if (requestCode == 657) {
                if (resultCode == RESULT_OK) {
                    callingTask();
                }
            }

            if (requestCode == METER_IMAGE_ACTIVITY) {
                if (resultCode == RESULT_OK) {
                    METER_VALUE = intentdata.getExtras().getString("" + IntentKeys.METER_VALUE);
                    METER_IMAGE = intentdata.getExtras().getString("" + IntentKeys.IMAGE);
                    switch (modelRideInfo.getData().getBooking_status()) {
                        case "" + STATUS.ARRIVED:
                            if (modelRideInfo.getData().getRide_otp_verify().equalsIgnoreCase("1")) {
                                Intent intent = new Intent(TrackerActivity.this, Ride_otp_Activity.class)
                                    .putExtra("value", "2")
                                    .putExtra("ride_otp", modelRideInfo.getData().getUser().getRide_otp()).putExtra("booking_id", "" + modelRideInfo.getData().getId());
                                startActivityForResult(intent, OTP_REQUEST_CODE);
                            } else {
                                callRideStartWithMeterImage("" + modelRideInfo.getData().getId(),
                                    intentdata.getExtras().getString("" + IntentKeys.METER_VALUE),
                                    intentdata.getExtras().getString("" + IntentKeys.IMAGE));
                            }
                            break;

                        case "" + STATUS.STARTED:
                            if (sessionManager.isShow_toll_dialog()) {
                                dialogTollAmount();
                            } else {
                                if (sessionManager.getAppConfig().getData().getGeneral_config().getLat_long_storing_at() == 2) {
                                    if(sessionManager.getEndTag() == 1){
                                        try {
                                            eventsButton.startLoading("" + getString(R.string.ending_your_trip));
                                            callRideEndApiWithImage("" + modelRideInfo.getData().getId(), sessionManager.getPolylineAts(), METER_VALUE, METER_IMAGE);
                                        } catch (Exception e) {
                                            //   Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                        }
                                    }else {
                                        ATS.endTrip("" + modelRideInfo.getData().getId()+""+getPackageName(), new AtsOnTripSetListener() {
                                            @Override
                                            public void onSuccess(String message) {
                                                try {
                                                    sessionManager.setEndTag(1);
                                                    sessionManager.setPolylineAts(message);
                                                    eventsButton.startLoading("" + getString(R.string.ending_your_trip));
                                                    callRideEndApiWithImage("" + modelRideInfo.getData().getId(), message, METER_VALUE, METER_IMAGE);
                                                } catch (Exception e) {
                                                    //     Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailed(String message) {
                                                eventsButton.startLoading("" + getString(R.string.cancelling_your_ride));
                                                hideMainContent();
                                                //    Snackbar.make(root, "ATS SERVER ERROR: " + message, Snackbar.LENGTH_INDEFINITE).show();
                                            }
                                        });
                                    }

                                } else {
                                    try {
                                        eventsButton.startLoading("" + getString(R.string.ending_your_trip));
                                        callRideEndApiWithImage("" + modelRideInfo.getData().getId(), "", METER_VALUE, METER_IMAGE);
                                    } catch (Exception e) {
                                        // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                    }
                                }


                            }
                        break;
                    }
                }
            }

            if (requestCode == DELIVERY_PLACE_ACTIVITY) {
                if (resultCode == RESULT_OK) {
                    METER_VALUE = intentdata.getExtras().getString("" + IntentKeys.METER_VALUE);
                    METER_IMAGE = intentdata.getExtras().getString("" + IntentKeys.IMAGE);
                    
                    if (sessionManager.getAppConfig().getData().getGeneral_config()
                            .getLat_long_storing_at() == 2) {
                        if (sessionManager.getEndTag() == 1) {
                            try {
                                eventsButton.startLoading("" + getString(R.string.ending_your_trip));
                                callDeliveryPlaceImage("" + modelRideInfo.getData().getId(), sessionManager.getPolylineAts(), METER_VALUE, METER_IMAGE);
                            } catch (Exception e) {
                                // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            }
                        } else {
                            ATS.endTrip("" + modelRideInfo.getData().getId() + "" + getPackageName(),
                                    new AtsOnTripSetListener() {
                                        @Override
                                        public void onSuccess(String message) {
                                            try {
                                                sessionManager.setEndTag(1);
                                                sessionManager.setPolylineAts(message);
                                                eventsButton.startLoading(
                                                        "" + getString(R.string.ending_your_trip));
                                                callDeliveryPlaceImage("" + modelRideInfo.getData().getId(), message, METER_VALUE, METER_IMAGE);
                                            } catch (Exception e) {
                                                // Snackbar.make(root, "" + e.getMessage(),
                                                // Snackbar.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailed(String message) {
                                            eventsButton.startLoading(
                                                    "" + getString(R.string.cancelling_your_ride));
                                            hideMainContent();
                                            // Snackbar.make(root, "ATS SERVER ERROR: " + message,
                                            // Snackbar.LENGTH_INDEFINITE).show();
                                        }
                                    });
                        }

                    } else {
                        try {
                            eventsButton.startLoading("" + getString(R.string.ending_your_trip));
                            callDeliveryPlaceImage("" + modelRideInfo.getData().getId(), "", METER_VALUE, METER_IMAGE);
                        } catch (Exception e) {
                            // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                    sessionManager.setEndTag(1);
                }
            }
        } catch (Exception e) {
            //   Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_INDEFINITE).show();
        }
    }


    private void refreshScreen(ModelTrackRide modelTrackRide) {

        locationText.setText("" + modelTrackRide.getData().getLocation().getLocation_text());
        // if (modelRideInfo.getData().getLocation().isLocation_editable()) {
        // } else {
        //     topLayoutTwo.setVisibility(View.GONE);
        // }

        setMapData(modelTrackRide.getData().getMovable_marker_type().getDriver_marker_name(), modelTrackRide.getData().getStil_marker().getMarker_type(),
                "" + modelTrackRide.getData().getStil_marker().getMarker_lat(),
                "" + modelTrackRide.getData().getStil_marker().getMarker_long(),
                modelTrackRide.getData().getMovable_marker_type().getDriver_marker_lat(),
                "" + modelTrackRide.getData().getMovable_marker_type().getDriver_marker_long(),
                "" + modelTrackRide.getData().getMovable_marker_type().getDriver_marker_bearing(),
                "" + modelTrackRide.getData().getPolydata().getPolyline(),
                "" + modelTrackRide.getData().getPolydata().getPolyline_color(),
                "" + modelTrackRide.getData().getPolydata().getPolyline_width());

    }


    private void setMapData(String icon_name, String StillMarkerType, String stillMarkrlat, String stillMarkerLong, String movableMakerLat, String movableMarkerLong, String movableMarkerBearing, String polylineCode, String polylineColor, String Polylinewidth) {
        if (!MARKER_TYPE.equals("" + StillMarkerType)) { // marcador de configuração que não se move
            if (STILL_MARKER != null) {
                STILL_MARKER.remove();
            }

            if (StillMarkerType.equalsIgnoreCase("PICK")) {
                MARKER_TYPE = StillMarkerType;
                MarkerOptions marker_option = new MarkerOptions()
                    .position(new LatLng(Double.parseDouble("" + stillMarkrlat), Double.parseDouble("" + stillMarkerLong))).anchor(0.5f, 0.5f).flat(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_locate));
                STILL_MARKER = mGoogleMap.addMarker(marker_option);
            }
            if (StillMarkerType.equalsIgnoreCase("DROP")) {
                if (!modelRideInfo.getData().getLocation().getLocation_text().equals("")) {
                    MARKER_TYPE = StillMarkerType;
                    MarkerOptions marker_option = new MarkerOptions()
                        .position(new LatLng(Double.parseDouble("" + stillMarkrlat), Double.parseDouble("" + stillMarkerLong))).anchor(0.5f, 0.5f).flat(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_new__home));
                    STILL_MARKER = mGoogleMap.addMarker(marker_option);
                }
            }

        }

        if (MOVABLE_MARKER == null) {
            MarkerOptions marker_option;
            if (icon_name == null || icon_name.equals("")) {
                marker_option = new MarkerOptions()
                        .position(new LatLng(Double.parseDouble("" + movableMakerLat), Double.parseDouble("" + movableMarkerLong))).anchor(0.5f, 0.5f)
                        .anchor(0.5f, 0.5f)
                        .rotation(Float.parseFloat("" + movableMarkerBearing))
                        .flat(true)
                        .icon(BitmapDescriptorFactory.fromResource(Config.getMarkerIcon(icon_name)));
            } else {
                marker_option = new MarkerOptions()
                        .position(new LatLng(Double.parseDouble("" + movableMakerLat), Double.parseDouble("" + movableMarkerLong))).anchor(0.5f, 0.5f)
                        .anchor(0.5f, 0.5f)
                        .rotation(Float.parseFloat("" + movableMarkerBearing))
                        .flat(true)
                        .icon(BitmapDescriptorFactory.fromResource(Config.getMarkerIcon(icon_name)));
            }

            MOVABLE_MARKER = mGoogleMap.addMarker(marker_option);
        } else {
            Maputils.animateMarker(new LatLng(Double.parseDouble("" + movableMakerLat), Double.parseDouble("" + movableMarkerLong)), mGoogleMap, MOVABLE_MARKER, Float.parseFloat("" + movableMarkerBearing), icon_name);
        }


        if (!polylineCode.equals("")) { // setting polyline
            PolylineOptions polylineOptions = new PolylineOptions();
            // polylineOptions.color(Color.parseColor("#" + polylineColor));
            // polylineOptions.width(Float.parseFloat("" + Polylinewidth));

            polylineOptions.color(Color.parseColor("#" + "000000"));
            polylineOptions.width(Float.parseFloat("10"));

            polydata.clear();
            polydata = PolyUtil.decode(polylineCode);
            for (int i = 0; i < polydata.size(); i++) {
                polylineOptions.add(polydata.get(i));
            }
            if (polyline != null) {
                polyline.remove();
                polyline = mGoogleMap.addPolyline(polylineOptions);
            } else {
                polyline = mGoogleMap.addPolyline(polylineOptions);
            }

        }


        try {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(new LatLng(Double.parseDouble("" + modelTrackRide.getData().getStil_marker().getMarker_lat()), Double.parseDouble("" + modelTrackRide.getData().getStil_marker().getMarker_long())));
            for (int i = 0; i < polydata.size(); i++) {
                builder.include(polydata.get(i));
            }
            builder.include(new LatLng(Double.parseDouble("" + modelTrackRide.getData().getMovable_marker_type().getDriver_marker_lat()), Double.parseDouble("" + modelTrackRide.getData().getMovable_marker_type().getDriver_marker_long())));

            LatLngBounds bounds = builder.build();
            Point displaySize = new Point();
            this.getWindowManager().getDefaultDisplay().getSize(displaySize);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, (height - 1000), 0));
        } catch (Exception e) {

        }
    }

    private void startScreenRefreshInterval() {
        try {
            mRunnable = () -> {
                try {
                    Log.e("TrackSessionDistance", "" + Double.parseDouble(sessionManager.getDistance()));
                    if (callTracking == 0) {
                        callTracking = 1;
                        emitDriverCurrentData();
                    } else {
                        if (Double.parseDouble(sessionManager.getDistance()) > 20.0) {
                            emitDriverCurrentData();
                        }
                    }

                } catch (Exception e) {
                    emitDriverCurrentData();
                    Log.d("" + TAG, "" + e.getMessage());
                }
                try {
                    mHandeler.postDelayed(mRunnable, 3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            runOnUiThread(mRunnable);
        } catch (Exception e) {

        }
    }


    // emitir dados atuais do driver
    private void emitDriverCurrentData() {
        Log.e("******BookingId******",""+ modelRideInfo.getData().getId());
        data.clear();
        data.put("booking_id", "" + modelRideInfo.getData().getId());

        try {
            LocationSession locationSession = new LocationSession(getApplicationContext());

            if (locationSession.getLocationDetails().get(LocationSession.KEY_CURRENT_LAT) == null || locationSession.getLocationDetails().get(LocationSession.KEY_CURRENT_LAT).equals("")) {
                samLocationRequestService.executeService(new SamLocationRequestService.SamLocationListener() {
                    @Override
                    public void onLocationUpdate(Location location) {
                        data.put("latitude", String.valueOf(location.getLatitude()));
                        data.put("longitude", String.valueOf(location.getLongitude()));
                        data.put("accuracy", "" + location.getAccuracy());
                        data.put("bearing", "" + location.getBearing());

                    }
                });
            } else {
                data.put("latitude", new LocationSession(getApplicationContext()).getLocationDetails().get(LocationSession.KEY_CURRENT_LAT));
                data.put("longitude", new LocationSession(getApplicationContext()).getLocationDetails().get(LocationSession.KEY_CURRENT_LONG));
                data.put("bearing", "" + new LocationSession(getApplicationContext()).getLocationDetails().get(LocationSession.KEY_BEARING_FACTOR));
                data.put("accuracy", "" + new LocationSession(getApplicationContext()).getLocationDetails().get(LocationSession.KEY_ACCURACY));

            }
        } catch (Exception e) {
            samLocationRequestService.executeService(location -> {
                data.put("latitude", String.valueOf(location.getLatitude()));
                data.put("longitude", String.valueOf(location.getLongitude()));
                data.put("accuracy", "" + location.getAccuracy());
                data.put("bearing", "" + location.getBearing());

            });
        }

        try {
            new ApiManager(new ApiManager.APIFETCHER() {
                @Override
                public void onAPIRunningState(int a, String APINAME) {
                    // hideMainContent();
                }

                @Override
                public void onFetchComplete(Object script, String APINAME) {
                    modelTrackRide = SingletonGson.getInstance().fromJson("" + script, ModelTrackRide.class);

                    try {
                        refreshScreen(modelTrackRide);
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onFetchResultZero(String script, String APINAME) {
                }
            }, this)._post(API_S.Tags.TRACK_RIDE, API_S.Endpoints.TRACK_RIDE, data, sessionManager);
        } catch (Exception e) {
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandeler.removeCallbacks(mRunnable);
        mHandeler = null;
        mRunnable = null;
    }

    @Override
    public void onAPIRunningState(int a, String APINAME) {

    }

    @Override
    public void onFetchComplete(Object script, String APINAME) {
        if (APINAME.equals("POLYLINE_API")){

            ModelDirection modelDirection = SingletonGson.getInstance().fromJson(""+script, ModelDirection.class);
            List<LatLng> points = PolyUtil.decode(modelDirection.getData().getPoly_point());

            // mGoogleMap.clear();
            try{stillPolyline.remove();}catch (Exception e){}
            try{routeOverlayView.removeRoutes(); }catch (Exception e){}
            try {
                //  setViewAccordingToRideInfo(modelRideInfo);
                // setViewAccordingToRideinfo(modelRideInfo);
                //  settleUpDriverMarker(new LatLng(ATS.getLastLocation().getLatitude(), ATS.getLastLocation().getLongitude()), 0F, R.drawable.yellow_car_with_circle);
                // callPolyLineApiWithoutAnimation(new LatLng(ATS.getLastLocation().getLatitude(), ATS.getLastLocation().getLongitude()),new LatLng(Double.parseDouble("" + modelRideInfo.getData().getStill_marker().getMarker_lat()), Double.parseDouble("" + modelRideInfo.getData().getStill_marker().getMarker_long())));
                CreatePolyLine(modelDirection.getData().getPoly_point());
            } catch (Exception e) {
            }

        }

    }

    @SuppressLint("ResourceType")
    public void infodialog(String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(TrackerActivity.this);
        builder.setCancelable(false);
        builder.setMessage(message)
                .setPositiveButton(""+getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        builder.create().dismiss();

                    }
                });

        builder.create().show();
        // is_gps_dialog_shown = true;
    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {

    }
}
