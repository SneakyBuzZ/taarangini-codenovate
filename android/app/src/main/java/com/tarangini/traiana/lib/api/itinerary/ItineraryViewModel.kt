package com.tarangini.traiana.lib.api.itinerary

import android.util.Log
import coil.network.HttpException
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.Success
import com.tarangini.traiana.lib.dto.LocationDTO
import com.tarangini.traiana.lib.dto.RegisterItineraryDTO
import com.tarangini.traiana.lib.dto.RegisterItineraryFormDTO
import com.tarangini.traiana.utils.RetrofitInstance
import kotlinx.coroutines.launch

class ItineraryViewModel(
  initialState: States
) : MavericksViewModel<States>(initialState) {

  fun register(data : RegisterItineraryFormDTO, locations : List<LocationDTO>) {
    setState { copy(registerRequest = Loading()) }
    viewModelScope.launch {
      try {
        val transportMode = modifyTransportMode(data.transportMode)
        val apiData = RegisterItineraryDTO(
          arrivalDate = data.arrivalDate,
          departureDate = data.departureDate,
          transportMode = transportMode,
          locations = locations
        )
        val response = RetrofitInstance.api.registerItinerary(apiData)
        setState { copy(registerRequest = Success(response.message)) }
      }catch (e : Exception){
        setState { copy(registerRequest = Fail(e)) }
        if(e is HttpException){
          Log.d("REGISTER ERROR", "ERROR : ${e.response}")
        }
      }
    }
  }

  private fun modifyTransportMode(transportModes : String) : List<String>{
    return transportModes.split(",").map { it.trim() }
  }
}