package com.xiangyixie.picshouse.model;

/**
 * Created by xiangyixie on 8/6/15.
 */

import java.util.ArrayList;

public class Post {
    User user;
    Location location;
    Photo photo;
    Comment note;
    Integer likes_count;
    ArrayList<UserCommentPair> comments;
    ArrayList<User> usersliked;
}

class UserCommentPair{
    User user;
    Comment comment;
}