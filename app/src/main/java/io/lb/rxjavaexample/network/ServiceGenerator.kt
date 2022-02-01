package io.lb.rxjavaexample.network

import io.lb.rxjavaexample.util.GeneralConstants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator {
    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(GeneralConstants.BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
    private val retrofit = retrofitBuilder.build()
    private val requestApi = retrofit.create(RequestApi::class.java)

    fun getRequestApi(): RequestApi? {
        return requestApi
    }
}