package com.hitherejoe.bourbon.ui.shot;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.hitherejoe.bourboncorecommon.data.model.Comment;
import com.hitherejoe.bourboncorecommon.data.model.Shot;
import com.hitherejoe.bourbon.ui.shot.widget.CommentView;
import com.hitherejoe.bourbon.ui.shot.widget.ShotView;

import java.util.ArrayList;
import java.util.List;

public class ShotAdapter extends PagerAdapter {

    private Context mContext;
    private List<Object> mPagerViews = new ArrayList<>();
    private Shot mShot;

    public ShotAdapter(Context context) {
        mContext = context;
    }

    public void setShot(Shot shot) {
        mPagerViews.add(shot);
    }

    public void setComments(List<Comment> comments) {
        mPagerViews.addAll(comments);
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
       // LayoutInflater inflater = LayoutInflater.from(mContext);
        Object object = mPagerViews.get(position);
        View view;
        if (object instanceof Shot) {
       // if (position == 0) {
            view = new ShotView(mContext);
            ((ShotView) view).setShot((Shot) mPagerViews.get(position));
        } else if (object instanceof Comment) {
            view = new CommentView(mContext);
            ((CommentView) view).setComment((Comment) mPagerViews.get(position));
        } else {
            throw new RuntimeException("Unsupported Object instance");
        }
        collection.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mPagerViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}