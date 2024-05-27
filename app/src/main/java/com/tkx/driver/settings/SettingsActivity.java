package com.tkx.driver.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.tkx.driver.BuildConfig;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.tkx.driver.AboutActivity;
import com.tkx.driver.CustomerSupportActivity;
import com.tkx.driver.MainActivity;
import com.tkx.driver.R;
import com.tkx.driver.ReferAndEarnActivity;
import com.tkx.driver.SetRadiusActivity;
import com.tkx.driver.SingletonGson;
import com.tkx.driver.SplashActivity;
import com.tkx.driver.TermsAndCondition;
import com.tkx.driver.baseClass.BaseActivity;
import com.tkx.driver.currentwork.API_S;
import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.models.ModelAutoUpgrade;
import com.tkx.driver.rating_module.DriverRatingActivity;
import com.tkx.driver.samwork.ApiManager;
import com.tkx.driver.sup_driver.SuperDriver;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import customviews.typefacesviews.TypeFaceGoogle;

public class SettingsActivity extends BaseActivity implements View.OnClickListener, ApiManager.APIFETCHER {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.ll_language_btn)
    TypeFaceGoogle llLanguageBtn;
    @BindView(R.id.ll_customer_btn)
    TypeFaceGoogle llCustomerBtn;
    @BindView(R.id.ll_report_issue_btn)
    TypeFaceGoogle llReportIssueBtn;
    @BindView(R.id.ll_terms_btn)
    TypeFaceGoogle llTermsBtn;
    @BindView(R.id.ll_about_btn)
    TypeFaceGoogle llAboutBtn;
    @BindView(R.id.textView_version_name)
    TypeFaceGoogle textViewVersionName;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.switchAutoUpgrade)
    Switch switchAutoUpgrade;
    @BindView(R.id.switchPoolRide)
    Switch switchPoolRide;
    @BindView(R.id.rlAutoUpgradation)
    LinearLayout rlAutoUpgradation;
    @BindView(R.id.rlPoolRide)
    LinearLayout rlPoolRide;
    @BindView(R.id.ll_privacy_policy)
    TypeFaceGoogle ll_privacy_policy;
    @BindView(R.id.super_driver)
    LinearLayout super_driver;
    @BindView(R.id.driver_rating)
    TypeFaceGoogle driver_rating;
    @BindView(R.id.refer_menu_btn)
    LinearLayout refer_menu_btn;
    @BindView(R.id.set_radius_btn)
    LinearLayout set_radius_btn;
    @BindView(R.id.driverRating)
    LinearLayout driverRating;
    @BindView(R.id.top_layout)
    TextView top_layout;
    @BindView(R.id.language)
    LinearLayout language;

    private SessionManager sessionManager;
    private ApiManager apiManager;
    private final String TAG = "SettingsActivity";

    private String versionName = "";

    Integer autoUpgrade;
    Boolean poolRideStatusShow;
    private HashMap<String, String> data = new HashMap<>();
    String autoUpgradeShow, poolRideStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        apiManager = new ApiManager(this, this);
        sessionManager = new SessionManager(this);
        try {
            versionName = getPackageManager()
                    .getPackageInfo(getPackageName(), 0).versionName;

            textViewVersionName.setText(getString(R.string.version) + " (" + versionName + ")");

            getActionStatus();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if(BuildConfig.Build_varient.equals("drivadriver") || BuildConfig.Build_varient.equals("tankydriver")){
            top_layout.setVisibility(View.GONE);
            language.setVisibility(View.GONE);

        }
        onClickListeners();
    }

    private void getActionStatus() {

        try{
            autoUpgrade = Integer.valueOf(getIntent().getStringExtra("" + IntentKeys.AUTO_UPGRADATION));
            poolRideStatusShow = getIntent().getBooleanExtra(IntentKeys.POOL_RIDE_ACTIVATE_SHOW, false);
            autoUpgradeShow = getIntent().getStringExtra("" + IntentKeys.AUTO_UPGRADATION_SHOW);
            poolRideStatus = getIntent().getStringExtra("" + IntentKeys.POOL_RIDE_ACTIVATE);

            if (autoUpgrade == 1) {
                switchAutoUpgrade.setChecked(true);
            } else {
                switchAutoUpgrade.setChecked(false);
            }

            if (poolRideStatus.equals("1")) {
                switchPoolRide.setChecked(true);
            } else {
                switchPoolRide.setChecked(false);
            }

            rlAutoUpgradation.setVisibility(autoUpgradeShow.equals("1") ? View.VISIBLE : View.GONE);
            rlPoolRide.setVisibility(poolRideStatusShow ? View.VISIBLE : View.GONE);

        }catch (Exception e){

        }

        super_driver.setVisibility(sessionManager.getAppConfig().getData().getGeneral_config().isEnable_super_driver()?View.VISIBLE:View.GONE);
        set_radius_btn.setVisibility(sessionManager.getAppConfig().getData().getGeneral_config().isDriver_limit()?View.VISIBLE:View.GONE);

        // driver_rating.setVisibility(sessionManager.getAppConfig().getData().getGeneral_config().is);


        super_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SettingsActivity.this, SuperDriver.class);
                startActivity(intent);
            }
        });

        switchAutoUpgrade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);

                if (isChecked) {

                    data.put("status", "1");
                    try {
                        apiManager._post(API_S.Tags.AUTO_UPGRADE, API_S.Endpoints.AUTO_UPGRADE, data, sessionManager);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {

                    data.put("status", "2");
                    try {
                        apiManager._post(API_S.Tags.AUTO_UPGRADE, API_S.Endpoints.AUTO_UPGRADE, data, sessionManager);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

            }

        });


        switchPoolRide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);

                if (isChecked) {

                    data.put("pool_status", "1");
                    try {
                        apiManager._post(API_S.Tags.ACTIVE_POOL, API_S.Endpoints.ACTIVE_POOL, data, sessionManager);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {

                    data.put("pool_status", "2");
                    try {
                        apiManager._post(API_S.Tags.ACTIVE_POOL, API_S.Endpoints.ACTIVE_POOL, data, sessionManager);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

            }

        });


    }


    private void onClickListeners() {

        back.setOnClickListener(this);
        llLanguageBtn.setOnClickListener(this);
        llCustomerBtn.setOnClickListener(this);
        llReportIssueBtn.setOnClickListener(this);
        llTermsBtn.setOnClickListener(this);
        llAboutBtn.setOnClickListener(this);
        ll_privacy_policy.setOnClickListener(this);
        driver_rating.setOnClickListener(this);
        refer_menu_btn.setOnClickListener(this);
        set_radius_btn.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.driver_rating:
                startActivity(new Intent(SettingsActivity.this, DriverRatingActivity.class));
                break;
            case R.id.ll_language_btn:
                String[] array = new String[sessionManager.getAppConfig().getData().getLanguages().size()];


                for (int i = 0; i < sessionManager.getAppConfig().getData().getLanguages().size(); i++) {
                    array[i] = sessionManager.getAppConfig().getData().getLanguages().get(i).getName();
                }


                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle(R.string.select_language)
                        .setItems(array, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (sessionManager.getAppConfig().getData().getLanguages().get(which).getLocale().equals("pt")){
                                    sessionManager.setUpdatedStringVersion("0.0");
                                    sessionManager.setLanguage("pt");
                                    finish();
                                }else {
                                    sessionManager.setUpdatedStringVersion("0.0");
                                    sessionManager.setLanguage("" + sessionManager.getAppConfig().getData().getLanguages().get(which).getLocale());
                                    finish();
                                }
                                MainActivity.activity.finish();
                                startActivity(new Intent(SettingsActivity.this, SplashActivity.class));
                            }
                        });
                builder.create().show();
                break;
            case R.id.ll_customer_btn:
                startActivity(new Intent(SettingsActivity.this, CustomerSupportActivity.class));
                break;

            case R.id.ll_report_issue_btn:

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "" + sessionManager.getAppConfig().getData().getCustomer_support().getMail(), null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "" + SettingsActivity.this.getResources().getString(R.string.report_issue));
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(emailIntent, "" + SettingsActivity.this.getResources().getString(R.string.send_email)));
                emailIntent.setType("text/plain");

                break;

            case R.id.ll_terms_btn:
                startActivity(new Intent(SettingsActivity.this, TermsAndCondition.class));
                break;

            case R.id.ll_about_btn:
                startActivity(new Intent(SettingsActivity.this, AboutActivity.class).putExtra("from", "1"));
                break;

            case R.id.ll_privacy_policy:
                startActivity(new Intent(SettingsActivity.this, AboutActivity.class).putExtra("from", "2"));
                break;

            case R.id.back:
                finish();
                break;

            case R.id.refer_menu_btn:
                startActivity(new Intent(SettingsActivity.this, ReferAndEarnActivity.class));
                break;

            case R.id.set_radius_btn :
                startActivity(new Intent(SettingsActivity.this, SetRadiusActivity.class));
                break;
        }
    }

    @Override
    public void onAPIRunningState(int a, String APINAME) {

    }

    @Override
    public void onFetchComplete(Object script, String APINAME) {
        try {
            switch (APINAME) {
                case API_S.Tags.AUTO_UPGRADE:
                    ModelAutoUpgrade modelAutoUpgrade = SingletonGson.getInstance().fromJson("" + script, ModelAutoUpgrade.class);
                    if (modelAutoUpgrade.getResult().equals("1")) {
                        Snackbar.make(root, "" + modelAutoUpgrade.getMessage(), Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(root, "" + modelAutoUpgrade.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                    break;

                case API_S.Tags.ACTIVE_POOL:
                    ModelAutoUpgrade modelAutoUpgrade1 = SingletonGson.getInstance().fromJson("" + script, ModelAutoUpgrade.class);
                    if (modelAutoUpgrade1.getResult().equals("1")) {
                        Snackbar.make(root, "" + modelAutoUpgrade1.getMessage(), Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(root, "" + modelAutoUpgrade1.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                    break;
            }
        } catch (Exception e) {
            Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {
        Snackbar.make(root, "" + script, Snackbar.LENGTH_SHORT).show();
    }
}
