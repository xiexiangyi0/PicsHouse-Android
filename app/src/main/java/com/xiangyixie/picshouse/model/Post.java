package com.xiangyixie.picshouse.model;

/**
 * Created by xiangyixie on 8/6/15.
 */

import java.util.ArrayList;

public class Post {
    private String mUsername;
    //Location location;
    //Photo photo;
    private String mUserImgUrl;
    String mPicImgUrl;
    String mPicDesc;
    String time;

    Integer mLikesNumber;

    ArrayList<Comment> mComments;


    public void setUsername(String usr_name){
        this.mUsername = usr_name;
    }

    public String getUsername(){
        return this.mUsername;
    }

    public void setUserImgUrl(String path){
        this.mUserImgUrl = path;
    }

    public String getUserImgUrl(){
        return this.mUserImgUrl;
    }

    public void setPicImgUrl(String path){
        this.mPicImgUrl = path;
    }

    public String getPicImgUrl(){
        return this.mPicImgUrl;
    }

    public void setPicDesc(String str){
        this.mPicDesc = str;
    }

    public String getPicDesc(){
        return this.mPicDesc;
    }

    public void setTime(String t){
        this.time = t;
    }

    public String getTime(){
        return this.time;
    }

    public void setLikesNumber(Integer n){
        this.mLikesNumber = n;
    }

    public Integer getLikesNumber(){
        return this.mLikesNumber;
    }

    public void setComments(ArrayList<Comment> comm){
        this.mComments = comm;
    }

    public ArrayList<Comment> getComments(){
        return this.mComments;
    }

}

