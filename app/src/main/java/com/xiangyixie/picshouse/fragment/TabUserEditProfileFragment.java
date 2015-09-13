package com.xiangyixie.picshouse.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.httpService.PHHttpClient;
import com.xiangyixie.picshouse.httpService.PHJsonPost;
import com.xiangyixie.picshouse.httpService.PHJsonRequest;
import com.xiangyixie.picshouse.model.JsonParser;
import com.xiangyixie.picshouse.model.User;
import com.xiangyixie.picshouse.util.UserWarning;
import com.xiangyixie.picshouse.view.UserProfileItemView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xiangyixie on 9/13/15.
 */
public class TabUserEditProfileFragment extends Fragment{

    public interface OnFragmentInteractionListener {
        void onProfileUpdated(User user);
    }

    private User mUser = null;
    private OnFragmentInteractionListener mInteractionListener = null;

    private final static int ITEM_USERNAME = 0;
    private final static int ITEM_FIRSTNAME = 1;
    private final static int ITEM_LASTNAME = 2;
    private final static int ITEM_GENDER = 3;
    private final static int ITEM_COUNT = 4;

    UserProfileItemView mUserProfileItemView [] = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout view = (LinearLayout)inflater.inflate(R.layout.tab_user_edit_profile, null);

        if (mUser != null) {
            mUserProfileItemView = new UserProfileItemView[ITEM_COUNT];
            String username = mUser.getUserName();
            mUserProfileItemView[ITEM_USERNAME] =
                new UserProfileItemView(view.getContext(),
                        "username", username, "username");

            String firstname = mUser.getFirstName();
            mUserProfileItemView[ITEM_FIRSTNAME] =
                new UserProfileItemView(view.getContext(),
                        "first_name", firstname, "first name");

            String lastname = mUser.getLastName();
            mUserProfileItemView[ITEM_LASTNAME] =
                new UserProfileItemView(view.getContext(),
                        "last_name", lastname, "last name");

            int gender = mUser.getGender();
            String gender_str = (gender == 1 ? "male" : "female");
            mUserProfileItemView[ITEM_GENDER] =
                new UserProfileItemView(view.getContext(),
                        "gender", gender_str, "male/female");

            for (UserProfileItemView item : mUserProfileItemView) {
                view.addView(item);
            }

        } else {
            // TODO: load user from url
        }

        Button submitBtn = (Button) view.findViewById(R.id.user_profile_item_submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PHHttpClient client = PHHttpClient.getInstance(getActivity());

                JSONObject juser = null;
                final User newuser = new User();

                if (mUserProfileItemView != null) {
                    newuser.setUsername(mUserProfileItemView[ITEM_USERNAME].getInfo());
                    newuser.setFirstName(mUserProfileItemView[ITEM_FIRSTNAME].getInfo());
                    newuser.setLastName(mUserProfileItemView[ITEM_LASTNAME].getInfo());
                    newuser.setGender(mUserProfileItemView[ITEM_GENDER].equals("male") ? 0 : 1);
                }

                try {
                    juser = newuser.toJsonObject();
                } catch (JSONException e) {
                    JsonParser.onException(e);
                }

                PHJsonPost post = new PHJsonPost("/user/update/", juser,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                String token = "";

                                try {
                                    token = response.getString("token");
                                    PHJsonRequest.auth_token_ = token;
                                    toastWarning(token);
                                } catch (JSONException e) {
                                    JsonParser.onException(e);
                                    toastWarning("OnJsonException");
                                }
                                mInteractionListener.onProfileUpdated(newuser);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                toastWarning("OnNetworkError");

                                mInteractionListener.onProfileUpdated(null);
                            }
                        });

                client.send(post);
            }
        });

        return view;
    }

    public void initialize(User user, OnFragmentInteractionListener listener) {
        mUser = user;
        mInteractionListener = listener;
    }

    private void toastWarning(String txt) {
        UserWarning.warn(getActivity(), txt);
    }

}
