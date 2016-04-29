package com.hitherejoe.bourbon.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.hitherejoe.bourbon.injection.component.ActivityComponent;
import com.hitherejoe.bourbon.injection.component.DaggerActivityComponent;
import com.hitherejoe.bourboncommon.BourbonApplication;
import com.hitherejoe.bourboncommon.injection.module.ActivityModule;

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
