package com.hitherejoe.bourboncorecommon.injection.component;

import android.app.Application;
import android.content.Context;

import com.hitherejoe.bourboncorecommon.data.DataManager;
import com.hitherejoe.bourboncorecommon.data.remote.BourbonService;
import com.hitherejoe.bourboncorecommon.injection.ApplicationContext;
import com.hitherejoe.bourboncorecommon.injection.module.ApplicationModule;

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
