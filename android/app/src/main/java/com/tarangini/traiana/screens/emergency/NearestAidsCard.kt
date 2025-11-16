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
fun NearestAidsCard() {
  val context = LocalContext.current

  AppColumn(
    modifier = Modifier
      .fillMaxWidth()
      .clip(MaterialTheme.shapes.medium)
      .background(MaterialTheme.colorScheme.surface)
      .padding(Dimens.PaddingXS),
    horizontal = ColumnHorizontalPlacement.Start
  ) {
    aids.forEachIndexed { index, aid ->
      ContactRow(
        title = aid.name,
        subtitle = aid.distance,
        leadingIconRes = aid.iconRes,
        trailingIconRes = R.drawable.ic_call,
        onClick = { /* entire row clicked */ },
        onTrailingClick = {
          // Launch phone dialer with number
          val intent = Intent(Intent.ACTION_DIAL).apply {
            data = "tel:${aid.phone}".toUri()
          }
          context.startActivity(intent)
        }
      )

      // Add divider if it's not the last item
      if (index < aids.lastIndex) {
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

// Helper data class
data class Aid(
  val name: String,
  val distance: String,
  val phone: String,
  val iconRes: Int
)

// List of nearby medical/aid centers with phone numbers
val aids = listOf(
  Aid("PVR Narayanguda", "2 min away", "08800900009", R.drawable.ic_wallet),
  Aid("Inox Maheshwari", "3 min away", "09100998002", R.drawable.ic_wallet),
  Aid("Cafe Niloufer Himayat Nagar", "4 min away", "07207918522", R.drawable.ic_home)
)