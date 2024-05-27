package com.tkx.driver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.others.AppUitls;
import com.tkx.driver.others.ImageCompressMode;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;
import com.sampermissionutils.AfterPermissionGranted;
import com.sampermissionutils.EasyPermissions;
import com.tkx.driver.logger.Logger;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.Manifest;

import org.json.JSONObject;

import id.zelory.compressor.Compressor;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import com.bumptech.glide.Glide;


public class MeterImageActivity extends Activity implements EasyPermissions.PermissionCallbacks{


    private static final int RC_CAMERA_PERM = 123;
    private static final String TAG = "MeterImageActivity";
    public static int CAMERS_PICKER = 102;
    private static final int GALLERY_PICKER = 124;
    private static String imagePathCompressed = "";

    private String PROOF = "";

    ImageView image;
    EditText meter_val_edt ;

    String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_meter_image);
        image = (ImageView) findViewById(R.id.image);
        meter_val_edt = (EditText) findViewById(R.id.meter_val_edt);

        imagePathCompressed = "";

        findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // cameraTask();
                ImagePicker.with(MeterImageActivity.this)
                    .setToolbarColor("#212121")
                    .setStatusBarColor("#000000")
                    .setToolbarTextColor("#FFFFFF")
                    .setToolbarIconColor("#FFFFFF")
                    .setProgressBarColor("#4CAF50")
                    .setBackgroundColor("#212121")
                    .setCameraOnly(false)
                    .setMultipleMode(false)
                    .setFolderMode(false)
                    .setShowCamera(true)
                    .setFolderTitle(getResources().getString(R.string.albums))
                    .setImageTitle(getResources().getString(R.string.albums))
                    .setDoneTitle(getResources().getString(R.string.done))
                    .setLimitMessage("You have reached selection limit")
                    .setMaxSize(1)
                    .setSavePath("ImagePicker")
                    .setAlwaysShowDoneButton(true)
                    .setRequestCode(100)
                    .setKeepScreenOn(true)
                .start();
            }
        });

        findViewById(R.id.ok_btn).setOnClickListener(v -> {
            if (imagePathCompressed.equals("")){
                Toast.makeText(MeterImageActivity.this, R.string.photo_proof_of_delivery, Toast.LENGTH_SHORT).show();
            }
            else if(meter_val_edt.getText().toString().equals("")){
                Toast.makeText(MeterImageActivity.this, R.string.enter_recipient_name, Toast.LENGTH_SHORT).show();
            }
            else {
                finalizeActivity();
            }
        });

    }


    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void cameraTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(intent, CAMERS_PICKER);
        } else {
            Intent i1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            i1.setType("image/*");
            startActivityForResult(i1, GALLERY_PICKER);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Toast.makeText(this, "permissão garantida", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "permissão negada", Toast.LENGTH_SHORT).show();

    }
    private void comppressImageFile(Uri uri ){
        Compressor.getDefault(this)
                .compressToFileAsObservable(new File(uri.getPath()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        try{
                            image.setImageBitmap(AppUitls.handleSamplingAndRotationBitmap(MeterImageActivity.this, file.getPath()));
                            imagePathCompressed = file.getPath();
                        }catch (Exception e){}
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        // showErrorDialoge("comppressImageFile()",""+throwable.getMessage());
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (resultCode == Activity.RESULT_OK) {
            try {
                if (requestCode == com.nguyenhoanglam.imagepicker.model.Config.RC_PICK_IMAGES) {
                    ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
                    CropImage.activity(Uri.fromFile(new File("" + images.get(0).getPath()))).start(this);
                }

                if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (resultCode == RESULT_OK) {
                        Uri resultUri = result.getUri();
                        comppressImageFile(resultUri);
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                    }
                }

                if (requestCode == CAMERS_PICKER) {
                    if (resultCode == RESULT_OK) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        image.setImageBitmap(photo);
                        Uri tempUri = getImageUri(getApplicationContext(), photo);
                        imagePathCompressed = new ImageCompressMode(this).compressImage(getPath(tempUri));
                    }
                }

                if (requestCode == GALLERY_PICKER) {
                    if (resultCode != 0) {
                        ImageCompressMode imageCompressMode = new ImageCompressMode(this);                        
                        image.setImageBitmap(null);
                        image.setBackgroundDrawable(null);
                        Glide.with(this).load(data.getData()).into(image);
                        PROOF = imageCompressMode.compressImage(getRealPathFromURI(data.getData()));
                    }
                }

            } catch (Exception e) {
                Logger.e("res         " + e.toString());
            }
        }
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



    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putExtra(""+ IntentKeys.IMAGE,"");
        intent.putExtra(""+ IntentKeys.METER_VALUE, "");
        setResult(Activity.RESULT_CANCELED,intent);
        finish();
    }

    private void finalizeActivity() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        JSONObject no_data = new JSONObject();
        if(imagePathCompressed.equals("") || meter_val_edt.getText().toString().equals("")){
            Intent intent = new Intent();
            intent.putExtra("" + IntentKeys.IMAGE,"");
            intent.putExtra("" + IntentKeys.METER_VALUE, "");
            setResult(Activity.RESULT_CANCELED, intent);
        }else{
            Intent intent = new Intent();
            intent.putExtra("" + IntentKeys.IMAGE,"" + imagePathCompressed);
            intent.putExtra("" + IntentKeys.METER_VALUE, "" + meter_val_edt.getText().toString());
            setResult(Activity.RESULT_OK, intent);
        }
        finish();
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
}