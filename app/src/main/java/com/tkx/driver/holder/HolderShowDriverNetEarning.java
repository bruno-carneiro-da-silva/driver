package com.tkx.driver.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tkx.driver.NewEarningModule.ModelNewDriverEarning;
import com.tkx.driver.R;
import com.sam.placer.annotations.Layout;
import com.sam.placer.annotations.Resolve;

import java.util.List;
@Layout(R.layout.holder_receipt_driver_earning_layout)
public class HolderShowDriverNetEarning {

    @com.sam.placer.annotations.View(R.id.linearlayout)
    LinearLayout highllinearlayoutightedText;

    public Context context;
    private final String TAG ="HolderReceipt";
    LayoutInflater mInflater;
    List<ModelNewDriverEarning.DataBean.HolderDataBean> holder_data;

    public HolderShowDriverNetEarning(Context context, List<ModelNewDriverEarning.DataBean.HolderDataBean> holder_data) {
        this.context = context;
        this.holder_data = holder_data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Resolve
    public void setData(){
        for(int i = 0 ; i < holder_data.size() ; i ++){
            highllinearlayoutightedText.addView(getView(holder_data.get(i)));
        }
    }

    private View getView(ModelNewDriverEarning.DataBean.HolderDataBean holderDataBean) {
        View v =  mInflater.inflate(R.layout.item_driver_earnings, null, true);
        TextView highlighted_text = (TextView) v.findViewById(R.id.highlighted_text);
        TextView highlighted_smalltext = (TextView) v.findViewById(R.id.highlighted_small_text);
        TextView value_text = (TextView) v.findViewById(R.id.value_text);

        try{
            setViewData(holderDataBean, highlighted_text, highlighted_smalltext,value_text);
            setViewVisibility(holderDataBean, highlighted_text, highlighted_smalltext,value_text);
            setViewStyle(holderDataBean, highlighted_text, highlighted_smalltext,value_text);
//            setViewColor(holderDataBean, highlighted_text, highlighted_smalltext,value_text);
        }catch (Exception e){

        }


        return v;
    }

    private void setViewColor(ModelNewDriverEarning.DataBean.HolderDataBean holderDataBean, TextView highlighted_text, TextView highlighted_smalltext, TextView value_text) {

        highlighted_text.setTextColor(Color.parseColor("#"+holderDataBean.getLeft_text_color()));
        //  highlighted_smalltext.setTextColor(Color.parseColor("#"+dataBean.getSmall_text_color()));
        value_text.setTextColor(Color.parseColor("#"+holderDataBean.getRight_text_color()));

    }

    private void setViewStyle(ModelNewDriverEarning.DataBean.HolderDataBean holderDataBean, TextView highlighted_text, TextView highlighted_smalltext, TextView value_text) {
        if(holderDataBean.getLeft_text_style().equals("BOLD")){ highlighted_text.setTypeface(null, Typeface.BOLD); }
        else{ highlighted_text.setTypeface(null, Typeface.NORMAL); }


//        if(dataBean.getLeft_text_style().equals("BOLD")){ highlighted_smalltext.setTypeface(null, Typeface.BOLD); }
//        else{ highlighted_smalltext.setTypeface(null, Typeface.NORMAL); }
//


        if(holderDataBean.getLeft_text_style().equals("BOLD")){ value_text.setTypeface(null,Typeface.BOLD); }
        else{ value_text.setTypeface(null,Typeface.NORMAL); }

    }

    private void setViewVisibility(ModelNewDriverEarning.DataBean.HolderDataBean holderDataBean, TextView highlighted_text, TextView highlighted_smalltext, TextView value_text) {

        if(holderDataBean.isLeft_text_visibility()){ highlighted_text.setVisibility(View.VISIBLE); }
        else{ highlighted_text.setVisibility(View.GONE); }
//
//        if(dataBean.isSmall_text_visibility()){ highlighted_smalltext.setVisibility(View.VISIBLE); }
//        else{ highlighted_smalltext.setVisibility(View.GONE); }

        if(holderDataBean.isRight_text_visibility()){ value_text.setVisibility(View.VISIBLE); }
        else{ value_text.setVisibility(View.GONE); }

    }

    private void setViewData(ModelNewDriverEarning.DataBean.HolderDataBean holderDataBean, TextView highlighted_text, TextView highlighted_smalltext, TextView value_text) {

        highlighted_text.setText(""+holderDataBean.getLeft_text());
        //  highlighted_smalltext.setText(""+dataBean.getRight_text());
        value_text.setText(""+holderDataBean.getRight_text());
    }




}

