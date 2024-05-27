package com.tkx.driver;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.manager.SessionManager;

public class FloatingViewService extends Service {

    private WindowManager mWindowManager;
    private View mFloatingView;

    Context context;

    public FloatingViewService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null);

        context = this;
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }


        // Adicione a visualização à janela.
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        // Especifique a posição de visualização
        params.gravity = Gravity.TOP | Gravity.LEFT; // Inicialmente a visualização será adicionada ao canto superior esquerdo
        params.x = 0;
        params.y = 100;

        // Adicione a visualização à janela
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);

        // O elemento raiz do layout de visualização recolhido
        final View collapsedView = mFloatingView.findViewById(R.id.collapse_view);
        // O elemento raiz do layout de visualização expandida

        // Defina o botão Fechar
        ImageView closeButtonCollapsed = mFloatingView.findViewById(R.id.close_btn);
        // collapsedView.setOnClickListener(view -> {
        // // feche o serviço e remova o da janela
        // Intent intent = new Intent(FloatingViewService.this, MainActivity.class);
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // startActivity(intent);
        //
        //
        // // feche o serviço e remova a visualização da hierarquia de visualizações
        // stopSelf();
        // });


        // Abra o aplicativo neste botão, clique
        RelativeLayout openButton = mFloatingView.findViewById(R.id.root_container);
        openButton.setOnClickListener(view -> {
        // Abra o aplicativo, clique.
            Intent intent = new Intent(FloatingViewService.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            // feche o serviço e remova a visualização da hierarquia de visualizações
            stopSelf();
        });


        // Arraste e mova a visualização flutuante usando a ação de toque do usuário.
        mFloatingView.findViewById(R.id.root_container).setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // lembre-se da posição inicial.
                        initialX = params.x;
                        initialY = params.y;
                        // obter a localização do toque
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initialTouchX);
                        int Ydiff = (int) (event.getRawY() - initialTouchY);


                        // A verificação de Xdiff <10 && YDiff< 10 porque às vezes os elementos se movem um pouco ao clicar.
                        // Então esse é o evento de clique.


                        if (Xdiff < 10 && Ydiff < 10) {
                            // if (isViewCollapsed()) {
                            // // Quando o usuário clica na visualização da imagem do layout recolhido,
                            // // a visibilidade do layout recolhido será alterada para "View.GONE"
                            // // e a visualização expandida ficará visível.
                            // collapsedView.setVisibility(View.GONE);

                            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

                            int sizeStack = am.getRunningTasks(2).size();

                            ComponentName cn = null;
                            for (int i = 0; i < sizeStack; i++) {

                                if (!am.getRunningTasks(2).get(i).topActivity.getClassName().equals("com.android.a1launcher.AndroidOneLauncher")) {
                                    cn = am.getRunningTasks(2).get(i).topActivity;
                                    Log.d("CURRENT Activity ", cn.getClassName());
                                }
                            }


                            if (cn.getClassName().equals("com.android.a1launcher.AndroidOneLauncher")) {

                            } else {
                                if (cn.getClassName().equals("com.tkx.driver.TrackingActivity")) {
                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                    intent.setComponent(new ComponentName("com.tkx.driver", cn.getClassName()));
                                    intent.putExtra(IntentKeys.BOOKING_ID, new SessionManager(context).getbooking_Id());
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }else {
                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                    intent.setComponent(new ComponentName("com.tkx.driver", cn.getClassName()));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }

                            }



                            stopSelf();
// }
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        // Calcule as coordenadas X e Y da visualização.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        // Atualize o layout com as novas coordenadas X e Y
                        mWindowManager.updateViewLayout(mFloatingView, params);
                        return true;
                }
                return false;
            }
        });
    }

    public static void openApp(Context context, String appName, String packageName) {
        if (isAppInstalled(context, packageName))
            if (isAppEnabled(context, packageName))
                context.startActivity(context.getPackageManager().getLaunchIntentForPackage(packageName));
            else
                Toast.makeText(context, appName + " aplicativo não está habilitado.", Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, appName + " aplicativo não está instalado.", Toast.LENGTH_SHORT).show();
    }

    private static boolean isAppInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return false;
    }

    private static boolean isAppEnabled(Context context, String packageName) {
        boolean appStatus = false;
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(packageName, 0);
            if (ai != null) {
                appStatus = ai.enabled;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appStatus;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
    }
}
