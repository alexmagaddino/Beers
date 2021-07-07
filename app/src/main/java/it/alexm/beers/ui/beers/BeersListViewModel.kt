package it.alexm.beers.ui.beers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import it.alexm.beers.data.repository.BeersPagingSource
import it.alexm.beers.data.vo.Beer
import it.alexm.beers.data.vo.BrewDate
import it.alexm.beers.ui.beers.DateSession.cleanDate
import it.alexm.beers.ui.beers.DateSession.endDate
import it.alexm.beers.ui.beers.DateSession.startDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BeersListViewModel : ViewModel() {

    private fun pager(start: String? = null, end: String? = null) = Pager(
        config = PagingConfig(pageSize = 25, enablePlaceholders = false),
        pagingSourceFactory = { BeersPagingSource(start, end) }
    ).flow.cachedIn(viewModelScope)

    fun getBeers(fromRefresh: Boolean = false): Flow<PagingData<Beer>> {
        if (fromRefresh) cleanDate()

        return pager()
    }

    fun getBeersInRange(start: BrewDate, end: BrewDate): Flow<PagingData<Beer>> {
        startDate = start
        endDate = end
        return pager(start.toString(), end.toString())
    }
}