package com.pabloSj.sofiapp.ui.di

import android.app.Application
import com.pabloSj.sofiapp.SofiApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
	AndroidInjectionModule::class,
	AppModule::class,
	ActivityBuildersModule::class,
	NetModule::class,
	DataModule::class
])
interface AppComponent {
	@Component.Builder
	interface Builder {
		@BindsInstance
		fun application(application: Application): Builder

		fun build(): AppComponent
	}

	fun inject(main: SofiApp)
}
