package com.tarangini.traiana.utils

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log


@Suppress("MissingPermission")
fun GetLocation(context: Context, onResult: (Double, Double) -> Unit){
  val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
  val provider = LocationManager.GPS_PROVIDER

  val location = locationManager.getLastKnownLocation(provider)
  if (location != null) {
    Log.d("TRIANA", "Longitude: ${location.longitude}")
    Log.d("TRIANA", "Latitude: ${location.latitude}")
    onResult(location.latitude, location.longitude)
  }else{
    locationManager.requestLocationUpdates(
      provider,
      1000L,
      1f,
      object : LocationListener {
        override fun onLocationChanged(loc: Location) {
          Log.d("TRIANA", "Location was null then from inside: $loc")
          onResult(loc.latitude, loc.longitude)
          locationManager.removeUpdates(this)
        }
      }
    )
  }
}