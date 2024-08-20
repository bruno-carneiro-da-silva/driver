package com.tkx.driver.activities.vehicleModule.fragments;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.appcompat.app.AlertDialog;

import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.tkx.driver.DocumentActivity;
import com.tkx.driver.R;
import com.tkx.driver.SingletonGson;
import com.tkx.driver.baseClass.BaseFragment;
import com.tkx.driver.currentwork.API_S;
import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.models.ModelAddVehicle;
import com.tkx.driver.models.ModelVehicleModel;
import com.tkx.driver.models.ModelTraction;
import com.tkx.driver.models.ModelvehicleConfiguration;
import com.tkx.driver.offlineService.ApiCallback;
import com.tkx.driver.others.AppUitls;
import com.tkx.driver.others.AppUtils;
import com.tkx.driver.others.ImageCompressMode;
import com.tkx.driver.samwork.ApiManager;
import com.bumptech.glide.Glide;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;
import com.sampermissionutils.AfterPermissionGranted;
import com.sampermissionutils.EasyPermissions;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class AddVehicleDriver_Activity extends BaseFragment implements ApiManager.APIFETCHER {

    @BindView(R.id.car_type_name)
    TextView carTypeName;
    @BindView(R.id.edt_car_make)
    TextView edtCarMake;
    @BindView(R.id.tv_car_model)
    TextView tvCarModel;
    @BindView(R.id.tv_car_traction)
    TextView tvCarTraction;
    @BindView(R.id.tv_car_color)
    TextView tvCarColor;
    @BindView(R.id.car_type_antt)
    TextView carTypeAntt;
    // @BindView(R.id.tv_ride_category)
    // TextView tvRideCategory;
    @BindView(R.id.number_placet_image)
    LinearLayout numberPlacetImage;
    @BindView(R.id.select_car_image)
    LinearLayout selectCarImage;
    @BindView(R.id.number_plate_image)
    ImageView numberPlateImage;
    @BindView(R.id.vehicle_image)
    ImageView vehicleImage;
    @BindView(R.id.submit_vehicle_button)
    TextView submitVehicleButton;
    @BindView(R.id.car_number_edt)
    EditText carNumberEdt;
    @BindView(R.id.car_capacity_edt)
    EditText carCapacityEdt;
    // @BindView(R.id.car_rntrc_edt)
    // EditText carRntrcEdt;
    @BindView(R.id.country_id_owner)
    TextView country_id_owner;
    // @BindView(R.id.car_color_edt)
    // EditText carColorEdt;
    @BindView(R.id.create_vehicle_info)
    ImageView createVehicleInfo;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.owner_veh_id)
    LinearLayout owner_vehLL;
    @BindView(R.id.ll_babyseat)
    LinearLayout ll_babyseat;
    @BindView(R.id.cb_babyseat)
    CheckBox cb_babyseat;

    private int Selected_Country_position;
    private Bitmap thumbnail;
    String imagePath = "";
    private Uri imageUri;

    @BindView(R.id.owner_chkbx)
    CheckBox ownerCheckBox;

    @BindView(R.id.owner_name_et)
    EditText owner_name_et;
    @BindView(R.id.owner_no_et)
    EditText owner_no_et;
    @BindView(R.id.owner_email_et)
    EditText owner_email_et;
    @BindView(R.id.bank_name_et)
    EditText bank_name_et;
    @BindView(R.id.bank_code_et)
    EditText bank_code_et;
    @BindView(R.id.owner_accno_et)
    EditText owner_accno_et;
    @BindView(R.id.select_cheque_image)
    LinearLayout select_cheque_image;
    @BindView(R.id.cheque_image)
    ImageView cheque_image;
    // @BindView(R.id.ac_non_ac_checkbox)
    // CheckBox ac_non_ac_checkbox;
    // @BindView(R.id.ll_vehicle_ac)
    // LinearLayout ll_vehicle_ac;


    @BindView(R.id.ll_wheel_chair)
    LinearLayout ll_wheel_chair;
    @BindView(R.id.cb_wheel_chair)
    CheckBox cb_wheel_chair;

    @BindView(R.id.llSelectVehicleMake)
    LinearLayout llSelectVehicleMake;
    @BindView(R.id.llSelectVehicleModel)
    LinearLayout llSelectVehicleModel;
    @BindView(R.id.llFieldVehicleMake)
    LinearLayout llFieldVehicleMake;
    @BindView(R.id.llFieldVehicleModel)
    LinearLayout llFieldVehicleModel;
    @BindView(R.id.edtVehicleMake)
    EditText edtVehicleMake;
    @BindView(R.id.edtVehicleModel)
    EditText edtVehicleModel;
    @BindView(R.id.llNoOfSeats)
    LinearLayout llNoOfSeats;
    @BindView(R.id.edtNoOfSeats)
    EditText edtNoOfSeats;

    private ApiManager apiManager;
    SessionManager sessionManager;
    String vehical_ac, babyseat, wheel_chair;


    private static final String TAG = "AddVehicleActivity";

    private String SELECTED_CAR_TYPE = "", SELECTED_CAR_MAKE = "", SELECTED_CAR_MODEL = "", SELECTED_CAR_TRACTION = "", SELECTED_CAR_ANTT = "",  COMPRESSES_IMAGE_NUMBER_PLATE = "", COMPRESSES_IMAGE_VEHICLE = "", COMPRESS_IMAGE_CHEQUE_IMAGE = "", SELECTED_CAR_COLOR = "";

    private int SELECTED_CAR_TYPE_ARRAY_INDEX = 999;
    private ModelvehicleConfiguration modelvehicleConfiguration;
    private ModelVehicleModel modelVehicleModel;
    private ModelTraction modelTraction;
    private ArrayList<Integer> SELECTED_SERVICES_TYPES = new ArrayList<>();


    private static final int RC_CAMERA_PERM = 123;
    private static final int CAMERS_PICKER = 122;
    private static final int GALLERY_PICKER = 124;

    private ContentValues values;
    private ProgressDialog progressDialog;
    int FLAG = 0;


    int SELECTED_IMAGE_TYPE, PICK_FOR_CAR = 1, PICK_FOR_NUMBER_PLATE = 2, PICK_FOR_CHEQUE = 3;

    String area_id, driver_id;
    public static String documentScreenApi;


    public static AddVehicleDriver_Activity newInstance(String area_id, String driver_id, String documentScreenApit) {
        AddVehicleDriver_Activity fragmentFirst = new AddVehicleDriver_Activity();

        Bundle args = new Bundle();
        args.putString(IntentKeys.AREA_ID, area_id);
        args.putString(IntentKeys.DRIVER_ID, driver_id);
        args.putString("documentScreenApi", documentScreenApit);
        fragmentFirst.setArguments(args);

        return fragmentFirst;
    }

    // Armazene variáveis de instância com base em argumentos passados
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    // Aumente a visualização do fragmento com base no layout XML
    @Override
    public View onCreateView(
        LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_vehicle_model, container, false);
        
        ButterKnife.bind(this, view);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(this.getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        apiManager = new ApiManager(this, getActivity());
        sessionManager = new SessionManager(getActivity());
        area_id = getArguments().getString(IntentKeys.AREA_ID);
        driver_id = getArguments().getString(IntentKeys.DRIVER_ID);
        documentScreenApi = getArguments().getString("documentScreenApi");

        // ll_vehicle_ac.setVisibility(sessionManager.getAppConfig().getData().getGeneral_config().isAc_vehicle_enable() ? View.VISIBLE : View.GONE);

        numberPlacetImage.setVisibility(View.VISIBLE);
        selectCarImage.setVisibility(View.VISIBLE);        
        // ownerCheckBox.setVisibility(View.VISIBLE);
        // ll_babyseat.setVisibility(View.VISIBLE);
        // ll_wheel_chair.setVisibility(View.VISIBLE);

        ownerCheckBox.setVisibility(sessionManager.getAppConfig().getData().getGeneral_config().isVehicle_owner() ? View.VISIBLE : View.GONE);
        ll_babyseat.setVisibility(sessionManager.getAppConfig().getData().getGeneral_config().isBaby_seat_enable() ? View.VISIBLE : View.GONE);
        ll_wheel_chair.setVisibility(sessionManager.getAppConfig().getData().getGeneral_config().isWheel_chair_enable() ? View.VISIBLE : View.GONE);

        if (sessionManager.getAppConfig().getData().getGeneral_config().isVehicle_make_text()) {
            llSelectVehicleMake.setVisibility(View.GONE);
            llFieldVehicleMake.setVisibility(View.VISIBLE);
        } else {
            llSelectVehicleMake.setVisibility(View.VISIBLE);
            llFieldVehicleMake.setVisibility(View.GONE);
        }

        if (sessionManager.getAppConfig().getData().getGeneral_config().isVehicle_model_text()) {
            llSelectVehicleModel.setVisibility(View.GONE);
            llFieldVehicleModel.setVisibility(View.VISIBLE);
            llNoOfSeats.setVisibility(View.VISIBLE);
        } else {
            llSelectVehicleModel.setVisibility(View.VISIBLE);
            llFieldVehicleModel.setVisibility(View.GONE);
            llNoOfSeats.setVisibility(View.GONE);
        }


        setCountryCodeWithValidation(0);
        country_id_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
                builderSingle.setTitle(R.string.select_country);
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_singlechoice);
                for (int i = 0; i < sessionManager.getAppConfig().getData().getCountries().size(); i++) {
                    arrayAdapter.add(sessionManager.getAppConfig().getData().getCountries().get(i).getPhonecode() + " " + sessionManager.getAppConfig().getData().getCountries().get(i).getName());
                }
                builderSingle.setNegativeButton(getActivity().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        owner_no_et.setText("");
                        // country_id.setText("" + sessionManager.getAppConfig().getData().getCountries().get(which).getPhonecode());
                        setCountryCodeWithValidation(which);
                        Selected_Country_position = which;
                        dialog.dismiss();
                    }
                });
                builderSingle.show();
            }
        });


        ownerCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    owner_vehLL.setVisibility(View.VISIBLE);
                    owner_vehLL.setAlpha(1.0f);

                    owner_name_et.requestFocus();
                    owner_name_et.setFocusable(true);
                    owner_name_et.setFocusableInTouchMode(true);
                    owner_name_et.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(owner_name_et, InputMethodManager.SHOW_IMPLICIT);

                } else {
                    owner_vehLL.animate()
                            .translationY(0)
                            .alpha(0.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    owner_vehLL.setVisibility(View.GONE);
                                }
                            });
                }
            }
        });

        try {
            HashMap<String, String> data = new HashMap<>();
            data.put("area", "" + area_id);
            apiManager._post_with_secreteonly(API_S.Tags.VEHICLE_CONFIGURATION, API_S.Endpoints.VEHICLE_CONFIGURATION, data, new ApiCallback() {
                @Override
                public void onSuccess(JSONObject response) {

                    Log.i(TAG, "Configuração recebida com sucesso: " + response);
                }

                @Override
                public void onError(ANError error) {

                    Log.e(TAG, "Erro ao buscar configuração: " + error.getMessage());
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "Exception caught while calling api for vehicle configuration " + e.getMessage());
        }


        numberPlacetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPickerDialog(PICK_FOR_NUMBER_PLATE);
                // FLAG = 1;
                // ImagePicker.with(AddVehicleDriver_Activity.this)//  Initialize ImagePicker with activity or fragment context
                // .setToolbarColor("#212121")         //  Toolbar color
                // .setStatusBarColor("#000000")       //  StatusBar color (works with SDK >= 21  )
                // .setToolbarTextColor("#FFFFFF")     //  Toolbar text color (Title and Done button)
                // .setToolbarIconColor("#FFFFFF")     //  Toolbar icon color (Back and Camera button)
                // .setProgressBarColor("#4CAF50")     //  ProgressBar color
                // .setBackgroundColor("#212121")      //  Background color
                // .setCameraOnly(false)               //  Camera mode
                // .setMultipleMode(false)              //  Select multiple images or single image
                // .setFolderMode(false)                //  Folder mode
                // .setShowCamera(true)                //  Show camera button
                // .setFolderTitle("album")           //  Folder title (works with FolderMode = true)
                // .setImageTitle("album")         //  Image title (works with FolderMode = false)
                // .setDoneTitle(AddVehicleDriver_Activity.this.getResources().getString(R.string.done))               //  Done button title
                // .setLimitMessage("You have reached selection limit")    // Selection limit message
                // .setMaxSize(1)                     //  Max images can be selected
                // .setSavePath("ImagePicker")         //  Image capture folder name
                // .setAlwaysShowDoneButton(true)      //  Set always show done button in multiple mode
                // .setRequestCode(100)                //  Set request code, default Config.RC_PICK_IMAGES
                // .setKeepScreenOn(true)              //  Keep screen on when selecting images
                // .start();
            }
        });

        select_cheque_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPickerDialog(PICK_FOR_CHEQUE);
                // FLAG = 2;
                // ImagePicker.with(AddVehicleDriver_Activity.this) //  Initialize ImagePicker with activity or fragment context
                //         .setToolbarColor("#212121")         //  Toolbar color
                //         .setStatusBarColor("#000000")       //  StatusBar color (works with SDK >= 21  )
                //         .setToolbarTextColor("#FFFFFF")     //  Toolbar text color (Title and Done button)
                //         .setToolbarIconColor("#FFFFFF")     //  Toolbar icon color (Back and Camera button)
                //         .setProgressBarColor("#4CAF50")     //  ProgressBar color
                //         .setBackgroundColor("#212121")      //  Background color
                //         .setCameraOnly(false)               //  Camera mode
                //         .setMultipleMode(false)              //  Select multiple images or single image
                //         .setFolderMode(false)                //  Folder mode
                //         .setShowCamera(true)                //  Show camera button
                //         .setFolderTitle("album")           //  Folder title (works with FolderMode = true)
                //         .setImageTitle("album")         //  Image title (works with FolderMode = false)
                //         .setDoneTitle(AddVehicleDriver_Activity.this.getResources().getString(R.string.done))               //  Done button title
                //         .setLimitMessage("You have reached selection limit")    // Selection limit message
                //         .setMaxSize(1)                     //  Max images can be selected
                //         .setSavePath("ImagePicker")         //  Image capture folder name
                //         .setAlwaysShowDoneButton(true)      //  Set always show done button in multiple mode
                //         .setRequestCode(100)                //  Set request code, default Config.RC_PICK_IMAGES
                //         .setKeepScreenOn(true)              //  Keep screen on when selecting images
                //         .start();
            }
        });

        selectCarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPickerDialog(PICK_FOR_CAR);
                // FLAG = 3;
                // ImagePicker.with(AddVehicleDriver_Activity.this) //  Initialize ImagePicker with activity or fragment context
                //         .setToolbarColor("#212121")         //  Toolbar color
                //         .setStatusBarColor("#000000")       //  StatusBar color (works with SDK >= 21  )
                //         .setToolbarTextColor("#FFFFFF")     //  Toolbar text color (Title and Done button)
                //         .setToolbarIconColor("#FFFFFF")     //  Toolbar icon color (Back and Camera button)
                //         .setProgressBarColor("#4CAF50")     //  ProgressBar color
                //         .setBackgroundColor("#212121")      //  Background color
                //         .setCameraOnly(false)               //  Camera mode
                //         .setMultipleMode(false)              //  Select multiple images or single image
                //         .setFolderMode(false)                //  Folder mode
                //         .setShowCamera(true)                //  Show camera button
                //         .setFolderTitle("album")           //  Folder title (works with FolderMode = true)
                //         .setImageTitle("album")         //  Image title (works with FolderMode = false)
                //         .setDoneTitle(AddVehicleDriver_Activity.this.getResources().getString(R.string.done))               //  Done button title
                //         .setLimitMessage("You have reached selection limit")    // Selection limit message
                //         .setMaxSize(1)                     //  Max images can be selected
                //         .setSavePath("ImagePicker")         //  Image capture folder name
                //         .setAlwaysShowDoneButton(true)      //  Set always show done button in multiple mode
                //         .setRequestCode(100)                //  Set request code, default Config.RC_PICK_IMAGES
                //         .setKeepScreenOn(true)              //  Keep screen on when selecting images
                //         .start();
            }
        });


        createVehicleInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.please_select_your_vehicle)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // FIRE ZE MISSILES!
                            }
                        });
                builder.create();
                builder.show();
            }
        });


        carTypeName.setOnClickListener(view12 -> {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
            // builderSingle.setTitle("" + getResources().getString(R.string.please_select_car_type));
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_singlechoice);

            for (int i = 0; i < modelvehicleConfiguration.getData().getVehicle_type().size(); i++) {
                arrayAdapter.add("" + modelvehicleConfiguration.getData().getVehicle_type().get(i).getVehicleTypeName());
            }
            // builderSingle.setNegativeButton("" + "" + getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            //     @Override
            //     public void onClick(DialogInterface dialog, int which) {
            //         dialog.dismiss();
            //     }
            // });
            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    carTypeName.setText("" + modelvehicleConfiguration.getData().getVehicle_type().get(which).getVehicleTypeName());
                    SELECTED_CAR_TYPE = "" + modelvehicleConfiguration.getData().getVehicle_type().get(which).getId();
                    SELECTED_CAR_TYPE_ARRAY_INDEX = which;
                    edtCarMake.setText("" + getResources().getString(R.string.enter_vehicle_make));
                    tvCarModel.setText("" + getResources().getString(R.string.enter_vehicle_model));
                    tvCarTraction.setText("" + getResources().getString(R.string.click_to_select_traction));
                    
                    tvCarColor.setText("" + getResources().getString(R.string.enter_car_color));

                    carTypeAntt.setText("" + getResources().getString(R.string.please_select_antt));
                    // tvRideCategory.setText("" + getResources().getString(R.string.click_to_select));
                    SELECTED_CAR_MAKE = "";
                    SELECTED_CAR_MODEL = "";
                    SELECTED_CAR_TRACTION = "";
                    SELECTED_CAR_COLOR = "";
                    SELECTED_CAR_ANTT = "";
                    SELECTED_SERVICES_TYPES = new ArrayList<>();
                    // callVehiclemakeApi();
                    dialog.dismiss();
                }
            });
            builderSingle.show();
        });

        edtCarMake.setOnClickListener(view1 -> {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
            // builderSingle.setTitle("" + getResources().getString(R.string.please_select_car_make));
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_singlechoice);

            for (int i = 0; i < modelvehicleConfiguration.getData().getVehicle_make().size(); i++) {
                arrayAdapter.add("" + modelvehicleConfiguration.getData().getVehicle_make().get(i).getVehicleMakeName());
            }

            // builderSingle.setNegativeButton("" + "" + getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            //     @Override
            //     public void onClick(DialogInterface dialog, int which) {
            //         dialog.dismiss();
            //     }
            // });
            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    edtCarMake.setText("" + modelvehicleConfiguration.getData().getVehicle_make().get(which).getVehicleMakeName());
                    SELECTED_CAR_MAKE = "" + modelvehicleConfiguration.getData().getVehicle_make().get(which).getId();
                    callVehiclemakeApi();
                    dialog.dismiss();
                }
            });
            builderSingle.show();
        });

        tvCarModel.setOnClickListener(view13 -> {
            if (SELECTED_CAR_MAKE.equals("") || SELECTED_CAR_TYPE.equals("") || modelVehicleModel == null) {
                Toast.makeText(getContext(), getResources().getString(R.string.no_car_model_found), Toast.LENGTH_LONG).show();
            } else {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
                // builderSingle.setTitle("" + getResources().getString(R.string.please_select_car_model));
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice);

                for (int i = 0; i < modelVehicleModel.getData().size(); i++) {
                    arrayAdapter.add("" + modelVehicleModel.getData().get(i).getVehicleModelName());
                }                
                // builderSingle.setNegativeButton("" + "" + getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                //     @Override
                //     public void onClick(DialogInterface dialog, int which) {
                //         dialog.dismiss();
                //     }
                // });
                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvCarModel.setText("" + modelVehicleModel.getData().get(which).getVehicleModelName());
                        SELECTED_CAR_MODEL = "" + modelVehicleModel.getData().get(which).getId();
                        // tvRideCategory.setText("" + getResources().getString(R.string.click_to_select));
                        dialog.dismiss();
                    }
                });
                builderSingle.show();
            }

        });

        tvCarTraction.setOnClickListener(view16 -> {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
            // builderSingle.setTitle("" + getResources().getString(R.string.please_select_car_model));
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice);
            
            for (int i = 0; i < modelvehicleConfiguration.getData().getVehicle_traction().size(); i++) {
                arrayAdapter.add("" + modelvehicleConfiguration.getData().getVehicle_traction().get(i).getVehicleTractionName());
            }                
            // builderSingle.setNegativeButton("" + "" + getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            //     @Override
            //     public void onClick(DialogInterface dialog, int which) {
            //         dialog.dismiss();
            //     }
            // });
            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tvCarTraction.setText("" + modelvehicleConfiguration.getData().getVehicle_traction().get(which).getVehicleTractionName());
                    SELECTED_CAR_TRACTION = "" + modelvehicleConfiguration.getData().getVehicle_traction().get(which).getId();
                    // tvRideCategory.setText("" + getResources().getString(R.string.click_to_select));
                    dialog.dismiss();
                }
            });
            builderSingle.show();
        });

        tvCarColor.setOnClickListener(view18 -> {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());            
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.select_dialog_singlechoice);

            for (int i = 0; i < modelvehicleConfiguration.getData().getVehicle_color().size(); i++) {
                arrayAdapter.add(
                        "" + modelvehicleConfiguration.getData().getVehicle_color().get(i).getVehicleColorName());
            }
            
            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tvCarColor.setText("" + modelvehicleConfiguration.getData().getVehicle_color().get(which)
                            .getVehicleColorName());
                    SELECTED_CAR_COLOR = "" + modelvehicleConfiguration.getData().getVehicle_color().get(which).getId();                    
                    dialog.dismiss();
                }
            });
            builderSingle.show();
        });

        carTypeAntt.setOnClickListener(view17 -> {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
            // builderSingle.setTitle("" + getResources().getString(R.string.please_select_car_model));
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice);
            
            for (int i = 0; i < modelvehicleConfiguration.getData().getVehicle_antt().size(); i++) {
                arrayAdapter.add("" + modelvehicleConfiguration.getData().getVehicle_antt().get(i).getVehicleAnttName());
            }            
            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    carTypeAntt.setText("" + modelvehicleConfiguration.getData().getVehicle_antt().get(which).getVehicleAnttName());
                    SELECTED_CAR_ANTT = "" + modelvehicleConfiguration.getData().getVehicle_antt().get(which).getId();
                    // tvRideCategory.setText("" + getResources().getString(R.string.click_to_select));
                    dialog.dismiss();
                }
            });
            builderSingle.show();
        });

        
        // tvRideCategory.setOnClickListener(view14 -> {

        //     if (SELECTED_CAR_TYPE_ARRAY_INDEX == 999) {
        //         Toast.makeText(getContext(), "Selecione o tipo de veículo primeiro", Toast.LENGTH_LONG).show();
        //     } else {
        //         boolean[] checkedItems = new boolean[modelvehicleConfiguration.getData().getVehicle_type().get(SELECTED_CAR_TYPE_ARRAY_INDEX).getServices().size()];

        //         for (int i = 0; i < modelvehicleConfiguration.getData().getVehicle_type().get(SELECTED_CAR_TYPE_ARRAY_INDEX).getServices().size(); i++) {
        //             if (SELECTED_SERVICES_TYPES.contains(i)) {
        //                 checkedItems[i] = true;
        //             } else {
        //                 checkedItems[i] = false;
        //             }
        //         }

        //         ArrayList<String> services = new ArrayList<>();
        //         for (int i = 0; i < modelvehicleConfiguration.getData().getVehicle_type().get(SELECTED_CAR_TYPE_ARRAY_INDEX).getServices().size(); i++) {
        //             services.add("" + modelvehicleConfiguration.getData().getVehicle_type().get(SELECTED_CAR_TYPE_ARRAY_INDEX).getServices().get(i).getServiceName());
        //         }
        //         String[] stringArray = services.toArray(new String[0]);


        //         AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //         builder.setMultiChoiceItems(stringArray, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
        //             @Override
        //             public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
        //                 if (isChecked) {
        //                     SELECTED_SERVICES_TYPES.add(indexSelected);
        //                 } else if (SELECTED_SERVICES_TYPES.contains(indexSelected)) {
        //                     SELECTED_SERVICES_TYPES.remove(Integer.valueOf(indexSelected));
        //                 }
        //             }
        //         }).setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
        //             @SuppressLint("SetTextI18n")
        //             @Override
        //             public void onClick(DialogInterface dialog, int id) {
        //                 tvRideCategory.setText("" + getResources().getString(R.string.REGISTER_ACTIVITY__select_ride_category));

        //                 for (int i = 0; i < SELECTED_SERVICES_TYPES.size(); i++) {

        //                     if (i == (SELECTED_SERVICES_TYPES.size() - 1)) {  // that is last element
        //                         //   tvRideCategory.setText(tvRideCategory.getText() + "" + modelvehicleConfiguration.getData().getService_type().get(SELECTED_SERVICES_TYPES.get(i)).getServiceName());
        //                         tvRideCategory.setText(tvRideCategory.getText() + "" + modelvehicleConfiguration.getData().getVehicle_type().get(SELECTED_CAR_TYPE_ARRAY_INDEX).getServices().get(SELECTED_SERVICES_TYPES.get(i)).getServiceName());
        //                     } else {
        //                         //   tvRideCategory.setText(tvRideCategory.getText() + "" + modelvehicleConfiguration.getData().getService_type().get(SELECTED_SERVICES_TYPES.get(i)).getServiceName() + ",");
        //                         tvRideCategory.setText(tvRideCategory.getText() + "" + modelvehicleConfiguration.getData().getVehicle_type().get(SELECTED_CAR_TYPE_ARRAY_INDEX).getServices().get(SELECTED_SERVICES_TYPES.get(i)).getServiceName() + ", ");
        //                     }
        //                 }
        //                 tvRideCategory.setText("" + tvRideCategory.getText().toString().replace("" + getResources().getString(R.string.REGISTER_ACTIVITY__select_ride_category), ""));
        //             }
        //         }).setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
        //             @Override
        //             public void onClick(DialogInterface dialog, int id) {
        //                 dialog.dismiss();
        //             }
        //         });

        //         builder.show();
        //     }

        // });

        // ac_non_ac_checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {


        //     if (ac_non_ac_checkbox.isChecked()) {

        //         vehical_ac = "1";

        //     } else {

        //         vehical_ac = "0";

        //     }

        // });

        cb_wheel_chair.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cb_wheel_chair.isChecked()) {
                    wheel_chair = "1";
                } else {
                    wheel_chair = "0";
                }

            }
        });

        cb_babyseat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (cb_babyseat.isChecked()) {
                babyseat = "1";
            } else {
                babyseat = "0";
            }
        });


        submitVehicleButton.setOnClickListener(view15 -> {

            HashMap<String, String> data = new HashMap<>();
            HashMap<String, File> data_image = new HashMap<>();
            
            // if (SELECTED_CAR_TYPE.equals("") || COMPRESSES_IMAGE_NUMBER_PLATE.equals("") || COMPRESSES_IMAGE_VEHICLE.equals("") || SELECTED_CAR_TRACTION.equals("") || SELECTED_CAR_ANTT.equals("") || SELECTED_CAR_COLOR.equals("")) {
            //     Toast.makeText(getContext(), getResources().getString(R.string.required_field_missing), Toast.LENGTH_LONG).show();                
            //     return;
            // }

            if (COMPRESSES_IMAGE_NUMBER_PLATE.equals("") || COMPRESSES_IMAGE_VEHICLE.equals("")) {
                COMPRESSES_IMAGE_NUMBER_PLATE = "1";
                COMPRESSES_IMAGE_VEHICLE = "1";
                // Toast.makeText(getContext(), "Imagem clear", Toast.LENGTH_LONG).show();                
                // return;
            }

            if (SELECTED_CAR_TYPE.equals("") || SELECTED_CAR_TRACTION.equals("") || SELECTED_CAR_ANTT.equals("") || SELECTED_CAR_COLOR.equals("")) {
                Toast.makeText(getContext(), getResources().getString(R.string.required_field_missing),
                        Toast.LENGTH_LONG).show();
                return;
            }
            
            if (!sessionManager.getAppConfig().getData().getGeneral_config().isVehicle_make_text()) {
                if (SELECTED_CAR_MAKE.equals("")) {
                    Toast.makeText(getContext(), getResources().getString(R.string.required_field_missing), Toast.LENGTH_LONG).show();                    
                    return;
                }
            }

            if (sessionManager.getAppConfig().getData().getGeneral_config().isVehicle_owner() && ownerCheckBox.isChecked()) {
                if (owner_name_et.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), getResources().getString(R.string.ownername_empty), Toast.LENGTH_LONG).show();
                    return;
                } else if (owner_no_et.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), getResources().getString(R.string.ownerno_empty), Toast.LENGTH_LONG).show();
                    return;
                } else if (owner_email_et.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), getResources().getString(R.string.owneremail_empty), Toast.LENGTH_LONG).show();
                    return;
                } else if (bank_name_et.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), getResources().getString(R.string.ownerbankname_empty), Toast.LENGTH_LONG).show();
                    return;
                } else if (bank_code_et.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), getResources().getString(R.string.ownerbandcode_empty), Toast.LENGTH_LONG).show();
                    return;
                } else if (owner_accno_et.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), getResources().getString(R.string.owneraccno_empty), Toast.LENGTH_LONG).show();
                    return;
                } else if (COMPRESS_IMAGE_CHEQUE_IMAGE.isEmpty()) {
                    Toast.makeText(getContext(), getResources().getString(R.string.ownerchequeimage_empty), Toast.LENGTH_LONG).show();
                    return;
                }
            }


            data.put("driver_id", "" + driver_id);
            data.put("vehicle_type", "" + SELECTED_CAR_TYPE);


            if (sessionManager.getAppConfig().getData().getGeneral_config().isVehicle_make_text()) {
                data.put("vehicle_make", "" + edtVehicleMake.getText().toString());
            } else {
                data.put("vehicle_make", "" + SELECTED_CAR_MAKE);
            }

            if (sessionManager.getAppConfig().getData().getGeneral_config().isVehicle_model_text()) {
                data.put("vehicle_model", "" + edtVehicleModel.getText().toString());
                data.put("vehicle_seat", "" + edtNoOfSeats.getText().toString());
            } else {
                data.put("vehicle_model", "" + SELECTED_CAR_MODEL);
            }

            data.put("vehicle_traction", "" + SELECTED_CAR_TRACTION);
            data.put("vehicle_color", "" + SELECTED_CAR_COLOR);
            data.put("vehicle_antt", "" + SELECTED_CAR_ANTT);

            data.put("vehicle_number", "" + carNumberEdt.getText().toString());
            data.put("vehicle_capacity", "" + carCapacityEdt.getText().toString());            
            // data.put("vehicle_rntrc", "" + carRntrcEdt.getText().toString());
            data.put("vehicle_rntrc", "");
            // data.put("vehicle_color", "" + carColorEdt.getText().toString());
            // data.put("ac_nonac", vehical_ac);
            data.put("ac_nonac", "1");
            data.put("baby_seat", babyseat);

            if (sessionManager.getAppConfig().getData().getGeneral_config().isWheel_chair_enable()) {
                data.put("wheel_chair", wheel_chair);
            }
            String services = "";
            for (int i = 0; i < SELECTED_SERVICES_TYPES.size(); i++) {
                if (i == (SELECTED_SERVICES_TYPES.size() - 1)) {  // that is last element
                    //  services = services + modelvehicleConfiguration.getData().getService_type().get(SELECTED_SERVICES_TYPES.get(i)).getId();
                    services = services + modelvehicleConfiguration.getData().getVehicle_type().get(SELECTED_CAR_TYPE_ARRAY_INDEX).getServices().get(SELECTED_SERVICES_TYPES.get(i)).getId();
                } else {
                    //    services = services + modelvehicleConfiguration.getData().getService_type().get(SELECTED_SERVICES_TYPES.get(i)).getId() + ",";
                    services = services + modelvehicleConfiguration.getData().getVehicle_type().get(SELECTED_CAR_TYPE_ARRAY_INDEX).getServices().get(SELECTED_SERVICES_TYPES.get(i)).getId() + ",";
                }
            }

            // data.put("service_type", "" + services);
            data.put("service_type", "" + 1);

            data_image.put("vehicle_image", new File(COMPRESSES_IMAGE_VEHICLE));
            data_image.put("vehicle_number_plate_image", new File(COMPRESSES_IMAGE_NUMBER_PLATE));

            if (sessionManager.getAppConfig().getData().getGeneral_config().isVehicle_owner()) {

                if (ownerCheckBox.isChecked()) {

                    data.put("owner_type", "1");
                    data.put("owner_name", "" + owner_name_et.getText().toString().trim());
                    data.put("owner_phone", "" + owner_no_et.getText().toString().trim());
                    data.put("owner_email", "" + owner_email_et.getText().toString().trim());
                    data.put("owner_bank_name", "" + bank_name_et.getText().toString().trim());
                    data.put("owner_bank_code", "" + bank_code_et.getText().toString().trim());
                    data.put("owner_account_number", "" + owner_accno_et.getText().toString());
                    // data.put("bank_check_image", "" + owner_accno_et.getText().toString().trim());

                    data_image.put("bank_check_image", new File(COMPRESS_IMAGE_CHEQUE_IMAGE));

                } else {
                    data.put("owner_type", "2");
                }
            }

            try {
                apiManager._post_image_with_secreteonly(API_S.Tags.ADD_VEHICLE, API_S.Endpoints.ADD_VEHICLE, data, data_image);
            } catch (Exception e) {
                Log.d("" + TAG, "Excreption caught while calling add vehicle API " + e.getMessage());
            }

        });

        return view;

    }

    private void setCountryCodeWithValidation(int position) {

        try {
            sessionManager.setCurrencyCode("" + sessionManager.getAppConfig().getData().getCountries().get(position).getIsoCode(), "" + sessionManager.getAppConfig().getData().getCountries().get(position).getIsoCode());
            country_id_owner.setText("" + sessionManager.getAppConfig().getData().getCountries().get(position).getPhonecode());

            int maxLength = Integer.parseInt("" + sessionManager.getAppConfig().getData().getCountries().get(position).getMaxNumPhone());
            InputFilter[] FilterArray = new InputFilter[1];
            FilterArray[0] = new InputFilter.LengthFilter(maxLength);
            owner_no_et.setFilters(FilterArray);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Nenhum país adicionado", Toast.LENGTH_LONG).show();
        }


    }

    public void callVehiclemakeApi() {
        if (SELECTED_CAR_TYPE.equals("") || SELECTED_CAR_MAKE.equals("")) {

        } else {
            HashMap<String, String> data_selected = new HashMap<>();
            data_selected.put("vehicle_type", "" + SELECTED_CAR_TYPE);
            data_selected.put("vehicle_make", "" + SELECTED_CAR_MAKE);
            try {
                apiManager._post_with_secreteonly(API_S.Tags.VEHICLE_MODEL, API_S.Endpoints.VEHICLE_MODEL, data_selected, new ApiCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        // Tratar a resposta aqui
                        Log.i(TAG, "Configuração recebida com sucesso: " + response);
                        // Fazer algo com o response, como atualizar as configurações locais
                    }

                    @Override
                    public void onError(ANError error) {
                        // Tratar o erro aqui
                        Log.e(TAG, "Erro ao buscar configuração: " + error.getMessage());
                    }
                });
            } catch (Exception e) {
                Log.d(TAG, "Exception caught while calling vehicle make API " + e.getMessage());
            }

        }
    }


    private void openPickerDialog(int type) {
        SELECTED_IMAGE_TYPE = type;
        
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        
        builderSingle.setTitle("Carregar foto");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice);
            arrayAdapter.add("Câmera");
            arrayAdapter.add("Galeria");
            builderSingle.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    try {
                        cameraTask();
                    } catch (Exception e) {
                    }
                } else if (which == 1) {
                    Intent i1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    i1.setType("image/*");
                    startActivityForResult(i1, GALLERY_PICKER);
                }
                dialog.dismiss();
            }
        });
        builderSingle.show();
    }


    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void cameraTask() throws Exception {
        if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.CAMERA)) {
            try { // Tenha permissão? então faça!
                values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "Nova foto");
                values.put(MediaStore.Images.Media.DESCRIPTION, "Da câmera");
                imageUri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CAMERS_PICKER);
            } catch (Exception e) {
            }
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera), RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            cameraTask();
        } catch (Exception e) {
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case Config.RC_PICK_IMAGES :
                    try{
                        ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
                        comppressImageFile(Uri.parse(images.get(0).getPath()));
                    }catch (Exception e){

                    }

                    // CropImage.activity(Uri.fromFile(new File("" + images.get(0).getPath()))).start(getActivity());
                    // if (FLAG == 1){
                    //  numberPlateImage.setImageBitmap(AppUtils.handleSamplingAndRotationBitmap(getContext(), images.get(0).getPath()));
                    //     COMPRESSES_IMAGE_NUMBER_PLATE =images.get(0).getPath();
                    // }else if (FLAG == 2){
                    //     cheque_image.setImageBitmap(AppUtils.handleSamplingAndRotationBitmap(getContext(),images.get(0).getPath()));
                    //     COMPRESS_IMAGE_CHEQUE_IMAGE =images.get(0).getPath();
                    // }else if (FLAG == 3){
                    //     vehicleImage.setImageBitmap(AppUtils.handleSamplingAndRotationBitmap(getContext(), images.get(0).getPath()));
                    //     COMPRESSES_IMAGE_VEHICLE =images.get(0).getPath();
                    // }

                    break;
                case GALLERY_PICKER:
                    if (resultCode != 0) {
                        ImageCompressMode imageCompressMode = new ImageCompressMode(getContext());
                        if (SELECTED_IMAGE_TYPE == PICK_FOR_CAR) {
                            vehicleImage.setImageBitmap(null);
                            vehicleImage.setBackgroundDrawable(null);
                            Glide.with(getContext()).load(data.getData()).into(vehicleImage);
                            COMPRESSES_IMAGE_VEHICLE = imageCompressMode.compressImage(getRealPathFromURI(data.getData()));
                        } else if (SELECTED_IMAGE_TYPE == PICK_FOR_NUMBER_PLATE) {
                            numberPlateImage.setImageBitmap(null);
                            numberPlateImage.setBackgroundDrawable(null);
                            Glide.with(getContext()).load(data.getData()).into(numberPlateImage);
                            COMPRESSES_IMAGE_NUMBER_PLATE = imageCompressMode.compressImage(getRealPathFromURI(data.getData()));
                        } else if (SELECTED_IMAGE_TYPE == PICK_FOR_CHEQUE) {
                            // cheque_image.setImageBitmap(null);
                            // cheque_image.setBackgroundDrawable(null);
                            Glide.with(getContext()).load(data.getData()).into(cheque_image);
                            COMPRESS_IMAGE_CHEQUE_IMAGE = imageCompressMode.compressImage(getRealPathFromURI(data.getData()));
                        }
                    }

                    break;
                case CAMERS_PICKER:

                    thumbnail = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                    ImageCompressMode imageCompressModee = new ImageCompressMode(getContext());
                    
                    if (SELECTED_IMAGE_TYPE == PICK_FOR_CAR) {
                        vehicleImage.setImageBitmap(thumbnail);
                        imagePath = getRealPathFromURI(imageUri);

                        ExifInterface ei = new ExifInterface(imagePath);
                        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_UNDEFINED);

                        Bitmap rotatedBitmap = null;
                        switch (orientation) {

                            case ExifInterface.ORIENTATION_ROTATE_90:
                                rotatedBitmap = rotateImage(thumbnail, 90);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_180:
                                rotatedBitmap = rotateImage(thumbnail, 180);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_270:
                                rotatedBitmap = rotateImage(thumbnail, 270);
                                break;

                            case ExifInterface.ORIENTATION_NORMAL:
                            default:
                                rotatedBitmap = thumbnail;
                        }

                        vehicleImage.setImageBitmap(rotatedBitmap);
                        COMPRESSES_IMAGE_VEHICLE = imageCompressModee.compressImage(imagePath);

                    } else if (SELECTED_IMAGE_TYPE == PICK_FOR_NUMBER_PLATE) {
                        numberPlateImage.setImageBitmap(thumbnail);

                        imagePath = getRealPathFromURI(imageUri);

                        ExifInterface ei = new ExifInterface(imagePath);
                        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_UNDEFINED);

                        Bitmap rotatedBitmap = null;
                        switch (orientation) {

                            case ExifInterface.ORIENTATION_ROTATE_90:
                                rotatedBitmap = rotateImage(thumbnail, 90);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_180:
                                rotatedBitmap = rotateImage(thumbnail, 180);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_270:
                                rotatedBitmap = rotateImage(thumbnail, 270);
                                break;

                            case ExifInterface.ORIENTATION_NORMAL:
                            default:
                                rotatedBitmap = thumbnail;
                        }

                        numberPlateImage.setImageBitmap(rotatedBitmap);

                        COMPRESSES_IMAGE_NUMBER_PLATE = imageCompressModee.compressImage(imagePath);

                    } else if (SELECTED_IMAGE_TYPE == PICK_FOR_CHEQUE) {
                        Glide.with(getContext()).load(MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri)).into(cheque_image);
                        COMPRESS_IMAGE_CHEQUE_IMAGE = imageCompressModee.compressImage(imagePath);
                    }
                    break;
            }
        } catch (Exception e) {
        }

    }


    private void comppressImageFile(Uri uri ){
        Compressor.getDefault(getActivity())
                .compressToFileAsObservable(new File(uri.getPath()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        try{
                            if (FLAG == 1){
                                numberPlateImage.setImageBitmap(AppUitls.handleSamplingAndRotationBitmap(getContext(), file.getPath()));
                                COMPRESSES_IMAGE_NUMBER_PLATE =file.getPath();
                            }else if (FLAG == 2){
                                cheque_image.setImageBitmap(AppUitls.handleSamplingAndRotationBitmap(getContext(), file.getPath()));
                                COMPRESS_IMAGE_CHEQUE_IMAGE =file.getPath();
                            }else if (FLAG == 3){
                                vehicleImage.setImageBitmap(AppUitls.handleSamplingAndRotationBitmap(getContext(), file.getPath()));
                                COMPRESSES_IMAGE_VEHICLE =file.getPath();
                            }
                            // driver_image.setImageBitmap(AppUitls.handleSamplingAndRotationBitmap(RegisterActivity.this, file.getPath()));
                            // DOCUMENT_IMAGE =file.getPath();
                        }catch (Exception e){}
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    // showErrorDialoge("comppressImageFile()",""+throwable.getMessage());
                    }
                });
    }


    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onAPIRunningState(int a, String APINAME) {
        if (a == ApiManager.APIFETCHER.KEY_API_IS_STARTED) {
            progressDialog.show();
        }
        if (a == ApiManager.APIFETCHER.KEY_API_IS_STOPPED) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onFetchComplete(Object script, String APINAME) {
        try {
            switch (APINAME) {
                case API_S.Tags.VEHICLE_CONFIGURATION:
                    modelvehicleConfiguration = SingletonGson.getInstance().fromJson("" + script, ModelvehicleConfiguration.class);
                    break;
                case API_S.Tags.VEHICLE_MODEL:

                    modelVehicleModel = null;
                    modelVehicleModel = SingletonGson.getInstance().fromJson("" + script, ModelVehicleModel.class);
                    tvCarModel.setText(getResources().getString(R.string.please_select_car_model));
                    SELECTED_CAR_MODEL = "";
                    // tvRideCategory.setText("" + getResources().getString(R.string.click_to_select));
                    SELECTED_SERVICES_TYPES = new ArrayList<>();

                    break;
                case API_S.Tags.ADD_VEHICLE:
                    ModelAddVehicle modelAddVehicle = SingletonGson.getInstance().fromJson("" + script, ModelAddVehicle.class);

                    Toast.makeText(getActivity(), "" + modelAddVehicle.getMessage(), Toast.LENGTH_LONG).show();

                    // sessionManager.setVehicleId(String.valueOf(modelAddVehicle.getData().getId()));

                    if (documentScreenApi.equals("1")) {
                        sessionManager.createLoginSession(true,
                                "" + modelAddVehicle.getData().getDriver_id(),
                                ""
                                , ""
                                , ""
                                , ""
                                , ""
                                , ""
                                , ""
                                , ""
                                , ""
                                , ""
                                , "" + modelAddVehicle.getData().getCountry_area_id()
                                , ""
                                , "" + modelAddVehicle.getData().getLogin_logout()
                                , ""
                                , "" + modelAddVehicle.getData().getOnline_offline()
                                , "3"
                                , ""
                                , ""
                                , ""
                        ,""
                        ,""
                        ,""
                        ,""
                        ,"");

                    } else {
                        sessionManager.createLoginSession(false,
                                "" + modelAddVehicle.getData().getDriver_id(),
                                ""
                                , ""
                                , ""
                                , ""
                                , ""
                                , ""
                                , ""
                                , ""
                                , ""
                                , ""
                                , "" + modelAddVehicle.getData().getCountry_area_id()
                                , ""
                                , "" + modelAddVehicle.getData().getLogin_logout()
                                , ""
                                , "" + modelAddVehicle.getData().getOnline_offline()
                                , "2"
                                , ""
                                , ""
                                , ""
                        ,""
                        ,""
                        ,""
                        ,""
                        ,"");

                    }

                    Log.e("****DocumenTParaAdd", "" + documentScreenApi);
                    startActivity(new Intent(getContext(), DocumentActivity.class)
                            .putExtra("documentScreenApi", documentScreenApi)
                            .putExtra("" + IntentKeys.DRIVER_ID, "" + driver_id)
                            .putExtra("" + IntentKeys.DRIVER_VEHICLE_ID, "" + modelAddVehicle.getData().getId()));
                    getActivity().finish();
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d(TAG, "Exception caught while parsing " + e.getMessage());
        }
    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {
        Log.e("Message", "" + script);
        modelVehicleModel = null;        
        tvCarModel.setText(getResources().getString(R.string.please_select_car_make));
        tvCarTraction.setText(getResources().getString(R.string.click_to_select_traction));
        carTypeAntt.setText(getResources().getString(R.string.please_select_antt));
        tvCarColor.setText(getResources().getString(R.string.enter_car_color));
        Toast.makeText(getActivity(), "" + script, Toast.LENGTH_LONG).show();
    }
}
