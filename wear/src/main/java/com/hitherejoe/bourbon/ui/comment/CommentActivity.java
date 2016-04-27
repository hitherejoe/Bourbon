package com.hitherejoe.bourbon.ui.comment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourboncommon.data.model.Comment;
import com.hitherejoe.bourboncommon.data.model.Shot;
import com.hitherejoe.bourboncommon.ui.shot.ShotMvpView;
import com.hitherejoe.bourboncommon.ui.shot.ShotPresenter;
import com.hitherejoe.bourbon.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CommentActivity extends BaseActivity implements ShotMvpView {

    public static final String EXTRA_SHOT =
            "com.hitherejoe.bourbon.ui.comment.CommentActivity.EXTRA_SHOT";

    @Bind(R.id.pager_shots)
    ViewPager mShotsPager;
    @Bind(R.id.progress)
    ProgressBar mProgress;
    @Bind(R.id.layout_error)
    View mErrorView;
    @Bind(R.id.text_error)
    TextView mErrorText;
    @Bind(R.id.image_error)
    ImageView mErrorImage;

    @Bind(R.id.page_indicator)
    PagerIndicatorView mPagerIndicatorView;

    @Inject
    ShotPresenter mShotPresenter;

    private CommentsAdapter mCommentAdapter;

    public static Intent newIntent(Context context, Shot shot) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra(EXTRA_SHOT, shot);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        activityComponent().inject(this);

        Shot shot = getIntent().getParcelableExtra(EXTRA_SHOT);

        if (shot == null) {
            throw new IllegalArgumentException("CommentActivity requires a shot instance!");
        }

        mShotPresenter.attachView(this);
        mCommentAdapter = new CommentsAdapter(this);
        mShotsPager.setAdapter(mCommentAdapter);

        mShotPresenter.getComments(shot.id, ShotPresenter.SHOT_COUNT, ShotPresenter.SHOT_PAGE);
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
    }

    @Override
    public void showError() {
        Timber.e("ERROR");
    }

    @Override
    public void showEmptyComments() {

    }

    @Override
    public void showCommentsTitle(boolean hasComments) {

    }

}