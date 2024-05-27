package com.tkx.driver.activities.chooseAddVehicleType;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.tkx.driver.AddVehicleActivity;
import com.tkx.driver.R;
import com.tkx.driver.baseClass.BaseActivity;
import com.tkx.driver.currentwork.IntentKeys;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseVehicleType extends BaseActivity implements View.OnClickListener {

    private final String TAG = "ChooseVehicleActivity";
    public static Activity chooseVehicleType;
    @BindView(R.id.btnAddNewVehicle)
    Button btnAddNewVehicle;

    @BindView(R.id.addExistingVehicle)
    Button addExistingVehicle;

    @BindView(R.id.llAddNewVehicleLayout)
    LinearLayout llAddNewVehicleLayout;

    String driver_id, area_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_vehicle_type);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        chooseVehicleType = ChooseVehicleType.this;
        driver_id = getIntent().getStringExtra(IntentKeys.DRIVER_ID);
        area_id = getIntent().getStringExtra(IntentKeys.AREA_ID);

        btnAddNewVehicle.setOnClickListener(this);
        addExistingVehicle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnAddNewVehicle:
                startActivity(new Intent(ChooseVehicleType.this, AddVehicleActivity.class)
                        .putExtra("" + IntentKeys.DRIVER_ID, "" + driver_id)
                        .putExtra("" + IntentKeys.AREA_ID, "" + area_id));
                break;

            case R.id.addExistingVehicle:
                startActivity(new Intent(ChooseVehicleType.this, AddExistingVehicleActivity.class)
                        .putExtra("" + IntentKeys.DRIVER_ID, "" + driver_id)
                        .putExtra("" + IntentKeys.AREA_ID, "" + area_id));
                break;

        }
    }
}
