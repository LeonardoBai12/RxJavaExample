package io.lb.rxjavaexample.di

import dagger.Module
import dagger.Provides
import io.lb.rxjavaexample.network.RetrofitServiceInterface
import io.lb.rxjavaexample.util.GeneralConstants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(GeneralConstants.BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun getRetrofitServiceInstance(retrofit: Retrofit): RetrofitServiceInterface {
        return retrofit.create(RetrofitServiceInterface::class.java)
    }
}