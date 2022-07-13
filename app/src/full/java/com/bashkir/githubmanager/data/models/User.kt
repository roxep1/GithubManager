package com.bashkir.githubmanager.data.models

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    val bio: String?,
    @SerializedName("blog") val site: String?,
    @SerializedName("twitter_username") val twitterUsername: String?,
    @SerializedName("html_url") val htmlUrl: String,
    val followers: Int,
    val following: Int
)
