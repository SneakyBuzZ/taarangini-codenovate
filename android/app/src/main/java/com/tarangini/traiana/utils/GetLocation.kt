package com.tarangini.traiana.utils

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log


@Suppress("MissingPermission")
fun GetLocation(context: Context, onResult: (Double, Double) -> Unit) {
  val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

  val providers = listOf(
    LocationManager.GPS_PROVIDER,
    LocationManager.NETWORK_PROVIDER
  )

  // Try cached locations first
  for (p in providers) {
    val cached = locationManager.getLastKnownLocation(p)
    if (cached != null) {
      Log.d("TRIANA", "Cached from $p: ${cached.latitude}, ${cached.longitude}")
      onResult(cached.latitude, cached.longitude)
      return
    }
  }

  // If no cached location, request a fresh one
  providers.forEach { provider ->
    locationManager.requestLocationUpdates(
      provider,
      1000L,
      1f,
      object : LocationListener {
        override fun onLocationChanged(loc: Location) {
          Log.d("TRIANA", "Fresh fix from $provider: ${loc.latitude}, ${loc.longitude}")
          onResult(loc.latitude, loc.longitude)
          locationManager.removeUpdates(this)
        }
      }
    )
  }
}
