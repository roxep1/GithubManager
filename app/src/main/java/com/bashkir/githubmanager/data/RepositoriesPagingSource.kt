package com.bashkir.githubmanager.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bashkir.githubmanager.data.models.Repository
import retrofit2.HttpException
import java.io.IOException

class RepositoriesPagingSource(
    private val api: GitHubApi,
    private val query: String
) : PagingSource<Int, Repository>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> =
        try {
            val nextPageNumber = params.key ?: 1
            val response = api.getRepositories(query, nextPageNumber, params.loadSize)
            LoadResult.Page(
                data = response.items,
                prevKey = null,
                nextKey =
                if (response.items.isNotEmpty() && response.items.size == params.loadSize)
                    nextPageNumber + 1
                else null
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }

    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
}