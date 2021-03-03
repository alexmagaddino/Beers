package it.alexm.beers.ui.beers

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import it.alexm.beers.R
import it.alexm.beers.databinding.FragmentBeersListBinding
import it.alexm.beers.showDatePicker
import it.alexm.beers.showToast
import it.alexm.beers.ui.beers.DateSession.endDate
import it.alexm.beers.ui.beers.DateSession.startDate

class BeersListFragment : Fragment() {

    private var currentPage: Int = 1
    private var previousTotal: Int = 0

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

        setRecycler()

        viewModel.getBeers(currentPage)

        observe()

        binding?.swipeRefreshLayout?.setOnRefreshListener {
            currentPage = 1
            previousTotal = 0
            viewModel.getBeers(currentPage, true)
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_beers_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.range_date)
            showDatePicker(startDate?.inMillis(), endDate?.inMillis()) { start, end ->
                showToast {
                    getString(R.string.between, start.toString(), end.toString())
                }
                viewModel.getBeersInRange(start, end)
            }
        return super.onOptionsItemSelected(item)
    }

    private fun observe() {
        viewModel.liveError.observe(viewLifecycleOwner) {
            showToast { "Errore" }
        }

        viewModel.liveBeers.observe(viewLifecycleOwner) { (beers, refresh) ->
            binding?.swipeRefreshLayout?.isRefreshing = false

            if (refresh)
                controller.refreshBeers(beers)
            else
                controller.addBeers(beers)
        }
    }

    private fun setRecycler() = binding?.recyclerView?.apply {

        layoutManager = GridLayoutManager(context, 2, VERTICAL, false)
        setController(controller)
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = controller.adapter.itemCount
                val visibleItemCount = recyclerView.childCount
                val firstVisibleItem =
                    (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()

                previousTotal = totalItemCount

                if ((totalItemCount - visibleItemCount) <= (firstVisibleItem + 20)) {
                    currentPage += 1
                    viewModel.getBeers(currentPage)
                }
            }
        })
    }
}