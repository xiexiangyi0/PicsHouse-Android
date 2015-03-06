package com.xiangyixie.picshouse.model;

import com.xiangyixie.picshouse.httpService.URLEntity;

import org.apache.http.entity.StringEntity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by xiangyixie on 3/5/15.
 */


public class User {

    private long mId;
    private String mUserName;
    private String mFirstName, mLastName;
    private String mDescription;

    private URLEntity[] mUrlEntities;

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
        mScreenName = user.getScreenName();
        mName = user.getName();
        mDescription = user.getDescription();
        ArrayList<URLEntity> urlEntityArrayList = new ArrayList<URLEntity>();
        if (user.getDescriptionURLEntities() != null) {
            urlEntityArrayList = new ArrayList<URLEntity>(Arrays.asList(user.getDescriptionURLEntities()));
        }

        if (user.getURL() != null) {
            mUrl = user.getURL();
            urlEntityArrayList.add(user.getURLEntity());
        }

        mUrlEntities = urlEntityArrayList.toArray(new URLEntity[urlEntityArrayList.size()]);

        if (user.getLocation() != null
                && !user.getLocation().equals("")) {
            mLocation = user.getLocation();
        }

        if (user.getOriginalProfileImageURLHttps() != null) {
            mProfileImageUrlOriginal = user.getOriginalProfileImageURLHttps();
        }

        if (user.getBiggerProfileImageURLHttps() != null) {
            mProfileImageUrlBigger = user.getBiggerProfileImageURLHttps();
        }

        if (user.getProfileImageURLHttps() != null) {
            mProfileImageUrlNormal = user.getProfileImageURLHttps();
        }

        if (user.getMiniProfileImageURLHttps() != null) {
            mProfileImageUrlMini = user.getMiniProfileImageURLHttps();
        }
    }

    /*
    public User(User user) {

        mId = user.mId;
        mUserName = user.mUserName;
        mFirstName = user.mFirstName;
        mLastName = user.mLastName;
        mDescription = user.mDescription;

        mFollowersCount = user.mFollowersCount;
        mFollowingCount = user.mFollowingCount;
        mPhotosCount = user.mPhotosCount;
        mBeLikedCount = user.mBeLikedCount;

        mCurrentUserFollowed = user.mCurrentUserFollowed;
        mFollowingCurrentUsers = user.mFollowingCurrentUsers;


        mProfileImageUrlMini = "https://alpha-api.app.net/stream/0/users/@" + user.mUserName + "/avatar?w=32";
        mProfileImageUrlNormal = "https://alpha-api.app.net/stream/0/users/@" + user.mUserName + "/avatar?w=48";
        mProfileImageUrlOriginal = "https://alpha-api.app.net/stream/0/users/@" + user.mUserName + "/avatar";

    }
    */


    public User(User user) {

        mId = user.getId();
        mUserName = user.getUserName();
        mLastName = user.getLastName();
        mFirstName = user.getFirstName();
        mDescription = user.getDescription();

        //mUrlEntities = user.getUrlEntities();


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


    public URLEntity[] getUrlEntities() {
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
