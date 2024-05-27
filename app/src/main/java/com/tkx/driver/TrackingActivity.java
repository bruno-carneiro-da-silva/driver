package com.tkx.driver;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import com.tkx.driver.baseClass.BaseActivity;
import com.tkx.driver.currentwork.API_S;
import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.currentwork.STATUS;
import com.tkx.driver.location.SamLocationRequestService;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.models.MOdelTrackingResponse;
import com.tkx.driver.models.ModelCancelReasion;
import com.tkx.driver.models.ModelEndRide;
import com.tkx.driver.models.ModelNotificationChat;
import com.tkx.driver.models.ModelNotificationType;
import com.tkx.driver.models.ModelRideInfo;
import com.tkx.driver.models.ModelTrackRide;
import com.tkx.driver.others.AppUtils;
import com.tkx.driver.others.Constants;
import com.tkx.driver.others.ImageCompressMode;
import com.tkx.driver.others.Maputils;
import com.tkx.driver.samwork.ApiManager;
import com.tkx.driver.socketManager.AtsEventManager;
import com.tkx.driver.views.CustomButton;
import com.apporioinfolabs.ats_sdk.ATS;
import com.apporioinfolabs.ats_sdk.AtsOnTripSetListener;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.android.PolyUtil;
import com.sampermissionutils.AfterPermissionGranted;
import com.sampermissionutils.EasyPermissions;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class TrackingActivity extends BaseActivity implements ApiManager.APIFETCHER, OnMapReadyCallback {

    public static int trackScreenOpen = 0;
    Handler handler;
    Runnable runnable;

    Window window;
    Dialog dialog;
    Dialog dialog1;

    private static final int CAMERS_PICKER = 122;

    private static final int RC_CAMERA_PERM = 123;


    Boolean driverDeliveryOtp = false;

    ImageView imgUnloadCargo;
    EditText etOTP;
    EditText etReceiverContact;
    EditText etReceiverName;

    String imagePath = "", imagePathCompressed = "";
    private ContentValues values;
    private Uri imageUri;
    Bitmap thumbnail;

    @BindView(R.id.chat_number)
    TextView chatNumber;

    @BindView(R.id.select_car_image)
    LinearLayout selectCarImage;

    private ApiManager apiManager;
    private static final int TELEPHONE_PERM = 657, PLACE_AUTOCOMPLETE_REQUEST_CODE = 332, METER_IMAGE_ACTIVITY = 231;
    private final String TAG = "TrackingActivity";
    private Handler mHandeler = new Handler();
    private Runnable mRunnable;
    private String MARKER_TYPE = "", DRIVER_MARKER = "";
    private Marker STILL_MARKER = null, MOVABLE_MARKER = null;
    private Polyline polyline = null;
    private List<LatLng> polydata = new ArrayList<>();
    private GoogleMap googleMap;
    private ModelRideInfo modelRideInfo = null;
    private ModelTrackRide modelTrackRide;
    private SessionManager sessionManager;
    private SamLocationRequestService samLocationRequestService;
    private HashMap<String, String> data = new HashMap<>();
    private int width, height;
    private boolean IS_LAYOUT_SLIDE = false;
    public ProgressDialog progressDialog;
    public ProgressDialog locationProgressDialog;
    private int OTP_REQUEST_CODE = 1111;

    int apiHeat = 0;
    int apiHitForStartRide = 0;
    int openFareActivityClass = 0;
    int emitSocketData = 0;
    int listenSocketData = 0;
    int callTracking = 0;

    MOdelTrackingResponse mOdelTrackingResponse;

    @BindView(R.id.top_layout_two)
    CardView topLayoutTwo;
    @BindView(R.id.top_layout_one)
    CardView topLayoutOne;
    @BindView(R.id.action_layout)
    LinearLayout actionLayout;
    @BindView(R.id.image_color)
    ImageView locationPinColor;
    @BindView(R.id.location_text)
    TextView locationText;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.image)
    CircleImageView image;
    @BindView(R.id.customer_name_txt)
    TextView customerNameTxt;
    @BindView(R.id.customer_phone_txt)
    TextView customerPhoneTxt;
    @BindView(R.id.rating)
    RatingBar rating;
    @BindView(R.id.rating_txt)
    TextView ratingTxt;
    @BindView(R.id.payment_method_name_txt)
    TextView paymentMethodNameTxt;
    @BindView(R.id.call_btn)
    FrameLayout callBtn;
    @BindView(R.id.chat_btn)
    FrameLayout chatBtn;
    @BindView(R.id.navigate_button)
    FrameLayout navigateButton;
    @BindView(R.id.sos)
    LinearLayout sos;
    @BindView(R.id.cancel_btn)
    TextView cancelBtn;
    @BindView(R.id.trip_status_txt_simple)
    TextView tripStatusTxtSimple;
    @BindView(R.id.trip_status_txt_slider)
    CustomButton tripStatusTxtSlider;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.slider_image)
    ImageView sliderImage;
    @BindView(R.id.layout_sliing_button)
    CardView layoutSliingButton;
    @BindView(R.id.location_edit_icon)
    ImageView locationEditIcon;
    @BindView(R.id.call_btn_track)
    ImageView call_track_btn;
    @BindView(R.id.chat_btn_track)
    ImageView chat_btn_track;
    @BindView(R.id.navigate_button_track)
    ImageView share_button_track;
    @BindView(R.id.info_button)
    FrameLayout info_button;
    @BindView(R.id.cardChildDetails)
    CardView cardChildDetails;
    @BindView(R.id.llWaitRide)
    LinearLayout llWaitRide;
    @BindView(R.id.ivWaitRide)
    ImageView ivWaitRide;
    String meterValue, meterImage;
    public static MediaPlayer mediaPlayer;

    int booking_id;

    String additional_note = "";

    int wait_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
        //     public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
        //         Log.e("Error" + Thread.currentThread().getStackTrace()[2], paramThrowable.getLocalizedMessage());
        //     }
        // });
        setContentView(R.layout.activity_tracking);
        MainActivity.clearVehicleId = 1;
        trackScreenOpen = 1;
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("" + this.getResources().getString(R.string.loading));
        locationProgressDialog = new ProgressDialog(this);
        locationProgressDialog.setMessage(this.getResources().getString(R.string.fetching_locaton));

        sessionManager = new SessionManager(this);
        samLocationRequestService = new SamLocationRequestService(this);
        apiManager = new ApiManager(this, this);


        tripStatusTxtSlider.setEnabled(true);
        tripStatusTxtSimple.setEnabled(true);
        setViewData();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_frag);
        mapFragment.getMapAsync(this);

        try {
            call_track_btn.setColorFilter(Color.parseColor(sessionManager.getAppConfig().getData().getTheme_cofig().getCall_button_color_driver()), PorterDuff.Mode.SRC_ATOP);
            chat_btn_track.setColorFilter(Color.parseColor(sessionManager.getAppConfig().getData().getTheme_cofig().getChat_button_color_driver()), PorterDuff.Mode.SRC_ATOP);
            share_button_track.setColorFilter(Color.parseColor(sessionManager.getAppConfig().getData().getTheme_cofig().getShare_button_color_driver()), PorterDuff.Mode.SRC_ATOP);
        } catch (Exception e) { }

        // IS_LAYOUT_SLIDE = true;
        // Maputils.slideToTop(topLayoutOne, -200);
        // Maputils.slideToTop(topLayoutTwo, -350);
        // Maputils.slideToTop(layoutSliingButton, -350);
        // sliderImage.setImageResource(R.drawable.ic_sort_down_vector);

        int int1 = topLayoutOne.getMeasuredHeight();
        int int2 = topLayoutTwo.getMeasuredHeight();

        if (TripHistoryActivity.isActivityOpen) {

            TripHistoryActivity.activity.finish();
        }

        // FrameLayout.LayoutParams _rootLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        // _rootLayoutParams.setMargins(0, int1, 0, 0);
        // mapFragment.getView().setLayoutParams(_rootLayoutParams);

        try {
            setMediaSound();
        } catch (Exception e) {
            Log.d("" + TAG, "Exception caught while calling API " + e.getMessage());
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        if (booking_id == 0) {
            try {
                callRideInfoAPI(getIntent().getExtras().getString("" + IntentKeys.BOOKING_ID));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                callRideInfoAPI(String.valueOf(booking_id));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        back.setOnClickListener(v -> TrackingActivity.this.finish());

        cancelBtn.setOnClickListener(view -> {

            try {
                apiManager._post(API_S.Tags.CANCEL_REASION, API_S.Endpoints.CANCLE_REASION, null, sessionManager);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        callBtn.setOnClickListener(view -> {
            try {
                callingTask();
            } catch (Exception e) {
            }
        });

        info_button.setOnClickListener(v -> {
            showAlertDialogNote(additional_note);
        });

        navigateButton.setOnClickListener(view -> {
            try {
                if (modelTrackRide != null) {
                    if (Maputils.isPackageExisted("com.waze", TrackingActivity.this)) {
                        showDialogForWazemap();
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/maps?saddr=" + modelTrackRide.getData().getMovable_marker_type().getDriver_marker_lat() + "," + modelTrackRide.getData().getMovable_marker_type().getDriver_marker_long() + "&daddr=" + modelTrackRide.getData().getStil_marker().getMarker_lat() + "," + modelTrackRide.getData().getStil_marker().getMarker_long()));
                        startActivity(intent);
                    }
                }
            } catch (Exception e) {
                Log.d("" + TAG, "" + e.getMessage());
            }
        });

        cardChildDetails.setOnClickListener(v -> {
            showDialogForChildDetails();
        });

        tripStatusTxtSlider.setOnSwipeListener(() -> {

            // user has swiped the btn. Perform your async operation now
            progressDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // task success! show TICK icon in ProSwipeButton

                    if (!AppUtils.checkGPSisOnOrNot(TrackingActivity.this)) {
                        Snackbar.make(root, "Please check your GPS", Snackbar.LENGTH_SHORT).show();
                    } else {
                        progressDialog.hide();
                        if (modelRideInfo.getData().isSend_meter_image()) {
                            if (modelRideInfo.getData().getBooking_status().equals("1003")) {
                                if (modelRideInfo.getData().getRide_otp_verify().equalsIgnoreCase("1")) {
                                    Intent intent = new Intent(TrackingActivity.this, Ride_otp_Activity.class)
                                            .putExtra("value", "2")
                                            .putExtra("ride_otp", modelRideInfo.getData().getUser().getRide_otp()).putExtra("booking_id", "" + modelRideInfo.getData().getId());
                                    startActivityForResult(intent, OTP_REQUEST_CODE);
                                } else {
                                    startActivityForResult(new Intent(TrackingActivity.this, MeterImageActivity.class), METER_IMAGE_ACTIVITY);

                                }
                            } else {
                                startActivityForResult(new Intent(TrackingActivity.this, MeterImageActivity.class), METER_IMAGE_ACTIVITY);
                            }
                        } else {
                            if (modelRideInfo.getData().getBooking_status().equals("1004") || modelRideInfo.getData().getBooking_status().equals("1444")) {

                                if (driverDeliveryOtp == true) {
                                    showDialogUploadCargo();
                                } else {
                                    if (modelRideInfo.getData().getLocation().isLocation_action()) {
                                        callApiForMultipleStops();
                                    } else {
                                        try{callStatusChangerAPIS();}catch (Exception e){Snackbar.make(root, ""+e.getMessage(), Snackbar.LENGTH_SHORT);}
                                    }
                                }
                            } else {
                                if (modelRideInfo.getData().getLocation().isLocation_action()) {
                                    callApiForMultipleStops();
                                } else {
                                    try{callStatusChangerAPIS();}catch (Exception e){Snackbar.make(root, ""+e.getMessage(), Snackbar.LENGTH_SHORT);}
                                }
                            }
                        }
                    }// false if task failed

                }
            }, 2000);
        });


        tripStatusTxtSimple.setOnClickListener(view -> {
            Toast.makeText(this, "Clicando sobre o texto recebido", Toast.LENGTH_SHORT).show();
            if (!AppUtils.checkGPSisOnOrNot(TrackingActivity.this)) {
                Snackbar.make(root, "Verifique seu GPS", Snackbar.LENGTH_SHORT).show();
            } else {
                if (modelRideInfo.getData().isSend_meter_image()) {
                    if (modelRideInfo.getData().getBooking_status().equals("1003")) {
                        if (modelRideInfo.getData().getRide_otp_verify().equalsIgnoreCase("1")) {
                            Intent intent = new Intent(TrackingActivity.this, Ride_otp_Activity.class)
                                    .putExtra("value", "2")
                                    .putExtra("ride_otp", modelRideInfo.getData().getUser().getRide_otp()).putExtra("booking_id", "" + modelRideInfo.getData().getId());
                            startActivityForResult(intent, OTP_REQUEST_CODE);
                        } else {
                            startActivityForResult(new Intent(TrackingActivity.this, MeterImageActivity.class), METER_IMAGE_ACTIVITY);
                        }
                    } else {
                        startActivityForResult(new Intent(TrackingActivity.this, MeterImageActivity.class), METER_IMAGE_ACTIVITY);
                    }
                } else {
                    if (modelRideInfo.getData().getBooking_status().equals("1004") || modelRideInfo.getData()
                            .getBooking_status().equals("1444")) {

                        if (driverDeliveryOtp == true) {
                            showDialogUploadCargo();
                        } else {
                            if (modelRideInfo.getData().getLocation().isLocation_action()) {
                                callApiForMultipleStops();
                            } else {
                                try{callStatusChangerAPIS();}catch (Exception e){Snackbar.make(root, ""+e.getMessage(), Snackbar.LENGTH_SHORT);}
                            }
                        }
                    } else {
                        if (modelRideInfo.getData().getLocation().isLocation_action()) {
                            callApiForMultipleStops();
                        } else {
                            try{callStatusChangerAPIS();}catch (Exception e){Snackbar.make(root, ""+e.getMessage(), Snackbar.LENGTH_SHORT);}
                        }
                    }
                }
            }
        });

        //        layoutSliingButton.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                if (IS_LAYOUT_SLIDE) {
        //                    IS_LAYOUT_SLIDE = false;
        //                    Maputils.showViewtooriginalPosition(topLayoutOne);
        //                    Maputils.showViewtooriginalPosition(topLayoutTwo);
        //                    Maputils.showViewtooriginalPosition(layoutSliingButton);
        //                    sliderImage.setImageResource(R.drawable.ic_sort_up_vector);
        //
        //                    int int1 = topLayoutOne.getMeasuredHeight();
        //                    int int2 = topLayoutTwo.getMeasuredHeight();
        //
        //                    FrameLayout.LayoutParams _rootLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        //                    _rootLayoutParams.setMargins(0, int1, 0, 0);
        //
        //                    mapFragment.getView().setLayoutParams(_rootLayoutParams);
        //
        //                } else {
        //                    IS_LAYOUT_SLIDE = true;
        //                    Maputils.slideToTop(topLayoutOne, -200);
        //                    Maputils.slideToTop(topLayoutTwo, -350);
        //                    Maputils.slideToTop(layoutSliingButton, -350);
        //                    sliderImage.setImageResource(R.drawable.ic_sort_down_vector);
        //
        ////                    ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
        ////
        //
        //                    FrameLayout.LayoutParams _rootLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //                    _rootLayoutParams.setMargins(0, 5, 0, 0);
        //
        //                    mapFragment.getView().setLayoutParams(_rootLayoutParams);
        //
        //
        //                }
        //            }
        //        });

        topLayoutTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (modelRideInfo.getData().getLocation().isLocation_editable()) {
                        openGooglePlaceAPiDialoge();
                    } else {
                        // Snackbar.make(root, getString(R.string.you_cannot_chnge_Address_right_now), Snackbar.LENGTH_SHORT).show();
                        Snackbar.make(root, modelRideInfo.getData().getLocation().getLocation_message(), Snackbar.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                    Log.d("" + TAG, "" + e.getMessage());
                }
            }
        });

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrackingActivity.this, ChatActivity.class)
                        .putExtra("" + IntentKeys.USER_IMAGE, "" + modelRideInfo.getData().getUser().getUserProfileImage())
                        .putExtra("" + IntentKeys.USER_NAME, "" + modelRideInfo.getData().getUser().getUserName())
                        .putExtra("" + IntentKeys.STATUS, "Status ... ")
                        .putExtra("" + IntentKeys.BOOKING_ID, "" + modelRideInfo.getData().getId()));
            }
        });

        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String SOS_IDS = "", SOS_NAMES = "", SOS_NUMBERS = "";
                    for (int i = 0; i < modelRideInfo.getData().getSos().size(); i++) {
                        SOS_IDS = SOS_IDS + modelRideInfo.getData().getSos().get(i).getId() + "__";
                        SOS_NAMES = SOS_NAMES + modelRideInfo.getData().getSos().get(i).getName() + "__";
                        SOS_NUMBERS = SOS_NUMBERS + modelRideInfo.getData().getSos().get(i).getNumber() + "__";
                    }
                    startActivity(new Intent(TrackingActivity.this, SosActivity.class)
                            .putExtra("" + IntentKeys.SOS_ID, "" + SOS_IDS)
                            .putExtra("" + IntentKeys.SOS_NAMES, "" + SOS_NAMES)
                            .putExtra("" + IntentKeys.SOS_NUMBERS, "" + SOS_NUMBERS)
                            .putExtra("" + IntentKeys.BOOKING_ID, "" + modelRideInfo.getData().getId()));
                } catch (Exception e) {
                    Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    Log.d("" + TAG, "Exception caught while calling API " + e.getMessage());
                }

            }
        });

    }



    private void listenTrackingResponse() {

        AtsEventManager.listen(String.valueOf(modelRideInfo.getData().getId()), new AtsEventManager.AtsEventListener() {
            @Override
            public void onListen(String data) {
                Log.d("***DriverTrackResponse", "" + data);

                runOnUiThread(() -> {
                    afficher(data);
                });

            }

            @Override
            public void onError(String error) {
                Log.d("***DriverTrackError", "" + error);
            }
        });

    }

    private void setViewData() {
        chatBtn.setVisibility(sessionManager.getAppConfig().getData().getGeneral_config().isChat() ? View.VISIBLE : View.GONE);
        try {
            ReceivePassengerActivity.activity.finish();
        } catch (Exception e) {

        }
    }

    @OnClick(R.id.llWaitRide)
    void onClickWaitRide() {

        samLocationRequestService.executeService(location -> {

            HashMap<String, String> mData = new HashMap<>();
            mData.clear();
            mData.put("booking_id", "" + modelRideInfo.getData().getId());
            mData.put("latitude", "" + location.getLatitude());
            mData.put("longitude", "" + location.getLongitude());

            if (wait_type == 0) {
                mData.put("type", "2");
            } else {
                mData.put("type", "" + wait_type);
            }


            try {
                apiManager._post(API_S.Tags.WAIT_RIDE, API_S.Endpoints.WAIT_RIDE, mData, sessionManager);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    private void callApiForMultipleStops() {
        locationProgressDialog.show();
        samLocationRequestService.executeService(new SamLocationRequestService.SamLocationListener() {
            @Override
            public void onLocationUpdate(Location location) {
                try {
                    if (locationProgressDialog.isShowing()) {
                        locationProgressDialog.dismiss();
                    }
                    data.clear();
                    data.put("booking_id", "" + modelRideInfo.getData().getId());
                    data.put("latitude", "" + location.getLatitude());
                    data.put("longitude", "" + location.getLongitude());
                    data.put("accuracy", "" + location.getAccuracy());

                    if (driverDeliveryOtp == true) {
                        HashMap<String, File> mFile = new HashMap<>();
                        data.put("receiver_name", etReceiverName.getText().toString());
                        data.put("receiver_otp", etOTP.getText().toString());
                        mFile.put("receiver_image", new File(imagePathCompressed));
                        System.out.println("IMAGE IMAGE" + mFile.toString());

                        apiManager._post_image(API_S.Tags.REACHED_STOP, API_S.Endpoints.REACHED_STOP, data, mFile, sessionManager);
                    } else {
                        apiManager._post(API_S.Tags.REACHED_STOP, API_S.Endpoints.REACHED_STOP, data, sessionManager);
                    }


                } catch (Exception e) {
                    Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    //// GooglePlaceApiDialog
    private void openGooglePlaceAPiDialoge() throws Exception {

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), Config.getEncodedApiKey(getApplicationContext()));
        }

        List<Place.Field> fields = Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG);

        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent intentdata) {
        try {
            if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    Place place = Autocomplete.getPlaceFromIntent(intentdata);
                    Log.d("*#*# getAddress", "" + place.getAddress());
                    Log.d("*#*# getAttributions", "" + place.getAttributions());

                    Log.d("*#*# getname", "" + place.getName());
                    Log.d("*#*# getId", "" + place.getId());
                    Log.d("*#*# geWebsiteURI", "" + place.getWebsiteUri());
                    HashMap<String, String> mdata = new HashMap<>();
                    mdata.put("booking_id", "" + modelRideInfo.getData().getId());
                    mdata.put("location", "" + place.getName());
                    mdata.put("latitude", "" + String.valueOf(place.getLatLng().latitude));
                    mdata.put("longitude", "" + String.valueOf(place.getLatLng().longitude));
                    apiManager._post(API_S.Tags.CHANGE_ADDRESS, API_S.Endpoints.CHANGE_ADDRESS, mdata, sessionManager);

                } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                    Status status = Autocomplete.getStatusFromIntent(intentdata);
                    Log.i("*****", status.getStatusMessage());
                } else if (resultCode == RESULT_CANCELED) {
                } else if (resultCode == RESULT_CANCELED) {
                }
            }

            if (requestCode == CAMERS_PICKER) {

                /*  Bitmap thumbnail = (Bitmap) intentdata.getExtras().get("data");
                imgUnloadCargo.setImageBitmap(thumbnail);

                file = new File(mImageCaptureUri.getPath());
                FileOutputStream fOut = new FileOutputStream(file);
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                */

                //Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
                //Uri uri = (Uri) data.get("data");
                //  camera.setImageBitmap(photo);

                //  thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imgUnloadCargo.setImageBitmap(thumbnail);
                imagePath = getRealPathFromURI(imageUri);

                ImageCompressMode imageCompressModee = new ImageCompressMode(this);
                imagePathCompressed = imageCompressModee.compressImage(imagePath);

                ExifInterface ei = new ExifInterface(imagePath);
                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);

                Bitmap rotatedBitmap = null;
                switch (orientation) {

                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotatedBitmap = rotateImage(thumbnail, 90);
                        imgUnloadCargo.setImageBitmap(rotatedBitmap);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotatedBitmap = rotateImage(thumbnail, 180);
                        imgUnloadCargo.setImageBitmap(rotatedBitmap);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotatedBitmap = rotateImage(thumbnail, 270);
                        imgUnloadCargo.setImageBitmap(rotatedBitmap);
                        break;

                    case ExifInterface.ORIENTATION_NORMAL:
                        rotatedBitmap = thumbnail;
                        imgUnloadCargo.setImageBitmap(rotatedBitmap);
                        break;
                    default:
                        rotatedBitmap = thumbnail;
                        imgUnloadCargo.setImageBitmap(rotatedBitmap);


                    // Toast.makeText(this, "Here", Toast.LENGTH_SHORT).show();
                    // try {
                    //     thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    // } catch (IOException e) {
                    //     e.printStackTrace();
                    // }
                    // imgUnloadCargo.setImageBitmap(thumbnail);
                    // imagePath = getRealPathFromURI(imageUri);


                }
            }

            if (requestCode == METER_IMAGE_ACTIVITY) {

                meterValue = intentdata.getExtras().getString("" + IntentKeys.METER_VALUE);

                meterImage = intentdata.getExtras().getString("" + IntentKeys.IMAGE);

                switch (modelRideInfo.getData().getBooking_status()) {
                    case "" + STATUS.ARRIVED:

                        if (modelRideInfo.getData().getRide_otp_verify().equalsIgnoreCase("1")) {

                            Intent intent = new Intent(TrackingActivity.this, Ride_otp_Activity.class)
                                    .putExtra("value", "2")
                                    .putExtra("ride_otp", modelRideInfo.getData().getUser().getRide_otp()).putExtra("booking_id", "" + modelRideInfo.getData().getId());
                            startActivityForResult(intent, OTP_REQUEST_CODE);
                        } else {
                            if (!modelRideInfo.getData().getLocation().getLocation_text().equals("")) {

                                locationEditIcon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        locationProgressDialog.show();
                                    }
                                });
                            }
                            samLocationRequestService.executeService(location -> {
                                try {
                                    HashMap<String, String> mData = new HashMap<>();
                                    mData.clear();
                                    mData.put("booking_id", "" + modelRideInfo.getData().getId());
                                    mData.put("latitude", "" + location.getLatitude());
                                    mData.put("longitude", "" + location.getLongitude());
                                    mData.put("accuracy", "" + location.getAccuracy());
                                    mData.put("send_meter_value", "" + intentdata.getExtras().getString("" + IntentKeys.METER_VALUE));
                                    HashMap<String, File> mFile = new HashMap<>();
                                    mFile.put("send_meter_image", new File("" + intentdata.getExtras().getString("" + IntentKeys.IMAGE)));
                                    if (apiHitForStartRide == 0) {
                                        apiHitForStartRide = 1;
                                        ATS.startTrip(""+modelRideInfo.getData().getId()+""+getPackageName(), new AtsOnTripSetListener() {
                                            @Override
                                            public void onSuccess(String message) {
                                                Log.e("StartTrip",""+message);
                                                Toast.makeText(TrackingActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                                                try{apiManager._post_image(API_S.Tags.START_RIDE, API_S.Endpoints.START_RIDE, mData, mFile, sessionManager);}catch (Exception e){}
                                            }

                                            @Override
                                            public void onFailed(String message) {
                                                Toast.makeText(TrackingActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                } catch (Exception e) {
                                    Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                    Log.d("" + TAG, "" + e.getMessage());
                                }
                            });
                        }

                        break;

                    case "" + STATUS.STARTED:
                        samLocationRequestService.executeService(location -> {
                            try {

                                if (sessionManager.isShow_toll_dialog()) {

                                    dialogTollAmount(location);

                                } else {
                                    HashMap<String, String> mData = new HashMap<>();
                                    mData.clear();
                                    mData.put("booking_id", "" + modelRideInfo.getData().getId());
                                    mData.put("latitude", "" + location.getLatitude());
                                    mData.put("longitude", "" + location.getLongitude());
                                    mData.put("accuracy", "" + location.getAccuracy());
                                    data.put("manual_toll_charge", "");
                                    mData.put("send_meter_value", "" + intentdata.getExtras().getString("" + IntentKeys.METER_VALUE));
                                    HashMap<String, File> mFile = new HashMap<>();

                                    mFile.put("send_meter_image", new File("" + intentdata.getExtras().getString("" + IntentKeys.IMAGE)));

                                    if (driverDeliveryOtp == true) {
                                        data.put("receiver_name", etReceiverName.getText().toString());
                                        data.put("receiver_otp", etOTP.getText().toString());
                                        mFile.put("receiver_image", new File(imagePathCompressed));
                                        System.out.println("IMAGE IMAGE" + mFile.toString());
                                    }

                                    if (openFareActivityClass == 0) {
                                        callRideEndApi(mData, mFile);
                                        openFareActivityClass = 1;
                                    }
                                }

                            } catch (Exception e) {
                                Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                Log.d("" + TAG, "" + e.getMessage());
                            }
                        });
                        break;
                }

            }

            if (requestCode == OTP_REQUEST_CODE && resultCode == RESULT_OK) {

                if (intentdata.getStringExtra("value").equals("2")) {
                    if (modelRideInfo.getData().isSend_meter_image()) {
                        startActivityForResult(new Intent(TrackingActivity.this, MeterImageActivity.class), METER_IMAGE_ACTIVITY);
                    }
                } else if (!modelRideInfo.getData().getLocation().getLocation_text().equals("")) {

                    locationProgressDialog.show();
                    samLocationRequestService.executeService(location -> {
                        try {

                            if (locationProgressDialog.isShowing()) {
                                locationProgressDialog.dismiss();
                            }
                            data.clear();
                            data.put("booking_id", "" + modelRideInfo.getData().getId());
                            data.put("latitude", "" + location.getLatitude());
                            data.put("longitude", "" + location.getLongitude());
                            data.put("accuracy", "" + location.getAccuracy());

                            if (apiHitForStartRide == 0) {
                                apiHitForStartRide = 1;
                                ATS.startTrip(""+modelRideInfo.getData().getId()+""+getPackageName(), new AtsOnTripSetListener() {
                                    @Override
                                    public void onSuccess(String message) {
                                        Log.e("StartTrip",""+message);
                                        Toast.makeText(TrackingActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                                        try{apiManager._post(API_S.Tags.START_RIDE, API_S.Endpoints.START_RIDE, data, sessionManager);}catch (Exception e){} }

                                    @Override
                                    public void onFailed(String message) { Toast.makeText(TrackingActivity.this, ""+message, Toast.LENGTH_SHORT).show(); }
                                });

                            }


                        } catch (Exception e) {
                            Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }

                    });

                } else {
                    Snackbar.make(root, "" + getString(R.string.please_enter_drop_location), Snackbar.LENGTH_SHORT).show();

                }
            }
        } catch (Exception e) {
            //Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            Log.d("Manual_Exception_Place_AutoComplete", "" + e);
        }
    }

    private void callStatusChangerAPIS() throws  Exception{
        switch (modelRideInfo.getData().getBooking_status()) {
            case "" + STATUS.ACCEPTED:
                locationProgressDialog.show();
                if (locationProgressDialog.isShowing()) { locationProgressDialog.dismiss(); }
                if (apiHeat == 0) {
                    apiHeat = 1;
                    data.clear();
                    data.put("booking_id", "" + modelRideInfo.getData().getId());
                    data.put("latitude", "" + ATS.getLastLocation().getLatitude());
                    data.put("longitude", "" + ATS.getLastLocation().getLongitude());
                    data.put("accuracy", "" + ATS.getLastLocation().getAccuracy());
                    apiManager._post(API_S.Tags.ARRIVE_RIDE, API_S.Endpoints.ARRIVE_RIDE, data, sessionManager);
                }
                break;
            case "" + STATUS.ARRIVED:
                if (modelRideInfo.getData().getRide_otp_verify().equalsIgnoreCase("1")) {

                    Intent intent = new Intent(TrackingActivity.this, Ride_otp_Activity.class)
                            .putExtra("value", "1")
                            .putExtra("ride_otp", modelRideInfo.getData().getUser().getRide_otp()).putExtra("booking_id", "" + modelRideInfo.getData().getId());
                    startActivityForResult(intent, OTP_REQUEST_CODE);
                } else if (!modelRideInfo.getData().getLocation().getLocation_text().equals("")) {

                    locationEditIcon.setOnClickListener(view -> locationProgressDialog.show());

                    if (locationProgressDialog.isShowing()) { locationProgressDialog.dismiss(); }
                    data.clear();
                    data.put("booking_id", "" + modelRideInfo.getData().getId());
                    data.put("latitude", "" + ATS.getLastLocation().getLatitude());
                    data.put("longitude", "" + ATS.getLastLocation().getLongitude());
                    data.put("accuracy", "" + ATS.getLastLocation().getAccuracy());

                    if (apiHitForStartRide == 0) {
                        apiHitForStartRide = 1;
                        ATS.startTrip(""+modelRideInfo.getData().getId()+""+getPackageName()+""+getPackageName(), new AtsOnTripSetListener() {
                            @Override
                            public void onSuccess(String message) {
                                Log.e("StartTrip",""+message);
                                Toast.makeText(TrackingActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                                try{apiManager._post(API_S.Tags.START_RIDE, API_S.Endpoints.START_RIDE, data, sessionManager);}catch (Exception e){}
                            }

                            @Override
                            public void onFailed(String message) {
                                Toast.makeText(TrackingActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } else { Snackbar.make(root, "" + getString(R.string.please_enter_drop_location), Snackbar.LENGTH_SHORT).show(); }
                break;
            case "" + STATUS.STARTED:

                ///0 for disable and 1 for enable
                locationProgressDialog.show();
                samLocationRequestService.executeService(location -> {
                    try {
                        if (locationProgressDialog.isShowing()) { locationProgressDialog.dismiss(); }

                        if (sessionManager.isShow_toll_dialog()) {

                            dialogTollAmount(location);

                        } else {

                            data.clear();
                            data.put("booking_id", "" + modelRideInfo.getData().getId());
                            data.put("latitude", "" + location.getLatitude());
                            data.put("longitude", "" + location.getLongitude());
                            data.put("manual_toll_charge", "");
                            data.put("accuracy", "" + location.getAccuracy());
                            if (driverDeliveryOtp == true) {
                                HashMap<String, File> mFile = new HashMap<>();
                                data.put("receiver_name", etReceiverName.getText().toString());
                                data.put("receiver_otp", etOTP.getText().toString());
                                mFile.put("receiver_image", new File(imagePathCompressed));
                                System.out.println("IMAGE IMAGE" + mFile.toString());


                                if (openFareActivityClass == 0) {
                                    callRideEndApi(data, null);
                                    openFareActivityClass = 1;
                                }
                            } else {
                                if (openFareActivityClass == 0) {
                                    callRideEndApi(data, null);
                                    openFareActivityClass = 1;
                                }
                            }

                        }

                    } catch (Exception e) {
                        Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    private void callRideInfoAPI(String booking) throws Exception {
        try {
            progressDialog.show();
        } catch (Exception e) {

        }
        data.clear();
        data.put("booking_id", "" + booking);
        apiManager._post("" + API_S.Tags.RIDE_INFO, "" + API_S.Endpoints.RIDE_INFO, data, sessionManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (booking_id == 0) {
                callRideInfoAPI(getIntent().getExtras().getString("" + IntentKeys.BOOKING_ID));
            } else {
                callRideInfoAPI(String.valueOf(booking_id));
            }
        } catch (Exception e) {

        }
        try {
            EventBus.getDefault().register(this);
            Constants.IS_TRACKING_ACTIVITY_OPEN = true;
            chatNumber.setText("0");
            chatNumber.setVisibility(View.GONE);


        } catch (Exception e) {
            // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
        // if (modelRideInfo != null) {
        //     startScreenRefreshInterval();
        // }
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        Constants.IS_TRACKING_ACTIVITY_OPEN = false;
        mHandeler.removeCallbacks(mRunnable);
        try {
            String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

            //makeFile("PAUSE", mydate, "");
        } catch (Exception e) {

        }
        super.onPause();
    }

    @Override
    protected void onStart() {
        try {
            String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

            // makeFile("ON_START", mydate, "");
        } catch (Exception e) {

        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        try {
            String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

            // makeFile("ON_STOP", mydate, "");
        } catch (Exception e) {

        }
        super.onStop();
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String data) {
        ModelNotificationType modelNotificationType = SingletonGson.getInstance().fromJson("" + data, ModelNotificationType.class);
        if (modelNotificationType.getType() == 1) { // Ride related notification
            try {
                callRideInfoAPI(getIntent().getExtras().getString("" + IntentKeys.BOOKING_ID));
            } catch (Exception e) {
                Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        }
        if (modelNotificationType.getType() == 5) {
            try {
                ModelNotificationChat modelNotificationChat = SingletonGson.getInstance().fromJson("" + data, ModelNotificationChat.class);
                chatNumber.setVisibility(View.VISIBLE);
                if (!chatNumber.getText().toString().isEmpty()) {
                    chatNumber.setText("" + (Integer.parseInt("" + chatNumber.getText().toString()) + 1));
                    mediaPlayer.start();
                } else {
                    chatNumber.setText("1");
                    mediaPlayer.start();
                }
            } catch (Exception e) {
                Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                Log.d("" + TAG, "Exception caught while calling API " + e.getMessage());
            }
        }
    }

    private void startScreenRefreshInterval() {

        try {
            mRunnable = () -> {
                try {
                    Log.e("TrackSessionDistance", "" + Double.parseDouble(sessionManager.getDistance()));
                    // emitDriverCurrentData();

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
        data.clear();
        data.put("booking_id", "" + modelRideInfo.getData().getId());
        data.put("latitude", ""+ATS.getLastLocation().getLatitude());
        data.put("longitude", ""+ATS.getLastLocation().getLongitude());
        data.put("bearing", ""+ATS.getLastLocation().getBearing());
        data.put("accuracy", ""+ATS.getLastLocation().getAccuracy());
        try {
            apiManager._postForTracking(API_S.Tags.TRACK_RIDE, API_S.Endpoints.TRACK_RIDE, data, sessionManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void afficher(String data) {

        Log.d("***DriverTrackResponse", "" + data);
        mOdelTrackingResponse = SingletonGson.getInstance().fromJson("" + data, MOdelTrackingResponse.class);

        try {
            // refreshScreenTracking(mOdelTrackingResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showDialogForWazemap() {
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
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/maps?saddr=" + modelTrackRide.getData().getMovable_marker_type().getDriver_marker_lat() + "," + modelTrackRide.getData().getMovable_marker_type().getDriver_marker_long() + "&daddr=" + modelTrackRide.getData().getStil_marker().getMarker_lat() + "," + modelTrackRide.getData().getStil_marker().getMarker_long()));
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.waze_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Launch Waze

                    String mapRequest = "https://waze.com/ul?" + "q=" + modelTrackRide.getData().getStil_marker().getMarker_lat() + "," + modelTrackRide.getData().getStil_marker().getMarker_long() + "&navigate=yes&zoom=17";
                    Log.d("**waze url=>", mapRequest);
                    Uri gmmIntentUri = Uri.parse(mapRequest);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.waze");
                    startActivity(mapIntent);

                } catch (ActivityNotFoundException e) {
                    // If Waze is not installed, open it in Google Play
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
                    startActivity(intent);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void refreshScreenTracking(MOdelTrackingResponse mOdelTrackingResponse) {

        locationPinColor.setColorFilter(Color.parseColor("#" + modelRideInfo.getData().getLocation().getLocation_color()));

        topLayoutTwo.setVisibility(View.VISIBLE);
        locationText.setText("" + modelRideInfo.getData().getLocation().getLocation_text());
        // if (modelRideInfo.getData().getLocation().isLocation_editable()) {
        // } else {
        //  topLayoutTwo.setVisibility(View.GONE);
        // }

        setMapData("car.png",
                mOdelTrackingResponse.getResponse().getNameValuePairs().getMarker_type(),
                "" + mOdelTrackingResponse.getResponse().getNameValuePairs().getStill_latitude(),
                "" + mOdelTrackingResponse.getResponse().getNameValuePairs().getStill_longitude(),
                mOdelTrackingResponse.getResponse().getNameValuePairs().getMovable_latitude(),
                "" + mOdelTrackingResponse.getResponse().getNameValuePairs().getMovable_longitude(),
                "" + mOdelTrackingResponse.getResponse().getNameValuePairs().getBearing(),
                "" + mOdelTrackingResponse.getResponse().getNameValuePairs().getPoly_points(),
                "#000000",
                "4");


        if (modelRideInfo.getData().isCancelable()) {
            cancelBtn.setVisibility(View.VISIBLE);
        } else {
            cancelBtn.setVisibility(View.GONE);
        }


    }

    private void setMapData(String icon_name, String StillMarkerType, String stillMarkrlat, String stillMarkerLong, String movableMakerLat, String movableMarkerLong, String movableMarkerBearing, String polylineCode, String polylineColor, String Polylinewidth) {
        if (!MARKER_TYPE.equals("" + StillMarkerType)) { // setting marker that doesn't move
            if (STILL_MARKER != null) {
                STILL_MARKER.remove();
            }

            if (StillMarkerType.equalsIgnoreCase("PICK")) {
                MARKER_TYPE = StillMarkerType;
                MarkerOptions marker_option = new MarkerOptions()
                        .position(new LatLng(Double.parseDouble("" + stillMarkrlat), Double.parseDouble("" + stillMarkerLong))).anchor(0.5f, 0.5f).flat(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_locate));
                STILL_MARKER = googleMap.addMarker(marker_option);
            }
            if (StillMarkerType.equalsIgnoreCase("DROP")) {
                if (!modelRideInfo.getData().getLocation().getLocation_text().equals("")) {
                    MARKER_TYPE = StillMarkerType;
                    MarkerOptions marker_option = new MarkerOptions()
                            .position(new LatLng(Double.parseDouble("" + stillMarkrlat), Double.parseDouble("" + stillMarkerLong))).anchor(0.5f, 0.5f).flat(true)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_new__home));
                    STILL_MARKER = googleMap.addMarker(marker_option);
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

            MOVABLE_MARKER = googleMap.addMarker(marker_option);
        } else {
            Maputils.animateMarker(new LatLng(Double.parseDouble("" + movableMakerLat), Double.parseDouble("" + movableMarkerLong)), googleMap, MOVABLE_MARKER, Float.parseFloat("" + movableMarkerBearing), icon_name);
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
                polyline = googleMap.addPolyline(polylineOptions);
            } else {
                polyline = googleMap.addPolyline(polylineOptions);
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

            // DisplayMetrics metrics = new DisplayMetrics();
            // this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            // Point targetPoint = new Point(metrics.widthPixels / 2, metrics.heightPixels - metrics.heightPixels / 6);
            // LatLng targetLatlng = googleMap.getProjection().fromScreenLocation(targetPoint);
            // double fromCenterToTarget = SphericalUtil.computeDistanceBetween(googleMap.getCameraPosition().target, targetLatlng);
            // LatLng center = SphericalUtil.computeOffset(new LatLng(Double.parseDouble("" + modelTrackRide.getData().getMovable_marker_type().getDriver_marker_lat()), Double.parseDouble("" + modelTrackRide.getData().getMovable_marker_type().getDriver_marker_long())), fromCenterToTarget/1.2,0);
            // CameraUpdate camera = CameraUpdateFactory.newLatLng(center);
            // googleMap.moveCamera(camera);

            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, (height - 1000), 0));
        } catch (Exception e) {

        }
    }

    private void setRideInfo(ModelRideInfo modelRideInfo) throws Exception {

        try {
            if (modelRideInfo.getData().getAdditional_notes() == null || modelRideInfo.getData().getAdditional_notes().equals("null") || modelRideInfo.getData().getAdditional_notes().equals("")) {
                info_button.setVisibility(View.GONE);
            } else {
                info_button.setVisibility(View.VISIBLE);
                additional_note = modelRideInfo.getData().getAdditional_notes();
            }

        } catch (Exception e) {

        }

        try {
            cardChildDetails.setVisibility(modelRideInfo.getData().getFamily_member_details().isFamily_visibility() ? View.VISIBLE : View.GONE);
        } catch (Exception e) {

        }
        if (Integer.parseInt(modelRideInfo.getData().getBooking_status()) == 1002) {
            apiHeat = 0;
        }


        if (Integer.parseInt(modelRideInfo.getData().getBooking_status()) == 1004 || Integer.parseInt(modelRideInfo.getData().getBooking_status()) == 1444) {
            apiHitForStartRide = 0;
        }

        if (Integer.parseInt(modelRideInfo.getData().getBooking_status()) == 1444) {
            apiHitForStartRide = 0;
        }

        if (modelRideInfo.getData().getBooking_status().equals("" + STATUS.USER_CONCELLED) ||
                modelRideInfo.getData().getBooking_status().equals("" + STATUS.DRIVER_CANCELLED) ||
                modelRideInfo.getData().getBooking_status().equals("" + STATUS.ADMIN_CANCELLED)) {
            showAlertDialog("" + modelRideInfo.getData().getLocation().getTrip_status_text());
        }

        if (modelRideInfo.getData().getBooking_status().equals("" + STATUS.END)) {
            Log.e("FareActivity", "FareActivity");
            startActivity(new Intent(TrackingActivity.this, FareActivity.class).putExtra("" + IntentKeys.BOOKING_ID, "" + modelRideInfo.getData().getId()));
            finish();
        }

        Log.e("SOSENABLE", "" + modelRideInfo.getData().isSos_visibility());

        if (modelRideInfo.getData().isSos_visibility()) {
            sos.setVisibility(View.VISIBLE);
        } else {
            sos.setVisibility(View.GONE);
        }

        customerNameTxt.setText("Operação - " + modelRideInfo.getData().getUser().getUserName());
        customerPhoneTxt.setText("" + modelRideInfo.getData().getUser().getUserPhone());
        Glide.with(this).load("" + modelRideInfo.getData().getUser().getUserProfileImage()).into(image);
        rating.setRating(Float.parseFloat("" + modelRideInfo.getData().getUser().getRating()));
        ratingTxt.setText("" + modelRideInfo.getData().getUser().getRating());
        paymentMethodNameTxt.setText("" + modelRideInfo.getData().getPayment_method().getPayment_method());
        tripStatusTxtSimple.setText("" + modelRideInfo.getData().getLocation().getTrip_status_text());
        tripStatusTxtSlider.setText("" + modelRideInfo.getData().getLocation().getTrip_status_text());
        locationPinColor.setColorFilter(Color.parseColor("#" + modelRideInfo.getData().getLocation().getLocation_color()));

        topLayoutTwo.setVisibility(View.VISIBLE);
        locationText.setText("" + modelRideInfo.getData().getLocation().getLocation_text());
        // if (modelRideInfo.getData().getLocation().isLocation_editable()) {
        // } else {
        //     topLayoutTwo.setVisibility(View.GONE);
        // }

        if (sessionManager.getAppConfig().getData().getRide_config().isSlide_button()) {
            tripStatusTxtSimple.setVisibility(View.GONE);
            tripStatusTxtSlider.setVisibility(View.VISIBLE);
        } else {

            tripStatusTxtSimple.setVisibility(View.VISIBLE);
            tripStatusTxtSlider.setVisibility(View.GONE);
        }

        if (modelRideInfo.getData().isCancelable()) {
            cancelBtn.setVisibility(View.VISIBLE);
        } else {
            cancelBtn.setVisibility(View.GONE);
        }
        //startScreenRefreshInterval();

        locationEditIcon.setVisibility(modelRideInfo.getData().getLocation().isLocation_editable() ? View.VISIBLE : View.GONE);

        setMapData(modelRideInfo.getData().getMovable_marker().getDriver_marker_name(), modelRideInfo.getData().getStill_marker().getMarker_type(),
                "" + modelRideInfo.getData().getStill_marker().getMarker_lat(),
                "" + modelRideInfo.getData().getStill_marker().getMarker_long(),
                "" + modelRideInfo.getData().getMovable_marker().getDriver_marker_lat(),
                "" + modelRideInfo.getData().getMovable_marker().getDriver_marker_long(),
                "" + modelRideInfo.getData().getMovable_marker().getDriver_marker_bearing(),
                "" + modelRideInfo.getData().getPolydata().getPolyline(),
                "" + modelRideInfo.getData().getPolydata().getPolyline_color(),
                "" + modelRideInfo.getData().getPolydata().getPolyline_width());
    }

    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TrackingActivity.this);
        builder.setCancelable(false);
        builder.setTitle(R.string.alert);
        builder.setMessage(message)
                .setPositiveButton(R.string.ok, (dialog, id) -> finish());
        builder.create().show();
    }

    private void showAlertDialogNote(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TrackingActivity.this);
        builder.setCancelable(false);
        builder.setTitle(getResources().getString(R.string.additional_note_text));
        builder.setMessage(message)
                .setPositiveButton(R.string.ok, (dialog, id) -> dialog.dismiss());
        builder.create().show();
    }

    private void setMediaSound() throws Exception {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(this, Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.incomig_chat_sound));
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
        mediaPlayer.prepare();
    }

    @Override
    public void onAPIRunningState(int a, String APINAME) {

        try {
            if (!APINAME.equals(API_S.Tags.TRACK_RIDE)) {
                if (a == ApiManager.APIFETCHER.KEY_API_IS_STARTED) {
                    tripStatusTxtSlider.setEnabled(false);
                    tripStatusTxtSimple.setEnabled(false);
                    progressDialog.show();
                } else if (progressDialog.isShowing()) {
                    tripStatusTxtSlider.setEnabled(true);
                    tripStatusTxtSimple.setEnabled(true);
                    progressDialog.dismiss();
                }
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onFetchComplete(Object script, String APINAME) {
        try {
            switch (APINAME) {
                case API_S.Tags.RIDE_INFO:
                    modelRideInfo = SingletonGson.getInstance().fromJson("" + script, ModelRideInfo.class);
                    MARKER_TYPE = "";
                    booking_id = modelRideInfo.getData().getId();
                    sessionManager.setBooking_Id(String.valueOf(booking_id));

                    try {
                        driverDeliveryOtp = modelRideInfo.getData().getDelivery_drop_otp();
                    } catch (Exception e) {

                    }

                    try {
                        if (modelRideInfo.getData().getBooking_status().equals("1004") || modelRideInfo.getData()
                                .getBooking_status().equals("1444")) {
                            if (modelRideInfo.getData().isOnride_pause_button()) {
                                llWaitRide.setVisibility(View.VISIBLE);
                            }
                        }

                    } catch (Exception e) {

                    }

                    try {
                        if (modelRideInfo.getData().getBooking_status().equals("1004") || modelRideInfo.getData()
                                .getBooking_status().equals("1444")) {
                            wait_type = modelRideInfo.getData().getOnride_waiting_type();
                            if (wait_type == 1) {
                                tripStatusTxtSimple.setEnabled(false);
                                tripStatusTxtSimple.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                tripStatusTxtSlider.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                tripStatusTxtSlider.setEnabled(false);
                                ivWaitRide.setImageDrawable(getResources().getDrawable(R.drawable.icons_off_64));
                            } else if (wait_type == 2) {
                                tripStatusTxtSimple.setEnabled(true);
                                tripStatusTxtSimple.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                tripStatusTxtSlider.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                tripStatusTxtSlider.setEnabled(true);
                                ivWaitRide.setImageDrawable(getResources().getDrawable(R.drawable.icons_on_64));
                            } else {
                                tripStatusTxtSimple.setEnabled(true);
                                tripStatusTxtSimple.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                tripStatusTxtSlider.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                tripStatusTxtSlider.setEnabled(true);
                                ivWaitRide.setImageDrawable(getResources().getDrawable(R.drawable.icons_on_64));
                            }
                        }
                    } catch (Exception e) {

                    }

                    // listenTrackingResponse();
                    try {
                        startScreenRefreshInterval();
                    } catch (Exception e) {

                    }

                    setRideInfo(modelRideInfo);
                    break;

                case API_S.Tags.WAIT_RIDE:

                    ModelWaitRide modelWaitRide = SingletonGson.getInstance().fromJson("" + script, ModelWaitRide.class);

                    if (modelWaitRide.getResult().equals("1")) {
                        Toast.makeText(this, "" + modelWaitRide.getMessage(), Toast.LENGTH_SHORT).show();
                        wait_type = modelWaitRide.getType();
                        if (modelWaitRide.getType() == 1) {
                            tripStatusTxtSimple.setEnabled(false);
                            tripStatusTxtSimple.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            tripStatusTxtSlider.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            tripStatusTxtSlider.setEnabled(false);
                            ivWaitRide.setImageDrawable(getResources().getDrawable(R.drawable.icons_off_64));
                        } else if (modelWaitRide.getType() == 2) {
                            tripStatusTxtSimple.setEnabled(true);
                            tripStatusTxtSimple.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            tripStatusTxtSlider.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            tripStatusTxtSlider.setEnabled(true);
                            ivWaitRide.setImageDrawable(getResources().getDrawable(R.drawable.icons_on_64));
                        } else {
                            tripStatusTxtSimple.setEnabled(true);
                            tripStatusTxtSimple.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            tripStatusTxtSlider.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            tripStatusTxtSlider.setEnabled(true);
                            ivWaitRide.setImageDrawable(getResources().getDrawable(R.drawable.icons_on_64));
                        }
                    }


                    break;

                case API_S.Tags.TRACK_RIDE:
                    modelTrackRide = SingletonGson.getInstance().fromJson("" + script, ModelTrackRide.class);

                    navigateButton.setVisibility(View.VISIBLE);
                    try {
                        if (Integer.parseInt(modelTrackRide.getData().getMovable_marker_type().getDriver_marker_accuracy()) > 100) {
                        } else {
                            refreshScreen(modelTrackRide);
                        }
                    } catch (Exception e) {
                        refreshScreen(modelTrackRide);
                    }

                    // if(callTracking==0){
                    //     callTracking =1;
                    // }else {
                    //     try{
                    //         if(Integer.parseInt(modelTrackRide.getData().getMovable_marker_type().getDriver_marker_accuracy())>100){
                    //             refreshScreen(modelTrackRide);
                    //         }
                    //     }catch (Exception e){
                    //         refreshScreen(modelTrackRide);
                    //     }
                    // }
                    break;
                case API_S.Tags.ARRIVE_RIDE:
                    modelRideInfo = SingletonGson.getInstance().fromJson("" + script, ModelRideInfo.class); // getting same response as details api and refresh screen
                    booking_id = modelRideInfo.getData().getId();
                    try {
                        tripStatusTxtSlider.setEnabled(true);
                        tripStatusTxtSimple.setEnabled(true);
                        emitDriverCurrentData();
                    } catch (Exception e) {

                    }
                    setRideInfo(modelRideInfo);
                    //   String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                    // makeFile("BUTTON_PRESSED", mydate, "ARRIVED RESULT");
                    break;
                case API_S.Tags.START_RIDE:
                    modelRideInfo = SingletonGson.getInstance().fromJson("" + script, ModelRideInfo.class); // getting same response as details api and refresh screen
                    booking_id = modelRideInfo.getData().getId();

                    sessionManager.setShow_toll_dialog(modelRideInfo.getData().isManual_toll_enable());
                    try {
                        if (modelRideInfo.getData().isOnride_pause_button()) {
                            llWaitRide.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {

                    }
                    try {
                        tripStatusTxtSlider.setEnabled(true);
                        tripStatusTxtSimple.setEnabled(true);
                        emitDriverCurrentData();
                    } catch (Exception e) {

                    }
                    setRideInfo(modelRideInfo);
                    String mydate1 = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                    //  makeFile("BUTTON_PRESSED", mydate1, "START RESULT");
                    break;
                case API_S.Tags.CANCEL_RIDE:
                    finish();
                    break;
                case API_S.Tags.CANCEL_REASION:
                    ModelCancelReasion modelCancelReasion = SingletonGson.getInstance().fromJson("" + script, ModelCancelReasion.class);

                    if (modelCancelReasion.getResult().equals("1")) {
                        try {
                            AlertDialog.Builder builderSingle = new AlertDialog.Builder(TrackingActivity.this);
                            builderSingle.setTitle(R.string.cancel_ride);
                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(TrackingActivity.this, android.R.layout.select_dialog_singlechoice);
                            for (int i = 0; i < modelCancelReasion.getData().size(); i++) {
                                arrayAdapter.add("" + modelCancelReasion.getData().get(i).getReason());
                            }
                            builderSingle.setNegativeButton(TrackingActivity.this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        data.clear();
                                        data.put("booking_id", "" + getIntent().getExtras().getString("" + IntentKeys.BOOKING_ID));
                                        data.put("cancel_reason_id", "" + modelCancelReasion.getData().get(which).getId());
                                        apiManager._post(API_S.Tags.CANCEL_RIDE, API_S.Endpoints.CANCEL_RIDE, data, sessionManager);
                                    } catch (Exception e) {
                                        Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                        Log.d("" + TAG, "Exception caught while calling API " + e.getMessage());
                                    }
                                    dialog.dismiss();
                                }
                            });
                            builderSingle.show();
                        } catch (Exception e) {
                            Log.d("" + TAG, "" + e.getMessage());
                        }
                    }

                    break;
                case API_S.Tags.REACHED_STOP:
                    modelRideInfo = SingletonGson.getInstance().fromJson("" + script, ModelRideInfo.class); // getting same response as details api and refresh screen
                    MARKER_TYPE = "";
                    setRideInfo(modelRideInfo);
                    break;
                case API_S.Tags.END_RIDE:

                    try {
                        modelRideInfo = SingletonGson.getInstance().fromJson("" + script, ModelRideInfo.class); // getting same response as details api and refresh screen
                        booking_id = modelRideInfo.getData().getId();
                    } catch (Exception e) { }

                    ModelEndRide modelEndRide = SingletonGson.getInstance().fromJson("" + script, ModelEndRide.class);

                    sessionManager.clearBookingType();
                    sessionManager.setBookingType("" + modelEndRide.getData().getBooking_type());
                    sessionManager.setBooking_Id(null);

                    startActivity(new Intent(TrackingActivity.this, FareActivity.class).putExtra("" + IntentKeys.BOOKING_ID, "" + modelEndRide.getData().getId()).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                    break;
                case API_S.Tags.CHANGE_ADDRESS:
                    emitDriverCurrentData();
                    callRideInfoAPI(String.valueOf(modelRideInfo.getData().getId()));

                    break;
            }

        } catch (Exception e) {
            Log.d("" + TAG, "" + e.getMessage());

        }
    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {
        try {
            switch (APINAME) {

                case API_S.Tags.START_RIDE:

                    Toast.makeText(this, "" + script, Toast.LENGTH_SHORT).show();
                    break;

                case API_S.Tags.END_RIDE:
                    Toast.makeText(this, "" + script, Toast.LENGTH_SHORT).show();

                    openFareActivityClass = 0;
                    try {
                        window.closeAllPanels();
                        dialog1.dismiss();
                        dialog.dismiss();
                        TrackingActivity.this.finish();
                    } catch (Exception e) {

                    }
                    break;

                case API_S.Tags.REACHED_STOP:
                    Toast.makeText(this, "" + script, Toast.LENGTH_SHORT).show();

                    openFareActivityClass = 0;
                    try {
                        window.closeAllPanels();
                        dialog1.dismiss();
                        dialog.dismiss();
                        TrackingActivity.this.finish();
                    } catch (Exception e) { }
                    break;
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMaxZoomPreference(20);

        try {
            if (new LocationSession(getApplicationContext()).getLocationDetails().get(LocationSession.KEY_CURRENT_LAT) == null || new LocationSession(getApplicationContext()).getLocationDetails().get(LocationSession.KEY_CURRENT_LAT).equals("")) {
                samLocationRequestService.executeService(location -> {
                    try {
                        LatLng currentLatLng = new LatLng(location.getLatitude(),
                                location.getLongitude());
                        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentLatLng, 20);
                        googleMap.moveCamera(update);
                        progressDialog.dismiss();
                    } catch (Exception e) {
                        progressDialog.dismiss();
                    }
                });
            } else {
                LatLng currentLatLng = new LatLng(Double.parseDouble(new LocationSession(getApplicationContext()).getLocationDetails().get(LocationSession.KEY_CURRENT_LAT)),
                        Double.parseDouble(new LocationSession(getApplicationContext()).getLocationDetails().get(LocationSession.KEY_CURRENT_LONG)));
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentLatLng, 17);
                googleMap.moveCamera(update);
            }
        } catch (Exception e) {

        }
    }

    @AfterPermissionGranted(TELEPHONE_PERM)
    public void callingTask() throws Exception {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CALL_PHONE)) {
            try { // Have permission, do the thing!
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + modelRideInfo.getData().getUser().getUserPhone()));
                if (ActivityCompat.checkSelfPermission(TrackingActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            } catch (Exception e) { }
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.this_app_need_telephony_permission), TELEPHONE_PERM, Manifest.permission.CALL_PHONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            callingTask();
        } catch (Exception e) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            trackScreenOpen = 0;
            mHandeler.removeCallbacks(mRunnable);
            handler.removeCallbacks(runnable);
            mHandeler = null;
            mRunnable = null;
            //  sessionManager.setBooking_Id(null);
            //   String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

            //makeFile("DESTROY", mydate, "");
        } catch (Exception e) { }
    }


    private void refreshScreen(ModelTrackRide modelTrackRide) {

        locationPinColor.setColorFilter(Color.parseColor("#" + modelTrackRide.getData().getLocation().getLocation_color()));

        topLayoutTwo.setVisibility(View.VISIBLE);
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


        if (modelTrackRide.getData().isCancelable()) {
            cancelBtn.setVisibility(View.VISIBLE);
        } else {
            cancelBtn.setVisibility(View.GONE);
        }
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

    @AfterPermissionGranted(TELEPHONE_PERM)
    public void callingTaskForChild() throws Exception {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CALL_PHONE)) {
            try { // Have permission, do the thing!
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + modelRideInfo.getData().getFamily_member_details().getFamily_member_phoneNumber()));
                if (ActivityCompat.checkSelfPermission(TrackingActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            } catch (Exception e) {
            }
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.this_app_need_telephony_permission), TELEPHONE_PERM, Manifest.permission.CALL_PHONE);
        }
    }

    private void dialogTollAmount(Location location) {

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

            data.clear();
            data.put("booking_id", "" + modelRideInfo.getData().getId());
            data.put("latitude", "" + location.getLatitude());
            data.put("longitude", "" + location.getLongitude());
            data.put("accuracy", "" + location.getAccuracy());
            data.put("manual_toll_charge", "");

            if (modelRideInfo.getData().isSend_meter_image()) {

                data.put("send_meter_value", "" + meterValue);
                HashMap<String, File> mFile = new HashMap<>();

                mFile.put("send_meter_image", new File("" + meterImage));

                if (driverDeliveryOtp == true) {
                    data.put("receiver_name", etReceiverName.getText().toString());
                    data.put("receiver_otp", etOTP.getText().toString());
                    mFile.put("receiver_image", new File(imagePathCompressed));
                    System.out.println("IMAGE IMAGE" + mFile.toString());
                }

                if (openFareActivityClass == 0) {
                    try {
                        callRideEndApi(data, mFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    openFareActivityClass = 1;
                }
            } else {
                HashMap<String, File> mFile = new HashMap<>();


                if (driverDeliveryOtp == true) {

                    data.put("receiver_name", etReceiverName.getText().toString());
                    data.put("receiver_otp", etOTP.getText().toString());
                    mFile.put("receiver_image", new File(imagePathCompressed));
                    System.out.println("IMAGE IMAGE" + mFile.toString());

                    if (openFareActivityClass == 0) {
                        try {
                            callRideEndApi(data, mFile);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        openFareActivityClass = 1;
                    }
                } else {
                    if (openFareActivityClass == 0) {
                        try {
                            callRideEndApi(data, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        openFareActivityClass = 1;
                    }
                }


            }
        });

        ll_ok_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editText.getText().toString().equals("")) {
                    Toast.makeText(TrackingActivity.this, "Insira um valor válido", Toast.LENGTH_SHORT).show();
                } else {
                    data.clear();
                    data.put("booking_id", "" + modelRideInfo.getData().getId());
                    data.put("latitude", "" + location.getLatitude());
                    data.put("longitude", "" + location.getLongitude());
                    data.put("accuracy", "" + location.getAccuracy());
                    data.put("manual_toll_charge", editText.getText().toString());

                    if (modelRideInfo.getData().isSend_meter_image()) {

                        data.put("send_meter_value", "" + meterValue);
                        HashMap<String, File> mFile = new HashMap<>();

                        mFile.put("send_meter_image", new File("" + meterImage));

                        if (driverDeliveryOtp == true) {
                            data.put("receiver_name", etReceiverName.getText().toString());
                            data.put("receiver_otp", etOTP.getText().toString());
                            mFile.put("receiver_image", new File(imagePathCompressed));
                            System.out.println("IMAGE IMAGE" + mFile.toString());
                        }

                        if (openFareActivityClass == 0) {
                            try {
                                callRideEndApi(data, mFile);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            openFareActivityClass = 1;
                        }
                    } else {
                        if (driverDeliveryOtp == true) {
                            HashMap<String, File> mFile = new HashMap<>();
                            data.put("receiver_name", etReceiverName.getText().toString());
                            data.put("receiver_otp", etOTP.getText().toString());
                            mFile.put("receiver_image", new File(imagePathCompressed));
                            System.out.println("IMAGE IMAGE" + mFile.toString());

                            if (openFareActivityClass == 0) {
                                try {
                                    callRideEndApi(data, mFile);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                openFareActivityClass = 1;
                            }
                        } else {
                            if (openFareActivityClass == 0) {
                                try {
                                    callRideEndApi(data, null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                openFareActivityClass = 1;
                            }
                        }

                    }
                }

            }
        });
        dialog1.show();
    }

    private void showDialogUploadCargo() {
        dialog = new Dialog(TrackingActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_unload_cargo);


        final int CAMERA_REQUEST = 1888;


        imgUnloadCargo = dialog.findViewById(R.id.img_unload_cargo);
        etReceiverName = dialog.findViewById(R.id.et_receiver_name);
        etReceiverContact = dialog.findViewById(R.id.et_receiver_contact);
        etOTP = dialog.findViewById(R.id.et_otp);
        Button btnSubmit = dialog.findViewById(R.id.btn_submit);

        imgUnloadCargo.setOnClickListener(v -> {
            try {
                if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
                    try { // Have permission, do the thing!
                        values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, "New Picture");
                        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, CAMERS_PICKER);


                    } catch (Exception e) {
                    }
                } else {
                    EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera), RC_CAMERA_PERM, Manifest.permission.CAMERA);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnSubmit.setOnClickListener(v -> {
            String receiverName = etReceiverName.getText().toString();
            String receiverContact = etReceiverContact.getText().toString();
            String OTP = etOTP.getText().toString();
            if (receiverName.equals("") || OTP.equals("") || imgUnloadCargo.getDrawable() == null) {
                Toast.makeText(TrackingActivity.this, "Não envie dados em branco", Toast.LENGTH_SHORT).show();
            } else {
                if (modelRideInfo.getData().getLocation().isLocation_action()) {
                    callApiForMultipleStops();
                } else {
                    try{callStatusChangerAPIS();}catch (Exception e){Snackbar.make(root, ""+e.getMessage(), Snackbar.LENGTH_SHORT);}
                }
            }
        });
        dialog.show();
    }

    private void callRideEndApi( HashMap<String, String> data,HashMap<String, File> mFile ){
        ATS.endTrip("" + data.get("booking_id")+""+getPackageName(), new AtsOnTripSetListener() {
            @Override
            public void onSuccess(String message) {
                try{
                    Log.e("EndTrip",""+message);
                    Toast.makeText(TrackingActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                    data.put("booking_polyline",""+message);
                    if(mFile == null){
                        apiManager._post(API_S.Tags.END_RIDE, API_S.Endpoints.END_RIDE, data, sessionManager);
                    }else{
                        apiManager._post_image(API_S.Tags.END_RIDE, API_S.Endpoints.END_RIDE, data, mFile, sessionManager);
                    }
                }catch (Exception e){
                    Toast.makeText(TrackingActivity.this, "Exception : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailed(String message) {
                Toast.makeText(TrackingActivity.this, ""+message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

