package io.aiico.currency.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.aiico.currency.BuildConfig
import io.aiico.currency.data.repository.ExchangeRatesRepositoryImpl
import io.aiico.currency.data.source.ExchangeRatesApi
import io.aiico.currency.domain.repository.ExchangeRatesRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
interface DataModule {

    @Binds
    fun bindExchangeRatesRepository(impl: ExchangeRatesRepositoryImpl): ExchangeRatesRepository

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideRetrofit(): Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.API_BASE_URL)
            .build()


        @Provides
        @JvmStatic
        fun provideExchangeRatesApi(retrofit: Retrofit): ExchangeRatesApi =
            retrofit.create(ExchangeRatesApi::class.java)
    }
}
