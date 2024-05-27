package com.tkx.driver;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tkx.driver.activities.JunoPaymentWebView;
import com.tkx.driver.activities.enterCardDetails.EnterCardDetailsActivity;
import com.tkx.driver.activities.modeljunomoney;
import com.tkx.driver.baseClass.BaseActivity;
import com.tkx.driver.currentwork.API_S;
import com.tkx.driver.currentwork.IntentKeys;
import com.tkx.driver.customization.paystack.PayWithPaystackActivity;
import com.tkx.driver.log.ApporioLog;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.models.ModelActiveSubscription;
import com.tkx.driver.models.ModelAddMoney;
import com.tkx.driver.models.ModelJunoPayment;
import com.tkx.driver.models.ModelViewCards;
import com.tkx.driver.samwork.ApiManager;
import com.sam.placer.PlaceHolderView;
import com.sam.placer.annotations.Click;
import com.sam.placer.annotations.Layout;
import com.sam.placer.annotations.Resolve;
import com.sam.placer.annotations.View;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardListActivity extends BaseActivity implements ApiManager.APIFETCHER {

    @BindView(R.id.ll_back_card)
    LinearLayout llBackCard;
    @BindView(R.id.add_new)
    TextView addNew;
    @BindView(R.id.placeholder)
    PlaceHolderView placeholder;
    @BindView(R.id.swiperefreshLayout)
    SwipeRefreshLayout swiperefreshLayout;
    @BindView(R.id.root)
    LinearLayout root;

    SessionManager sessionManager;
    ApiManager apiManager;
    private final String TAG = "CardListActivity";
    private ProgressDialog progressDialog;
    private String AMOUNT = "", sub_id,payment_method_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_card_list);
        ButterKnife.bind(this);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(this.getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        sessionManager = new SessionManager(this);
        apiManager = new ApiManager(this,this);
        try {
            AMOUNT = "" + getIntent().getExtras().getString("" + IntentKeys.TOP_UP_AMOUNT);
        } catch (Exception e) {
        }

        try {
            sub_id = getIntent().getExtras().getString("sub_id");
            payment_method_id=  getIntent().getExtras().getString("payment_method_id");
        }catch (Exception e){

        }


        llBackCard.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                finish();
            }
        });

        swiperefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    callAPI();
                } catch (Exception e) {
                    Log.d("" + TAG, "Exception caught while calling API " + e.getMessage());
                }
            }
        });


        addNew.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                if (BuildConfig.Build_varient.equals("DudesDudettesdriver")||BuildConfig.Build_varient.equals("JudahDriver")){
                    startActivity(new Intent(CardListActivity.this, PayWithPaystackActivity.class));
                }else if (BuildConfig.Build_varient.equals("tkx")){

                    progressDialog.show();
                    HashMap<String, String> datas = new HashMap<>();
                    datas.put("type", "2");
                    try {
                        apiManager._post("" + API_S.Tags.JUNO_PAYMNET, "" + API_S.Endpoints.JUNO_PAYMNET, datas, sessionManager);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // startActivity(new Intent(CardListActivity.this, PayWithPayStackActivity.class));
                }

                else {
                    startActivity(new Intent(CardListActivity.this, EnterCardDetailsActivity.class));
                }




            }
        });


    }


    private void callAPI() throws Exception {
        HashMap<String, String> data = new HashMap<>();
        if (BuildConfig.Build_varient.equals("DudesDudettesdriver")||BuildConfig.Build_varient.equals("JudahDriver")){
            data.put("payment_option", "PAYSTACK");
        }
        else  if (BuildConfig.Build_varient.equals("tkx")){
            data.put("payment_option", "JUNO");
        }
        else {
            data.put("payment_option", "STRIPE");
        }
        apiManager._post(API_S.Tags.VIEW_CARDS, API_S.Endpoints.VIEW_CARDS, data, sessionManager);
        placeholder.removeAllViews();
    }


    @Override
    public void onAPIRunningState(int a, String APINAME) {
        switch (APINAME) {
            case API_S.Tags.ADD_MONEY_IN_WALLET:
                if (a == ApiManager.APIFETCHER.KEY_API_IS_STARTED) {
                    progressDialog.show();
                } else if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                break;
            case API_S.Tags.VIEW_CARDS:
                if (a == ApiManager.APIFETCHER.KEY_API_IS_STARTED) {
                    swiperefreshLayout.setRefreshing(true);
                } else {
                    swiperefreshLayout.setRefreshing(false);
                }
                break;
            case API_S.Tags.MAKE_PAYMENT_WITH_CARD:
                if (a == ApiManager.APIFETCHER.KEY_API_IS_STARTED) {
                    swiperefreshLayout.setRefreshing(true);
                } else {
                    swiperefreshLayout.setRefreshing(false);
                }
                break;
            case API_S.Tags.ACTIVE_SUBSCRIPTION:
                if (a == ApiManager.APIFETCHER.KEY_API_IS_STARTED) {
                    swiperefreshLayout.setRefreshing(true);
                } else {
                    swiperefreshLayout.setRefreshing(false);
                }
                break;
        }
    }

    @Override
    public void onFetchComplete(Object script, String APINAME) {
        try {

            switch (APINAME) {
                case API_S.Tags.VIEW_CARDS:
                    placeholder.getViewAdapter().notifyDataSetChanged();
                    placeholder.removeAllViews();
                    ModelViewCards modelViewCards = SingletonGson.getInstance().fromJson("" + script, ModelViewCards.class);
                    for (int i = 0; i < modelViewCards.getData().size(); i++) {
                        placeholder.addView(new HolderCard(modelViewCards.getData().get(i)));
                    }
                    break;

                case API_S.Tags.JUNO_PAYMNET:

                    progressDialog.dismiss();

                    ModelJunoPayment modelJunoPayment = SingletonGson.getInstance().fromJson("" + script, ModelJunoPayment.class);
                    startActivityForResult(new Intent(CardListActivity.this, JunoPaymentWebView.class).putExtra("url", ""+modelJunoPayment.getData())
                            .putExtra("sucess_url",""+modelJunoPayment.getSuccessUrl()).putExtra("fail_url",""+modelJunoPayment.getFailUrl()), 300);

                    break;

                case API_S.Tags.JUNO_PAYMENT:
                    modeljunomoney modeljunomoney = SingletonGson.getInstance().fromJson("" + script, modeljunomoney.class);
                    if (modeljunomoney.getResult().equals("1")) {
                        HashMap<String, String> data = new HashMap<>();
                        data.put("amount", "" + AMOUNT);
                        data.put("payment_method", "2");  // 1 for cash 2 for non cash
                        data.put("receipt_number", "Application");
                        data.put("description", "sending as per demo card only");
                        apiManager._post("" + API_S.Tags.ADD_MONEY_IN_WALLET, "" + API_S.Endpoints.ADD_MONEY_IN_WALLET, data, sessionManager);

                    } else {
                        Toast.makeText(this, "" + modeljunomoney.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case API_S.Tags.MAKE_PAYMENT_WITH_CARD:
                    ModelAddMoney modelAddMoney2 = SingletonGson.getInstance().fromJson("" + script, ModelAddMoney.class);
                    if (modelAddMoney2.getResult().equals("1")) {
                        Toast.makeText(this, "" + modelAddMoney2.getMessage(), Toast.LENGTH_SHORT).show();

                        if(getIntent().getStringExtra("ADD_MONEY").equals("2")){
                            HashMap<String, String> datas = new HashMap<>();
                            datas.put("subscription_package_id", "" + sub_id);
                            datas.put("payment_method_id", "" + payment_method_id);
                            datas.put("payment_status", "" +"SUCCESS");
                            try {
                                apiManager._post(""+API_S.Tags.ACTIVE_SUBSCRIPTION,""+API_S.Endpoints.ACTIVE_SUBSCRIPTION,datas,sessionManager);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else {
                            HashMap<String, String> data = new HashMap<>();
                            data.put("amount", "" + AMOUNT);
                            data.put("payment_method", "2");  // 1 for cash 2 for non cash
                            data.put("receipt_number", "Application");
                            data.put("description", "sending as per demo card only");
                            apiManager._post("" + API_S.Tags.ADD_MONEY_IN_WALLET, "" + API_S.Endpoints.ADD_MONEY_IN_WALLET, data, sessionManager);
                        }

                    } else {
                        Toast.makeText(this, "" + modelAddMoney2.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    break;

                case API_S.Tags.ADD_MONEY_IN_WALLET:
                   // ModelAddMoney modelAddMoney = SingletonGson.getInstance().fromJson("" + script, ModelAddMoney.class);
                    Toast.makeText(this, "Recarga feita com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case API_S.Tags.DELETE_CARD:
                    ModelAddMoney modelAddMoney1 = SingletonGson.getInstance().fromJson("" + script, ModelAddMoney.class);
                    Toast.makeText(this, "" + modelAddMoney1.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                    break;

                case API_S.Tags.ACTIVE_SUBSCRIPTION:
                    ModelActiveSubscription modelActiveSubscription = SingletonGson.getInstance().fromJson("" + script, ModelActiveSubscription.class);
                    Toast.makeText(this, "" + modelActiveSubscription.getMessage(), Toast.LENGTH_SHORT).show();
                    CardListActivity.this.finish();
                    break;

            }


        } catch (Exception e) {
            Log.d("" + TAG, "Exception caught while calling API " + e.getMessage());
        }
    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {
        Toast.makeText(CardListActivity.this, ""+script, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            callAPI();
        } catch (Exception e) {
            Log.d("" + TAG, "Exception caught while calling API " + e.getMessage());
        }

    }

    @Layout(R.layout.holder_card_item)
    class HolderCard {

        @View(R.id.card_image)
        ImageView cardImage;
        @View(R.id.card_number)
        TextView cardNumber;
        @View(R.id.card_hlder_name)
        TextView cardHlderName;
        @View(R.id.delete_btn)
        ImageView deleteBtn;
        @View(R.id.root)
        LinearLayout root;
        @View(R.id.select_text)
        TextView select_text;


        private ModelViewCards.DataBean mData;

        public HolderCard(ModelViewCards.DataBean responseBean) {
            this.mData = responseBean;
        }


        @Resolve
        private void setdata() {
            cardNumber.setText("" + mData.getCard_number());
            cardHlderName.setText("" + mData.getExp_month() + "/" + mData.getExp_year());

            if (AMOUNT.equals("")) {
                select_text.setVisibility(android.view.View.GONE);
                deleteBtn.setVisibility(android.view.View.VISIBLE);
            } else {
                select_text.setVisibility(android.view.View.VISIBLE);
                deleteBtn.setVisibility(android.view.View.VISIBLE);
            }

            if (mData.getCard_type().equals("MASTER")) {
                cardImage.setImageResource(R.drawable.ic_master_card_vector);
            }
            if (mData.getCard_type().equals("VISA")) {
                cardImage.setImageResource(R.drawable.ic_visa_card_vector);
            }
        }


        @Click(R.id.delete_btn)
        private void onDeleteClick() {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(CardListActivity.this);
            dialog.setTitle(R.string.delete_card);
            dialog.setMessage(getString(R.string.are_you_sure_you_want_to_delete_card_) + " " + mData.getCard_number());
            dialog.setCancelable(false);
            dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    HashMap<String, String> data = new HashMap<>();
                    data.put("card_id", "" + mData.getCard_id());
                    try {
                        apiManager._post("" + API_S.Tags.DELETE_CARD, "" + API_S.Endpoints.DELETE_CARD, data, sessionManager);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    paramDialogInterface.dismiss();
                }
            }).show();
        }

        @Click(R.id.root)
        private void onRootClick() {
            if (!AMOUNT.equals("")) {
                try {
                    Log.i(TAG, "Now GPS Status = " + false + ", Now Showing Dialog");
                    AlertDialog.Builder builder = new AlertDialog.Builder(CardListActivity.this);
                    builder.setTitle(R.string.are_you_sure);
                    builder.setMessage(R.string.click_ok_to_redeem_card)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    if (BuildConfig.Build_varient.equals("tkx")){
                                        try {
                                            HashMap<String, String> data = new HashMap<>();
                                            data.put("card_id", "" + mData.getCard_id());
                                            data.put("amount", "" + AMOUNT);
                                            data.put("type", "2");

                                            apiManager._post("" + API_S.Tags.JUNO_PAYMENT, "" + API_S.Endpoints.JUNO_PAYMENT, data, sessionManager);

                                            dialog.dismiss();
                                        } catch (Exception e) {
                                           // ApporioLog.logE("" + TAG, "Exception caught while calling API " + e.getMessage());
                                        }
                                    }else {
                                        try {
                                            HashMap<String, String> data = new HashMap<>();
                                            data.put("card_id", "" + mData.getCard_id());
                                            data.put("amount", "" + AMOUNT);
                                            data.put("currency", "" + sessionManager.getCurrencyCode());

                                            apiManager._post("" + API_S.Tags.MAKE_PAYMENT_WITH_CARD, "" + API_S.Endpoints.MAKE_PAYMENT_WITH_CARD, data, sessionManager);

                                        } catch (Exception e) {
                                            Log.d("" + TAG, "Exception caught while calling API " + e.getMessage());
                                        }
                                    }

                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                } catch (Exception e) {
                    Toast.makeText(CardListActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
