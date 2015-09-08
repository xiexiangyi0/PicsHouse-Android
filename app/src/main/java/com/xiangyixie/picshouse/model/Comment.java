package com.xiangyixie.picshouse.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by xiangyixie on 8/6/15.
 */
public class Comment {
    private User mUser;
    private String mContent;
    private String mId;

    public Comment(User user, String str){
        this.mUser = user;
        this.mContent = str;
    }


    private Comment(){
        this.mId = "fdsakjkgaljgdas";
        this.mUser = null;
        this.mContent = "";
    }


    public String getId() {
        return mId;
    }
    public void setId(String id){
        this.mId = id;
    }
    public User getUser(){
        return this.mUser;
    }
    public void setUser(User user){
        this.mUser = user;
    }
    public String getContent(){
        return this.mContent;
    }
    public void setContent(String content){
        this.mContent = content;
    }


    public static Comment parseComment(JSONObject jcomment) throws JSONException {
        Comment comment = new Comment();

        comment.mId = jcomment.getString("id");
        // User
        comment.mUser = User.parseUser(jcomment.getJSONObject("user"));
        comment.mContent = jcomment.getString("content");

        return comment;
    }


    public static ArrayList<Comment> parseCommentArray(JSONArray jcomments) {
        ArrayList<Comment> comments = new ArrayList<>();

        int len = jcomments.length();
        for (int i=0; i<len; ++i) {
            try {
                Comment c = parseComment(jcomments.getJSONObject(i));
                comments.add(c);
            } catch (JSONException e) {
                JsonParser.onException(e);
            }
        }
        return comments;
    }
}
