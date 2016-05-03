package com.hitherejoe.bourboncommon.common.injection.component;

import com.hitherejoe.bourboncommon.common.injection.module.ApplicationTestModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}