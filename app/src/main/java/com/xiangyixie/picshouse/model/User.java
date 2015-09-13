package com.xiangyixie.picshouse.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by xiangyixie on 3/5/15.
 */


public class User {

    private String mId;                        //primary key, unique.

    private String mUserName;
    private String mUserAvatarUrl;

    private String mEmail;
    private int mGender;

    private String mFirstName, mLastName;
    private String mDescription;

    private int mFollowersCount;
    private int mFollowingCount;
    private int mPhotosCount;
    private int mBeLikedCount;

    private ArrayList<User> mCurrentUserFollowed;
    private ArrayList<User> mFollowingCurrentUsers;


    enum ProfileImageSize{
        MINI,NORMAL,ORIGINAL
    }


    public User(User user) {
        mId = user.getId();
        mUserName = user.getUserName();
        mUserAvatarUrl = user.getUserAvatarUrl();
        mEmail = user.getEmail();
        mGender = user.getGender();
        mLastName = user.getLastName();
        mFirstName = user.getFirstName();
        mDescription = user.getDescription();

        mFollowersCount = user.getFollowersCount();
        mFollowingCount = user.getFollowingCount();
        mPhotosCount = user.getPhotosCount();
        mBeLikedCount = user.getBeLikedCount();

        mCurrentUserFollowed = user.getCurrentUserFollowed();
        mFollowingCurrentUsers = user.getFollowingCurrentUsers();
    }


    public User(String username) {
        mId = "10000000000000";
        mUserName = username;
        mUserAvatarUrl = new String();
        mEmail = new String();
        mGender = 0;
        mLastName = new String();
        mFirstName = new String();
        mDescription = new String();

        mFollowersCount = 0;
        mFollowingCount = 0;
        mPhotosCount = 0;
        mBeLikedCount = 0;

        mCurrentUserFollowed = new ArrayList<>();
        mFollowingCurrentUsers = new ArrayList<>();
    }

    public User() {
        mId = "10000000000000";
        mUserName = "test_user";
        mUserAvatarUrl = new String();
        mEmail = new String();
        mGender = 0;
        mLastName = new String();
        mFirstName = new String();
        mDescription = new String();

        mFollowersCount = 0;
        mFollowingCount = 0;
        mPhotosCount = 0;
        mBeLikedCount = 0;

        mCurrentUserFollowed = new ArrayList<>();
        mFollowingCurrentUsers = new ArrayList<>();
    }


    public String getId() {
        return mId;
    }
    public void setId(String id){
        mId = id;
    }
    public String getUserName() {
        return mUserName;
    }
    public void setUsername(String username){
        mUserName = username;
    }
    public String getUserAvatarUrl(){
        return mUserAvatarUrl;
    }
    public void setUserAvatarUrl(String url){
        mUserAvatarUrl = url;
    }
    public String getEmail(){
        return mEmail;
    }
    public void setEmail(String email){
        mEmail = email;
    }
    public int getGender(){
        return mGender;
    }
    public void setGender(int gender){
        mGender = gender;
    }
    public String getFirstName(){
        return mFirstName;
    }
    public void setFirstName(String firstname){
        mFirstName = firstname;
    }
    public String getLastName() {
        return mLastName;
    }
    public void setLastName(String lastname){
        mLastName = lastname;
    }
    public String getDescription() {
        return mDescription;
    }
    public void setDescription(String desc){
        mDescription = desc;
    }


    public int getFollowersCount() {
        return mFollowersCount;
    }
    public int getFollowingCount() {
        return mFollowingCount;
    }
    public int getPhotosCount(){
        return mPhotosCount;
    }
    public int getBeLikedCount() {
        return mBeLikedCount;
    }


    public ArrayList<User> getCurrentUserFollowed(){
        return mCurrentUserFollowed;
    }
    public ArrayList<User> getFollowingCurrentUsers(){
        return mFollowingCurrentUsers;
    }


    public String getProfileImageUrl(ProfileImageSize size) {

        switch (size) {
            case MINI:
                //return mProfileImageUrlMini;
            case NORMAL:
                //return mProfileImageUrlNormal;
            case ORIGINAL:
                //return mProfileImageUrlOriginal;
        }
        return null;
    }

    public JSONObject toJsonObject() throws JSONException {
        JSONObject juser = new JSONObject();

        if (!getUserName().isEmpty()) {
            juser.put("username", getUserName());
        }

        if (!getFirstName().isEmpty()) {
            juser.put("first_name", getFirstName());
        }

        if (!getLastName().isEmpty()) {
            juser.put("last_name", getLastName());
        }

        if (getGender() == 0 || getGender() == 1) {
            juser.put("gender", getGender());
        }

        return juser;
    }

    public static User parseUser(JSONObject juser) throws JSONException{
        User user = new User();
        user.setId(juser.getString("id"));
        user.setUsername(juser.getString("username"));
        user.setEmail(juser.getString("email"));
        user.setGender(juser.getInt("gender"));
        JSONObject javatar = juser.getJSONObject("avatar");
        user.setUserAvatarUrl(javatar.getString("src"));

        return user;
    }
}
