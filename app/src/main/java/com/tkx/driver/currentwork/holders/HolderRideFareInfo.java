package com.tkx.driver.currentwork.holders;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tkx.driver.R;
import com.tkx.driver.models.ModelReceipt;
import com.bumptech.glide.Glide;
import com.sam.placer.annotations.Click;
import com.sam.placer.annotations.Layout;
import com.sam.placer.annotations.Resolve;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;

import android.media.AudioManager;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.provider.MediaStore;

import com.sampermissionutils.AfterPermissionGranted;
import com.sampermissionutils.EasyPermissions;
import android.net.Uri;
import android.content.ContentValues;
import com.tkx.driver.baseClass.BaseFragment;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
// import java.io.File;
import java.util.ArrayList;
// import java.util.HashMap;
import com.tkx.driver.others.ImageCompressMode;
import butterknife.BindView;
import android.widget.ImageView;



@Layout(R.layout.holder_ride_info_layout)

public class HolderRideFareInfo extends BaseFragment {    
    @com.sam.placer.annotations.View(R.id.circular_image) CircleImageView circularImage;
    @com.sam.placer.annotations.View(R.id.text_service_type) TextView text_service_type;
    @com.sam.placer.annotations.View(R.id.image_name) TextView circularText;
    @com.sam.placer.annotations.View(R.id.price_text) TextView valueText;
    @com.sam.placer.annotations.View(R.id.left_text) TextView leftText;
    @com.sam.placer.annotations.View(R.id.right_text) TextView rightText;
    @com.sam.placer.annotations.View(R.id.pick_location) TextView pickLocation;
    @com.sam.placer.annotations.View(R.id.drop_location_txt) TextView dropLocationTxt;
    @com.sam.placer.annotations.View(R.id.receipt_layout) LinearLayout receiptLayout;
    @com.sam.placer.annotations.View(R.id.receipt_view) LinearLayout receiptView;

    @BindView(R.id.vehicle_image)
    ImageView vehicleImage;

    private ModelReceipt.DataBean.HolderRideInfoBean mData ;
    private final String TAG = "HolderRideFareInfo";
    private Context context;
    private LayoutInflater mInflater;
    private Activity activity;

    public HolderRideFareInfo(Context context, Activity activity, ModelReceipt.DataBean.HolderRideInfoBean holder_ride_info) {
        mData = holder_ride_info;
        this.context = context;
        this.activity = activity;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    int SELECTED_IMAGE_TYPE, PICK_FOR_CAR = 1, PICK_FOR_NUMBER_PLATE = 2, PICK_FOR_CHEQUE = 3;

    private static final int RC_CAMERA_PERM = 123;
    private static final int CAMERS_PICKER = 122;
    private static final int GALLERY_PICKER = 124;

    // private int Selected_Country_position;
    private Bitmap thumbnail;
    String imagePath = "";
    private Uri imageUri;
    private ContentValues values;

    private String SELECTED_CAR_TYPE = "", SELECTED_CAR_MAKE = "", SELECTED_CAR_MODEL = "", SELECTED_CAR_TRACTION = "",
            SELECTED_CAR_ANTT = "", COMPRESSES_IMAGE_NUMBER_PLATE = "", COMPRESSES_IMAGE_VEHICLE = "",
            COMPRESS_IMAGE_CHEQUE_IMAGE = "", SELECTED_CAR_COLOR = "";


    @SuppressLint("SetTextI18n")
    @Resolve
    private void setData(){
        try{
            setColor();
            setStyle();
            setVisibility();

            Glide.with(context).load(""+mData.getCircular_image()).into(circularImage);
            text_service_type.setText(""+ mData.getCircular_text_one());
            circularText.setText(""+mData.getCircular_text());
            valueText.setText(""+mData.getValue_text());
            leftText.setText(""+mData.getLeft_text());
            rightText.setText(""+mData.getRight_text());
            pickLocation.setText(""+mData.getPick_locaion());
            dropLocationTxt.setText(""+mData.getDrop_location());
            for(int i = 0 ; i < mData.getStatic_values().size() ; i ++){

                receiptLayout.addView(getView(mData.getStatic_values().get(i)));
            }
        }catch (Exception e){
            Log.e("" + TAG, "Exception caught while calling API " + e.getMessage());}

    }

    @Click(R.id.view_receipt_btn)
    private void setOnReceiptClick(){
        AlertDialog alertDialog = null;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_receipt, null);
        TextView left_text  = dialogView.findViewById(R.id.left_text);
        TextView right_text  = dialogView.findViewById(R.id.right_text);
        LinearLayout receiptLayout  = dialogView.findViewById(R.id.receipt_layout);
        left_text.setText(""+mData.getLeft_text());
        right_text.setText(""+mData.getRight_text());

        for(int i = 0 ; i < mData.getStatic_values().size() ; i ++){
            receiptLayout.addView(getView(mData.getStatic_values().get(i)));
        }
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    // @Click(R.id.delivery_receipt)
    // private void setOndelivery_receipt() {
    //     openPickerDialog(PICK_FOR_CAR);

    //     // AlertDialog alertDialog = null;
    //     // AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
    //     // LayoutInflater inflater = activity.getLayoutInflater();
    //     // dialogBuilder.setMessage("Em breve !");
    //     // alertDialog = dialogBuilder.create();
    //     // alertDialog.show();
    // }



    private void setColor() throws Exception {
        circularText.setTextColor(Color.parseColor("#"+mData.getCircular_text_color()));
        valueText.setTextColor(Color.parseColor("#"+mData.getValue_text_color()));
        leftText.setTextColor(Color.parseColor("#"+mData.getLeft_text_color()));
        rightText.setTextColor(Color.parseColor("#"+mData.getRight_text_color()));
    }


    private void setStyle()throws Exception {
        if(mData.getCircular_text_style().equals("BOLD")){
            circularText.setTypeface(null, Typeface.BOLD); }
        else{ circularText.setTypeface(null, Typeface.NORMAL); }

        if(mData.getValue_text_style().equals("BOLD")){
            valueText.setTypeface(null, Typeface.BOLD); }
        else{ valueText.setTypeface(null, Typeface.NORMAL); }


        if(mData.getLeft_text_style().equals("BOLD")){leftText.setTypeface(null, Typeface.BOLD); }
        else{ leftText.setTypeface(null, Typeface.NORMAL); }


        if(mData.getRight_text_style().equals("BOLD")){rightText.setTypeface(null, Typeface.BOLD); }
        else{ rightText.setTypeface(null, Typeface.NORMAL); }

    }


    private void setVisibility()throws Exception{

        if(mData.isCircular_image_visibility()){circularImage.setVisibility(View.VISIBLE);}
        else{circularImage.setVisibility(View.GONE);}

        if(mData.isCircular_text_visibility()){ circularText.setVisibility(View.VISIBLE);}
        else{ circularText.setVisibility(View.GONE);}

        if(mData.isValue_text_visibility()){ valueText.setVisibility(View.VISIBLE);}
        else{ valueText.setVisibility(View.GONE);}



        if(mData.isLeft_text_visibility()){leftText.setVisibility(View.VISIBLE);}
        else{leftText.setVisibility(View.GONE);}


        if(mData.isRight_text_visibility()){rightText.setVisibility(View.VISIBLE);}
        else{rightText.setVisibility(View.GONE);}

        if(mData.isPick_location_visibility()){pickLocation.setVisibility(View.VISIBLE);}
        else{pickLocation.setVisibility(View.GONE);}

        if(mData.isDrop_location_visibility()){dropLocationTxt.setVisibility(View.VISIBLE);}
        else{dropLocationTxt.setVisibility(View.GONE);}


    }



    private View getView(ModelReceipt.DataBean.HolderRideInfoBean.StaticValuesBean dataBean){
        View v =  mInflater.inflate(R.layout.item_receipt, null, true);
        TextView highlighted_text = (TextView) v.findViewById(R.id.highlighted_text);
        TextView highlighted_smalltext = (TextView) v.findViewById(R.id.highlighted_small_text);
        TextView value_text = (TextView) v.findViewById(R.id.value_text);

        try{
            setViewData(dataBean, highlighted_text, highlighted_smalltext,value_text);
            setViewVisibility(dataBean, highlighted_text, highlighted_smalltext,value_text);
            setViewStyle(dataBean, highlighted_text, highlighted_smalltext,value_text);
            setViewColor(dataBean, highlighted_text, highlighted_smalltext,value_text);
        }catch (Exception e){
            Log.e("" +TAG, ""+e.getMessage());
        }
        return v;

    }

    private void setViewStyle(ModelReceipt.DataBean.HolderRideInfoBean.StaticValuesBean dataBean, TextView highlighted_text, TextView highlighted_smalltext, TextView value_text) {
        if(dataBean.getHighlighted_style().equals("BOLD")){ highlighted_text.setTypeface(null, Typeface.BOLD); }
        else{ highlighted_text.setTypeface(null, Typeface.NORMAL); }


        if(dataBean.getSmall_text_style().equals("BOLD")){ highlighted_smalltext.setTypeface(null, Typeface.BOLD); }
        else{ highlighted_smalltext.setTypeface(null, Typeface.NORMAL); }



        if(dataBean.getValue_text_style().equals("BOLD")){ value_text.setTypeface(null, Typeface.BOLD); }
        else{ value_text.setTypeface(null, Typeface.NORMAL); }
    }

    private void setViewVisibility(ModelReceipt.DataBean.HolderRideInfoBean.StaticValuesBean dataBean, TextView highlighted_text, TextView highlighted_smalltext, TextView value_text){
        if(dataBean.isHighlighted_visibility()){ highlighted_text.setVisibility(View.VISIBLE); }
        else{ highlighted_text.setVisibility(View.GONE); }
        if(dataBean.isSmall_text_visibility()){ highlighted_smalltext.setVisibility(View.VISIBLE); }
        else{ highlighted_smalltext.setVisibility(View.GONE); }

        if(dataBean.isValue_textvisibility()){ value_text.setVisibility(View.VISIBLE); }
        else{ value_text.setVisibility(View.GONE); }

    }

    private void setViewColor(ModelReceipt.DataBean.HolderRideInfoBean.StaticValuesBean dataBean, TextView highlighted_text, TextView highlighted_smalltext, TextView value_text){
        highlighted_text.setTextColor(Color.parseColor("#"+dataBean.getHighlighted_text_color()));
        highlighted_smalltext.setTextColor(Color.parseColor("#"+dataBean.getSmall_text_color()));
        value_text.setTextColor(Color.parseColor("#"+dataBean.getValue_text_color()));
    }


    private void setViewData(ModelReceipt.DataBean.HolderRideInfoBean.StaticValuesBean dataBean, TextView highlighted_text, TextView highlighted_smalltext, TextView value_text) {
        highlighted_text.setText(""+dataBean.getHighlighted_text());
        highlighted_smalltext.setText(""+dataBean.getSmall_text());
        value_text.setText(""+dataBean.getValue_text());
    }

    private void openPickerDialog(int type) {
        SELECTED_IMAGE_TYPE = type;
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        
        builderSingle.setTitle("   Comprovante de entrega");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,
                android.R.layout.select_dialog_singlechoice);
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
                        // cameraTask();
                    } catch (Exception e) {
                        
                    }
                } else if (which == 1) {
                    // Intent i1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    // i1.setType("image/*");
                    // startActivityForResult(i1, GALLERY_PICKER);
                }
                dialog.dismiss();
            }
        });
        builderSingle.show();
    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void cameraTask() throws Exception {
        if (EasyPermissions.hasPermissions(context, Manifest.permission.CAMERA)) {
            try { // Tenha permissão? então faça!
                values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "Nova foto");
                values.put(MediaStore.Images.Media.DESCRIPTION, "Da câmera");
                imageUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CAMERS_PICKER);
            } catch (Exception e) {
                Log.e("", "Falha cameraTask()" + e.getMessage());
            }
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera), RC_CAMERA_PERM,
                    Manifest.permission.CAMERA);
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
                case Config.RC_PICK_IMAGES:
                    try {
                        ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
                        // comppressImageFile(Uri.parse(images.get(0).getPath()));
                    } catch (Exception e) {

                    }

                    // CropImage.activity(Uri.fromFile(new File("" +
                    // images.get(0).getPath()))).start(getActivity());
                    // if (FLAG == 1){
                    // numberPlateImage.setImageBitmap(AppUtils.handleSamplingAndRotationBitmap(context,
                    // images.get(0).getPath()));
                    // COMPRESSES_IMAGE_NUMBER_PLATE =images.get(0).getPath();
                    // }else if (FLAG == 2){
                    // cheque_image.setImageBitmap(AppUtils.handleSamplingAndRotationBitmap(context,images.get(0).getPath()));
                    // COMPRESS_IMAGE_CHEQUE_IMAGE =images.get(0).getPath();
                    // }else if (FLAG == 3){
                    // vehicleImage.setImageBitmap(AppUtils.handleSamplingAndRotationBitmap(context,
                    // images.get(0).getPath()));
                    // COMPRESSES_IMAGE_VEHICLE =images.get(0).getPath();
                    // }

                    break;
                case GALLERY_PICKER:
                    if (resultCode != 0) {
                        ImageCompressMode imageCompressMode = new ImageCompressMode(context);
                        if (SELECTED_IMAGE_TYPE == PICK_FOR_CAR) {
                            vehicleImage.setImageBitmap(null);
                            vehicleImage.setBackgroundDrawable(null);
                            Glide.with(context).load(data.getData()).into(vehicleImage);
                            COMPRESSES_IMAGE_VEHICLE = imageCompressMode
                                    .compressImage(getRealPathFromURI(data.getData()));
                        } else if (SELECTED_IMAGE_TYPE == PICK_FOR_NUMBER_PLATE) {
                            // numberPlateImage.setImageBitmap(null);
                            // numberPlateImage.setBackgroundDrawable(null);
                            // Glide.with(context).load(data.getData()).into(numberPlateImage);
                            // COMPRESSES_IMAGE_NUMBER_PLATE = imageCompressMode
                            //         .compressImage(getRealPathFromURI(data.getData()));
                        } else if (SELECTED_IMAGE_TYPE == PICK_FOR_CHEQUE) {
                            // cheque_image.setImageBitmap(null);
                            // cheque_image.setBackgroundDrawable(null);
                            // Glide.with(context).load(data.getData()).into(cheque_image);
                            // COMPRESS_IMAGE_CHEQUE_IMAGE = imageCompressMode
                            //         .compressImage(getRealPathFromURI(data.getData()));
                        }
                    }

                    break;
                case CAMERS_PICKER:

                    thumbnail = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
                    ImageCompressMode imageCompressModee = new ImageCompressMode(context);

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
                        // numberPlateImage.setImageBitmap(thumbnail);

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

                        // numberPlateImage.setImageBitmap(rotatedBitmap);

                        COMPRESSES_IMAGE_NUMBER_PLATE = imageCompressModee.compressImage(imagePath);

                    } else if (SELECTED_IMAGE_TYPE == PICK_FOR_CHEQUE) {
                        // Glide.with(getContext())
                        //         .load(MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri))
                        //         .into(cheque_image);
                        // COMPRESS_IMAGE_CHEQUE_IMAGE = imageCompressModee.compressImage(imagePath);
                    }
                    break;
            }
        } catch (Exception e) {

        }

    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
