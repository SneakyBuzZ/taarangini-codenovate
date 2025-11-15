package com.tarangini.traiana.lib.api.kyc

import android.util.Log
import coil.network.HttpException
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.Success
import com.tarangini.traiana.lib.dto.RegisterKycApiDTO
import com.tarangini.traiana.utils.RetrofitInstance
import kotlinx.coroutines.launch

class KycViewModel(
  initialState: States
) : MavericksViewModel<States>(initialState) {

  fun register(data : RegisterKycApiDTO) {
    setState { copy(registerRequest = Loading()) }
    viewModelScope.launch {
      try {
        val apiData = modifyEnums(data)
        val response = RetrofitInstance.api.registerKyc(apiData)
        setState { copy(registerRequest = Success(response.message)) }
      }catch (e : Exception){
        setState { copy(registerRequest = Fail(e)) }
        if(e is HttpException){
          Log.d("REGISTER ERROR", "ERROR : ${e.response}")
        }
      }
    }
  }

  private fun modifyEnums(data : RegisterKycApiDTO) : RegisterKycApiDTO{
    val docType = when(data.docType){
      "Aadhaar Card" -> "aadhaar"
      "Passport" -> "passport"
      "Driving License" -> "driver_license"
      else -> ""
    }
    val issuedAt = when(data.issuedAt){
      "Govt. of India" -> "goi"
      "Unique Id Authority of India" -> "uidai"
      "Ministry of Enternal Affair" -> "mea"
      "Road Transport Authority" -> "rta"
      "Foreign Passport Authority" -> "fpa"
      "Other" -> "other"
      else -> ""
    }
    val relation = data.emergencyContact.relation.lowercase()
    return data.copy(
      docType = docType,
      issuedAt = issuedAt,
      emergencyContact = data.emergencyContact.copy(
        relation = relation
      )
    )
  }
}