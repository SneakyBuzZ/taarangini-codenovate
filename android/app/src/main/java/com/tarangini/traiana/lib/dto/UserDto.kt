package com.tarangini.traiana.lib.dto

data class RegisterUserDTO(
  val fullname: String,
  val gender: String,
  val dob: String,
  val email: String,
  val mobile: String,
  val bloodType: String,
  val nationality: String
)