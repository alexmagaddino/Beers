package it.alexm.beers.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import it.alexm.beers.data.vo.Beer

/**
 * created by alexm on 07/07/21
 */
class BeersPagingSource : PagingSource<Int, Beer>() {

    companion object {
        private const val STARTING_PAGE = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Beer>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Beer> {
        val pageIndex = params.key ?: STARTING_PAGE

    }
}