package com.hitherejoe.bourbon.ui.shot.widget;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourboncorecommon.data.model.Shot;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShotView extends FrameLayout {

    @Bind(R.id.detail_shot) DetailView mShotDetail;
    @Bind(R.id.image_shot) ImageView mShotImage;

    private boolean mIsDetailViewAnimating = false;
    private boolean mIsDetailViewShowing = true;

    public ShotView(Context context) {
        super(context);
        init();
    }

    public ShotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ShotView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_shot, this);
        ButterKnife.bind(this);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (!mIsDetailViewAnimating) {
            if (event.getAction() == KeyEvent.ACTION_UP) {
                if (!mIsDetailViewShowing) {
                    animateViewIn();
                }
            } else if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (mIsDetailViewShowing) {
                    animateViewOut();
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public void setShot(Shot shot) {
        setBackgroundColor(Color.WHITE);
        Glide.with(getContext()).load(shot.images.getImage()).into(mShotImage);
        mShotDetail.setShot(shot);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animateViewOut();
            }
        }, 2000);
    }

    private void animateViewIn() {
        mShotDetail.animate().translationY(mShotDetail.getTop() + mShotDetail.getHeight())
                .setInterpolator(new LinearOutSlowInInterpolator())
                .setListener(mAnimatorListener).start();
    }

    private void animateViewOut() {
       mShotDetail.animate().translationY(mShotDetail.getTop() - mShotDetail.getHeight())
                .setInterpolator(new LinearOutSlowInInterpolator())
                .setListener(mAnimatorListener).start();
    }

    private Animator.AnimatorListener mAnimatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            mIsDetailViewAnimating = true;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mIsDetailViewAnimating = false;
            mIsDetailViewShowing = !mIsDetailViewShowing;
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

}