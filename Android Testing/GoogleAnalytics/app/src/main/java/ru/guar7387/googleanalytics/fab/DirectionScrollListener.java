package ru.guar7387.googleanalytics.fab;

import android.view.View;
import android.widget.AbsListView;

class DirectionScrollListener implements AbsListView.OnScrollListener {

    private static final int DIRECTION_CHANGE_THRESHOLD = 1;

    private final FloatingActionButton mFloatingActionButton;

    private int mPrevPosition;
    private int mPrevTop;

    private boolean mUpdated;

    DirectionScrollListener(FloatingActionButton floatingActionButton) {
        mFloatingActionButton = floatingActionButton;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        View topChild = view.getChildAt(0);
        int firstViewTop = 0;
        if (topChild != null) {
            firstViewTop = topChild.getTop();
        }
        boolean down;
        boolean changed = true;
        if (mPrevPosition == firstVisibleItem) {
            int topDifference = mPrevTop - firstViewTop;
            down = firstViewTop < mPrevTop;
            changed = Math.abs(topDifference) > DIRECTION_CHANGE_THRESHOLD;
        } else {
            down = firstVisibleItem > mPrevPosition;
        }
        if (changed && mUpdated) {
            mFloatingActionButton.hide(down);
        }
        mPrevPosition = firstVisibleItem;
        mPrevTop = firstViewTop;
        mUpdated = true;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }
}
