package com.xiangyixie.picshouse.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.xiangyixie.picshouse.R;

import java.util.Arrays;


public class FbLoginFragment extends Fragment {



    private static final String TAG = "FbLoginFragment";

    public interface FbLoginListener {
        public void onLoginSuccess(String token);
        public void onLoginFail();
    }

    private FbLoginListener m_login_callback;


    private Session.StatusCallback m_stcallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    private UiLifecycleHelper uiHelper;

    private boolean m_is_resumed = false;




    public FbLoginFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        m_login_callback = (FbLoginListener) activity;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fb_login, container, false);

        LoginButton fbAuthButton = (LoginButton) getActivity().findViewById(R.id.authButton);
        fbAuthButton.setReadPermissions(Arrays.asList("public_profile"));
        fbAuthButton.setFragment(this);

        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(getActivity(), m_stcallback);
        uiHelper.onCreate(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();

        m_is_resumed = true;

        // Main activity is launched and user session is not null,
        // but the session state change notification not be triggered.
        Session session = Session.getActiveSession();
        if (session != null &&
                (session.isOpened() || session.isClosed())) {
            Log.d(TAG, "onResume");
            onSessionStateChange(session, session.getState(), null);
        }
        uiHelper.onResume();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        m_is_resumed = false;
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

/*
    private void onClickLogin() {
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(getActivity())
                    .setPermissions(Arrays.asList("public_profile"))
                    .setCallback(m_stcallback));
        } else {
            Session.openActiveSession(getActivity(), this, true, m_stcallback);
        }
    }
*/

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {

        if(m_is_resumed) {
            if (state.isOpened()) {
                Log.i(TAG, "Logged in...");
                m_login_callback.onLoginSuccess(session.getAccessToken());
            } else if (state.isClosed()) {
                Log.i(TAG, "Logged out...");
                m_login_callback.onLoginFail();
            }
        }
    }


}
