package com.tkx.driver;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.tkx.driver.R;
import com.tkx.driver.baseClass.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationWebViewActivity extends BaseActivity {

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.progressBar)
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_notification_web_view);
        ButterKnife.bind(this);

        webview.setWebChromeClient(new MyWebViewClient());
        progress.setMax(100);
        webview.getSettings().setJavaScriptEnabled(true);
        Log.d("*****weburl", ""+getIntent().getExtras().getString("web_url"));
        try{webview.loadUrl(""+getIntent().getExtras().getString("web_url"));}catch (Exception e){}
        NotificationWebViewActivity.this.progress.setProgress(0);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {finish();}});
    }
    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            NotificationWebViewActivity.this.setValue(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }


    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }

}
