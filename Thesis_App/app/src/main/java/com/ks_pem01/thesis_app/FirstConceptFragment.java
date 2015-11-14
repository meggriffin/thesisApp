package com.ks_pem01.thesis_app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FirstConceptFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FirstConceptFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstConceptFragment extends Fragment {

    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;


    private float totalLiters = 2.6f;
    private float actualDrinkingLevel;
    private float actualAmount = 0.15f;
    private float newAmount;
    private float fillFactor;
    private float defaultValue = 15f;
    private double actualLiters;

    private List<FloatingActionMenu> menus = new ArrayList<>();
    private Handler mUiHandler = new Handler();

    CircleDisplay cd;

    private TextView actualLevel;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;



    public FirstConceptFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstConceptFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstConceptFragment newInstance(String param1, String param2) {
        FirstConceptFragment fragment = new FirstConceptFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_concept, container, false);
        cd = (CircleDisplay) view.findViewById(R.id.circleDisplay);
        cd.setAnimDuration(3000);
        cd.setValueWidthPercent(45f);
        cd.setTextSize(28f);
        cd.setColor(Color.parseColor("#29B6F6"));
        cd.setDrawText(true);
        cd.setDrawInnerCircle(true);
        cd.setFormatDigits(1);
        cd.setTouchEnabled(false);
        cd.setUnit("%");
        cd.setStepSize(0.5f);
        cd.increaseValue(0f, 15f, 100f);


        final FloatingActionMenu menu1 = (FloatingActionMenu) view.findViewById(R.id.menu1);

        menu1.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu1.toggle(true);
            }
        });

        menus.add(menu1);

        int delay = 400;
        for (final FloatingActionMenu menu : menus) {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    menu.showMenuButton(true);
                }
            }, delay);
            delay += 150;
        }
        menu1.setClosedOnTouchOutside(true);


        fab1 =  (FloatingActionButton) view.findViewById(R.id.fab1);
        fab2 =  (FloatingActionButton) view.findViewById(R.id.fab2);
        fab3 =  (FloatingActionButton) view.findViewById(R.id.fab3);


        fab1.setOnClickListener(clickListener);
        fab2.setOnClickListener(clickListener);
        fab3.setOnClickListener(clickListener);

        System.out.println("FABS initialized");

        actualLevel = (TextView) view.findViewById(R.id.actualStatus);

        calculateActualLevel(actualAmount);
        updateTextLabel(actualDrinkingLevel);
        actualLiters = RoundTo2Decimals(actualDrinkingLevel);
        actualLevel.setText(actualLiters + "l");


        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            float previousAmount = actualAmount*100f;

            switch (v.getId()) {
                case R.id.fab1:
                    fillFactor = 0.038f;
                    calculateContent(fillFactor);
                    System.out. println("Aktuelle Tagesmenge nach fab1: " + actualAmount + " %");
                    calculateActualLevel(actualAmount);
                    System.out.println("Aktueller Trinkstatus: " + actualDrinkingLevel + " Liter");
                    cd.increaseValue(previousAmount, actualAmount * 100f, 100f);
                    updateTextLabel(actualDrinkingLevel);
                    break;
                case R.id.fab2:
                    fillFactor = 0.096f;
                    calculateContent(fillFactor);
                    System.out.println("Aktuelle Tagesmenge nach fab2: " + actualAmount + " %");
                    calculateActualLevel(actualAmount);
                    System.out.println("Aktueller Trinkstatus: " + actualDrinkingLevel + " Liter");
                    cd.increaseValue(previousAmount, actualAmount * 100f, 100f);
                    updateTextLabel(actualDrinkingLevel);
                    break;
                case R.id.fab3:
                    fillFactor = 0.192f;
                    calculateContent(fillFactor);
                    System.out.println("Aktuelle Tagesmenge nach fab3: " + actualAmount + " %");
                    calculateActualLevel(actualAmount);
                    System.out.println("Aktueller Trinkstatus: " + actualDrinkingLevel + " Liter");
                    cd.increaseValue(previousAmount, actualAmount * 100f, 100f);
                    updateTextLabel(actualDrinkingLevel);
                    break;

            }

        }
    };

    public float calculateContent(float fillFactor){
        newAmount = actualAmount + fillFactor;
        actualAmount = newAmount;
        actualAmount = Math.round(actualAmount * 100f) / 100.0f;
        return actualAmount;
    }

    public float calculateActualLevel(float actualAmount){
        actualDrinkingLevel = actualAmount * totalLiters;
        actualDrinkingLevel = Math.round(actualDrinkingLevel * 100f) / 100.0f;

        return actualDrinkingLevel;
    }

    double RoundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
    }

    public void updateTextLabel(float actualDrinkingLevel){
        actualLiters = RoundTo2Decimals(actualDrinkingLevel);
        actualLevel.setText(actualLiters + "l");
    }


}
