package com.hitherejoe.bourbon.injection.component;

import android.app.Application;
import android.content.Context;

import com.hitherejoe.bourbon.data.DataManager;
import com.hitherejoe.bourbon.data.remote.BourbonService;
import com.hitherejoe.bourbon.injection.ApplicationContext;
import com.hitherejoe.bourbon.injection.module.ApplicationModule;
import com.hitherejoe.bourbon.ui.browse.BrowseAdapter;

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
