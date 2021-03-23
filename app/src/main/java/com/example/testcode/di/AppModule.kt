package com.example.testcode.di

import android.content.Context
import com.example.testcode.BuildConfig
import com.example.testcode.data.network.ApiService
import com.example.testcode.data.pref.MyPreferences
import com.example.testcode.repository.DefaultMainRepository
import com.example.testcode.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): ApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideMainRepository(api: ApiService, pref: MyPreferences): MainRepository =
        DefaultMainRepository(api, pref)

    @Provides
    @Singleton
    fun providePreference(@ApplicationContext context: Context) = MyPreferences(context = context)
}