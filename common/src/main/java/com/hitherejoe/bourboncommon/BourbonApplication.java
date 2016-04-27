package com.hitherejoe.bourboncommon;

import android.app.Application;
import android.content.Context;

import com.hitherejoe.bourbon.common.BuildConfig;
import com.hitherejoe.bourboncommon.injection.component.ApplicationComponent;
import com.hitherejoe.bourboncommon.injection.component.DaggerApplicationComponent;
import com.hitherejoe.bourboncommon.injection.module.ApplicationModule;

import timber.log.Timber;

public class BourbonApplication extends Application  {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static BourbonApplication get(Context context) {
        return (BourbonApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}

