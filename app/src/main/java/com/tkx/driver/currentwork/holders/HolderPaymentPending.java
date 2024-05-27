package com.tkx.driver.currentwork.holders;


import android.content.Context;
import android.widget.TextView;

import com.tkx.driver.R;
import com.tkx.driver.models.ModelReceipt;
import com.sam.placer.annotations.Layout;
import com.sam.placer.annotations.Position;
import com.sam.placer.annotations.Resolve;

@Layout(R.layout.holder_payment_pending)
public class HolderPaymentPending {

    @com.sam.placer.annotations.View(R.id.tvPaymentStatus)
    TextView tvPaymentStatus;

    @Position
    int mPosition;
    Context context;
    ModelReceipt.DataBean.PaymentHolderBean payment_holder;

    public HolderPaymentPending(Context context, ModelReceipt.DataBean.PaymentHolderBean payment_holder) {
        this.context = context;
        this.payment_holder = payment_holder;
    }

    @Resolve
    private void setData(){
        tvPaymentStatus.setText(""+ payment_holder.getText());
    }
}
