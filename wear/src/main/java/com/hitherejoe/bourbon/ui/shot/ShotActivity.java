package com.hitherejoe.bourbon.ui.shot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourboncorecommon.data.model.Comment;
import com.hitherejoe.bourboncorecommon.data.model.Shot;
import com.hitherejoe.bourboncorecommon.ui.shot.ShotMvpView;
import com.hitherejoe.bourboncorecommon.ui.shot.ShotPresenter;
import com.hitherejoe.bourbon.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShotActivity extends BaseActivity implements ShotMvpView {

    public static final String EXTRA_SHOT =
            "com.hitherejoe.bourbon.ui.comment.ShotActivity.EXTRA_SHOT";

    @Bind(R.id.image_message) ImageView mErrorImage;
    @Bind(R.id.page_indicator) PagerIndicatorView mPagerIndicatorView;
    @Bind(R.id.progress) ProgressBar mProgress;
    @Bind(R.id.layout_footer) RelativeLayout mFooterlayout;
    @Bind(R.id.text_message) TextView mErrorText;
    @Bind(R.id.text_message_action) TextView mActionText;
    @Bind(R.id.layout_message) View mErrorView;
    @Bind(R.id.pager_comments) ViewPager mShotsPager;

    @Inject ShotPresenter mShotPresenter;

    private CommentAdapter mCommentAdapter;
    private Shot mShot;

    public static Intent newIntent(Context context, Shot shot) {
        Intent intent = new Intent(context, ShotActivity.class);
        intent.putExtra(EXTRA_SHOT, shot);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        activityComponent().inject(this);

        mShot = getIntent().getParcelableExtra(EXTRA_SHOT);

        if (mShot == null) {
            throw new IllegalArgumentException("ShotActivity requires a shot instance!");
        }

        mShotPresenter.attachView(this);
        mCommentAdapter = new CommentAdapter(this);
        mShotsPager.setAdapter(mCommentAdapter);

        mShotPresenter.getComments(mShot.id, ShotPresenter.SHOT_COUNT, ShotPresenter.SHOT_PAGE);
    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showComments(List<Comment> comments) {
        mCommentAdapter.setComments(comments);
        mCommentAdapter.notifyDataSetChanged();
        mPagerIndicatorView.attachViewPager(mShotsPager);
        mPagerIndicatorView.bringToFront();
        setUIErrorState(false);
    }

    @Override
    public void showError() {
        mErrorImage.setImageResource(R.drawable.ic_sentiment_very_dissatisfied_gray_48dp);
        mErrorText.setText(getString(R.string.text_error_loading_comments));
        mActionText.setText(getString(R.string.text_reload));
        setUIErrorState(true);
    }

    @Override
    public void showEmptyComments() {
        mErrorImage.setImageResource(R.drawable.ic_empty_glass_gray_48dp);
        mErrorText.setText(getString(R.string.text_no_recent_comments));
        mActionText.setText(getString(R.string.text_check_again));
        setUIErrorState(true);
    }

    private void setUIErrorState(boolean isError) {
        mShotsPager.setVisibility(isError ? View.GONE : View.VISIBLE);
        mFooterlayout.setVisibility(isError ? View.GONE : View.VISIBLE);
        mErrorView.setVisibility(isError ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showCommentsTitle(boolean hasComments) {

    }

    @OnClick(R.id.text_message_action)
    public void onTextMessageActionClick() {
        mShotPresenter.getComments(mShot.id, ShotPresenter.SHOT_COUNT, ShotPresenter.SHOT_PAGE);
    }

}