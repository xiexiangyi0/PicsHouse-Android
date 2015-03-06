package com.xiangyixie.picshouse.httpService;

/**
 * Created by xiangyixie on 3/5/15.
 */

public interface URLEntity extends java.io.Serializable {
    java.lang.String getText();

    java.lang.String getURL();

    java.lang.String getExpandedURL();

    java.lang.String getDisplayURL();

    int getStart();

    int getEnd();
}