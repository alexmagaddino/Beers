package it.alexm.beers.ui.beers.controller

import androidx.recyclerview.widget.RecyclerView
import it.alexm.beers.data.vo.Beer
import it.alexm.beers.databinding.BeerElementBinding
import it.alexm.beers.load

typealias BeerClickAction = (Beer) -> Unit

class BeerViewHolder(private val binding: BeerElementBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(beer: Beer?, beerClick: BeerClickAction) {
        beer?.apply {
            binding.name.text = name
            binding.tagline.text = tagline
            binding.beerImage.load(imageUrl)
            binding.root.setOnClickListener {
                beerClick.invoke(this)
            }
        }
    }
}