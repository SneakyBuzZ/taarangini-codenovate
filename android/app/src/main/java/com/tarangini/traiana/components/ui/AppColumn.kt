package com.tarangini.traiana.components.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AppColumn(
  layout: ColumnLayout = ColumnLayout.Top,
  modifier: Modifier = Modifier,
  horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
  children: @Composable ColumnScope.() -> Unit
) {
  val arrangement = when (layout) {
    ColumnLayout.Top -> Arrangement.Top
    ColumnLayout.Center -> Arrangement.Center
    ColumnLayout.Bottom -> Arrangement.Bottom
    ColumnLayout.SpaceBetween -> Arrangement.SpaceBetween
    ColumnLayout.SpaceAround -> Arrangement.SpaceAround
    ColumnLayout.SpaceEvenly -> Arrangement.SpaceEvenly
  }

  Column(
    modifier = modifier,
    verticalArrangement = arrangement,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    children()
  }
}

enum class ColumnLayout {
  Top,
  Center,
  Bottom,
  SpaceBetween,
  SpaceAround,
  SpaceEvenly
}
