package com.xiangyixie.picshouse.model;

/**
 * Created by xiangyixie on 8/6/15.
 */

import java.util.ArrayList;

public class Post {
    String username;
    //Location location;
    //Photo photo;
    String user_img_uri;
    String pic_img_uri;
    String time;

    Integer likes_number;
    String likes_number_str;
    ArrayList<Comment> comments;
    //ArrayList<User> usersliked;


    public void setUsername(String usr_name){
        this.username = usr_name;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUser_img_uri(String path){
        this.user_img_uri = path;
    }

    public String getUser_img_uri(){
        return this.user_img_uri;
    }

    public void setPic_img_uri(String path){
        this.pic_img_uri = path;
    }

    public String getPic_img_uri(){
        return this.pic_img_uri;
    }

    public void setTime(String t){
        this.time = t;
    }

    public String getTime(){
        return this.time;
    }

    public void setLikes_number(Integer n){
        this.likes_number = n;
        this.likes_number_str = n + " likes";
    }

    public Integer getLikes_number(){
        return this.likes_number;
    }

    public String getLikes_number_str(){
        return this.likes_number_str;
    }

    public void setComment(ArrayList<Comment> comm){
        this.comments = comm;
    }

    public ArrayList<Comment> getComment(){
        return this.comments;
    }

}

