package com.tkx.driver.activities.vehicleModule;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.View;

import com.tkx.driver.R;
import com.tkx.driver.SplashActivity;
import com.tkx.driver.activities.vehicleModule.fragments.AddVehicleDriver_Activity;
import com.tkx.driver.activities.vehicleModule.fragments.ExistinVehicle_Activity;
import com.tkx.driver.baseClass.BaseActivity;
import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.samwork.ApiManager;

public class SampleVehicleActivity extends BaseActivity implements
        VehicleInterface, ApiManager.APIFETCHER  {

    public static ViewPager vpPager;
    static String area_id, driver_id;
    public static VehicleInterface vehicleInterface;
   public static SessionManager sessionManager;

   public static String  documentScreenApi = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_vehicle);
        getSupportActionBar().hide();

        sessionManager = new SessionManager(this);
        vpPager = (ViewPager) findViewById(R.id.register_vp);
        MyPagerAdapter adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(),SampleVehicleActivity.this);
        vpPager.setAdapter(adapterViewPager);

        area_id = getIntent().getStringExtra(IntentKeys.AREA_ID);
        driver_id = getIntent().getStringExtra(IntentKeys.DRIVER_ID);
        vehicleInterface = this;

        documentScreenApi = getIntent().getStringExtra("documentScreenApi");

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SampleVehicleActivity.this, SplashActivity.class)
                        .putExtra("value","1"));
                SampleVehicleActivity.this.finish();
            }
        });

    }

    @Override
    public void newVehicle(String city_id, String driver_id, String phone_no) {

//        finish();
//        startActivity(new Intent(this, DocumentActivity.class)
//                .putExtra("driver_id", "" + driver_id)
//                .putExtra("city_id", "" + city_id)
//                .putExtra("phone", phone_no)
//                .putExtra("password", password));
    }

    @Override
    public void existingVehicle(String city_id, String driver_id) {

    }

    @Override
    public void onAPIRunningState(int a, String APINAME) {

    }

    @Override
    public void onFetchComplete(Object script, String APINAME) {

    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {

    }


    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;
        Context context;

        public MyPagerAdapter(FragmentManager fm,Context context) {
            super(fm);
            this.context = context;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            if (!sessionManager.getAppConfig().getData().getGeneral_config().isExisting_vehicle_enable()) {
                return 1;
            } else {
                return NUM_ITEMS;
            }
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    Log.e("****DocumenTParaSample",""+documentScreenApi);
                    return AddVehicleDriver_Activity.newInstance(area_id, driver_id,documentScreenApi);
                case 1: // Fragment # 1 - This will show FirstFragment different title
                    return ExistinVehicle_Activity.newInstance(area_id, driver_id,documentScreenApi);
                default:
                    return AddVehicleDriver_Activity.newInstance(area_id, driver_id,documentScreenApi);
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {


            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return ""+context.getResources().getString(R.string.New_Vehicle);
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return ""+context.getResources().getString(R.string.Existing_Vehicle); // "Existing Vehicle";
                default:
                    return null;
            }
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SampleVehicleActivity.this, SplashActivity.class)
        .putExtra("value","1"));
        SampleVehicleActivity.this.finish();
    }
}
