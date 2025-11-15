package com.tarangini.traiana.lib.api.kyc

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized

data class States(
  val docType : String = "",
  val docNumber : String = "",
  val docImage : String = "",
  val issuedAt : String = "",
  val address : String = "",
  val emergencyName : String = "",
  val emergencyRelation : String = "",
  val emergencyNumber: String = "",
  val registerRequest : Async<String> = Uninitialized
) : MavericksState