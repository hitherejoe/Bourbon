package com.hitherejoe.bourbon.ui.browse;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.VerticalGridFragment;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.VerticalGridPresenter;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourbon.common.data.DataManager;
import com.hitherejoe.bourbon.common.data.model.Shot;
import com.hitherejoe.bourbon.common.ui.browse.*;
import com.hitherejoe.bourbon.ui.base.BaseActivity;
import com.hitherejoe.bourbon.ui.shot.ShotActivity;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;

public class BrowseFragment extends VerticalGridFragment implements BrowseMvpView {

    @Inject BrowsePresenter mBrowsePresenter;
    @Inject DataManager mDataManager;

    private static final int NUM_COLUMNS = 5;
    private static final int BACKGROUND_UPDATE_DELAY = 300;

    private BackgroundManager mBackgroundManager;
    private DisplayMetrics mMetrics;
    private Drawable mDefaultBackground;
    private Handler mHandler;
    private Runnable mBackgroundRunnable;
    private BrowseAdapter mBrowseAdapter;

    public static BrowseFragment newInstance() {
        return new BrowseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) getActivity()).activityComponent().inject(this);
        mBrowsePresenter.attachView(this);

        setupFragment();
        prepareBackgroundManager();
        setSearchAffordanceColor(ContextCompat.getColor(getActivity(),
                com.hitherejoe.bourbon.common.R.color.colorAccent));
        mBrowseAdapter = new BrowseAdapter();
        setAdapter(mBrowseAdapter);
        setOnItemViewSelectedListener(mOnItemViewSelectedListener);
        setOnItemViewClickedListener(mOnItemViewClickedListener);
        mBrowsePresenter.getShots(BrowsePresenter.SHOT_COUNT, BrowsePresenter.SHOT_PAGE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBackgroundRunnable != null) {
            mHandler.removeCallbacks(mBackgroundRunnable);
            mBackgroundRunnable = null;
        }
        mBackgroundManager = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        mBackgroundManager.release();
    }

    private void prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        mDefaultBackground =
                new ColorDrawable(ContextCompat.getColor(getActivity(),
                        com.hitherejoe.bourbon.common.R.color.gray));
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void setupFragment() {
        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);
        setBadgeDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.badge));

        mHandler = new Handler();
    }

    private void startBackgroundTimer(final URI backgroundURI) {
        if (mBackgroundRunnable != null) mHandler.removeCallbacks(mBackgroundRunnable);
        mBackgroundRunnable = new Runnable() {
            @Override
            public void run() {
                if (backgroundURI != null) updateBackground(backgroundURI.toString());
            }
        };
        mHandler.postDelayed(mBackgroundRunnable, BACKGROUND_UPDATE_DELAY);
    }

    protected void updateBackground(String uri) {
        int width = mMetrics.widthPixels;
        int height = mMetrics.heightPixels;
        Glide.with(getActivity())
                .load(uri)
                .asBitmap()
                .centerCrop()
                .error(mDefaultBackground)
                .into(new SimpleTarget<Bitmap>(width, height) {
                    @Override
                    public void onResourceReady(Bitmap resource,
                                                GlideAnimation<? super Bitmap>
                                                        glideAnimation) {
                        mBackgroundManager.setBitmap(resource);
                    }
                });
        if (mBackgroundRunnable != null) mHandler.removeCallbacks(mBackgroundRunnable);
    }

    private OnItemViewClickedListener mOnItemViewClickedListener = new OnItemViewClickedListener() {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (item instanceof Shot) {
                startActivity(ShotActivity.newStartIntent(getActivity(), (Shot) item));
            }
        }
    };

    private OnItemViewSelectedListener mOnItemViewSelectedListener = new OnItemViewSelectedListener() {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (item instanceof Shot) {
                String backgroundUrl = ((Shot) item).images.getImage();
                if (backgroundUrl != null) startBackgroundTimer(URI.create(backgroundUrl));
            }
        }
    };

    /** Browse MVP View method implementation **/

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showShots(List<Shot> shots) {
        mBrowseAdapter.addAll(0, shots);
    }

    @Override
    public void setComplete() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void hideErrorView() {

    }
}