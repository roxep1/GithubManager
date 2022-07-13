package com.bashkir.githubmanager.data

import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubManagerViewModel @Inject constructor(private val api: GitHubApi) : ViewModel() {
    private val _userState: MutableStateFlow<UserState> = MutableStateFlow(UserState.Uninitialized)
    val userState: StateFlow<UserState> = _userState

    val repositories by lazy { MutableLiveData<Flow<PagingData<Repository>>>() }

    fun getRepositories(query: String) {
        repositories.value = Pager(
            PagingConfig(pageSize = 30)
        ) {
            RepositoriesPagingSource(api, query)
        }.flow.cachedIn(viewModelScope)
    }

    fun getUser(login: String) = viewModelScope.launch {
        _userState.value = UserState.Loading
        try {
            _userState.value = UserState.Success(api.getUser(login))
        } catch (e: Exception) {
            _userState.value = UserState.Error(e)
        }
    }
}

sealed class UserState {
    object Uninitialized : UserState()
    object Loading : UserState()
    data class Success(val user: User) : UserState()
    data class Error(val exception: Exception) : UserState()
}