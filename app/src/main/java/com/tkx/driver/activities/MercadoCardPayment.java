package com.tkx.driver.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tkx.driver.BuildConfig;
import com.tkx.driver.R;

public class MercadoCardPayment extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_mercado_card_payment);
        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new MyBrowser());
        String url = getIntent().getStringExtra("url");
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
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

            if(url.contains(""+ BuildConfig.BASE_URL+"api/process-payment-success")){
                Intent output = new Intent();
                output.putExtra("STATUS", "SUCCESS");
                setResult(RESULT_OK, output);
                finish();
            }else if(url.contains(""+BuildConfig.BASE_URL+"api/process-payment-fail")){
                Intent output = new Intent();
                output.putExtra("STATUS", "FAILED");
                setResult(RESULT_OK, output);
                finish();
            }
        }
        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MercadoCardPayment.this);
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