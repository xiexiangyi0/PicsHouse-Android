package com.xiangyixie.picshouse.register;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AppEventsLogger;
import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.fragment.FbLoginFragment;


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

    */

}