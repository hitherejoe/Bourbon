package com.hitherejoe.bourbon.ui.browse;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourbon.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BrowseActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        ButterKnife.bind(this);

    }

    @Override
    public boolean onSearchRequested() {
        return true;
    }

}