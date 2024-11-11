package com.gilbersoncampos.cardgame.di

import com.gilbersoncampos.cardgame.BuildConfig
import com.gilbersoncampos.cardgame.data.remote.dataSource.DecksCardDataSource
import com.gilbersoncampos.cardgame.data.remote.dataSource.DecksCardDataSourceImpl
import com.gilbersoncampos.cardgame.data.remote.retrofit.DeckCardsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = BuildConfig.URL
private const val READ_TIMEOUT_SECONDS = 60L
private const val CONNECT_TIMEOUT_SECONDS = 60L

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()

    @Provides
    fun providesNetworkRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit.Builder): DeckCardsService =
        retrofit.baseUrl(BASE_URL).build().create(DeckCardsService::class.java)

    @Provides
    fun provideDecksCardApiHelper(apiHelper: DecksCardDataSourceImpl): DecksCardDataSource =
        apiHelper
}