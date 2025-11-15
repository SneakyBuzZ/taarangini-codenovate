package com.tarangini.traiana.components.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

@Composable
fun AppRow(
  modifier: Modifier = Modifier,

  horizontal: RowHorizontalPlacement = RowHorizontalPlacement.Center,
  vertical: RowVerticalPlacement = RowVerticalPlacement.Center,

  children: @Composable RowScope.() -> Unit
) {
  val horizontalArrangement = when (horizontal) {
    RowHorizontalPlacement.Start -> Arrangement.Start
    RowHorizontalPlacement.Center -> Arrangement.Center
    RowHorizontalPlacement.End -> Arrangement.End
    RowHorizontalPlacement.SpaceBetween -> Arrangement.SpaceBetween
    RowHorizontalPlacement.SpaceAround -> Arrangement.SpaceAround
    RowHorizontalPlacement.SpaceEvenly -> Arrangement.SpaceEvenly
  }

  val verticalAlignment = when (vertical) {
    RowVerticalPlacement.Top -> Alignment.Top
    RowVerticalPlacement.Center -> Alignment.CenterVertically
    RowVerticalPlacement.Bottom -> Alignment.Bottom
  }

  Row(
    modifier = modifier.clip(MaterialTheme.shapes.medium),
    horizontalArrangement = horizontalArrangement,
    verticalAlignment = verticalAlignment
  ) {
    children()
  }
}

enum class RowHorizontalPlacement {
  Start,
  Center,
  End,
  SpaceBetween,
  SpaceAround,
  SpaceEvenly
}

enum class RowVerticalPlacement {
  Top,
  Center,
  Bottom
}
