package com.tkx.driver;

import android.content.Context;
import android.widget.TextView;

import com.sam.placer.annotations.Layout;
import com.sam.placer.annotations.Resolve;
import com.sam.placer.annotations.View;

@Layout(R.layout.multiple_stop_view)
public class MultipleStopAdapter {

    Context context;
    String dest_name;

    @View(R.id.txt_stops)
    TextView txt_stops;

    public MultipleStopAdapter(Context context, String name){

        this.context = context;
        dest_name = name;

    }


    @Resolve
    private void onResolved() {

        txt_stops.setText(dest_name);

    }

}
