package com.tarangini.traiana.lib.api.itinerary

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.tarangini.traiana.lib.dto.LocationDTO

data class States(
  val arrivalDate: String = "",
  val departureDate: String = "",
  val locations: List<LocationDTO> = emptyList(),
  val transportMode: List<String> = emptyList(),
  val registerRequest : Async<String> = Uninitialized
) : MavericksState
