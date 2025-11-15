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
import com.tarangini.traiana.components.ui.RowHorizontalPlacement
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.components.ui.ColumnVerticalPlacement
import com.tarangini.traiana.components.ui.RowVerticalPlacement

@Composable
fun ContactRow(
  modifier: Modifier = Modifier,
  title: String,
  subtitle: String,
  leadingIconRes: Int,
  trailingIconRes: Int? = null,
  onClick: (() -> Unit)? = null,
  onTrailingClick: (() -> Unit)? = null,
  trailingTint : Color = Colors.CoralGreen200.copy(
    alpha = 0.8f
  )
) {
  val clickableModifier = if (onClick != null) {
    modifier
      .fillMaxWidth()
      .clip(MaterialTheme.shapes.medium)
      .clickable { onClick() }
      .padding(horizontal = Dimens.SpaceM, vertical = Dimens.SpaceS)
  } else {
    modifier
      .fillMaxWidth()
      .padding(horizontal = Dimens.SpaceM, vertical = Dimens.SpaceS)
  }

  AppRow(
    modifier = clickableModifier,
    horizontal = RowHorizontalPlacement.SpaceBetween,
    vertical = RowVerticalPlacement.Center
  ) {

    //-- LEFT SECTION -------------------------------------------------------
    AppRow(
      modifier = Modifier.weight(1f),
      horizontal = RowHorizontalPlacement.Start,
      vertical = RowVerticalPlacement.Center
    ) {

      // Leading Icon
      Box(
        modifier = Modifier
          .size(35.dp)
          .clip(MaterialTheme.shapes.large)
          .background(Colors.Neutral500),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          painter = painterResource(id = leadingIconRes),
          contentDescription = null,
          modifier = Modifier.size(Dimens.IconSize)
        )
      }

      Spacer(modifier = Modifier.width(Dimens.SpaceM))

      // Text Column
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
        Text(
          text = subtitle,
          style = MaterialTheme.typography.bodySmall,
        )
      }
    }

    //-- RIGHT SECTION (optional button) -----------------------------------
    if (trailingIconRes != null) {
      Icon(
        painter = painterResource(trailingIconRes),
        contentDescription = "Trailing Icon",
        modifier= Modifier
          .size(Dimens.IconSize - 2.dp)
          .clickable { onTrailingClick?.invoke() },
        tint = trailingTint
      )
    }
  }
}