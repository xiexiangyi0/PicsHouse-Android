package com.xiangyixie.picshouse.register;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.AppEventsLogger;
import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.fragment.FbLoginFragment;
import com.xiangyixie.picshouse.httpService.PHHttpClient;
import com.xiangyixie.picshouse.httpService.PHJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends FragmentActivity {

    private EditText  email = null;
    private EditText  password = null;
    
    private Button login_btn;
    private Button signup_btn;
    private Button facebook_login_btn;

    private TextView forget_pwd = null;



    private FbLoginFragment m_fbLoginFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);



        email = (EditText)findViewById(R.id.input_email);
        password = (EditText)findViewById(R.id.input_password);

        login_btn = (Button)findViewById(R.id.btn_login);
        signup_btn = (Button)findViewById(R.id.btn_signup);
        facebook_login_btn = (Button)findViewById(R.id.authButton);
        forget_pwd = (TextView)findViewById(R.id.forget_pwd);

        final TextView debug_text = (TextView) findViewById(R.id.debug_text);


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                PHHttpClient client = PHHttpClient.getInstance(LoginActivity.this);

                String username_str = email.getText().toString();
                String password_str = password.getText().toString();

                JSONObject jdata = new JSONObject();
                try {
                    jdata.put("username", username_str);
                    jdata.put("password", password_str);
                } catch(JSONException e) {

                    jdata = null;

                }

                // Request a string response from the provided URL.
                PHJsonRequest req = new PHJsonRequest(Request.Method.POST, "/user/login/", jdata,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                String tk = null;
                                try {
                                    tk = response.getString("token");
                                } catch(JSONException e) {
                                    tk = "parse error";
                                }
                                debug_text.setText("Login Success, token is " + tk);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                debug_text.setText("Fail");

                            }
                        }
                );

                // Add the request to the RequestQueue.
                client.send(req);

            }
        });


        //for 3rd facebook login!
        if (savedInstanceState == null) {
                  // Add the fragment on initial activity setup
            m_fbLoginFragment = new FbLoginFragment();


            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, m_fbLoginFragment)
                    .commit();


        }
        else {
                    // Or set the fragment from restored state info
            m_fbLoginFragment = (FbLoginFragment) getSupportFragmentManager()
                    .findFragmentById(android.R.id.content);
        }




        signup_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                SignupFragment m_signupFragment = new SignupFragment();
                fragmentTransaction.add(android.R.id.content, m_signupFragment);
                fragmentTransaction.commit();
            }
        });

    }



    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }



    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }


}
