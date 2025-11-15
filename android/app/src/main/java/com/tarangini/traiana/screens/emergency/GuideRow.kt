package com.tarangini.traiana.screens.emergency

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarangini.traiana.components.theme.Dimens
import com.tarangini.traiana.components.ui.AppColumn
import com.tarangini.traiana.components.ui.AppRow
import com.tarangini.traiana.components.ui.ColumnHorizontalPlacement
import com.tarangini.traiana.components.ui.ColumnVerticalPlacement
import com.tarangini.traiana.components.ui.RowHorizontalPlacement
import com.tarangini.traiana.components.ui.RowVerticalPlacement
import com.tarangini.traiana.components.theme.Colors


@Composable
fun GuideRow(
  modifier: Modifier = Modifier,
  title: String,
  description: String? = null,
  leadingIconRes: Int,
  onClick: () -> Unit = {},
  leftIconTint : Color = Colors.CoralBlue100.copy(alpha = 0.2f)
) {

  AppRow(
    modifier = modifier
      .fillMaxWidth()
      .clip(MaterialTheme.shapes.medium)
      .background(Colors.Neutral700.copy(alpha = 0.06f))
      .clickable { onClick() }
      .padding(horizontal = Dimens.SpaceM, vertical = Dimens.SpaceS),
    horizontal = RowHorizontalPlacement.Start,
    vertical = RowVerticalPlacement.Center
  ) {

    //-- Left Icon --------------------------------------------------------
    Box(
      modifier = Modifier
        .size(36.dp)
        .clip(MaterialTheme.shapes.medium)
        .background(leftIconTint),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        painter = painterResource(id = leadingIconRes),
        contentDescription = null,
        modifier = Modifier.size(Dimens.IconSize - 4.dp),
        tint = Colors.Neutral200
      )
    }

    Spacer(modifier = Modifier.width(Dimens.SpaceM))

    //-- Title + optional description ------------------------------------
    AppColumn(
      horizontal = ColumnHorizontalPlacement.Start,
      vertical = ColumnVerticalPlacement.Center
    ) {
      Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall.copy(
          fontSize = 15.sp
        )
      )

      // show description only if provided
      if (!description.isNullOrBlank()) {
        Text(
          text = description,
          style = MaterialTheme.typography.bodySmall,
        )
      }
    }
  }
}
