package com.tkx.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.tkx.driver.manager.ModelCountries;
import com.tkx.driver.manager.SessionManager;
import com.sam.placer.PlaceHolderView;

public class CountryCodeActivity extends AppCompatActivity {


    PlaceHolderView placeHolderView;
    SessionManager sessionManager;

    ProgressDialog progressDialog;
    ModelCountries modelCountries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_code);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();


        placeHolderView = findViewById(R.id.placeholder_country);
        sessionManager = new SessionManager(this);

        // for (int i = 0; i < modelCountries.getData().getCountries().size(); i++){
        //    placeHolderView.addView(new HolderCountryCode(this,modelCountries.getData().getCountries().get(i)));
        // }

        progressDialog.hide();

    }

}
