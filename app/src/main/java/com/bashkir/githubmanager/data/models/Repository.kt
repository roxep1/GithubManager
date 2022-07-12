package com.bashkir.githubmanager.data.models

import com.bashkir.githubmanager.base.LocalDateTimeJsonAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Repository(
    val id: Int,
    val name: String,
    val description: String?,
    val language: String?,
    @JsonAdapter(LocalDateTimeJsonAdapter::class)
    @SerializedName("updated_at")
    val updatedAt: LocalDateTime,
    @SerializedName("stargazers_count") val starsCount: Int,
    val owner: Owner
)
