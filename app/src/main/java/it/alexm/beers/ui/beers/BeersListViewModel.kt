package it.alexm.beers.ui.beers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.alexm.beers.data.repository.BeerRepository
import it.alexm.beers.data.vo.Beer
import it.alexm.beers.data.vo.BrewDate
import it.alexm.beers.ui.beers.DateSession.endDate
import it.alexm.beers.ui.beers.DateSession.cleanDate
import it.alexm.beers.ui.beers.DateSession.startDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BeersListViewModel : ViewModel() {

    val liveError = MutableLiveData<Boolean>()
    val liveBeers = MutableLiveData<Pair<List<Beer>, Boolean>>()

    private val repo = BeerRepository(Dispatchers.IO)
    private var fromPicker: Boolean = false

    fun getBeers(page: Int, fromRefresh: Boolean = false) {
        if (fromRefresh) cleanDate()

        if (!fromPicker || fromRefresh)
            viewModelScope.launch {
                try {
                    fromPicker = false
                    liveBeers.value = repo.getPagedBeers(page, startDate, endDate) to fromRefresh
                } catch (t: Throwable) {
                    t.printStackTrace()
                    liveError.value = true
                }
            }
    }

    fun getBeersInRange(start: BrewDate, end: BrewDate) {
        startDate = start
        endDate = end
        viewModelScope.launch {
            try {
                fromPicker = true
                liveBeers.value = repo.getPagedBeers(1, start, end) to true
            } catch (t: Throwable) {
                t.printStackTrace()
                liveError.value = true
            }
        }
    }
}