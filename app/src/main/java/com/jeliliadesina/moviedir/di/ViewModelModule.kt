package com.jeliliadesina.moviedir.di

import com.jeliliadesina.moviedir.movie.ui.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MoviesViewModel(get(), get(named(COROUTINE_SCOPE_NAMED))) }
}
