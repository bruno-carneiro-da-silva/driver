package com.tkx.driver;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.cardview.widget.CardView;

import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apporioinfolabs.ats_sdk.ATS;
import com.apporioinfolabs.ats_sdk.AtsOnTripSetListener;
import com.tkx.driver.activities.placepicker.NewPlacePickerActivity;
import com.tkx.driver.baseClass.BaseActivity;
import com.tkx.driver.currentwork.API_S;
import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.location.SamLocationRequestService;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.models.ModelManualCheckOut;
import com.tkx.driver.models.ModelManualEstimate;
import com.tkx.driver.models.ModelRideInfo;
import com.tkx.driver.others.AppUtils;
import com.tkx.driver.samwork.ApiManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManualUserDetailActivity extends BaseActivity implements ApiManager.APIFETCHER {

    private static final int OTP_REQUEST_CODE = 9;
    private final String TAG = "ManualDispatchActivity";
    private final int PLACE_PICKER_ACTIVITY = 111;

    LocationSession locationSession;
    SessionManager sessionManager;

    @BindView(R.id.ll_back_rides)
    LinearLayout ll_back_rides;

    @BindView(R.id.textView_dropPoint)
    TextView textView_dropPoint;

    @BindView(R.id.ll_dropLocation)
    LinearLayout ll_dropLocation;

    String country_id = "";

    @BindView(R.id.textView_pickUp)
    TextView textView_pickUp;

    @BindView(R.id.ll_pickUpLocation)
    LinearLayout ll_pickUpLocation;

    @BindView(R.id.edt_cus_number)
    EditText edt_cus_number;

    @BindView(R.id.edt_cus_name)
    EditText edt_cus_name;

    @BindView(R.id.est_card)
    CardView estCard;

    @BindView(R.id.est_price_tv)
    TextView estPriceTV;

    @BindView(R.id.est_time_tv)
    TextView estTimeTV;

    @BindView(R.id.phone_code)
    TextView phone_code;

    private ModelRideInfo modelRideInfo = null;
    private SamLocationRequestService samLocationRequestService;
    private static int MAX_PHONE_LENGTH = 10;
    private static int MIN_PHONE_LENGTH = 5;
    Button button_startRide;
    JSONArray jsArray;

    ModelManualRideStart modelManualRideStart;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    String manualDropLat = "", manualDropLng = "", manualPickLat = "", manualPickLng = "", manualPickLocation = "", manualDropLocation = "", bookingId = "";
    ApiManager apiManager;
    ProgressDialog pd;
    GsonBuilder builder;
    ModelManualEstimate modelManualEstimate;
    Gson gson;
    private HashMap<String, String> data = new HashMap<>();

    boolean otpManual = false;

    int openTrackScreen;

    int apiCheckDropArea;
    int apiCheckEstimate;
    int apiCheckOutManual;
    int apiManualBooking;

    @BindView(R.id.country_code1)
    CountryCodePicker country_code;

    String countryIso;
    int Selected_Country_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_user_detail);

        ButterKnife.bind(this);
        getSupportActionBar().hide();

        initialization();


        // phone_code.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         AlertDialog.Builder builderSingle = new AlertDialog.Builder(ManualUserDetailActivity.this);
        //         builderSingle.setTitle(R.string.select_country);
        //         final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ManualUserDetailActivity.this, android.R.layout.select_dialog_singlechoice);
        // for (int j = 0; j < sessionManager.getAppConfig().getData().getCountries().size(); j++) {
        //     arrayAdapter.add(sessionManager.getAppConfig().getData().getCountries().get(j).getPhonecode() + " " + sessionManager.getAppConfig().getData().getCountries().get(j).getName());
        //  }
        //  builderSingle.setNegativeButton(ManualUserDetailActivity.this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
        //         @Override
        //         public void onClick(DialogInterface dialog, int which) {
        //             dialog.dismiss();
        //         }
        //     });
        //     builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
        //         @Override
        //         public void onClick(DialogInterface dialog, int which) {
        //             setCountryCodeWithValidation(which);
        //             dialog.dismiss();
        //         }
        //     });
        //     builderSingle.show();
        // }
        // });
    }

    private void initialization() {

        samLocationRequestService = new SamLocationRequestService(this);

        locationSession = new LocationSession(this);
        sessionManager = new SessionManager(this);
        apiManager = new ApiManager(this, this);
        pd = new ProgressDialog(this);
        builder = new GsonBuilder();
        gson = builder.create();
        pd.setMessage(ManualUserDetailActivity.this.getResources().getString(R.string.loading));

        button_startRide = (Button) findViewById(R.id.button_startRide);

        initialClickListeners();
        setPickUpView();



        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        countryIso = telephonyManager.getSimCountryIso().toUpperCase();

        String country = "";
        String[] country_codes = new String[sessionManager.getAppConfig().getData().getCountries().size()];

        for(int i =0;i<sessionManager.getAppConfig().getData().getCountries().size();i++) {

            String code = sessionManager.getAppConfig().getData().getCountries().get(i).getCountry_code();
            country   = country + code+ ",";
            country_codes[i] = code;

            // setCountryCodeWithValidation(i);
        }

        country_code.setCustomMasterCountries(country);

        country_code.setCountryForNameCode( ""+countryIso );
        country_code.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                for(int i=0;i<country_codes.length;i++){
                    if(country_code.getSelectedCountryNameCode().equalsIgnoreCase(""+sessionManager.getAppConfig().getData().getCountries().get(i).getCountry_code())){
                        setCountryCodeWithValidation(i);
                        Selected_Country_position = i;
                    }

                }
            }
        });

        for (int i=0;i<sessionManager.getAppConfig().getData().getCountries().size();i++){
            if (countryIso.equalsIgnoreCase(sessionManager.getAppConfig().getData().getCountries().get(i).getCountry_code())){
                setCountryCodeWithValidation(i);
                Selected_Country_position = i;
            }
        }

       // setCountryCodeWithValidation(0);

    }

    private void setCountryCodeWithValidation(int selected_Country_position) {

        Thread thread = new Thread() {
            @Override
            public void run() {

                sessionManager.setCurrencyCode("" + sessionManager.getAppConfig().getData().getCountries().get(selected_Country_position).getIsoCode(), "" + sessionManager.getAppConfig().getData().getCountries().get(selected_Country_position).getIsoCode());
                MAX_PHONE_LENGTH = sessionManager.getAppConfig().getData().getCountries().get(selected_Country_position).getMaxNumPhone();
                MIN_PHONE_LENGTH = sessionManager.getAppConfig().getData().getCountries().get(selected_Country_position).getMinNumPhone();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        edt_cus_number.setFilters(new InputFilter[]{AppUtils.filter,
                                new InputFilter.LengthFilter(Integer.parseInt("" + MAX_PHONE_LENGTH))});

                        country_id = String.valueOf(sessionManager.getAppConfig().getData().getCountries().get(selected_Country_position).getId());
                       // phone_code.setText("" + sessionManager.getAppConfig().getData().getCountries().get(selected_Country_position).getPhonecode());
                        edt_cus_number.setText("");
                    }
                });
            }
        };

        thread.start();

    }

    private void setPickUpView() {

        // try {
        //     manualPickLat = locationSession.getLocationDetails().get(LocationSession.KEY_CURRENT_LAT);
        //     manualPickLng = locationSession.getLocationDetails().get(LocationSession.KEY_CURRENT_LONG);
        //     List<Address> addresses;
        //     Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        //     try {
        //         addresses = geocoder.getFromLocation(Double.parseDouble(manualPickLat), Double.parseDouble(manualPickLng), 1);
        // if (addresses != null && addresses.size() > 0) {
        //     // addresses.get(0).getCountryCode();
        //     String getAddress =  addresses.get(0).getAddressLine(0);
        //     textView_pickUp.setText(""+addresses.get(0).getAddressLine(0));
        //     manualPickLocation = ""+addresses.get(0).getAddressLine(0);
        // }
        // } catch (IOException e) {
        //     // Bloco de captura gerado automaticamente
        //     e.printStackTrace();
        // }
        ////  data.clear();
        ////  data.put("latitude", "" + manualPickLat);
        ////  data.put("longitude", "" + manualPickLng);
        ////  apiManager._post(API_S.Tags.REVERSE_GEOCODE, API_S.Endpoints.REVERSE_GEOCODE, data, sessionManager);
        // } catch (Exception e) {

        // }

        samLocationRequestService.executeService(location -> {


            manualPickLat = String.valueOf(location.getLatitude());
            manualPickLng = String.valueOf(location.getLongitude());

            List<Address> addresses;
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {


                addresses = geocoder.getFromLocation(Double.parseDouble(manualPickLat), Double.parseDouble(manualPickLng), 1);

                if (addresses != null && addresses.size() > 0) {

                    // addresses.get(0).getCountryCode();
                   String getAddress = addresses.get(0).getAddressLine(0);
                    textView_pickUp.setText("" + addresses.get(0).getAddressLine(0));
                    manualPickLocation = "" + addresses.get(0).getAddressLine(0);

                }
            } catch (IOException e) {
                // Bloco de captura gerado automaticamente
                e.printStackTrace();
            }

        });


}


    private void initialClickListeners() {

        ll_dropLocation.setOnClickListener(new setOnClickList());
        ll_back_rides.setOnClickListener(new setOnClickList());
        button_startRide.setOnClickListener(new setOnClickList());
    }

    //StartRideMethod
    private void startRideMethod() {

        try {
            if (apiManualBooking == 0) {
                apiManualBooking = 1;
                HashMap<String, String> data = new HashMap<>();
                data.put("checkout_id", bookingId);
                apiManager._post(API_S.Tags.MANUAL_BOOKING, API_S.Endpoints.MANUAL_BOOKING, data, sessionManager);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //// GooglePlaceApiDialog
    private void openGooglePlaceAPiDialoge() {

        Intent intent = new Intent(ManualUserDetailActivity.this, NewPlacePickerActivity.class)
                .putExtra("" + IntentKeys.LATITUDE, "" + manualPickLat)
                .putExtra("" + IntentKeys.LONGITUDE, "" + manualPickLng);
        startActivityForResult(intent, PLACE_PICKER_ACTIVITY);

        // try {
        //  Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(ManualUserDetailActivity.this);
        //   startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        // } catch (GooglePlayServicesRepairableException e) {
        //     // Trate o erro.
        // } catch (GooglePlayServicesNotAvailableException e) {
        //     // Trate o erro.
        // }
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == PLACE_PICKER_ACTIVITY) {

                if (!data.getExtras().getString(IntentKeys.ADDRESS_NAME).equals("")) {

                    textView_dropPoint.setText("" + data.getExtras().getString(IntentKeys.ADDRESS_NAME));

                    manualDropLat = String.valueOf(data.getExtras().getString(IntentKeys.LATITUDE));
                    manualDropLng = String.valueOf(data.getExtras().getString(IntentKeys.LONGITUDE));

                    manualDropLocation = data.getExtras().getString(IntentKeys.ADDRESS_NAME);

                    // execute api para obter estimativa de tarifa e hora

                    Thread thread = new Thread() {

                        @Override
                        public void run() {
                            jsArray = new JSONArray();

                            JSONObject jsonObject = new JSONObject();
                            try {

                                jsonObject.put("stop", "1");

                                jsonObject.put("drop_latitude", "" + manualDropLat);
                                jsonObject.put("drop_longitude", "" + manualDropLng);
                                jsonObject.put("drop_location", manualDropLocation);
                                jsonObject.put("status", "1");
                                jsArray.put(jsonObject);

                                HashMap<String, String> manualRequest = new HashMap<>();
                                manualRequest.put("pickup_longitude", manualPickLng);
                                manualRequest.put("pickup_latitude", manualPickLat);
                                manualRequest.put("service_type", "1");
                                manualRequest.put("drop_location", String.valueOf(jsArray));

                                runOnUiThread(() -> {
                                    try {
                                        if (apiCheckEstimate == 0) {
                                            apiCheckEstimate = 1;
                                            apiManager._post(API_S.Tags.CHECK_ESTIMATE_TAG, API_S.Endpoints.CHECK_ESTIMATE_URL, manualRequest, sessionManager);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    thread.start();


                } else if (resultCode == RESULT_CANCELED) {
                }
            }

            // if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            //     if (resultCode == RESULT_OK) {
            //         Place place = PlaceAutocomplete.getPlace(this, data);
            //         Log.d("*#*# getAddress", "" + place.getAddress());
            //         Log.d("*#*# getAttributions", "" + place.getAttributions());
            //         Log.d("*#*# getLocale", "" + place.getLocale());
            //         Log.d("*#*# getname", "" + place.getName());
            //         Log.d("*#*# getId", "" + place.getId());
            //         Log.d("*#*# geWebsiteURI", "" + place.getWebsiteUri());
            //         textView_dropPoint.setText("" + place.getName());
            //         manualDropLat = String.valueOf(place.getLatLng().latitude);
            //         manualDropLng = String.valueOf(place.getLatLng().longitude);
            //         manualDropLocation = place.getName().toString();
            //         // execute api para obter estimativa de tarifa e hora.
            //         Thread thread = new Thread() {
            //             @Override
            //             public void run() {
            //                 jsArray = new JSONArray();
            //                 JSONObject jsonObject = new JSONObject();
            //                 try {
            //                     jsonObject.put("stop", "1");
            //                     jsonObject.put("drop_latitude", "" + manualDropLat);
            //                     jsonObject.put("drop_longitude", "" + manualDropLng);
            //                     jsonObject.put("drop_location", manualDropLocation);
            //                     jsonObject.put("status", "1");
            //                     jsArray.put(jsonObject);
            //                     HashMap<String, String> manualRequest = new HashMap<>();
            //                     manualRequest.put("pickup_longitude", manualPickLng);
            //                     manualRequest.put("pickup_latitude", manualPickLat);
            //                     manualRequest.put("service_type", "1");
            //                     manualRequest.put("drop_location", String.valueOf(jsArray));
            //                     runOnUiThread(new Runnable() {
            //                         @Override
            //                         public void run() {
            //                             try {
            //                                 apiManager._post(API_S.Tags.CHECK_ESTIMATE_TAG, API_S.Endpoints.CHECK_ESTIMATE_URL, manualRequest, sessionManager);
            //                           } catch (Exception e) {
            //                               e.printStackTrace();
            //                           }
            //                       }
            //                   });
            //               } catch (JSONException e) {
            //                   e.printStackTrace();
            //               }
            //           }
            //       };
            //       thread.start();
            //   } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
            //       Status status = PlaceAutocomplete.getStatus(this, data);
            //       Log.i("*****", status.getStatusMessage());
            //   } else if (resultCode == RESULT_CANCELED) {
            //   }
            // }
            else if (requestCode == OTP_REQUEST_CODE && resultCode == RESULT_OK) {
                startRideMethod();
            }

        } catch (Exception e) {
            Log.e("Manual_Exception_Place_AutoComplete", "" + e);
        }
    }

    @Override
    public void onAPIRunningState(int a, String APINAME) {

        if (a == ApiManager.APIFETCHER.KEY_API_IS_STARTED) {
            pd.show();
        }
        if (a == ApiManager.APIFETCHER.KEY_API_IS_STOPPED) {
            pd.dismiss();
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onFetchComplete(Object script, String APINAME) {


        try {
            switch (APINAME) {
                case API_S.Tags.CHECK_DROP_AREA:

                    callBookingApi();

                    break;

                case API_S.Tags.REVERSE_GEOCODE:
                    GetAddressResponse getAddressResponse = gson.fromJson("" + script, GetAddressResponse.class);
                    if (getAddressResponse.getResult().equals("1")) {
                        textView_pickUp.setText("" + getAddressResponse.getData().getAddress().toString());
                        manualPickLocation = getAddressResponse.getData().getAddress().toString();
                    } else {

                    }
                    break;

                case API_S.Tags.MANUAL_BOOKING:
                    modelRideInfo = SingletonGson.getInstance().fromJson("" + script, ModelRideInfo.class); // getting same response as details api and refresh screen

                    if (modelRideInfo.getResult().equals("1")) {
                        try {
                            sessionManager.setShow_toll_dialog(modelRideInfo.getData().isManual_toll_enable());
                        } catch (Exception e) {

                        }

                        if (sessionManager.getAppConfig().getData().getGeneral_config().getLat_long_storing_at() == 2) {

                            ATS.startTrip("" +modelRideInfo.getData().getId()+""+getPackageName(), new AtsOnTripSetListener() {
                                @Override
                                public void onSuccess(String message) {
                                    try {
                                        // sessionManager.setStartTag(1);
                                        // // callRideStartApi(booking_id);
                                        // // startRideMethod();
                                        // sessionManager.setEndTag(0);

                                        startApi();
                                    } catch (Exception e) {
                                        // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailed(String message) {
                                    // Snackbar.make(root, "ATS SERVER ERROR: " + message, Snackbar.LENGTH_INDEFINITE).show();
                                    infodialog();

                                }
                            });

                        } else {
                            try {
                                // sessionManager.setStartTag(1);
                                // // callRideStartApi(booking_id);
                                // // startRideMethod();
                                // sessionManager.setEndTag(0);

                                startApi();
                            } catch (Exception e) {
                                // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            }
                        }

                        // startApi();

                        //                        try {
                        ////                                    callRideStartApi(booking_id);
                        //                            //  startRideMethod();
                        //                            if (openTrackScreen == 0) {
                        //                                openTrackScreen = 1;
                        //                                startActivity(new Intent(ManualUserDetailActivity.this, TrackerActivity.class)
                        //                                        .putExtra("" + IntentKeys.BOOKING_ID, "" + modelRideInfo.getData().getId()));
                        //
                        //                                ManualUserDetailActivity.this.finish();
                        //                            }
                        //                        } catch (Exception e) {
                        //                            // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        //                        }

                        //                        if (sessionManager.getAppConfig().getData().getGeneral_config().getLat_long_storing_at() == 2) {
                        //
                        //                            ATS.startTrip("" +modelRideInfo.getData().getId()+""+getPackageName(), new AtsOnTripSetListener() {
                        //                                @Override
                        //                                public void onSuccess(String message) {
                        //                                    try {
                        //                                        sessionManager.setStartTag(1);
                        //                                        // callRideStartApi(booking_id);
                        //                                        // startRideMethod();
                        //
                        //                                        if (openTrackScreen == 0) {
                        //                                            openTrackScreen = 1;
                        //                                            startActivity(new Intent(ManualUserDetailActivity.this, TrackerActivity.class)
                        //                                                    .putExtra("" + IntentKeys.BOOKING_ID, "" + modelRideInfo.getData().getId()));
                        //
                        //                                            ManualUserDetailActivity.this.finish();
                        //                                        }
                        //                                    } catch (Exception e) {
                        //                                        // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        //                                    }
                        //                                }
                        //
                        //                                @Override
                        //                                public void onFailed(String message) {
                        //                                    // Snackbar.make(root, "ATS SERVER ERROR: " + message, Snackbar.LENGTH_INDEFINITE).show();
                        //                                }
                        //                            });
                        //
                        //                        } else {
                        //
                        //                        }

                    }
                    break;

                case API_S.Tags.MANUAL_START_RIDE:

                    modelManualRideStart = SingletonGson.getInstance().fromJson("" + script, ModelManualRideStart.class);
                    try {
                        sessionManager.setStartTag(1);
                        // callRideStartApi(booking_id);
                        // startRideMethod();
                        sessionManager.setEndTag(0);

                        if (openTrackScreen == 0) {
                            openTrackScreen = 1;
                            startActivity(new Intent(ManualUserDetailActivity.this, TrackerActivity.class)
                                    .putExtra("" + IntentKeys.BOOKING_ID, "" + modelRideInfo.getData().getId()));

                            ManualUserDetailActivity.this.finish();
                        }
                    } catch (Exception e) {
                        // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }



                    break;

                case API_S.Tags.CHECK_ESTIMATE_TAG:
                    modelManualEstimate = SingletonGson.getInstance().fromJson("" + script, ModelManualEstimate.class);

                    if (modelManualEstimate.getResult().equals("1")) {
                        estCard.setVisibility(View.VISIBLE);
                        estPriceTV.setText("" + getResources().getString(R.string.estimate_price) + " " + modelManualEstimate.getData().getAmount());
                        estTimeTV.setText("" + getResources().getString(R.string.estimate_time) + " " + modelManualEstimate.getData().getTime());

                        otpManual = modelManualEstimate.isOtp_manual_dispatch();

                    } else {
                        estCard.setVisibility(View.GONE);
                    }

                    break;

                case API_S.Tags.MANUAL_DETAILS_CHECKOUT:

                    ModelManualCheckOut modelManualCheckOut = SingletonGson.getInstance().fromJson("" + script, ModelManualCheckOut.class);


                    if (modelManualCheckOut.getResult().equals("1")) {

                        bookingId = "" + modelManualCheckOut.getData().getId();
                        if (otpManual) {
                            Intent intent = new Intent(ManualUserDetailActivity.this, ManualRide_Otp_activity.class).putExtra("ride_otp", "" + modelManualCheckOut.getOtp());
                            startActivityForResult(intent, OTP_REQUEST_CODE);
                        } else {
                            startRideMethod();
                        }
                    }
                    break;

            }

        } catch (Exception e) {
            Log.e("" + TAG, "" + e.getMessage());

        }

    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {
        Toast.makeText(ManualUserDetailActivity.this, "" + script, Toast.LENGTH_SHORT).show();
    }

    public class setOnClickList implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int i = view.getId();
            if (i == button_startRide.getId()) {
                if (textView_pickUp.getText().toString().equals("")) {
                    Toast.makeText(ManualUserDetailActivity.this, getResources().getString(R.string.Manual_Activity_select_pick_location), Toast.LENGTH_SHORT).show();
                    ManualUserDetailActivity.this.finish();
                } else if (manualDropLat.equals("") && manualDropLng.equals("")) {
                    Toast.makeText(ManualUserDetailActivity.this, getResources().getString(R.string.Manual_Activity_select_drop_location), Toast.LENGTH_SHORT).show();
                } else if (!isValidPhoneNumber(edt_cus_number.getText().toString())) {
                    Toast.makeText(ManualUserDetailActivity.this, getResources().getString(R.string.valid_phone_number), Toast.LENGTH_SHORT).show();
                } else if (edt_cus_name.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ManualUserDetailActivity.this, getResources().getString(R.string.name_can_not_be_empty), Toast.LENGTH_SHORT).show();
                } else {

                    checkDropLocation();
                }

            } else if (i == ll_dropLocation.getId()) {
                openGooglePlaceAPiDialoge();

            } else if (i == ll_back_rides.getId()) {

                ManualUserDetailActivity.this.finish();

            }
        }
    }

    private void startApi() {

        try {

            HashMap<String, String> data = new HashMap<>();
            data.put("booking_id", "" + modelRideInfo.getData().getId());
            apiManager._post(API_S.Tags.MANUAL_START_RIDE, API_S.Endpoints.MANUAL_START_RIDE, data, sessionManager);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void infodialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(ManualUserDetailActivity.this);
        builder.setCancelable(false);
        builder.setMessage("Their is some problem with network please try again")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ATS.startTrip("" +modelRideInfo.getData().getId()+""+getPackageName(), new AtsOnTripSetListener() {
                            @Override
                            public void onSuccess(String message) {
                                try {
                                    startApi();
                                } catch (Exception e) {
                                    // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailed(String message) {
                                // Snackbar.make(root, "ATS SERVER ERROR: " + message, Snackbar.LENGTH_INDEFINITE).show();
                                infodialog();

                            }
                        });
                    }
                });

        builder.create().show();
        // is_gps_dialog_shown = true;
    }

    private void checkDropLocation() {
        samLocationRequestService.executeService(location -> {
            try {
                if (apiCheckDropArea == 0) {
                    apiCheckDropArea = 1;
                    HashMap<String, String> data = new HashMap<>();
                    data.put("latitude", manualDropLat);
                    data.put("longitude", manualDropLng);
                    data.put("service_type", "1");
                    data.put("area_id", sessionManager.getUserDetails().get(SessionManager.KEY_AREA_ID));
                    apiManager._post(API_S.Tags.CHECK_DROP_AREA, API_S.Endpoints.CHECK_DROP_AREA, data, sessionManager);
                }

            } catch (Exception e) {
            }
        });
    }

    public final static boolean isValidPhoneNumber(CharSequence target) {
        if (target == null || target.length() < MIN_PHONE_LENGTH || target.length() > MAX_PHONE_LENGTH) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }

    }


    private void callBookingApi() {
        samLocationRequestService.executeService(location -> {
            try {
                Thread thread = new Thread() {

                    @Override
                    public void run() {

                        jsArray = new JSONArray();

                        if (manualDropLat != null) {

                            try {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("stop", "1");
                                jsonObject.put("drop_latitude", "" + manualDropLat);
                                jsonObject.put("drop_longitude", "" + manualDropLng);
                                jsonObject.put("drop_location", manualDropLocation);
                                jsonObject.put("status", "1");
                                jsArray.put(jsonObject);
                            } catch (Exception e) {

                            }
                        }
                        if (apiCheckOutManual == 0) {
                            apiCheckOutManual = 1;
                            HashMap<String, String> data = new HashMap<>();
                            data.put("pickup_latitude", manualPickLat);
                            data.put("estimate_time", modelManualEstimate.getData().getTime());
                            data.put("estimate_bill", modelManualEstimate.getData().getAmount());
                            data.put("estimate_distance", modelManualEstimate.getData().getDistance());
                            data.put("pickup_longitude", manualPickLng);
                            data.put("country_id", "" + country_id);
                            data.put("pickup_location", manualPickLocation);
                            data.put("drop_location", String.valueOf(jsArray));
                            data.put("accuracy", String.valueOf(location.getAccuracy()));
                            data.put("phone",sessionManager.getAppConfig().getData().getCountries().get(Selected_Country_position).getPhonecode() + edt_cus_number.getText().toString());


                            if (edt_cus_name.getText().toString().contains(" ")) {
                                String currentString = edt_cus_name.getText().toString();
                                String[] separated = currentString.split(" ");


                                data.put("first_name", separated[0]);

                                if (separated[1] == null || separated[1].equals("")) {
                                    data.put("last_name", ".");
                                } else {
                                    data.put("last_name", separated[1]);

                                }
                            } else {
                                data.put("first_name", edt_cus_name.getText().toString());
                                data.put("last_name", ".");
                            }


                            runOnUiThread(() -> {
                                try {
                                    apiManager._post(API_S.Tags.MANUAL_DETAILS_CHECKOUT, API_S.Endpoints.checkoutBooking_url, data, sessionManager);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        }

                    }
                };

                thread.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
