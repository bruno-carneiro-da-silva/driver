package com.tkx.driver.holder;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.tkx.driver.PersonalDocumentActivity;
import com.tkx.driver.R;
import com.tkx.driver.VehicleListActivity;
import com.tkx.driver.activities.subscriptionModule.SubscriptionModuleList;
import com.tkx.driver.models.ModelMainScreen;
import com.tkx.driver.models.SampleModel;
import com.tkx.driver.wallet.WalletActivity;
import com.sam.placer.annotations.Click;
import com.sam.placer.annotations.Layout;
import com.sam.placer.annotations.Resolve;
import com.sam.placer.annotations.View;

import java.util.List;

@Layout(R.layout.holder_subscription_layout)
public class HolderSubscription {

    @View(R.id.head_line)
    TextView headLine;
    @View(R.id.message_text)
    TextView messageText;
    private ModelMainScreen.DataBean.AdminMessageBean mData;
    private List<SampleModel.DataBean.DocumentMessageBean> document_message;
    private Context context;

    public HolderSubscription(Context context, ModelMainScreen.DataBean.AdminMessageBean adminMessageBean) {
        this.mData = adminMessageBean;
        this.context = context ;
    }


    @Resolve
    private void setdata(){
        headLine.setText(""+mData.getHeadline());
        messageText.setText(""+mData.getMessage());
    }

    @Click(R.id.root)
    private void setOnClick(){
        if(mData.getAction().equals("PERSONAL_DOC_LIST_SCREEN")){
            context.startActivity(new Intent(context, PersonalDocumentActivity.class));
        }if(mData.getAction().equals("WALLET_SCREEN")){
            context.startActivity(new Intent(context, WalletActivity.class));
        }if(mData.getAction().equals("VEHICLE_LIST_SCREEN")){
            context.startActivity(new Intent(context, VehicleListActivity.class));
        }
        if(mData.getAction().equals("SUBSCRIPTION_BUY")){
            context.startActivity(new Intent(context, SubscriptionModuleList.class));
        }
        if(mData.getAction().equals("SUBSCRIPTION_EXPIRE")){
            context.startActivity(new Intent(context, SubscriptionModuleList.class));
        }

    }
}
