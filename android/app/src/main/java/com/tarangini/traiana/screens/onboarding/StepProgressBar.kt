package com.tarangini.traiana.screens.onboarding

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.components.theme.Dimens

@Composable
fun StepProgressBar(
  steps: List<String>,
  currentStep: Int,
  completedSteps: List<Boolean>,
  modifier: Modifier = Modifier
) {
  val activeColor = MaterialTheme.colorScheme.tertiary
  val inactiveColor = Colors.Neutral400
  val completedColor = Colors.CoralBlue100

  Column(
    modifier = modifier
      .padding(horizontal = Dimens.PaddingL),
    verticalArrangement = Arrangement.Center,
  ) {
    Canvas(
      modifier = Modifier
        .fillMaxWidth()
        .height(Dimens.HeightXS)
        .clipToBounds()
    ) {
      val stepCount = steps.size
      val availableWidth = size.width - (2 * Dimens.RadiusL.toPx())
      val spacing = if (stepCount > 1) availableWidth / (stepCount - 1) else 0f
      val startX = Dimens.RadiusL.toPx()
      val centerY = size.height / 2

      steps.forEachIndexed { index, _ ->
        val x = startX + (index * spacing)

        if (index < stepCount - 1) {
          val nextX = startX + ((index + 1) * spacing)
          val lineColor = when {
            completedSteps.getOrNull(index) == true -> completedColor
            index == currentStep - 1 -> activeColor
            else -> Colors.Neutral700
          }
          drawLine(
            color = lineColor,
            start = Offset(x + Dimens.RadiusM.toPx(), centerY),
            end = Offset(nextX - Dimens.RadiusM.toPx(), centerY),
            strokeWidth = 4.dp.toPx(),
            cap = StrokeCap.Round
          )
        }

        val isCompleted = completedSteps.getOrNull(index) == true
        val circleColor = when {
          isCompleted -> completedColor
          index == currentStep -> activeColor
          else -> Colors.Neutral500
        }

        drawCircle(
          color = circleColor,
          radius = Dimens.RadiusL.toPx() - 10,
          center = Offset(x, centerY)
        )

        if (isCompleted) {
          val checkmarkSize = (Dimens.RadiusS.toPx() - 1 )* 0.8f
          val left = x - checkmarkSize * 1f
          val top = centerY - checkmarkSize * 0.5f
          val right = x + checkmarkSize * 1.3f
          val bottom = centerY + checkmarkSize * 0.9f

          val path = Path().apply {
            moveTo(left, centerY)
            lineTo(x, bottom)
            lineTo(right, top)
          }
          drawPath(
            path = path,
            color = Color.White,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
          )
        }
      }
    }

    BoxWithConstraints(
      modifier = Modifier.fillMaxWidth()
    ) {
      val stepCount = steps.size
      val availableWidth = maxWidth - (2 * Dimens.RadiusL)
      val spacing = if (stepCount > 1) availableWidth / (stepCount - 1) else 0.dp
      val startX = Dimens.RadiusL

      steps.forEachIndexed { index, step ->
        val textX = startX + (index * spacing)

        val showCompleted = completedSteps.getOrNull(index) == true

        Text(
          text = step,
          modifier = Modifier
            .offset(x = textX - 40.dp)
            .width(80.dp),
          style = MaterialTheme.typography.bodySmall.copy(
            color = when {
              showCompleted -> activeColor
              index == currentStep -> activeColor
              else -> inactiveColor
            },
            fontSize = 14.sp,
            textAlign = TextAlign.Center
          )
        )
      }
    }
  }
}
