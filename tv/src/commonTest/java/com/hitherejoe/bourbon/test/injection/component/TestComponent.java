package com.hitherejoe.bourbon.test.injection.component;

import com.hitherejoe.bourboncommon.injection.component.ApplicationComponent;
import com.hitherejoe.bourbon.test.injection.module.ApplicationTestModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}