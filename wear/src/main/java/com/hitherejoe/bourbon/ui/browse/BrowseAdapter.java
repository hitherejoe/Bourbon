package com.hitherejoe.bourbon.ui.browse;


import android.content.Context;
import android.support.wearable.view.GridPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourboncorecommon.data.model.Shot;
import com.hitherejoe.bourbon.ui.shot.ShotActivity;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

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
        return 4;
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
            View view = (View) LayoutInflater.from(mContext).inflate(R.layout.item_user, viewGroup, false);
            UserViewHolder viewHolder = new UserViewHolder(view);
            viewHolder.bind(shot);
            view.setTag(viewHolder);
            viewGroup.addView(view);
            return viewHolder.frameLayout;
        } else if (i1 == 2) {
            View view = (View) LayoutInflater.from(mContext).inflate(R.layout.item_detail, viewGroup, false);
            DetailViewHolder viewHolder = new DetailViewHolder(view);
            viewHolder.bind(shot.likes_count, R.drawable.ic_favorite_accent_48dp);
            view.setTag(viewHolder);
            viewGroup.addView(view);
            return viewHolder.frameLayout;
        } else if (i1 == 3) {
            View view = (View) LayoutInflater.from(mContext).inflate(R.layout.item_detail, viewGroup, false);
            DetailViewHolder viewHolder = new DetailViewHolder(view);
            viewHolder.bind(shot.views_count, R.drawable.ic_visibility_accent_48dp);
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
                    mContext.startActivity(ShotActivity.newIntent(mContext, shot));
                }
            });
        }
    }

    class UserViewHolder {
        TextView titleView;
        ImageView userImage;
        TextView userName;

        View frameLayout;

        public UserViewHolder(View frameLayout) {
            this.frameLayout = frameLayout;
            titleView = (TextView) frameLayout.findViewById(R.id.text_title);
            userImage = (ImageView) frameLayout.findViewById(R.id.image_avatar);
            userName = (TextView) frameLayout.findViewById(R.id.text_user);
        }

        void bind(final Shot shot) {
            titleView.setText(shot.title);
            userName.setText(shot.user.username);
            Glide.with(mContext).load(shot.user.avatarUrl).into(userImage);
        }
    }

    class DetailViewHolder {
        TextView countText;
        View frameLayout;

        public DetailViewHolder(View frameLayout) {
            this.frameLayout = frameLayout;
            countText = (TextView) frameLayout.findViewById(R.id.text_count);
        }

        void bind(String text, int iconRes) {
            countText.setText(formatString(text));
            countText.setCompoundDrawablesWithIntrinsicBounds(0, iconRes, 0, 0);
        }
    }

    private String formatString(String number) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        int n = Integer.valueOf(number);
        return formatter.format(n);
    }
}