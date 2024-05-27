package com.tkx.driver.currentwork;

import android.os.Bundle;

import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tkx.driver.R;
import com.tkx.driver.SingletonGson;
import com.tkx.driver.baseClass.BaseFragment;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.models.ModelFragmenRides;
import com.tkx.driver.samwork.ApiManager;
import com.sam.placer.PlaceHolderView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentScheduleRides extends BaseFragment implements ApiManager.APIFETCHER,HolderRideHistoryItem.OnBottomReachedListener{


    @BindView(R.id.no_record_trips)
    TextView no_record_schedule;
    @BindView(R.id.place_holder) PlaceHolderView placeHolder;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    ApiManager apiManager;
    private SessionManager sessionManager;
    private final String TAG = "FragmentScheduleRides";

    public FragmentScheduleRides() {
    }

    public static FragmentScheduleRides newInstance() {
        FragmentScheduleRides fragment = new FragmentScheduleRides();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiManager = new ApiManager(this,getContext());
        sessionManager = new SessionManager(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.active_ride_fragment, container, false);
        ButterKnife.bind(this, rootView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callActiveAPI();
            }
        });

        return rootView;
    }




    private void callActiveAPI(){
        try {
            apiManager._post(API_S.Tags.RIDE_HISTORY_SCHEDULE, API_S.Endpoints.RIDE_HISTORY_SCHEDULE, null, sessionManager);
            placeHolder.removeAllViews();
        } catch (Exception e) {
            Snackbar.make(root, "", Snackbar.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // ButterKnife.unbind(this);
    }

    @Override
    public void onAPIRunningState(int a, String APINAME) {
        try {
            if(a == ApiManager.APIFETCHER.KEY_API_IS_STARTED){
                swipeRefreshLayout.setRefreshing(true);
            }else{
                swipeRefreshLayout.setRefreshing(false);
            }
        }catch (Exception e){
        }
    }

    @Override
    public void onFetchComplete(Object script, String APINAME) {
        try{
            no_record_schedule.setVisibility(View.GONE);
            placeHolder.setVisibility(View.VISIBLE);
            ModelFragmenRides modelScheduledRIdes = SingletonGson.getInstance().fromJson("" + script, ModelFragmenRides.class);
            for(int i =0 ; i < modelScheduledRIdes.getData().size() ; i++){
                placeHolder.addView(new HolderRideHistoryItem(getActivity(), modelScheduledRIdes.getData().get(i), true, this));
            }
        }catch (Exception e){
            Log.d("" +TAG , "Exception caught while calling API "+e.getMessage());}

    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {
        try {
            placeHolder.setVisibility(View.GONE);
            no_record_schedule.setText(script);
            no_record_schedule.setVisibility(View.VISIBLE);
        }catch (Exception e){

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        callActiveAPI();
    }

    @Override
    public void onBottomReached() {

    }
}
