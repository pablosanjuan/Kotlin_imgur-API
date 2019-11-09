package com.pabloSj.sofiapp.ui.di

import com.pabloSj.sofiapp.data.repository.DataRepository
import com.pabloSj.sofiapp.data.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideRepository(dataRepository: DataRepository): Repository {
        return dataRepository
    }
}