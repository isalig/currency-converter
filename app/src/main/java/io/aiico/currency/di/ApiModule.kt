package io.aiico.currency.di

import dagger.Module
import dagger.Provides
import io.aiico.currency.BuildConfig
import io.aiico.currency.CurrencyApi
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
object ApiModule {

    @Provides
    @JvmStatic
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .baseUrl(BuildConfig.API_BASE_URL)
            .build()


    @Provides
    @JvmStatic
    fun provideCurrencyApi(retrofit: Retrofit): CurrencyApi =
        retrofit.create(CurrencyApi::class.java)
}