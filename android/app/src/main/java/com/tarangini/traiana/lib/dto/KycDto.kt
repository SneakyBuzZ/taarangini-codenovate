package com.tarangini.traiana.lib.dto

data class RegisterKycFormDTO(
  val docType: String,
  val docNumber: String,
  val docImage: String,
  val issuedAt: String,
  val address: String,
  val emergencyName: String,
  val emergencyRelation: String,
  val emergencyPhone: String
)

data class RegisterKycApiDTO(
  val docType: String,
  val docNumber: String,
  val docImage: String,
  val issuedAt: String,
  val address: String,
  val emergencyContact: EmergencyContact
)

data class EmergencyContact(
  val name: String,
  val relation: String,
  val phone: String
)

fun RegisterKycFormDTO.toApiDTO(): RegisterKycApiDTO {
  return RegisterKycApiDTO(
    docType = this.docType,
    docNumber = this.docNumber,
    docImage = this.docImage,
    issuedAt = this.issuedAt,
    address = this.address,
    emergencyContact = EmergencyContact(
      name = this.emergencyName,
      relation = this.emergencyRelation,
      phone = this.emergencyPhone
    )
  )
}