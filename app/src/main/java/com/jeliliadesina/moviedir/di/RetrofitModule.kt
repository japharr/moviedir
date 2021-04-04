package com.jeliliadesina.moviedir.di

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jeliliadesina.moviedir.BuildConfig
import com.jeliliadesina.moviedir.api.ApiKeyInterceptor
import com.jeliliadesina.moviedir.api.MovieDbService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun provideGson() =
        GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()

    @Singleton
    @Provides
    fun provideHttpCache(@ApplicationContext context: Context) =
        Cache(context.cacheDir, (1024 * 1024 * 1024).toLong())

    @Singleton @Provides
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

    @Singleton @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MovieDbService.ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(gson))
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            //.addConverterFactory(ScalarsConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDbService(retrofit: Retrofit) = retrofit.create(MovieDbService::class.java)
}
