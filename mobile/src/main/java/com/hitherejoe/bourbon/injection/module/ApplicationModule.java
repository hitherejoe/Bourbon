package com.hitherejoe.bourbon.injection.module;

import android.app.Application;
import android.content.Context;

import com.hitherejoe.bourbon.data.remote.BourbonService;
import com.hitherejoe.bourbon.data.remote.BourbonServiceFactory;
import com.hitherejoe.bourbon.injection.ApplicationContext;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies. Mainly singleton object that can be injected from
 * anywhere in the app.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    File provideCacheDir() {
        return mApplication.getCacheDir();
    }

    @Provides
    @Singleton
    BourbonService provideSecretsService() {
        return BourbonServiceFactory.makeBourbonService();
    }

}