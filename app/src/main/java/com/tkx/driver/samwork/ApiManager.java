package com.tkx.driver.samwork;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import com.tkx.driver.BuildConfig;
import com.tkx.driver.Config;
import com.tkx.driver.R;
import com.tkx.driver.SingletonGson;
import com.tkx.driver.SplashActivity;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.models.ResultCheck;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tkx.driver.offlineService.ApiCallback;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.tkx.driver.samwork.ApiManager.APIFETCHER.KEY_API_IS_ERRORED;
import static com.tkx.driver.samwork.ApiManager.APIFETCHER.KEY_API_IS_STARTED;
import static com.tkx.driver.samwork.ApiManager.APIFETCHER.KEY_API_IS_STOPPED;


/**
 * Created by cabmedriver on 30/01/17.
 */

public class ApiManager {

    public static Context context;
    String url;
    HashMap map;
    GsonBuilder gsonBuilder;
    Gson gson;
    APIFETCHER apifetcher;
    SessionManager sessionManager;
    private static final String TAG = "APIExecution";
    String locale = "";

    public ApiManager(APIFETCHER apifetcher, Context context) {
        this.context = context;
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        map = new HashMap();
        this.apifetcher = apifetcher;
    }


    @SuppressLint("LongLogTag")
    public void execution_method_post(final String tag, String url, HashMap<String, String> bodyparameter) {
        bodyparameter.put("language_code", "" + Locale.getDefault().getLanguage());
        Log.d(tag + " **Body API_S Posting parameter ==> ", "" + bodyparameter);
        Log.d(tag + " **Url API_S Url executed ==> ", "" + url);
        apifetcher.onAPIRunningState(KEY_API_IS_STARTED, tag);
        AndroidNetworking.post("" + url)
                .addBodyParameter(bodyparameter)
                .setTag(this)
                .setPriority(Priority.HIGH)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(final JSONObject jsonObject) {
                        Log.d(tag + " **Response Response==> ", "" + jsonObject);
                        apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                        apifetcher.onFetchComplete(jsonObject, tag);
                    }

                    @Override
                    public void onError(ANError anError) {
                        apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                        Log.d("errror", "" + anError.getErrorBody());
                        Log.d("errror", "" + anError.getErrorDetail());
                        Log.d("errror", "" + anError.getMessage());
                        Log.d("error", "" + anError.getStackTrace());
                        Log.d("error", "" + anError.getCause());
                    }
                });
    }

    @SuppressLint("LongLogTag")
    public void execution_method_get(final String tag, String url) {
        Log.d(tag + " **Url API_S Url executed ==> ", "" + url + "&language_code=" + Locale.getDefault().getLanguage());

        apifetcher.onAPIRunningState(KEY_API_IS_STARTED, tag);
        AndroidNetworking.get(url)
                .setTag(this).setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                }).getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(final JSONObject jsonObject) {
                Log.d(tag + " **Response Response==> ", "" + jsonObject);
                apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                apifetcher.onFetchComplete(jsonObject, tag);
            }

            @Override
            public void onError(ANError anError) {
                apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                Log.d("" + TAG, "" + anError.getErrorBody());
                Log.d("" + TAG, "" + anError.getErrorDetail());
                apifetcher.onAPIRunningState(KEY_API_IS_ERRORED, tag);
            }
        });
    }

    public void execution_method_multipart(final String tag, String url, HashMap<String, String> hashmapdetails, String image_key, String image) {
        hashmapdetails.put("language_code", "" + Locale.getDefault().getLanguage());

        Log.d(tag + " **Body API_S Posting parameter ==> ", "" + hashmapdetails);
        Log.d(tag + " **Body(Images) API_S Posting parameter ==> ", image_key + "  " + image);
        Log.d(tag + " **Url API_S Url executed ==> ", "" + url);
        apifetcher.onAPIRunningState(KEY_API_IS_STARTED, tag);
        AndroidNetworking.upload(url)
                .addMultipartParameter(hashmapdetails)
                .addMultipartFile("" + image_key, new File(image))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d(tag + " **Response Response==> ", "" + jsonObject);
                        apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                        apifetcher.onFetchComplete(jsonObject, tag);
                    }

                    @Override
                    public void onError(ANError anError) {
                        apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                        Log.d("errror", "" + anError.getErrorBody());
                        Log.d("errror", "" + anError.getErrorDetail());
                        Log.d("errror", "" + anError.getMessage());
                        Log.d("error", "" + anError.getStackTrace());
                        Log.d("error", "" + anError.getCause());
                    }
                });
    }

    public void _get(final String tag, String url) {

        apifetcher.onAPIRunningState(KEY_API_IS_STARTED, tag);
        AndroidNetworking.get(url)
                .setTag(this).setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                }).getAsJSONObject(new JSONObjectRequestListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(final JSONObject jsonObject) {
                Log.d(tag + " **Response Response==> ", "" + jsonObject);
                apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                apifetcher.onFetchComplete(jsonObject, tag);
            }

            @Override
            public void onError(ANError anError) {
                apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                Log.d("" + TAG, "" + anError.getErrorBody());
                Log.d("" + TAG, "" + anError.getErrorDetail());
                apifetcher.onAPIRunningState(KEY_API_IS_ERRORED, tag);
            }
        });
    }

    public static final Interceptor REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = chain -> {
        Request request = chain.request();
        if (isOnline()) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
        } else {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        }
        Response response = chain.proceed(request);

        System.out.println("network: " + response.networkResponse());
        System.out.println("cache: " + response.cacheResponse());

        return response;
    };

    public static boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    @SuppressLint("LongLogTag")
    public void _post(final String tag, String url, HashMap<String, String> bodyparameter, SessionManager sessionManager) throws Exception {
        Log.d(tag + " **Body API_S Posting parameter ==> ", "" + bodyparameter);
        Log.d(tag + " **Url API_S Url executed ==> ", "" + url);
        Log.d("" + tag, "" + sessionManager.getHeader());
        Log.d("" + tag, "locale:" + sessionManager.getLanguage());

        if(BuildConfig.Build_varient.equals("onetaxidriver")){

            if (sessionManager.getLanguage().equals("pt")){
                locale = "pt";
            }else {
                locale = sessionManager.getLanguage();
            }
        }else {
            locale = sessionManager.getLanguage();
        }


        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 Mi

        TimeUnit time = TimeUnit.MILLISECONDS;
        apifetcher.onAPIRunningState(KEY_API_IS_STARTED, tag);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
                .cache(new Cache(httpCacheDirectory,cacheSize))
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                . writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Log.d(TAG, "Authorization:  "+sessionManager.getHeader());


        apifetcher.onAPIRunningState(KEY_API_IS_STARTED, tag);

        AndroidNetworking.post("" + url)
                .addBodyParameter(bodyparameter)
                .addHeaders(sessionManager.getHeader())
                .addHeaders("Accept","application/json")
                .setMaxAgeCacheControl(10,time)
                .addHeaders("locale", "" + locale)
                .setTag(this)
                .setOkHttpClient(okHttpClient)
                .setPriority(Priority.HIGH)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(final JSONObject jsonObject) {
                        Log.d(tag + " **Response Response==> ", "" + jsonObject);
                        Log.d(tag + " **Response Response==> ", "" + jsonObject.toString().length());
                        ResultCheck resultCheck = SingletonGson.getInstance().fromJson("" + jsonObject, ResultCheck.class);
                        apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                        if (resultCheck.result.equals("0")) {
                            apifetcher.onFetchResultZero("" + resultCheck.message,tag);
                        } else if (resultCheck.result.equals("999")) {
                            logoutUnAuthorizedDriver(sessionManager);  // Excluir driver do painel de administração
                        } else {
                            apifetcher.onFetchComplete(jsonObject, tag);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                        // Toast.makeText(context, "" + anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                        // apifetcher.onFetchResultZero("" + anError.getErrorDetail());
                        Log.d("errror", "" + anError.getErrorBody());
                        Log.d("errror", "" + anError.getErrorDetail());
                        Log.d("errror", "" + anError.getMessage());
                        Log.d("error", "" + anError.getStackTrace());
                        Log.d("error", "" + anError.getCause());
                    }
                });
    }


    @SuppressLint("LongLogTag")
    public void _postIfCached(final String tag, String url, HashMap<String, String> bodyparameter) throws Exception {
        Log.d(tag + " **Body API_S Posting parameter ==> ", "" + bodyparameter);
        Log.d(tag + " **Url API_S Url executed ==> ", "" + url);
        Log.d("" + tag, "" + sessionManager.getHeader());
        Log.d("" + tag, "locale:" + sessionManager.getLanguage());

        if(BuildConfig.Build_varient.equals("onetaxidriver")){

            if (sessionManager.getLanguage().equals("pt")){
                locale = "pt";
            }else {
                locale = sessionManager.getLanguage();
            }
        }else {
            locale = sessionManager.getLanguage();
        }

        apifetcher.onAPIRunningState(KEY_API_IS_STARTED, tag);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .build();

        AndroidNetworking.post("" + url)
                .addBodyParameter(bodyparameter)
                .addHeaders(Config.IntentKeys.PUBLIC_KEY, "" + BuildConfig.PUBLIC_KEY)
                .addHeaders(Config.IntentKeys.SECRET_KEY, "" + BuildConfig.SECRET_KEY)
                .addHeaders("locale", "" + locale)
                .setTag(this)
                .getResponseOnlyIfCached()
                .setOkHttpClient(okHttpClient)
                .setPriority(Priority.HIGH)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(final JSONObject jsonObject) {
                        Log.d(tag + " **Response Response==> ", "" + jsonObject);
                        Log.d(tag + " **Response Response==> ", "" + jsonObject.toString().length());
                        ResultCheck resultCheck = SingletonGson.getInstance().fromJson("" + jsonObject, ResultCheck.class);
                        apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                        if (resultCheck.result.equals("0")) {
                            apifetcher.onFetchResultZero("" + resultCheck.message,tag);
                        } else if (resultCheck.result.equals("999")) {
                            logoutUnAuthorizedDriver(sessionManager);  // Delete Driver from admin panel
                        } else {
                            apifetcher.onFetchComplete(jsonObject, tag);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                        // Toast.makeText(context, "" + anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                        // apifetcher.onFetchResultZero("" + anError.getErrorDetail());
                        Log.d("errror", "" + anError.getErrorBody());
                        Log.d("errror", "" + anError.getErrorDetail());
                        Log.d("errror", "" + anError.getMessage());
                        Log.d("error", "" + anError.getStackTrace());
                        Log.d("error", "" + anError.getCause());
                    }
                });
    }

    @SuppressLint("LongLogTag")
    public void _postForTracking(final String tag, String url, HashMap<String, String> bodyparameter, SessionManager sessionManager) throws Exception {
        Log.d(tag + " **Body API_S Posting parameter ==> ", "" + bodyparameter);
        Log.d(tag + " **Url API_S Url executed ==> ", "" + url);
        Log.d("" + tag, "" + sessionManager.getHeader());
        Log.d("" + tag, "locale:" + sessionManager.getLanguage());

        if(BuildConfig.Build_varient.equals("onetaxidriver")){

            if (sessionManager.getLanguage().equals("pt")){
                locale = "pt";
            }else {
                locale = sessionManager.getLanguage();
            }
        }else {
            locale = sessionManager.getLanguage();
        }

        apifetcher.onAPIRunningState(KEY_API_IS_STARTED, tag);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .build();

        AndroidNetworking.post("" + url)
                .addBodyParameter(bodyparameter)
                .addHeaders(sessionManager.getHeader())
                .addHeaders("locale", "" + locale)
                .setTag(this)
                .setOkHttpClient(okHttpClient)
                .setPriority(Priority.HIGH)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(final JSONObject jsonObject) {
                        Log.d(tag + " **Response Response==> ", "" + jsonObject);
                        Log.d(tag + " **Response Response==> ", "" + jsonObject.toString().length());
                        ResultCheck resultCheck = SingletonGson.getInstance().fromJson("" + jsonObject, ResultCheck.class);
                        apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                        if (resultCheck.result.equals("0")) {
                            apifetcher.onFetchResultZero("" + resultCheck.message,tag);
                        } else {
                            apifetcher.onFetchComplete(jsonObject, tag);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                        //  apifetcher.onFetchResultZero("" + anError.getErrorDetail());
                        Log.d("errror", "" + anError.getErrorBody());
                        Log.d("errror", "" + anError.getErrorDetail());
                        Log.d("errror", "" + anError.getMessage());
                        Log.d("error", "" + anError.getStackTrace());
                        Log.d("error", "" + anError.getCause());
                    }
                });
    }


    @SuppressLint("LongLogTag")
    public void _post_image(final String tag, String url, HashMap<String, String> bodyparameter, HashMap<String, File> fileparams, SessionManager sessionManager) throws Exception {
        Log.d(tag + " **Body API_S Posting parameter ==> ", "" + bodyparameter);
        Log.d(tag + " **Url API_S Url executed ==> ", "" + url);
        Log.d("" + tag, "" + sessionManager.getHeader());
        Log.d("" + tag, "locale:" + locale);

        if(BuildConfig.Build_varient.equals("onetaxidriver")){

            if (sessionManager.getLanguage().equals("pt")){
                locale = "pt";
            }else {
                locale = sessionManager.getLanguage();
            }
        }else {
            locale = sessionManager.getLanguage();
        }
        apifetcher.onAPIRunningState(KEY_API_IS_STARTED, tag);
        AndroidNetworking.upload("" + url)
                .addMultipartFile(fileparams)
                .addQueryParameter(bodyparameter)
                .addHeaders(sessionManager.getHeader())
                .addHeaders("locale", "" + locale).setTag(this)
                .setPriority(Priority.HIGH)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(final JSONObject jsonObject) {
                        Log.d(tag + " **Response Response==> ", "" + jsonObject);
                        ResultCheck resultCheck = SingletonGson.getInstance().fromJson("" + jsonObject, ResultCheck.class);
                        apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                        if (resultCheck.result.equals("0")) {
                            apifetcher.onFetchResultZero("" + resultCheck.message,tag);
                        } else if (resultCheck.result.equals("999")) {
                            logoutUnAuthorizedDriver(sessionManager);  // Delete Driver from admin panel
                        } else {
                            apifetcher.onFetchComplete(jsonObject, tag);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                        Log.d("errror", "" + anError.getErrorBody());
                        Log.d("errror", "" + anError.getErrorDetail());
                        Log.d("errror", "" + anError.getMessage());
                        Log.d("error", "" + anError.getStackTrace());
                        Log.d("error", "" + anError.getCause());
                    }
                });
    }

    @SuppressLint("LongLogTag")
    public void _getgoogleAPI(final String tag, String url) {
        Log.e(tag + " **Url API_S Url executed ==> ", "" + url);

        apifetcher.onAPIRunningState(KEY_API_IS_STARTED, tag);
        AndroidNetworking.get("" + url)
                .setTag(this)
                .setPriority(Priority.HIGH)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onResponse(final JSONObject jsonObject) {
                        Log.e(tag + " **Response Response==> ", "" + jsonObject);
                        ModelResultCheck modelResultCheck = SingletonGson.getInstance().fromJson("" + jsonObject, ModelResultCheck.class);
                        apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                        apifetcher.onFetchComplete(jsonObject, tag);

                    }

                    @Override
                    public void onError(ANError anError) {
                        apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                        if (anError.getErrorDetail().equals("connectionError")) {
                            apifetcher.onFetchResultZero("" + context.getResources().getString(R.string.no_internet_connection),tag);
                        }
                    }
                });
    }


    @SuppressLint("LongLogTag")
    public void _post_with_secretStrings(final String tag, String url, HashMap<String, String> bodyparameter) throws Exception {
        Log.d(tag + " **Body API_S Posting parameter ==> ", "" + bodyparameter);
        Log.d(tag + " **Url API_S Url executed ==> ", "" + url);
        apifetcher.onAPIRunningState(KEY_API_IS_STARTED, tag);
        AndroidNetworking.post("" + url)
                .addBodyParameter(bodyparameter)
                .addHeaders("locale", "" + new SessionManager(context).getLanguage())
                .addHeaders(Config.IntentKeys.PUBLIC_KEY, "" + BuildConfig.PUBLIC_KEY)
                .addHeaders(Config.IntentKeys.SECRET_KEY, "" + BuildConfig.SECRET_KEY)
                .setTag(this)
                .setPriority(Priority.HIGH)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(final JSONObject jsonObject) {
                        Log.d(tag + " **Response Response==> ", "" + jsonObject);
                        ResultCheckString resultCheck = SingletonGson.getInstance().fromJson("" + jsonObject, ResultCheckString.class);
                        apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                        if (resultCheck.result.equals("0")) {
                            apifetcher.onFetchResultZero("" + resultCheck.message,tag);
                        } else if (resultCheck.result.equals("999")) {
                            logoutUnAuthorizedDriver(sessionManager);   // Excluir driver do painel de administração
                        } else {
                            apifetcher.onFetchComplete(jsonObject, tag);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                        Log.d("errror", "" + anError.getErrorBody());
                        Log.d("errror", "" + anError.getErrorDetail());
                        Log.d("errror", "" + anError.getMessage());
                        Log.d("error", "" + anError.getStackTrace());
                        Log.d("error", "" + anError.getCause());
                    }
                });
    }


    @SuppressLint("LongLogTag")
    public void _post_with_secreteonly(final String tag, String url, HashMap<String, String> bodyparameter, ApiCallback callback) throws Exception {
        Log.d(tag + " **Body API_S Posting parameter ==> ", "" + bodyparameter);
        Log.d(tag + " **Url API_S Url executed ==> ", "" + url);

        if(BuildConfig.Build_varient.equals("onetaxidriver")){

            if (new SessionManager(context).getLanguage().equals("pt")){
                locale = "pt";
            }else {
                locale = new SessionManager(context).getLanguage();
            }
        }else {
            locale = new SessionManager(context).getLanguage();
        }
        apifetcher.onAPIRunningState(KEY_API_IS_STARTED, tag);
        AndroidNetworking.post("" + url)
                .addBodyParameter(bodyparameter)
                .addHeaders("locale", "" + locale)
                .addHeaders(Config.IntentKeys.PUBLIC_KEY, "" + BuildConfig.PUBLIC_KEY)
                .addHeaders(Config.IntentKeys.SECRET_KEY, "" + BuildConfig.SECRET_KEY)
                .setTag(this)
                .setPriority(Priority.HIGH)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(final JSONObject jsonObject) {
                        Log.d(tag + " **Response Response==> ", "" + jsonObject);

                        try {
                            ResultCheck resultCheck = SingletonGson.getInstance().fromJson("" + jsonObject, ResultCheck.class);

                            //guardar dados offline aqui


                            apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                            if (resultCheck.result.equals("0")) {
                                apifetcher.onFetchResultZero("" + resultCheck.message,tag);
                            } else if (resultCheck.result.equals("999")) {
                                logoutUnAuthorizedDriver(sessionManager);   // Excluir driver do painel de administração
                            } else {
                                apifetcher.onFetchComplete(jsonObject, tag);

                                if (callback != null) {
                                    callback.onSuccess(jsonObject);
                                }
                            }

                        }catch (Exception e){

                            Log.d("jhks",""+e.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                        Log.d("errror", "" + anError.getErrorBody());
                        Log.d("errror", "" + anError.getErrorDetail());
                        Log.d("errror", "" + anError.getMessage());
                        Log.d("error", "" + anError.getStackTrace());
                        Log.d("error", "" + anError.getCause());

                        if (callback != null) {
                            callback.onError(anError);
                        }
                    }
                });
    }

    @SuppressLint("LongLogTag")
    public void _post_image_with_secreteonly(final String tag, String url, HashMap<String, String> bodyparameter, HashMap<String, File> fileparams) throws Exception {
        Log.d(tag + " **Body API_S Posting parameter ==> ", "" + bodyparameter);
        Log.d(tag + " **Url API_S Url executed ==> ", "" + url);
        apifetcher.onAPIRunningState(KEY_API_IS_STARTED, tag);

        AndroidNetworking.upload("" + url)
                .addMultipartFile(fileparams)
                .addQueryParameter(bodyparameter)
                .addHeaders(Config.IntentKeys.PUBLIC_KEY, "" + BuildConfig.PUBLIC_KEY)
                .addHeaders(Config.IntentKeys.SECRET_KEY, "" + BuildConfig.SECRET_KEY)
                .setTag(this)
                .setPriority(Priority.HIGH)
                .build()
                .setAnalyticsListener((timeTakenInMillis, bytesSent, bytesReceived, isFromCache) -> {
                    Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                    Log.d(TAG, " bytesSent : " + bytesSent);
                    Log.d(TAG, " bytesReceived : " + bytesReceived);
                    Log.d(TAG, " isFromCache : " + isFromCache);
                }).getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(final JSONObject jsonObject) {
                Log.d(tag + " **Response Response==> ", "" + jsonObject);
                ResultCheck resultCheck = SingletonGson.getInstance().fromJson("" + jsonObject, ResultCheck.class);
                apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                if (resultCheck.result.equals("0")) {
                    apifetcher.onFetchResultZero("" + resultCheck.message,tag);
                } else {
                    apifetcher.onFetchComplete(jsonObject, tag);
                }

            }

            @Override
            public void onError(ANError anError) {
                apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                Log.d("errror", "" + anError.getErrorBody());
                Log.d("errror", "" + anError.getErrorDetail());
                Log.d("errror", "" + anError.getMessage());
                Log.d("error", "" + anError.getStackTrace());
                Log.d("error", "" + anError.getCause());
            }
        });

        // .getAsString(new StringRequestListener() {
        //     @Override
        //     public void onResponse(String response) {
        //         JSONObject obj = null;
        //         try {
        //             obj = new JSONObject(response);
        //         } catch (JSONException e) {
        //             e.printStackTrace();
        //         }
        //         ResultCheck resultCheck = SingletonGson.getInstance().fromJson("" + obj, ResultCheck.class);
        //         apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
        //         if (resultCheck.result.equals("0")) {
        //             apifetcher.onFetchResultZero("" + resultCheck.message);
        //         } else {
        //             apifetcher.onFetchComplete(obj, tag);
        //         }
        //     }
        //     @Override
        //     public void onError(ANError anError) {
        //         apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
        //         Log.d("errror", "" + anError.getErrorBody());
        //         Log.d("errror", "" + anError.getErrorDetail());
        //         Log.d("errror", "" + anError.getMessage());
        //         Log.d("error", "" + anError.getStackTrace());
        //         Log.d("error", "" + anError.getCause());
        //     }
        // });
        // .getAsJSONObject(new JSONObjectRequestListener() {
        //     @Override
        //     public void onResponse(final JSONObject jsonObject) {
        //         Log.d(tag + " **Response Response==> ", "" + jsonObject);
        //    ResultCheck resultCheck = SingletonGson.getInstance().fromJson("" + jsonObject, ResultCheck.class);
        //         apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
        //         if (resultCheck.result.equals("0")) {
        //             apifetcher.onFetchResultZero("" + resultCheck.message);
        //         }
        //         else {
        //             apifetcher.onFetchComplete(jsonObject, tag);
        //         }
        //     }
        //     @Override
        //     public void onError(ANError anError) {
        //         apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
        //         Log.d("errror", "" + anError.getErrorBody());
        //         Log.d("errror", "" + anError.getErrorDetail());
        //         Log.d("errror", "" + anError.getMessage());
        //         Log.d("error", "" + anError.getStackTrace());
        //         Log.d("error", "" + anError.getCause());
        //     }
        // });
    }


    public interface APIFETCHER {

        public static int KEY_API_IS_STARTED = 0;
        public static int KEY_API_IS_RUNNING = 1;
        public static int KEY_API_IS_STOPPED = 2;
        public static int KEY_API_IS_ERRORED = 3;


        public static int KEY_ERRORED = 4;

        void onAPIRunningState(int a, String APINAME);  // estado - API_S inicia (0), API_S em execução (1), API_S para (2) API_S Error(3)

        void onFetchComplete(Object script, String APINAME); // Isso dará o script completo

        void onFetchResultZero(String script, String APINAME);

    }


    public void logoutUnAuthorizedDriver(SessionManager sessionManager) {
        sessionManager.logoutUser();
        sessionManager.clearAccessToken("");
        Intent intent = new Intent(context, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
