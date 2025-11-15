package com.tarangini.traiana.screens.alerts

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.tarangini.traiana.R
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.components.theme.Dimens
import com.tarangini.traiana.components.ui.*

data class AlertItem(
  val title: String,
  val description: String,
  val date: String,
  val category: String,
  val severity: String,
  val severityVariant: BadgeVariant,
  val imageUrl: String
)

@Composable
fun AlertCard(
  alert: AlertItem,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(MaterialTheme.shapes.medium)
      .border(1.dp, Colors.Neutral700, MaterialTheme.shapes.medium)
      .background(MaterialTheme.colorScheme.surface)
      .padding(6.dp)
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(Dimens.HeightM + 15.dp)
    ) {
      AppImage(
        url = alert.imageUrl,
        modifier = Modifier
          .fillMaxSize()
          .clip(MaterialTheme.shapes.small)
          .zIndex(2f)
      )
      Box(
        modifier = Modifier
          .fillMaxSize()
          .clip(MaterialTheme.shapes.small)
          .background(
            Brush.verticalGradient(
              colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
              startY = 0f,
              endY = Float.POSITIVE_INFINITY
            )
          )
          .zIndex(3f)
      )
    }

    AppColumn(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = Dimens.PaddingXS, horizontal = Dimens.PaddingXXS)
    ){
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = Dimens.PaddingXXS)
      ) {
        Text(
          alert.title,
          style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(Dimens.PaddingXXS))
        Text(
          alert.description,
          style = MaterialTheme.typography.bodySmall,
          color = Colors.Neutral300
        )
      }

      Spacer(modifier = Modifier.height(Dimens.PaddingXS))

      AppRow(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = Dimens.PaddingXXS),
        horizontal = RowHorizontalPlacement.Start
      ) {
        AppBadge(
          text = alert.date,
          iconResId = R.drawable.ic_calendar
        )

        AppDivider(
          orientation = DividerOrientation.Vertical,
          modifier = Modifier
            .height(18.dp)
        )

        AppBadge(
          text = alert.category,
          iconResId = R.drawable.ic_tag,
        )

        AppDivider(
          orientation = DividerOrientation.Vertical,
          modifier = Modifier
            .height(18.dp)
        )

        AppBadge(
          text = alert.severity,
          iconResId = R.drawable.ic_alert,
          variant = alert.severityVariant
        )
      }
    }
  }
}
