package com.hitherejoe.bourbon.injection.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.hitherejoe.bourbon.injection.ActivityContext;
import com.hitherejoe.bourbon.ui.browse.BrowseAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mActivity;
    }

    @Provides
    FragmentManager providesFragmentManager() {
        return ((FragmentActivity) mActivity).getSupportFragmentManager();
    }

}