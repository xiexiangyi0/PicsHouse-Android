package com.xiangyixie.picshouse.model;

import com.xiangyixie.picshouse.httpService.URLEntity;

import org.apache.http.entity.StringEntity;

import java.util.ArrayList;

/**
 * Created by xiangyixie on 3/5/15.
 */


public class User {

    private long mId;                        //primary key, unique.
    private String mUserName;
    private String mFirstName, mLastName;
    private String mDescription;

    private ArrayList<URLEntity> mUrlEntities;

    private int mFollowersCount;
    private int mFollowingCount;
    private int mPhotosCount;
    private int mBeLikedCount;

    private ArrayList<User> mCurrentUserFollowed;
    private ArrayList<User> mFollowingCurrentUsers;

    private StringEntity mProfileImageUrlMini;
    private StringEntity mProfileImageUrlNormal;
    private StringEntity mProfileImageUrlOriginal;



    enum ProfileImageSize{
        MINI,NORMAL,ORIGINAL
    }




    public User(User user) {
        mId = user.getId();
        mUserName = user.getUserName();
        mLastName = user.getLastName();
        mFirstName = user.getFirstName();
        mDescription = user.getDescription();

        mFollowersCount = user.getFollowersCount();
        mFollowingCount = user.getFollowingCount();
        mPhotosCount = user.getPhotosCount();
        mBeLikedCount = user.getBeLikedCount();

        mCurrentUserFollowed = user.getCurrentUserFollowed();
        mFollowingCurrentUsers = user.getFollowingCurrentUsers();

        mProfileImageUrlMini = user.getProfileImageUrlMini();
        mProfileImageUrlNormal = user.getProfileImageUrlNormal();
        mProfileImageUrlOriginal = user.getProfileImageUrlOriginal();
    }

    public User(String username) {

        mId = 1000;
        mUserName = username;
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


    public long getId() {
        return mId;
    }
    public String getUserName() {
        return mUserName;
    }
    public String getLastName() {
        return mLastName;
    }
    public String getFirstName(){
        return mFirstName;
    }
    public String getDescription() {
        return mDescription;
    }


    public ArrayList<URLEntity> getUrlEntities() {
        return mUrlEntities;
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
    public StringEntity getProfileImageUrlMini() {
        return mProfileImageUrlMini;
    }
    public StringEntity getProfileImageUrlNormal() {
        return mProfileImageUrlNormal;
    }
    public StringEntity getProfileImageUrlOriginal() {
        return mProfileImageUrlOriginal;
    }


    public StringEntity getProfileImageUrl(ProfileImageSize size) {

        switch (size) {
            case MINI:
                return mProfileImageUrlMini;
            case NORMAL:
                return mProfileImageUrlNormal;
            case ORIGINAL:
                return mProfileImageUrlOriginal;
        }
        return null;
    }
}
