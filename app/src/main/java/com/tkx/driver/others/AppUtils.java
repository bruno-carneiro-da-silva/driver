package com.tkx.driver.others;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.ActivityCompat;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.tkx.driver.R;
import com.tkx.driver.models.ModelDeviceOnlineIffline;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by cabmedriver 11/9/2017.
 */

public class AppUtils {

    public static InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; ++i)
            {
                if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890]*").matcher(String.valueOf(source.charAt(i))).matches())
                {
                    return "";
                }
            }

            return null;
        }
    };


    public static String uriToBase64(Uri imageUri , Context context) throws Exception {
        final InputStream imageStream = context.getContentResolver().openInputStream(imageUri);
        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
           return encodeImage(selectedImage);
    }


    private static  String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }

    public  static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


    // User validate while creating new User
    public static String validateUserWhileSignUp(String username , String useremail , String userphone , String userpassword){
        String value = "OK" ;
        if(!validateUsername(username).equals("OK")){
            value = validateUsername(username) ;
        }else if (!validatePhone(userphone).equals("OK")){
            value = validatePhone(userphone) ;
        }else if (!validateEmail(useremail).equals("OK")){
            value = validateEmail(useremail) ;
        }else if (!validatePassword(userpassword).equals("OK")){
            value = validatePassword(userpassword) ;
        }
        return value;
    }

    private static String validateEmail (String email){
        if(!TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return "OK" ;
        }else{
            return "Email Not valid";
        }
    }

    private static String validatePhone(String phone){

        if(phone.contains("+") && phone.length()==13){
            return "OK";
        }else{
            return "Phone No Not valid , Should be of 12 digit with <+> sign";
        }
    }

    private static String validatePassword(String password){
        if(password.length() < 6){
            return "Please set atleast 6 digit in pasword";
        }else{
            return  "OK";
        }
    }

    private static String validateUsername (String username ){
        if(username.length() < 1){
            return "Username not Valid";
        }else{
            return "OK";
        }
    }

    public static boolean checkGPSisOnOrNot(Context context){
        LocationManager lm = (LocationManager)context.getSystemService(context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!network_enabled&& !gps_enabled){
            return false;
        }else return true;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static long creatTimeStampViaDate(String myDate  , String Formatter) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(Formatter);
        Date date = sdf.parse(myDate);
        return date.getTime();
    }

    public static  String getDateViaTimestampFormat(long time , String formatter) throws Exception{
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        DateFormat mformatter = new SimpleDateFormat(formatter);
        return mformatter.format(time);
    }


    public static void enterReveal(View myView) {

        int cx = myView.getMeasuredWidth()/2 ;
        int cy = myView.getMeasuredHeight() ;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(myView.getWidth(), myView.getHeight()) / 2;

        // create the animator for this view (the start radius is zero)
        Animator anim =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
        }
        myView.setVisibility(View.VISIBLE);
        anim.start();
    }


    public static void exitReveal(final View myView ) {
        // get the center for the clipping circle
        int cx = myView.getMeasuredWidth() / 2;
        int cy = myView.getMeasuredHeight() ;

        // get the initial radius for the clipping circle
        int initialRadius = myView.getWidth() / 2;

        // create the animation (the final radius is zero)
        Animator anim =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);
        }

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.INVISIBLE);
            }
        });

        // start the animation
        anim.start();
    }

    public static int setViewAccordingToStatus(int status) {
        switch (status) {
            case 0:  // Document not yet uploaded
                return R.string.upload_document;

            case 2:
                return  R.string.verified;

            case 1:
                return R.string.verification_pending;

            case 3:
                return R.string.rejected;

            case 4:
                return R.string.expire;
        }
        return R.string.upload_document;
    }


    public static boolean isMyServiceRunning(Class<?> serviceClass,Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static String getCurrentTagByStatus(ModelDeviceOnlineIffline modelDeviceOnlineIffline){
        // area    service_type    vehicle_type     online_offline(1 online and 2 offline )    free_busy (2 is free 1 busy)

        String services1 = "";
        for (int i = 0; i < modelDeviceOnlineIffline.getData().getSocket_tag().getActive_service_type_id().size(); i++) {
            if (i == (modelDeviceOnlineIffline.getData().getSocket_tag().getActive_service_type_id().size() - 1)) {  // that is last element
                //  services = services + modelvehicleConfiguration.getData().getService_type().get(SELECTED_SERVICES_TYPES.get(i)).getId();
                services1 = services1 + ""
                        + modelDeviceOnlineIffline.getData().getSocket_tag().getCountry_area_id()
                        + "@@" + modelDeviceOnlineIffline.getData().getSocket_tag().getActive_service_type_id().get(i)
                        +"@@" + modelDeviceOnlineIffline.getData().getSocket_tag().getActive_vehicle_type_id()
                        +"@@" + modelDeviceOnlineIffline.getData().getOnline_offline()
                        +"@@" + modelDeviceOnlineIffline.getData().getFree_busy();
            } else {
                //    services = services + modelvehicleConfiguration.getData().getService_type().get(SELECTED_SERVICES_TYPES.get(i)).getId() + ",";
                services1 = services1 + ""
                        + modelDeviceOnlineIffline.getData().getSocket_tag().getCountry_area_id()
                        + "@@" + modelDeviceOnlineIffline.getData().getSocket_tag().getActive_service_type_id().get(i)
                        +"@@" + modelDeviceOnlineIffline.getData().getSocket_tag().getActive_vehicle_type_id()
                        +"@@" + modelDeviceOnlineIffline.getData().getOnline_offline()
                        +"@@" + modelDeviceOnlineIffline.getData().getFree_busy() + "||";
            }
        }

//        return ""
//                + modelDeviceOnlineIffline.getData().getSocket_tag().getCountry_area_id()
//                + "@@" + modelDeviceOnlineIffline.getData().getSocket_tag().getActive_service_type_id().get(0)
//                +"@@" + modelDeviceOnlineIffline.getData().getSocket_tag().getActive_vehicle_type_id()
//                +"@@" + modelDeviceOnlineIffline.getData().getOnline_offline()
//                +"@@" + modelDeviceOnlineIffline.getData().getFree_busy();

        Log.e("@@@Tagsss",""+services1);
        return services1;
    }



    public static String getCurrentTagByStatusIfBusy(ModelDeviceOnlineIffline modelDeviceOnlineIffline,int free_busy){
        // area    service_type    vehicle_type     online_offline(1 online and 2 offline )    free_busy (2 is free 1 busy)

        String services1 = "";
        for (int i = 0; i < modelDeviceOnlineIffline.getData().getSocket_tag().getActive_service_type_id().size(); i++) {
            if (i == (modelDeviceOnlineIffline.getData().getSocket_tag().getActive_service_type_id().size() - 1)) {  // that is last element
                //  services = services + modelvehicleConfiguration.getData().getService_type().get(SELECTED_SERVICES_TYPES.get(i)).getId();
                services1 = services1 + ""
                        + modelDeviceOnlineIffline.getData().getSocket_tag().getCountry_area_id()
                        + "@@" + modelDeviceOnlineIffline.getData().getSocket_tag().getActive_service_type_id().get(i)
                        +"@@" + modelDeviceOnlineIffline.getData().getSocket_tag().getActive_vehicle_type_id()
                        +"@@" + modelDeviceOnlineIffline.getData().getOnline_offline()
                        +"@@" +free_busy;
            } else {
                //    services = services + modelvehicleConfiguration.getData().getService_type().get(SELECTED_SERVICES_TYPES.get(i)).getId() + ",";
                services1 = services1 + ""
                        + modelDeviceOnlineIffline.getData().getSocket_tag().getCountry_area_id()
                        + "@@" + modelDeviceOnlineIffline.getData().getSocket_tag().getActive_service_type_id().get(i)
                        +"@@" + modelDeviceOnlineIffline.getData().getSocket_tag().getActive_vehicle_type_id()
                        +"@@" + modelDeviceOnlineIffline.getData().getOnline_offline()
                        +"@@" + free_busy+ "||";
            }
        }

//        return ""
//                + modelDeviceOnlineIffline.getData().getSocket_tag().getCountry_area_id()
//                + "@@" + modelDeviceOnlineIffline.getData().getSocket_tag().getActive_service_type_id().get(0)
//                +"@@" + modelDeviceOnlineIffline.getData().getSocket_tag().getActive_vehicle_type_id()
//                +"@@" + modelDeviceOnlineIffline.getData().getOnline_offline()
//                +"@@" + modelDeviceOnlineIffline.getData().getFree_busy();

        Log.e("@@@Tagsss",""+services1);
        return services1;
    }



}
