package com.tarangini.traiana.screens.emergency

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.tarangini.traiana.R
import com.tarangini.traiana.components.theme.Dimens
import com.tarangini.traiana.components.ui.AppColumn
import com.tarangini.traiana.components.ui.AppDivider
import com.tarangini.traiana.components.ui.ColumnHorizontalPlacement
import com.tarangini.traiana.components.ui.DividerOrientation
import com.tarangini.traiana.components.ui.DividerStyle

@Composable
fun PoliceStationsCard(){
  AppColumn(
    modifier = Modifier
      .fillMaxWidth()
      .clip(MaterialTheme.shapes.medium)
      .background(MaterialTheme.colorScheme.surface)
      .padding(Dimens.PaddingXS),
    horizontal = ColumnHorizontalPlacement.Start
  ) {
    ContactRow(
      title = "Punjagutta Police Station",
      subtitle = "20 min away",
      leadingIconRes = R.drawable.ic_police,
      trailingIconRes = R.drawable.ic_call,
      onClick = { /* entire row clicked */ },
      onTrailingClick = { /* trailing icon clicked */ }
    )
    AppDivider(
      orientation = DividerOrientation.Horizontal,
      style = DividerStyle.Dotted,
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = Dimens.SpaceS)
    )
    ContactRow(
      title = "Lakdikapul Police Station",
      subtitle = "15 min away",
      leadingIconRes = R.drawable.ic_police,
      trailingIconRes = R.drawable.ic_call,
      onClick = { /* entire row clicked */ },
      onTrailingClick = { /* trailing icon clicked */ }
    )
    AppDivider(
      orientation = DividerOrientation.Horizontal,
      style = DividerStyle.Dotted,
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = Dimens.SpaceS)
    )
    ContactRow(
      title = "Koti Police Station",
      subtitle = "10 min away",
      leadingIconRes = R.drawable.ic_police,
      trailingIconRes = R.drawable.ic_call,
      onClick = { /* entire row clicked */ },
      onTrailingClick = { /* trailing icon clicked */ }
    )
  }
}