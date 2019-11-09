package com.pabloSj.sofiapp.data.repository

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.pabloSj.sofiapp.R
import com.pabloSj.sofiapp.data.api.ApiEmptyResponse
import com.pabloSj.sofiapp.data.api.ApiErrorResponse
import com.pabloSj.sofiapp.data.api.ApiSuccessResponse
import com.pabloSj.sofiapp.data.api.CardApiResponse
import com.pabloSj.sofiapp.data.api.service.appService
import com.pabloSj.sofiapp.data.model.Event
import com.pabloSj.sofiapp.data.model.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(private val service: appService, private val resources: Resources): Repository {

	override fun doSearchRepo(page: Int, whatField: String, whereField: String): LiveData<Resource<CardApiResponse>> {
		return Transformations.map(service.doSearch(
			page = page,
			category =  "cats",
			typeFilter = "png"))
		{ response ->
			when (response) {
				is ApiSuccessResponse -> Resource.success(response.body)
				is ApiEmptyResponse -> Resource.error(resources.getString(R.string.app_name), null)
				is ApiErrorResponse -> Resource.error(response.errorMessage, null)
			}
		}
	}
}