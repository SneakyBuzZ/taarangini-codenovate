package com.tarangini.traiana.lib.dto

data class LocationDTO(
  val city: String,
  val stay: String,
  val fromDate: String,
  val toDate: String
)

data class RegisterItineraryDTO(
  val arrivalDate: String,
  val departureDate: String,
  val locations: List<LocationDTO>,
  val transportMode: List<String>
)

data class RegisterItineraryFormDTO(
  val arrivalDate: String,
  val departureDate: String,
  val transportMode: String = ""
)