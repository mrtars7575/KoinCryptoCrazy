package com.example.koincryptocrazy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koincryptocrazy.model.CryptoList
import com.example.koincryptocrazy.repository.CryptoDownload
import com.example.koincryptocrazy.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import kotlin.coroutines.coroutineContext

class CryptoViewModel(
    val cryptoDownloadRepository : CryptoDownload
) : ViewModel() {

    val cryptoList = MutableLiveData<Resource<CryptoList>>()
    val cryptoError = MutableLiveData<Resource<Boolean>>()
    val cryptoLoading = MutableLiveData<Resource<Boolean>>()

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
        cryptoError.value = Resource.error(throwable.localizedMessage ?: "error!",data = true)
    }


    private var job : Job? = null

    fun getDataFromAPI(){
        cryptoLoading.value = Resource.loading(true)

        job = CoroutineScope(Dispatchers.IO + exceptionHandler ).launch {
            val resource = cryptoDownloadRepository.downloadCryptos()
            withContext(Dispatchers.Main){
                resource.data?.let {
                    cryptoLoading.value = Resource.loading(false)
                    cryptoError.value = Resource.error("",data = false)
                    cryptoList.value = resource
                }
            }
        }

    }


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }




}