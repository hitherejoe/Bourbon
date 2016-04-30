package com.hitherejoe.bourbon.common.injection.component;

import android.app.Application;
import android.content.Context;

import com.hitherejoe.bourbon.common.data.DataManager;
import com.hitherejoe.bourbon.common.data.remote.BourbonService;
import com.hitherejoe.bourbon.common.injection.ApplicationContext;
import com.hitherejoe.bourbon.common.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();
    Application application();
    DataManager dataManager();
    BourbonService bourbonService();
}
