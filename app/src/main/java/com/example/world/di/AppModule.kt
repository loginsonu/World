package com.example.world.di

import com.example.world.BuildConfig
import com.example.world.core.data.remote.Api
import com.example.world.core.data.remote.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.example.world.country.data.repository.CountryRepositoryImpl
import com.example.world.country.domain.repository.CountryRepository
import com.example.world.detail.data.repository.CountryDetailsRepositoryImpl
import com.example.world.detail.domain.repository.CountryDetailsRepository

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(
        authInterceptor: AuthInterceptor
    ):Api{
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideCountryRepository(
        api: Api
    ):CountryRepository{
        return CountryRepositoryImpl(api)
    }
    @Provides
    @Singleton
    fun provideCountryDetailsRepository(
        api: Api
    ): CountryDetailsRepository{
        return CountryDetailsRepositoryImpl(api)
    }
}