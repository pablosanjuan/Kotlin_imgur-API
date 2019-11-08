package com.pabloSj.sofiapp.data.api

import com.pabloSj.sofiapp.utils.API_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient{
    lateinit var retrofit: Retrofit
    fun provideHttpClient(): Retrofit {
        retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
}