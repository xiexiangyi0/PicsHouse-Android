package com.xiangyixie.picshouse.register;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AppEventsLogger;
import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.fragment.FbLoginFragment;

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


        


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(this);
                String url ="http://www.google.com";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(DownloadManager.Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                mTextView.setText("Response is: "+ response.substring(0,500));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTextView.setText("That didn't work!");
                    }
                });
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
                /*
                FinalHttp fh = new FinalHttp();
                fh.get("http://172.27.35.1:8080/login/index.jsp?userName=miquan", new AjaxCallBack<Object>() {
                    @Override
                    public void onSuccess(Object t) {
                        //获取返回来的json
                        String str = t.toString();
                        str = str.trim();
                        try {
                            JSONObject obj = new JSONObject(str);
                            boolean success = obj.getBoolean("success");
                            //登录成功
                            if (success) {
                                //app = (MyApplication) this.getApplication();
                                //MyApplication添加了属性sessionId和isLogin
                                app.setLogin(true);
                                app.setSessionId(obj.getString("sessionId"));
                                Toast.makeText(app, "登录成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(app, "登录失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        super.onSuccess(t);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo,
                                          String strMsg) {
                        Log.e("miquan", "failure  " + strMsg);
                        super.onFailure(t, errorNo, strMsg);
                    }
                });
                */
            }
        });


        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                FinalHttp fh = new FinalHttp();
                //添加session，连请求一起发送，这里服务器用的是java开发的
                fh.addHeader("Cookie", "JSESSIONID=" + app.getSessionId());
                fh.get("http://172.27.35.1:8080/login/session.jsp", new AjaxCallBack<Object>() {
                    @Override
                    public void onSuccess(Object t) {
                        Log.e("miquan", t.toString());
                        super.onSuccess(t);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo,
                                          String strMsg) {
                        Log.e("miquan", "failure  " + strMsg);
                        super.onFailure(t, errorNo, strMsg);
                    }
                });
                */
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
        //

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



    /*
    public void login(View view){
        if(email.getText().toString().equals("admin") &&
                password.getText().toString().equals("admin")){
            Toast.makeText(getApplicationContext(), "Redirecting...",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Wrong Credentials",
                    Toast.LENGTH_SHORT).show();



        }

    }
    */

}