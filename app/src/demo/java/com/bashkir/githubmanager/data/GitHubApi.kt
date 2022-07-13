package com.bashkir.githubmanager.data

import com.bashkir.githubmanager.data.models.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    companion object{
        const val BASE_URL = "https://api.github.com/"
    }

    @GET("/search/repositories")
    suspend fun getRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response
}