package com.tkx.driver.currentwork;

import static com.tkx.driver.Mappers.ActiveRidesMapper.mapToTripDetails;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;

import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tkx.driver.AppDatabase;
import com.tkx.driver.DatabeanTripDetailsSchedule;
import com.tkx.driver.DatabeanTripDetailsScheduleDao;
import com.tkx.driver.R;
import com.tkx.driver.SingletonGson;
import com.tkx.driver.baseClass.BaseFragment;
import com.tkx.driver.database.DatabaseClient;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.models.ModelFragmenRides;
import com.tkx.driver.samwork.ApiManager;
import com.sam.placer.PlaceHolderView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActiveRideFragment extends BaseFragment implements ApiManager.APIFETCHER, HolderRideHistoryItem.OnBottomReachedListener {

    @BindView(R.id.no_record_trips)
    TextView no_record_active_rides;
    @BindView(R.id.place_holder)
    PlaceHolderView placeHolder;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    DatabeanTripDetailsScheduleDao tripDetailsScheduleDao;

    ApiManager apiManager;
    private SessionManager sessionManager;
    private final String TAG = "ActiveRideFragment";

    public ActiveRideFragment() {
    }

    public static ActiveRideFragment newInstance() {
        ActiveRideFragment fragment = new ActiveRideFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiManager = new ApiManager(this, getContext());
        sessionManager = new SessionManager(getActivity());

        AppDatabase db = DatabaseClient.getInstance(getContext()).getAppDatabase();
        tripDetailsScheduleDao = db.databeanTripDetailsSchedule();
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

    private void callActiveAPI() {
        try {
            apiManager._post(API_S.Tags.RIDE_HISTORY_ACTIVE, API_S.Endpoints.RIDE_HISTORY_ACTIVE, null, sessionManager);
            placeHolder.removeAllViews();
        } catch (Exception e) {
            Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onAPIRunningState(int a, String APINAME) {
        try {
            if (a == ApiManager.APIFETCHER.KEY_API_IS_STARTED) {
                swipeRefreshLayout.setRefreshing(true);
            } else {
                swipeRefreshLayout.setRefreshing(false);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in onAPIRunningState: " + e.getMessage());
        }
    }

    @Override
    public void onFetchComplete(Object script, String APINAME) {
        try {
            ModelFragmenRides modelFragmenRides = SingletonGson.getInstance().fromJson("" + script, ModelFragmenRides.class);

            for (ModelFragmenRides.DataBean ride : modelFragmenRides.getData()) {
                DatabeanTripDetailsSchedule dataBeanRoom = mapToTripDetails(ride);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (tripDetailsScheduleDao) {
                            if (tripDetailsScheduleDao.existsById(dataBeanRoom.getBooking_id())) {
                                tripDetailsScheduleDao.update(dataBeanRoom);
                            } else {
                                tripDetailsScheduleDao.insert(dataBeanRoom);
                            }
                        }
                    }
                }).start();
            }

            Handler mainHandler = new Handler(Looper.getMainLooper());
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    placeHolder.removeAllViews();
                    for (ModelFragmenRides.DataBean ride : modelFragmenRides.getData()) {
                        placeHolder.addView(new HolderRideHistoryItem(getActivity(), ride, true, ActiveRideFragment.this));
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Exception caught while calling API: " + e.getMessage());
        }
    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {
        try {
            placeHolder.setVisibility(View.GONE);
            no_record_active_rides.setText(script);
            no_record_active_rides.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Log.e(TAG, "Error in onFetchResultZero: " + e.getMessage());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        callActiveAPI();
    }

    @Override
    public void onBottomReached() {
        // Implement if needed
    }
}