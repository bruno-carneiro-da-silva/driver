package com.tkx.driver;

import android.Manifest;
import android.app.Activity;

// import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tkx.driver.activities.vehicleModule.SampleVehicleActivity;
import com.tkx.driver.baseClass.BaseActivity;
import com.tkx.driver.currentwork.API_S;
import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.location.UpdateServiceClass;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.models.ModelDriverDetails;
import com.tkx.driver.models.ModelLogin;
import com.tkx.driver.models.ModelOTPVerifier;
import com.tkx.driver.others.AppUtils;
import com.tkx.driver.samwork.ApiManager;
import com.apporioinfolabs.ats_sdk.ATS;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;

import java.util.Arrays;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements ApiManager.APIFETCHER {

    private final static int RESOLVE_HINT = 1011;
    String mobNumber;

    private static final int FIREBASE_OTP_REQUEST_CODE = 1000;
    public static Activity loginactivity1;
    ProgressDialog pd;
    SessionManager sessionManager;
    ApiManager apiManager;
    private String TAG = "LoginActivity";
    private ModelLogin modelLogin;
    OSPermissionSubscriptionState status;
    private int MAX_PHONE_LENGTH = 10;

    @BindView(R.id.root) LinearLayout root;
    @BindView(R.id.ll_back_login) LinearLayout ll_back_login;
    @BindView(R.id.phone_code) TextView phone_code;
    @BindView(R.id.edt_phone_login) EditText edt_phone_login;
    @BindView(R.id.phone_layout) LinearLayout phone_layout;
    @BindView(R.id.edt_pass_login) EditText edt_pass_login;
    @BindView(R.id.edt_os) EditText edt_os;
    @BindView(R.id.tv_forgot) TextView tv_forgot;
    @BindView(R.id.ll_login) CardView ll_login;

    private String phoneNumber = "";
    
    AlertDialog.Builder builderSingle1;

    @BindView(R.id.country_code1) CountryCodePicker country_code;

    int Selected_Country_position;

    String countryIso;

    String vehicleId="";

    String number_os = "";

    String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        sessionManager = new SessionManager(this);
        apiManager = new ApiManager(this, this);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        loginactivity1 = this;

        Intent it = getIntent();
        String collection_order = it.getStringExtra("collection_order");
        Log.i("Ordem de coleta: ", collection_order);

        if (collection_order.equals("1")){
            edt_os.setVisibility(View.VISIBLE);
            edt_os.setHint("" + getString(R.string.order_of_service));
        }
        
        pd = new ProgressDialog(this);
        pd.setMessage("" + this.getResources().getString(R.string.loading));

        OneSignal.startInit(LoginActivity.this)
            .autoPromptLocation(true)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .init();

        stopService(new Intent(this,MyService.class));

        try{
            status = OneSignal.getPermissionSubscriptionState();
            if (status == null) {
                showPlayerIdNullDialog();
            }
        }catch (Exception e){ }

        // try{
        //     if(sessionManager.getAppConfig().getData().getRegister().isPhone()){
        //         getPhone();
        //     }
        // }catch (Exception e){
        // }

        getPhone();

        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        countryIso = telephonyManager.getSimCountryIso().toUpperCase();

        String country = "";
        String[] country_codes = new String[sessionManager.getAppConfig().getData().getCountries().size()];

        for(int i = 0; i < sessionManager.getAppConfig().getData().getCountries().size(); i++) {
            String code = sessionManager.getAppConfig().getData().getCountries().get(i).getCountry_code();
            country   = country + code + ",";
            country_codes[i] = code;
            setCountryCodeWithValidation(i);
        }

        country_code.setCustomMasterCountries(country);

        country_code.setCountryForNameCode( "" + countryIso );
        country_code.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                for(int i=0; i <country_codes.length; i++){
                    if(country_code.getSelectedCountryNameCode().equalsIgnoreCase(""+sessionManager.getAppConfig().getData().getCountries().get(i).getCountry_code())){
                        setCountryCodeWithValidation(i);
                        Selected_Country_position = i;
                    }
                }
            }
        });

        for (int i = 0; i<sessionManager.getAppConfig().getData().getCountries().size(); i++){
            if (countryIso.equalsIgnoreCase(sessionManager.getAppConfig().getData().getCountries().get(i).getCountry_code())){
                setCountryCodeWithValidation(i);
                Selected_Country_position = i;
            }
        }

        // setCountryCodeWithValidation(0);
        // btnDemo.setVisibility(sessionManager.getAppConfig().getData().get.equals("1") ? View.VISIBLE : View.GONE);

        ll_back_login.setOnClickListener(v -> finish());

        phone_code.setOnClickListener(view -> {
            builderSingle1.show();
        });

        ll_login.setOnClickListener(v -> {
            if (edt_phone_login.getText().toString().equals("")) {
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.phone_can_not_be_empty), Toast.LENGTH_LONG).show();
            } else if (edt_pass_login.getText().toString().equals("")) {
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.please_enter_password), Toast.LENGTH_LONG).show();
            } else {
                // Toast.makeText(loginactivity1, "" + ATS.getAtsid(), Toast.LENGTH_LONG).show();
                Toast.makeText(loginactivity1, "" + edt_phone_login.getText().toString() , Toast.LENGTH_LONG).show();
                HashMap<String, String> data = new HashMap<>();

                data.put("ats_id", ATS.getAtsid().equals("NA") ? "":"" + ATS.getAtsid());

                if (sessionManager.getAppConfig().getData().getLogin().isEmail()) {
                    data.put("phone", "" + edt_phone_login.getText().toString());
                } else {
                    data.put("phone", "" + sessionManager.getAppConfig().getData().getCountries().get(Selected_Country_position).getPhonecode() + edt_phone_login.getText().toString());
                }
                data.put("password", "" + edt_pass_login.getText().toString());
                data.put("player_id", "" + status.getSubscriptionStatus().getUserId());

                if (edt_os.getText().toString().isEmpty() && collection_order.equals("1")) {
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.please_enter_order_number), Toast.LENGTH_LONG).show();
                } else {
                    data.put("number_os", "" + edt_os.getText().toString());
                    try {
                        apiManager._post_with_secreteonly(API_S.Tags.LOGIN, API_S.Endpoints.LOGIN, data);
                    } catch (Exception e) {
                        Log.d(TAG, "Exceção capturada ao chamar o método de login " + e.getMessage());
                    }
                }
            }
        });

        tv_forgot.setOnClickListener(v -> {

            if (sessionManager.getAppConfig().getData().getLogin().isEmail()) {
                startActivity(new Intent(LoginActivity.this, OTPVerifierActivity.class)
                    .putExtra("" + IntentKeys.VERIFIER_MODE, "EMAIL")
                    .putExtra("" + IntentKeys.VERIFIER_TYPE, "" + OTPVerifierActivity.FORGOT_VERIFIER));
            } else {
                if (sessionManager.getAppConfig().getData().getGeneral_config().isOtp_from_firebase()) {
                    startActivity(new Intent(LoginActivity.this, OTPVerifierActivity.class)
                        .putExtra("" + IntentKeys.VERIFIER_MODE, "FIREBASE")
                        .putExtra("" + IntentKeys.VERIFIER_TYPE, "" + OTPVerifierActivity.FORGOT_VERIFIER));
                } else {
                    startActivity(new Intent(LoginActivity.this, OTPVerifierActivity.class)
                        .putExtra("" + IntentKeys.VERIFIER_MODE, "PHONE")
                        .putExtra("" + IntentKeys.VERIFIER_TYPE, "" + OTPVerifierActivity.FORGOT_VERIFIER));
                }
            }
        });

        if (!AppUtils.hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        } else {

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(this, UpdateServiceClass.class));
        } else { // normal
            startService(new Intent(this, UpdateServiceClass.class));
        }

    }

    // carregar dados do código do país na caixa de diálogo
    @Override
    protected void onResume() {
        super.onResume();

        builderSingle1 = new AlertDialog.Builder(LoginActivity.this);
        builderSingle1.setTitle(R.string.select_country);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.select_dialog_singlechoice);
        for (int i = 0; i < sessionManager.getAppConfig().getData().getCountries().size(); i++) {
            arrayAdapter.add(sessionManager.getAppConfig().getData().getCountries().get(i).getPhonecode() + " " + sessionManager.getAppConfig().getData().getCountries().get(i).getName());
        }
        builderSingle1.setNegativeButton(LoginActivity.this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builderSingle1.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setCountryCodeWithValidation(which);
                dialog.dismiss();
            }
        });
    }
    private void getPhone() {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
            .addApi(Auth.CREDENTIALS_API)
            .build();
        googleApiClient.connect();
        HintRequest hintRequest = new HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true)
            .build();
        // Comentado --> causando restart vm android api 31 android 12 e 13
        // PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(googleApiClient, hintRequest);
        // try {
        //     startIntentSenderForResult(intent.getIntentSender(), RESOLVE_HINT, null, 0, 0, 0);
        // } catch (IntentSender.SendIntentException e) {
        //     e.printStackTrace();
        // }
    }

    private void setCountryCodeWithValidation(int selected_Country_position) {

        sessionManager.setCurrencyCode("" + sessionManager.getAppConfig().getData().getCountries().get(selected_Country_position).getIsoCode(), "" + sessionManager.getAppConfig().getData().getCountries().get(selected_Country_position).getIsoCode());
        // phone_code.setText("" + sessionManager.getAppConfig().getData().getCountries().get(selected_Country_position).getPhonecode());
        MAX_PHONE_LENGTH = Integer.parseInt("" + sessionManager.getAppConfig().getData().getCountries().get(selected_Country_position).getMaxNumPhone());
        edt_phone_login.setText("");
        // edt_phone_login.setFilters(new InputFilter[]{AppUtils.filter, new InputFilter.LengthFilter(MAX_PHONE_LENGTH)});
        sessionManager.setDistanceUnit(sessionManager.getAppConfig().getData().getCountries().get(selected_Country_position).getDistance_unit());
        setLoginMethodViaConfig();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (AppUtils.hasPermissions(LoginActivity.this, PERMISSIONS)) {

                } else {
                    Log.i("" + TAG, "Algumas permissões estão faltando");
                }
                return;
            }
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

    @Override
    public void onFetchComplete(Object script, String APINAME) {
        try {
            switch (APINAME) {
                case API_S.Tags.LOGIN:
                    modelLogin = SingletonGson.getInstance().fromJson("" + script, ModelLogin.class);
                    sessionManager.setAccessToken("" + modelLogin.getData().getAccess_token());
                    try {
                        sessionManager.setTaxiCompany(modelLogin.getData().isTaxi_company());
                        // sessionManager.setFirebaseNotification(modelLogin.getData().getPush_notification().isFire_base());
                    }catch (Exception e){

                    }

                    apiManager._post(API_S.Tags.DRIVER_DETAILS, API_S.Endpoints.DRIVER_DETAILS, null, sessionManager);
                    break;

                case API_S.Tags.DRIVER_DETAILS:
                    ModelDriverDetails modelDriverDetails = SingletonGson.getInstance().fromJson("" + script, ModelDriverDetails.class);

                    sessionManager.setcountryid(Integer.parseInt(modelDriverDetails.getData().getCountry_id()));
                    sessionManager.setDemoOrNot("2");
                    try{
                        if(modelDriverDetails.getData().getDriverVehicle().size()>0){
                            vehicleId = String.valueOf(modelDriverDetails.getData().getDriverVehicle().get(0).getId());
                            sessionManager.setVehicleId(vehicleId);
                        }else{
                            vehicleId = "";
                        }
                    }catch(Exception e){

                    }

                    if (modelDriverDetails.getData().getSignupStep().equals("1")) {
                        // o registro básico é feito, abra a tela de adicionar carro
                        sessionManager.createLoginSession(false, "" + modelDriverDetails.getData().getId()
                            ,""+vehicleId
                            , modelDriverDetails.getData().getFirst_name()
                            , modelDriverDetails.getData().getLast_name()
                            , modelDriverDetails.getData().getPhoneNumber()
                            , modelDriverDetails.getData().getPhone_code()
                            , modelDriverDetails.getData().getEmail()
                            , String.valueOf(modelDriverDetails.getData().getDriver_gender())
                            , String.valueOf(modelDriverDetails.getData().getSmoker_type())
                            , String.valueOf(modelDriverDetails.getData().getAllow_other_smoker())
                            , modelDriverDetails.getData().getProfile_image()
                            , String.valueOf(modelDriverDetails.getData().getCountry_area_id())
                            , String.valueOf(modelDriverDetails.getData().getCreated_at())
                            , String.valueOf(modelDriverDetails.getData().getLogin_logout())
                            , String.valueOf(modelDriverDetails.getData().getFree_busy())
                            , String.valueOf(modelDriverDetails.getData().getOnline_offline())
                            , modelDriverDetails.getData().getSignupStep()
                            , modelDriverDetails.getData().getAuto_accept_enable()
                            , modelDriverDetails.getData().getSubscription_wise_commission()
                            , ""
                            ,modelDriverDetails.getData().getNumber()
                            ,modelDriverDetails.getData().getStreet()
                            ,modelDriverDetails.getData().getCity()
                            ,modelDriverDetails.getData().getState_code()
                            ,modelDriverDetails.getData().getPostCode_cep());
                        startActivity(new Intent(LoginActivity.this, SampleVehicleActivity.class)
                                .putExtra("documentScreenApi", "0")
                                .putExtra("" + IntentKeys.DRIVER_ID, "" + modelDriverDetails.getData().getId())
                                .putExtra("" + IntentKeys.AREA_ID, "" + modelDriverDetails.getData().getCountry_area_id()));
                        SplashActivity.splash.finish();
                    }
                    if (modelDriverDetails.getData().getSignupStep().equals("2")) {
                        // documentos não carregados ou todos verificados
                        sessionManager.createLoginSession(false, "" + modelDriverDetails.getData().getId()
                            ,""+vehicleId
                            , modelDriverDetails.getData().getFirst_name()
                            , modelDriverDetails.getData().getLast_name()
                            , modelDriverDetails.getData().getPhoneNumber()
                            , modelDriverDetails.getData().getPhone_code()
                            , modelDriverDetails.getData().getEmail()
                            , String.valueOf(modelDriverDetails.getData().getDriver_gender())
                            , String.valueOf(modelDriverDetails.getData().getSmoker_type())
                            , String.valueOf(modelDriverDetails.getData().getAllow_other_smoker())
                            , modelDriverDetails.getData().getProfile_image()
                            , String.valueOf(modelDriverDetails.getData().getCountry_area_id())
                            , String.valueOf(modelDriverDetails.getData().getCreated_at())
                            , String.valueOf(modelDriverDetails.getData().getLogin_logout())
                            , String.valueOf(modelDriverDetails.getData().getFree_busy())
                            , String.valueOf(modelDriverDetails.getData().getOnline_offline())
                            , modelDriverDetails.getData().getSignupStep()
                            , modelDriverDetails.getData().getAuto_accept_enable()
                            , ""
                            , modelDriverDetails.getData().getSubscription_wise_commission()
                            ,modelDriverDetails.getData().getNumber()
                            ,modelDriverDetails.getData().getStreet()
                            ,modelDriverDetails.getData().getCity()
                            ,modelDriverDetails.getData().getState_code()
                            ,modelDriverDetails.getData().getPostCode_cep());
                        if (modelDriverDetails.getData().getDriverVehicle().size() > 0) {
                            startActivity(new Intent(LoginActivity.this, DocumentActivity.class)
                                .putExtra("documentScreenApi", "0")
                                .putExtra("" + IntentKeys.DRIVER_ID, "" + modelDriverDetails.getData().getId())
                                .putExtra("" + IntentKeys.DRIVER_VEHICLE_ID, "" + modelDriverDetails.getData().getDriverVehicle().get(0).getId()));
                            SplashActivity.splash.finish();
                            finish();
                        } else {
                            startActivity(new Intent(LoginActivity.this, DocumentActivity.class)
                                .putExtra("documentScreenApi", "0")
                                .putExtra("" + IntentKeys.DRIVER_ID, "" + modelDriverDetails.getData().getId())
                                .putExtra("" + IntentKeys.DRIVER_VEHICLE_ID, "" + "0"));
                            SplashActivity.splash.finish();
                            finish();
                        }
                    }
                    if (modelDriverDetails.getData().getSignupStep().equals("3")) { // all things are set and ready to go
                        sessionManager.createLoginSession(true, "" + modelDriverDetails.getData().getId()
                            ,""+vehicleId
                            , modelDriverDetails.getData().getFirst_name()
                            , modelDriverDetails.getData().getLast_name()
                            , modelDriverDetails.getData().getPhoneNumber()
                            , modelDriverDetails.getData().getPhone_code()
                            , modelDriverDetails.getData().getEmail()
                            , String.valueOf(modelDriverDetails.getData().getDriver_gender())
                            , String.valueOf(modelDriverDetails.getData().getSmoker_type())
                            , String.valueOf(modelDriverDetails.getData().getAllow_other_smoker())
                            , modelDriverDetails.getData().getProfile_image()
                            , String.valueOf(modelDriverDetails.getData().getCountry_area_id())
                            , String.valueOf(modelDriverDetails.getData().getCreated_at())
                            , String.valueOf(modelDriverDetails.getData().getLogin_logout())
                            , String.valueOf(modelDriverDetails.getData().getFree_busy())
                            , String.valueOf(modelDriverDetails.getData().getOnline_offline())
                            , modelDriverDetails.getData().getSignupStep()
                            , modelDriverDetails.getData().getAuto_accept_enable()
                            , modelDriverDetails.getData().getSubscription_wise_commission(),
                            modelDriverDetails.getData().getPhoneNumber()
                            ,modelDriverDetails.getData().getNumber()
                            ,modelDriverDetails.getData().getStreet()
                            ,modelDriverDetails.getData().getCity()
                            ,modelDriverDetails.getData().getState_code()
                            ,modelDriverDetails.getData().getPostCode_cep());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        SplashActivity.splash.finish();
                        finish();
                    }
                    break;

                case API_S.Tags.OTP:
                    ModelOTPVerifier modelOTPVerifier = SingletonGson.getInstance().fromJson("" + script, ModelOTPVerifier.class);
                    if (modelOTPVerifier.getResult().equalsIgnoreCase("1")) {
                        startActivity(new Intent(LoginActivity.this, UpdatePasswordActivity.class)
                        .putExtra("" + IntentKeys.VERIFIER_MODE, "PHONE")
                        .putExtra("" + IntentKeys.PHONE, phoneNumber));
                    }
                    break;
            }

        } catch (Exception e) {
            Log.d("" + TAG, "Exceção capturada durante a análise no login " + e.getMessage());
        }
    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {
        Toast.makeText(LoginActivity.this, "" + script, Toast.LENGTH_LONG).show();
    }

    private void showPlayerIdNullDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setCancelable(false);
        builder.setMessage(R.string.it_seems_you_are_out_of_internet_connection)
            .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    startActivity(new Intent(LoginActivity.this, SplashActivity.class));
                    finish();
                }
            });
        builder.create().show();
    }

    protected void setLoginMethodViaConfig() {

        if (sessionManager.getAppConfig().getData().getLogin().isEmail()) {
            // phone_code.setVisibility(View.INVISIBLE);
            country_code.setVisibility(View.INVISIBLE);
            edt_phone_login.setHint(getResources().getString(R.string.please_enter_email));
            edt_phone_login.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        } else {
            // phone_code.setVisibility(View.VISIBLE);

            phone_code.setVisibility(View.VISIBLE);
            
            country_code.setVisibility(View.VISIBLE);
            edt_phone_login.setHint(getResources().getString(R.string.LOGIn_ACTIVITY__phone));
            edt_phone_login.setInputType(InputType.TYPE_CLASS_NUMBER);
            edt_phone_login.setFilters(new InputFilter[]{AppUtils.filter, new InputFilter.LengthFilter(MAX_PHONE_LENGTH)});
        }
    }

    private void callFirebaseOtpMethod() {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(
            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build())).build(), FIREBASE_OTP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==  RESOLVE_HINT){
            if (resultCode == RESULT_OK) {
                com.google.android.gms.auth.api.credentials.Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                if (credential != null) {
                    mobNumber = credential.getId();
                    String newString = mobNumber.replace("+55", "");
                    // Toast.makeText(this, "" + newString, Toast.LENGTH_LONG).show();
                    edt_phone_login.setText(newString);
                } else {
                    Toast.makeText(this, "err", Toast.LENGTH_LONG).show();
                }
            }
        }

        if (requestCode == FIREBASE_OTP_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == Activity.RESULT_OK) {
                if (!FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().isEmpty()) {
                    phoneNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                    HashMap<String, String> data1 = new HashMap<>();
                    data1.put("type", "" + OTPVerifierActivity.FORGOT_VERIFIER);
                    data1.put("for", "PHONE");
                    data1.put("user_name", phoneNumber);
                    try {
                        apiManager._post_with_secreteonly("" + API_S.Tags.OTP, "" + API_S.Endpoints.OTP, data1);
                    } catch (Exception e) {
                        Log.e("" + TAG, "Exceção capturada ao chamar a API " + e.getMessage());
                    }
                    return;
                } else {
                    if (response == null) {
                        Toast.makeText(this, getResources().getString(R.string.signinfailed_string), Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                        Toast.makeText(this, getResources().getString(R.string.nonetwork_string), Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                        Toast.makeText(this, getResources().getString(R.string.unkown_error_string), Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, UpdateServiceClass.class));
    }
}