package com.bashkir.githubmanager.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bashkir.githubmanager.data.models.Repository
import com.bashkir.githubmanager.data.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubManagerViewModel @Inject constructor(private val api: GitHubApi) : ViewModel() {
    var uiState by mutableStateOf(UiState())
        private set

    fun getRepositories(query: String) {
        uiState = uiState.copy(repositories = Pager(
            PagingConfig(pageSize = 30)
        ) {
            RepositoriesPagingSource(api, query)
        }.flow.cachedIn(viewModelScope))
    }

    fun getUser(login: String) = viewModelScope.launch {
        uiState = uiState.copy(user = UserState.Loading)
        uiState = try {
            uiState.copy(user = UserState.Success(api.getUser(login)))
        } catch (e: Exception) {
            uiState.copy(user = UserState.Error(e))
        }
    }
}

sealed class UserState {
    object Uninitialized : UserState()
    object Loading : UserState()
    data class Success(val user: User) : UserState()
    data class Error(val exception: Exception) : UserState()
}

data class UiState(
    val user: UserState = UserState.Uninitialized,
    val repositories : Flow<PagingData<Repository>>? = null
)