package com.pabloSj.sofiapp.data.repository

import androidx.lifecycle.LiveData
import com.pabloSj.sofiapp.data.api.CardApiResponse
import com.pabloSj.sofiapp.data.model.Event
import com.pabloSj.sofiapp.data.model.Resource

interface Repository {
	fun doSearchRepo(page: Int, search_parameter: String, type_img: String): LiveData<Resource<CardApiResponse>>
}