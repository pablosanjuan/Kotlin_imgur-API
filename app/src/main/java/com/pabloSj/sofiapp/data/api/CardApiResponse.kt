package com.pabloSj.sofiapp.data.api

import com.google.gson.annotations.SerializedName
import com.pabloSj.sofiapp.data.model.Card

data class CardApiResponse (
    @SerializedName("success") var success: String,
    @SerializedName("status") var status: String,
    @SerializedName("data") var data: List<Card> = emptyList()
)