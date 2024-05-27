package com.tkx.driver.rating_module;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.tkx.driver.R;
import com.tkx.driver.manager.SessionManager;
import com.tkx.driver.rating_module.rating_holders.DailyRaing_selectdate;
import com.tkx.driver.samwork.ApiManager;
import com.sam.placer.PlaceHolderView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DailyRatingFragment extends android.app.Fragment implements DatePickerDialog.OnDateSetListener, ApiManager.APIFETCHER, DailyRaing_selectdate.Dailty_rating_listner{
    /*
     * Renomeie argumentos de parâmetro, escolha nomes que correspondam
     * os parâmetros de inicialização do fragmento, e. ARG_ITEM_NUMBER
     */
    
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ApiManager apiManager;
    SessionManager sessionManager;

    @BindView(R.id.placeholder_rating)
    PlaceHolderView placeHolderRating;

    /*
     * Rename and change types of parameters
     */

    private String mParam1;
    private String mParam2;
    static Context mcontext;
    private static final int Date_id = 0;

    private OnFragmentInteractionListener mListener;

    public DailyRatingFragment() {
        /*
         * Required empty public constructor
         */        
    }

    /*
     * Rename and change types and number of parameters
     */
    
    public static DailyRatingFragment newInstance(String param1, String param2,Context context) {
        DailyRatingFragment fragment = new DailyRatingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        mcontext = context;
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            apiManager = new ApiManager(this,getActivity());
            sessionManager = new SessionManager(getActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rating_fragment, container, false);
        ButterKnife.bind(this, rootView);

        /* Inflar o layout para este fragmento  */
        
        placeHolderRating.addView(new DailyRaing_selectdate(DailyRatingFragment.this, getActivity()));
        return rootView;


    }

    /*
     * Renomeie o método, atualize o argumento e o método de gancho no evento
     * da interface do usuário
     */
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//
//
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    public void onAPIRunningState(int a, String APINAME) {

    }

    @Override
    public void onFetchComplete(Object script, String APINAME) {

    }

    @Override
    public void onFetchResultZero(String script, String APINAME) {

    }

    @Override
    public void OnSelectDateClick() {


        Calendar calendar = Calendar.getInstance();
       com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance((com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener) getContext(), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dpd.setMinDate(calendar);
        dpd.setCancelText("" + getResources().getString(R.string.cancel));
        dpd.setOkText("" + getResources().getString(R.string.ok));
        dpd.setAccentColor(DailyRatingFragment.this.getResources().getColor(R.color.colorPrimary));
        dpd.show(getFragmentManager(),"dialogdatepicker");


    }

    public interface OnFragmentInteractionListener {
        /*
         * Atualizar tipo e nome do argumento
         */        
        void onFragmentInteraction(Uri uri);
    }
}
