package it.alexm.beers.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import it.alexm.beers.R
import it.alexm.beers.databinding.FragmentBeerDetailBinding
import it.alexm.beers.load

class DetailFragment : Fragment(R.layout.fragment_beer_detail) {

    private var binding: FragmentBeerDetailBinding? = null
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBeerDetailBinding.inflate(inflater)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.beerImage?.load(args.beer.imageUrl)

        binding?.tagline?.text = args.beer.tagline
        binding?.firstBrewed?.text = getString(R.string.first_brewed, args.beer.firstBrewed)
        binding?.abv?.text = getString(R.string.abv, args.beer.abv.toString())
        binding?.description?.text = args.beer.description
    }
}