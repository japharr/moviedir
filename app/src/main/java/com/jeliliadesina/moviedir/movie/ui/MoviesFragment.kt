package com.jeliliadesina.moviedir.movie.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jeliliadesina.moviedir.BR
import com.jeliliadesina.moviedir.R
import com.jeliliadesina.moviedir.databinding.FragmentMoviesBinding
import com.jeliliadesina.moviedir.util.ConnectivityUtil
import com.jeliliadesina.moviedir.util.ui.BaseFragment
import com.jeliliadesina.moviedir.util.ui.GridSpacingItemDecoration
import com.jeliliadesina.moviedir.util.ui.VerticalItemDecoration
import com.jeliliadesina.moviedir.util.ui.hide
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MoviesFragment : BaseFragment<FragmentMoviesBinding, MoviesViewModel>(), MoviesNavigator {
    private val moviesViewModel: MoviesViewModel by viewModel()

    //private lateinit var binding: FragmentMoviesBinding
    private val adapter: MovieAdapter by lazy { MovieAdapter() }
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var recyclerView: RecyclerView

    private lateinit var gridLayoutManager: GridLayoutManager

    private val linearDecoration: RecyclerView.ItemDecoration by lazy {
        VerticalItemDecoration(
            resources.getDimension(R.dimen.margin_normal).toInt())
    }

    private val gridDecoration: RecyclerView.ItemDecoration by lazy {
        GridSpacingItemDecoration(
            SPAN_COUNT, resources.getDimension(R.dimen.margin_grid).toInt())
    }

    private var isLinearLayoutManager: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        view.findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)

        val conn = ConnectivityUtil.isConnected(requireContext())
        println("isConnected: $conn")

        moviesViewModel.connectivityAvailable = ConnectivityUtil.isConnected(requireContext())

        //moviesViewModel.load()

        println("isConnected after: $conn")

        linearLayoutManager = LinearLayoutManager(activity)
        gridLayoutManager = GridLayoutManager(activity, SPAN_COUNT)

        recyclerView = getViewDataBinding().recyclerView

        setLayoutManager()

        recyclerView.adapter = adapter

        subscribeUi(adapter)
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menu_list_representation, menu)
//        setDataRepresentationIcon(menu.findItem(R.id.list))
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.list -> {
//                isLinearLayoutManager = !isLinearLayoutManager
//                setDataRepresentationIcon(item)
//                setLayoutManager()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//
//    private fun setDataRepresentationIcon(item: MenuItem) {
//        item.setIcon(if (isLinearLayoutManager)
//            R.drawable.ic_grid_list_24dp else R.drawable.ic_list_white_24dp)
//    }

    private fun subscribeUi(adapter: MovieAdapter) {
        moviesViewModel.movies.observe(viewLifecycleOwner) {
            getViewDataBinding().progressBar.hide()
            adapter.submitList(it)
        }
    }

    private fun setLayoutManager() {
        //val recyclerView = binding.recyclerView

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
        const val SPAN_COUNT = 3
    }

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_movies

    override fun getViewModel() = moviesViewModel
}