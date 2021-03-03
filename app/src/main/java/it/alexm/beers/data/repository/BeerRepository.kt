package it.alexm.beers.data.repository

import it.alexm.beers.data.api.lazyBeerService
import it.alexm.beers.data.vo.BrewDate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class BeerRepository(private val dispatcher: CoroutineDispatcher) {

    private val service by lazyBeerService()

    suspend fun getPagedBeers(
        page: Int,
        start: BrewDate? = null,
        end: BrewDate? = null
    ) = withContext(dispatcher) {
        service.getPagedBeers(page, start?.toString(), end?.toString())
    }
}