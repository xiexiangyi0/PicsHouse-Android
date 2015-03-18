package com.xiangyixie.picshouse.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.AppEventsLogger;
import com.xiangyixie.picshouse.AppConfig;
import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.activity.MainActivity;
import com.xiangyixie.picshouse.fragment.FbLoginFragment;
import com.xiangyixie.picshouse.httpService.PHHttpClient;
import com.xiangyixie.picshouse.httpService.PHJsonRequest;
import com.xiangyixie.picshouse.util.UserWarning;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends ActionBarActivity
    implements SignupFragment.SignupStep1NextListener, PasswordFragment.SignupStep2NextListener {

    private EditText  email = null;
    private EditText  password = null;
    
    private Button login_btn;
    private Button signup_btn;
    private Button facebook_login_btn;

    private TextView forget_pwd = null;



    private FbLoginFragment m_fbLoginFragment;

    private PHHttpClient m_http_client = null;

    //for internal data store
    class UserSignupInfo {
        String username;
        String email;
        boolean is_male;
        UserSignupInfo(String u, String e, boolean male) {
            username = u;
            email = e;
            is_male = male;
        }
    }

    private UserSignupInfo m_user_info = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }



        email = (EditText)findViewById(R.id.input_email);
        password = (EditText)findViewById(R.id.input_password);

        login_btn = (Button)findViewById(R.id.btn_login);
        signup_btn = (Button)findViewById(R.id.btn_signup);
        facebook_login_btn = (Button)findViewById(R.id.authButton);
        forget_pwd = (TextView)findViewById(R.id.forget_pwd);

        m_http_client = PHHttpClient.getInstance(this);

        final TextView debug_text = (TextView) findViewById(R.id.debug_text);


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                PHHttpClient client = m_http_client;

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
                                    UserWarning.warn(LoginActivity.this, R.string.http_response_syntax_error);
                                    return;
                                }

                                //login successfully, switch to main
                                LoginActivity.this.gotoMain();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                LoginActivity.this.showHttpError(error);





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

                FragmentManager fragmentManager = LoginActivity.this.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                SignupFragment m_signupFragment = new SignupFragment();
                fragmentTransaction.add(android.R.id.content, m_signupFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                getSupportActionBar().show();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_signup1, menu);

        super.onCreateOptionsMenu(menu);

        return false;

    }


    @Override
    public void onSignupStep1Next(String username, String email, boolean is_male) {
        m_user_info = new UserSignupInfo(username, email, is_male);
        FragmentManager fmgr = getSupportFragmentManager();
        FragmentTransaction ftrans = fmgr.beginTransaction();

        PasswordFragment password_fragment = new PasswordFragment();

        ftrans.replace(android.R.id.content, password_fragment);
        ftrans.addToBackStack(null);

        ftrans.commit();
    }

    @Override
    public void onSignupStep2Next(String password) {

        JSONObject jdata = new JSONObject();

        try {
            jdata.put(AppConfig.KEY_USERNAME, m_user_info.username);
            jdata.put(AppConfig.KEY_EMAIL, m_user_info.email);
            jdata.put(AppConfig.KEY_PASSWORD, password);
            jdata.put(AppConfig.KEY_GENDER, m_user_info.is_male ? 1 : 0);
            m_user_info = null;
        } catch(JSONException e) {
            return;
        }

        PHJsonRequest req = new PHJsonRequest(Request.Method.POST, "/user/create/", jdata,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String tk = null;
                        try {
                            tk = response.getString("token");
                        } catch (JSONException e) {
                            UserWarning.warn(LoginActivity.this, R.string.http_response_syntax_error);
                            return;
                        }

                        getSupportFragmentManager()
                                .popBackStackImmediate(
                                        null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                        LoginActivity.this.gotoMain();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                            LoginActivity.this.showHttpError(error);
                        }

                });


        m_http_client.send(req);



    }

    private void gotoMain() {

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void showHttpError(VolleyError error) {
        if(error instanceof AuthFailureError) {
            String str = null;
            try {
                str = new String(error.networkResponse.data, "UTF8");
                JSONObject jerror = new JSONObject(str);

                if(jerror.has(AppConfig.KEY_ECODE)) {
                    UserWarning.warn(this, jerror.getString(AppConfig.KEY_ECODE));
                } else {
                    UserWarning.warn(this, R.string.http_response_login_fail);
                }
            } catch (Exception e) {
                UserWarning.warn(this, R.string.http_response_syntax_error);
            }
        } else {
            UserWarning.warn(this, R.string.http_response_error);
        }
    }
}
