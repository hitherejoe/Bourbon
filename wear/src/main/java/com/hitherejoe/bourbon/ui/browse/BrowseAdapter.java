package com.hitherejoe.bourbon.ui.browse;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourbon.common.data.model.Shot;
import com.hitherejoe.bourbon.ui.comment.CommentActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BrowseAdapter extends GridPagerAdapter {

    private Context mContext;
    private List<Shot> mShots;

    public BrowseAdapter(Context context) {
        mContext = context;
        mShots = Collections.emptyList();
    }

    public void setShots(List<Shot> shots) {
        mShots = shots;
    }

    @Override
    public int getRowCount() {
        return mShots.size();
    }

    @Override
    public int getColumnCount(int i) {
        return 2;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int i, int i1) {
        Shot shot = mShots.get(i);

        if (i1 == 0) {

            //  View child = viewGroup.getChildAt(i);
            //  if (child == null) {
            FrameLayout view = (FrameLayout) LayoutInflater.from(mContext).inflate(R.layout.item_shot, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.bind(shot);
            view.setTag(viewHolder);
            viewGroup.addView(view);
            //  } else {
            //      viewHolder = (ViewHolder) child.getTag();
            //      viewHolder.bind(shot);
            //  }
            return viewHolder.frameLayout;
        } else if (i1 == 1) {
            View view = (View) LayoutInflater.from(mContext).inflate(R.layout.item_detail, viewGroup, false);
            DetailViewHolder viewHolder = new DetailViewHolder(view);
            viewHolder.bind(shot);
            view.setTag(viewHolder);
            viewGroup.addView(view);
            return viewHolder.frameLayout;
        }

        return null;
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int i, int i1, Object o) {
        viewGroup.removeView((View) o);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view.equals(o);
    }

    class ViewHolder {
        ImageView imageView;

        FrameLayout frameLayout;

        public ViewHolder(FrameLayout frameLayout) {
            this.frameLayout = frameLayout;
            imageView = (ImageView) frameLayout.findViewById(R.id.image_shot);
        }

        void bind(final Shot shot) {
            Glide.with(mContext).load(shot.images.getImage()).into(imageView);
            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(CommentActivity.newIntent(mContext, shot));
                }
            });
        }
    }

    class DetailViewHolder {
        TextView titleView;
        TextView likeText;
        TextView viewText;

        View frameLayout;

        public DetailViewHolder(View frameLayout) {
            this.frameLayout = frameLayout;
            titleView = (TextView) frameLayout.findViewById(R.id.text_title);
            likeText = (TextView) frameLayout.findViewById(R.id.text_like_count);
            viewText = (TextView) frameLayout.findViewById(R.id.text_view_count);
        }

        void bind(final Shot shot) {
            titleView.setText(shot.title);
            likeText.setText(shot.likes_count);
            viewText.setText(shot.views_count);
        }
    }
}