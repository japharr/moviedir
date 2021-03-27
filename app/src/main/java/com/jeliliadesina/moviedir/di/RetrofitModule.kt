package com.jeliliadesina.moviedir.di

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jeliliadesina.moviedir.BuildConfig
import com.jeliliadesina.moviedir.api.ApiKeyInterceptor
import com.jeliliadesina.moviedir.api.MovieDbService
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val retrofitModule = module {
    fun provideGson() = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()

    fun provideHttpCache(context: Context)
            = Cache(context.cacheDir, (1024 * 1024 * 1024).toLong())

    fun provideHttpClient(cache: Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.cache(cache)
        //client.connectTimeout(60, TimeUnit.SECONDS)
        //client.readTimeout(60, TimeUnit.SECONDS)
        //client.writeTimeout(60, TimeUnit.SECONDS)
        client.addInterceptor(ApiKeyInterceptor(BuildConfig.API_DEVELOPER_KEY)).build()
        //client.connectionSpecs(listOf(ConnectionSpec.CLEARTEXT))
        //client.connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS))

        return client.build()
    }


    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MovieDbService.ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(gson))
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            //.addConverterFactory(ScalarsConverterFactory.create())
            .client(client)
            .build()
    }

    fun provideMovieDbService(retrofit: Retrofit) = retrofit.create(MovieDbService::class.java)

    single { provideGson() }
    single { provideHttpCache(get()) }
    single { provideHttpClient(get()) }
    single { provideRetrofit(get(), get()) }
    single { provideMovieDbService(get()) }

}
