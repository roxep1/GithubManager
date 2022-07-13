package com.bashkir.githubmanager.data

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    var uiState by mutableStateOf(UiState())
        private set

    fun getRepositories(query: String) {
        uiState = uiState.copy(
            repositories = Pager(
                PagingConfig(pageSize = 30)
            ) {
                RepositoriesPagingSource(api, query)
            }.flow.cachedIn(viewModelScope)
        )
    }
}

data class UiState(
    val repositories: Flow<PagingData<Repository>>? = null
)