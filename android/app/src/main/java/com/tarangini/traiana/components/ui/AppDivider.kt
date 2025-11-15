package com.tarangini.traiana.components.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.components.theme.Dimens

enum class DividerOrientation {
  Horizontal, Vertical
}

enum class DividerStyle {
  Solid, Dotted, Dashed
}

@Composable
fun AppDivider(
  modifier: Modifier = Modifier,
  orientation: DividerOrientation = DividerOrientation.Vertical,
  style: DividerStyle = DividerStyle.Solid, // new parameter
  color: Color = Colors.Neutral600,
  thickness: Dp = 0.8.dp,
  length: Dp = 20.dp,
  dotSize: Dp = 2.dp,
  dashSize: Dp = 8.dp,
  gap: Dp = 3.dp
) {
  when (style) {

    DividerStyle.Solid -> {
      // Keep old behavior unchanged
      Box(
        modifier = when (orientation) {
          DividerOrientation.Horizontal ->
            modifier
              .padding(horizontal = Dimens.SpaceS)
              .height(thickness)
              .fillMaxWidth()
              .background(color)

          DividerOrientation.Vertical ->
            modifier
              .padding(horizontal = Dimens.SpaceS)
              .width(thickness)
              .fillMaxHeight()
              .background(color)
        }
      )
    }

    DividerStyle.Dotted -> {
      Canvas(
        modifier = if (orientation == DividerOrientation.Horizontal)
          modifier
            .padding(horizontal = Dimens.SpaceS)
            .height(thickness)
            .width(length)
        else
          modifier
            .padding(horizontal = Dimens.SpaceS)
            .width(thickness)
            .height(length)
      ) {
        val dot = dotSize.toPx()
        val gapPx = gap.toPx()

        if (orientation == DividerOrientation.Horizontal) {
          var x = 0f
          while (x < size.width) {
            drawCircle(color, radius = dot / 2, center = androidx.compose.ui.geometry.Offset(x + dot / 2, size.height / 2))
            x += dot + gapPx
          }
        } else {
          var y = 0f
          while (y < size.height) {
            drawCircle(color, radius = dot / 2, center = androidx.compose.ui.geometry.Offset(size.width / 2, y + dot / 2))
            y += dot + gapPx
          }
        }
      }
    }

    DividerStyle.Dashed -> {
      Canvas(
        modifier = if (orientation == DividerOrientation.Horizontal)
          modifier
            .padding(horizontal = Dimens.SpaceS)
            .height(thickness)
            .width(length)
        else
          modifier
            .padding(horizontal = Dimens.SpaceS)
            .width(thickness)
            .height(length)
      ) {
        val dash = dashSize.toPx()
        val gapPx = gap.toPx()

        if (orientation == DividerOrientation.Horizontal) {
          var x = 0f
          while (x < size.width) {
            drawRect(color, size = androidx.compose.ui.geometry.Size(dash, thickness.toPx()), topLeft = androidx.compose.ui.geometry.Offset(x, 0f))
            x += dash + gapPx
          }
        } else {
          var y = 0f
          while (y < size.height) {
            drawRect(color, size = androidx.compose.ui.geometry.Size(thickness.toPx(), dash), topLeft = androidx.compose.ui.geometry.Offset(0f, y))
            y += dash + gapPx
          }
        }
      }
    }
  }
}
