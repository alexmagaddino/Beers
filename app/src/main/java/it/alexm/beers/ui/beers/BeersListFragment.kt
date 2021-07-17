package it.alexm.beers.ui.beers

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import it.alexm.beers.R
import it.alexm.beers.databinding.FragmentBeersListBinding
import it.alexm.beers.showDatePicker
import it.alexm.beers.showToast
import it.alexm.beers.ui.beers.DateSession.endDate
import it.alexm.beers.ui.beers.DateSession.startDate
import it.alexm.beers.ui.beers.controller.BeersController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BeersListFragment : Fragment() {

    private var binding: FragmentBeersListBinding? = null
    private val viewModel: BeersListViewModel by viewModels()
    private val controller by lazy {
        BeersController {
            findNavController().navigate(
                BeersListFragmentDirections.toDetailFragment(it.name, it)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBeersListBinding.inflate(inflater)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recyclerView?.adapter = controller

        binding?.offersCard?.setOnClickListener {
            findNavController().navigate(
                BeersListFragmentDirections.toOffersFragment()
            )
        }

        binding?.swipeRefreshLayout?.setOnRefreshListener {
            controller.refresh()
        }

        observe()

        setHasOptionsMenu(true)
    }

    private fun observe() {
        lifecycleScope.launch {

            viewModel.getBeers().collectLatest { beers ->
                binding?.swipeRefreshLayout?.isRefreshing = false
                controller.submitData(beers)
            }
        }

        viewModel.searchCollect {
            showToast { it }
            lifecycleScope.launch {
                viewModel.getBeers(it).collectLatest { beers ->
                    binding?.swipeRefreshLayout?.isRefreshing = false
                    controller.submitData(beers)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_beers_list, menu)
        val search = menu.findItem(R.id.search).actionView as SearchView
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.addSearchQuery(newText)
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.range_date) showDatePicker(
            startDate?.inMillis(),
            endDate?.inMillis()
        ) { start, end ->
            showToast {
                getString(R.string.between, start.toString(), end.toString())
            }
            startDate = start
            endDate = end
            lifecycleScope.launch {
                viewModel.getBeers(start = start.toString(), end = end.toString()).collectLatest {
                    binding?.swipeRefreshLayout?.isRefreshing = false
                    controller.submitData(it)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}