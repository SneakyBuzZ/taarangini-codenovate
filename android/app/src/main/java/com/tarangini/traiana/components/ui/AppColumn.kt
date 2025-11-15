package com.tarangini.traiana.components.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AppColumn(
  modifier: Modifier = Modifier,

  horizontal: ColumnHorizontalPlacement = ColumnHorizontalPlacement.Center,
  vertical: ColumnVerticalPlacement = ColumnVerticalPlacement.Center,

  children: @Composable ColumnScope.() -> Unit
) {
  val horizontalAlignment = when (horizontal) {
    ColumnHorizontalPlacement.Start -> Alignment.Start
    ColumnHorizontalPlacement.Center -> Alignment.CenterHorizontally
    ColumnHorizontalPlacement.End -> Alignment.End
  }

  val verticalArrangement = when (vertical) {
    ColumnVerticalPlacement.Top -> Arrangement.Top
    ColumnVerticalPlacement.Center -> Arrangement.Center
    ColumnVerticalPlacement.Bottom -> Arrangement.Bottom
    ColumnVerticalPlacement.SpaceBetween -> Arrangement.SpaceBetween
    ColumnVerticalPlacement.SpaceAround -> Arrangement.SpaceAround
    ColumnVerticalPlacement.SpaceEvenly -> Arrangement.SpaceEvenly
  }

  Column(
    modifier = modifier,
    horizontalAlignment = horizontalAlignment,
    verticalArrangement = verticalArrangement
  ) {
    children()
  }
}

enum class ColumnHorizontalPlacement {
  Start,
  Center,
  End
}

enum class ColumnVerticalPlacement {
  Top,
  Center,
  Bottom,
  SpaceBetween,
  SpaceAround,
  SpaceEvenly
}
