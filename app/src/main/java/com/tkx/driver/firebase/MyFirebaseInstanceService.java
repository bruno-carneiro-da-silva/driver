package com.tkx.driver.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.iid.FirebaseInstanceId;
// import com.google.firebase.iid.FirebaseInstanceIdService;
// import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseInstanceService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseIIdService";

    @Override
    public void onNewToken(@NonNull String s) {
        // Get updated InstanceID token.
        super.onNewToken(s);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        /*
         * Se você deseja enviar mensagens para esta instância de aplicativo ou
         * gerenciar as assinaturas de aplicativos no lado do servidor, envie o token de
         * ID da instância para o servidor de aplicativos.
         */
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        Log.d("TOKEN ", refreshedToken.toString());
    }
}
