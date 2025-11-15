package com.tarangini.traiana.lib

import com.tarangini.traiana.lib.api.user.User
import com.tarangini.traiana.lib.dto.RegisterItineraryDTO
import com.tarangini.traiana.lib.dto.RegisterKycApiDTO
import com.tarangini.traiana.lib.dto.RegisterUserDTO
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.GET

data class DataResponse<T>(
  val statusCode: Int,
  val message: String,
  val payload: T?,
  val success: String
)

interface ApiService{

  @POST("user")
  suspend fun registerUser(@Body data : RegisterUserDTO) : DataResponse<String>

  @GET("user")
  suspend fun getUser() : DataResponse<User>

  @POST("user/auth")
  suspend fun getAuthStatus() : DataResponse<Unit>

  @POST("kyc")
  suspend fun registerKyc(@Body data : RegisterKycApiDTO) : DataResponse<Unit>

  @POST("itinerary")
  suspend fun registerItinerary(@Body data : RegisterItineraryDTO) : DataResponse<Unit>

}