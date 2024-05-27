package com.tkx.driver;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.DisplayMetrics;

import com.tkx.driver.others.ChatModel;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by cabmedriver 4/1/2017.
 */

public class Config {

    public static String text;
    public static String DriverReference = "Drivers_A";
    public static String DriverEventReference = "driver_event";
    public static String Devicetype = "2";
    public static String GeoFireReference = "geofire";
    public static String ActiveRidesRefrence = "Activeride";
    public static String RideTableReference = "RideTable";
    public static String ChatReferencetable = "Chat";
    public static boolean ReceiverPassengerActivity = false;
    public static boolean RentalReceivepassengerActivity = false ;


    public static List<ChatModel> cities = new ArrayList<ChatModel>(){{
        add(new ChatModel("NOCHAT" , "NO CHAT" , ""+System.currentTimeMillis()));
    }};

    public static float bearingfactor = 0 ;
    public static String Null_Address_Message = "Out Of Network";

    public static int MainScrenZoon = 15 ;
    public static long LocationUpdateTimeinterval = 2000 ;


    public interface ApiKeys{
        String KEY_ARRIVED = "KEY_ARRIVED";
        String KEY_BEGIN_TRIP = "KEY_BEGIN_TRIP";
        String KEY_END_TRIP = "KEY_END_TRIP";
        String KEY_CANCEL_TRIP = "KEY_CANCEL_TRIP";
        String KEY_CANCEL_REASONS = "KEY_CANCEL_REASONS";
        String KEY_View_cities = "KEY_View_cities";
        String KEY_View_car_by_city = "KEY_View_car_by_city";
        String KEY_View_car_Model = "KEY_View_car_Model";
        String KEY_Driver_register = "KEY_Driver_register";
        String KEY_View_done_ride_info = "KEY_View_done_ride_info";
        String KEY_Documents_Submit = "KEY_Documents_Submit";
        String DRIVER_ACTIVE_RIDES = "DRIVER_ACTIVE_RIDES";
        String SOS_REQUEST_NOTIFIER = "Sos_Request";


        String KEY_ONLINE_OFFLINE = "KEY_ONLINE_OFFLINE";
        String KEY_UPDATE_DRIVER_LAT_LONG = "KEY_UPDATE_DRIVER_LAT_LONG";
        String KEY_UPDATE_DEVICE_ID = "KEY_UPDATE_DEVICE_ID";
        String KEY_CALL_SUPPORT = "KEY_CALL_SUPPORT";
        String KEY_RATING_DRIVER = "KEY_RATING_DRIVER";
        String KEY_APP_CONFIG = "KEY_APP_CONFIG";
        String KEY_NEW_RIDE_SYNC = "KEY_NEW_RIDE_SYNC";
        String KEY_VIEW_RIDE_INFO_DRIVER = "KEY_VIEW_RIDE_INFO_DRIVER";

        String KEY_ACEPT_RIDE = "KEY_ACEPT_RIDE";
        String KEY_REJECT_RIDE = "KEY_REJECT_RIDE";
        String KEY_RIDES_HISTORY = "KEY_RIDES_HISTORY";
        String KEY_ABOUT_US = "KEY_ABOUT_US";
        String KEY_TERMS_AND_CONDITION = "KEY_TERMS_AND_CONDITION";
        String KEY_CUSTOMER_SUPPORT = "KEY_CUSTOMER_SUPPORT";
        String KEY_SOS = "KEY_SOS";
        String KEY_VERIFY_OTP = "KEY_VERIFY_OTP";

        /////// account module keys
        String KEY_NEW_LOGIN = "KEY_NEW_LOGIN";
        String KEY_SIGN_UP_STEP_ONE = "KEY_SIGN_UP_STEP_ONE";
        String KEY_SIGNUP_TWO = "KEY_SIGNUP_TWO";
        String KEY_SIGNUP_THREE = "KEY_SIGNUP_THREE";
        String KEY_GET_ALL = "KEY_GET_ALL";

        String KEY_DOCUMENT_LIST = "KEY_DOCUMENT_LIST";
        String KEY_DOCUMENT_UPLOAD = "KEY_DOCUMENT_UPLOAD";
        String KEY_GET_SIGNUP_STAP_FOUR = "KEY_GET_SIGNUP_STAP_FOUR";
        String KEY_DOCUMENTS_UPLOAD_LIST = "KEY_DOCUMENTS_UPLOAD_LIST";


        //////////////////   rest apis
        String KEY_REST_RIDE_SYNC = "KEY_REST_RIDE_SYNC";
        String KEY_REST_RIDE_INFO = "KEY_REST_RIDE_INFO";
        String KEY_RESt_ACCEPT_API = "KEY_RESt_ACCEPT_API";
        String KEY_REST_REJECT_RIDE = "KEY_REST_REJECT_RIDE";
        String KEY_REST_RIDE_ARRIVED = "KEY_REST_RIDE_ARRIVED";
        String KEY_REST_START_RIDE = "KEY_REST_START_RIDE";
        String KEY_REST_END_RIDE = "KEY_REST_END_RIDE";

        String DELIVERY_PLACE = "KEY_REST_DELIVERY_PLACE";

        String KEY_REST_DONE_RIDE_INFO = "KEY_REST_DONE_RIDE_INFO";
        String KEY_REST_RATING = "KEY_REST_RATING";
        String KEY_REST_RIDE_HISTORY = "KEY_REST_RIDE_HISTORY";
        String KEY_REST_UPCOMING = "KEY_REST_UPCOMING";
        String KEY_REST_RIDE_DETAILS = "KEY_REST_RIDE_DETAILS";
        String KEY_REST_CANCEl_RIDE = "KEY_REST_CANCEl_RIDE";

        String AUTO_UPGRADE = "AUTO_UPGRADE";

        String KEY_WEEKLY_EARNING = "KEY_WEEKLY_EARNING";
        String KEY_DAILY_EARNING = "KEY_DAILY_EARNING";
        String KEY_DAILY_EARNING_RENTAL = "KEY_DAILY_EARNING_RENTAL";
        String KEY_CHANGE_DESTINATION  = "KEY_CHANGE_DESTINATION";
        String KEY_REST_NOTIFICATIONS = "KEY_REST_NOTIFICATIONS";

        String KEY_FORGOTPASS_OTP = "KEY_FORGOTPASS_OTP";
        String KEY_FORGOTPASS_CONFIRMPASS = "KEY_FORGOTPASS_CONFIRMPASS";
        String APP_VERSIONS= "APP_VERSIONS";
        String MANUAL_RIDE = "manual_ride";
        String PARTIAL_ACCEPT = "PARTIAL_ACCEPT";
        String KEY_SCHEDULE_AND_UPDATED  ="KEY_SCHEDULE_AND_UPDATED";
        String CHECK_RIDE_TIME = "CHECK_RIDE_TIME";
        String KEY_UPDATE_DRIVER_LAT_LONG_BACKGROUND = "KEY_UPDATE_DRIVER_LAT_LONG_BACKGROUND";
        String KEY_REPORT_ISSUE = "KEY_REPORT_ISSUE";

        String KEY_WALLET_BALANCE = "KEY_WALLET_BALANCE";

        String LOGOUT = "LOGOUT";
        String KEY_DEMO_USER = "KEY_DEMO_USER";


        String APP_CONFIGURATION = "APP_CONFIGURATION";
    }


    public interface IntentKeys {
      String DOCUMENT_ID = "document_id";
        String RIDE_ID= "ride_id";

        String PUBLIC_KEY ="publicKey";
        String SECRET_KEY="secretKey";

        String TERMS_CONDITION = "termscondition";

    }


//////////////////////////// config attributes

    public static int DistanceGap_tail = 100 ; //


    public static int getScreenWidth (Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return  width;
    }


    public static String getEncodedApiKey(Context context){
        byte[] decrypt= Base64.decode(""+BuildConfig.google_map_key_encoded, Base64.DEFAULT);
        try {
            text = new String(decrypt, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return text;
    }


    public static int getScreenheight (Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        return  height ;
    }

    public interface Status{
        String VAL_1 = "1";  // viagem reservada pelo usuário
        String NORMAL_CANCEL_BY_USER = "2";  // viagem cancelada pelo usuário
        String NORMAL_ACCEPTED = "3";  // aceito pelo motorista
        String VAL_4 = "4";  // Viagem cancelada pelo motorista e tentando alocá-la para outro
        String NORMAL_ARRIVED = "5";  // motorista chegou na porta
        String NORMAL_STARTED = "6";  //  Viagem iniciada
        String NORMAL_RIDE_END = "7";  //  Viagem encerrada pelo motorista
        String NORMAL_LATER = "8";  // quando o usuário reservou uma viagem para mais tarde
        String NORMAL_CANCEL_BY_DRIVER = "9";  // viagem cancelada pelo motorista
        String NORMAL_RIDE_CANCEl_BY_ADMIN = "17";  // Viagem normal cancelada pelo administrador
        
        String NORMAL_DELIVERY_PLACE = "20"; // No local de coleta

        // para abordagem de aluguel
        String RENTAL_BOOKED = "10";  // viagem reservada pelo usuário
        String RENTAL_ACCEPTED = "11";  // reserva de aluguel aceita pelo motorista
        String RENTAL_ARRIVED = "12";  // motorista de aluguel chegou
        String RENTAl_RIDE_STARTED = "13";  // viagem de aluguel iniciada pelo motorista
        String RENTAL_RIDE_REJECTED = "14";  // Viagem de aluguel rejeitado pelo motorista
        String RENTAL_RIDE_CANCEL_BY_USER = "15";  // viagem de aluguel cancelada pelo usuário
        String RENTAL_RIDE_END = "16";  // passeio de aluguel finalizado pelo motorista
        String RENTAL_RIDE_CANCEl_BY_ADMIN = "19";  // viagem de aluguel cancelada pelo administrador
        String RENTAL_RIDE_CANCELLED_BY_DRIVER = "18";  // passeio de aluguel finalizado pelo motorista
        String PARTIAL_ACCEPTED = "22";
        String RIDE_LATER_BOOKING= "52";
        String RIDE_LATER_REASSIGNED = "54";
    }
    public static String getStatustext(String val ){
        if(val.equals(""+Status.VAL_1)){
            return  "Booked";
        } if(val.equals(""+Status.NORMAL_CANCEL_BY_USER)){
            return  "Cancelled by user";
        } if(val.equals(""+Status.NORMAL_ACCEPTED)){
            return  "Accepted";
        } if(val.equals(""+Status.VAL_4)){
            return  "Rejected";
        } if(val.equals(""+Status.NORMAL_ARRIVED)){
            return  "Arrived";
        } if(val.equals(""+Status.NORMAL_STARTED)){
            return  "Riding Now";
        } if(val.equals(""+Status.NORMAL_RIDE_END)){
            return "Completed";
        } if(val.equals(""+Status.NORMAL_DELIVERY_PLACE)){
            return "Delivery Place";
        } if(val.equals(""+Status.NORMAL_LATER)){
            return  "Later Request";
        } if(val.equals(""+Status.NORMAL_CANCEL_BY_DRIVER)){
            return  "Cancelled by You";
        }if(val.equals(""+Status.RENTAL_BOOKED)){
            return  "Rental Booking";
        }if(val.equals(""+Status.RENTAL_ARRIVED)){
            return  "Arrived";
        }if(val.equals(""+Status.RENTAl_RIDE_STARTED)){
            return  "Riding Now";
        }if(val.equals(""+Status.RENTAL_ACCEPTED)){
            return  "Accepted";
        }if(val.equals(""+Status.RENTAL_RIDE_END)){
            return  "Completed";
        }if(val.equals(""+Status.RENTAL_RIDE_REJECTED)){
            return  "Rejected By You";
        }if(val.equals(""+Status.RENTAL_RIDE_CANCEL_BY_USER)){
            return  "User Cancelled";
        }if(val.equals(""+Status.RENTAL_RIDE_CANCELLED_BY_DRIVER)){
            return  "You Cancelled";
        }if(val.equals(""+Status.PARTIAL_ACCEPTED)){
            return  "Scheduled";
        }if(val.equals(""+Status.NORMAL_RIDE_CANCEl_BY_ADMIN)){
            return  "Cancelled by admin";
        }if(val.equals(""+Status.RENTAL_RIDE_CANCEl_BY_ADMIN)){
            return  "Cancelled By Admin";
        }else {
            return  "Something Went Wrong";
        }

    }

    interface MarkerSets{

        String CarCode_AMBULANCE= "ambulance.png" ;
        String CarCodee_AUTORICKSHAW = "auto-rickshaw.png" ;
        String CarCode_BIKE = "bike.png" ;
        String CarCode_CAR = "car.png" ;
        String CarCode_CARGO = "cargo-truck.png" ;
        String CarCode_VAN= "pickup-van.png" ;
        String CarCode_SCOOTER= "scooter.png" ;
        String CarCode_TRUCK= "truck.png" ;
        String CarCode_CUSTOM= "custom_mark.png" ;
        String CarCode_buttain = "beautitian.png";
        String CarCode_car_old = "car_old.png";
        String CarCode_CARGO_VAN = "cargo_van.png";
        String CarCode_ICONBLACK = "iconblack.png";
        String CarCode_ICONGRAY = "icongray.png";
        String CarCode_physio= "physio.png";
        String CarCode_REDCAR = "red_car.png";
        String CarCode_taxistandard = "taxi_standard.png";
        String CarCode_yellowcar_luxury = "yellow_car_luxury.png";
        String CarCode_electritian = "electritian.png";
        String CarCode_iconblue = "iconblue.png";
        String CarCode_iconred= "iconred.png";
        String CarCode_sedan = "sedan.png";
        String CarCode_taxi = "taxi.png";
        String CarCode_white_car_sedan= "white_car_sedan.png";
        String CarCode_barber = "barber.png";
        String CarCode_black_car_suv= "black_car_suv.png";
        String CarCode_greencar= "green_car.png";
        String CarCode_icondrakblue = "icondarkblue.png";
        String CarCode_map_car = "map_car.png";
        String CarCode_plumber = "plumber.png";
        String CarCode_suv = "suv.png";
        String CarCode_taxi_en_mepa= "taxi_en_mapa.png";
        String CarCode_yellocar = "yellow_car.png";


        int ICON_AMULANCE = R.drawable.ambulance;
        int ICON_AUTORICKSHAW = R.drawable.autorickshaw;
        int ICON_BIKE = R.drawable.bike;
        int ICON_CAR = R.drawable.car;
        int ICON_CARGO = R.drawable.cargotruck;
        int ICON_VAN = R.drawable.cargovan;
        int ICON_SCOOTER = R.drawable.scooter;
        int ICON_TRUCK = R.drawable.truck;
        int ICON_DEFAULT = R.drawable.custom_mark;
        int ICON_CUSTOM = R.drawable.custom_mark;
        int ICON_BEAUTITIAN = R.drawable.beautitian;
        // int ICON_car_old = R.drawable.caroldone;
        int ICON_cargo_van = R.drawable.cargo_van;
        int ICON_iconblack = R.drawable.iconblack;
        int ICON_icongray = R.drawable.icongray;
        int ICON_physio = R.drawable.physio;
        int ICON_redcar = R.drawable.red_car;
        //  int ICON_taxistandarded = R.drawable.taxi_standard;
        int ICON_yellowcar_luxury= R.drawable.yellow_car_luxury;
        int ICON_electritain = R.drawable.electritian;
        int ICON_blue = R.drawable.iconblue;
        int ICON_red = R.drawable.iconred;
        int ICON_sedan = R.drawable.sedan;
        //int ICON_taxi = R.drawable.taxi;
        int ICON_white_car_sedan = R.drawable.white_car_sedan;
        int ICON_barber = R.drawable.barber;
        int ICON_black_car_suv = R.drawable.black_car_suv;
        int ICON_green = R.drawable.green_car;
        int ICON_icondarkblue = R.drawable.icondarkblue;
        // int ICON_mapcar = R.drawable.map_car;
        int ICON_plumber = R.drawable.plumber;
        //int ICON_suv = R.drawable.suv;
        int ICON_taxi_en_mapa = R.drawable.taxi_en_mapa;
        int ICON_yellowcar = R.drawable.yellow_car;
    }

    public static int getMarkerIcon(String CarCode){
        switch (CarCode){
            case MarkerSets.CarCode_buttain:
                return MarkerSets.ICON_BEAUTITIAN;
            case MarkerSets.CarCode_CARGO_VAN:
                return MarkerSets.ICON_cargo_van;
            case MarkerSets.CarCode_ICONBLACK:
                return MarkerSets.ICON_iconblack;
            case MarkerSets.CarCode_ICONGRAY:
                return MarkerSets.ICON_icongray;
            case MarkerSets.CarCode_physio:
                return MarkerSets.ICON_physio;
            case MarkerSets.CarCode_REDCAR:
                return MarkerSets.ICON_redcar;
            case MarkerSets.CarCode_yellowcar_luxury:
                return MarkerSets.ICON_yellowcar_luxury;
            case MarkerSets.CarCode_electritian:
                return MarkerSets.ICON_electritain;
            case MarkerSets.CarCode_iconblue:
                return MarkerSets.ICON_blue;
            case MarkerSets.CarCode_iconred:
                return MarkerSets.ICON_red;
            case MarkerSets.CarCode_sedan:
                return MarkerSets.ICON_sedan;
            case MarkerSets.CarCode_white_car_sedan:
                return MarkerSets.ICON_white_car_sedan;
            case MarkerSets.CarCode_barber:
                return MarkerSets.ICON_barber;
            case MarkerSets.CarCode_black_car_suv:
                return MarkerSets.ICON_black_car_suv;
            case MarkerSets.CarCode_greencar:
                return MarkerSets.ICON_green;
            case MarkerSets.CarCode_icondrakblue:
                return MarkerSets.ICON_icondarkblue;
            case MarkerSets.CarCode_plumber:
                return MarkerSets.ICON_plumber;
            case MarkerSets.CarCode_taxi_en_mepa:
                return MarkerSets.ICON_taxi_en_mapa;
            case MarkerSets.CarCode_yellocar:
                return MarkerSets.ICON_yellowcar;
            case MarkerSets.CarCode_AMBULANCE:
                return MarkerSets.ICON_AMULANCE ;
            case MarkerSets.CarCodee_AUTORICKSHAW:
                return MarkerSets.ICON_AUTORICKSHAW ;
            case MarkerSets.CarCode_BIKE:
                return MarkerSets.ICON_BIKE;
            case MarkerSets.CarCode_CAR:
                return MarkerSets.ICON_CAR;
            case MarkerSets.CarCode_CARGO:
                return MarkerSets.ICON_CARGO;
            case MarkerSets.CarCode_VAN:
                return MarkerSets.ICON_VAN ;
            case MarkerSets.CarCode_SCOOTER:
                return MarkerSets.ICON_SCOOTER ;
            case MarkerSets.CarCode_TRUCK:
                return MarkerSets.ICON_TRUCK ;
            case MarkerSets.CarCode_CUSTOM:
                return MarkerSets.ICON_CUSTOM ;
            default: return MarkerSets.ICON_CAR;
        }
    }


    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
    
    // VishaK
}
