package it.alexm.beers.data.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val loggingInterceptor = HttpLoggingInterceptor {
    Log.d("HTTP BODY", it)
}.apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private fun getOkHttpClient() = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .addInterceptor(loggingInterceptor)
    .build()

private fun getRetrofitClient() = Retrofit.Builder()
    .client(getOkHttpClient())
    .baseUrl("https://api.punkapi.com/v2/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

fun lazyBeerService(): Lazy<BeersService> =
    lazy { getRetrofitClient().create(BeersService::class.java) }