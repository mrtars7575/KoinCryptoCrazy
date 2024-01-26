package com.example.koincryptocrazy.di

import com.example.koincryptocrazy.repository.CryptoDownload
import com.example.koincryptocrazy.repository.CryptoDownloadImpl
import com.example.koincryptocrazy.service.CryptoAPI
import com.example.koincryptocrazy.viewmodel.CryptoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        val BASE_URL = "https://raw.githubusercontent.com/"
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)
    }


    single<CryptoDownload> {
        CryptoDownloadImpl(get())
    }

    viewModel{
        CryptoViewModel(get())
    }
}