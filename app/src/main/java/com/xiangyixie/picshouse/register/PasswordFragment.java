package com.xiangyixie.picshouse.register;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.xiangyixie.picshouse.R;

/**
 * Created by xiangyixie on 3/10/15.
 */
public class PasswordFragment extends Fragment {
    public interface SignupStep2NextListener {
        public void onSignupStep2Next(String password);
    }

    private SignupStep2NextListener m_step2_next = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup2, container, false);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_signup2, menu);

        ((AppCompatActivity)getActivity()).getSupportActionBar().show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.signup_next2:

                EditText txt_pwd = (EditText)getActivity().findViewById(R.id.signup_pwd);
                EditText txt_pwd2 = (EditText)getActivity().findViewById(R.id.signup_pwd_verify);

                String pwd = txt_pwd.getText().toString();
                String pwd2 = txt_pwd2.getText().toString();

                if(pwd.equals("")) {
                    Toast.makeText(getActivity(),
                            R.string.warn_password_empty, Toast.LENGTH_LONG).show();

                    return true;
                }

                if(pwd2.equals("")) {
                    Toast.makeText(getActivity(),
                            R.string.warn_password2_empty, Toast.LENGTH_LONG).show();
                    return true;
                }

                if(!pwd.equals(pwd2)) {
                    Toast.makeText(getActivity(),
                            R.string.password_verify_no_match, Toast.LENGTH_LONG).show();
                    return true;
                }

                m_step2_next.onSignupStep2Next(pwd);

                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        setHasOptionsMenu(true);
        setMenuVisibility(true);

        //register call back
        m_step2_next = (SignupStep2NextListener) activity;
        ((AppCompatActivity)activity).getSupportActionBar().show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();

    }


}
