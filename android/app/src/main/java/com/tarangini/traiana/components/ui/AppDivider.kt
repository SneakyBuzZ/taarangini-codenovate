package com.tarangini.traiana.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.components.theme.Dimens

enum class DividerOrientation {
  Horizontal, Vertical
}

@Composable
fun AppDivider(
  modifier: Modifier = Modifier,
  orientation: DividerOrientation = DividerOrientation.Vertical,
  color: Color = Colors.Neutral600,
  thickness: Dp = 0.8.dp,
  length: Dp = 20.dp,
) {
  val shape = MaterialTheme.shapes.small

  Box(
    modifier = when (orientation) {
      DividerOrientation.Horizontal ->
        modifier
          .padding(horizontal = Dimens.SpaceS)
          .height(thickness)
          .width(length)
          .background(color, shape)

      DividerOrientation.Vertical ->
        modifier
          .padding(horizontal = Dimens.SpaceS)
          .width(thickness)
          .height(length)
          .background(color, shape)
    }
  )
}
