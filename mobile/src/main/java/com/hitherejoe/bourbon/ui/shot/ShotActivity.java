package com.hitherejoe.bourbon.ui.shot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourbon.common.data.model.Comment;
import com.hitherejoe.bourbon.common.data.model.Shot;
import com.hitherejoe.bourbon.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShotActivity extends BaseActivity {

    public static final String EXTRA_SHOT = "com.hitherejoe.bourbon.ui.shot.EXTRA_SHOT";

    public static Intent getStartIntent(Context context, Shot shot) {
        Intent intent = new Intent(context, ShotActivity.class);
        intent.putExtra(EXTRA_SHOT, shot);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Shot shot = getIntent().getParcelableExtra(EXTRA_SHOT);
        if (shot == null) {
            throw new IllegalArgumentException("Shot activity requires a shot instance!");
        }

        activityComponent().inject(this);
        setContentView(R.layout.activity_shot);
       // Slide slide = new Slide();
       // slide.setDuration(1000);
       // getWindow().setReturnTransition(slide);

        ShotFragment shotFragment = ShotFragment.newInstance(shot);
       // postponeEnterTransition();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_container, shotFragment)
                .commit();
      //  getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // supportFinishAfterTransition();
    }
}