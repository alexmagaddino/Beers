package it.alexm.beers.ui.beers

import com.airbnb.epoxy.TypedEpoxyController
import it.alexm.beers.data.vo.Beer

class BeersController(private val beerClick: BeerClickAction) : TypedEpoxyController<List<Beer>>() {

    fun addBeers(newBeers: List<Beer>) {
        val distinct = ((currentData ?: emptyList()) + newBeers).distinct()
        setData(distinct.toList())
    }

    fun refreshBeers(pickerBeers: List<Beer>) {
        setData(pickerBeers.distinct().toList())
    }

    override fun buildModels(beers: List<Beer>?) {
        if (beers != null && beers.isNotEmpty()) beers.forEach {
            beer {
                id(it.hashCode())
                beer(it)
                beerClick(beerClick)
            }
        }
        else emptyState {
            id("empty state")
            spanSizeOverride { _, _, _ -> 2 }
        }
    }
}