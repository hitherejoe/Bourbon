package com.hitherejoe.bourboncorecommon.injection.component;

import com.hitherejoe.bourboncorecommon.injection.module.ApplicationTestModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}