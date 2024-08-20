package com.tkx.driver;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.room.Room;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.tkx.driver.activities.subscriptionModule.SubscriptionModuleList;
import com.tkx.driver.baseClass.BaseActivity;
import com.tkx.driver.baseClass.BaseClassFragmentActivity;
import com.tkx.driver.currentwork.API_S;
import com.tkx.driver.currentwork.EventLocation;
import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.currentwork.NOTIFICATION_TYPES;
import com.tkx.driver.fcmclasses.OneSignalServiceClass;
import com.tkx.driver.holder.HolderActiveRide;
import com.tkx.driver.location.SamLocationRequestService;
import com.tkx.driver.location.UpdateServiceClass;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.models.ModelActive;
import com.tkx.driver.models.ModelAutoAccept;
import com.tkx.driver.models.ModelDemandSpot;
import com.tkx.driver.models.ModelDeviceOnlineIffline;
import com.tkx.driver.models.ModelMainScreen;
import com.tkx.driver.models.ModelNotificationType;
import com.tkx.driver.models.ModelRideNotifications;
import com.tkx.driver.others.AppUtils;
import com.tkx.driver.samwork.ApiManager;
import com.tkx.driver.settings.SettingsActivity;
import com.tkx.driver.wallet.WalletActivity;
import com.apporioinfolabs.ats_sdk.ATS;
import com.apporioinfolabs.ats_sdk.AtsOnTagSetListener;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.onesignal.OneSignal;
import com.sam.placer.PlaceHolderView;
import com.tkx.driver.workers.SyncData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements ApiManager.APIFETCHER, OnMapReadyCallback {

    // EditText t1,t2,t3;
    // TextView lbl, databol;
    // Button b1, b2;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 858;

    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    public static MediaPlayer mediaPlayer;

    private boolean doubleBackToExitPressedOnce = false;

    private SamLocationRequestService samLocationRequestService;

    LocationSession app_location_mamanger;
    CountDownTimer SoundTimer;
    boolean type_sel = true;

    @BindView(R.id.docs_alert)
    LinearLayout docs_alert;
    @BindView(R.id.expiry_msg)
    TextView expire_msg;
    @BindView(R.id.see_docs)
    TextView see_docs;
    @BindView(R.id.close_docs)
    ImageView close_docs;
    @BindView(R.id.online_offline_text)
    TextView onlineOfflineText;
    @BindView(R.id.todays_booking)
    TextView todaysBooking;
    @BindView(R.id.todays_earning)
    TextView todaysEarning;
    @BindView(R.id.activated_car_image)
    ImageView activatedCarImage;
    @BindView(R.id.activated_car_text)
    TextView activatedCarText;
    @BindView(R.id.demand_Spot)
    CardView demandSpot;
    @BindView(R.id.todays_schedule_text)
    FrameLayout main_screen_counter_layout;
    @BindView(R.id.btn_upcomming_ride)
    CardView upcoming_ride_btn;
    @BindView(R.id.trips_btn)
    LinearLayout tripsBtn;
    // @BindView(R.id.earning_btn)
    // LinearLayout earningBtn;
    @BindView(R.id.accounts_btn)
    LinearLayout accountsBtn;
    @BindView(R.id.settings_btn)
    LinearLayout settingsBtn;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.switch_btn)
    SwitchCompat switchBtn;
    @BindView(R.id.switch_btn_auto)
    SwitchCompat switch_btn_auto;
    @BindView(R.id.switch_controll)
    RelativeLayout switchControll;
    @BindView(R.id.frameAuto)
    FrameLayout frameAuto;
    @BindView(R.id.switch_controll_auto)
    RelativeLayout switch_controll_auto;
    @BindView(R.id.view_cars)
    LinearLayout viewCars;
    @BindView(R.id.notification_btn)
    RelativeLayout notificationBtn;
    // @BindView(R.id.wallet_btn)
    // LinearLayout walletBtn;
    @BindView(R.id.placeholder)
    PlaceHolderView placeholder;
    @BindView(R.id.placeholder_layout)
    CardView placeholderLayout;
    @BindView(R.id.main_screen_conter_text)
    TextView mainScreenConterText;
    @BindView(R.id.bottom_bell)
    ImageView bottomBell;
    @BindView(R.id.bottom_cross)
    CardView bottomCross;
    @BindView(R.id.btn_manual_dispatch)
    CardView btn_manual_dispatch;
    // @BindView(R.id.switch_btn_goto_home)
    // SwitchCompat switchBtnGotoHome;
    // @BindView(R.id.goto_home_text)
    // TextView gotoHomeText;
    // @BindView(R.id.switch_controll_go_to_home)
    // RelativeLayout switchControllGoToHome;
    @BindView(R.id.activated_car_number)
    TextView activatedCarNumber;
    @BindView(R.id.auto_text)
    TextView auto_text;
    @BindView(R.id.connection_status)
    TextView connectionStatus;
    @BindView(R.id.tvVehicleStatus)
    TextView tvVehicleStatus;
    @BindView(R.id.igi)
    Button igi;
    @BindView(R.id.frm1)
    FrameLayout frm1;
    @BindView(R.id.gender_option)
    CardView gender_option;
    View locationButtonView;
    @BindView(R.id.notfication_text)
    TextView NotficationText;

    private ArrayList<Integer> SELECTED_SERVICES_TYPES = new ArrayList<>();

    @BindView(R.id.llViewBottomLayout)
    LinearLayout llViewBottomLayout;
    private HashMap<String, String> data = new HashMap<>();
    public static SessionManager sessionManager;
    private ModelMainScreen modelMainScreen = null;
    private ApiManager apiManager;
    private GoogleMap mGoogleMap;
    private final String TAG = "MainActivity";
    public static Activity activity;

    SamLocationRequestService sam_location;

    ProgressDialog progressDialog;
    Location userLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private boolean DEMANDSPOT = false;
    public static int openScreenTerms = 0;
    int mainViewLoader = 0;
    static String vehicle_id = "";
    int openDialog = 1;
    static int clearVehicleId = 1;

    int startVehhicle = 0;

    // private String phonenumber() {
    //     TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    //     String phonenumber = telephonyManager.getLine1Number();
    //     return phonenumber;
    // }

    @BindView(R.id.notification_number)
    TextView notification;
    @BindView(R.id.todaytriptext)
    TextView todaytriptext;
    @BindView(R.id.todayearningtext)
    TextView todayearningtext;
    @BindView(R.id.text_upcoming)
    TextView text_upcoming;
    @BindView(R.id.triptext)
    TextView triptext;
    // @BindView(R.id.earngings_text)
    // TextView earngings_text;
    @BindView(R.id.accounttext)
    TextView accounttext;
    // @BindView(R.id.wallettext)
    // TextView wallettext;
    @BindView(R.id.settingtext)
    TextView settingtext;

    //work manager to sync data
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    WorkManager workManager = WorkManager.getInstance(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler((paramThread, paramThrowable) -> Log.e("Error" + Thread.currentThread().getStackTrace()[2], paramThrowable.getLocalizedMessage()));

        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        sessionManager = new SessionManager(this);

        // LOG.v(TAG, "Número atual do dispositivo é: " + phonenumber();)

        // work manager instance with the time
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(
                SyncData.class, 15, TimeUnit.MINUTES
        ).build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            workManager.enqueue(workRequest);
        }


        clearVehicleId = 1;

        app_location_mamanger = new LocationSession(this);
        samLocationRequestService = new SamLocationRequestService(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        ButterKnife.bind(this);
        activity = this;
        sam_location = new SamLocationRequestService(this);
        apiManager = new ApiManager(this, this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationButtonView = mapFragment.getView();

        if(BuildConfig.Build_varient.equals("onetaxidriver")){
            if (sessionManager.getLanguage().equals("pt")){
                sessionManager.setLanguage("pt");
                NotficationText.setText("notificações");
                todaytriptext.setText("viagens de hoje");
                todayearningtext.setText("Ganhos de Hoje");
                text_upcoming.setText("próximos");
                triptext.setText("viagens");
                // earngings_text.setText("Ganhos");
                accounttext.setText("contas");
                // wallettext.setText("Carteira");
                settingtext.setText("Definições");
            }else {
                NotficationText.setText("Notifications");
                todaytriptext.setText("Today trips");
                todayearningtext.setText("Todays Earning");
                text_upcoming.setText("Upcoming");
                triptext.setText("Trips");
                // earngings_text.setText("Earnings");
                accounttext.setText("Account");
                // wallettext.setText("Wallet");
                settingtext.setText("Settings");
            }
        }

        if(BuildConfig.Build_varient.equals("tankydriver")){
            if (sessionManager.getLanguage().equals("es")){
                sessionManager.setLanguage("es");
                NotficationText.setText("Notificaciones");
                todaytriptext.setText("Viajes de hoy");
                todayearningtext.setText("Ganancias de hoy");
                text_upcoming.setText("Próximamente");
                triptext.setText("viajes");
                // earngings_text.setText("Ganancias");
                accounttext.setText("Cuentas");
                // wallettext.setText("Wallet");
                settingtext.setText("Settings");
            }else {
                NotficationText.setText("Notifications");
                todaytriptext.setText("Today trips");
                todayearningtext.setText("Todays Earning");
                text_upcoming.setText("Upcoming");
                triptext.setText("Trips");
                // earngings_text.setText("Earnings");
                accounttext.setText("Account");
                // wallettext.setText("Wallet");
                settingtext.setText("Settings");
            }
        }


        OneSignal.startInit(MainActivity.this)
                .autoPromptLocation(true)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        try {
            // UpdateServiceClass.openScreen = 0;
            OneSignalServiceClass.openScreen = 0;
            //  if(sessionManager.getFirebaseNotification()==true){
            // MyFirebaseMessagingService.openScreen = 0;
            // }else {
            //     OneSignalServiceClass.openScreen = 0;
            // }

            // pushLogToServer();
        } catch (Exception e) {
        }

        // try {
        //     if (sessionManager.getAppSecret() != null)
        //         sessionManager.getAppSecret();
        //     Log.e("AppSecretKey", "" + sessionManager.getAppSecret());
        // } catch (Exception e) {
        // }


        setDataAccordingToConfig();
        // Explicando a sessão do github


        // ponto de demanda

        if (sessionManager.getAppConfig().getData().getGeneral_config().isDemand_spot_enable()) {
            demandSpot.setVisibility(View.VISIBLE);
        } else {
            demandSpot.setVisibility(View.GONE);
        }

        btn_manual_dispatch.setOnClickListener(v -> {
            // startActivity(new Intent(MainActivity.this, SubscriptionModuleList.class));
            if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("2")) {
                Toast.makeText(MainActivity.this, getString(R.string.make_online), Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(MainActivity.this, ManualUserDetailActivity.class));
            }
        });

        igi.setOnClickListener(v -> {
            if (type_sel) {
                type_sel = false;
                gefenceApi("1");
            } else {
                type_sel = true;
                gefenceApi("2");
            }

        });

        gender_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogForGender();
            }
        });
        
        if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("2")) {
            switchBtn.setVisibility(View.VISIBLE);
            switchControll.setVisibility(View.VISIBLE);
        }
        
        switchControll.setOnClickListener((View view) -> {
            manageVehicle();
            switchBtn.setVisibility(View.GONE);
            switchControll.setVisibility(View.GONE);
        });

        switch_controll_auto.setOnClickListener((View view) -> {
            if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("1")) {
                data.put("status", "2");
            } else if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("2")) {
                data.put("status", "1");
            } else {
                data.put("status", "2");
            }
            try {
                apiManager._post(API_S.Tags.AUTO_ACCEPT, API_S.Endpoints.AUTO_ACCEPT, data, sessionManager);
            } catch (Exception e) {
                Log.e(TAG, "Exceção detectada ao chamar API " + e.getMessage());
            }
        });

        if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("2")) {
            startVehhicle = 1;
            manageVehicle();
        } else {
            startActivity(new Intent(MainActivity.this, TripHistoryActivity.class));
        }
        
        tripsBtn.setOnClickListener((View view) -> {
            if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("2")) {                
                // android.app.AlertDialog.Builder builderSingle = new android.app.AlertDialog.Builder(this);
                // builderSingle.setMessage("Fique On-line !    -    Ative a chave !");
                // builderSingle.show();
                startVehhicle = 1;
                manageVehicle();
            } else {
                startActivity(new Intent(MainActivity.this, TripHistoryActivity.class));
            }
        });

        // earningBtn.setOnClickListener((View view) -> {
        //     //  writeStringAsFile(this, "HELLo Punnet How are you I am fine Okay the", "vishal.txt");
        //     //   getTextFileData("hello.txt");
        //     // startActivity(new Intent(MainActivity.this, NewEarningActivity.class));
        //     startActivity(new Intent(MainActivity.this, NewEarningDriver.class));
        //     // if(sessionManager.getAppConfig().getData().getGeneral_config().isDriver_cashout_module()){
        //     // }else {
        //     //     startActivity(new Intent(MainActivity.this, NewEarningActivity.class));
        //     // }
        // });

        try {
            accountsBtn.setOnClickListener((View view) -> {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class)
                        .putExtra("" + IntentKeys.IFSC_VISIBILITY, "" + modelMainScreen.getData().getAdditional_bank_data())
                        .putExtra("" + IntentKeys.IFSC_PLACEHOLDER, "" + modelMainScreen.getData().getAdditional_bank_data_placeholder()));
            });
        } catch (Exception e) {
        }

        settingsBtn.setOnClickListener((View view) -> {
            //  loadAssetTextAsString(this,"vishal.txt");

            //   Log.e("File",""+loadAssetTextAsString(this,"vishal.txt"));
            // readFileAsString(this,"vishal.txt");
            startActivity(new Intent(MainActivity.this, SettingsActivity.class)
                    .putExtra(IntentKeys.AUTO_UPGRADATION, modelMainScreen.getData().getRide_config().getAuto_upgradetion_status())
                    .putExtra(IntentKeys.AUTO_UPGRADATION_SHOW, modelMainScreen.getData().getRide_config().getAuto_upgradetion())
                    .putExtra(IntentKeys.POOL_RIDE_ACTIVATE, modelMainScreen.getData().getRide_config().getPool_enable_status())
                    .putExtra(IntentKeys.POOL_RIDE_ACTIVATE_SHOW, modelMainScreen.getData().getRide_config().isPool_enable()));
        });

        viewCars.setOnClickListener((View view) -> {
            startActivityForResult(new Intent(MainActivity.this, VehicleListActivity.class)
                    .putExtra("SHOW_VEHICLE_LIST", "NO"), 114);

        });

        notificationBtn.setOnClickListener((View view) -> {
            startActivity(new Intent(MainActivity.this, NotificationActivity.class));
            notification.setText("");
            notification.setVisibility(View.GONE);
        });

        // walletBtn.setOnClickListener((View view) -> {
        //     startActivity(new Intent(MainActivity.this, WalletActivity.class));
        // });

        demandSpot.setOnClickListener((View view) -> {
            if (DEMANDSPOT != true) {
                try {
                    apiManager._post(API_S.Tags.DEMAND_SPOT, API_S.Endpoints.DEMAND_SPOT, null, sessionManager);
                } catch (Exception e) {
                    Log.e("" + TAG, "Exceção detectada ao chamar API " + e.getMessage());
                }
            } else {
                mGoogleMap.clear();
                DEMANDSPOT = false;
            }
        });

        upcoming_ride_btn.setOnClickListener((View view) -> {
            startActivity(new Intent(MainActivity.this, NewRequestActivity.class));
        });

        bottomBell.setOnClickListener((View view) -> {
            if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("2")) {
                startVehhicle = 1;
                manageVehicle();                
            } else {
                placeholderLayout.setVisibility(View.VISIBLE);
                AppUtils.enterReveal(placeholderLayout);
                // bottomBell.setVisibility(View.GONE);
                mainScreenConterText.setVisibility(View.GONE);
                bottomCross.setVisibility(View.VISIBLE);
            }
        });

        bottomCross.setOnClickListener((View view) -> {
            placeholderLayout.setVisibility(View.INVISIBLE);
            AppUtils.exitReveal(placeholderLayout);
            bottomBell.setVisibility(View.VISIBLE);
            mainScreenConterText.setVisibility(View.VISIBLE);
            bottomCross.setVisibility(View.GONE);
        });
        close_docs.setOnClickListener(v -> docs_alert.setVisibility(View.GONE));
        see_docs.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, PersonalDocumentActivity.class)));

        // t1=findViewById(R.id.t1);
        // t2=findViewById(R.id.t2);
        // t3=findViewById(R.id.t3);
        // b1=findViewById(R.id.b1);

        // b2=findViewById(R.id.b2);
        // lbl=findViewById(R.id.lbl);
        // databol=findViewById(R.id.dataholder);

        // b1.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View view) {
        //         new Thread(new Runnable() {
        //             @Override
        //             public void run() {
        //                 AppDatabase db = Room.databaseBuilder(getApplicationContext(),
        //                         AppDatabase.class, "room_db").build();
        //                 UserDao userDao = db.userDao();
        //                 Boolean check = userDao.is_exist(Integer.parseInt(t1.getText().toString()));
        //                 if (check == false) {
        //                     userDao.insertrecord(new User(Integer.parseInt(t1.getText().toString()), t2.getText().toString(), t3.getText().toString()));
        //                 }

        //                 runOnUiThread(new Runnable() {
        //                     @Override
        //                     public void run() {
        //                         t1.setText("");
        //                         t2.setText("");
        //                     }
        //                 });
        //             }
        //         }).start();
        //     }
        // });
        // b2.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View view) {
        //         AppDatabase db = Room.databaseBuilder(getApplicationContext(),
        //                 AppDatabase.class, "room_db").allowMainThreadQueries().build();
        //         UserDao userDao = db.userDao();
        //         List<User> users=userDao.getAllUsers();
        //         String str="";

        //         for(User user : users)
        //             str=str+"\t " +user.getUid()+" "+user.getFirstName()+" "+user.getLastName()+"\n\n";

        //         databol.setText(str);

        //     }
        // });
    }


    private void gefenceApi(String type) {

        samLocationRequestService.executeService(location -> {
            HashMap<String, String> al_parms = new HashMap<>();
            al_parms.put("latitude", "" + location.getLatitude());
            al_parms.put("longitude", "" + location.getLongitude());
            al_parms.put("type", type);

            try {
                apiManager._post(API_S.Tags.GEOFENCE, API_S.Endpoints.GEOFENCE, al_parms, sessionManager);
            } catch (Exception e) {
                Log.e(TAG, "Exceção detectada ao chamar API " + e.getMessage());
            }
        });
    }

    private void openSelectServiceDialog() {
        boolean[] checkedItems = new boolean[modelMainScreen.getData().getAvailable_services().size()];
        for (int i = 0; i < modelMainScreen.getData().getAvailable_services().size(); i++) {
            if (SELECTED_SERVICES_TYPES.contains(i)) {
                checkedItems[i] = true;
            } else {
                checkedItems[i] = false;
            }
        }
        ArrayList<String> services = new ArrayList<>();
        for (int i = 0; i < modelMainScreen.getData().getAvailable_services().size(); i++) {
            services.add("" + modelMainScreen.getData().getAvailable_services().get(i).getServiceName());
        }
        String[] stringArray = services.toArray(new String[0]);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMultiChoiceItems(stringArray, checkedItems, (dialog, indexSelected, isChecked) -> {
            if (isChecked) {
                SELECTED_SERVICES_TYPES.add(indexSelected);
            } else if (SELECTED_SERVICES_TYPES.contains(indexSelected)) {
                SELECTED_SERVICES_TYPES.remove(Integer.valueOf(indexSelected));
            }
        }).setPositiveButton(getResources().getString(R.string.ok), (dialog, id) -> {
            String services1 = "";
            for (int i = 0; i < SELECTED_SERVICES_TYPES.size(); i++) {
                if (i == (SELECTED_SERVICES_TYPES.size() - 1)) {  // that is last element
                    //  services = services + modelvehicleConfiguration.getData().getService_type().get(SELECTED_SERVICES_TYPES.get(i)).getId();
                    services1 = services1 + modelMainScreen.getData().getAvailable_services().get(SELECTED_SERVICES_TYPES.get(i)).getService_id();
                } else {
                    //    services = services + modelvehicleConfiguration.getData().getService_type().get(SELECTED_SERVICES_TYPES.get(i)).getId() + ",";
                    services1 = services1 + modelMainScreen.getData().getAvailable_services().get(SELECTED_SERVICES_TYPES.get(i)).getService_id() + ",";
                }
            }

            if (modelMainScreen.getData().getActive_rides().size() > 0) {
                if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("1")) {
                    Toast.makeText(activity, "Você não pode ficar off-line. Você tem viagem ativa", Toast.LENGTH_SHORT).show();
                } else {
                    data.put("service_type", "" + services1);
                    if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("1")) {
                        data.put("status", "2");
                    } else {
                        data.put("status", "1");
                    }

                    data.put("vehicle_id", "" + vehicle_id);

                    if (SELECTED_SERVICES_TYPES.size() != 0) {
                        try {
                            apiManager._post(API_S.Tags.ONLINE_OFFLINE, API_S.Endpoints.ONLINE_OFFLINE, data, sessionManager);
                        } catch (Exception e) {
                            Log.e(TAG, "Exceção detectada ao chamar API " + e.getMessage());
                        }
                    } else {
                        Toast.makeText(MainActivity.this, R.string.select_service_online, Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                data.put("service_type", "" + services1);
                if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("1")) {
                    data.put("status", "2");
                } else {
                    data.put("status", "1");
                }

                data.put("vehicle_id", "" + vehicle_id);

                if (SELECTED_SERVICES_TYPES.size() != 0) {
                    try {
                        apiManager._post(API_S.Tags.ONLINE_OFFLINE, API_S.Endpoints.ONLINE_OFFLINE, data, sessionManager);
                    } catch (Exception e) {
                        Log.e(TAG, "Exceção detectada ao chamar API " + e.getMessage());
                    }
                } else {
                    Toast.makeText(MainActivity.this, R.string.select_service_online, Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton(getResources().getString(R.string.cancel), (dialog, id) -> {
            if (sessionManager.getAppConfig().getData().getGeneral_config().isAdd_multiple_vehicle() || sessionManager.getAppConfig().getData().getGeneral_config().isExisting_vehicle_enable()) {
                if (sessionManager.getDemoOrNot().equals("2")) {
                    vehicle_id = "";
                    sessionManager.setVehicleId("");
                }

                try {
                    HashMap<String, String> data1 = new HashMap<>();
                    data1.put("vehicle_id", vehicle_id);
                    // if(sessionManager.getFirebaseNotification()==true){
                    //     data1.put("player_id", "" + FirebaseInstanceId.getInstance().getToken());
                    // }else{
                    //     data1.put("player_id", "" + OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId());
                    // }

                    data1.put("player_id", "" + OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId());
                    data1.put("device", "1");
                    apiManager._post(API_S.Tags.MAIN_SCREEN, API_S.Endpoints.MAIN_SCREEN, data1, sessionManager);
                    placeholder.removeAllViews();
                } catch (Exception e) {
                    Log.e("" + TAG, "Exceção detectada ao chamar API " + e.getMessage());
                }
            }

            // if (sessionManager.getAppConfig().getData().getGeneral_config().isAdd_multiple_vehicle() || sessionManager.getAppConfig().getData().getGeneral_config().isExisting_vehicle_enable()) {
            //     activatedCarText.setText("");
            //     activatedCarNumber.setText("");
            //     tvVehicleStatus.setText(getResources().getString(R.string.no_vehicle_activated_message));
            //     tvVehicleStatus.setTextColor(getResources().getColor(R.color.red_text));
            // } else {
            //     activatedCarText.setText("" + modelMainScreen.getData().getActive_vehicle().getVehicle_type());
            //     activatedCarNumber.setText("" + modelMainScreen.getData().getActive_vehicle().getVehicle_number());
            //     tvVehicleStatus.setText(getResources().getString(R.string.live_now));
            //     tvVehicleStatus.setTextColor(getResources().getColor(R.color.icons_8_muted_green_1));
            //     Glide.with(this).load("" + modelMainScreen.getData().getActive_vehicle().getVehicleTypeImage()).into(activatedCarImage);
            // }
            dialog.dismiss();
        });

        builder.show();
    }


    private void setDataAccordingToConfig() {

        frameAuto.setVisibility(sessionManager.getAppConfig().getData().getGeneral_config().isAuto_accept_mode() ? View.VISIBLE : View.GONE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();

        try {
        //  if(sessionManager.getFirebaseNotification()==true){
        //      MyFirebaseMessagingService.openScreen = 0;
        //  }else {
        //      OneSignalServiceClass.openScreen = 0;
        //  }

            OneSignalServiceClass.openScreen = 0;
        } catch (Exception e) {

        }

        clearVehicleId = 1;

        try {
            if (!sessionManager.getUserDetails().get(SessionManager.KEY_DriverVehicleId).equals("")) {
                vehicle_id = sessionManager.getUserDetails().get(SessionManager.KEY_DriverVehicleId);
                sessionManager.setVehicleIdForOnline(vehicle_id);
            } else {
                vehicle_id = sessionManager.getVehicleId();
                sessionManager.setVehicleIdForOnline(vehicle_id);
            }
        } catch (Exception e) {

        }


        this.doubleBackToExitPressedOnce = false;
        EventBus.getDefault().register(this);
        setStatusAccordingly();

        if (sessionManager.getAppConfig().getData().getGeneral_config().isAuto_accept_mode()) {
            setStatusForAutoAccept();
        }

        try {
            HashMap<String, String> data1 = new HashMap<>();
            data1.put("vehicle_id", vehicle_id);
            //  if(sessionManager.getFirebaseNotification()==true){
            //      data1.put("player_id", "" + FirebaseInstanceId.getInstance().getToken());
            //  }else{
            //      data1.put("player_id", "" + OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId());
            //  }
            data1.put("player_id", "" + OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId());
            data1.put("device", "1");
            apiManager._post(API_S.Tags.MAIN_SCREEN, API_S.Endpoints.MAIN_SCREEN, data1, sessionManager);
            placeholder.removeAllViews();
        } catch (Exception e) {
            Log.e("" + TAG, "Exceção detectada ao chamar API " + e.getMessage());
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setStatusAccordingly() {
        if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("1")) {
            startService();
            switchBtn.setChecked(true);
            onlineOfflineText.setText("" + this.getResources().getString(R.string.MAIN_ACTIVITY__online));
            onlineOfflineText.setTextColor(Color.parseColor("#2ecc71"));

            // startBackgroundTask();
        } else if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("2")) {
            switchBtn.setChecked(false);
            onlineOfflineText.setText("" + this.getResources().getString(R.string.MAIN_ACTIVITY__offline));
            onlineOfflineText.setTextColor(Color.parseColor("#e74c3c"));
            stopService();
            try {
                stopService(new Intent(this, FloatingViewService.class));
            } catch (Exception e) {

            }
            
            //   stopBackgroundTask();

        }
    }

    private void setData() {
        if (clearVehicleId == 1) {
            if (!vehicle_id.equals("")) {
                if (modelMainScreen.getData().getActive_vehicle().getVehicle_type() != null) {
                    activatedCarText.setText("" + modelMainScreen.getData().getActive_vehicle().getVehicle_type());
                    activatedCarNumber.setText("" + modelMainScreen.getData().getActive_vehicle().getVehicle_number());
                    tvVehicleStatus.setText(getResources().getString(R.string.live_now));
                    tvVehicleStatus.setTextColor(getResources().getColor(R.color.icons_8_muted_green_1));
                    Glide.with(this).load("" + modelMainScreen.getData().getActive_vehicle().getVehicleTypeImage()).into(activatedCarImage);
                } else {
                    if (sessionManager.getAppConfig().getData().getGeneral_config().isAdd_multiple_vehicle() || sessionManager.getAppConfig().getData().getGeneral_config().isExisting_vehicle_enable()) {
                        activatedCarText.setText("");
                        activatedCarNumber.setText("");
                        tvVehicleStatus.setText(getResources().getString(R.string.no_vehicle_activated_message));
                        tvVehicleStatus.setTextColor(getResources().getColor(R.color.icons_8_muted_grey));
                    } else {
                        activatedCarText.setText("" + modelMainScreen.getData().getActive_vehicle().getVehicle_type());
                        activatedCarNumber.setText("" + modelMainScreen.getData().getActive_vehicle().getVehicle_number());
                        tvVehicleStatus.setText(getResources().getString(R.string.live_now));
                        tvVehicleStatus.setTextColor(getResources().getColor(R.color.icons_8_muted_green_1));
                        Glide.with(this).load("" + modelMainScreen.getData().getActive_vehicle().getVehicleTypeImage()).into(activatedCarImage);
                    }

                }
            } else {
                if (sessionManager.getAppConfig().getData().getGeneral_config().isAdd_multiple_vehicle() || sessionManager.getAppConfig().getData().getGeneral_config().isExisting_vehicle_enable()) {
                    activatedCarText.setText("");
                    activatedCarNumber.setText("");
                    tvVehicleStatus.setText(getResources().getString(R.string.no_vehicle_activated_message));
                    tvVehicleStatus.setTextColor(getResources().getColor(R.color.icons_8_muted_grey));
                } else {
                    activatedCarText.setText("" + modelMainScreen.getData().getActive_vehicle().getVehicle_type());
                    activatedCarNumber.setText("" + modelMainScreen.getData().getActive_vehicle().getVehicle_number());
                    tvVehicleStatus.setText(getResources().getString(R.string.live_now));
                    tvVehicleStatus.setTextColor(getResources().getColor(R.color.icons_8_muted_green_1));
                    Glide.with(this).load("" + modelMainScreen.getData().getActive_vehicle().getVehicleTypeImage()).into(activatedCarImage);
                }
            }
        }
    }

    private void setStatusForAutoAccept() {

        if (sessionManager.getUserDetails().get(SessionManager.KEY_AUTO_ACCEPT).equals("1")) {
            switch_btn_auto.setChecked(true);
            auto_text.setTextColor(Color.parseColor("#2ecc71"));

        } else {
            switch_btn_auto.setChecked(false);
            auto_text.setTextColor(Color.parseColor("#e74c3c"));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setDataForMainScreen(String script) throws Exception {

        try {
            modelMainScreen = SingletonGson.getInstance().fromJson("" + script, ModelMainScreen.class);

            if(modelMainScreen.getData().isRides_according_to_gender()) {
                gender_option.setVisibility(View.VISIBLE);
            }else {
                gender_option.setVisibility(View.GONE);
            }

            try {
                frm1.setVisibility(modelMainScreen.getData().getGeofence_queue_enable() ? View.VISIBLE : View.GONE);
                igi.setText(modelMainScreen.getData().getGeofence_queue_text());
                if (modelMainScreen.getData().getGeofence_queue_color().equals("#FF0000")) {
                    igi.setBackgroundResource(R.drawable.rounded_btn);
                } else {
                    igi.setBackgroundResource(R.drawable.green_btn);
                }
            } catch (Exception e) {
                Toast.makeText(activity, "Exception 1 : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            if(sessionManager.getAppConfig().getData().getGeneral_config().getLat_long_storing_at()==2){
                try {
                    if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("1")) {
                      //  removeTagToAtsServer();
                        setTagToAtsServerifBusy(sessionManager.getOnlineOffileData(), modelMainScreen.getData().getFree_busy());
                    }
                } catch (Exception e) {
                    Log.e("Error in Update Tag", "" + e);
                }

            }


            try {
                llViewBottomLayout.setVisibility(View.VISIBLE);
                for (int i = 0; i < modelMainScreen.getData().getActive_rides().size(); i++) {
                    placeholder.addView(new HolderActiveRide(this, modelMainScreen.getData().getActive_rides().get(i)));
                }

                for (int i = 0; i < modelMainScreen.getData().getDocument_message().size(); i++) {

                    if (modelMainScreen.getData().getDocument_message().get(i).isShow()) {
                        sessionManager.setonline_offline(false);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            setStatusAccordingly();
                        }
                        showDialogForOpenEvent(modelMainScreen.getData().getDocument_message().get(i).getMessage(), modelMainScreen.getData().getDocument_message().get(i).getAction());
                    }
                }

            } catch (Exception e) {
                Toast.makeText(activity, "Exception 2 : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }


            try {
                for (int i = 0; i < modelMainScreen.getData().getAdmin_message().size(); i++) {

                    Log.e("***clearVehicleId", "" + clearVehicleId);
                    // if (modelMainScreen.getData().getAdmin_message().get(i).getShow()) {
                    // placeholder.addView(new HolderSubscription(this, modelMainScreen.getData().getAdmin_message().get(i)));
                    // }
                    if (modelMainScreen.getData().getAdmin_message().get(i).getShow()) {
                        sessionManager.setonline_offline(false);
                        setStatusAccordingly();
                        if (modelMainScreen.getData().getAdmin_message().get(i).getAction().equals("SUBSCRIPTION_BUY")) {
                            //  if (clearVehicleId != 2) {
                            //      if (sessionManager.getAppConfig().getData().getGeneral_config().isAdd_multiple_vehicle() || sessionManager.getAppConfig().getData().getGeneral_config().isExisting_vehicle_enable()) {
                            //         if (sessionManager.getDemoOrNot().equals("2")) {
                            //             vehicle_id = "";
                            //             sessionManager.setVehicleId("");
                            //         }
                            //     }
                            // }
                            sessionManager.setonline_offline(false);

                            setStatusAccordingly();
                            Toast.makeText(MainActivity.this, "" + modelMainScreen.getData().getAdmin_message().get(i).getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (modelMainScreen.getData().getAdmin_message().get(i).getAction().equals("SUBSCRIPTION_EXPIRE")) {
                            //  if (clearVehicleId != 2) {
                            //      if (sessionManager.getAppConfig().getData().getGeneral_config().isAdd_multiple_vehicle() || sessionManager.getAppConfig().getData().getGeneral_config().isExisting_vehicle_enable()) {
                            //          if (sessionManager.getDemoOrNot().equals("2")) {
                            //              vehicle_id = "";
                            //              sessionManager.setVehicleId("");
                            //          }
                            //      }
                            //  }
                            sessionManager.setonline_offline(false);

                            setStatusAccordingly();
                            Toast.makeText(MainActivity.this, "" + modelMainScreen.getData().getAdmin_message().get(i).getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (modelMainScreen.getData().getAdmin_message().get(i).getAction().equals("WALLET_BALANCE")) {

                            // if (clearVehicleId != 2) {
                            //     if (sessionManager.getAppConfig().getData().getGeneral_config().isAdd_multiple_vehicle() || sessionManager.getAppConfig().getData().getGeneral_config().isExisting_vehicle_enable()) {
                            //          if (sessionManager.getDemoOrNot().equals("2")) {
                            //              vehicle_id = "";
                            //              sessionManager.setVehicleId("");
                            //          }
                            //      }
                            //  }

                            sessionManager.setonline_offline(false);

                            setStatusAccordingly();
                            Toast.makeText(MainActivity.this, "" + modelMainScreen.getData().getAdmin_message().get(i).getMessage(), Toast.LENGTH_SHORT).show();
                        } else {


                            // if (clearVehicleId != 2) {
                            //     if (sessionManager.getAppConfig().getData().getGeneral_config().isAdd_multiple_vehicle() || sessionManager.getAppConfig().getData().getGeneral_config().isExisting_vehicle_enable()) {
                            //         if (sessionManager.getDemoOrNot().equals("2")) {
                            //             vehicle_id = "";
                            //             sessionManager.setVehicleId("");
                            //         }
                            //     }
                            // }

                            sessionManager.setonline_offline(false);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                setStatusAccordingly();
                            }
                            showDialogForOpenEvent(modelMainScreen.getData().getAdmin_message().get(i).getMessage(), modelMainScreen.getData().getAdmin_message().get(i).getAction());
                        }
                    }
                }
                bottomBell.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                Toast.makeText(activity, "Exception 3 : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            try {
                mainScreenConterText.setVisibility(View.VISIBLE);
                placeholderLayout.setVisibility(View.INVISIBLE);
                bottomCross.setVisibility(View.GONE);


                todaysBooking.setText("" + modelMainScreen.getData().getTodays().getTodays_rides());
                todaysBooking.setTextColor(Color.parseColor("#" + modelMainScreen.getData().getTodays().getTodays_rides_color()));
                todaysEarning.setText("" + modelMainScreen.getData().getTodays().getTodays_earning());
                //todaysEarning.setTextColor(Color.parseColor("#" + modelMainScreen.getData().getTodays().getTodays_earning_color()));
                //  mainScreenConterText.setText("" + (modelMainScreen.getData().getAdmin_message().size() + modelMainScreen.getData().getActive_rides().size() + modelMainScreen.getData().getScheduled_rides().size()));

                if (clearVehicleId != 2) {
                    if (!vehicle_id.equals("")) {
                        if (modelMainScreen.getData().getActive_vehicle().getVehicle_type() != null) {
                            activatedCarText.setText("" + modelMainScreen.getData().getActive_vehicle().getVehicle_type());
                            activatedCarNumber.setText("" + modelMainScreen.getData().getActive_vehicle().getVehicle_number());
                            tvVehicleStatus.setText(getResources().getString(R.string.live_now));
                            tvVehicleStatus.setTextColor(getResources().getColor(R.color.icons_8_muted_green_1));
                            Glide.with(this).load("" + modelMainScreen.getData().getActive_vehicle().getVehicleTypeImage()).into(activatedCarImage);
                            // veículo on-line página Ordens de coleta
                            if (startVehhicle == 1) {
                                startVehhicle = 0;
                                startActivity(new Intent(MainActivity.this, TripHistoryActivity.class));
                            }
                        } else {
                            if (sessionManager.getAppConfig().getData().getGeneral_config().isAdd_multiple_vehicle() || sessionManager.getAppConfig().getData().getGeneral_config().isExisting_vehicle_enable()) {
                                activatedCarText.setText("");
                                activatedCarNumber.setText("");
                                tvVehicleStatus.setText(getResources().getString(R.string.no_vehicle_activated_message));
                                tvVehicleStatus.setTextColor(getResources().getColor(R.color.icons_8_muted_grey));
                                Glide.with(this).load("").into(activatedCarImage);
                            } else {
                                activatedCarText.setText("" + modelMainScreen.getData().getActive_vehicle().getVehicle_type());
                                activatedCarNumber.setText("" + modelMainScreen.getData().getActive_vehicle().getVehicle_number());
                                tvVehicleStatus.setText(getResources().getString(R.string.live_now));
                                tvVehicleStatus.setTextColor(getResources().getColor(R.color.icons_8_muted_green_1));
                                Glide.with(this).load("" + modelMainScreen.getData().getActive_vehicle().getVehicleTypeImage()).into(activatedCarImage);
                            }
                        }
                    } else {
                        if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("1")) {
                            activatedCarText.setText("" + modelMainScreen.getData().getActive_vehicle().getVehicle_type());
                            activatedCarNumber.setText("" + modelMainScreen.getData().getActive_vehicle().getVehicle_number());
                            tvVehicleStatus.setText(getResources().getString(R.string.live_now));
                            tvVehicleStatus.setTextColor(getResources().getColor(R.color.icons_8_muted_green_1));
                            Glide.with(this).load("" + modelMainScreen.getData().getActive_vehicle().getVehicleTypeImage()).into(activatedCarImage);
                        } else {
                            if (sessionManager.getAppConfig().getData().getGeneral_config().isAdd_multiple_vehicle() || sessionManager.getAppConfig().getData().getGeneral_config().isExisting_vehicle_enable()) {
                                activatedCarText.setText("");
                                activatedCarNumber.setText("");
                                tvVehicleStatus.setText(getResources().getString(R.string.no_vehicle_activated_message));
                                tvVehicleStatus.setTextColor(getResources().getColor(R.color.icons_8_muted_grey));
                                Glide.with(this).load("").into(activatedCarImage);
                            } else {
                                activatedCarText.setText("" + modelMainScreen.getData().getActive_vehicle().getVehicle_type());
                                activatedCarNumber.setText("" + modelMainScreen.getData().getActive_vehicle().getVehicle_number());
                                tvVehicleStatus.setText(getResources().getString(R.string.live_now));
                                tvVehicleStatus.setTextColor(getResources().getColor(R.color.icons_8_muted_green_1));
                                Glide.with(this).load("" + modelMainScreen.getData().getActive_vehicle().getVehicleTypeImage()).into(activatedCarImage);
                            }
                        }
                    }
                }

            } catch (Exception e) {
                Toast.makeText(activity, "Exception 4 : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            try {
                Log.e("Active Rides", "" + modelMainScreen.getData().getActive_rides().size());
                int a = Integer.parseInt(String.valueOf(modelMainScreen.getData().getActive_rides().size()));
                mainScreenConterText.setText(String.valueOf(a));
            } catch (Exception e) {
                Toast.makeText(activity, "Exception 5 : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }


            try {
                btn_manual_dispatch.setVisibility(sessionManager.getAppConfig().getData().getGeneral_config().isManual_dispatch() ? View.VISIBLE : View.GONE);


                if (!sessionManager.getTaxiCompany()) {
                    // earningBtn.setVisibility(View.VISIBLE);
                    // walletBtn.setVisibility(sessionManager.getAppConfig().getData().getNavigation_drawer().isWallet() ? View.VISIBLE : View.GONE);
                } else {
                    // walletBtn.setVisibility(View.GONE);
                    // earningBtn.setVisibility(View.GONE);
                }


                if (modelMainScreen.getData().getTerm_status() == 1) {
                    if (openScreenTerms == 0) {
                        startActivity(new Intent(this, TermsAndCondition.class)
                                .putExtra(Config.IntentKeys.TERMS_CONDITION, "acceptmain"));
                        openScreenTerms = 1;
                    }
                }

            } catch (Exception e) {
                Toast.makeText(activity, "Exception 6 : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                // walletBtn.setVisibility(sessionManager.getAppConfig().getData().getNavigation_drawer().isWallet() ? View.VISIBLE : View.GONE);
            }

        } catch (Exception e) {

            progressDialog.dismiss();

            Log.e("Exception", "" + e);
        }

        progressDialog.dismiss();
    }

    @Override
    public void onAPIRunningState(int a, String APINAME) {
        try {
            if (APINAME.equals(API_S.Tags.ONLINE_OFFLINE)) {
                if (a == ApiManager.APIFETCHER.KEY_API_IS_STARTED) {
                    progressDialog.show();
                } else if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
            if (APINAME.equals(API_S.Tags.GEOFENCE)) {
                if (a == ApiManager.APIFETCHER.KEY_API_IS_STARTED) {
                    progressDialog.show();
                } else if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
            if (APINAME.equals(API_S.Tags.MAIN_SCREEN)) {
                if (mainViewLoader == 0) {
                    if (a == ApiManager.APIFETCHER.KEY_API_IS_STARTED) {
                        progressDialog.show();
                    } else if (progressDialog.isShowing()) {
                    }
                    mainViewLoader = 1;
                }
            }
        } catch (Exception e) {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onFetchComplete(Object script, String APINAME) {
        try {
            switch (APINAME) {
                case API_S.Tags.ONLINE_OFFLINE:
                    ModelDeviceOnlineIffline modelDeviceOnlineIffline = SingletonGson.getInstance().fromJson("" + script, ModelDeviceOnlineIffline.class);
                    try {
                        if (sessionManager.getAppConfig().getData().getGeneral_config().getLat_long_storing_at() == 2) {
                            try{
                                sessionManager.setOnlineOfflineTag("" + script);
                            }catch (Exception e){

                            }
                            setTagToAtsServer(modelDeviceOnlineIffline);
                        }
                    } catch (Exception e) {

                    }

                    if (modelDeviceOnlineIffline.getData().getOnline_offline().equals("1")) {
                        sessionManager.setonline_offline(true);
                        if (sessionManager.getAppConfig().getData().getGeneral_config().isAdd_multiple_vehicle() || sessionManager.getAppConfig().getData().getGeneral_config().isExisting_vehicle_enable()) {
                            activatedCarText.setText("");
                            activatedCarNumber.setText("");
                            tvVehicleStatus.setText(getResources().getString(R.string.no_vehicle_activated_message));
                            tvVehicleStatus.setTextColor(getResources().getColor(R.color.icons_8_muted_grey));
                            Glide.with(this).load("").into(activatedCarImage);
                        } else {
                            activatedCarText.setText("" + modelMainScreen.getData().getActive_vehicle().getVehicle_type());
                            activatedCarNumber.setText("" + modelMainScreen.getData().getActive_vehicle().getVehicle_number());
                            tvVehicleStatus.setText(getResources().getString(R.string.live_now));
                            tvVehicleStatus.setTextColor(getResources().getColor(R.color.icons_8_muted_green_1));
                            Glide.with(this).load("" + modelMainScreen.getData().getActive_vehicle().getVehicleTypeImage()).into(activatedCarImage);
                        }
                        try {
                            startService();
                        } catch (Exception e) {

                        }
                    } else if (modelDeviceOnlineIffline.getData().getOnline_offline().equals("2")) {
                        if (sessionManager.getAppConfig().getData().getGeneral_config().isAdd_multiple_vehicle() || sessionManager.getAppConfig().getData().getGeneral_config().isExisting_vehicle_enable()) {
                            if (sessionManager.getDemoOrNot().equals("2")) {
                                vehicle_id = "";
                                sessionManager.setVehicleId("");
                            }
                        }
                        sessionManager.setonline_offline(false);

                    }

                    try {
                        HashMap<String, String> data1 = new HashMap<>();
                        data1.put("vehicle_id", vehicle_id);
                        // if(sessionManager.getFirebaseNotification()==true){
                        //     data1.put("player_id", "" + FirebaseInstanceId.getInstance().getToken());
                        // }else{
                        //     data1.put("player_id", "" + OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId());
                        // }
                        data1.put("player_id", "" + OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId());
                        data1.put("device", "1");
                        apiManager._post(API_S.Tags.MAIN_SCREEN, API_S.Endpoints.MAIN_SCREEN, data1, sessionManager);
                        placeholder.removeAllViews();
                    } catch (Exception e) {
                        Log.e("" + TAG, "Exceção detectada ao chamar API " + e.getMessage());
                    }

                    setStatusAccordingly();
                    break;

                case API_S.Tags.GEOFENCE:
                    GeoFence geoFence = SingletonGson.getInstance().fromJson("" + script, GeoFence.class);
                    if (geoFence.getResult().equals("1")) {
                        if (geoFence.getGeofence_queue_color().equals("#FF0000")) {
                            igi.setBackgroundResource(R.drawable.rounded_btn);
                            igi.setText(geoFence.getGeofence_queue_text());
                        } else {
                            igi.setText(geoFence.getGeofence_queue_text());
                            igi.setBackgroundResource(R.drawable.green_btn);
                        }
                    }

                    break;

                case API_S.Tags.CHANGE_RIDE_GENDER:
                    try{
                        progressDialog.dismiss();
                        JSONObject object = new JSONObject(script+"");
                        Toast.makeText(activity, "Solicitação de viagem alterada com sucesso !", Toast.LENGTH_SHORT).show();
                        if (sessionManager.getAppConfig().getData().getGeneral_config().isAuto_accept_mode()) {
                            setStatusForAutoAccept();
                        }

                        try {
                            HashMap<String, String> data1 = new HashMap<>();
                            data1.put("vehicle_id", vehicle_id);
                            apiManager._post(API_S.Tags.MAIN_SCREEN, API_S.Endpoints.MAIN_SCREEN, data1, sessionManager);
                            placeholder.removeAllViews();
                        } catch (Exception e) {
                            Log.e("" + TAG, "Exceção detectada ao chamar API " + e.getMessage());
                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }


                    break;
                case API_S.Tags.AUTO_ACCEPT:
                    ModelAutoAccept modelAutoAccept = SingletonGson.getInstance().fromJson("" + script, ModelAutoAccept.class);
                    if (modelAutoAccept.getAutoAccept().equals("1")) {
                        sessionManager.setAutoAccept(true);
                    } else if (modelAutoAccept.getAutoAccept().equals("2")) {
                        sessionManager.setAutoAccept(false);
                    }
                    setStatusForAutoAccept();
                    break;


                case API_S.Tags.ACTIVE_RIDE:
                    final ModelActive modelActive = SingletonGson.getInstance().fromJson("" + script, ModelActive.class);
                    if (modelActive.getData().size() > 0) {
                        Snackbar.make(root, getString(R.string.you_have_one_onegoing_ride) + " #" + modelActive.getData().get(0).getId(), Snackbar.LENGTH_SHORT)
                                .setAction(R.string.go_to_ride, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(MainActivity.this, TrackerActivity.class)
                                                .putExtra("" + IntentKeys.BOOKING_ID, "" + modelActive.getData().get(0).getId()));
                                    }
                                }).setActionTextColor(Color.parseColor("#ffffff")).show();
                    }
                    break;
                case API_S.Tags.DEMAND_SPOT:
                    ModelDemandSpot modelDemandSpot = SingletonGson.getInstance().fromJson("" + script, ModelDemandSpot.class);
                    DEMANDSPOT = true;
                    setheatmap(modelDemandSpot);
                    break;
                case API_S.Tags.MAIN_SCREEN:
                    try {
                        setDataForMainScreen("" + script);
                    } catch (Exception e) {
                        // Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        //  Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        Log.e("" + TAG, "Exception caught while setting data " + e.getMessage());
                    }

                    if (openDialog == 2) {
                        try {
                            progressDialog.dismiss();
                        } catch (Exception e) {
                        }
                        openSelectServiceDialog();
                        openDialog = 1;
                    }

                    break;

                case API_S.Tags.LOGOUT:
                    sessionManager.setonline_offline(false);
                    sessionManager.logoutUser();
                    sessionManager.clearAccessToken("");
                    //    stopBackgroundTask();
                    stopService();
                    finish();
                    startActivity(new Intent(MainActivity.this, SplashActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    break;

            }
        } catch (Exception e) {
            //  Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            //  Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {
        Toast.makeText(MainActivity.this, "" + script, Toast.LENGTH_SHORT).show();
        // Snackbar.make(root, "" + script, Snackbar.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusAccordingly();
        }
        if (sessionManager.getAppConfig().getData().getGeneral_config().isAuto_accept_mode()) {
            setStatusForAutoAccept();
        }

        if (APINAME.equals("ONLINE_OFFLINE")) {
            stopService();
            if (sessionManager.getAppConfig().getData().getGeneral_config().isAdd_multiple_vehicle() || sessionManager.getAppConfig().getData().getGeneral_config().isExisting_vehicle_enable()) {
                sessionManager.setVehicleId("");
                activatedCarText.setText("");
                vehicle_id = "";
                activatedCarNumber.setText("");
                tvVehicleStatus.setText(getResources().getString(R.string.no_vehicle_activated_message));
                tvVehicleStatus.setTextColor(getResources().getColor(R.color.icons_8_muted_grey));
            } else {
                activatedCarText.setText("" + modelMainScreen.getData().getActive_vehicle().getVehicle_type());
                activatedCarNumber.setText("" + modelMainScreen.getData().getActive_vehicle().getVehicle_number());
                tvVehicleStatus.setText(getResources().getString(R.string.live_now));
                tvVehicleStatus.setTextColor(getResources().getColor(R.color.icons_8_muted_green_1));
                Glide.with(this).load("" + modelMainScreen.getData().getActive_vehicle().getVehicleTypeImage()).into(activatedCarImage);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (sessionManager.getAppConfig().getData().getGeneral_config().isAdd_multiple_vehicle() || sessionManager.getAppConfig().getData().getGeneral_config().isExisting_vehicle_enable()) {
            if (sessionManager.getDemoOrNot().equals("2")) {
                if (requestCode == 113 && resultCode == RESULT_OK) {
                    vehicle_id = data.getExtras().getString("VEHICLE_ID");

                    if (vehicle_id == null || vehicle_id.equals("")) {
                        clearVehicleId = 1;
                    } else {
                        if (sessionManager.getAppConfig().getData().getGeneral_config().isService_type_selection()) {
                            openDialog = 2;
                        }
                        clearVehicleId = 2;
                        sessionManager.setVehicleId(vehicle_id);
                        sessionManager.setVehicleIdForOnline(vehicle_id);

                        if (!sessionManager.getAppConfig().getData().getGeneral_config().isService_type_selection()) {
                            if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("2")) {
                                HashMap<String, String> data1 = new HashMap<>();
                                if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("1")) {
                                    data1.put("status", "2");
                                } else {
                                    data1.put("status", "1");
                                }

                                data1.put("vehicle_id", "" + vehicle_id);
                                try {
                                    apiManager._post(API_S.Tags.ONLINE_OFFLINE, API_S.Endpoints.ONLINE_OFFLINE, data1, sessionManager);
                                } catch (Exception e) {
                                    Log.e(TAG, "Exceção detectada ao chamar API " + e.getMessage());
                                }
                            }
                        }
                    }

                    try {
                        progressDialog.show();
                    } catch (Exception e) {
                        Log.e("" + TAG, "Exceção detectada ao chamar API " + e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        mGoogleMap.setMaxZoomPreference(20);

        if (checkPermission())
            progressDialog.show();
        onLocationPermissionGranted();
    }

    private boolean checkPermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Peça a permissão
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            Toast.makeText(this, "De permissão de localização", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    private void onLocationPermissionGranted() {
        if (!checkPermission()) return;
        mGoogleMap.setMyLocationEnabled(true);

        View locationButton = ((View) locationButtonView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        // e em seguida coloque-o, no canto inferior direito (como aplicativo do Google Maps)
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
            locationButton.getLayoutParams();
        // posição na parte inferior direita
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        layoutParams.setMargins(0, 0, 30, 200);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {
                            EventBus.getDefault().post(new EventLocation(location));
                            userLocation = location;

                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()))
                                    .zoom(5) // zoom página inicial do motorista                                    
                                    .build();
                            //  mGoogleMap.clear();
                            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        } else {
                            userLocation = null;
                        }
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventLocation event) {
        //  Maputils.moverCamera(mGoogleMap, new LatLng(event.location.getLatitude(), event.location.getLongitude()));
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String data) {
        try{
            ModelRideNotifications modelRideNotifications = SingletonGson.getInstance().fromJson("" + data, ModelRideNotifications.class);
            if (modelRideNotifications.getType() == 6) {// Excluir driver do painel de administração
                try {
                    apiManager._post("" + API_S.Tags.LOGOUT, "" + API_S.Endpoints.LOGOUT, null, sessionManager);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    Log.e("" + TAG, "Exceção detectada ao chamar API " + e.getMessage());
                }
            } else if (modelRideNotifications.getType() == NOTIFICATION_TYPES.RIDE) { // Excluir driver do painel de administração

                if (modelRideNotifications.getData().getBooking_type().equals("2")) {
                    try {
                        setMediaSound();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    showDialogForShowOutStation(modelRideNotifications.getData().getOutstation_msg(), modelRideNotifications);
                }
            }

            ModelNotificationType modelNotificationType = SingletonGson.getInstance().fromJson("" + data, ModelNotificationType.class);

            if (modelNotificationType.getType() == 2) {
                try {

                    // ModelNotificationChat modelNotificationChat = SingletonGson.getInstance().fromJson("" + data, ModelNotificationChat.class);
                    notification.setVisibility(View.VISIBLE);
                    if (!notification.getText().toString().isEmpty()) {
                        notification.setText("" + (Integer.parseInt("" + notification.getText().toString()) + 1));
                        // Toast.makeText("MAINACTIVITY",""+notification.getText(),Toast.LENGTH_SHORT).show();
                    } else {
                        notification.setText("1");
                    }
                } catch (Exception e) {
                    // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    Log.d("" + TAG, "Exceção detectada ao chamar API " + e.getMessage());
                }
            }

        }catch (Exception e){

        }

    }


    private void setheatmap(ModelDemandSpot modelDemandSpot) throws Exception {
        List<LatLng> list = new ArrayList<>();

        try {
            for (int i = 0; i < modelDemandSpot.getData().size(); i++) {
                list.add(new LatLng(Double.parseDouble("" + modelDemandSpot.getData().get(i).getPickup_latitude()), Double.parseDouble("" + modelDemandSpot.getData().get(i).getPickup_longitude())));
            }

        } catch (Exception e) {

        }

        // Crie um provedor de blocos de mapa de calor, passando-o pelos latlngs das delegacias.
        HeatmapTileProvider mProvider = new HeatmapTileProvider.Builder()
                .data(list)
                .build();
        // Adicione uma sobreposição de blocos ao mapa usando o provedor de blocos de mapa de calor.
        TileOverlay mOverlay = mGoogleMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));


        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < list.size(); i++) {
            builder.include(list.get(i));
        }
        LatLngBounds bounds = builder.build();
        Point displaySize = new Point();
        this.getWindowManager().getDefaultDisplay().getSize(displaySize);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 1300, 100));

    }

    private void showDialogForOpenEvent(String message, String action) {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Configurando a visualização de diálogo
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);

        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
        dialog.setTitle(null);
        dialog.setCancelable(false);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_open_event);

        TextView textView = (TextView) dialog.findViewById(R.id.tvMessage);
        textView.setText(message);

        Button btnOkay = (Button) dialog.findViewById(R.id.btnOkay);

        btnOkay.setOnClickListener(v -> {
            if (action.equals("SUBSCRIPTION_BUY")) {
            //  data.clear();
            //  data.put("status", "2");
            //  data.put("vehicle_id", "" + vehicle_id);
            //  try {
            // apiManager._post(API_S.Tags.ONLINE_OFFLINE, API_S.Endpoints.ONLINE_OFFLINE, data, sessionManager);
            //  } catch (Exception e) {
            // Log.e(TAG, "Exceção detectada ao chamar API " + e.getMessage());
            // }
                startActivity(new Intent(MainActivity.this, SubscriptionModuleList.class));
            } else if (action.equals("SUBSCRIPTION_EXPIRE")) {

                startActivity(new Intent(MainActivity.this, SubscriptionModuleList.class));
            } else if (action.equals("DOCUMENT_STATUS")) {
                startActivity(new Intent(MainActivity.this, PersonalDocumentActivity.class));
            } else if (action.equals("GENDER_UPDATE")) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            } else if (action.equals("")) {
            }

            dialog.dismiss();
        });


        dialog.show();
    }

    private void showDialogForShowOutStation(String message, ModelRideNotifications modelRideNotifications) {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Setting dialogview
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);

        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
        dialog.setTitle(null);
        dialog.setCancelable(false);

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_open_event);

        TextView textView = (TextView) dialog.findViewById(R.id.tvMessage);
        textView.setText(message);
        Button btnOkay = (Button) dialog.findViewById(R.id.btnOkay);

        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                SoundTimer.cancel();
                startActivity(new Intent(MainActivity.this, SpecificTripDetailsActivity.class)
                        .putExtra("" + IntentKeys.BOOKING_ID, "" + modelRideNotifications.getData().getBooking_id()));
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void setMediaSound() throws Exception {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(this, Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.message_pops));
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
        mediaPlayer.prepare();
        SoundTimer = new CountDownTimer(30000, 2000) {
            @Override
            public void onTick(long l) {
                mediaPlayer.start();
            }

            @Override
            public void onFinish() {
            }
        }.start();
    }

    public static void writeStringAsFile(Context context, final String fileContents, String fileName) {
        try {
            FileWriter out = new FileWriter(new File(context.getFilesDir(), fileName));
            out.write(fileContents);
            out.close();
        } catch (IOException e) {
            Log.e("Exception", "" + e);
        }
    }


    public static String readFileAsString(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(new File(context.getFilesDir(), fileName)));
            while ((line = in.readLine()) != null) stringBuilder.append(line);

        } catch (FileNotFoundException e) {
            Log.e("Exxception", "" + e);
        } catch (IOException e) {
            Log.e("Exception", "" + e);
        }

        return stringBuilder.toString();
    }

    private String loadAssetTextAsString(Context context, String name) {
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = context.getAssets().open(name);
            in = new BufferedReader(new InputStreamReader(is));

            String str;
            boolean isFirst = true;
            while ((str = in.readLine()) != null) {
                if (isFirst)
                    isFirst = false;
                else
                    buf.append('\n');
                buf.append(str);
            }
            return buf.toString();
        } catch (IOException e) {
            Log.e(TAG, "Error opening asset " + name);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e(TAG, "Error closing asset " + name);
                }
            }
        }

        return null;
    }


    private void updateLocationToSession(Location location) {
        Log.d("****" + TAG, "UpdatingLocationToSession");
        if (location.getBearing() != 0.0) {
            app_location_mamanger.setBearingFactor("" + location.getBearing());
        }
        app_location_mamanger.setLocationLatLong(location);
    }


    public void startService() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(this, UpdateServiceClass.class));
        } else { // normal
            startService(new Intent(this, UpdateServiceClass.class));
        }
        //  if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("1")) {
        //  }
    }

    public void stopService() {
        stopService(new Intent(this, UpdateServiceClass.class));
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.click_back), Toast.LENGTH_SHORT).show();
    }

    private void setTagToAtsServer(ModelDeviceOnlineIffline modelDeviceOnlineIffline) {
        ATS.setTag(AppUtils.getCurrentTagByStatus(modelDeviceOnlineIffline), new AtsOnTagSetListener() {
            @Override
            public void onSuccess(String message) {
                Log.e("Socket Response", "" + message);
            }

            @Override
            public void onFailed(String message) {
                Log.e("Socket Response", "" + message);
            }
        });
    }

    private void setTagToAtsServerifBusy(ModelDeviceOnlineIffline modelDeviceOnlineIffline, int free_busy) {
        ATS.setTag(AppUtils.getCurrentTagByStatusIfBusy(modelDeviceOnlineIffline, free_busy), new AtsOnTagSetListener() {
            @Override
            public void onSuccess(String message) {
                Log.e("Socket Response", "" + message);
            }

            @Override
            public void onFailed(String message) {
                Log.e("Socket Response", "" + message);
            }
        });
    }


    private void removeTagToAtsServer() {
        ATS.removeTag(new AtsOnTagSetListener() {
            @Override
            public void onSuccess(String message) {
                Log.e("Socket Response", "" + message);
            }

            @Override
            public void onFailed(String message) {
                Log.e("Socket Response", "" + message);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            SoundTimer.cancel();
        }catch (Exception e){

        }

    }

    private void showDialogForGender() {
        Dialog builder = new Dialog(this);
        builder.setContentView(R.layout.dialog_females_driver);
        SwitchCompat compat = builder.findViewById(R.id.switch_female);
        try {
            if (modelMainScreen.getData().getRider_gender_choice().equals("0")){
                compat.setChecked(false);
            }else {
                compat.setChecked(true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Button btnOk = builder.findViewById(R.id.ok);
        Button btnCancel = builder.findViewById(R.id.cancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    progressDialog.show();
                    HashMap<String,String> map = new HashMap<>();
                    if(compat.isChecked()) {
                        map.put("rider_gender_choice", "2");
                    }else {
                        map.put("rider_gender_choice","0");
                    }
                    apiManager._post(API_S.Tags.CHANGE_RIDE_GENDER,API_S.Endpoints.CHANGE_RIDE_GENDER,map,sessionManager);
                }catch (Exception e){
                    e.printStackTrace();
                }
                builder.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        builder.show();
    }

    private void manageVehicle() {
        if (sessionManager.getAppConfig().getData().getGeneral_config().isService_type_selection()) {
            if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("2")) {
                if (sessionManager.getAppConfig().getData().getGeneral_config().isAdd_multiple_vehicle()
                        || sessionManager.getAppConfig().getData().getGeneral_config().isExisting_vehicle_enable()) {
                    if (sessionManager.getDemoOrNot().equals("2")) {
                        SELECTED_SERVICES_TYPES.clear();
                        startActivityForResult(new Intent(MainActivity.this, VehicleListActivity.class)
                                .putExtra("SHOW_VEHICLE_LIST", "YES"), 113);
                    } else {
                        openSelectServiceDialog();
                    }
                } else {
                    openSelectServiceDialog();
                }
            } else {
                HashMap<String, String> data = new HashMap<>();
                if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("1")) {
                    data.put("status", "2");
                } else if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("2")) {
                    data.put("status", "1");
                } else {
                    data.put("status", "2");
                }

                data.put("vehicle_id", "" + vehicle_id);
                try {
                    apiManager._post(API_S.Tags.ONLINE_OFFLINE, API_S.Endpoints.ONLINE_OFFLINE, data,
                            sessionManager);
                } catch (Exception e) {
                    Log.e(TAG, "Exceção detectada ao chamar API " + e.getMessage());
                }
            }
        } else {
            if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("2")) {

                if (sessionManager.getAppConfig().getData().getGeneral_config().isAdd_multiple_vehicle()
                        || sessionManager.getAppConfig().getData().getGeneral_config()
                                .isExisting_vehicle_enable()) {
                    if (sessionManager.getDemoOrNot().equals("2")) {
                        startActivityForResult(new Intent(MainActivity.this, VehicleListActivity.class)
                                .putExtra("SHOW_VEHICLE_LIST", "YES"), 113);
                    } else {
                        HashMap<String, String> data = new HashMap<>();
                        if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE)
                                .equals("1")) {
                            data.put("status", "2");
                        } else if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE)
                                .equals("2")) {
                            data.put("status", "1");
                        } else {
                            data.put("status", "2");
                        }

                        data.put("vehicle_id", "" + vehicle_id);

                        try {
                            apiManager._post(API_S.Tags.ONLINE_OFFLINE, API_S.Endpoints.ONLINE_OFFLINE, data,
                                    sessionManager);
                        } catch (Exception e) {
                            Log.e(TAG, "Exceção detectada ao chamar API " + e.getMessage());
                        }
                    }
                } else {
                    HashMap<String, String> data = new HashMap<>();
                    if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("1")) {
                        data.put("status", "2");
                    } else if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE)
                            .equals("2")) {
                        data.put("status", "1");
                    } else {
                        data.put("status", "2");
                    }

                    data.put("vehicle_id", "" + vehicle_id);

                    try {
                        apiManager._post(API_S.Tags.ONLINE_OFFLINE, API_S.Endpoints.ONLINE_OFFLINE, data,
                                sessionManager);
                    } catch (Exception e) {
                        Log.e(TAG, "Exceção detectada ao chamar API " + e.getMessage());
                    }
                }

            } else {
                HashMap<String, String> data = new HashMap<>();
                if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("1")) {
                    data.put("status", "2");
                } else if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("2")) {
                    data.put("status", "1");
                } else {
                    data.put("status", "2");
                }

                data.put("vehicle_id", "" + vehicle_id);

                try {
                    apiManager._post(API_S.Tags.ONLINE_OFFLINE, API_S.Endpoints.ONLINE_OFFLINE, data,
                            sessionManager);
                } catch (Exception e) {
                    Log.e(TAG, "Exceção detectada ao chamar API " + e.getMessage());
                }
            }
        }
    }
}
