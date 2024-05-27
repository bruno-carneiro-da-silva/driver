package com.tkx.driver;

import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.tkx.driver.baseClass.BaseActivity;
import com.tkx.driver.currentwork.API_S;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.models.ModelAboutUs;
import com.tkx.driver.samwork.ApiManager;
import com.tkx.driver.typeface.TypefaceTextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity implements ApiManager.APIFETCHER {
    
    SessionManager sessionManager;
    ApiManager apiManager;
    @BindView(R.id.ll_back_about)
    LinearLayout llBackAbout;
    @BindView(R.id.tv_about)
    TypefaceTextView tvAbout;
    @BindView(R.id.tv_version)
    TypefaceTextView tvVersion;
    @BindView(R.id.text_name)
    TextView text_name;

    private final String TAG = "AboutActivity";
    @BindView(R.id.root)
    LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        apiManager = new ApiManager(this,this);
        sessionManager = new SessionManager(this);

        tvVersion.setText("Version Name : "+SplashActivity.app_version_name);

        String from=getIntent().getStringExtra("from");


        if (from.equals("1")) {
            try {
                HashMap<String, String> data = new HashMap<>();
                data.put("slug", "about_us");
                apiManager._post(API_S.Tags.CMS_PAGES, API_S.Endpoints.CMS_PAGES, data, sessionManager);
            } catch (Exception e) {
                Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                Log.d("" + TAG, "Exception caught while calling API " + e.getMessage());
            }

        }else {

            text_name.setText(getResources().getString(R.string.Privacy_policy)); 
            HashMap<String, String> data = new HashMap<>();
            data.put("slug", "privacy_policy");
            try {
                apiManager._post(API_S.Tags.CMS_PAGES, API_S.Endpoints.CMS_PAGES, data, sessionManager);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        llBackAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onAPIRunningState(int a, String APINAME) {

    }

    @Override
    public void onFetchComplete(Object script, String APINAME) {
        try {
            ModelAboutUs modelAboutUs = SingletonGson.getInstance().fromJson(""+script, ModelAboutUs.class);
            tvAbout.setText(Html.fromHtml("" + modelAboutUs.getData().getDescription()));

            if(modelAboutUs.getResult().equals("1")){
                tvAbout.setText(Html.fromHtml("" + modelAboutUs.getData().getDescription()));
            }else {
                Snackbar.make(root,"No Pages are added from admin panel",Snackbar.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            //Snackbar.make(root,""+script,Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {
        Toast.makeText(AboutActivity.this, ""+ script, Toast.LENGTH_SHORT).show();
    }

    //
    // Actually I ahve to restrict branched to select another brancj thjaht is ncnjvnjfnv
}
