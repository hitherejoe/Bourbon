package com.hitherejoe.bourbon.ui.shot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourboncommon.common.data.model.Comment;
import com.hitherejoe.bourboncommon.common.data.model.Shot;
import com.hitherejoe.bourboncommon.common.ui.shot.ShotMvpView;
import com.hitherejoe.bourboncommon.common.ui.shot.ShotPresenter;
import com.hitherejoe.bourbon.ui.base.BaseActivity;
import com.hitherejoe.bourbon.ui.shot.widget.PagerIndicatorView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShotActivity extends BaseActivity implements ShotMvpView {

    public static final String EXTRA_SHOT = "EXTRA_SHOT";

    @Bind(R.id.pager_shot)
    ViewPager mShotPager;

    ShotAdapter mShotAdapter;
    @Inject
    ShotPresenter mShotPresenter;

    @Bind(R.id.page_indicator)
    PagerIndicatorView mPagerIndicatorView;

    public static Intent newStartIntent(Context context, Shot shot) {
        Intent intent = new Intent(context, ShotActivity.class);
        intent.putExtra(EXTRA_SHOT, shot);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_shot);
        activityComponent().inject(this);
        ButterKnife.bind(this);

        mShotPresenter.attachView(this);

        mShotAdapter = new ShotAdapter(this);

        Shot shot = getIntent().getParcelableExtra(EXTRA_SHOT);

        if (shot == null) {
            throw new IllegalArgumentException("ShotActivity requires a shot instance!");
        }

        mShotAdapter.setShot(shot);
        mShotPager.setAdapter(mShotAdapter);
        mShotPresenter.getComments(shot.id, ShotPresenter.SHOT_COUNT, ShotPresenter.SHOT_PAGE);
    }

    /** Shot MVP View method implementation **/

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
