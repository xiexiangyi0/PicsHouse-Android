package com.xiangyixie.picshouse.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by xiangyixie on 8/9/15.
 */
public class PostFeedData {
    ArrayList<Post> post_feed_data;

    public PostFeedData(){
        post_feed_data = new ArrayList<Post>();
    }

    public void addPostData(Post post){
        post_feed_data.add(post);
    }

    public ArrayList<Post> getAllPostFeedData(){
        int count = 16;

        String[] usernames={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};


        /*
        String[] user_img_uris = {
                "/sdcard/Download/img1.jpg",
                "/sdcard/Download/img2.jpg",
                "/sdcard/Download/img3.jpg",
                "/sdcard/Download/img4.jpg",
                "/sdcard/Download/img5.jpg",
                "/sdcard/Download/img6.jpg",
                "/sdcard/Download/img7.jpg",
                "/sdcard/Download/img8.jpg",
                "/sdcard/Download/img9.jpg",
                "/sdcard/Download/img10.jpg",
                "/sdcard/Download/img11.jpg",
                "/sdcard/Download/img12.jpg",
                "/sdcard/Download/img13.jpg",
                "/sdcard/Download/img14.jpg",
                "/sdcard/Download/img15.jpg",
                "/sdcard/Download/img15.jpg",
        };

        String[] pic_img_uris = {
                "/sdcard/Download/img15.jpg",
                "/sdcard/Download/img14.jpg",
                "/sdcard/Download/img13.jpg",
                "/sdcard/Download/img12.jpg",
                "/sdcard/Download/img11.jpg",
                "/sdcard/Download/img10.jpg",
                "/sdcard/Download/img9.jpg",
                "/sdcard/Download/img8.jpg",
                "/sdcard/Download/img7.jpg",
                "/sdcard/Download/img6.jpg",
                "/sdcard/Download/img5.jpg",
                "/sdcard/Download/img4.jpg",
                "/sdcard/Download/img3.jpg",
                "/sdcard/Download/img2.jpg",
                "/sdcard/Download/img1.jpg",
                "/sdcard/Download/img1.jpg",
        };
        */

        String[] times = {
                "1m",
                "2s",
                "3h",
                "4d",
                "5m",
                "6h",
                "7h",
                "8h",
                "9h",
                "10h",
                "11m",
                "12s",
                "13d",
                "14m",
                "15s",
                "15m",
        };

        String pic_desc = "The moment is so wonderful! I feel so happy now! Hope all of us can enjoy it~~~~~";

        Integer[] likes_numbers = {
            1, 2, 3, 4, 5, 6, 7,8,8,10,11,12,13,14,15,16
        };

        ArrayList<String> comm_usernames = new ArrayList<>(Arrays.asList("AAA","BBB","CCC"));

        ArrayList<String> comm_contents = new ArrayList<String>(Arrays.asList(
                "WOW, it's so awesome!",
                "I like your concert and your songs very much. I have listened your songs for 10 years, starting from my 14 year old youth, to now. I listened every song of yours,actually whole of them just recorded the youth days of my generation. Love you and your spirit, it's so wonderful and have lived with me for 10 years.",
                "Like you!",
                "Cute!"
        ));
        ArrayList<Comment> comment = new ArrayList<>();
        for(int i=0;i<comm_usernames.size();++i){
            User newuser = new User(comm_usernames.get(i));
            Comment newcomment = new Comment(newuser, comm_contents.get(i));
            comment.add(newcomment);
        }

        for(int i=0 ; i<count; ++i){
            Post post = new Post();
            post.setUsername(usernames[i]);
            //post.setUser_img_uri(user_img_uris[i]);
            //post.setPic_img_uri(pic_img_uris[i]);
            post.setPicDesc(pic_desc);
            post.setTime(times[i].toString());
            post.setLikesNumber(likes_numbers[i]);
            post.setComments(comment);
            post_feed_data.add(post);
        }

        return post_feed_data;
    }
}