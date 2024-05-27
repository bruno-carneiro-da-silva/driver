package com.tkx.driver.currentwork;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tkx.driver.R;
import com.tkx.driver.baseClass.BaseFragment;
import com.sam.placer.PlaceHolderView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentActiveRides extends BaseFragment {

    @BindView(R.id.place_holder)
    PlaceHolderView placeHolder;


    public static FragmentActiveRides newInstance() {
        FragmentActiveRides fragment = new FragmentActiveRides();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_request_fragments, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }



}
