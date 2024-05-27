package com.tkx.driver.activities.demo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.EditText;
import android.widget.Toast;

import com.tkx.driver.AddVehicleActivity;
import com.tkx.driver.DocumentActivity;
import com.tkx.driver.MainActivity;
import com.tkx.driver.R;
import com.tkx.driver.SingletonGson;
import com.tkx.driver.SplashActivity;
import com.tkx.driver.currentwork.API_S;
import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.models.ModelDriverDetails;
import com.tkx.driver.models.ModelLogin;
import com.tkx.driver.samwork.ApiManager;
import com.onesignal.OneSignal;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DemoActivity extends AppCompatActivity implements ApiManager.APIFETCHER {

    @BindView(R.id.demo_name)
    EditText demo_namel;

    @BindView(R.id.demo_email)
    EditText demo_email;

    @BindView(R.id.demo_phone)
    EditText demo_phone;

    ApiManager apiManager;
    SessionManager sessionManager;
    ProgressDialog progressDialog;
    String PLAYER_ID = "";
    private ModelLogin modelLogin;
    String vehicleId ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);

        initialoization();
    }

    private void initialoization() {
        try{
            OneSignal.idsAvailable((userId, registrationId) -> PLAYER_ID = userId);
        }catch (Exception e){

        }
        apiManager = new ApiManager(this, this);
        sessionManager = new SessionManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
    }

    @OnClick(R.id.ll_back_signup)
    public void onClickBack() {
        DemoActivity.this.finish();
    }

    @OnClick(R.id.demo_ok_btn)
    public void onClickDemoButton() {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+.[a-z]+";

        if (demo_namel.getText().toString().equals("")) {

            Toast.makeText(this, getResources().getString(R.string.please_enter_name), Toast.LENGTH_SHORT).show();
        } else if (demo_email.getText().toString().equals("")) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_email), Toast.LENGTH_SHORT).show();
        } else if (!demo_email.getText().toString().trim().matches(emailPattern)) {
            Toast.makeText(this, getResources().getString(R.string.correct_email), Toast.LENGTH_SHORT).show();
        } else if (demo_phone.getText().toString().equals("")) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_phone), Toast.LENGTH_SHORT).show();
        } else {
            showInstructionsDialog();
        }
    }

    @OnClick(R.id.skip)
    public void onCLickSkipButton() {
        showInstructionsDialog();
    }

    private void showInstructionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DemoActivity.this);
        builder.setCancelable(false);
        builder.setTitle(getResources().getString(R.string.for_demo_testing));
        builder.setMessage(getResources().getString(R.string.make_sure))
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                    callApiForDemo();
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                    // User cancelled the dialog
                    dialog.cancel();
                });

        builder.create().show();
    }

    private void callApiForDemo() {
        final HashMap<String, String> data = new HashMap<String, String>();
        data.put("unique_number", "" + Settings.Secure.getString(DemoActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID));
        data.put("player_id", "" + PLAYER_ID);

        data.put("name", "" + demo_namel.getText().toString().trim());
        data.put("email", "" + demo_email.getText().toString().trim());
        data.put("phone", "" + demo_phone.getText().toString().trim());
        try {
            apiManager._post_with_secreteonly(API_S.Tags.DEMO_LOGIN, API_S.Endpoints.DEMO_LOGIN, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAPIRunningState(int a, String APINAME) {
        if (a == ApiManager.APIFETCHER.KEY_API_IS_STARTED) {
            progressDialog.show();
        } else {
            progressDialog.cancel();
        }
    }

    @Override
    public void onFetchComplete(Object script, String APINAME) {
        switch (APINAME) {
            case API_S.Tags.DEMO_LOGIN:
                modelLogin = SingletonGson.getInstance().fromJson("" + script, ModelLogin.class);
                sessionManager.setAccessToken("" + modelLogin.getData().getAccess_token());

//                try{
//                    sessionManager.setFirebaseNotification(modelLogin.getData().getPush_notification().isFire_base());
//                }catch (Exception e){
//
//                }
                try {
                    final HashMap<String, String> data = new HashMap<String, String>();
                    apiManager._post(API_S.Tags.DRIVER_DETAILS, API_S.Endpoints.DRIVER_DETAILS, null, sessionManager);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case API_S.Tags.DRIVER_DETAILS:
                progressDialog.hide();
                ModelDriverDetails modelDriverDetails = SingletonGson.getInstance().fromJson("" + script, ModelDriverDetails.class);

                try {
                    if (modelDriverDetails.getData().getDriverVehicle().size() > 0) {
                        vehicleId = String.valueOf(modelDriverDetails.getData().getDriverVehicle().get(0).getId());
                        sessionManager.setVehicleId(vehicleId);
                    } else {
                        vehicleId = "";
                    }
                } catch (Exception e) {

                }

                if (modelDriverDetails.getData().getSignupStep().equals("1")) { // basic registration is done open add car screen
                    startActivity(new Intent(DemoActivity.this, AddVehicleActivity.class)
                            .putExtra("" + IntentKeys.DRIVER_ID, "" + modelDriverDetails.getData().getId())
                            .putExtra("" + IntentKeys.AREA_ID, "" + modelDriverDetails.getData().getCountry_area_id()));
                    finish();
                }
                if (modelDriverDetails.getData().getSignupStep().equals("2")) { // documents not uploaded or all are verified
                    startActivity(new Intent(DemoActivity.this, DocumentActivity.class)
                            .putExtra("" + IntentKeys.DRIVER_ID, "" + modelDriverDetails.getData().getId())
                            .putExtra("documentScreenApi", "0")
                            .putExtra("" + IntentKeys.DRIVER_ID, "" + modelDriverDetails.getData().getId())
                            .putExtra("" + IntentKeys.DRIVER_VEHICLE_ID, "" + modelDriverDetails.getData().getDriverVehicle().get(0).getId()));
                    finish();
                }
                if (modelDriverDetails.getData().getSignupStep().equals("3")) { // all things are set and ready to go
                    sessionManager.setcountryid(Integer.parseInt(modelDriverDetails.getData().getCountry_id()));
                    sessionManager.setDemoOrNot("1");
                    sessionManager.createLoginSession(true, "" + modelDriverDetails.getData().getId()
                            , "" + vehicleId
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
                            , modelDriverDetails.getData().getCreated_at()
                            , String.valueOf(modelDriverDetails.getData().getLogin_logout())
                            , String.valueOf(modelDriverDetails.getData().getFree_busy())
                            , String.valueOf(modelDriverDetails.getData().getOnline_offline())
                            , "3"
                            , ""
                            , modelDriverDetails.getData().getAuto_accept_enable()
                            , modelDriverDetails.getData().getSubscription_wise_commission()
                    ,modelDriverDetails.getData().getNumber()
                    ,modelDriverDetails.getData().getStreet()
                    ,modelDriverDetails.getData().getCity()
                    ,modelDriverDetails.getData().getState_code()
                    ,modelDriverDetails.getData().getPostCode_cep());
                    startActivity(new Intent(DemoActivity.this, MainActivity.class));
                    SplashActivity.splash.finish();
                    finish();
                }
                break;
        }
    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {

        Toast.makeText(this, "" + script, Toast.LENGTH_SHORT).show();
    }
}
