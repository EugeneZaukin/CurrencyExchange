package com.example.currencyexchange.ui.main

import androidx.lifecycle.ViewModel
import com.example.currencyexchange.data.model.InfoOfValutes
import com.example.currencyexchange.data.networkRepository.NetworkRepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    fun getValutes() {
        val networkRepositoryImpl = NetworkRepositoryImpl()

        networkRepositoryImpl.getValutes(object : Callback<InfoOfValutes> {
            override fun onResponse(call: Call<InfoOfValutes>, response: Response<InfoOfValutes>) {
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()
                }
            }

            override fun onFailure(call: Call<InfoOfValutes>, t: Throwable) {

            }
        })
    }
}