package com.example.koincryptocrazy.repository

import com.example.koincryptocrazy.model.CryptoList
import com.example.koincryptocrazy.service.CryptoAPI
import com.example.koincryptocrazy.util.Resource
import javax.inject.Inject

class CryptoDownloadImpl @Inject constructor(
    val api : CryptoAPI
) : CryptoDownload {
    override suspend fun downloadCryptos(): Resource<CryptoList> {

        return try {
            val response = api.getData()
            if (response.isSuccessful){
                response.body().let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }

        }catch (e : Exception){
            Resource.error("No data",null)
        }



    }
}