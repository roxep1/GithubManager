package com.bashkir.githubmanager.data.models

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("total_count") val totalCount: Int,
    val items: List<Repository>
)