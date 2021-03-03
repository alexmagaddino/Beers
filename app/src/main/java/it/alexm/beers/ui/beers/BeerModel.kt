package it.alexm.beers.ui.beers

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import it.alexm.beers.R
import it.alexm.beers.data.vo.Beer
import it.alexm.beers.databinding.BeerElementBinding
import it.alexm.beers.load

typealias BeerClickAction = (Beer) -> Unit

@EpoxyModelClass
abstract class BeerModel : EpoxyModelWithHolder<BeerModel.BeerHolder>() {

    @EpoxyAttribute
    lateinit var beer: Beer

    @EpoxyAttribute
    lateinit var beerClick: BeerClickAction

    override fun getDefaultLayout(): Int = R.layout.beer_element

    inner class BeerHolder : EpoxyHolder() {
        override fun bindView(itemView: View) {
            val binding = BeerElementBinding.bind(itemView)
            binding.name.text = beer.name
            binding.tagline.text = beer.tagline
            binding.beerImage.load(beer.imageUrl)
            binding.root.setOnClickListener {
                beerClick.invoke(beer)
            }
        }
    }
}