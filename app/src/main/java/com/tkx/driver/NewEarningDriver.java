package com.tkx.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tkx.driver.NewEarningModule.ModelNewDriverEarning;
import com.tkx.driver.currentwork.API_S;
import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.earnings.DailyStatementActivity;
import com.tkx.driver.holder.HolderShowDriverNetEarning;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.samwork.ApiManager;
import com.sam.placer.PlaceHolderView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewEarningDriver extends AppCompatActivity implements ApiManager.APIFETCHER,android.view.View.OnClickListener, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.placeHoder)
    PlaceHolderView placeHoder;
    @BindView(R.id.rightClick)
    LinearLayout rightClick;
    @BindView(R.id.leftClick)
    LinearLayout leftClick;
    @BindView(R.id.selectedDate)
    TextView selectedDate;
    @BindView(R.id.tvTotalEarnings)
    TextView tvTotalEarnings;
    @BindView(R.id.tvTotalEarnings_text)
    TextView tvTotalEarnings_text;
    @BindView(R.id.highlighted_text1)
    TextView highlighted_text1;
    @BindView(R.id.value_text1)
    TextView value_text1;
    @BindView(R.id.trip_history)
    TextView trip_history;
    @BindView(R.id.walletAmount)
    TextView walletAmount;
    @BindView(R.id.wallet)
    TextView wallet;
    @BindView(R.id.cash)
    TextView cash;
    @BindView(R.id.cash_value)
    TextView cash_value;
    @BindView(R.id.l_triphistory)
    LinearLayout l_triphistory;
    @BindView(R.id.l1_trip)
    LinearLayout l1_trip;
    @BindView(R.id.l1_wallet)
    LinearLayout l1_wallet;
    @BindView(R.id.l1_cash)
    LinearLayout l1_cash;
    @BindView(R.id.l1_wallet_balance)
    LinearLayout l1_wallet_balance;
    @BindView(R.id.l1_w_amnt)
    LinearLayout l1_w_amnt;

    @BindView(R.id.curent_wall_amt)
    TextView curent_wall_amt;
    @BindView(R.id.current_wallet_balance)
    TextView current_wallet_balance;
    @BindView(R.id.wallet_amount)
    TextView wallet_amount;
    @BindView(R.id.cashout)
    TextView cashout;
    @BindView(R.id.trip_details)
    ImageView trip_details;








    private final String TAG = "EarningActivity";
    HashMap<String, String> data = new HashMap<>();
    String currentDateandTime;
    Context context;
    private SessionManager sessionManager;
    private ApiManager apiManager;
    ProgressDialog progressDialog;
    ModelNewDriverEarning modelNewEarnings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_earning_driver);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        sessionManager = new SessionManager(this);
        apiManager = new ApiManager(this,this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        ButterKnife.bind(this);
        setToolbar();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        currentDateandTime = sdf.format(new Date());
        selectedDate.setText(currentDateandTime);
        try {
            callApi();
        } catch (Exception e) {
            e.printStackTrace();
        }

        leftClick.setOnClickListener(this);
        rightClick.setOnClickListener(this);

        cashout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewEarningDriver.this,CashOutActivity.class));
            }
        });


    }


    private void callApi() throws Exception {
        data.clear();
        data.put("date", "" +currentDateandTime);
        apiManager._post(API_S.Tags.NEW_EARNING_DRIVER, API_S.Endpoints.NEW_EARNING_DRIVER, data, sessionManager);
        placeHoder.removeAllViews();
    }

    private void callApiForSelect(String value) throws Exception {
        data.clear();
        data.put("date", "" +value);
        apiManager._post(API_S.Tags.NEW_EARNING_DRIVER, API_S.Endpoints.NEW_EARNING_DRIVER, data, sessionManager);
        placeHoder.removeAllViews();
    }



    private void setToolbar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.earnings));
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        mToolbar.setNavigationOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                onBackPressed();
            }
        });
    }



    @Override
    public void onAPIRunningState(int a, String APINAME) {

        try {

            if (APINAME.equals(API_S.Tags.NEW_EARNING_DRIVER)) {
                if (a == ApiManager.APIFETCHER.KEY_API_IS_STARTED) {
                    progressDialog.show();
                } else if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }catch (Exception e){

        }

    }

    @Override
    public void onFetchComplete(Object script, String APINAME) {
        try{
            switch (APINAME) {
                case API_S.Tags.NEW_EARNING_DRIVER:
                    modelNewEarnings = SingletonGson.getInstance().fromJson("" + script, ModelNewDriverEarning.class);
                    selectedDate.setText(modelNewEarnings.getData().getCurrent_period());

                    tvTotalEarnings.setText(": " +modelNewEarnings.getData().getTotal_earnings().getRight_text());
                    tvTotalEarnings_text.setText(""+ modelNewEarnings.getData().getTotal_earnings().getLeft_text());

                    if(modelNewEarnings.getData().getTrips().isLeft_text_visibility())
                    {
                        l_triphistory.setVisibility(View.VISIBLE);
                        highlighted_text1.setText("" +modelNewEarnings.getData().getTrips().getLeft_text());
//                        highlighted_text1.setTextColor(Color.parseColor("#"+modelNewEarnings.getData().getTrips().getLeft_text_color()));
                        highlighted_text1.setTypeface(null, Typeface.BOLD);
                    }
                    else {
                        l_triphistory.setVisibility(View.GONE);
                    }

                    if(modelNewEarnings.getData().getTrips().isRight_text_visibility())
                    {
                        value_text1.setText(""+modelNewEarnings.getData().getTrips().getRight_text());
//                        value_text1.setTextColor(Color.parseColor("#"+modelNewEarnings.getData().getTrips().getRight_text_color()));
                        value_text1.setTypeface(null, Typeface.BOLD);
                    }


                    if(modelNewEarnings.getData().getTrip_history().isLeft_text_visibility())
                    {
                        l1_trip.setVisibility(View.VISIBLE);
                        trip_history.setText("" +modelNewEarnings.getData().getTrip_history().getLeft_text());
//                        trip_history.setTextColor(Color.parseColor("#"+modelNewEarnings.getData().getTrip_history().getLeft_text_color()));
                        trip_history.setTypeface(null, Typeface.BOLD);
                    }


                    trip_details.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            startActivity(new Intent(NewEarningDriver.this, DailyStatementActivity.class).putExtra(""+ IntentKeys.DATE,""+modelNewEarnings.getData().getCurrent_period_forsend()));
                        }
                    });



                    if(modelNewEarnings.getData().getTotal_amount_collected().isLeft_text_visibility())
                    {
                        l1_wallet.setVisibility(View.VISIBLE);
                        walletAmount.setText("" +modelNewEarnings.getData().getTotal_amount_collected().getLeft_text());
//                        walletAmount.setTextColor(Color.parseColor("#"+modelNewEarnings.getData().getTotal_amount_collected().getLeft_text_color()));
                        walletAmount.setTypeface(null, Typeface.BOLD);
                    }

                    if(modelNewEarnings.getData().getReceived_cash().isLeft_text_visibility())
                    {
                        l1_cash.setVisibility(View.VISIBLE);
                        cash.setText(""+modelNewEarnings.getData().getReceived_cash().getLeft_text());
//                        cash.setTextColor(Color.parseColor("#"+modelNewEarnings.getData().getReceived_cash().getLeft_text_color()));
                        cash.setTypeface(null, Typeface.BOLD);
                    }
                    if(modelNewEarnings.getData().getReceived_cash().isRight_text_visibility())
                    {
                        cash_value.setText("" +modelNewEarnings.getData().getReceived_cash().getRight_text());
//                        cash_value.setTextColor(Color.parseColor("#"+modelNewEarnings.getData().getReceived_cash().getRight_text_color()));
                        cash_value.setTypeface(null, Typeface.BOLD);
                    }

                    if(modelNewEarnings.getData().getReceived_in_wallet().isRight_text_visibility())
                    {
                        l1_wallet_balance.setVisibility(View.VISIBLE);
                        wallet_amount.setText(""+modelNewEarnings.getData().getReceived_in_wallet().getRight_text());
//                        wallet_amount.setTextColor(Color.parseColor("#"+modelNewEarnings.getData().getReceived_in_wallet().getRight_text_color()));
                        wallet_amount.setTypeface(null, Typeface.BOLD);
                    }
                    if(modelNewEarnings.getData().getReceived_in_wallet().isLeft_text_visibility())
                    {
                        wallet.setText(""+modelNewEarnings.getData().getReceived_in_wallet().getLeft_text());
//                        wallet.setTextColor(Color.parseColor("#"+modelNewEarnings.getData().getReceived_in_wallet().getLeft_text_color()));
                        wallet.setTypeface(null, Typeface.BOLD);
                    }
                    if(modelNewEarnings.getData().getWallet_balance().isLeft_text_visibility())
                    {
                        l1_w_amnt.setVisibility(View.VISIBLE);
                        current_wallet_balance.setText(""+modelNewEarnings.getData().getWallet_balance().getLeft_text());
//                        current_wallet_balance.setTextColor(Color.parseColor("#"+modelNewEarnings.getData().getWallet_balance().getLeft_text_color()));
                        current_wallet_balance.setTypeface(null, Typeface.BOLD);
                    }
                    if(modelNewEarnings.getData().getWallet_balance().isRight_text_visibility())
                    {

                        curent_wall_amt.setText(""+modelNewEarnings.getData().getWallet_balance().getRight_text());
//                        curent_wall_amt.setTextColor(Color.parseColor("#"+modelNewEarnings.getData().getWallet_balance().getRight_text_color()));
                        curent_wall_amt.setTypeface(null, Typeface.BOLD);
                    }

                    placeHoder.addView(new HolderShowDriverNetEarning(NewEarningDriver.this, modelNewEarnings.getData().getHolder_data()));



                    break;
            }
        } catch (Exception e){

        }
    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.leftClick:
                try {
                    callApiForSelect("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.rightClick:

                try {

                    openDateFDialog();


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }


    }



    private void openDateFDialog() throws Exception {


        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dpd = DatePickerDialog.newInstance(NewEarningDriver.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//            dpd.setMinDate(calendar);
        dpd.setCancelText("" + getResources().getString(R.string.cancel));
        dpd.setOkText("" + getResources().getString(R.string.ok));
        dpd.setAccentColor(NewEarningDriver.this.getResources().getColor(R.color.colorPrimary));
        dpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });
        dpd.show(getFragmentManager(), "Date Picker Dialog");
    }



    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(year, monthOfYear, dayOfMonth);

        String data = dateFormat.format(cal.getTime());



        //  String data = "" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        selectedDate.setText("" + data);

        try {
            callApiForSelect(data);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
