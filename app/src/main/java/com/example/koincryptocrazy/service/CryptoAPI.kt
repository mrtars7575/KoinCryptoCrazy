package com.example.koincryptocrazy.service

import com.example.koincryptocrazy.model.CryptoList
import retrofit2.Response
import retrofit2.http.GET

interface CryptoAPI {
    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    suspend fun getData(): Response<CryptoList>

}