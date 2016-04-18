package com.hitherejoe.bourbon.test.common.injection.component;

import com.hitherejoe.bourbon.common.injection.component.ApplicationComponent;
import com.hitherejoe.bourbon.test.common.injection.module.ApplicationTestModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}