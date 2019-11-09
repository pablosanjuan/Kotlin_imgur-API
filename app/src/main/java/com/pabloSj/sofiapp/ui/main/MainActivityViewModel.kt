package com.pabloSj.sofiapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.pabloSj.sofiapp.data.api.CardApiResponse
import com.pabloSj.sofiapp.data.model.Card
import com.pabloSj.sofiapp.data.model.Event
import com.pabloSj.sofiapp.data.model.Resource
import com.pabloSj.sofiapp.data.repository.Repository
import com.pabloSj.sofiapp.utils.AbsentLiveData
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(repository: Repository) :ViewModel() {

    var page = 1
    val cachedJobsResultsList = MutableLiveData<List<Card>>()

    private val _searching = MutableLiveData<String>()
    fun search(searchData: String) {
        _searching.value = searchData
    }

    val searching: LiveData<Resource<CardApiResponse>> = Transformations.switchMap(_searching) {
        if (it == null)
            AbsentLiveData.create()
        else
            repository.doSearchRepo(page,"cat", "cat")
    }

}