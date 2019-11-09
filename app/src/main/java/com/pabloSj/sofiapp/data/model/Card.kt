package com.pabloSj.sofiapp.data.model

import com.google.gson.annotations.SerializedName

data class Card(
    @SerializedName("type") var type: String,
    @SerializedName("account_id") val account_id:Int,
    @SerializedName("account_url") val account_url:String,
    @SerializedName("comment_count") val comment_count:Int,
    @SerializedName("cover") val cover:String,
    @SerializedName("cover_height") val cover_height:Int,
    @SerializedName("cover_width") val cover_width:Int,
    @SerializedName("datetime") val datetime:Int,
    @SerializedName("description") val description:String,
    @SerializedName("downs") val downs:Int,
    @SerializedName("favorite") val favorite:Boolean,
    @SerializedName("id") val id:String,
    @SerializedName("images_count") val images_count:Int,
    @SerializedName("in_gallery") val in_gallery:Boolean,
    @SerializedName("is_ad") val is_ad:Boolean,
    @SerializedName("is_album") val is_album:Boolean,
    @SerializedName("layout") val layout:String,
    @SerializedName("link") val link:String,
    @SerializedName("nsfw") val nsfw:Boolean,
    @SerializedName("points") val points:Int,
    @SerializedName("privacy") val privacy:String,
    @SerializedName("score") val score:Int,
    @SerializedName("section") val section:String,
    @SerializedName("title") val title:String,
    @SerializedName("topic") val topic:String,
    @SerializedName("topic_id") val topic_id:Int,
    @SerializedName("ups") val ups:Int,
    @SerializedName("views") val views:Int,
    @SerializedName("vote") val vote:String
)