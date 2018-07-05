package com.chenggong.trip.ui;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by chenggong on 18-5-1.
 *
 * @author chenggong
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int bottom;
    private int right;
    private int left;
    private int top;

    public SpaceItemDecoration(int bottom) {
        this(bottom, 0, 0, 0);
    }


    public SpaceItemDecoration(int bottom, int right, int left) {
        this(bottom, right, left, 0);
    }

    public SpaceItemDecoration(int bottom, int right, int left, int top) {
        this.bottom = bottom;
        this.right = right;
        this.left = left;
        this.top = top;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = bottom;
        outRect.right = right;
        outRect.left = left;
        if (parent.getChildAdapterPosition(view) == 0) {
//            outRect.top = bottom;

        }
    }
}
