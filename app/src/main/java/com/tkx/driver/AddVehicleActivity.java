package com.tkx.driver;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tkx.driver.baseClass.BaseActivity;
import com.tkx.driver.currentwork.API_S;
import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.models.ModelAddVehicle;
import com.tkx.driver.models.ModelVehicleModel;
import com.tkx.driver.models.ModelvehicleConfiguration;
import com.tkx.driver.others.ImageCompressMode;
import com.tkx.driver.samwork.ApiManager;
import com.bumptech.glide.Glide;
import com.sampermissionutils.AfterPermissionGranted;
import com.sampermissionutils.EasyPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddVehicleActivity extends BaseActivity implements ApiManager.APIFETCHER {


    @BindView(R.id.car_type_name)
    TextView carTypeName;
    @BindView(R.id.edt_car_make)
    TextView edtCarMake;
    @BindView(R.id.tv_car_model)
    TextView tvCarModel;
    @BindView(R.id.tv_car_traction)
    TextView tvCarTraction;
    @BindView(R.id.car_type_antt)
    TextView carTypeAntt;
    @BindView(R.id.tv_ride_category)
    TextView tvRideCategory;
    @BindView(R.id.number_placet_image)
    LinearLayout numberPlacetImage;
    @BindView(R.id.select_car_image)
    LinearLayout selectCarImage;
    @BindView(R.id.number_plate_image)
    ImageView numberPlateImage;
    @BindView(R.id.vehicle_image)
    ImageView vehicleImage;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.submit_vehicle_button)
    TextView submitVehicleButton;
    @BindView(R.id.car_number_edt)
    EditText carNumberEdt;
    @BindView(R.id.car_capacity_edt)
    EditText carCapacityEdt;
    // @BindView(R.id.car_rntrc_edt)
    // EditText carRntrcEdt;
    // @BindView(R.id.car_color_edt)
    // EditText carColorEdt;
    @BindView(R.id.create_vehicle_info)
    ImageView createVehicleInfo;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.ac_non_ac_checkbox)
    CheckBox ac_non_ac_checkbox;
    private ApiManager apiManager;
    SessionManager sessionManager;

    private static final String TAG = "AddVehicleActivity";
    
    private String SELECTED_CAR_TYPE = "", SELECTED_CAR_MAKE = "", SELECTED_CAR_MODEL = "", SELECTED_CAR_TRACTION = "", SELECTED_CAR_ANTT = "", COMPRESSES_IMAGE_NUMBER_PLATE = "", COMPRESSES_IMAGE_VEHICLE = "", SELECTED_CAR_COLOR = "";

    private int SELECTED_CAR_TYPE_ARRAY_INDEX = 999;
    private ModelvehicleConfiguration modelvehicleConfiguration;
    private ModelVehicleModel modelVehicleModel;
    private ArrayList<Integer> SELECTED_SERVICES_TYPES = new ArrayList<>();


    private static final int RC_CAMERA_PERM = 123;
    private static final int CAMERS_PICKER = 122;
    private static final int GALLERY_PICKER = 124;

    private ContentValues values;
    private Uri imageUri;
    private ProgressDialog progressDialog;
    String vehical_ac;


    int SELECTED_IMAGE_TYPE, PICK_FOR_CAR = 1, PICK_FOR_NUMBER_PLATE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(this.getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        apiManager = new ApiManager(this,this);
        sessionManager = new SessionManager(this);
        ButterKnife.bind(this);


        try {
            HashMap<String, String> data = new HashMap<>();
            data.put("area", "" + getIntent().getExtras().getString("" + IntentKeys.AREA_ID));
            apiManager._post(API_S.Tags.VEHICLE_CONFIGURATION, API_S.Endpoints.VEHICLE_CONFIGURATION, data, sessionManager);
        } catch (Exception e) {
            Log.d(TAG, "Exception caught while calling api for vehicle configuration " + e.getMessage());
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        numberPlacetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPickerDialog(PICK_FOR_NUMBER_PLATE);
            }
        });

        selectCarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPickerDialog(PICK_FOR_CAR);
            }
        });


        createVehicleInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddVehicleActivity.this);
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


        carTypeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(AddVehicleActivity.this);
                builderSingle.setTitle("" + AddVehicleActivity.this.getResources().getString(R.string.please_select_car_type));
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddVehicleActivity.this, android.R.layout.select_dialog_singlechoice);

                for (int i = 0; i < modelvehicleConfiguration.getData().getVehicle_type().size(); i++) {
                    arrayAdapter.add("" + modelvehicleConfiguration.getData().getVehicle_type().get(i).getVehicleTypeName());
                }
                builderSingle.setNegativeButton("" + "" + AddVehicleActivity.this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        carTypeName.setText("" + modelvehicleConfiguration.getData().getVehicle_type().get(which).getVehicleTypeName());
                        SELECTED_CAR_TYPE = "" + modelvehicleConfiguration.getData().getVehicle_type().get(which).getId();
                        SELECTED_CAR_TYPE_ARRAY_INDEX = which;
                        // callVehiclemakeApi();
                        edtCarMake.setText(getResources().getString(R.string.please_select_car_make));
                        dialog.dismiss();
                    }
                });
                builderSingle.show();
            }
        });

        edtCarMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(AddVehicleActivity.this);
                builderSingle.setTitle("" + AddVehicleActivity.this.getResources().getString(R.string.please_select_car_make));
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddVehicleActivity.this, android.R.layout.select_dialog_singlechoice);

                for (int i = 0; i < modelvehicleConfiguration.getData().getVehicle_make().size(); i++) {
                    arrayAdapter.add("" + modelvehicleConfiguration.getData().getVehicle_make().get(i).getVehicleMakeName());
                }

                builderSingle.setNegativeButton("" + "" + AddVehicleActivity.this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
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
            }
        });

        tvCarModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SELECTED_CAR_MAKE.equals("") || SELECTED_CAR_TYPE.equals("") || modelVehicleModel == null) {
                    Toast.makeText(AddVehicleActivity.this, "Selecione o tipo de carro primeiro", Toast.LENGTH_LONG).show();
                } else {
                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(AddVehicleActivity.this);
                    builderSingle.setTitle("" + AddVehicleActivity.this.getResources().getString(R.string.please_select_car_make));
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddVehicleActivity.this, android.R.layout.select_dialog_singlechoice);

                    for (int i = 0; i < modelVehicleModel.getData().size(); i++) {
                        arrayAdapter.add("" + modelVehicleModel.getData().get(i).getVehicleModelName());
                    }
                    builderSingle.setNegativeButton("" + "" + AddVehicleActivity.this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tvCarModel.setText("" + modelVehicleModel.getData().get(which).getVehicleModelName());
                            SELECTED_CAR_MODEL = "" + modelVehicleModel.getData().get(which).getId();
                            dialog.dismiss();
                        }
                    });
                    builderSingle.show();
                }

            }
        });

        tvCarTraction.setOnClickListener(new View.OnClickListener() {        
            @Override
            public void onClick(View view) {                
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(AddVehicleActivity.this);
                builderSingle.setTitle("" + AddVehicleActivity.this.getResources().getString(R.string.please_select_car_make));
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddVehicleActivity.this, android.R.layout.select_dialog_singlechoice);

                for (int i = 0; i < modelvehicleConfiguration.getData().getVehicle_traction().size(); i++) {
                    arrayAdapter.add("" + modelvehicleConfiguration.getData().getVehicle_traction().get(i).getVehicleTractionName());
                }
                builderSingle.setNegativeButton("" + "" + AddVehicleActivity.this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvCarTraction.setText("" + modelvehicleConfiguration.getData().getVehicle_traction().get(which).getVehicleTractionName());
                        SELECTED_CAR_TRACTION = "" + modelvehicleConfiguration.getData().getVehicle_traction().get(which).getId();
                        dialog.dismiss();
                    }
                });
                builderSingle.show();
            }
        });

        carTypeAntt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(AddVehicleActivity.this);
                builderSingle.setTitle("" + AddVehicleActivity.this.getResources().getString(R.string.please_select_car_type));
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddVehicleActivity.this, android.R.layout.select_dialog_singlechoice);

                for (int i = 0; i < modelvehicleConfiguration.getData().getVehicle_antt().size(); i++) {
                    arrayAdapter.add("" + modelvehicleConfiguration.getData().getVehicle_antt().get(i).getVehicleAnttName());
                }
                builderSingle.setNegativeButton("" + "" + AddVehicleActivity.this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        carTypeAntt.setText("" + modelvehicleConfiguration.getData().getVehicle_antt().get(which).getVehicleAnttName());
                        SELECTED_CAR_TYPE = "" + modelvehicleConfiguration.getData().getVehicle_antt().get(which).getId();
                        SELECTED_CAR_TYPE_ARRAY_INDEX = which;
                        // callVehiclemakeApi();
                        edtCarMake.setText(getResources().getString(R.string.please_select_car_make));
                        dialog.dismiss();
                    }
                });
                builderSingle.show();
            }
        });

        
        ac_non_ac_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(ac_non_ac_checkbox.isChecked())
                {

                    vehical_ac = "1";

                }else {

                    vehical_ac = "0";

                }
            }
        });


        tvRideCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SELECTED_CAR_TYPE_ARRAY_INDEX == 999) {
                    Toast.makeText(AddVehicleActivity.this, "Selecione o tipo de veículo primeiro", Toast.LENGTH_LONG).show();
                } else {
                    boolean[] checkedItems = new boolean[modelvehicleConfiguration.getData().getVehicle_type().get(SELECTED_CAR_TYPE_ARRAY_INDEX).getServices().size()];

                    for (int i = 0; i < modelvehicleConfiguration.getData().getVehicle_type().get(SELECTED_CAR_TYPE_ARRAY_INDEX).getServices().size(); i++) {
                        if (SELECTED_SERVICES_TYPES.contains(i)) {
                            checkedItems[i] = true;
                        } else {
                            checkedItems[i] = false;
                        }
                    }

                    ArrayList<String> services = new ArrayList<>();
                    for (int i = 0; i < modelvehicleConfiguration.getData().getVehicle_type().get(SELECTED_CAR_TYPE_ARRAY_INDEX).getServices().size(); i++) {
                        services.add("" + modelvehicleConfiguration.getData().getVehicle_type().get(SELECTED_CAR_TYPE_ARRAY_INDEX).getServices().get(i).getServiceName());
                    }
                    String[] stringArray = services.toArray(new String[0]);


                    AlertDialog.Builder builder = new AlertDialog.Builder(AddVehicleActivity.this);
                    builder.setMultiChoiceItems(stringArray, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                            if (isChecked) {
                                SELECTED_SERVICES_TYPES.add(indexSelected);
                            } else if (SELECTED_SERVICES_TYPES.contains(indexSelected)) {
                                SELECTED_SERVICES_TYPES.remove(Integer.valueOf(indexSelected));
                            }
                        }
                    }).setPositiveButton(AddVehicleActivity.this.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            tvRideCategory.setText("" + AddVehicleActivity.this.getResources().getString(R.string.REGISTER_ACTIVITY__select_ride_category));

                            for (int i = 0; i < SELECTED_SERVICES_TYPES.size(); i++) {

                                if (i == (SELECTED_SERVICES_TYPES.size() - 1)) {  // that is last element
                                    //   tvRideCategory.setText(tvRideCategory.getText() + "" + modelvehicleConfiguration.getData().getService_type().get(SELECTED_SERVICES_TYPES.get(i)).getServiceName());
                                    tvRideCategory.setText(tvRideCategory.getText() + "" + modelvehicleConfiguration.getData().getVehicle_type().get(SELECTED_CAR_TYPE_ARRAY_INDEX).getServices().get(SELECTED_SERVICES_TYPES.get(i)).getServiceName());
                                } else {
                                    //   tvRideCategory.setText(tvRideCategory.getText() + "" + modelvehicleConfiguration.getData().getService_type().get(SELECTED_SERVICES_TYPES.get(i)).getServiceName() + ",");
                                    tvRideCategory.setText(tvRideCategory.getText() + "" + modelvehicleConfiguration.getData().getVehicle_type().get(SELECTED_CAR_TYPE_ARRAY_INDEX).getServices().get(SELECTED_SERVICES_TYPES.get(i)).getServiceName() + ",");
                                }
                            }
                            tvRideCategory.setText("" + tvRideCategory.getText().toString().replace("" + AddVehicleActivity.this.getResources().getString(R.string.REGISTER_ACTIVITY__select_ride_category), ""));
                        }
                    }).setNegativeButton(AddVehicleActivity.this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

                    builder.show();
                }

            }
        });

        submitVehicleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SELECTED_CAR_TYPE.equals("") || SELECTED_CAR_MAKE.equals("") || SELECTED_CAR_MODEL.equals("") || SELECTED_SERVICES_TYPES.size() == 0 || COMPRESSES_IMAGE_NUMBER_PLATE.equals("") || COMPRESSES_IMAGE_VEHICLE.equals("")) {
                    Toast.makeText(AddVehicleActivity.this, AddVehicleActivity.this.getResources().getString(R.string.required_field_missing), Toast.LENGTH_LONG).show();
                } else {
                    HashMap<String, String> data = new HashMap<>();

                    data.put("driver_id", "" + getIntent().getExtras().getString("" + IntentKeys.DRIVER_ID));
                    data.put("vehicle_type", "" + SELECTED_CAR_TYPE);
                    data.put("vehicle_make", "" + SELECTED_CAR_MAKE);
                    data.put("vehicle_model", "" + SELECTED_CAR_MODEL);                    
                    data.put("vehicle_traction", "" + SELECTED_CAR_TRACTION);
                    data.put("vehicle_antt", "" + SELECTED_CAR_ANTT);
                    data.put("vehicle_number", "" + carNumberEdt.getText().toString());
                    data.put("vehicle_capacity", "" + carCapacityEdt.getText().toString());
                    // data.put("vehicle_rntrc", "" + carRntrcEdt.getText().toString());
                    data.put("vehicle_rntrc", "");
                    // data.put("vehicle_color", "" + carColorEdt.getText().toString());
                    data.put("vehicle_color", "" + SELECTED_CAR_COLOR);
                    
                    data.put("ac_nonac", vehical_ac);
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

                    data.put("service_type", "" + services);
                    HashMap<String, File> data_image = new HashMap<>();
                    data_image.put("vehicle_image", new File(COMPRESSES_IMAGE_VEHICLE));
                    data_image.put("vehicle_number_plate_image", new File(COMPRESSES_IMAGE_NUMBER_PLATE));

                    try {
                        apiManager._post_image(API_S.Tags.ADD_VEHICLE, API_S.Endpoints.ADD_VEHICLE, data, data_image, sessionManager);
                    } catch (Exception e) {
                        Log.d("" + TAG, "Excreption caught while calling add vehicle API " + e.getMessage());
                    }
                }
            }
        });

    }


    public void callVehiclemakeApi() {
        if (SELECTED_CAR_TYPE.equals("") || SELECTED_CAR_MAKE.equals("")) {

        } else {
            HashMap<String, String> data_selected = new HashMap<>();
            data_selected.put("vehicle_type", "" + SELECTED_CAR_TYPE);
            data_selected.put("vehicle_make", "" + SELECTED_CAR_MAKE);
            try {
                apiManager._post(API_S.Tags.VEHICLE_MODEL, API_S.Endpoints.VEHICLE_MODEL, data_selected, sessionManager);
            } catch (Exception e) {
                Log.d(TAG, "Exception caught while calling vehicle make API " + e.getMessage());
            }

        }
    }


    private void openPickerDialog(int type) {
        SELECTED_IMAGE_TYPE = type;
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(AddVehicleActivity.this);
        builderSingle.setTitle("Upload Photo");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddVehicleActivity.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Camera");
        arrayAdapter.add("Gallery");
        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
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
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            try { // Have permission, do the thing!
                values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                // case GALLERY_PICKER:
                //     ImageCompressMode imageCompressMode = new ImageCompressMode(this);
                //     if (SELECTED_IMAGE_TYPE == PICK_FOR_CAR) {
                //         Glide.with(AddVehicleActivity.this).load(data.getData()).into(vehicleImage);
                //         COMPRESSES_IMAGE_VEHICLE = imageCompressMode.compressImage(getRealPathFromURI(data.getData()));
                //     } else if (SELECTED_IMAGE_TYPE == PICK_FOR_NUMBER_PLATE) {
                //         Glide.with(AddVehicleActivity.this).load(data.getData()).into(numberPlateImage);
                //         COMPRESSES_IMAGE_NUMBER_PLATE = imageCompressMode.compressImage(getRealPathFromURI(data.getData()));
                //     }
                //     break;
                // case CAMERS_PICKER:
                //     ImageCompressMode imageCompressModee = new ImageCompressMode(this);
                //     if (SELECTED_IMAGE_TYPE == PICK_FOR_CAR) {
                //         vehicleImage.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri));
                //         COMPRESSES_IMAGE_VEHICLE = imageCompressModee.compressImage(getRealPathFromURI(imageUri));
                //     } else if (SELECTED_IMAGE_TYPE == PICK_FOR_NUMBER_PLATE) {
                //         numberPlateImage.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri));
                //         COMPRESSES_IMAGE_NUMBER_PLATE = imageCompressModee.compressImage(getRealPathFromURI(imageUri));
                //     }
                //     break;
            }
        } catch (Exception e) {
        }

    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
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
                    modelVehicleModel = SingletonGson.getInstance().fromJson("" + script, ModelVehicleModel.class);
                    tvCarModel.setText(getResources().getString(R.string.enter_vehicle_model));
                    break;
                case API_S.Tags.ADD_VEHICLE:
                    ModelAddVehicle modelAddVehicle = SingletonGson.getInstance().fromJson("" + script, ModelAddVehicle.class);
                    startActivity(new Intent(AddVehicleActivity.this, DocumentActivity.class)
                        .putExtra("" + IntentKeys.DRIVER_ID, "" + getIntent().getExtras().getString("" + IntentKeys.DRIVER_ID))
                        .putExtra("" + IntentKeys.DRIVER_VEHICLE_ID, "" + modelAddVehicle.getData().getId()));
                    finish();
                    break;
            }
        } catch (Exception e) {
            Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            Log.d(TAG, "Exception caught while parsing " + e.getMessage());
        }
    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {
        Log.e("Message",""+script);
        edtCarMake.setText(getResources().getString(R.string.please_select_car_make));
        tvCarModel.setText(getResources().getString(R.string.enter_vehicle_model));
        tvCarTraction.setText(getResources().getString(R.string.click_to_select_traction));
        carTypeAntt.setText(getResources().getString(R.string.please_select_antt));        
        Snackbar.make(root, "" + script, Snackbar.LENGTH_SHORT).show();
    }

}
