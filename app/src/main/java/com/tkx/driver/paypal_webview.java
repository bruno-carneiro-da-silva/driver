package com.tkx.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.tkx.driver.currentwork.API_S;
import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.models.ModelAddMoney;
import com.tkx.driver.samwork.ApiManager;
import com.tkx.driver.wallet.WalletActivity;

import java.util.HashMap;

public class paypal_webview extends AppCompatActivity implements ApiManager.APIFETCHER{

    WebView webView;
    Handler mHandler;
    String AMOUNT= "";

    ApiManager apiManagerNew;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_paypal_webview);

        mHandler = new Handler();
        sessionManager = new SessionManager(this);

        apiManagerNew = new ApiManager(this, this);



        AMOUNT = ""+getIntent().getExtras().getString(IntentKeys.TOP_UP_AMOUNT);
        webView = findViewById(R.id.webview);


        webView.setWebViewClient(new MyBrowser());

        String url = getIntent().getStringExtra("data");

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);



        //  webView.addJavascriptInterface(new JccPaymentGateway.PayUJavaScriptInterface(), "JccPaymentGateway");

//        webView.setWebViewClient(new MyWebViewClient() {
//
//            public void onPageFinished(WebView view, final String url) {
//                  Toast.makeText(Paypal_WebView.this, "hello", Toast.LENGTH_SHORT).show();
//            }
//
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//
//                 Toast.makeText(Paypal_WebView.this, "hello2", Toast.LENGTH_SHORT).show();
//
//            }
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                if(url.contains("https://admin.apporioproducts.com/Apporiov21/public/api/jcc/success")){
//
//                    HashMap<String, String> datas = new HashMap<>();
//                    datas.put("amount", "" + AMOUNT);
//                    try {
//                        apiManagerNew._post("" + API_S.Tags.ADD_MONEY_IN_WALLET, "" + API_S.Endpoints.ADD_MONEY_IN_WALLET, datas, sessionManager);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//                else {
//                    Toast.makeText(Paypal_WebView.this, "Something went wrong please try again later", Toast.LENGTH_SHORT).show();
//
//                }
//
//                return true;
//            }
//        });

        // webview_ClientPost(webView, getIntent().getStringExtra("data"));


        // webView.loadUrl(""+getIntent().getExtras().getString("data"));


    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            // Toast.makeText(paypal_webview.this, ""+url, Toast.LENGTH_SHORT).show();
            if(url.equals(BuildConfig.BASE_URL+"api/paypal/success")){
//                Intent intent = new Intent();
//                intent.putExtra("success", "success");
//                setResult(Activity.RESULT_OK, intent);
                HashMap<String, String> datas = new HashMap<>();
                datas.put("amount", "" + AMOUNT);
                datas.put("payment_method", "2");  // 1 for cash 2 for non cash
                datas.put("receipt_number", "Application");
                datas.put("description", "sending as per demo card only");
                try {
                    apiManagerNew._post("" + API_S.Tags.ADD_MONEY_IN_WALLET, "" + API_S.Endpoints.ADD_MONEY_IN_WALLET, datas, sessionManager);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
            }else if(url.equals(BuildConfig.BASE_URL+"api/paypal/fail")){
                Toast.makeText(paypal_webview.this, "Algo deu errado, tente novamente mais tarde", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("failed", "failed");
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
            return true;
        }
    }

    @Override
    public void onAPIRunningState(int a, String APINAME) {

    }

    @Override
    public void onFetchComplete(Object script, String APINAME) {

        if (APINAME.equals("" + API_S.Tags.ADD_MONEY_IN_WALLET)) {
            ModelAddMoneyPaypal modelAddMoneyPaypal = SingletonGson.getInstance().fromJson("" + script, ModelAddMoneyPaypal.class);
            Toast.makeText(this, "" + modelAddMoneyPaypal.getMessage(), Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent();
//            intent.putExtra("ok", "Done");
//            setResult(RESULT_OK, intent);
            Intent intent = new Intent(paypal_webview.this, WalletActivity.class);
            startActivity(intent);
            finish();
        }


    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {

    }


}

