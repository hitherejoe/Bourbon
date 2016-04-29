package com.hitherejoe.bourboncommon.injection.component;

import com.hitherejoe.bourboncommon.injection.module.ApplicationTestModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}