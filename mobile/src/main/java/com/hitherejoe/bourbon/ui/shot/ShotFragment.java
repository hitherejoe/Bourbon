package com.hitherejoe.bourbon.ui.shot;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourbon.common.data.model.Comment;
import com.hitherejoe.bourbon.common.data.model.Shot;
import com.hitherejoe.bourbon.common.ui.shot.ShotMvpView;
import com.hitherejoe.bourbon.common.ui.shot.ShotPresenter;
import com.hitherejoe.bourbon.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShotFragment extends Fragment implements ShotMvpView {

    public static final String ARGUMENT_SHOT = "ARGUMENT_SHOT";

    @Inject CommentAdapter mCommentsAdapter;
    @Inject ShotPresenter mShotPresenter;

    @Bind(R.id.layout_shot)
    View mRootView;
    @Bind(R.id.recycler_comments)
    RecyclerView mCommentsRecycler;
    @Bind(R.id.toolbar_shot)
    Toolbar mToolbar;
    @Bind(R.id.text_error_message)
    View mErrorText;
    @Bind(R.id.progress)
    View mProgress;
    @Bind(R.id.image_shot)
    ImageView mShotImage;
    @Bind(R.id.text_title)
    TextView mTitleText;

    @Bind(R.id.text_comments_title)
    TextView mCommentsTitleText;



    @Bind(R.id.text_like_count)
    TextView mLikeText;

    public static ShotFragment newInstance(Shot shot) {
        ShotFragment shotFragment = new ShotFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_SHOT, shot);
        shotFragment.setArguments(bundle);
        return shotFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((BaseActivity) getActivity()).activityComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_shot, container, false);
        ButterKnife.bind(this, fragmentView);

       // Slide slide = new Slide(Gravity.BOTTOM);
      //  slide.addTarget(R.id.layout_comments);
      //  slide.addTarget(R.id.text_title);
      //  slide.addTarget(R.id.layout_likes);
       // getActivity().getWindow().setEnterTransition(slide);



        Shot shot = getArguments().getParcelable(ARGUMENT_SHOT);

        if (shot == null) {
            throw new IllegalArgumentException("Shotfragment requires a shot instance");
        }

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mShotPresenter.attachView(this);

        mCommentsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCommentsRecycler.setHasFixedSize(true);
        mCommentsRecycler.setAdapter(mCommentsAdapter);

        mShotPresenter.getComments(shot.id, 0, 0);
        Glide.with(this).load(shot.images.normal)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mShotImage);
        mTitleText.setText(shot.title);
        mLikeText.setText(shot.likes_count);

        final ViewTreeObserver observer = mRootView.getViewTreeObserver();

        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
            //    mRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
              //  getActivity().startPostponedEnterTransition();
            }
        });

        return fragmentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // getActivity().supportFinishAfterTransition();
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
        mCommentsAdapter.setComments(comments);
        mCommentsAdapter.notifyDataSetChanged();
        mCommentsRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCommentsTitle(boolean hasComments) {
        mCommentsTitleText.setText(getString(hasComments ?
                R.string.text_recent_comments : R.string.text_no_recent_comments));
        mCommentsTitleText.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        mCommentsRecycler.setVisibility(View.GONE);
        mCommentsTitleText.setVisibility(View.GONE);
        mErrorText.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyComments() {
        mCommentsTitleText.setVisibility(View.VISIBLE);
        mCommentsRecycler.setVisibility(View.GONE);
    }
}
