package com.hitherejoe.bourboncommon.injection.component;

import android.app.Application;
import android.content.Context;

import com.hitherejoe.bourboncommon.data.DataManager;
import com.hitherejoe.bourboncommon.data.remote.BourbonService;
import com.hitherejoe.bourboncommon.injection.ApplicationContext;
import com.hitherejoe.bourboncommon.injection.module.ApplicationModule;

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
