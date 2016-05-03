package com.hitherejoe.bourbon.ui.shot.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourboncorecommon.data.model.Shot;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailView extends RelativeLayout {

    private Shot mShot;

    @Bind(R.id.image_avatar)
    ImageView mAvatarImage;

    @Bind(R.id.text_like_count)
    TextView mLikeCountText;

    @Bind(R.id.text_view_count)
    TextView mViewCountText;

    @Bind(R.id.text_shot_name)
    TextView mShotNameText;

    @Bind(R.id.text_user_name)
    TextView mShotUserText;

    public DetailView(Context context) {
        super(context);
        init();
    }

    public DetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DetailView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_shot_detail, this);
        ButterKnife.bind(this);
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
    }

    public void setShot(Shot shot) {
        mShot = shot;
        Glide.with(getContext()).load(mShot.user.avatarUrl).into(mAvatarImage);
        mLikeCountText.setText(mShot.likes_count);
        mViewCountText.setText(mShot.views_count);
        mShotNameText.setText(mShot.title);
        mShotUserText.setText(mShot.user.username);
    }

}
