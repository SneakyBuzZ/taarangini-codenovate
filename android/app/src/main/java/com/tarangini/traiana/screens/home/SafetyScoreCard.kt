package com.tarangini.traiana.screens.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.components.ui.AppColumn
import com.tarangini.traiana.components.ui.ColumnLayout

@Composable
fun SafetyScoreCard(
  modifier: Modifier = Modifier,
  score: Int,
  lastUpdated: String
) {
  Column(
    modifier = modifier
      .fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceEvenly
  ) {

    val labelText = when(score){
      in 0..24 -> "Unsafe"
      in 25..49 -> "Low"
      in 50..74 -> "Moderate"
      in 75..99 -> "Good"
      else -> "Safe"
    }

    val labelSubText = when(score){
      in 0..39 -> "Please contact authorities immediately"
      in 40..69 -> "Please go to the nearest safe place"
      in 70..89 -> "Enjoy your trip"
      in 90..99 -> "Enjoy your trip, you at not risk"
      else -> "No comment"
    }

    AppColumn (
      modifier = Modifier
        .fillMaxWidth(),
      layout = ColumnLayout.Center
    ){
      Text(
        text = labelText,
        style = MaterialTheme.typography.headlineMedium.copy(
          fontSize = 22.sp
        ),
      )
      Text(
        text = labelSubText,
        style = MaterialTheme.typography.bodySmall.copy(
          fontSize = 12.sp
        ),
      )
    }

    Box(
      modifier = Modifier
        .height(50.dp)
        .width(100.dp),
      contentAlignment = Alignment.Center
    ){
      ScoreGauge(
        score = score,
        offsetY = 50.dp
      )
      Text(
        text = score.toString(),
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier
          .offset(y=(20).dp)
      )
    }

    // Last updated text
    Text(
      text = "Last updated: $lastUpdated",
      style = MaterialTheme.typography.bodySmall,
      color = Color.Gray
    )
  }
}

@Composable
fun ScoreGauge(
  score : Int,
  offsetY: Dp
){
  Canvas(
    modifier = Modifier
      .fillMaxSize()
      .offset(y=offsetY)
  ) {
    val strokeWidth = 8.dp.toPx()
    val arcRect = Size(size.width, size.height * 2)
    val startAngle = 180f
    val sweepAngle = 180f

    // Background arc (gray)
    drawArc(
      color = Colors.Neutral600,
      startAngle = startAngle,
      sweepAngle = sweepAngle,
      useCenter = false,
      style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
      size = arcRect,
      topLeft = Offset(0f, -size.height)
    )

    // Progress arc (colored)
    val progressSweep = (score / 100f) * 180f
    drawArc(
      color = if (score < 40) Colors.CoralRed200 else if (score < 70) Colors.CoralAmber100 else Colors.CoralGreen100,
      startAngle = startAngle,
      sweepAngle = progressSweep,
      useCenter = false,
      style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
      size = arcRect,
      topLeft = Offset(0f, -size.height)
    )

//      // Labels at 0,45,90,135,180 degrees
//      val radius = size.width / 2
//      val angles = listOf(180f, 135f, 90f, 45f, 0f)
//      val labels = listOf("Unsafe", "Low", "Moderate", "Good", "Safe")
//
//      angles.forEachIndexed { i, angle ->
//        val rad = Math.toRadians(angle.toDouble())
//        val x = ((radius + 90) * cos(rad)).toFloat() + radius
//        val y = (-(radius + 40) * sin(rad)).toFloat()
//        drawContext.canvas.nativeCanvas.drawText(
//          labels[i],
//          x,
//          y,
//          android.graphics.Paint().apply {
//            color = android.graphics.Color.GRAY
//            textSize = 32f
//            textAlign = Paint.Align.CENTER
//          }
//        )
//      }
  }
}
