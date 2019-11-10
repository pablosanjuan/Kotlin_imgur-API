package com.pabloSj.sofiapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.pabloSj.sofiapp.data.api.CardApiResponse
import com.pabloSj.sofiapp.data.model.Card
import com.pabloSj.sofiapp.data.model.Resource
import com.pabloSj.sofiapp.data.repository.Repository
import com.pabloSj.sofiapp.utils.AbsentLiveData
import com.pabloSj.sofiapp.utils.IMG_TYPE
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(repository: Repository) :ViewModel() {

    var page = MutableLiveData<Int?>()
    val cachedList = MutableLiveData<List<Card>>()
    val searchParameter = MutableLiveData<String?>()
    private val serachParam = MutableLiveData<String>()

    fun search(searchData: String) {
        serachParam.value = searchData
    }

    val searching: LiveData<Resource<CardApiResponse>> = Transformations.switchMap(serachParam) {
        if (it == null)
            AbsentLiveData.create()
        else
            repository.doSearchRepo(page.value!!,it, IMG_TYPE)
    }

    fun loadNextPage(category: String) {
                page.value = page.value?.plus(1)
                    search(category)
    }

}