package com.hitherejoe.bourbon.component;

import com.hitherejoe.bourboncommon.common.injection.PerActivity;
import com.hitherejoe.bourboncommon.common.injection.component.ApplicationComponent;
import com.hitherejoe.bourboncommon.common.injection.module.ActivityModule;
import com.hitherejoe.bourbon.ui.browse.BrowseActivity;
import com.hitherejoe.bourbon.ui.shot.ShotActivity;

import dagger.Component;

/**
 * This component injects dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(BrowseActivity browseActivity);
    void inject(ShotActivity shotActivity);

}