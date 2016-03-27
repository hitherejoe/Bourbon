package com.hitherejoe.bourbon.injection.component;

import com.hitherejoe.bourbon.injection.PerActivity;
import com.hitherejoe.bourbon.injection.module.ActivityModule;
import com.hitherejoe.bourbon.ui.browse.BrowseActivity;
import com.hitherejoe.bourbon.ui.browse.BrowseFragment;
import com.hitherejoe.bourbon.ui.shot.ShotActivity;
import com.hitherejoe.bourbon.ui.shot.ShotFragment;

import dagger.Component;

/**
 * This component injects dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(BrowseFragment browseFragment);
    void inject(ShotFragment shotFragment);
    void inject(ShotActivity shotActivity);
}