package com.tkx.driver.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tkx.driver.R;
import com.tkx.driver.currentwork.API_S;
import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.samwork.ApiManager;

import java.util.HashMap;

public class JunoPaymentWebView extends AppCompatActivity  implements ApiManager.APIFETCHER {

    WebView webView;
    Handler mHandler;
    String AMOUNT= "",sucess_url = "",fail_url = "";
    SessionManager sessionManager;
    ApiManager apiManagerNew;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juno_payment_web_view);

        sessionManager = new SessionManager(this);

        apiManagerNew = new ApiManager(this, this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));

        AMOUNT = ""+getIntent().getExtras().getString(IntentKeys.TOP_UP_AMOUNT);
        webView = findViewById(R.id.webview);


        webView.setWebViewClient(new MyBrowser());

        String url = getIntent().getStringExtra("url");
        sucess_url = getIntent().getStringExtra("sucess_url");
        fail_url = getIntent().getStringExtra("fail_url");


        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
    }

    @Override
    public void onAPIRunningState(int a, String APINAME) {

    }

    @Override
    public void onFetchComplete(Object script, String APINAME) {

        progressDialog.dismiss();
        Intent output = new Intent();
        output.putExtra("STATUS", "SUCCESS");
        setResult(RESULT_OK, output);
        finish();

    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {

        progressDialog.dismiss();
    }

    private class MyBrowser extends WebViewClient {


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.e("Url",""+ url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.e("Url",""+ url);

            if(url.contains("https://delhitrial.apporioproducts.com/socket/public/api/juno/card-save/success?cardHash")){
//                Intent output = new Intent();
//                output.putExtra("STATUS", "SUCCESS");
//                setResult(RESULT_OK, output);
//                finish();

                progressDialog.show();
                HashMap<String, String> datas = new HashMap<>();
                datas.put("type", "2");
                try {
                    apiManagerNew._post("" + API_S.Tags.JUNO_CARD_HASH, "" + API_S.Endpoints.JUNO_CARD_HASH, datas, sessionManager);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(url.equals(""+fail_url)){
                Intent output = new Intent();
                output.putExtra("STATUS", "FAILED");
                setResult(RESULT_OK, output);
                finish();
            }
        }
        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(JunoPaymentWebView.this);
            String message = "SSL Certificate error.";
            switch (error.getPrimaryError()) {
                case SslError.SSL_UNTRUSTED:
                    message = "The certificate authority is not trusted.";
                    break;
                case SslError.SSL_EXPIRED:
                    message = "The certificate has expired.";
                    break;
                case SslError.SSL_IDMISMATCH:
                    message = "The certificate Hostname mismatch.";
                    break;
                case SslError.SSL_NOTYETVALID:
                    message = "The certificate is not yet valid.";
                    break;
            }
            message += " Do you want to continue anyway?";

            builder.setTitle("SSL Certificate Error");
            builder.setMessage(message);
            builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.proceed();
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.cancel();
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}