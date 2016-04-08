package com.hitherejoe.bourbon.ui.browse;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourbon.common.data.model.Shot;
import com.hitherejoe.bourbon.common.ui.browse.BrowseMvpView;
import com.hitherejoe.bourbon.common.ui.browse.BrowsePresenter;
import com.hitherejoe.bourbon.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class BrowseActivity extends BaseActivity implements BrowseMvpView {

    @Bind(R.id.pager_shots)
    GridViewPager mShotsPager;
    @Bind(R.id.page_indicator)
    DotsPageIndicator mPageIndicator;
    @Bind(R.id.progress)
    ProgressBar mProgress;
    @Bind(R.id.layout_error)
    View mErrorView;
    @Bind(R.id.text_error)
    TextView mErrorText;
    @Bind(R.id.image_error)
    ImageView mErrorImage;

    @Inject
    BrowsePresenter mBrowsePresenter;

    private BrowseAdapter mBrowseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        ButterKnife.bind(this);
        activityComponent().inject(this);
        mBrowsePresenter.attachView(this);
        mBrowseAdapter = new BrowseAdapter(this);
        mShotsPager.setAdapter(mBrowseAdapter);
        mPageIndicator.setPager(mShotsPager);
        mPageIndicator.setDotColor(ContextCompat.getColor(this, R.color.colorPrimary));
        mPageIndicator.setDotColorSelected(ContextCompat.getColor(this, R.color.colorAccent));
        mPageIndicator.setDotRadius(4);
        mBrowsePresenter.getShots(20, 0);
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
    public void showShots(List<Shot> shots) {
        mBrowseAdapter.setShots(shots);
        mBrowseAdapter.notifyDataSetChanged();
        for (Shot shot : shots) {
            Timber.e(shot.title);
        }
    }

    @Override
    public void setComplete() {

    }

    @Override
    public void showError() {
        Timber.e("ERROR");
    }

    @Override
    public void showEmpty() {

    }
}