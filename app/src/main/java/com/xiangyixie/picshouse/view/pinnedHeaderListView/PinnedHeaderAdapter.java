package com.xiangyixie.picshouse.view.pinnedHeaderListView;

import android.view.View;

/**
 * Adapter interface.  The list adapter must implement this interface.
 */
public interface PinnedHeaderAdapter {

    /**
     * Pinned header state: don't show the header.
     */
    public static final int PINNED_HEADER_GONE = 0;

    /**
     * Pinned header state: show the header at the top of the list.
     */
    public static final int PINNED_HEADER_VISIBLE = 1;

    /**
     * Pinned header state: show the header. If the header extends beyond
     * the bottom of the first shown element, push it up and clip.
     */
    public static final int PINNED_HEADER_PUSHED_UP = 2;

    /**
     * Computes the desired state of the pinned header for the given
     * position of the first visible list item. Allowed return values are
     * {@link #PINNED_HEADER_GONE}, {@link #PINNED_HEADER_VISIBLE} or
     * {@link #PINNED_HEADER_PUSHED_UP}.
     */
    int getPinnedHeaderState(int position);

    /**
     * Configures the pinned header view to match the first visible list item.
     *
     * @param header pinned header view.
     * @param position position of the first visible list item.
     * @param alpha fading of the header view, between 0 and 255.
     */
    void configurePinnedHeader(View header, int position, int alpha);
}
