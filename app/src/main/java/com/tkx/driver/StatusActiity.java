package com.tkx.driver;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tkx.driver.R;
import com.tkx.driver.baseClass.BaseActivity;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import customviews.typefacesviews.TypefaceDosisRegular;

/**
 * Created by cabmedriver 9/11/2017.
 */

public class StatusActiity extends BaseActivity {
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.message)
    TypefaceDosisRegular message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_activity);
        ButterKnife.bind(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        try{
            Glide.with(this).load(""+getIntent().getExtras().getString("image")).into(image);
        }catch
                (Exception e){}
        try{message.setText(""+getIntent().getExtras().getString("message"));}catch (Exception e){}

    }


}
