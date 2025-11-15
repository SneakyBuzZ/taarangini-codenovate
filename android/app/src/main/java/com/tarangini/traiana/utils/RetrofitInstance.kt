package com.tarangini.traiana.utils

import android.content.Context
import com.tarangini.traiana.BuildConfig
import retrofit2.Retrofit
import com.tarangini.traiana.lib.ApiService
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
  private lateinit var retrofit: Retrofit

  fun init(context: Context){
    val client = OkHttpClient
      .Builder()
      .addInterceptor(AuthInterceptor(context))
      .build()

    retrofit = Retrofit
      .Builder()
      .baseUrl(BuildConfig.API_URL)
      .client(client)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  val api : ApiService by lazy {
    retrofit.create(ApiService::class.java)
  }
}