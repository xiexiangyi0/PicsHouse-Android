package com.xiangyixie.picshouse.model;

/**
 * Created by xiangyixie on 8/6/15.
 */
public class Comment {
    private User mUser;
    private String mContent;

    public Comment(User user, String str){
        this.mUser = user;
        this.mContent = str;
    }

    /*
    public Comment(){
        this.mUser = new User();
        this.content = new String();
    }*/


    public String getUsername(){
        return this.mUser.getUserName();
    }

    public String getContent(){
        return this.mContent;
    }
}
