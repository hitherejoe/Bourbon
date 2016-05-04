package com.hitherejoe.bourbon.ui.shot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourboncorecommon.data.model.Shot;
import com.hitherejoe.bourbon.ui.base.BaseActivity;

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

        ShotFragment shotFragment = ShotFragment.newInstance(shot);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_container, shotFragment)
                .commit();
    }

}