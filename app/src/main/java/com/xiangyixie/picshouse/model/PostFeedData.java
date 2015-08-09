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
        int count = 6;
        String[] usernames={"Sophie","Emma","Anna","Diana","Michael","Peter"};

        String[] user_img_uris = {
                "/sdcard/DCIM/Camera/IMG_20140328_100316.jpeg",
                "/sdcard/DCIM/Camera/IMG_20140328_120349.jpeg",
                "/sdcard/DCIM/Camera/IMG_20140330_113729.jpeg",
                "/sdcard/DCIM/Camera/IMG_20140331_174356.jpeg",
                "/sdcard/DCIM/Camera/IMG_20140726_154656.jpeg",
                "/sdcard/DCIM/Camera/IMG_20140918_120303.jpeg"
        };

        String[] pic_img_uris = {
                "/sdcard/DCIM/Camera/IMG_20141026_122202.jpeg",
                "/sdcard/DCIM/Camera/IMG_20141127_135254.jpeg",
                "/sdcard/DCIM/Camera/IMG_20141224_210531.jpeg",
                "/sdcard/DCIM/Camera/IMG_20150406_083757.jpeg",
                "/sdcard/DCIM/Camera/IMG_20150409_123209.jpeg",
                "/sdcard/DCIM/Camera/IMG_20150430_173705.jpeg"
        };

        String[] times = {
                "30m",
                "10s",
                "2h",
                "3d",
                "10m",
                "5h"
        };

        Integer[] likes_numbers = {
            153262, 53642, 43, 9428951,248, 8432
        };

        ArrayList<String> comment = new ArrayList<String>(Arrays.asList(
                "WOW, it's so awesome!",
                "I like your concert and your songs very much. I have listened your songs for 10 years, starting from my 14 year old youth, to now. I listened every song of yours,actually whole of them just recorded the youth days of my generation. Love you and your spirit, it's so wonderful and have lived with me for 10 years.",
                "Like you!",
                "Cute!"
        ));

        for(int i=0 ; i<count; ++i){
            Post post = new Post();
            post.setUsername(usernames[i]);
            post.setUser_img_uri(user_img_uris[i]);
            post.setPic_img_uri(pic_img_uris[i]);
            post.setTime(times[i]);
            post.setLikes_number(likes_numbers[i]);
            post.setComment(comment);
            post_feed_data.add(post);
        }

        return post_feed_data;
    }
}
