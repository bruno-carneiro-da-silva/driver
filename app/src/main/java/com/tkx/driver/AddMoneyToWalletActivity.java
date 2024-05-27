package com.tkx.driver;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

// import com.tkx.driver.customization.razorpay.RazorPayPaymentGateway;
import com.tkx.driver.activities.MercadoCardPayment;
import com.tkx.driver.activities.ModelPayphoneOnline;
import com.tkx.driver.models.Model_Paypal_Webview;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tkx.driver.baseClass.BaseActivity;
import com.tkx.driver.currentwork.API_S;
import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.customization.CCAvenue.CcVenueModel;
import com.tkx.driver.customization.CCAvenue.WebViewActivity;
import com.tkx.driver.customization.CCAvenue.utility.AvenuesParams;
import com.tkx.driver.customization.CCAvenue.utility.ServiceUtility;
// import com.tkx.driver.customization.Paypal.PaypalPaymentActivity;
// import com.tkx.driver.customization.mpesa.MainMpesa;
// import com.tkx.driver.customization.paytm.PaytmPayment;
// import com.tkx.driver.customization.razorpay.RazorPayPaymentGateway;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.models.ModelAddMoney;
import com.tkx.driver.samwork.ApiManager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMoneyToWalletActivity extends BaseActivity implements ApiManager.APIFETCHER {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.ed_enter_money)
    EditText edEnterMoney;
    @BindView(R.id.am_first_txt)
    TextView amFirstTxt;
    @BindView(R.id.am_second_txt)
    TextView amSecondTxt;
    @BindView(R.id.am_third_txt)
    TextView amThirdTxt;
    @BindView(R.id.txt_add_money)
    Button txtAddMoney;
    @BindView(R.id.root)
    LinearLayout root;
    ApiManager apiManager;
    CcVenueModel ccVenueModel;
    private final String TAG = "AddMoneyToWalletActivity";
    private SessionManager sessionManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_money_to_wallet);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiManager = new ApiManager(this, this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));

        try {
            amFirstTxt.setText("" + sessionManager.getAppConfig().getData().getGeneral_config().getDriver_wallet_package().get(0).getAmount() + " +");
            amSecondTxt.setText("" + sessionManager.getAppConfig().getData().getGeneral_config().getDriver_wallet_package().get(1).getAmount() + " +");
            amThirdTxt.setText("" + sessionManager.getAppConfig().getData().getGeneral_config().getDriver_wallet_package().get(2).getAmount() + " +");
        } catch (Exception e) {
        }

        // try {
        //  txtAddMoney.setVisibility(sessionManager.getAppConfig().getData().getAdd_wallet_money_enable() ? View.VISIBLE : View.GONE);
        //  } catch (Exception e) {
        // }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        amFirstTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    setAmount(Long.parseLong("" + sessionManager.getAppConfig().getData().getGeneral_config().getDriver_wallet_package().get(0).getAmount()));
                } catch (Exception e) {
                    Log.d("" + TAG, "" + e.getMessage());
                }
            }
        });


        amSecondTxt.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
                try {
                    setAmount(Long.parseLong("" + sessionManager.getAppConfig().getData().getGeneral_config().getDriver_wallet_package().get(1).getAmount()));
                } catch (Exception e) {
                    Log.d("" + TAG, "" + e.getMessage());
                }
            }});

        amThirdTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    setAmount(Long.parseLong("" + sessionManager.getAppConfig().getData().getGeneral_config().getDriver_wallet_package().get(2).getAmount()));
                } catch (Exception e) {
                    Log.d("" + TAG, "" + e.getMessage());
                }
            }
        });

        txtAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            // Intent intent = new Intent(AddMoneyToWalletActivity.this, RazorPayPaymentGateway.class)
            // .putExtra("wallet_amount", edEnterMoney.getText().toString())
            // .putExtra("activity", "wallet")
            // .putExtra("currency", "" + sessionManager.getCurrencyCode());
            //onSelectMethodPaymentMethod();

            if (BuildConfig.Build_varient.equals("DudesDudettesdriver")){
                onSelectMethod();
                // HashMap<String, String> datas = new HashMap<>();
                // datas.put("currency",""+sessionManager.getCurrencyCode());
                // datas.put("amount", "" + edEnterMoney.getText().toString());
                // datas.put("booking_id","");
                // try {
                // apiManager._post("" + API_S.Tags.PAYPAL_WEBVIEW, "" + API_S.Endpoints.PAYPAL_WEBVIEW, datas, sessionManager);
                // } catch (Exception e) {
                //    e.printStackTrace();
                // }
                }else if (BuildConfig.Build_varient.equals("nHoradriver")){
                    HashMap<String, String> datas = new HashMap<>();
                    datas.put("amount", "" + edEnterMoney.getText().toString());
                    datas.put("type", "2");  // 1 para dinheiro 2 para não dinheiro
                    try {
                        apiManager._post("" + API_S.Tags.NHORA_PAYMENT, "" + API_S.Endpoints.NHORA_PAYMENT, datas, sessionManager);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if (BuildConfig.Build_varient.equals("drivadriver")){
                    startActivity(new Intent(AddMoneyToWalletActivity.this, FlutterWavePaymentGateway.class)
                        .putExtra("" + IntentKeys.TOP_UP_AMOUNT, "" + edEnterMoney.getText().toString()));
                    finish();
                }
                else if (BuildConfig.Build_varient.equals("takidriver")){
                    HashMap<String, String> datas = new HashMap<>();
                    datas.put("phone", "" + sessionManager.getUserDetails().get(SessionManager.KEY_PHONE_CODE) + sessionManager.getUserDetails().get(SessionManager.KEY_DRIVER_PHONE));
                    datas.put("amount", "" + edEnterMoney.getText().toString());
                    datas.put("type", "2");
                    try {
                        apiManager._post("" + API_S.Tags.MPESA_ADD_MONEY, "" + API_S.Endpoints.MPESA_ADD_MONEY, datas, sessionManager);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (BuildConfig.Build_varient.equals("tkx")){
                    onSelectMethodtkx();
                }
                else {
                    startActivity(new Intent(AddMoneyToWalletActivity.this, CardListActivity.class)
                        .putExtra("ADD_MONEY", "1")
                        .putExtra("" + IntentKeys.TOP_UP_AMOUNT, "" + edEnterMoney.getText().toString()));
                    finish();
                }
            }
        });
    }

    private void selectCCAvenueMethod() {

        if (edEnterMoney.getText().toString().equals("")) {
            Toast.makeText(AddMoneyToWalletActivity.this, R.string.please_enter_valid_amount, Toast.LENGTH_SHORT).show();
        } else {
            String vAccessCode = ServiceUtility.chkNull("AVWG83GA03BW24GWWB").toString().trim();
            String vMerchantId = ServiceUtility.chkNull("187905").toString().trim();
            String vCurrency = ServiceUtility.chkNull(sessionManager.getCurrencyCode()).toString().trim();
            //  String vAmount = ServiceUtility.chkNull(edEnterMoney.getText()).toString().trim();
            String vAmount = ServiceUtility.chkNull(edEnterMoney.getText().toString()).toString().trim();
            if (!vAccessCode.equals("") && !vMerchantId.equals("") && !vCurrency.equals("") && !vAmount.equals("")) {
                Intent intent = new Intent(AddMoneyToWalletActivity.this, WebViewActivity.class);
                intent.putExtra(AvenuesParams.ACCESS_CODE, ServiceUtility.chkNull(vAccessCode).toString().trim());
                intent.putExtra(AvenuesParams.MERCHANT_ID, ServiceUtility.chkNull(vMerchantId).toString().trim());
                intent.putExtra(AvenuesParams.ORDER_ID, ServiceUtility.chkNull(65790).toString().trim());
                intent.putExtra(AvenuesParams.CURRENCY, ServiceUtility.chkNull(vCurrency).toString().trim());
                intent.putExtra(AvenuesParams.AMOUNT, ServiceUtility.chkNull(vAmount).toString().trim());
                intent.putExtra(AvenuesParams.REDIRECT_URL, ServiceUtility.chkNull("" + ccVenueModel.getReturn_url()).toString().trim());
                intent.putExtra(AvenuesParams.CANCEL_URL, ServiceUtility.chkNull("" + ccVenueModel.getCancel_url()).toString().trim());
                intent.putExtra(AvenuesParams.RSA_KEY_URL, ServiceUtility.chkNull("" + ccVenueModel.getRsakey()).toString().trim());
                startActivityForResult(intent, 101);
            }
        }
    }

    public void onSelectMethodPaymentMethod() {
        android.app.AlertDialog.Builder builderSingle = new android.app.AlertDialog.Builder(AddMoneyToWalletActivity.this);
        builderSingle.setTitle(R.string.RIDE_SELECTED_ACTIVITY__payment);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddMoneyToWalletActivity.this, android.R.layout.select_dialog_singlechoice);

        for (int i = 0; i < sessionManager.getAppConfig().getData().getPaymentOption().size(); i++) {
            arrayAdapter.add("" + sessionManager.getAppConfig().getData().getPaymentOption().get(i).getName());
        }

        builderSingle.setNegativeButton(AddMoneyToWalletActivity.this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {

                    if (sessionManager.getAppConfig().getData().getPaymentOption().get(which).getName().equals("RAZORPAY")) {
                    // Intent intent = new Intent(AddMoneyToWalletActivity.this, RazorPayPaymentGateway.class)
                    //   .putExtra("wallet_amount", edEnterMoney.getText().toString())
                    //   .putExtra("activity", "wallet")
                    //   .putExtra("currency", "" + sessionManager.getCurrencyCode());
                    // startActivityForResult(intent, 112);
                    } else if (sessionManager.getAppConfig().getData().getPaymentOption().get(which).getName().equals("M-PESA")) {
                    // Intent intent = new Intent(AddMoneyToWalletActivity.this, MainMpesa.class)
                    //   .putExtra("amount", "" + edEnterMoney.getText().toString())
                    //   .putExtra("phone", "" + sessionManager.getUserDetails().get(SessionManager.KEY_DRIVER_PHONE));
                    //  startActivityForResult(intent, 112);
                    } else if (sessionManager.getAppConfig().getData().getPaymentOption().get(which).getName().equals("CC-AVENUE")) {
                        selectCCAvenueMethod();
                    } else if (sessionManager.getAppConfig().getData().getPaymentOption().get(which).getName().equals("PAYTM")) {

                    // Intent intent = new Intent(AddMoneyToWalletActivity.this, PaytmPayment.class)
                    //         .putExtra("amount",""+edEnterMoney.getText().toString());
                    // startActivityForResult(intent,112);


                    } else if (sessionManager.getAppConfig().getData().getPaymentOption().get(which).getName().equals("PAYTM")) {

                    // Intent intent = new Intent(AddMoneyToWalletActivity.this, PaypalPaymentActivity.class)
                    //         .putExtra("amount",""+edEnterMoney.getText().toString());
                    // startActivityForResult(intent,112);

                    } else {
                        onSelectMethod();
                    }
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.d("" + TAG, "Exceção capturada ao chamar a API " + e.getMessage());
                }
                dialog.dismiss();
            }
        });
        builderSingle.show();
    }


    private void onSelectMethodtkx() {
        if (edEnterMoney.getText().toString().equals("")) {
            Snackbar.make(root, R.string.please_enter_valid_amount, Snackbar.LENGTH_SHORT).show();
        } else {
            if (edEnterMoney.getText().toString().equals("")) {
                Snackbar.make(root, R.string.please_enter_valid_amount, Snackbar.LENGTH_SHORT).show();
            } else {
                final String[] payments = {"Pix", "Mercado Pago", "Pay Pal"};
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(AddMoneyToWalletActivity.this);

                builderSingle.setTitle("" + AddMoneyToWalletActivity.this.getResources().getString(R.string.select_payment_method));

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddMoneyToWalletActivity.this, android.R.layout.select_dialog_singlechoice);

                for (int i = 0; i < payments.length; i++) {
                    arrayAdapter.add("" + payments[i]);
                }

                builderSingle.setNegativeButton("" + "" + AddMoneyToWalletActivity.this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            progressDialog.show();
                            HashMap<String, String> datas = new HashMap<>();
                            datas.put("amount", "" + edEnterMoney.getText().toString());
                            datas.put("type_recharge", "pix");
                            datas.put("payment_method_id", "4");
                            datas.put("payment_option_id", "17");
                            datas.put("calling_from", "DRIVER");
                            try {
                                apiManager._post("" + API_S.Tags.ONLINE_PAYMNET, "" + API_S.Endpoints.ONLINE_PAYMNET, datas, sessionManager);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else if (which == 1){
                            progressDialog.show();
                            HashMap<String, String> datas = new HashMap<>();
                            datas.put("amount", "" + edEnterMoney.getText().toString());
                            datas.put("type_recharge", "cartão");
                            datas.put("payment_method_id", "4");
                            datas.put("payment_option_id", "17");
                            datas.put("calling_from", "DRIVER");
                            try {
                                apiManager._post("" + API_S.Tags.ONLINE_PAYMNET, "" + API_S.Endpoints.ONLINE_PAYMNET, datas, sessionManager);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else if (which == 2) {
                            HashMap<String, String> datas = new HashMap<>();
                            datas.put("currency",""+sessionManager.getCurrencyCode());
                            datas.put("amount", "" + edEnterMoney.getText().toString());
                            datas.put("booking_id","");
                            try {
                                apiManager._post("" + API_S.Tags.PAYPAL_WEBVIEW, "" + API_S.Endpoints.PAYPAL_WEBVIEW, datas, sessionManager);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            // startActivity(new Intent(AddMoneyToWalletActivity.this, CardListActivity.class)
                            // .putExtra("ADD_MONEY", "1")
                            // .putExtra("" + IntentKeys.TOP_UP_AMOUNT, "" + edEnterMoney.getText().toString()));
                            // finish();
                        }else {
                            startActivity(new Intent(AddMoneyToWalletActivity.this, CardListActivity.class).putExtra("" + IntentKeys.TOP_UP_AMOUNT, "" + edEnterMoney.getText().toString()));
                            finish();
                        }
                        dialog.dismiss();
                    }
                });
                builderSingle.show();
            }
        }
    }


    private void onSelectMethod() {
        if (edEnterMoney.getText().toString().equals("")) {
            Snackbar.make(root, R.string.please_enter_valid_amount, Snackbar.LENGTH_SHORT).show();
        } else {
            if (edEnterMoney.getText().toString().equals("")) {
                Snackbar.make(root, R.string.please_enter_valid_amount, Snackbar.LENGTH_SHORT).show();
            } else {
                final String[] payments = {"Bank Card"};
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(AddMoneyToWalletActivity.this);
                builderSingle.setTitle("" + AddMoneyToWalletActivity.this.getResources().getString(R.string.select_payment_method));
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddMoneyToWalletActivity.this, android.R.layout.select_dialog_singlechoice);

                for (int i = 0; i < payments.length; i++) {
                    arrayAdapter.add("" + payments[i]);
                }
                builderSingle.setNegativeButton("" + "" + AddMoneyToWalletActivity.this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            startActivity(new Intent(AddMoneyToWalletActivity.this, CardListActivity.class)
                                .putExtra("ADD_MONEY", "1")
                                .putExtra("" + IntentKeys.TOP_UP_AMOUNT, "" + edEnterMoney.getText().toString()));
                            finish();
                        }
                        //else {
                        //   HashMap<String, String> datas = new HashMap<>();
                        //   datas.put("currency",""+sessionManager.getCurrencyCode());
                        //   datas.put("amount", "" + edEnterMoney.getText().toString());
                        //   datas.put("booking_id","");
                        //   try {
                        //      apiManager._post("" + API_S.Tags.PAYPAL_WEBVIEW, "" + API_S.Endpoints.PAYPAL_WEBVIEW, datas, sessionManager);
                        //     } catch (Exception e) {
                        //          e.printStackTrace();
                        //     }
                        //  }
                        dialog.dismiss();
                    }
                });
                builderSingle.show();
            }
        }
    }


    private void setAmount(double value) throws Exception {
        Double money = 0.0;
        if (!edEnterMoney.getText().toString().equals("")) {
            money = Double.parseDouble("" + edEnterMoney.getText().toString());
        }
        edEnterMoney.setText("" + (money + value));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 112 && resultCode == RESULT_OK) {
            if (data.getStringExtra("success").equals("success")) {
                HashMap<String, String> datas = new HashMap<>();
                datas.put("amount", "" + edEnterMoney.getText().toString());
                datas.put("payment_method", "2");  // 1 para dinheiro 2 para não dinheiro
                datas.put("receipt_number", "Application");
                datas.put("description", "enviando apenas como cartão de demonstração");
                try {
                    apiManager._post("" + API_S.Tags.ADD_MONEY_IN_WALLET, "" + API_S.Endpoints.ADD_MONEY_IN_WALLET, datas, sessionManager);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (data.getStringExtra("failed").equals("failed")) {

            }
        }else if (requestCode == 201 && resultCode == RESULT_OK) {
            if (data.getStringExtra("STATUS").equals("SUCCESS")) {
                HashMap<String, String> datas = new HashMap<>();
                datas.put("amount", "" + edEnterMoney.getText().toString());
                datas.put("payment_method", "2");  // 1 for cash 2 for non cash
                datas.put("receipt_number", "Application");
                datas.put("description", "enviando apenas como cartão de demonstração");
                try {
                    apiManager._post("" + API_S.Tags.ADD_MONEY_IN_WALLET, "" + API_S.Endpoints.ADD_MONEY_IN_WALLET, datas, sessionManager);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (data.getStringExtra("STATUS").equals("FAILED")) {

            }
        }

        else if (requestCode == 100 && resultCode == RESULT_OK) {
            if (data.getStringExtra("success").equals("success")) {
                Intent intent = new Intent();
                intent.putExtra("success", "success");
                setResult(RESULT_OK, intent);
                finish();

            } else if (data.getStringExtra("failed").equals("failed")) {

            }
        }
    }

    @Override
    public void onAPIRunningState(int a, String APINAME) {

    }

    @Override
    public void onFetchComplete(Object script, String APINAME) {
        if (APINAME.equals("" + API_S.Tags.ADD_MONEY_IN_WALLET)) {
            ModelAddMoney modelAddMoney = SingletonGson.getInstance().fromJson("" + script, ModelAddMoney.class);
            Toast.makeText(this, "" + modelAddMoney.getMessage(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("ok", "Done");
            setResult(RESULT_OK, intent);
            finish();
        }
        else if (APINAME.equals(""+API_S.Tags.PAYPAL_WEBVIEW)){
            Model_Paypal_Webview model_paypal_webview = SingletonGson.getInstance().fromJson("" + script, Model_Paypal_Webview.class);
            //Toast.makeText(this, "" + modelAddMoney.getMessage(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddMoneyToWalletActivity.this, paypal_webview.class)
                .putExtra("data", model_paypal_webview.getData()).putExtra(IntentKeys.TOP_UP_AMOUNT,""+edEnterMoney.getText().toString());
            startActivity(intent);
            finish();
        } else if (APINAME.equals(""+API_S.Tags.NHORA_PAYMENT)){
            Modelnhorapayment modelnhorapayment = SingletonGson.getInstance().fromJson("" + script, Modelnhorapayment.class);
            //Toast.makeText(this, "" + modelAddMoney.getMessage(), Toast.LENGTH_SHORT).show();
            infodialog();
        }else if (APINAME.equals(""+API_S.Tags.ONLINE_PAYMNET)){
            progressDialog.dismiss();
            ModelPayphoneOnline modelPayphoneOnline = SingletonGson.getInstance().fromJson("" + script, ModelPayphoneOnline.class);
            startActivityForResult(new Intent(AddMoneyToWalletActivity.this, MercadoCardPayment.class).putExtra("url", "" + modelPayphoneOnline.getData().getUrl()), 201);
        }
    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {
        if (APINAME.equals(""+API_S.Tags.NHORA_PAYMENT)){
            Modelnhorapayment modelnhorapayment = SingletonGson.getInstance().fromJson("" + script, Modelnhorapayment.class);
            Toast.makeText(this, "Something went wrong please try again later", Toast.LENGTH_SHORT).show();
        }else{
            progressDialog.dismiss();
            Toast.makeText(this,""+script, Toast.LENGTH_SHORT).show();
        }
    }

    public void infodialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddMoneyToWalletActivity.this);
        builder.setCancelable(false);
        builder.setTitle("Referência de pagamento gerada");
        builder.setMessage("Aguarde, você será notificado assim que for aprovado")
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
             }
        });
        builder.create().show();
        // is_gps_dialog_shown = true;
    }

}