package ru.guar7387.googleanalytics.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SwipeToDismissTouchListener implements View.OnTouchListener {

    private final int mSlop;
    private final int mMinFlingVelocity;
    private final int mMaxFlingVelocity;
    private final long mAnimationTime;

    private final ListView mListView;
    private final DismissCallbacks mCallbacks;
    private int mViewWidth = 1;

    private final List<PendingDismissData> mPendingDismisses = new ArrayList<PendingDismissData>();
    private int mDismissAnimationRefCount = 0;
    private float mDownX;
    private boolean mSwiping;
    private VelocityTracker mVelocityTracker;
    private int mDownPosition;
    private View mDownView;
    private boolean mPaused;

    public interface DismissCallbacks {

        boolean canDismiss(int position);

        void onDismiss(ListView listView, int[] reverseSortedPositions);
    }

    public SwipeToDismissTouchListener(ListView listView, DismissCallbacks callbacks) {
        ViewConfiguration vc = ViewConfiguration.get(listView.getContext());
        mSlop = vc.getScaledTouchSlop();
        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity() * 16;
        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        mAnimationTime = listView.getContext().getResources().getInteger(
                android.R.integer.config_shortAnimTime) / 3;
        mListView = listView;
        mCallbacks = callbacks;
    }

    public void setEnabled(boolean enabled) {
        mPaused = !enabled;
    }

    public AbsListView.OnScrollListener makeScrollListener() {
        return new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                setEnabled(scrollState != AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
            }
        };
    }

    public void dismiss(int position) {
        dismiss(getViewForPosition(position), position, true);
    }

    @Override
    public boolean onTouch(View view, @NonNull MotionEvent motionEvent) {
        if (mViewWidth < 2) {
            mViewWidth = mListView.getWidth();
        }
        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                if (mPaused) {
                    return false;
                }
                down(motionEvent);
                view.onTouchEvent(motionEvent);
                return true;
            }
            case MotionEvent.ACTION_UP: {
                up(motionEvent);
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                cancel();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (mVelocityTracker == null || mPaused) {
                    break;
                }
                float deltaX = move(motionEvent);
                if (mSwiping) {
                    mDownView.setTranslationX(deltaX);
                    mDownView.setAlpha(Math.max(0.15f, Math.min(1f,
                            1f - 2f * Math.abs(deltaX) / mViewWidth)));
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private void down(MotionEvent motionEvent) {
        Rect rect = new Rect();
        int childCount = mListView.getChildCount();
        int[] listViewCoords = new int[2];
        mListView.getLocationOnScreen(listViewCoords);
        int x = (int) motionEvent.getRawX() - listViewCoords[0];
        int y = (int) motionEvent.getRawY() - listViewCoords[1];
        for (int i = 0; i < childCount; i++) {
            View child = mListView.getChildAt(i);
            child.getHitRect(rect);
            if (rect.contains(x, y)) {
                mDownView = child;
                break;
            }
        }
        if (mDownView != null) {
            mDownX = motionEvent.getRawX();
            mDownPosition = mListView.getPositionForView(mDownView);
            if (mCallbacks.canDismiss(mDownPosition)) {
                mVelocityTracker = VelocityTracker.obtain();
                mVelocityTracker.addMovement(motionEvent);
            }
            else {
                mDownView = null;
            }
        }
    }

    private void up(MotionEvent motionEvent) {
        if (mVelocityTracker == null) {
            return;
        }
        float deltaX = motionEvent.getRawX() - mDownX;
        mVelocityTracker.addMovement(motionEvent);
        mVelocityTracker.computeCurrentVelocity(1000);
        float velocityX = mVelocityTracker.getXVelocity();
        float absVelocityX = Math.abs(velocityX);
        float absVelocityY = Math.abs(mVelocityTracker.getYVelocity());
        boolean dismiss = false;
        boolean dismissRight = false;
        if (Math.abs(deltaX) > mViewWidth / 2) {
            dismiss = true;
            dismissRight = deltaX > 0;
        }
        else if (mMinFlingVelocity <= absVelocityX && absVelocityX <= mMaxFlingVelocity
                && absVelocityY < absVelocityX) {
            dismiss = (velocityX < 0) == (deltaX < 0);
            dismissRight = mVelocityTracker.getXVelocity() > 0;
        }
        if (dismiss) {
            dismiss(mDownView, mDownPosition, dismissRight);
        }
        else {
            mDownView.animate()
                    .translationX(0)
                    .alpha(1)
                    .setDuration(mAnimationTime)
                    .setListener(null);
        }
        mVelocityTracker.recycle();
        mVelocityTracker = null;
        mDownX = 0;
        mDownView = null;
        mDownPosition = ListView.INVALID_POSITION;
        mSwiping = false;
    }

    private void cancel() {
        if (mVelocityTracker == null) {
            return;
        }
        if (mDownView != null) {
            mDownView.animate()
                    .translationX(0)
                    .alpha(1)
                    .setDuration(mAnimationTime)
                    .setListener(null);
        }
        mVelocityTracker.recycle();
        mVelocityTracker = null;
        mDownX = 0;
        mDownView = null;
        mDownPosition = ListView.INVALID_POSITION;
        mSwiping = false;
    }

    private float move(MotionEvent motionEvent) {
        mVelocityTracker.addMovement(motionEvent);
        float deltaX = motionEvent.getRawX() - mDownX;
        if (Math.abs(deltaX) > mSlop) {
            mSwiping = true;
            mListView.requestDisallowInterceptTouchEvent(true);
            MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
            cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                    (motionEvent.getActionIndex()
                            << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
            mListView.onTouchEvent(cancelEvent);
            cancelEvent.recycle();
        }
        return deltaX;
    }

    private void dismiss(final View view, final int position, boolean dismissRight) {
        ++mDismissAnimationRefCount;
        if (view == null) {
            mCallbacks.onDismiss(mListView, new int[]{position});
            return;
        }
        view.animate()
                .translationX(dismissRight ? mViewWidth : -mViewWidth)
                .alpha(0)
                .setDuration(mAnimationTime)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        performDismiss(view, position);
                    }
                });
    }

    private View getViewForPosition(int position) {
        int index = position
                - (mListView.getFirstVisiblePosition() - mListView.getHeaderViewsCount());
        return (index >= 0 && index < mListView.getChildCount())
                ? mListView.getChildAt(index)
                : null;
    }

    class PendingDismissData implements Comparable<PendingDismissData> {
        public int position;
        public View view;

        public PendingDismissData(int position, View view) {
            this.position = position;
            this.view = view;
        }

        @Override
        public int compareTo(@NonNull PendingDismissData other) {
            return other.position - position;
        }
    }

    private void performDismiss(final View dismissView, int dismissPosition) {
        final ViewGroup.LayoutParams lp = dismissView.getLayoutParams();
        final int originalHeight = dismissView.getHeight();
        ValueAnimator animator = ValueAnimator.ofInt(originalHeight, 1).setDuration(mAnimationTime);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                --mDismissAnimationRefCount;
                if (mDismissAnimationRefCount == 0) {
                    Collections.sort(mPendingDismisses);
                    int[] dismissPositions = new int[mPendingDismisses.size()];
                    for (int i = mPendingDismisses.size() - 1; i >= 0; i--) {
                        dismissPositions[i] = mPendingDismisses.get(i).position;
                    }
                    mCallbacks.onDismiss(mListView, dismissPositions);
                    for (PendingDismissData pendingDismiss : mPendingDismisses) {
                        pendingDismiss.view.setAlpha(1f);
                        pendingDismiss.view.setTranslationX(0);
                        ViewGroup.LayoutParams lp = pendingDismiss.view.getLayoutParams();
                        lp.height = originalHeight;
                        pendingDismiss.view.setLayoutParams(lp);
                    }
                    mPendingDismisses.clear();
                }
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lp.height = (Integer) valueAnimator.getAnimatedValue();
                dismissView.setLayoutParams(lp);
            }
        });
        mPendingDismisses.add(new PendingDismissData(dismissPosition, dismissView));
        animator.start();
    }
}