package it.alexm.beers.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import it.alexm.beers.data.api.lazyBeerService
import it.alexm.beers.data.vo.Beer

/**
 * created by alexm on 07/07/21
 */
class BeersPagingSource(
    private val start: String? = null,
    private val end: String? = null
) : PagingSource<Int, Beer>() {

    private val service by lazyBeerService()

    companion object {
        private const val STARTING_PAGE = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Beer>) =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Beer> {
        val pageIndex = params.key ?: STARTING_PAGE
        val r = service.getPagedBeers(pageIndex, start, end)
        return if (r.isSuccessful) {
            val nextPage = r.body()?.isNotEmpty()?.let { pageIndex + 1 }
            LoadResult.Page(
                data = r.body() ?: emptyList(),
                prevKey = if (pageIndex == STARTING_PAGE) null else pageIndex,
                nextKey = nextPage
            )
        } else
            LoadResult.Error(Exception(r.errorBody()?.string()))
    }
}