package com.jeliliadesina.moviedir.movie.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.snackbar.Snackbar
import com.jeliliadesina.moviedir.MainActivity
import com.jeliliadesina.moviedir.R
import com.jeliliadesina.moviedir.data.Result
import com.jeliliadesina.moviedir.databinding.FragmentMovieBinding
import com.jeliliadesina.moviedir.movie.data.Movie
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieFragment : Fragment(), MainActivity.OnBackPressedListener {
    private val movieViewModel: MovieViewModel by viewModels()

    private lateinit var movie: Movie

    private val args: MovieFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        movieViewModel.id = args.id

        val binding = DataBindingUtil.inflate<FragmentMovieBinding>(
            inflater, R.layout.fragment_movie, container, false
        ).apply { lifecycleOwner = this@MovieFragment }

        subscribeUi(binding)
        setCollapsibleToolbar(binding)

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setCollapsibleToolbar(binding: FragmentMovieBinding) {
        binding.collapsingToolbar.title = " "
        binding.appBarLayout.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.collapsingToolbar.title = movie.title
                    isShow = true
                } else if (isShow) {
                    binding.collapsingToolbar.title = " "
                    isShow = false
                }
            }
        })
    }

    override fun onBackPressed(): Boolean {
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        view.findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)
    }

    private fun subscribeUi(binding: FragmentMovieBinding) {
        movieViewModel.movie.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    //binding.progressBar.hide()
                    result.data?.let {
                        binding.movie = it
                        movie = it
                    }
                }
                Result.Status.LOADING -> {
                    //binding.progressBar.show()
                }
                Result.Status.ERROR -> {
                    //binding.progressBar.hide()
                    Snackbar.make(binding.coordinatorLayout, result.message!!, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        })
    }
}