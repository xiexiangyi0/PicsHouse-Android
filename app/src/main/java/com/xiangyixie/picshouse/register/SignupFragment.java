package com.xiangyixie.picshouse.register;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.httpService.PHHttpClient;
import com.xiangyixie.picshouse.httpService.PHJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;



public class SignupFragment extends Fragment {

    private static final String TAG = "SignupFragment";

    private Context thisContext= null;

    private EditText signup_username = null;
    private EditText signup_email = null;
    private CheckBox signup_gender_female = null;
    private CheckBox signup_gender_male = null;
    private CheckBox signup_gender_selected = null;
    private TextView debug_signup = null;






    public SignupFragment() {
        // Required empty public constructor
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


        //signup http related
        signup_email = (EditText)view.findViewById(R.id.signup_email);
        signup_username = (EditText)view.findViewById(R.id.signup_username);
        signup_gender_female = (CheckBox)view.findViewById(R.id.checkbox_female);
        signup_gender_male = (CheckBox)view.findViewById(R.id.checkbox_male);
        debug_signup = (TextView) view.findViewById(R.id.debug_signup);

        setHasOptionsMenu(true);
        setMenuVisibility(true);
        return view;

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_signup1, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.signup_next1:

                        // Instantiate the RequestQueue.
                        PHHttpClient client = PHHttpClient.getInstance(thisContext);

                        String username_str = signup_email.getText().toString();
                        String email_str = signup_username.getText().toString();

                        //gender:true--male, false--female
                        Boolean gender_male = signup_gender_male.isSelected();
                        Boolean gender_female = signup_gender_female.isSelected();
                        Boolean gender_selected = gender_male;

                        if(gender_male || gender_female == false){
                            Toast.makeText(thisContext, "must select gender!",
                                    Toast.LENGTH_LONG).show();
                            return true;
                        }

                        JSONObject jdata = new JSONObject();

                        try {
                            jdata.put("username", username_str);
                            jdata.put("email", email_str);
                            jdata.put("gender", gender_selected);

                        } catch(JSONException e) {

                            jdata = null;

                        }

                        // Request a string response(token) from the provided URL.
                        PHJsonRequest req = new PHJsonRequest(Request.Method.POST, "/user/signup/", jdata,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        String tk = null;

                                        try {
                                            tk = response.getString("token");
                                        } catch(JSONException e) {
                                            tk = "parse error";
                                        }
                                        debug_signup.setText("Signup Success, token is " + tk);
                                    }
                                },

                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        debug_signup.setText("Fail");

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
        setHasOptionsMenu(true);
        setMenuVisibility(true);

    }




    @Override
    public void onDetach() {
        super.onDetach();

    }

}
