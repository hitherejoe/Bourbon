package com.hitherejoe.bourbon.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.hitherejoe.bourbon.common.BourbonApplication;
import com.hitherejoe.bourbon.common.injection.module.ActivityModule;
import com.hitherejoe.bourbon.component.ActivityComponent;
import com.hitherejoe.bourbon.component.DaggerActivityComponent;

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ActivityComponent activityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(BourbonApplication.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }

}
