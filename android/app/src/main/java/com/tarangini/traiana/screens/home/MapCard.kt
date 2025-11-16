package com.tarangini.traiana.screens.home

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.DisposableMapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlin.math.abs
import java.lang.Math.toDegrees

@OptIn(MapboxExperimental::class, FlowPreview::class)
@Composable
fun MapCard(
  modifier: Modifier = Modifier,
  hasPermission: Boolean,
  styleUri: String = "mapbox://styles/sneakybuzz/cmf9wympa002k01s398occe4w",
  defaultPitch: Double = 45.0
) {
  val context = LocalContext.current
  val viewportState = rememberMapViewportState {
    setCameraOptions {
      zoom(20.0)
      pitch(defaultPitch)
    }
  }

  var headingDeg by remember { mutableDoubleStateOf(0.0) }
  var pitch by remember { mutableDoubleStateOf(defaultPitch) }
  var isUserInteracting by remember { mutableStateOf(false) }
  var lastBearing by remember { mutableDoubleStateOf(0.0) }
  var sensorActive by remember { mutableStateOf(true) }
  val sensorCooldownMs = 800L
  val headingThreshold = 2.0
  var sensorCooldownKey by remember { mutableIntStateOf(0) }

  val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }

  DisposableEffect(sensorManager, sensorActive) {
    val rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    val listener = object : SensorEventListener {
      override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR && sensorActive) {
          val r = FloatArray(9)
          SensorManager.getRotationMatrixFromVector(r, event.values)
          val o = FloatArray(3)
          SensorManager.getOrientation(r, o)
          val azimuthRad = o[0]
          val newHeading = ((toDegrees(azimuthRad.toDouble()) + 360.0) % 360.0)

          if (abs(newHeading - lastBearing) > headingThreshold) {
            headingDeg = newHeading
            lastBearing = newHeading
          }
        }
      }

      override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }
    if (rotationSensor != null && sensorActive) {
      sensorManager.registerListener(listener, rotationSensor, SensorManager.SENSOR_DELAY_UI)
      onDispose {
        sensorManager.unregisterListener(listener)
      }
    } else {
      onDispose { }
    }
  }

  LaunchedEffect(headingDeg, isUserInteracting, pitch) {
    if (!isUserInteracting && sensorActive) {
      viewportState.easeTo(
        cameraOptions = CameraOptions.Builder()
          .bearing(headingDeg)
          .pitch(pitch)
          .padding(EdgeInsets(100.0, 0.0, 0.0, 0.0))
          .build(),
        animationOptions = MapAnimationOptions.mapAnimationOptions {
          duration(300)
        }
      )
    }
  }

  LaunchedEffect(sensorCooldownKey) {
    if (!isUserInteracting) {
      delay(sensorCooldownMs)
      sensorActive = true
    }
  }

  MapboxMap(
    modifier = modifier,
    mapViewportState = viewportState
  ) {
    DisposableMapEffect(hasPermission) { mapView ->
      val gestures = mapView.gestures
      val locationComponent = mapView.location
      val mapboxMap = mapView.getMapboxMap()

      val moveListener = object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
          isUserInteracting = true
          sensorActive = false
        }
        override fun onMove(detector: MoveGestureDetector): Boolean {
          isUserInteracting = true
          sensorActive = false
          return false
        }
        override fun onMoveEnd(detector: MoveGestureDetector) {
          isUserInteracting = false
          sensorCooldownKey++
          pitch = mapboxMap.cameraState.pitch.coerceIn(0.0, 60.0)
        }
      }
      gestures.addOnMoveListener(moveListener)

      if (hasPermission) {
        locationComponent.updateSettings {
          enabled = true
          locationPuck = createDefault2DPuck(withBearing = true)
          puckBearing = PuckBearing.HEADING
          puckBearingEnabled = true
        }
        mapboxMap.loadStyleUri(styleUri) { style ->
          addSafeAndUnsafeHeatmaps(style)
        }

        var movedOnce = false
        val listener = OnIndicatorPositionChangedListener { point ->
          if (!movedOnce) {
            mapboxMap.setCamera(
              CameraOptions.Builder()
                .zoom(15.0)
                .center(point)
                .pitch(pitch)
                .build()
            )
            movedOnce = true
          }
        }
        locationComponent.addOnIndicatorPositionChangedListener(listener)

        onDispose {
          locationComponent.removeOnIndicatorPositionChangedListener(listener)
          gestures.removeOnMoveListener(moveListener)
        }
      } else {
        onDispose {
          gestures.removeOnMoveListener(moveListener)
        }
      }
    }
  }
}
