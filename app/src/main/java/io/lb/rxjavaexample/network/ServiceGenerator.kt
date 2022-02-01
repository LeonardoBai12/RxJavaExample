package io.lb.rxjavaexample.network

import io.lb.rxjavaexample.util.GeneralConstants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {
    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(GeneralConstants.BASE_URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
    private val retrofit = retrofitBuilder.build()
    val requestApi: RequestApi = retrofit.create(RequestApi::class.java)
}