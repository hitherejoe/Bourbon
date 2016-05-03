package com.hitherejoe.bourboncommon.common.injection.component;

import android.app.Application;
import android.content.Context;

import com.hitherejoe.bourboncommon.common.data.DataManager;
import com.hitherejoe.bourboncommon.common.data.remote.BourbonService;
import com.hitherejoe.bourboncommon.common.injection.ApplicationContext;
import com.hitherejoe.bourboncommon.common.injection.module.ApplicationModule;

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
