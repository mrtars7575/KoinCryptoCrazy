package com.example.koincryptocrazy.repository

import com.example.koincryptocrazy.model.CryptoList
import com.example.koincryptocrazy.util.Resource

interface CryptoDownload {

    suspend fun downloadCryptos() : Resource<CryptoList>

}