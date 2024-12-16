package com.example.world.di

import com.example.world.BuildConfig
import com.example.world.data.remote.api.Api
import com.example.world.data.remote.network.interceptor.AuthInterceptor
import com.example.world.data.repository.CountryRepositoryImpl
import com.example.world.domain.repository.CountryRepository
import com.example.world.data.repository.CountryDetailsRepositoryImpl
import com.example.world.domain.repository.CountryDetailsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Provides
    fun provideCountryRepository(api: Api): CountryRepository = CountryRepositoryImpl(api)

    @Provides
    fun provideCountryDetailsRepository(api: Api): CountryDetailsRepository = CountryDetailsRepositoryImpl(api)
}
