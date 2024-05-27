package com.tkx.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tkx.driver.NewEarningModule.ModelCashOut;
import com.tkx.driver.NewEarningModule.ModelCashoutHistory;
import com.tkx.driver.currentwork.API_S;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.samwork.ApiManager;
import com.sam.placer.PlaceHolderView;
import com.sam.placer.annotations.Layout;
import com.sam.placer.annotations.Resolve;
import com.sam.placer.annotations.View;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CashOutActivity extends AppCompatActivity implements  ApiManager.APIFETCHER{

    @BindView(R.id.placeholder)
    PlaceHolderView placeholder;
    @BindView(R.id.back)
    ImageView back;
    //    @BindView(R.id.swiperefreshLayout)
//    SwipeRefreshLayout swiperefreshLayout;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.ed_enter_money)
    EditText ed_enter_money;
    @BindView(R.id.txt_add_money)
    Button txt_add_money;



    private ApiManager apiManager;
    private SessionManager sessionManager;
    ProgressDialog progressDialog;
    ModelCashOut modelCashOut;
    ModelCashoutHistory cashoutHistory;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cash_out);
        ButterKnife.bind(this);

        apiManager = new ApiManager(this,this);
        sessionManager = new SessionManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        ButterKnife.bind(this);

        back.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                finish();
            }
        });

        try {

            progressDialog.show();
            apiManager._post(""+ API_S.Tags.CASHOUT_HISTORY, ""+API_S.Endpoints.CASHOUT_HISTORY,null,sessionManager);
            placeholder.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
        }


        txt_add_money.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                HashMap<String,String> data = new HashMap<>();
                data.put("amount",""+ed_enter_money.getText().toString());
                try {

                    apiManager._post(""+ API_S.Tags.CASH_OUT, ""+API_S.Endpoints.CASH_OUT,data,sessionManager);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });



    }

    @Override
    public void onAPIRunningState(int a, String APINAME) {

        try {

            if (APINAME.equals(API_S.Tags.CASH_OUT)) {
                if (a == ApiManager.APIFETCHER.KEY_API_IS_STARTED) {
                    progressDialog.show();
                } else if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }catch (Exception e){

        }


    }

    @Override
    public void onFetchComplete(Object script, String APINAME) {
        try{
            switch (APINAME)
            {

                case API_S.Tags.CASHOUT_HISTORY:
                    progressDialog.dismiss();
                    placeholder.getViewAdapter().notifyDataSetChanged();
                    placeholder.removeAllViews();
                    cashoutHistory = SingletonGson.getInstance().fromJson(""+script, ModelCashoutHistory.class);

                    for(int i=0;i<cashoutHistory.getData().size();i++)
                    {
                        placeholder.addView(new HolderTranscation(cashoutHistory.getData().get(i)));
                    }
                    break;

                case API_S.Tags.CASH_OUT:
                    ed_enter_money.setText("");
                    modelCashOut = SingletonGson.getInstance().fromJson(""+script, ModelCashOut.class);
                    if(modelCashOut.getResult().equals("1")){
                        Toast.makeText(this, ""+modelCashOut.getMessage(), Toast.LENGTH_SHORT).show();

                        try {
                            apiManager._post(""+ API_S.Tags.CASHOUT_HISTORY, ""+API_S.Endpoints.CASHOUT_HISTORY,null,sessionManager);
                            placeholder.removeAllViews();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    break;
            }

        }catch (Exception e){

        }



    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {
        try {
            switch (APINAME)
            {
                case API_S.Tags.CASH_OUT:
                    ed_enter_money.setText("");
                    Toast.makeText(this, ""+script, Toast.LENGTH_SHORT).show();
                    break;
                case API_S.Tags.CASHOUT_HISTORY:
                    progressDialog.dismiss();
                    Toast.makeText(this, ""+cashoutHistory.getMessage(), Toast.LENGTH_SHORT).show();

                    break;
            }




        }catch (Exception e)
        {

        }

    }


    @Layout(R.layout.holder_transaction_layout)
    class HolderTranscation{
        @View(R.id.payment_heading)
        TextView payment_heading;
        @View(R.id.payment_date)
        TextView payment_date;
        @View(R.id.payment_valur_txt)
        TextView payment_valur_txt;
        ModelCashoutHistory.DataBean dataBean;

        public HolderTranscation(ModelCashoutHistory.DataBean dataBean) {
            this.dataBean = dataBean;
        }
        @Resolve
        public void setData()
        {
            payment_heading.setText(""+dataBean.getCashout_status());
            payment_date.setText(""+dataBean.getUpdated_at());
            payment_valur_txt.setText(""+dataBean.getAmount());
        }

    }


}
