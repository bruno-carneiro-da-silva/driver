package com.tkx.driver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tkx.driver.currentwork.API_S;
import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.models.ModelAddMoney;
import com.tkx.driver.samwork.ApiManager;
import com.tkx.driver.wallet.WalletActivity;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RaveUiManager;
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants;

import java.util.HashMap;
import java.util.Random;

public class FlutterWavePaymentGateway extends AppCompatActivity implements ApiManager.APIFETCHER {

    ProgressDialog progressDialog;
    ApiManager apiManagerNew;
    private SessionManager sessionManager;
    String txRef;
    // String publicKey = "FLWPUBK-e425ad9edaf74406a33e9e616fb55649-X";
    // String encryptionKey = "59636caf40d99b526571c66d";
    // ammy app keys
    // String publicKey = "FLWPUBK-cbe44d79979b9e8e3fe68abbb64bf4cf-X";
    // String encryptionKey = "13154b084941cd9de6437c93";
    // driva
    String publicKey = "FLWPUBK_TEST-386c48ce3faff6ccf4e6b94e399e0d25-X";
    String encryptionKey = "FLWSECK_TEST1b8dc43d0ad5";
    String amount = "";
    String country_code = "";


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                Toast.makeText(this, "TRANSACTION is SUCCESSFULL ", Toast.LENGTH_SHORT).show();

                HashMap<String, String> datas = new HashMap<>();
                datas.put("amount", "" +amount);
                datas.put("payment_method", "2");  // 1 for cash 2 for non cash
                datas.put("receipt_number", "Application");
                datas.put("description", "sending as per demo card only");
                try {
                    apiManagerNew._post("" + API_S.Tags.ADD_MONEY_IN_WALLET, "" + API_S.Endpoints.ADD_MONEY_IN_WALLET, datas, sessionManager);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(this, "ERROR " + message, Toast.LENGTH_SHORT).show();
                Log.e("###",message);
                Intent intent = new Intent(FlutterWavePaymentGateway.this, WalletActivity.class);
                startActivity(intent);
                finish();
            }
            else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(this, "CANCELLED " + message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FlutterWavePaymentGateway.this, WalletActivity.class);
                startActivity(intent);
                finish();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flutter_wave_payment_gateway);
        sessionManager = new SessionManager(this);

        apiManagerNew = new ApiManager(this,this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getResources().getString(R.string.loading));

        amount = ""+getIntent().getExtras().getString(IntentKeys.TOP_UP_AMOUNT);

        for(int i =0;i < sessionManager.getAppConfig().getData().getCountries().size(); i++) {
            // setCountryCodeWithValidation(i);
        }







        Random rand = new Random();
        String rndm = Integer.toString(rand.nextInt())+(System.currentTimeMillis() / 1000L);

        txRef = sessionManager.getUserDetails().get(SessionManager.KEY_DriverEmail) +" "+ rndm;

        /*
        Create instance of RavePayManager
         */
        // new RavePayManager(this).setAmount(Double.parseDouble(amount))
        // .setCountry(country_code)
        // .setCurrency(MainActivity.mModelViewServcesAndCars.getCurrency())
        // .setEmail(sessionManager.getUserDetails().get(SessionManager.USER_EMAIL))
        // .setfName(sessionManager.getUserDetails().get(SessionManager.USER_FIRST_NAME))
        // .setlName(sessionManager.getUserDetails().get(SessionManager.USER_LAST_NAME))
        // .setNarration("payment for TAXI")
        // .setPublicKey(publicKey)
        // .setEncryptionKey(encryptionKey)
        // .setTxRef(txRef)
        // .acceptAccountPayments(true)
        // .acceptCardPayments(true)
        // .acceptMpesaPayments(false)
        // .acceptGHMobileMoneyPayments(false)
        // .onStagingEnv(false).
        // allowSaveCardFeature(false)
        // .withTheme(R.style.DefaultTheme)
        // .initialize();

        new RaveUiManager(this).setAmount(Double.parseDouble(amount))
                .setCurrency(sessionManager.getCurrencyCode())
                .setEmail(sessionManager.getUserDetails().get(SessionManager.KEY_DriverEmail))
                .setfName(sessionManager.getUserDetails().get(SessionManager.KEY_DRIVER_NAME))
                .setlName(sessionManager.getUserDetails().get(SessionManager.KEY_DRIVER_LAST_NAME))
                .setNarration("payment for TAXI")
                .setPublicKey(publicKey)
                .setEncryptionKey(encryptionKey)
                .setTxRef(txRef)
                .setPhoneNumber(sessionManager.getUserDetails().get(SessionManager.KEY_PHONE_CODE)+""+sessionManager.getUserDetails().get(SessionManager.KEY_DRIVER_PHONE), true)
                .acceptAccountPayments(true)
                .acceptCardPayments(true)
                .acceptMpesaPayments(true)
                .acceptAchPayments(true)
                .acceptGHMobileMoneyPayments(true)
                .acceptUgMobileMoneyPayments(true)
                .acceptZmMobileMoneyPayments(true)
                .acceptRwfMobileMoneyPayments(true)
                .acceptSaBankPayments(true)
                .acceptUkPayments(true)
                .acceptBankTransferPayments(true)
                .acceptUssdPayments(true)
                .acceptBarterPayments(true)
                .acceptFrancMobileMoneyPayments(true)
                .allowSaveCardFeature(true)
                .onStagingEnv(true)
                .shouldDisplayFee(true)
                .showStagingLabel(true)
                .initialize();

    }

    @Override
    public void onAPIRunningState(int a, String APINAME) {

    }

    @Override
    public void onFetchComplete(Object script, String APINAME) {

        if (APINAME.equals("" + API_S.Tags.ADD_MONEY_IN_WALLET)) {
            ModelAddMoney modelAddMoney = SingletonGson.getInstance().fromJson("" + script, ModelAddMoney.class);
            Toast.makeText(this, "" + modelAddMoney.getMessage(), Toast.LENGTH_SHORT).show();
            // Intent intent = new Intent();
            // intent.putExtra("ok", "Done");
            // setResult(RESULT_OK, intent);
            Intent intent = new Intent(FlutterWavePaymentGateway.this, WalletActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {

        Toast.makeText(this, ""+script, Toast.LENGTH_SHORT).show();

    }
}