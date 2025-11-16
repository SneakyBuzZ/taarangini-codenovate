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
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri

@Composable
fun PoliceStationsCard() {
  val context = LocalContext.current
  AppColumn(
    modifier = Modifier
      .fillMaxWidth()
      .clip(MaterialTheme.shapes.medium)
      .background(MaterialTheme.colorScheme.surface)
      .padding(Dimens.PaddingXS),
    horizontal = ColumnHorizontalPlacement.Start
  ) {
    stations.forEachIndexed { index, station ->
      ContactRow(
        title = station.name,
        subtitle = station.distance,
        leadingIconRes = R.drawable.ic_police,
        trailingIconRes = R.drawable.ic_call,
        onClick = { /* entire row clicked */ },
        onTrailingClick = {
          // Launch phone dialer with number
          val intent = Intent(Intent.ACTION_DIAL).apply {
            data = "tel:${station.phone}".toUri()
          }
          context.startActivity(intent)
        }
      )

      // Add divider if it's not the last item
      if (index < stations.lastIndex) {
        AppDivider(
          orientation = DividerOrientation.Horizontal,
          style = DividerStyle.Dotted,
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.SpaceS)
        )
      }
    }
  }
}

// Helper data class with phone number
data class Station(
  val name: String,
  val distance: String,
  val phone: String
)

val stations = listOf(
  Station("Narayanguda Traffic Police Station", "3 min away", "040-27852351"),
  Station("Sultan Bazar Police Station", "4 min away", "040-27854778"),
  Station("King Koti Police Station", "10 min away", "040-27854770"),
  Station("Narayanguda Police Station", "10 min away", "040-27852579")
)

