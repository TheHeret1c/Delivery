package com.example.delivery.utils.impl

import com.example.delivery.utils.DataBaseAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiBuilder {

    fun getApi() : DataBaseAPI {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(DataBaseAPI::class.java)
    }
    companion object {
        private const val BASE_URL = "https://thwzxkajcqlrkvtoqiuk.supabase.co"
    }
}