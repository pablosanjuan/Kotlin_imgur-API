package com.pabloSj.sofiapp.data.api.service

import com.pabloSj.sofiapp.data.api.CardApiResponse
import com.pabloSj.sofiapp.utils.PATH
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {

    @Headers("Authorization: Client-ID 126701cd8332f32")
    @GET("{page}?")
    //@GET("1?")
    fun getSearch(
        @Path("page") page: Int,
        @Query("q") category: String,
        @Query("q_type") typeFilter: String
    ):Call<CardApiResponse>
}