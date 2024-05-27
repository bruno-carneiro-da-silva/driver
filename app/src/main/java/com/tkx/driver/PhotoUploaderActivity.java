package com.tkx.driver;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tkx.driver.currentwork.API_S;
import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.logger.Logger;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.models.ModelUploadVehoicleDocument;
import com.tkx.driver.models.ModelUploadpersonalDocument;
import com.tkx.driver.others.AppUitls;
import com.tkx.driver.others.ImageCompressMode;
import com.tkx.driver.samwork.ApiManager;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;
import com.sampermissionutils.AfterPermissionGranted;
import com.sampermissionutils.EasyPermissions;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import customviews.typefacesviews.TypeFaceGoogle;
import customviews.typefacesviews.TypeFaceGoogleBold;
import id.zelory.compressor.Compressor;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class PhotoUploaderActivity extends Activity implements EasyPermissions.PermissionCallbacks, DatePickerDialog.OnDateSetListener, ApiManager.APIFETCHER {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.camera)
    TypeFaceGoogleBold camera;
    @BindView(R.id.gallery)
    TypeFaceGoogleBold gallery;
    @BindView(R.id.date)
    TypeFaceGoogle date;
    @BindView(R.id.submit)
    CardView submit;
    @BindView(R.id.llExpiryLayout)
    LinearLayout llExpiryLayout;
    @BindView(R.id.llDocumentNumber)
    LinearLayout llDocumentNumber;
    @BindView(R.id.edDocumnetNumber)
    EditText edDocumnetNumber;
    private Uri imageUri;
    ApiManager apiManager;
    ProgressDialog progressDialog;
    private File compressedImage;
    private ContentValues values;
    private Bitmap thumbnail;
    private SessionManager sessionManager;
    private static final int RC_CAMERA_PERM = 123;
    private static final int PICK_IMAGE = 124;
    private static final int CAMERS_PICKER = 122;
    private static final String TAG = "";
    Uri selectedImage;
    String imagePath = "", COMPRESSES_IMAGE_VEHICLE = "";
    Bitmap bitmap1;
    String EXPIRY_DATE = "";
    String strType = "";

    String document_number_required = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_photo_uploader);
        ButterKnife.bind(this);

        apiManager = new ApiManager(this, this);
        sessionManager = new SessionManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(this.getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);

        EXPIRY_DATE = getIntent().getStringExtra(IntentKeys.EXPIRY_DATE);

        try {
            document_number_required = getIntent().getStringExtra(IntentKeys.DOCUMENT_NUMBER_REQUIRED);

        } catch (Exception e) {

        }


        try {
            llDocumentNumber.setVisibility(document_number_required.equals("1") ? View.VISIBLE : View.GONE);
        } catch (Exception e) {

        }


        llExpiryLayout.setVisibility((null == EXPIRY_DATE || EXPIRY_DATE.isEmpty() || EXPIRY_DATE.equals("2")) ? View.GONE : View.VISIBLE);

        try {
            strType = getIntent().getStringExtra("TYPE");
        } catch (Exception e) {

        }
        camera.setOnClickListener(view -> {
            try {
                cameraTask();
                // ImagePicker.with(PhotoUploaderActivity.this)                         //  Initialize ImagePicker with activity or fragment context
                //         .setToolbarColor("#212121")         //  Toolbar color
                //         .setStatusBarColor("#000000")       //  StatusBar color (works with SDK >= 21  )
                //         .setToolbarTextColor("#FFFFFF")     //  Toolbar text color (Title and Done button)
                //         .setToolbarIconColor("#FFFFFF")     //  Toolbar icon color (Back and Camera button)
                //         .setProgressBarColor("#4CAF50")     //  ProgressBar color
                //         .setBackgroundColor("#212121")      //  Background color
                //         .setCameraOnly(true)               //  Camera mode
                //         .setMultipleMode(false)              //  Select multiple images or single image
                //         .setFolderMode(false)                //  Folder mode
                //         .setShowCamera(true)                //  Show camera button
                //         .setFolderTitle("album")           //  Folder title (works with FolderMode = true)
                //         .setImageTitle("album")         //  Image title (works with FolderMode = false)
                //         .setDoneTitle(PhotoUploaderActivity.this.getResources().getString(R.string.done))               //  Done button title
                //         .setLimitMessage("You have reached selection limit")    // Selection limit message
                //         .setMaxSize(1)                     //  Max images can be selected
                //         .setSavePath("ImagePicker")         //  Image capture folder name
                //         .setAlwaysShowDoneButton(true)      //  Set always show done button in multiple mode
                //         .setRequestCode(100)                //  Set request code, default Config.RC_PICK_IMAGES
                //         .setKeepScreenOn(true)              //  Keep screen on when selecting images
                //         .start();
            } catch (Exception e) {
            }
        });

        gallery.setOnClickListener(view -> {
            try {
                selectFromgalery();

                // ImagePicker.with(PhotoUploaderActivity.this)                         //  Initialize ImagePicker with activity or fragment context
                //         .setToolbarColor("#212121")         //  Toolbar color
                //         .setStatusBarColor("#000000")       //  StatusBar color (works with SDK >= 21  )
                //         .setToolbarTextColor("#FFFFFF")     //  Toolbar text color (Title and Done button)
                //         .setToolbarIconColor("#FFFFFF")     //  Toolbar icon color (Back and Camera button)
                //         .setProgressBarColor("#4CAF50")     //  ProgressBar color
                //         .setBackgroundColor("#212121")      //  Background color
                //         .setCameraOnly(false)               //  Camera mode
                //         .setMultipleMode(false)              //  Select multiple images or single image
                //         .setFolderMode(false)                //  Folder mode
                //         .setShowCamera(false)                //  Show camera button
                //         .setFolderTitle("album")           //  Folder title (works with FolderMode = true)
                //         .setImageTitle("album")         //  Image title (works with FolderMode = false)
                //         .setDoneTitle(PhotoUploaderActivity.this.getResources().getString(R.string.done))               //  Done button title
                //         .setLimitMessage("You have reached selection limit")    // Selection limit message
                //         .setMaxSize(1)                     //  Max images can be selected
                //         .setSavePath("ImagePicker")         //  Image capture folder name
                //         .setAlwaysShowDoneButton(true)      //  Set always show done button in multiple mode
                //         .setRequestCode(100)                //  Set request code, default Config.RC_PICK_IMAGES
                //         .setKeepScreenOn(true)              //  Keep screen on when selecting images
                //         .start();
            } catch (Exception e) {
            }
        });

        date.setOnClickListener(view -> {
            try {
                openDateFDialog();
            } catch (Exception e) {
                Log.d("" + TAG, "Exception caught while date click --> " + e.getMessage());
            }
        });

        submit.setOnClickListener(view -> {
            try {

                if (document_number_required.equals("1") && edDocumnetNumber.getText().toString().equals("")) {
                    Toast.makeText(this, getResources().getString(R.string.please_enter_document_number), Toast.LENGTH_SHORT).show();
                } else {

                    if (null != EXPIRY_DATE && EXPIRY_DATE.equals("1") && !EXPIRY_DATE.isEmpty()) {
                        if (date.getText().toString().equals("DD MM YYYY")) {

                            Toast.makeText(PhotoUploaderActivity.this, R.string.please_expiry_date, Toast.LENGTH_SHORT).show();
                        } else {
                            if (getIntent().getExtras().getString(IntentKeys.DOCUMENT_TYPE).equals("1")) {  // that is personal documents
                                HashMap<String, String> data = new HashMap<>();
                                HashMap<String, File> images_data = new HashMap<>();
                                data.put("driver", "" + getIntent().getExtras().getString("" + IntentKeys.DRIVER_ID));
                                data.put("document", "" + getIntent().getExtras().getString("" + IntentKeys.DOCUMENT_ID));
                                data.put("expire_date", "" + date.getText().toString());
                                data.put("type", "" + strType);
                            /*if(EXPIRY_DATE.equals("1")){

                            }else {
                                data.put("expire_date", "");
                            }*/

                                if (document_number_required.equals("1")) {
                                    data.put("document_number", edDocumnetNumber.getText().toString());
                                }

                                images_data.put("document_image", new File(COMPRESSES_IMAGE_VEHICLE));
                                apiManager._post_image_with_secreteonly(API_S.Tags.UPLOAD_DRIVER_DOCUMENT, API_S.Endpoints.UPLOAD_DRIVER_DOCUMENT, data, images_data);
                            } else if (getIntent().getExtras().getString(IntentKeys.DOCUMENT_TYPE).equals("2")) { // // vehicle documents
                                HashMap<String, String> data = new HashMap<>();
                                HashMap<String, File> images_data = new HashMap<>();
                                data.put("driver", "" + getIntent().getExtras().getString("" + IntentKeys.DRIVER_ID));
                                data.put("document", "" + getIntent().getExtras().getString("" + IntentKeys.DOCUMENT_ID));
                                data.put("expire_date", "" + date.getText().toString());
                                data.put("type", "" + strType);

                            /*if(EXPIRY_DATE.equals("1")){
                                data.put("expire_date", "" + date.getText().toString());
                            }else {
                                data.put("expire_date", "");
                            }*/

                                if (document_number_required.equals("1")) {
                                    data.put("document_number", edDocumnetNumber.getText().toString());
                                }
                                data.put("driver_vehicle_id", "" + getIntent().getExtras().getString("" + IntentKeys.DRIVER_VEHICLE_ID));
                                images_data.put("document_image", new File(COMPRESSES_IMAGE_VEHICLE));
                                apiManager._post_image_with_secreteonly(API_S.Tags.UPLOAD_VEHICLE_DOCUMENT, API_S.Endpoints.UPLOAD_VEHICLE_DOCUMENT, data, images_data);
                            }

                        }
                    } else {
                        if (getIntent().getExtras().getString(IntentKeys.DOCUMENT_TYPE).equals("1")) {  // that is personal documents
                            HashMap<String, String> data = new HashMap<>();
                            HashMap<String, File> images_data = new HashMap<>();
                            data.put("driver", "" + getIntent().getExtras().getString("" + IntentKeys.DRIVER_ID));
                            data.put("document", "" + getIntent().getExtras().getString("" + IntentKeys.DOCUMENT_ID));
                            data.put("type", "" + strType);
                        /*if(EXPIRY_DATE.equals("1")){
                            data.put("expire_date", "" + date.getText().toString());
                        } else {
                            data.put("expire_date", "");
                        }*/

                            if (document_number_required.equals("1")) {
                                data.put("document_number", edDocumnetNumber.getText().toString());
                            }

                            images_data.put("document_image", new File(COMPRESSES_IMAGE_VEHICLE));
                            apiManager._post_image_with_secreteonly(API_S.Tags.UPLOAD_DRIVER_DOCUMENT, API_S.Endpoints.UPLOAD_DRIVER_DOCUMENT, data, images_data);
                        } else if (getIntent().getExtras().getString(IntentKeys.DOCUMENT_TYPE).equals("2")) { // // vehicle documents
                            HashMap<String, String> data = new HashMap<>();
                            HashMap<String, File> images_data = new HashMap<>();
                            data.put("driver", "" + getIntent().getExtras().getString("" + IntentKeys.DRIVER_ID));
                            data.put("document", getIntent().getExtras().getString("" + IntentKeys.DOCUMENT_ID));
                            data.put("type", "" + strType);
                        /*if(EXPIRY_DATE.equals("1")){
                            data.put("expire_date", "" + date.getText().toString());
                        }else {
                            data.put("expire_date", "");
                        }*/

                            if (document_number_required.equals("1")) {
                                data.put("document_number", edDocumnetNumber.getText().toString());
                            }

                            data.put("driver_vehicle_id", "" + getIntent().getExtras().getString("" + IntentKeys.DRIVER_VEHICLE_ID));
                            images_data.put("document_image", new File(COMPRESSES_IMAGE_VEHICLE));
                            apiManager._post_image_with_secreteonly(API_S.Tags.UPLOAD_VEHICLE_DOCUMENT, API_S.Endpoints.UPLOAD_VEHICLE_DOCUMENT, data, images_data);
                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void cameraTask() throws Exception {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            try { // Have permission, do the thing!
                values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CAMERS_PICKER);
            } catch (Exception e) {
            }
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera), RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    public void selectFromgalery() throws Exception {
        Intent i1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i1.setType("image/*");
        startActivityForResult(i1, PICK_IMAGE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d("" + TAG, "Permission Granted");
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d("" + TAG, "Permission Denied");

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case com.nguyenhoanglam.imagepicker.model.Config.RC_PICK_IMAGES :
                try{
                    ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
                    comppressImageFile(Uri.parse(images.get(0).getPath()));
                }catch (Exception e){

                }

                //CropImage.activity(Uri.fromFile(new File("" + images.get(0).getPath()))).start(getActivity());
                // if (FLAG == 1){
                //     numberPlateImage.setImageBitmap(AppUtils.handleSamplingAndRotationBitmap(getContext(), images.get(0).getPath()));
                //     COMPRESSES_IMAGE_NUMBER_PLATE =images.get(0).getPath();
                // }else if (FLAG == 2){
                //     cheque_image.setImageBitmap(AppUtils.handleSamplingAndRotationBitmap(getContext(),images.get(0).getPath()));
                //     COMPRESS_IMAGE_CHEQUE_IMAGE =images.get(0).getPath();
                // }else if (FLAG == 3){
                //     vehicleImage.setImageBitmap(AppUtils.handleSamplingAndRotationBitmap(getContext(), images.get(0).getPath()));
                //     COMPRESSES_IMAGE_VEHICLE =images.get(0).getPath();
                // }

                break;

            case CAMERS_PICKER:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        if (resultCode == RESULT_OK) {
                            ImageCompressMode imageCompressModeee = new ImageCompressMode(this);
                            // Bitmap photo = (Bitmap) data.getExtras().get("data");
                            // image.setImageBitmap(photo);
                            // Uri tempUri = getImageUri(getApplicationContext(), photo);
                            // imagePath = getPath(tempUri);
                            thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            image.setImageBitmap(thumbnail);
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
                            image.setImageBitmap(rotatedBitmap);

                            ImageCompressMode imageCompressModee = new ImageCompressMode(this);
                            COMPRESSES_IMAGE_VEHICLE = imageCompressModee.compressImage(imagePath);


                            // COMPRESSES_IMAGE_VEHICLE = imageCompressModeee.compressImage(getRealPathFromURI(imageUri));


                            if (EXPIRY_DATE.equals("1")) {
                                Toast.makeText(PhotoUploaderActivity.this, R.string.attach_expirey_date_of_your_document, Toast.LENGTH_SHORT).show();
                                openDateFDialog();
                            }

                        }
                    } catch (Exception e) {
                        Logger.e("Exception -->" + e.toString());
                    }
                }
                break;


            case PICK_IMAGE:
                try {
                    ImageCompressMode imageCompressMode = new ImageCompressMode(this);
                    selectedImage = data.getData();
                    imagePath = getPath(selectedImage);
                    COMPRESSES_IMAGE_VEHICLE = imageCompressMode.compressImage(getRealPathFromURI(data.getData()));
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    // Set the Image in ImageView after decoding the String
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(filePath, options);
                    final int REQUIRED_SIZE = 300;
                    int scale = 1;
                    while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                        scale *= 2;
                    options.inSampleSize = scale;
                    options.inJustDecodeBounds = false;
                    bitmap1 = BitmapFactory.decodeFile(filePath, options);
                    image.setImageBitmap(bitmap1);
                    if (EXPIRY_DATE.equals("1")) {
                        Toast.makeText(PhotoUploaderActivity.this, R.string.attach_expirey_date_of_your_document, Toast.LENGTH_SHORT).show();
                        openDateFDialog();
                    }
                } catch (Exception e) {
                    Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void comppressImageFile(Uri uri ){
        Compressor.getDefault(PhotoUploaderActivity.this)
                .compressToFileAsObservable(new File(uri.getPath()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        try{
                            image.setImageBitmap(AppUitls.handleSamplingAndRotationBitmap(PhotoUploaderActivity.this, file.getPath()));
                            COMPRESSES_IMAGE_VEHICLE =file.getPath();
                            // driver_image.setImageBitmap(AppUitls.handleSamplingAndRotationBitmap(RegisterActivity.this, file.getPath()));
                            // DOCUMENT_IMAGE =file.getPath();

                            if (EXPIRY_DATE.equals("1")) {
                                Toast.makeText(PhotoUploaderActivity.this, R.string.attach_expirey_date_of_your_document, Toast.LENGTH_SHORT).show();
                                openDateFDialog();
                            }
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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    private void openDateFDialog() throws Exception {


        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dpd = DatePickerDialog.newInstance(PhotoUploaderActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dpd.setMinDate(calendar);
        dpd.setCancelText("" + getResources().getString(R.string.cancel));
        dpd.setOkText("" + getResources().getString(R.string.ok));
        dpd.setAccentColor(PhotoUploaderActivity.this.getResources().getColor(R.color.colorPrimary));
        dpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });
        dpd.show(getFragmentManager(), "Date Picker Dialog");
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(year, monthOfYear, dayOfMonth);

        String data = dateFormat.format(cal.getTime());

        //  String data = "" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        date.setText("" + data);
    }


    @Override
    public void onAPIRunningState(int a, String APINAME) {
        try {
            if (a == ApiManager.APIFETCHER.KEY_API_IS_STARTED) {
                progressDialog.show();
            } else if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onFetchComplete(Object script, String APINAME) {

        try {
            switch (APINAME) {
                case API_S.Tags.UPLOAD_DRIVER_DOCUMENT:
                    ModelUploadpersonalDocument modelUploadpersonalDocument = SingletonGson.getInstance().fromJson("" + script, ModelUploadpersonalDocument.class);
                    Toast.makeText(this, "" + modelUploadpersonalDocument.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case API_S.Tags.UPLOAD_VEHICLE_DOCUMENT:
                    ModelUploadVehoicleDocument modelUploadVehoicleDocument = SingletonGson.getInstance().fromJson("" + script, ModelUploadVehoicleDocument.class);
                    Toast.makeText(this, "" + modelUploadVehoicleDocument.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        } catch (Exception e) {
            Log.d("" + TAG, "Exception caught onfetchcomplete " + e.getMessage());
        }
    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {
        Toast.makeText(this, "Falha" + script, Toast.LENGTH_SHORT).show();
    }
}
