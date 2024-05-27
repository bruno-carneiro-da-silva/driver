package com.tkx.driver;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tkx.driver.baseClass.BaseActivity;
import com.tkx.driver.currentwork.API_S;
import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.currentwork.STATUS;
import com.tkx.driver.currentwork.holders.HolderInput;
import com.tkx.driver.currentwork.holders.HolderPaymentPending;
import com.tkx.driver.currentwork.holders.HolderRideFareInfo;
import com.tkx.driver.holder.HolderCashCollection;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.models.ModelCashCollection;
import com.tkx.driver.models.ModelNotificationType;
import com.tkx.driver.models.ModelRateUser;
import com.tkx.driver.models.ModelReceipt;
import com.tkx.driver.models.ModelRideCLose;
import com.tkx.driver.models.ModelRideNotifications;
import com.tkx.driver.samwork.ApiManager;
import com.onesignal.OneSignal;
import com.sam.placer.PlaceHolderView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FareActivity extends BaseActivity implements ApiManager.APIFETCHER, HolderInput.showButton {

    @BindView(R.id.placeholder)
    PlaceHolderView placeholder;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.bottom_text_btn)
    TextView bottomTextBtn;

    private SessionManager sessionManager;
    private ApiManager apiManager;
    private HolderInput holderInput;
    private HolderCashCollection holderCashCollection;
    private String TAG = "FareActivity";
    private ModelReceipt modelReceipt;
    private ProgressDialog progressDialog;
    AlertDialog alertDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_fare);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiManager = new ApiManager(this, this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("" + this.getResources().getString(R.string.loading));

        OneSignal.startInit(FareActivity.this)
                .autoPromptLocation(true)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    callApi();
                } catch (Exception e) {
                    Toast.makeText(FareActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    //  Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    Log.e("" + TAG, "Exception caught while calling API " + e.getMessage());
                }
            }
        });

        bottomTextBtn.setOnClickListener((View view) -> {
            try {
                if (modelReceipt.getData().getBottom_button().getAction().equals("RATE_USER")) {
                    if (modelReceipt.getData().getPayment_holder().getAmount_change()) {
                        onOpenConfirmAmount((modelReceipt.getData().getPayment_holder().getAmount_change_text()));
                    } else {
                        ShowDialogForRateUser();
                    }
                }
                if (modelReceipt.getData().getBottom_button().getAction().equals("INPUT_PRICES")) {
                    HashMap<String, String> data = new HashMap<>();
                    data.put("input_values", "" + holderInput.getInputsFromFields());
                    data.put("booking_id", "" + getIntent().getExtras().getString("" + IntentKeys.BOOKING_ID));
                    apiManager._post(API_S.Tags.RIDE_CLOSE, API_S.Endpoints.RIDE_CLOSE, data, sessionManager);
                }
                if (modelReceipt.getData().getBottom_button().getAction().equals("COMPLETE")) {
                    HashMap<String, String> data = new HashMap<>();
                    data.put("booking_id", "" + getIntent().getExtras().getString("" + IntentKeys.BOOKING_ID));
                    apiManager._post(API_S.Tags.RIDE_CLOSE, API_S.Endpoints.RIDE_CLOSE, data, sessionManager);
                }

            } catch (Exception e) {
                Toast.makeText(FareActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                Log.e("" + TAG, "Exceção capturada ao chamar a API " + e.getMessage());
            }
        });

    }


    private void callApi() throws Exception {
        try {
            placeholder.removeAllViews();
            placeholder.getAdapter().notifyDataSetChanged();
            placeholder.refresh();
        } catch (Exception e) {

        }
        Log.e("CallReceiptApi", "CallReceiptAPi");
        HashMap<String, String> data = new HashMap<>();
        data.put("booking_id", "" + getIntent().getExtras().getString("" + IntentKeys.BOOKING_ID));
        apiManager._post("" + API_S.Tags.RECEIPT, "" + API_S.Endpoints.RECEIPT, data, sessionManager);
    }


    public static void cancelAllNotification(Context ctx) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancelAll();
    }

    @Override
    public void onAPIRunningState(int a, String APINAME) {
        try {
            switch (APINAME) {
                case API_S.Tags.RECEIPT:
                    break;
                case API_S.Tags.RIDE_CLOSE:
                    break;
                case API_S.Tags.RATE_USER:
                    if (a == ApiManager.APIFETCHER.KEY_API_IS_STARTED) {
                        progressDialog.show();
                    } else if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    break;

                case API_S.Tags.CHANGE_AMOUNT:
                    if (a == ApiManager.APIFETCHER.KEY_API_IS_STARTED) {
                        progressDialog.show();
                    } else if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    break;

                case API_S.Tags.CASH_COLLECTION:
                    if (a == ApiManager.APIFETCHER.KEY_API_IS_STARTED) {
                        progressDialog.show();
                    } else if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    break;
            }

            if (a == ApiManager.APIFETCHER.KEY_API_IS_STARTED) {
                swipeRefreshLayout.setRefreshing(true);
            } else {
                swipeRefreshLayout.setRefreshing(false);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onFetchComplete(Object script, String APINAME) {
        try {
            switch (APINAME) {

                case API_S.Tags.CASH_COLLECTION:
                    ModelCashCollection modelCashCollection = SingletonGson.getInstance().fromJson("" + script, ModelCashCollection.class);
                    if (modelCashCollection.getResult() == 1) {
                        callApi();
                    }
                    break;

                case API_S.Tags.CHANGE_AMOUNT:
                    ModelChangeAmount modelChangeAmount = SingletonGson.getInstance().fromJson("" + script, ModelChangeAmount.class);

                    if (modelChangeAmount.getResult().equals("1")) {
                        Toast.makeText(FareActivity.this, "" + modelChangeAmount.getMessage(), Toast.LENGTH_SHORT).show();
                        ShowDialogForRateUser();
                    }

                    break;

                case API_S.Tags.RECEIPT:
                    modelReceipt = SingletonGson.getInstance().fromJson("" + script, ModelReceipt.class);
                    placeholder.addView(new HolderPaymentPending(this, modelReceipt.getData().getPayment_holder()));

                    placeholder.addView(new HolderRideFareInfo(this, this, modelReceipt.getData().getHolder_ride_info()));
                    holderInput = new HolderInput(this, modelReceipt.getData().getHolder_input_info(), this);

                    placeholder.addView(holderInput);
                    bottomTextBtn.setText("" + modelReceipt.getData().getBottom_button().getText());
                    bottomTextBtn.setBackgroundColor(Color.parseColor("#" + modelReceipt.getData().getBottom_button().getText_back_ground_Color()));
                    if (modelReceipt.getData().getBottom_button().isVisibility()) {
                        bottomTextBtn.setVisibility(View.VISIBLE);
                    } else {
                        bottomTextBtn.setVisibility(View.GONE);
                    }
                    try {
                        //   holderCashCollection = new HolderCashCollection(this, modelReceipt.getData().getHolder_driver_ride_payment());
                        if (modelReceipt.getData().getHolder_driver_ride_payment().getVisiblity()) {
                            placeholder.addView(new HolderCashCollection(this, modelReceipt.getData().getHolder_driver_ride_payment()));
                        }
                    } catch (Exception e) {

                    }
                    break;
                case API_S.Tags.RIDE_CLOSE:

                    try {
                        ModelRideCLose modelRideClose = SingletonGson.getInstance().fromJson("" + script, ModelRideCLose.class);
                        //try {
                        //  hideKeyboard();
                        //}catch (Exception e){

                        // }
                        // FareActivity.this.finish();

                        if (sessionManager.getBookingType().equals("2")) {

                            //  startActivity(new Intent(FareActivity.this, MainActivity.class));
                            hideKeyboard();
                            FareActivity.this.finish();

                        } else {

                            hideKeyboard();
                            FareActivity.this.finish();
                        }
                    } catch (Exception e) {

                    }


                    // if (modelRideClose.getData().isRefresh_screen()) {
                    //     callApi();
                    // } else {
                    // }
                    break;
                case API_S.Tags.RATE_USER:
                    ModelRateUser modelRateUser = SingletonGson.getInstance().fromJson("" + script, ModelRateUser.class);
                    HashMap<String, String> data = new HashMap<>();
                    data.put("booking_id", "" + getIntent().getExtras().getString("" + IntentKeys.BOOKING_ID));
                    apiManager._post(API_S.Tags.RIDE_CLOSE, API_S.Endpoints.RIDE_CLOSE, data, sessionManager);
                    break;
            }

        } catch (Exception e) {
            Toast.makeText(FareActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            //  Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            Log.e("" + TAG, "Exceção capturada ao chamar a API " + e.getMessage());
        }
    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {
        Toast.makeText(FareActivity.this, "" + script, Toast.LENGTH_SHORT).show();
    }

    private void ShowDialogForRateUser() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_rate_us, null);

        EditText comments = dialogView.findViewById(R.id.comments);
        RatingBar rating_bar = dialogView.findViewById(R.id.rating_bar);
        Button ll_submit_rating = dialogView.findViewById(R.id.ll_submit_rating);


        ll_submit_rating.setOnClickListener((View view) -> {

            try {
                HashMap<String, String> data = new HashMap<>();
                data.put("booking_id", "" + getIntent().getExtras().getString("" + IntentKeys.BOOKING_ID));
                data.put("rating", "" + rating_bar.getRating());
                data.put("comment", "" + comments.getText().toString());
                apiManager._post(API_S.Tags.RATE_USER, API_S.Endpoints.RATE_USER, data, sessionManager);
                try {
                    hideKeyboard();

                } catch (Exception e) {
                }
                alertDialog.hide();
            } catch (Exception e) {
                Toast.makeText(FareActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                Log.e("" + TAG, "Exceção capturada ao chamar a API " + e.getMessage());
            }
        });

        try {
            dialogBuilder.setView(dialogView);
            alertDialog = dialogBuilder.create();
            alertDialog.show();
        } catch (Exception e) {

        }
    }

    public void hideKeyboard() {
        View view = findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String data) {
        ModelNotificationType modelNotificationType = SingletonGson.getInstance().fromJson("" + data, ModelNotificationType.class);
        if (modelNotificationType.getType() == 1) { // Ride related notification
            try {
                ModelRideNotifications modelRideNotifications = SingletonGson.getInstance().fromJson("" + data, ModelRideNotifications.class);
                if (modelRideNotifications.getData().getBooking_status().equals("" + STATUS.MAKE_PAYMENT)) {
                    callApi();
                }
            } catch (Exception e) {
                Toast.makeText(FareActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                //Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            callApi();
        } catch (Exception e) {
            Toast.makeText(FareActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            //  Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            Log.e("" + TAG, "Exceção capturada ao chamar a API " + e.getMessage());
        }
    }

    @Override
    public void callback() {

        bottomTextBtn.setVisibility(View.VISIBLE);
        bottomTextBtn.setText(getResources().getString(R.string.completed));
    }

    public void onClickYesButton(String dialog_message) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(FareActivity.this);
        builder1.setMessage(dialog_message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                getResources().getString(R.string.ok),
                (dialog, id) -> {

                    try {
                        HashMap<String, String> data = new HashMap<>();
                        data.put("booking_id", "" + getIntent().getExtras().getString("" + IntentKeys.BOOKING_ID));
                        apiManager._post(API_S.Tags.CASH_COLLECTION, API_S.Endpoints.CASH_COLLECTION, data, sessionManager);
                        try {
                            hideKeyboard();

                        } catch (Exception e) {
                        }
                        alertDialog.hide();
                    } catch (Exception e) {
                        // Toast.makeText(FareActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        Log.e("" + TAG, "Exceção capturada ao chamar a API " + e.getMessage());
                    }

                    dialog.cancel();
                });

        builder1.setNegativeButton(
                getResources().getString(R.string.cancel),
                (dialog, id) -> dialog.cancel());

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }


    public void onOpenConfirmAmount(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.add_wallet_rider));

        builder.setPositiveButton("YES", (dialog, which) -> {
            // Não faça nada além de fechar a caixa de diálogo
            openDialogForEnterChangeAmount(message);
            dialog.dismiss();
        });

        builder.setNegativeButton("NO", (dialog, which) -> {
            // Fazer nada
            ShowDialogForRateUser();
            dialog.dismiss();
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    public void onOpenDialogForAmountChange(String message, String amount) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);

        builder.setPositiveButton("YES", (dialog, which) -> {
            // Não faça nada além de fechar a caixa de diálogo

            HashMap<String, String> data = new HashMap<>();
            data.put("amount", "" + amount);
            data.put("booking_id", "" + getIntent().getExtras().getString("" + IntentKeys.BOOKING_ID));
            try {
                apiManager._post(API_S.Tags.CHANGE_AMOUNT, API_S.Endpoints.CHANGE_AMOUNT, data, sessionManager);
            } catch (Exception e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        });

        builder.setNegativeButton("NO", (dialog, which) -> {

            // Fazer nada
            dialog.dismiss();
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    public void openDialogForEnterChangeAmount(String message) {

        // criar um construtor de alertas
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(message);
        // definir o layout personalizado
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_ed_amount_change, null);
        builder.setView(customLayout);
        // definir o layout personalizado
        builder.setPositiveButton(getResources().getString(R.string.submit), (dialog, which) -> {
            // enviar dados do AlertDialog para a Activity
            EditText editText = customLayout.findViewById(R.id.editText);

            // Você trabalha
            onOpenDialogForAmountChange(message, editText.getText().toString());
        });
        // crie e mostre a caixa de diálogo de alerta
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

