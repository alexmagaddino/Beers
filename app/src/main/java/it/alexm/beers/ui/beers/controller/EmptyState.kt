package it.alexm.beers.ui.beers.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.alexm.beers.R

class EmptyState(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind() {
        LayoutInflater.from(itemView.context)
            .inflate(R.layout.layout_empty_state, itemView as ViewGroup, false)
    }
}