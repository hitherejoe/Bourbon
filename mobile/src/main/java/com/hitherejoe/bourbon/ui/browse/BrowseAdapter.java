package com.hitherejoe.bourbon.ui.browse;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourbon.common.data.model.Shot;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BrowseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<Shot> mShots;
    private ClickListener mClickListener;
    private int mCurrentPage;
    private boolean mIsLastPage;
    private int mPageCount;

    @Inject
    public BrowseAdapter() {
        mShots = Collections.emptyList();
        mCurrentPage = 0;
        mIsLastPage = false;
        mPageCount = 20;
    }

    public void setShots(List<Shot> shots) {
        mShots = shots;
    }

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public int getNextCurrentPage() {
        return mCurrentPage++;
    }

    public boolean getIsLastPage() {
        return mIsLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        mIsLastPage = isLastPage;
    }

    public int getPageCount() {
        return mPageCount;
    }

    @Override
    public int getItemCount() {
        return mShots.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mShots.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_shot, parent, false);
        return new ShotViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ShotViewHolder) {
            Shot shot = (Shot) mShots.get(position);
            ShotViewHolder viewHolder = (ShotViewHolder) holder;
            viewHolder.mShot = shot;
            viewHolder.likeCountText.setText(shot.likes_count);
            viewHolder.viewCountText.setText(shot.title);
            Glide.with(holder.itemView.getContext()).load(shot.images.normal)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.shotImage);
        } else {
            ((ProgressViewHolder) holder).progress.setIndeterminate(true);
        }
    }

    public class ShotViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image_shot) ImageView shotImage;
        @Bind(R.id.image_like) ImageView likeCountImage;
        @Bind(R.id.text_like_count) TextView likeCountText;
        @Bind(R.id.text_title) TextView viewCountText;
        @Bind(R.id.layout_header) View viewHeader;

        public Shot mShot;

        public ShotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        mClickListener.onShotClick(mShot, shotImage, viewCountText, likeCountImage, likeCountText, viewHeader);
                    }
                }
            });
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.progress) ProgressBar progress;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ClickListener {
        void onShotClick(Shot shot, ImageView shotImage, TextView titleText, ImageView likeImage, View likeView, View header);
    }

}
