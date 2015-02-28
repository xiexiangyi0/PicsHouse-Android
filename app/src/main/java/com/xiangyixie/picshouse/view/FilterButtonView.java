package com.xiangyixie.picshouse.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiangyixie.picshouse.R;

/**
 * Created by xiangyixie on 2/24/15.
 */
public class FilterButtonView extends FrameLayout {
    View m_view;
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        m_view = inflater.inflate(R.layout.listitem_filter_item, this);
        setClickable(true);
    }

    public FilterButtonView(Context context) {
        super(context);
        init(context);
    }

    public FilterButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FilterButtonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void setText(String text) {
        TextView t = (TextView) m_view.findViewById(R.id.filter_item_text);
        t.setText(text);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        View v = m_view.findViewById(R.id.filter_item_cover);
        v.setOnClickListener(l);
    }

    public void highlight() {
        ImageView cover = (ImageView) m_view.findViewById(R.id.filter_item_cover);
        cover.setImageResource(R.drawable.imagefactory_filter_item_pressed);
    }

    public void dim() {
        ImageView cover = (ImageView) m_view.findViewById(R.id.filter_item_cover);
        cover.setImageResource(R.drawable.imagefactory_filter_item_normal);
    }
}
