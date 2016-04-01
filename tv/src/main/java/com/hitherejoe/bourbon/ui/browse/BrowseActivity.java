package com.hitherejoe.bourbon.ui.browse;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourbon.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BrowseActivity extends BaseActivity {

    @Bind(R.id.frame_container)
    FrameLayout mFragmentContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        ButterKnife.bind(this);

        getFragmentManager().beginTransaction()
                .replace(mFragmentContainer.getId(), BrowseFragment.newInstance()).commit();
    }

    @Override
    public boolean onSearchRequested() {
        return true;
    }

}