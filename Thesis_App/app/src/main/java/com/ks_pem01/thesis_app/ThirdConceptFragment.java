package com.ks_pem01.thesis_app;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ThirdConceptFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ThirdConceptFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThirdConceptFragment extends Fragment {

    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;

    private ImageView concept01;
    private ImageView concept02;
    private ImageView concept03;
    private ImageView concept04;

//    private TransitionDrawable crossfader01;
//    private TransitionDrawable crossfader02;
//    private TransitionDrawable crossfader03;


    private float totalLiters = 2.6f;
    private float actualDrinkingLevel;
    private float actualAmount = 0.15f;
    private float newAmount;
    private float fillFactor;
    private float defaultValue = 15f;
    private double actualLiters;

    private TextView actualLevel;

    private List<FloatingActionMenu> menus = new ArrayList<>();
    private Handler mUiHandler = new Handler();

    private int counter = 1;
    private int buttonCounter = 1;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ThirdConceptFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThirdConceptFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThirdConceptFragment newInstance(String param1, String param2) {
        ThirdConceptFragment fragment = new ThirdConceptFragment();
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
        View view = inflater.inflate(R.layout.fragment_third_concept, container, false);


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


        concept01 = (ImageView) view.findViewById(R.id.concept_three_01);
        concept02 = (ImageView) view.findViewById(R.id.concept_three_02);
        concept03 = (ImageView) view.findViewById(R.id.concept_three_03);
        concept04 = (ImageView) view.findViewById(R.id.concept_three_04);

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

            switch (v.getId()) {
                case R.id.fab1:
                    buttonCounter++;
                    System.out. println("FAAAB 1");
                    fillFactor = 0.038f;
                    calculateContent(fillFactor);
                    System.out. println("Ergebnis nach fab1: " + actualAmount);
                    calculateActualLevel(actualAmount);
                    System.out.println("Aktueller Trinkstatus: " + actualDrinkingLevel);
                    doTransition();
                    updateTextLabel(actualDrinkingLevel);
                    break;
                case R.id.fab2:
                    buttonCounter++;
                    System.out. println("FAAAB 2");
                    fillFactor = 0.096f;
                    calculateContent(fillFactor);
                    System.out. println("Ergebnis nach fab2: " + actualAmount);
                    calculateActualLevel(actualAmount);
                    System.out.println("Aktueller Trinkstatus: " + actualDrinkingLevel);
                    doTransition();
                    updateTextLabel(actualDrinkingLevel);
                    break;
                case R.id.fab3:
                    buttonCounter++;
                    System.out. println("FAAAB 3");
                    fillFactor = 0.192f;
                    calculateContent(fillFactor);
                    System.out. println("Ergebnis nach fab3: " + actualAmount);
                    calculateActualLevel(actualAmount);
                    System.out.println("Aktueller Trinkstatus: " + actualDrinkingLevel);
                    doTransition();
                    updateTextLabel(actualDrinkingLevel);
                    break;

            }

        }
    };

    public float calculateContent(float fillFactor){
        newAmount = actualAmount + fillFactor;
        actualAmount = newAmount;
        return actualAmount;
    }

    public float calculateActualLevel(float actualAmount){
        actualDrinkingLevel = actualAmount * totalLiters;
        return actualDrinkingLevel;
    }

    public void doTransition(){
            if(buttonCounter == 2){
                System.out.println("Button Counter: " + buttonCounter);
                new Crossfader(concept04, concept03, 500).start();
            } else if (buttonCounter == 3){
                System.out.println("Button Counter: " + buttonCounter);
                new Crossfader(concept03, concept02, 500).start();
            } else if (buttonCounter == 4){
                System.out.println("Button Counter: " + buttonCounter);
                new Crossfader(concept02, concept01, 500).start();
            } else if (buttonCounter >= 5){
                return;
            }
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
