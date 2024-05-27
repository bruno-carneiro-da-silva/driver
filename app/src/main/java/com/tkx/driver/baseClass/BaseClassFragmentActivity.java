package com.tkx.driver.baseClass;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.tkx.driver.FloatingViewService;
import com.tkx.driver.manager.SessionManager;

public class BaseClassFragmentActivity extends FragmentActivity {

    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    SessionManager sessionManager;
    public BaseClassFragmentActivity() {
        // doesn't do anything special
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        showWidgetView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopFloatingView();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    private void showWidgetView() {

        if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("1")) {

            if(sessionManager.getAppConfig().getData().getGeneral_config().isQuick_access_button_visibility()){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

                    // Se a permissão de sorteio não estiver disponível, abra a tela de configurações
                    // para conceder a permissão.
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);

                } else {
                    initializeView();
                }

            }

        }
    }

    public void initializeView() {
        if (sessionManager.getUserDetails().get(SessionManager.KEY_ONLINE_OFFLINE).equals("1")) {
            if(sessionManager.getAppConfig().getData().getGeneral_config().isQuick_access_button_visibility()){
                startService(new Intent(this, FloatingViewService.class));
            }
        }
    }

    private void stopFloatingView() {
        stopService(new Intent(this, FloatingViewService.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
                    //Check if the permission is granted or not.
                    if (resultCode == RESULT_OK) {
                        initializeView();
                    } else { //Permission is not available
                        Toast.makeText(this, "Desenhe sobre outra permissão de aplicativo não disponível. Fechando o aplicativo", Toast.LENGTH_SHORT).show();

                        finish();
                    }
                } else {
                    super.onActivityResult(requestCode, resultCode, data);
                }
    }
}
