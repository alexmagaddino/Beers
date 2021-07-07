package it.alexm.beers.ui.beers.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import it.alexm.beers.data.vo.Beer
import it.alexm.beers.databinding.BeerElementBinding

class BeersController(
    private val beerClick: BeerClickAction
) : PagingDataAdapter<Beer, BeerViewHolder>(BeerComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = BeerViewHolder(
        BeerElementBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, beerClick)
    }
}

object BeerComparator : DiffUtil.ItemCallback<Beer>() {
    override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean {
        return oldItem == newItem
    }
}