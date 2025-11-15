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
  layout: RowLayout = RowLayout.Start,
  modifier: Modifier = Modifier,
  verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
  children: @Composable RowScope.() -> Unit
) {
  val arrangement = when (layout) {
    RowLayout.Start -> Arrangement.Start
    RowLayout.Center -> Arrangement.Center
    RowLayout.End -> Arrangement.End
    RowLayout.SpaceBetween -> Arrangement.SpaceBetween
    RowLayout.SpaceAround -> Arrangement.SpaceAround
    RowLayout.SpaceEvenly -> Arrangement.SpaceEvenly
  }

  Row(
    modifier = modifier
      .clip(MaterialTheme.shapes.medium),
    horizontalArrangement = arrangement,
    verticalAlignment = verticalAlignment
  ) {
    children()
  }
}

enum class RowLayout {
  Start,
  Center,
  End,
  SpaceBetween,
  SpaceAround,
  SpaceEvenly
}
