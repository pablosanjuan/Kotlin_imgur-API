package com.pabloSj.sofiapp.ui.di

import com.pabloSj.sofiapp.ui.main.MainActivity
import com.pabloSj.sofiapp.ui.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

	@ContributesAndroidInjector(modules = [MainActivityModule::class])
	abstract fun contributeMainActivity(): MainActivity

}