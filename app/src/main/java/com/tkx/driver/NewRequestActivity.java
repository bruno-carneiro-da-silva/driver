package com.tkx.driver;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tkx.driver.baseClass.BaseActivity;
import com.tkx.driver.currentwork.NormalUpcomingFragment;
import com.tkx.driver.currentwork.OutStationUpcomingFragment;
import com.tkx.driver.manager.SessionManager;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
// import com.sam.placer.PlaceHolderView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewRequestActivity extends BaseActivity {

    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.viewpagertab)
    SmartTabLayout viewpagertab;
    @BindView(R.id.container)
    ViewPager container;
    // delivery sdvi
    // @BindView(R.id.llSegmentScreen)
    // LinearLayout llSegmentScreen;
    @BindView(R.id.llViewPager)
    LinearLayout llViewPager;
    public static Activity activity;
    private SessionManager sessionManager;
    private final String TAG = "NewRequestActivity";
    // delivery sdvi
    // @BindView(R.id.placeHolderModule)
    // PlaceHolderView placeHolderModule;
    String selctedModule = "1";
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_request);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        container.setAdapter(pagerAdapter);
        viewpagertab.setViewPager(container);

        setNavView();

        back.setOnClickListener((View view) -> {
            finish();
        });
    }

    private void setNavView() {

        if(sessionManager.getAppConfig().getData().getSegments().get(0).getSlag().equals("TAXI")){
            selctedModule = "1";
        }else {
            selctedModule = "2";
        }

        // delivery sdvi
        // if (sessionManager.getAppConfig().getData().getSegments().size() > 1) {
        //     for (int i = 0; i < sessionManager.getAppConfig().getData().getSegments().size(); i++) {
        //         placeHolderModule.addView(new YourUpcoming_trips(this, sessionManager.getAppConfig().getData().getSegments().get(i)));
        //     }
        // } else {
        //     llSegmentScreen.setVisibility(View.GONE);
        //     LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
        //             LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //     params.weight = 2f;
        //     llViewPager .setLayoutParams(params);
        // }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        // String[] fragmens_name = { getString(R.string.new_request), getString(R.string.out_station) };
        String[] fragmens_name = { "Fechado", "Complemento" };

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 1) {
                return OutStationUpcomingFragment.newInstance();
            }
            if (position == 0) {
                return NormalUpcomingFragment.newInstance(selctedModule);
            } else {
                return NormalUpcomingFragment.newInstance(selctedModule);
            }
        }

        @Override
        public int getCount() {
            if (!sessionManager.getAppConfig().getData().getRide_config().isOutstation()) {
                return 1;
            } else {
                return fragmens_name.length;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmens_name[position];
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public void Data(String id){
        selctedModule = String.valueOf(id);
        NormalUpcomingFragment.newInstance(selctedModule);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        container.setAdapter(pagerAdapter);
        viewpagertab.setViewPager(container);
    }
}
