package com.jeliliadesina.moviedir.movie.ui

import android.R.attr.bitmap
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.palette.graphics.Palette
import androidx.palette.graphics.Palette.PaletteAsyncListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.ImageViewTarget
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.snackbar.Snackbar
import com.jeliliadesina.moviedir.MainActivity
import com.jeliliadesina.moviedir.R
import com.jeliliadesina.moviedir.data.Result
import com.jeliliadesina.moviedir.databinding.FragmentMovieBinding
import com.jeliliadesina.moviedir.movie.data.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.bumptech.glide.request.target.Target as GlideTarget


class MovieFragment : Fragment(), MainActivity.OnBackPressedListener {
    private val movieViewModel: MovieViewModel by viewModel()

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
                        setUpPalette(binding, movie)
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

    private fun setUpPalette(binding: FragmentMovieBinding, movie: Movie) {
        Glide.with(requireContext())
            .asBitmap()
            .load(movie.backdropPathUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(object : RequestListener<Bitmap?> {
                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: GlideTarget<Bitmap?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    if (resource != null) {
                        Palette.from(resource).generate { palette ->
                            val vibrantColor = palette!!.getVibrantColor(resources.getColor(R.color.primary_500))
                            binding.collapsingToolbar.setContentScrimColor(vibrantColor)
                            binding.collapsingToolbar.setStatusBarScrimColor(resources.getColor(R.color.black_trans80))
                        }
                    }
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: GlideTarget<Bitmap?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(binding.image)
    }
}