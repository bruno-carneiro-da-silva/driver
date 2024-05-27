package com.tkx.driver.holder;


import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tkx.driver.R;
import com.tkx.driver.models.ModelSubscriptionList;
import com.bumptech.glide.Glide;
import com.sam.placer.annotations.Click;
import com.sam.placer.annotations.Layout;
import com.sam.placer.annotations.Resolve;
import com.sam.placer.annotations.View;

import java.util.List;

@Layout(R.layout.holder_subscription_drivcer)
public class HolderForSubscriptionList {

    @View(R.id.imgPackageName)
    ImageView imgPackageName;
    @View(R.id.tvPackageName)
    TextView tvPackageName;
    @View(R.id.tvPackagePrice)
    TextView tvPackagePrice;
    @View(R.id.ll_activated)
    LinearLayout ll_activated;

    private Context context;
    ModelSubscriptionList.DataBean dataBean;
    List<ModelSubscriptionList.PaymentMethodBean> payment_method;

    public HolderForSubscriptionList(Context context, ModelSubscriptionList.DataBean dataBean, List<ModelSubscriptionList.PaymentMethodBean> payment_method) {
        this.context = context;
        this.dataBean = dataBean;
        this.payment_method = payment_method;
    }

    @Resolve
    public void OnResole() {
        tvPackageName.setText("" + dataBean.getName());
        tvPackagePrice.setText("" + dataBean.getShow_price());
        Glide.with(context).load( "" + dataBean.getImage()).into(imgPackageName);

     //   ll_activated.setVisibility(dataBean.() ? android.view.View.VISIBLE : android.view.View.GONE);
    }

    @Click(R.id.root)
    public void onClick() {
//        context.startActivity(new Intent(context, SubscriptionModuleDetails.class)
//                .putExtra("modelSubscription",(Serializable))
//                .putExtra("methods", (Serializable) payment_method)
//                .putExtra("details", (Serializable) dataBean));
    }

}
