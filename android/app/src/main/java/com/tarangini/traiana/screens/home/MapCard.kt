package com.tarangini.traiana.screens.home

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.DisposableMapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import java.lang.Math.toDegrees

@OptIn(MapboxExperimental::class)
@Composable
fun MapCard(
  hasPermission: Boolean,
  styleUri: String = "mapbox://styles/sneakybuzz/cmf9wympa002k01s398occe4w",
  modifier: Modifier = Modifier
    .fillMaxWidth()
    .height(250.dp)
    .clip(MaterialTheme.shapes.medium)
    .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
) {
  val context = LocalContext.current
  val viewportState = rememberMapViewportState {
    setCameraOptions { zoom(17.0) }
  }
  var headingDeg by remember { mutableStateOf(0.0) }

  // --- Sensor setup for compass ---
  val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
  DisposableEffect(sensorManager) {
    val rot = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    if (rot != null) {
      val listener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
          if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
            val r = FloatArray(9)
            SensorManager.getRotationMatrixFromVector(r, event.values)
            val o = FloatArray(3)
            SensorManager.getOrientation(r, o)
            val azimuthRad = o[0]
            headingDeg = ((toDegrees(azimuthRad.toDouble()) + 360.0) % 360.0)
          }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
      }
      sensorManager.registerListener(listener, rot, SensorManager.SENSOR_DELAY_FASTEST)
      onDispose { sensorManager.unregisterListener(listener) }
    } else {
      onDispose { }
    }
  }

  // --- Rotate map with heading dynamically ---
  LaunchedEffect(headingDeg) {
    viewportState.setCameraOptions(
      CameraOptions.Builder()
        .bearing(headingDeg)
        .padding(EdgeInsets(100.0, 0.0, 0.0, 0.0))
        .pitch(45.0)
        .build(),
    )
  }

  MapboxMap(
    modifier = modifier,
    mapViewportState = viewportState
  ) {
    DisposableMapEffect(hasPermission) { mapView ->
      val locationComponent = mapView.location
      if (hasPermission) {
        locationComponent.updateSettings {
          enabled = true
          locationPuck = createDefault2DPuck(withBearing = true)
          puckBearing = PuckBearing.HEADING
          puckBearingEnabled = true
        }
        mapView.getMapboxMap().loadStyleUri(styleUri)

        var movedOnce = false
        val listener = OnIndicatorPositionChangedListener { point ->
          if (!movedOnce) {
            // Move camera ONLY once to initial location, don't force pitch/zoom after
            mapView.getMapboxMap().setCamera(
              CameraOptions.Builder()
                .zoom(15.0)
                .center(point)
                .pitch(45.0)
                .build()
            )
            movedOnce = true
          }
        }
        locationComponent.removeOnIndicatorPositionChangedListener(listener)
        locationComponent.addOnIndicatorPositionChangedListener(listener)
        onDispose { locationComponent.removeOnIndicatorPositionChangedListener(listener) }
      } else {
        onDispose { }
      }
    }
  }
}
