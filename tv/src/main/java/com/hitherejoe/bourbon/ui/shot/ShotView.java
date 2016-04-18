package com.hitherejoe.bourbon.ui.shot;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourbon.common.data.model.Shot;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShotView extends FrameLayout {

    @Bind(R.id.image_shot)
    ImageView mShotImage;

    @Bind(R.id.detail_shot)
    DetailView mShotDetail;

    private Shot mShot;

    public ShotView(Context context) {
        super(context);
        init();
    }

    public ShotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ShotView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setShot(Shot shot) {
        mShot = shot;
        setBackgroundColor(Color.WHITE);
        Glide.with(getContext()).load(mShot.images.getImage()).into(mShotImage);
        mShotDetail.setShot(shot);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_shot, this);
        ButterKnife.bind(this);
    }
}
