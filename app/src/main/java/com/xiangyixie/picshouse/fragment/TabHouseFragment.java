package com.xiangyixie.picshouse.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiangyixie.picshouse.R;
import com.xiangyixie.picshouse.view.pinnedHeaderListView.PinnedHeaderListView;
import com.xiangyixie.picshouse.view.pinnedHeaderListView.HeaderListViewAdapter;
import com.xiangyixie.picshouse.view.pinnedHeaderListView.SectionedBaseAdapter;


public class TabHouseFragment extends Fragment {

    private SectionedBaseAdapter adapter;
    private PinnedHeaderListView listView;

    public TabHouseFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //root view for tab 'house'.
        final View view = inflater.inflate(R.layout.tab_house, container, false);

        // set PinnedHeaderListView.
        adapter = new HeaderListViewAdapter(inflater);

        listView = (PinnedHeaderListView) view.findViewById(R.id.tab_house_listview);
        listView.setPinHeaders(true);
        // TODO: API starts from 21. Need to change it. Consider to use NestedScrollView.
        //listView.setNestedScrollingEnabled(true);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
