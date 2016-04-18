package com.hitherejoe.bourbon.ui.shot;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourbon.common.data.model.Comment;
import com.hitherejoe.bourbon.common.data.model.Shot;
import com.hitherejoe.bourbon.common.ui.shot.ShotMvpView;
import com.hitherejoe.bourbon.common.ui.shot.ShotPresenter;
import com.hitherejoe.bourbon.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ShotActivity extends BaseActivity implements ShotMvpView {

    public static final String EXTRA_SHOT = "EXTRA_SHOT";

    int[] mOverrideKeyCodes = new int[] {
            KeyEvent.KEYCODE_DPAD_CENTER,
            KeyEvent.KEYCODE_DPAD_UP,
            KeyEvent.KEYCODE_DPAD_LEFT,
            KeyEvent.KEYCODE_DPAD_DOWN,
            KeyEvent.KEYCODE_DPAD_RIGHT
    };

    @Bind(R.id.pager_shot)
    ViewPager mShotPager;

    ShotAdapter mShotAdapter;
    @Inject ShotPresenter mShotPresenter;

    @Bind(R.id.page_indicator)
    PagerIndicatorView mPagerIndicatorView;

    private boolean mIsDetailViewAnimating = false;
    private boolean mIsDetailViewShowing = true;
    private Shot mShot;

    public static Intent newStartIntent(Context context, Shot shot) {
        Intent intent = new Intent(context, ShotActivity.class);
        intent.putExtra(EXTRA_SHOT, shot);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_shot_comments);
        activityComponent().inject(this);
        ButterKnife.bind(this);

        mShotPresenter.attachView(this);

        mShotAdapter = new ShotAdapter(this);

        mShot = getIntent().getParcelableExtra(EXTRA_SHOT);

        if (mShot == null) {
            throw new IllegalArgumentException("ShotActivity requires a shot instance!");
        }

        //mShotAdapter.setShot(shot);
        mShotAdapter.setShot(mShot);
        //  mShotAdapter.setComments(objects);
        mShotPager.setAdapter(mShotAdapter);
        mShotPresenter.getComments(mShot.id, ShotPresenter.SHOT_COUNT, ShotPresenter.SHOT_PAGE);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Timber.e("KEYYYYYASGHSABJF");

        if (!mIsDetailViewAnimating) {
            if (!mIsDetailViewShowing) {
           //     mAnimateIn.start();
            } else {
             //   mAnimateOut.start();
            }
        }

        return super.dispatchKeyEvent(event);
    }

    private void animateViewOut() {
       // mShotDetail.animate().translationY(140 + mShotDetail.getHeight())
         //       .setInterpolator(new LinearOutSlowInInterpolator())
           //     .setListener(mAnimatorListener).start();
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

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showComments(List<Comment> comments) {
        mShotAdapter.setComments(comments);
        mShotAdapter.notifyDataSetChanged();
        mPagerIndicatorView.attachViewPager(mShotPager);

    }

    @Override
    public void showError() {

    }

    @Override
    public void showEmptyComments() {

    }

    @Override
    public void showCommentsTitle(boolean hasComments) {

    }
}
