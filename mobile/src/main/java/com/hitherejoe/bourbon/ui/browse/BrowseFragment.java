package com.hitherejoe.bourbon.ui.browse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourbon.common.data.model.Shot;
import com.hitherejoe.bourbon.common.ui.browse.BrowseMvpView;
import com.hitherejoe.bourbon.common.ui.browse.BrowsePresenter;
import com.hitherejoe.bourbon.ui.base.BaseActivity;
import com.hitherejoe.bourbon.ui.shot.ShotActivity;
import com.hitherejoe.bourbon.util.DisplayMetricsUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BrowseFragment extends Fragment implements BrowseMvpView,
        BrowseAdapter.ClickListener {

    @Inject BrowseAdapter mBrowseAdapter;
    @Inject
    BrowsePresenter mBrowsePresenter;

    @Bind(R.id.recycler_view)
    RecyclerView mShotRecycler;
    @Bind(R.id.swipe_to_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.toolbar_browse)
    Toolbar mToolbar;

    @Bind(R.id.image_message)
    ImageView mErrorImage;
    @Bind(R.id.text_error_message)
    TextView mErrorText;

    @Bind(R.id.progress)
    ProgressBar mRecyclerProgress;

    private boolean mIsLoading;
    private boolean mIsTabletLayout;
    private boolean mIsLargeTablet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((BaseActivity) getActivity()).activityComponent().inject(this);
        getActivity().postponeEnterTransition();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_browse, container, false);
        ButterKnife.bind(this, fragmentView);
        mBrowsePresenter.attachView(this);

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mIsTabletLayout = DisplayMetricsUtil.isScreenW(600);
        mIsLargeTablet  = DisplayMetricsUtil.isScreenW(800);

        mBrowseAdapter.setClickListener(this);
        mShotRecycler.setLayoutManager(setLayoutManager());
        mShotRecycler.setHasFixedSize(true);
        mShotRecycler.setAdapter(mBrowseAdapter);

        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(
                com.hitherejoe.bourbon.common.R.color.colorAccent);
        mSwipeRefreshLayout.setColorSchemeResources(
                com.hitherejoe.bourbon.common.R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            //    mBrowsePresenter.getShots(mBrowseAdapter.getPageCount(),
              //          mBrowseAdapter.getCurrentPage());
            }
        });

        mBrowsePresenter.getShots(mBrowseAdapter.getPageCount(), mBrowseAdapter.getCurrentPage());
        return fragmentView;
    }

    private RecyclerView.LayoutManager setLayoutManager() {
        RecyclerView.LayoutManager layoutManager = null;
        if (!mIsTabletLayout) {
            layoutManager = new LinearLayoutManager(getActivity());
        } else {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 6);
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (mIsLargeTablet) {
                            // Designs for larger screens require a repeated pattern grid
                            // every 5 items so we do a modulo of the position. The grid will be
                            // alternating row lengths of 2 columns, followed by 3 columns.
                            int positionMod = position % 5;

                            switch (positionMod) {
                                case 0:
                                case 1:
                                    return 3;
                                default:
                                    return 2;
                            }
                        } else {
                            // Designs for smaller tablet screens require a 2 column grid, so we
                            // give each item a span of 3/6 columns to achieve this.
                            return 3;
                        }
                    }
                });
                layoutManager = gridLayoutManager;
            }
        return layoutManager;
    }

    @Override
    public void showProgress() {
        if (mShotRecycler.getVisibility() == View.VISIBLE && mBrowseAdapter.getItemCount() > 0) {
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mRecyclerProgress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerProgress.setVisibility(View.GONE);
    }

    @Override
    public void showShots(final List<Shot> shots) {
        mBrowseAdapter.setShots(shots);
        mBrowseAdapter.notifyDataSetChanged();
        mShotRecycler.setVisibility(View.VISIBLE);
        mIsLoading = false;
    }

    @Override
    public void setComplete() {
        mBrowseAdapter.setIsLastPage(true);
    }

    @Override
    public void showError() {
        mIsLoading = false;

        mErrorImage.setImageResource(R.drawable.bg_card);
        mErrorText.setText("Error");
        mErrorImage.setVisibility(View.VISIBLE);
        mErrorText.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        mIsLoading = false;

        mErrorImage.setImageResource(R.drawable.bg_card);
        mErrorText.setText("Empty");
        mErrorImage.setVisibility(View.VISIBLE);
        mErrorText.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShotClick(Shot shot, ImageView shotImage, TextView titleText, ImageView likeImage, View likeText, View header) {

        Intent intent = ShotActivity.getStartIntent(getActivity(), shot);
        //Pair squareParticipant = new Pair<>(shotImage, ViewCompat.getTransitionName(shotImage));
        //Pair toolbarParticipants = new Pair<>(titleText, ViewCompat.getTransitionName(titleText));
        //Pair likeImageParticipant = new Pair<>(likeImage, ViewCompat.getTransitionName(likeImage));
        //Pair likeTextParticipant = new Pair<>(likeText, ViewCompat.getTransitionName(likeText));
        //Pair head = new Pair<>(header, ViewCompat.getTransitionName(header));
        //ActivityOptionsCompat transitionActivityOptions =
          //      ActivityOptionsCompat.makeSceneTransitionAnimation(
            //            getActivity(), squareParticipant, toolbarParticipants, likeImageParticipant, likeTextParticipant, head);
        //startActivity(intent, transitionActivityOptions.toBundle());
        startActivity(intent);
    }
}
