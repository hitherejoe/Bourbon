package com.hitherejoe.bourbon.ui.browse;

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
import butterknife.OnClick;

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
        mBrowsePresenter.getShots(BrowsePresenter.SHOT_COUNT, BrowsePresenter.SHOT_PAGE);
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
    }

    @Override
    public void showError() {
        mErrorImage.setImageResource(R.drawable.ic_sentiment_very_dissatisfied_gray_48dp);
        mErrorText.setText(getString(R.string.text_error_loading_shots));
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        mErrorImage.setImageResource(R.drawable.ic_empty_glass_gray_48dp);
        mErrorText.setText(getString(R.string.text_no_shots));
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessageLayout(boolean show) {
        mErrorView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.layout_error)
    public void onErrorLayoutClick() {
        mBrowsePresenter.getShots(20, 0);
    }
}