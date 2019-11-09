package com.pabloSj.sofiapp.ui.di

import com.pabloSj.sofiapp.data.api.service.appService
import com.pabloSj.sofiapp.utils.API_URL
import com.pabloSj.sofiapp.utils.LiveDataCallAdapterFactory
import dagger.Module
import retrofit2.Retrofit
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

import java.util.concurrent.TimeUnit

@Module
class NetModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.readTimeout(10, TimeUnit.SECONDS)
        httpClientBuilder.connectTimeout(10, TimeUnit.SECONDS)
        return httpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideHttpClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(API_URL)
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }

    @Provides
    fun provideAppService(retrofit: Retrofit): appService {
        return retrofit.create<appService>(appService::class.java)
    }
}