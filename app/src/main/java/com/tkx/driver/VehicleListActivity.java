package com.tkx.driver;

import static com.tkx.driver.Mappers.DataBeanMapper.mapToDataBeanRoom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tkx.driver.activities.vehicleModule.SampleVehicleActivity;
import com.tkx.driver.baseClass.BaseActivity;
import com.tkx.driver.currentwork.API_S;
import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.database.DatabaseClient;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.models.ModelActivateVehicle;
import com.tkx.driver.models.ModelDeviceOnlineIffline;
import com.tkx.driver.models.ModelDriverVehicles;
import com.tkx.driver.samwork.ApiManager;
import com.bumptech.glide.Glide;

import com.sam.placer.PlaceHolderView;
import com.sam.placer.annotations.Click;
import com.sam.placer.annotations.Layout;
import com.sam.placer.annotations.Resolve;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VehicleListActivity extends BaseActivity implements ApiManager.APIFETCHER {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.placeHolder)
    PlaceHolderView placeHolder;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tvManageVehicle)
    TextView tvManageVehicle;

    private final String TAG = "VehicleListActivity";
    ApiManager apiManager;
    SessionManager sessionManager;
    private HashMap<String, String> data = new HashMap<>();
    ModelDriverVehicles modelDriverVehicles;
    DataBeanRoomDao dataBeanRoomDao;

    String strShowStatus = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_vehicle_list);
        ButterKnife.bind(this);
        apiManager = new ApiManager(this, this);
        sessionManager = new SessionManager(this);
        strShowStatus = getIntent().getStringExtra("SHOW_VEHICLE_LIST");
        back.setOnClickListener(view -> finish());
        swipeRefreshLayout.setOnRefreshListener(() -> refreshPage());

        AppDatabase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
        dataBeanRoomDao = db.dataBeanRoomDao();
    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshPage();
    }


    private void refreshPage() {
        try {
            placeHolder.removeAllViews();
        } catch (Exception e) {
            Log.d("" + TAG, "Exception caught while removing view from placeholder " + e.getMessage());
        }
        try {
            apiManager._post(API_S.Tags.DRIVER_VEHICLES, API_S.Endpoints.DRIVER_VEHICLES, null, sessionManager);
        } catch (Exception E) {
            Log.d("" + TAG, "Exception caught while calling api " + E.getMessage());
        }
    }

    @Override
    public void onAPIRunningState(int a, String APINAME) {
        if (a == ApiManager.APIFETCHER.KEY_API_IS_STARTED) {
            swipeRefreshLayout.setRefreshing(true);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    @Override
    public void onFetchComplete(Object script, String APINAME) {

        try {
            switch (APINAME) {
             case API_S.Tags.DRIVER_VEHICLES:
                 modelDriverVehicles = SingletonGson.getInstance().fromJson("" + script, ModelDriverVehicles.class);

                 for (ModelDriverVehicles.DataBean vehicle : modelDriverVehicles.getData()) {
                     DataBeanRoom dataBeanRoom = mapToDataBeanRoom(vehicle);

                     new Thread(new Runnable() {
                         @Override
                         public void run() {
                             synchronized (dataBeanRoomDao) {
                                 if (dataBeanRoomDao.getDataBeanById(dataBeanRoom.getId())) {
                                     dataBeanRoomDao.update(dataBeanRoom);
                                 } else {
                                     dataBeanRoomDao.insert(dataBeanRoom);
                                 }
                             }
                         }
                     }).start();
                 }

                 Handler mainHandler = new Handler(Looper.getMainLooper());
                 mainHandler.post(new Runnable() {
                     @Override
                     public void run() {
                         if (strShowStatus.equalsIgnoreCase("YES")) {
                             tvManageVehicle.setText(getResources().getString(R.string.manage_you_vehicles));
                             placeHolder.addView(new Holderheader(getResources().getString(R.string.your_vehicleds)));
                             for (int i = 0; i < modelDriverVehicles.getData().size(); i++) {
                                 placeHolder.addView(new HolderVehicles(modelDriverVehicles.getData().get(i)));
                             }
                         } else {
                             placeHolder.addView(new HolderActivatedvehicles(modelDriverVehicles.getData().get(0)));
                             if (modelDriverVehicles.getData().size() > 1) {
                                 tvManageVehicle.setText(getResources().getString(R.string.your_vehicleds));
                                 placeHolder.addView(new Holderheader(getResources().getString(R.string.your_other_vehicles)));
                                 for (int i = 1; i < modelDriverVehicles.getData().size(); i++) {
                                     placeHolder.addView(new HolderVehicles(modelDriverVehicles.getData().get(i)));
                                 }
                             }
                         }
                     }
                 });
                break;

                case API_S.Tags.ONLINE_OFFLINE:
                    ModelDeviceOnlineIffline modelDeviceOnlineIffline = SingletonGson.getInstance().fromJson("" + script, ModelDeviceOnlineIffline.class);
                    if (modelDeviceOnlineIffline.getData().getOnline_offline().equals("1")) {
                        sessionManager.setonline_offline(true);
                    } else if (modelDeviceOnlineIffline.getData().getOnline_offline().equals("2")) {
                        sessionManager.setonline_offline(false);
                    }
                    refreshPage();
                    break;
                case API_S.Tags.ACTIVATE_VEHICLE:
                    ModelActivateVehicle modelActivateVehicle = SingletonGson.getInstance().fromJson("" + script, ModelActivateVehicle.class);
                    Toast.makeText(this, "" + modelActivateVehicle.getMessage(), Toast.LENGTH_SHORT).show();

                    refreshPage();
                    // data.put("status", "2");
                    // data.put("vehicle_id", "" + modelActivateVehicle.getData().getId());
                    // try {
                    //     apiManager._post(API_S.Tags.ONLINE_OFFLINE, API_S.Endpoints.ONLINE_OFFLINE, data, sessionManager);
                    // } catch (Exception e) {
                    //     Log.e(TAG, "Exception caught while calling API " + e.getMessage());
                    // }
                    break;
            }
        } catch (Exception e) {
            // Toast.makeText(VehicleListActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            //  Snackbar.make(root,""+e.getMessage(),Snackbar.LENGTH_SHORT).show();
            Log.d("" + TAG, "Exceção capturada durante a análise " + e.getMessage());
        }
    }



    @Override
    public void onFetchResultZero(String script, String APINAME) {
        Snackbar.make(root, "" + script, Snackbar.LENGTH_SHORT).show();
    }

    @Layout(R.layout.layout_vehicles)
    private class HolderVehicles {
        @com.sam.placer.annotations.View(R.id.vehicle_category_image)
        ImageView vehicleCategoryImage;
        @com.sam.placer.annotations.View(R.id.vehicle_number)
        TextView vehicleNumber;
        @com.sam.placer.annotations.View(R.id.vehicle_make_and_model)
        TextView vehicleMakeAndModel;
        @com.sam.placer.annotations.View(R.id.vehicle_type_name)
        TextView vehicleTypeName;
        @com.sam.placer.annotations.View(R.id.services_types)
        TextView services_types;
        @com.sam.placer.annotations.View(R.id.status_text)
        TextView status_text;
        @com.sam.placer.annotations.View(R.id.vehicle_id_code)
        TextView vehicle_id_code;

        @com.sam.placer.annotations.View(R.id.radio_button)
        RadioButton radio_button;


        ModelDriverVehicles.DataBean mData;

        public HolderVehicles(ModelDriverVehicles.DataBean dataBean) {
            mData = dataBean;
        }


        @Resolve
        private void onResolved() {
            Glide.with(VehicleListActivity.this).load("" + mData.getVehicleTypeImage()).into(vehicleCategoryImage);
            vehicleNumber.setText("" + mData.getVehicle_number());
            vehicleMakeAndModel.setText("" + mData.getVehicle_make() + " | " + mData.getVehicle_model());
            vehicleTypeName.setText("" + mData.getVehicle_type());
            services_types.setText("" + mData.getService_types());
            vehicle_id_code.setText(getResources().getString(R.string.share_vehicle_id) + " " + mData.getShareCode());
            settextAccordingTostatus();

            radio_button.setOnClickListener(view -> {
                onClickRootMethod();
            });
        }


        @SuppressLint("SetTextI18n")
        private void settextAccordingTostatus() {

            status_text.setText(mData.getShow_msg());

            if (mData.getVehicle_active_status().equals("1")) { // documents are verified
                status_text.setTextColor(Color.parseColor("#3498db"));
            } else {

            }
            if (mData.getVehicle_verification_status().equals("2")) { // documents pending
                status_text.setTextColor(Color.parseColor("#95a5a6"));
            }
            if (mData.getVehicle_verification_status().equals("3")) { // documents rejected
                status_text.setTextColor(Color.parseColor("#e74c3c"));
            }

            if (mData.getReady_for_live() == 1 ) { // documents are verified
                if (strShowStatus.equalsIgnoreCase("NO")) {
                    radio_button.setVisibility(View.GONE);
                }
            }

            if (mData.getReady_for_live() == 2 || mData.getReady_for_live()==4 ) { // documents are verified
                if (strShowStatus.equalsIgnoreCase("NO")) {
                    radio_button.setVisibility(View.GONE);
                }
            }
        }


        @Click(R.id.root)
        private void rootClick() {
            onClickRootMethod();
        }

        private void onClickRootMethod() {

            if (mData.getVehicle_verification_status().equals("3")) { // documentos rejeitados
                Toast.makeText(VehicleListActivity.this, R.string.please_contact_your_admin, Toast.LENGTH_SHORT).show();
            } else if (mData.getVehicle_verification_status().equals("2") || mData.getVehicle_verification_status().equals("4")) { // documentos pendentes
                startActivity(new Intent(VehicleListActivity.this, DocumentActivity.class)
                        .putExtra("documentScreenApi", "1")
                        .putExtra("" + IntentKeys.DRIVER_ID, "" + sessionManager.getUserDetails().get(SessionManager.KEY_DRIVER_ID))
                        .putExtra("" + IntentKeys.DRIVER_VEHICLE_ID, "" + mData.getId()));
            } else {
                if (mData.getReady_for_live() == 1) { // documentos verificados
                    if (strShowStatus.equalsIgnoreCase("YES")) {
                        Intent intent = new Intent();
                        intent.putExtra("VEHICLE_ID", String.valueOf(mData.getId()));
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                        sessionManager.setVehicleId(String.valueOf(mData.getId()));
                    } else {
                    
                    AlertDialog.Builder builder = new AlertDialog.Builder(VehicleListActivity.this);
                    builder.setTitle(getString(R.string.activate) + " " + vehicleNumber.getText().toString());
                    builder.setMessage(R.string.are_you_sure_you_want_to_activate_this_vehicle)
                            .setPositiveButton(R.string.ok, (dialog, id) -> {
                                HashMap<String, String> data = new HashMap<>();
                                data.put("driver_vehicle", "" + mData.getId());
                                try {
                                    sessionManager.setVehicleId(String.valueOf(mData.getId()));
                                    apiManager._post(API_S.Tags.ACTIVATE_VEHICLE, API_S.Endpoints.ACTIVATE_VEHICLE, data, sessionManager);
                                } catch (Exception e) {
                                    Log.d("" + TAG, "Exceção capturada ao chamar api " + e.getMessage()
                                    );
                                }
                            })
                            .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.dismiss());
                    builder.create().show();
                    }

                } else if (mData.getReady_for_live() == 2) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(VehicleListActivity.this);
                    builder.setTitle(getString(R.string.activate) + " " + vehicleNumber.getText().toString());
                    builder.setMessage(R.string.another_driver);
                    builder.create().show();
                }
            }
        }
    }


    @Layout(R.layout.layout_activated_vehicle)
    private class HolderActivatedvehicles {

        private ModelDriverVehicles.DataBean mData;

        @com.sam.placer.annotations.View(R.id.activated_car_image)
        ImageView activatedCarImage;
        @com.sam.placer.annotations.View(R.id.cativated_car_type)
        TextView cativatedCarType;
        @com.sam.placer.annotations.View(R.id.cativated_car_number)
        TextView cativatedCarNumber;
        @com.sam.placer.annotations.View(R.id.cativated_car_make_model)
        TextView cativatedCarMakeModel;
        @com.sam.placer.annotations.View(R.id.cativated_car_services)
        TextView cativatedCarServices;
        @com.sam.placer.annotations.View(R.id.cardViewAddVehicle)
        CardView cardViewAddVehicle;
        @com.sam.placer.annotations.View(R.id.vehicle_id_code)
        TextView vehicle_id_code;
        @com.sam.placer.annotations.View(R.id.tvActivatedStatus)
        TextView tvActivatedStatus;

        public HolderActivatedvehicles(ModelDriverVehicles.DataBean dataBean) {
            mData = dataBean;
        }

        @Resolve
        private void setData() {
            Glide.with(VehicleListActivity.this).load("" + mData.getVehicleTypeImage()).into(activatedCarImage);
            tvActivatedStatus.setText("" + mData.getShow_msg());
            cativatedCarType.setText("" + mData.getVehicle_type());
            cativatedCarMakeModel.setText("" + mData.getVehicle_make() + " | " + mData.getVehicle_model());
            cativatedCarServices.setText("" + mData.getService_types());
            cativatedCarNumber.setText("" + mData.getVehicle_number());
            vehicle_id_code.setText(getResources().getString(R.string.share_vehicle_id) + " " + mData.getShareCode());
            cardViewAddVehicle.setVisibility(sessionManager.getAppConfig().getData().getGeneral_config().isAdd_multiple_vehicle() ? View.VISIBLE : View.GONE);

        }

        @Click(R.id.add_vehicles)
        private void rootClick() {
            startActivity(new Intent(VehicleListActivity.this, SampleVehicleActivity.class)
                    .putExtra("documentScreenApi", "1")
                    .putExtra("" + IntentKeys.AREA_ID, "" + sessionManager.getUserDetails().get(SessionManager.KEY_AREA_ID))
                    .putExtra("" + IntentKeys.DRIVER_ID, "" + sessionManager.getUserDetails().get(SessionManager.KEY_DRIVER_ID)));
        }

    }

    @Layout(R.layout.header_layout)
    private class Holderheader {
        @com.sam.placer.annotations.View(R.id.header)
        TextView header;

        private String mheader;

        public Holderheader(String message) {
            mheader = message;
        }

        @Resolve
        private void setData() {
            header.setText("" + mheader);
        }
    }
}
