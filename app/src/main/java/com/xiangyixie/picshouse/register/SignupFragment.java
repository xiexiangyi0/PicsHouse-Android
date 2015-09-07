package com.xiangyixie.picshouse.register;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.httpService.PHHttpClient;
import com.xiangyixie.picshouse.httpService.PHJsonPost;
import com.xiangyixie.picshouse.util.UserWarning;

import org.json.JSONException;
import org.json.JSONObject;



public class SignupFragment extends Fragment {

    private static final String TAG = "SignupFragment";

    private Context thisContext= null;

    private EditText signup_username = null;
    private EditText signup_email = null;
    private RadioGroup signup_gender = null;
    //private CheckBox signup_gender_selected = null;
    private TextView debug_signup = null;


    private SignupStep1NextListener m_step1_next = null;

    public SignupFragment() {

    }
    //fragment interaction listener
    public interface SignupStep1NextListener {
        public void onSignupStep1Next(String username, String email, boolean is_male);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setMenuVisibility(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        thisContext = container.getContext();

        //signup http related
        signup_email = (EditText)view.findViewById(R.id.signup_email);
        signup_username = (EditText)view.findViewById(R.id.signup_username);
        signup_gender = (RadioGroup)view.findViewById(R.id.radio_group_gender);
        debug_signup = (TextView) view.findViewById(R.id.debug_signup);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_signup1, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.signup_next1:
                        // Instantiate the RequestQueue.
                        PHHttpClient client = PHHttpClient.getInstance(thisContext);

                        final String email_str = signup_email.getText().toString();
                        final String username_str = signup_username.getText().toString();
                        final int selected_gender = signup_gender.getCheckedRadioButtonId();

                        if(username_str == "") {
                            toastWarning("Please input username");
                            return true;
                        }
                        if(email_str == "") {
                            toastWarning("Please input email");
                            return true;
                        }
                        if(selected_gender != R.id.radiobutton_male
                                && selected_gender != R.id.radiobutton_female){
                            toastWarning("must select gender");
                            return true;
                        }

                        JSONObject jdata = new JSONObject();
                        try {
                            jdata.put("username", username_str);
                            jdata.put("email", email_str);

                            Log.d("DEBUG", "username = " + username_str);

                        } catch(JSONException e) {

                            jdata = null;
                            Log.d("DEBUG", "email = " + email_str);

                        }

                        // Request a string response(token) from the provided URL.
                        PHJsonPost req = new PHJsonPost(
                                "/user/exist/", jdata,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        String tk = null;

                                        try {
                                            tk = response.getString("ecode");
                                        } catch(JSONException e) {
                                            toastWarning(R.string.http_response_syntax_error);
                                            return;
                                        }


                                        if(tk.equals("username_exist")) {
                                            toastWarning("user name exists");

                                        } else if(tk.equals("email_exist")) {
                                            toastWarning("email exists");

                                        } else  {

                                            m_step1_next.onSignupStep1Next(
                                                    username_str, email_str,
                                                    selected_gender == R.id.radiobutton_male);
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        toastWarning(R.string.http_response_error);
                                    }
                                }
                        );
                        // Add the request to the RequestQueue.
                        client.send(req);
            return true;

            default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        //register call back
        m_step1_next = (SignupStep1NextListener) activity;
        ((ActionBarActivity)activity).getSupportActionBar().show();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((ActionBarActivity)getActivity()).getSupportActionBar().hide();
    }

    private void toastWarning(String txt) {
        UserWarning.warn(getActivity(), txt);
    }

    private void toastWarning(int id) {
        UserWarning.warn(getActivity(), id);
    }

}
