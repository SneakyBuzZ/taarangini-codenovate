package com.tarangini.traiana.lib.api.user

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized

data class States(
  val fullname : String = "",
  val gender : String = "",
  val dob : String = "",
  val email : String = "",
  val mobile : String = "",
  val bloodType : String = "",
  val nationality : String = "",
  val registerRequest : Async<String> = Uninitialized,
  val getUserRequest : Async<User?> = Uninitialized,
  val currentLocation : Pair<Double,Double> = Pair(0.0,0.0)
) : MavericksState

data class User(
  val fullname: String,
  val touristId : String,
  val qrcode : String,
  val email : String,
  val dob : String,
  val gender : String
)

