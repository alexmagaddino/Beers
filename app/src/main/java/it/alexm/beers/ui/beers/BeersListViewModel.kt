package it.alexm.beers.ui.beers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import it.alexm.beers.data.api.BeersService
import it.alexm.beers.data.datasource.BeersPagingSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeersListViewModel @Inject constructor(private val service: BeersService) : ViewModel() {

    private val searchFlow = MutableStateFlow("")

    fun addSearchQuery(query: String?) {
        searchFlow.value = query ?: ""
    }

    fun searchCollect(action: (String) -> Unit) {
        viewModelScope.launch {
            searchFlow.debounce(400).filter(String::isNotEmpty).collect {
                action(it)
            }
        }
    }

    fun getBeers(beerName: String? = null, start: String? = null, end: String? = null) = Pager(
        config = PagingConfig(pageSize = 25, enablePlaceholders = false),
        pagingSourceFactory = {
            BeersPagingSource(service, beerName, start, end)
        }
    ).flow.cachedIn(viewModelScope)
}