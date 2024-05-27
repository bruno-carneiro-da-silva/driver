package com.tkx.driver.holder;


import android.widget.TextView;

import com.tkx.driver.R;
import com.tkx.driver.models.ModelSpecificTripDetails;
import com.sam.placer.annotations.Layout;
import com.sam.placer.annotations.Resolve;

@Layout(R.layout.holder_child_details)
public class HolderChildDetails {


    @com.sam.placer.annotations.View(R.id.tvChildname)
    TextView tvChildname;
    @com.sam.placer.annotations.View(R.id.tvChildNumber) TextView tvChildNumber;
    ModelSpecificTripDetails.DataBeanXXXXXX.HolderFamilyMemberBean holderFamilyMemberBean;


    public HolderChildDetails(ModelSpecificTripDetails.DataBeanXXXXXX.HolderFamilyMemberBean holderFamilyMemberBean){
        this.holderFamilyMemberBean = holderFamilyMemberBean;
    }

    @Resolve
    private void setData (){
        try{
            setDataAccordingy();
        }catch (Exception e){
        }

    }

    private void setDataAccordingy() {

        tvChildname.setText(holderFamilyMemberBean.getName());
        tvChildNumber.setText(holderFamilyMemberBean.getPhoneNumber());
    }

}
