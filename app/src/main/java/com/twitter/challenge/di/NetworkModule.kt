package com.twitter.challenge.di

import com.google.gson.GsonBuilder
import com.twitter.challenge.network.api.WeatherApi
import com.twitter.challenge.network.repository.WeatherRepository
import com.twitter.challenge.network.repository.WeatherRepositoryImpl
import com.twitter.challenge.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        val gson = GsonBuilder().create()
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Constants.BASE_URL)
                .build()
    }

    @Singleton
    @Provides
    fun getWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

}