package com.jeliliadesina.moviedir.movie.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jeliliadesina.moviedir.R
import com.jeliliadesina.moviedir.databinding.FragmentMoviesBinding
import com.jeliliadesina.moviedir.util.ui.GridSpacingItemDecoration
import com.jeliliadesina.moviedir.util.ui.VerticalItemDecoration
import com.jeliliadesina.moviedir.util.ui.hide
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker
import com.treebo.internetavailabilitychecker.InternetConnectivityListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MoviesFragment : Fragment(), InternetConnectivityListener {
    private val moviesViewModel: MoviesViewModel by viewModels()

    private lateinit var binding: FragmentMoviesBinding
    private val adapter: MovieAdapter by lazy { MovieAdapter() }
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager

    private val linearDecoration: RecyclerView.ItemDecoration by lazy {
        VerticalItemDecoration(resources.getDimension(R.dimen.margin_normal).toInt())
    }

    private val gridDecoration: RecyclerView.ItemDecoration by lazy {
        GridSpacingItemDecoration(SPAN_COUNT, resources.getDimension(R.dimen.margin_grid).toInt())
    }

    private var isLinearLayoutManager: Boolean = false

    private var mInternetAvailabilityChecker: InternetAvailabilityChecker? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mInternetAvailabilityChecker = InternetAvailabilityChecker.getInstance()
        mInternetAvailabilityChecker?.addInternetConnectivityListener(this)

        //moviesViewModel.connectivityAvailable = ConnectivityUtil.isConnected(requireContext())

        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        context ?: return binding.root

        linearLayoutManager = LinearLayoutManager(activity)
        gridLayoutManager = GridLayoutManager(activity, SPAN_COUNT)

        setLayoutManager()
        binding.recyclerView.adapter = adapter

        subscribeUi(adapter)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        view.findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)
    }

    private fun subscribeUi(adapter: MovieAdapter) {
        moviesViewModel.movies.observe(viewLifecycleOwner) {
            Timber.v("data size: ${it.size}")
            binding.progressBar.hide()
            adapter.submitList(it)
        }
    }


    private fun setLayoutManager() {
        val recyclerView = binding.recyclerView

        var scrollPosition = 0
        // If a layout manager has already been set, get current scroll position.
        if (recyclerView.layoutManager != null) {
            scrollPosition = (recyclerView.layoutManager as LinearLayoutManager)
                .findFirstCompletelyVisibleItemPosition()
        }

        if (isLinearLayoutManager) {
            recyclerView.removeItemDecoration(gridDecoration)
            recyclerView.addItemDecoration(linearDecoration)
            recyclerView.layoutManager = linearLayoutManager
        } else {
            recyclerView.removeItemDecoration(linearDecoration)
            recyclerView.addItemDecoration(gridDecoration)
            recyclerView.layoutManager = gridLayoutManager
        }

        recyclerView.scrollToPosition(scrollPosition)
    }

    companion object {
        const val SPAN_COUNT = 2
    }

    override fun onInternetConnectivityChanged(isConnected: Boolean) {
        moviesViewModel.connectivityAvailable.set(isConnected)
    }

    override fun onDetach() {
        super.onDetach()
        mInternetAvailabilityChecker?.removeInternetConnectivityChangeListener(this)
    }
}