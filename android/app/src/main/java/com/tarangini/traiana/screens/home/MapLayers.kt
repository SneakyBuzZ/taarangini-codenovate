package com.tarangini.traiana.screens.home

import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.heatmapLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource


fun addSafeAndUnsafeHeatmaps(style: Style) {

  val safePoints = listOf(
    Point.fromLngLat(78.48995533, 17.39796638),
    Point.fromLngLat(78.3928, 17.4421),
  )
  val unsafePoints = listOf(
    Point.fromLngLat(78.49095533, 17.40796638),
    Point.fromLngLat(78.4947286, 17.3866394),
  )

  // SAFE SOURCE
  style.addSource(
    geoJsonSource("safe-source") {
      featureCollection(
        FeatureCollection.fromFeatures(
          safePoints.map { Feature.fromGeometry(it) }
        )
      )
    }
  )

  // UNSAFE SOURCE
  style.addSource(
    geoJsonSource("unsafe-source") {
      featureCollection(
        FeatureCollection.fromFeatures(
          unsafePoints.map { Feature.fromGeometry(it) }
        )
      )
    }
  )

  // SAFE LAYER (Green Heat Glow)
  style.addLayer(
    heatmapLayer("safe-layer", "safe-source") {
      heatmapColor(
        interpolate {
          linear()
          heatmapDensity()

          stop(0.0) { rgba(0.0, 255.0, 0.0, 0.0) }        // transparent green
          stop(0.3) { rgba(0.0, 255.0, 0.0, 0.6) }     // light green (0.25 alpha)
          stop(0.6) { rgba(0.0, 255.0, 0.0, 0.9) }      // medium green
          stop(1.0) { rgba(0.0, 255.0, 0.0, 0.9) }      // dense green
        }
      )
      heatmapIntensity(1.2)
      heatmapRadius(120.0)
      heatmapOpacity(0.3)
    }
  )

  // UNSAFE LAYER (Red Heat Glow)
  style.addLayer(
    heatmapLayer("unsafe-layer", "unsafe-source") {
      heatmapColor(
        interpolate {
          linear()
          heatmapDensity()

          stop(0.0) { rgba(255.0, 0.0, 0.0, 0.0) }        // transparent red
          stop(0.3) { rgba(255.0, 100.0, 0.0, 0.6) }    // light red
          stop(0.6) { rgba(255.0, 50.0, 0.0, 0.9) }     // medium red
          stop(1.0) { rgba(255.0, 0.0, 0.0, 0.9) }     // dense red
        }
      )


      heatmapIntensity(1.5)
      heatmapRadius(100.0)
      heatmapOpacity(0.3)
    }
  )
}
