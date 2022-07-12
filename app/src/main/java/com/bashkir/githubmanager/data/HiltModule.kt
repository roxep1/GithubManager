package com.bashkir.githubmanager.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Singleton
    @Provides
    fun provideApi(): GitHubApi = Retrofit.Builder()
        .baseUrl(GitHubApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(GitHubApi::class.java)
}