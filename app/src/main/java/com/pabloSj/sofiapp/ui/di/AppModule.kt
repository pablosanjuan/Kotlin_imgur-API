package com.pabloSj.sofiapp.ui.di

import android.app.Application
import android.content.res.Resources
import com.pabloSj.sofiapp.ui.main.MainActivityViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideResources(application: Application): Resources = application.resources

    @Provides
    fun provideMainActivityViewModel(): Class<MainActivityViewModel> = MainActivityViewModel::class.java
}