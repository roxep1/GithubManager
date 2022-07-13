package com.bashkir.githubmanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.bashkir.githubmanager.data.GithubManagerViewModel
import com.bashkir.githubmanager.ui.components.RepositoryCard
import com.bashkir.githubmanager.ui.components.RepositoryCardPlaceholder
import com.bashkir.githubmanager.ui.components.SearchTextField
import com.bashkir.githubmanager.ui.theme.normalPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(viewModel: GithubManagerViewModel) = Column {
    val searchText = remember { mutableStateOf(TextFieldValue()) }
    val lazyPagingRepositories = viewModel.uiState.repositories?.collectAsLazyPagingItems()

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Row {
        SearchTextField(
            searchTextState = searchText, onSearch = viewModel::getRepositories,
            modifier = Modifier.weight(1F)
        )
        if (remember { derivedStateOf { listState.firstVisibleItemIndex } }.value > 1)
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                },
                Modifier
                    .weight(0.15F)
                    .padding(end = normalPadding)
                    .align(CenterVertically)
            ) {
                Icon(Icons.Default.KeyboardArrowUp, null)
            }
    }

    if (lazyPagingRepositories != null)
        SwipeRefresh(
            state = rememberSwipeRefreshState(
                lazyPagingRepositories.loadState.refresh is LoadState.Loading ||
                        lazyPagingRepositories.loadState.append is LoadState.Loading
            ),
            onRefresh = {
                if (searchText.value.text.isNotBlank())
                    viewModel.getRepositories(searchText.value.text)
            }
        ) {
            LazyColumn(Modifier.fillMaxSize(), listState) {
                items(lazyPagingRepositories) {
                    if (it != null)
                        RepositoryCard(repository = it)
                    else RepositoryCardPlaceholder()
                }

                item {
                    if (lazyPagingRepositories.loadState.refresh is LoadState.Error)
                        Box(Modifier.fillParentMaxSize()) {
                            Text("Ошибка при отправке запроса :(", Modifier.align(Alignment.Center))
                        }
                }
            }
        }
}