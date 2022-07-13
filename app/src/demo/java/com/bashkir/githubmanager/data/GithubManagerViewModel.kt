package com.bashkir.githubmanager.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bashkir.githubmanager.data.models.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GithubManagerViewModel @Inject constructor(private val api: GitHubApi) : ViewModel() {
    val repositories by lazy { MutableLiveData<Flow<PagingData<Repository>>>() }

    fun getRepositories(query: String) {
        repositories.value = Pager(
            PagingConfig(pageSize = 30)
        ) {
            RepositoriesPagingSource(api, query)
        }.flow.cachedIn(viewModelScope)
    }
}