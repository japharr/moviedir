package com.jeliliadesina.moviedir.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val COROUTINE_SCOPE_NAMED = "coroutineScopeIO"

val appModule = module {
    single(named(COROUTINE_SCOPE_NAMED)) { CoroutineScope(Dispatchers.IO) }
}