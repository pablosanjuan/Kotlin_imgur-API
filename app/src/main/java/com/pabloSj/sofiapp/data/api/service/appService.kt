package com.pabloSj.sofiapp.data.api.service

import androidx.lifecycle.LiveData
import com.pabloSj.sofiapp.data.api.ApiResponse
import com.pabloSj.sofiapp.data.api.CardApiResponse
import retrofit2.http.*

interface appService {

    @Headers("Authorization: Client-ID 126701cd8332f32")
    @GET("{page}?")
    //@GET("1?")
    fun doSearch(
        @Path("page") page: Int,
        @Query("q") category: String,
        @Query("q_type") typeFilter: String
    ): LiveData<ApiResponse<CardApiResponse>>
}