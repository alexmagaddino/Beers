package it.alexm.beers.ui.beers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithView
import it.alexm.beers.R

@EpoxyModelClass
abstract class EmptyState : EpoxyModelWithView<View>() {

    override fun buildView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_empty_state, parent, false)
    }
}