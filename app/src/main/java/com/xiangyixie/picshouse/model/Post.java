package com.xiangyixie.picshouse.model;

/**
 * Created by xiangyixie on 8/6/15.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Post {
    private String mUsername;
    private String mUserAvatarUrl;

    String mPicImgUrl;

    String mPicDesc;
    String time;
    Integer mLikesNumber;

    ArrayList<Comment> mComments;

    public Post() {
        mUsername = "test_user";
        time = "12h";
        mLikesNumber = 123;
    }


    public void setUsername(String usr_name){
        this.mUsername = usr_name;
    }

    public String getUsername(){
        return this.mUsername;
    }

    public void setUserAvatarUrl(String path){
        this.mUserAvatarUrl = path;
    }

    public String getUserAvatarUrl(){
        return this.mUserAvatarUrl;
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


    public static ArrayList<Post> parsePostArray(JSONArray post_jarr) {
        ArrayList<Post> post_array = new ArrayList<>();
        int len = post_jarr.length();
        for (int i=0; i<len; ++i) {
            try {
                Post post = parsePost(post_jarr.getJSONObject(i));
                post_array.add(post);
            } catch (JSONException e) {
                JsonParser.onException(e);
            }
        }
        return post_array;
    }


    public static Post parsePost(JSONObject jpost) throws JSONException {
        Post post = new Post();

        JSONObject juser = jpost.getJSONObject("user");
        post.mUsername = juser.getString("username");
        JSONObject javatar = juser.getJSONObject("avatar");
        String avatar_url = javatar.getString("src");
        if (avatar_url == "null") {
            post.mUserAvatarUrl = "";
        } else {
            post.mUserAvatarUrl = avatar_url;
        }

        post.mPicDesc = jpost.getString("desc");

        JSONObject jimage = jpost.getJSONObject("image");
        post.mPicImgUrl = jimage.getString("src");

        JSONArray jcomments = jpost.getJSONArray("comments");
        post.mComments = Comment.parseCommentArray(jcomments);

        return post;
    }
}

