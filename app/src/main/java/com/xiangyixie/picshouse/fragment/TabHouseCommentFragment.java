package com.xiangyixie.picshouse.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiangyixie.picshouse.R;

/**
 * Created by xiangyixie on 9/7/15.
 */
public class TabHouseCommentFragment extends Fragment {

    public TabHouseCommentFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                      Bundle savedInstanceState) {
        return View.inflate(container.getContext(), R.layout.tab_house_comment, null);
    };
}
