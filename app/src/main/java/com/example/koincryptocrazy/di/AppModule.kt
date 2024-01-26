package com.example.koincryptocrazy.di

import com.example.koincryptocrazy.repository.CryptoDownload
import com.example.koincryptocrazy.repository.CryptoDownloadImpl
import com.example.koincryptocrazy.service.CryptoAPI
import com.example.koincryptocrazy.util.BASE_URL
import com.example.koincryptocrazy.viewmodel.CryptoViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

        @Singleton
        @Provides
        fun provideCryptoAPI()  = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)

        @Singleton
        @Provides

        fun provideCryptoDownloadRepo(api : CryptoAPI)  = CryptoDownloadImpl(api)
                as CryptoDownload

}