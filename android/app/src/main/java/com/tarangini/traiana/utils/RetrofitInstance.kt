package com.tarangini.traiana.utils

import android.content.Context
import android.util.Log
import com.tarangini.traiana.BuildConfig
import retrofit2.Retrofit
import com.tarangini.traiana.lib.ApiService
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
  private var retrofit: Retrofit? = null

  fun init(context: Context) {
    try {
      val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(context))
        .build()

      retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

      Log.d("RETROFIT_INIT", "Retrofit initialized successfully")
    } catch (e: Exception) {
      Log.e("RETROFIT_INIT", "Failed to initialize Retrofit", e)
    }
  }

  val api: ApiService
    get() = retrofit!!.create(ApiService::class.java)
}