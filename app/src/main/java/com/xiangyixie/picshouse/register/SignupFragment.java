package com.xiangyixie.picshouse.register;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.xiangyixie.picshouse.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignupFragment} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {

    private static final String TAG = "SignupFragment";

    //private OnFragmentInteractionListener mListener;



    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup, container, false);


        //set male/female checkbox.
        final CheckBox checkBox_male = (CheckBox) view.findViewById(R.id.checkbox_male);
        final CheckBox checkBox_female = (CheckBox)view.findViewById(R.id.checkbox_female);

        if (checkBox_male.isChecked()) {
            checkBox_male.setChecked(false);
        }
        if (checkBox_female.isChecked()) {
            checkBox_female.setChecked(false);
        }


        checkBox_male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkBox_female.setChecked(false);
                }
            }
        });

        checkBox_female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkBox_male.setChecked(false);
                }
            }
        });


        return view;
    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    


    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

}
