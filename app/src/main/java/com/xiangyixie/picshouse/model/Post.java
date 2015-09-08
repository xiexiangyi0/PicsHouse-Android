package com.xiangyixie.picshouse.model;

/**
 * Created by xiangyixie on 8/6/15.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Post {
    private User mUser = null;
    private String mId = null;

    private String mPicImgUrl = null;
    private String mPicDesc = null;

    private String time = null;
    private Integer mLikesNumber = null;

    private ArrayList<Comment> mComments = null;


    public Post() {
        mUser = new User("test_user");
        mId = "123456abcdefgh";
        time = "12h";
        mLikesNumber = 123;
        mComments = new ArrayList<>();
    }

    public String getId() {
        return mId;
    }
    public void setId(String id){
        mId = id;
    }

    public User getUser(){
        return this.mUser;
    }
    public void setUser(User user){
        this.mUser = user;
    }

    public String getPicImgUrl(){
        return this.mPicImgUrl;
    }
    public void setPicImgUrl(String path){
        this.mPicImgUrl = path;
    }

    public String getPicDesc(){
        return this.mPicDesc;
    }
    public void setPicDesc(String str){
        this.mPicDesc = str;
    }

    public String getTime(){
        return this.time;
    }
    public void setTime(String t){
        this.time = t;
    }

    public Integer getLikesNumber(){
        return this.mLikesNumber;
    }
    public void setLikesNumber(Integer n){
        this.mLikesNumber = n;
    }

    public ArrayList<Comment> getComments(){
        return this.mComments;
    }
    public void setComments(ArrayList<Comment> comm){
        this.mComments = comm;
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

    //http://104.236.145.14:8000/post/get/
    public static Post parsePost(JSONObject jpost) throws JSONException {
        Post post = new Post();
        User user = new User();
        post.setId(jpost.getString("id"));

        JSONObject juser = jpost.getJSONObject("user");
        user = User.parseUser(juser);
        post.setUser(user);
        post.setPicDesc(jpost.getString("desc"));

        JSONObject jimage = jpost.getJSONObject("image");
        post.setPicImgUrl(jimage.getString("src"));

        JSONArray jcomments = jpost.getJSONArray("comments");
        post.setComments(Comment.parseCommentArray(jcomments));

        return post;
    }
}

