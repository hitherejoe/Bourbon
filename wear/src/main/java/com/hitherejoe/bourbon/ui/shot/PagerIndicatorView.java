package com.hitherejoe.bourbon.ui.shot;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.hitherejoe.bourbon.R;

/**
 * Simple view pager indicator that uses circles to indicate the pages.
 * Just place this view in your layout and call {@link #attachViewPager(ViewPager)} to use it
 * with your view pager.
 */
public class PagerIndicatorView extends LinearLayout {

    private final int mIndicatorSize = dpToPx(4);
    private final int mIndicatorMargin = dpToPx(2);

    public int dpToPx(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
    private ViewPager mViewPager;

    public PagerIndicatorView(Context context) {
        super(context);
        init();
    }

    public PagerIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PagerIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PagerIndicatorView(Context context, AttributeSet attrs, int defStyleAttr,
                              int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
    }

    /**
     * Attaches the view pager to this indicator. Please make sure your you set a PagerAdapter in
     * your ViewPager before calling this method.
     */
    public void attachViewPager(ViewPager viewPager) {
        detachViewPager();

        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);

        PagerAdapter adapter = mViewPager.getAdapter();
        if (adapter == null) {
            throw new RuntimeException("Set a PagerAdapter in the ViewPager before attaching it");
        }

        updateViewIndicators(adapter.getCount());
        selectIndicatorAtPosition(viewPager.getCurrentItem(), false);
    }

    /**
     * Clear any listener set in the view pager and its reference.
     */
    public void detachViewPager() {
        if (mViewPager != null) {
            mViewPager.removeOnPageChangeListener(mOnPageChangeListener);
            mViewPager = null;
        }
    }

    private void updateViewIndicators(int numIndicators) {
        removeAllViews();

        for (int i = 0; i < numIndicators; i++) {
            addView(makeIndicatorView());
        }
    }

    private View makeIndicatorView() {
        View view = new View(getContext());
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(mIndicatorSize, mIndicatorSize);
        params.setMargins(mIndicatorMargin, mIndicatorMargin, mIndicatorMargin, mIndicatorMargin);
        view.setLayoutParams(params);
        view.setBackgroundResource(R.drawable.circle_accent);
        return view;
    }

    private void selectIndicatorAtPosition(int position, boolean animated) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (i == position) {
                child.setBackgroundResource(R.drawable.circle_accent);
                scaleViewTo(child, 1.5f, animated);
            } else {
                child.setBackgroundResource(R.drawable.circle_primary_dark);
                scaleViewTo(child, 1, animated);
            }
        }
    }

    private void scaleViewTo(View view, float scale, boolean animated) {
        if (view.getScaleX() == scale && view.getScaleY() == scale) {
            return; // nothing to do here!
        }

        if (animated) {
            view.animate()
                    .scaleX(scale).scaleY(scale)
                    .setDuration(200)
                    .start();
        } else {
            view.setScaleX(scale);
            view.setScaleY(scale);
        }
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener =
            new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset,
                                           int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    selectIndicatorAtPosition(position, true);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            };
}