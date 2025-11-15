package com.tarangini.traiana.components.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.components.theme.Dimens

enum class BadgeVariant {
  Default, Success, Warning, Error, Info, Outline, Dark
}

@Composable
fun AppBadge(
  modifier: Modifier = Modifier,
  text: String? = null,
  iconResId: Int? = null,
  variant: BadgeVariant = BadgeVariant.Default,
  contentColor: Color? = null,
  backgroundColor: Color? = null
) {
  val (bgColor, borderColor, txtColor) = when (variant) {
    BadgeVariant.Success -> Triple(Colors.CoralGreen100.copy(alpha = 0.2f), Colors.CoralGreen200.copy(alpha = 0.2f), Colors.Neutral100)
    BadgeVariant.Warning -> Triple(Colors.CoralAmber100, Colors.CoralAmber200, Colors.Neutral900)
    BadgeVariant.Error -> Triple(Colors.CoralRed100.copy(alpha = 0.2f), Colors.CoralRed200.copy(alpha = 0.2f), Colors.Neutral100)
    BadgeVariant.Info -> Triple(Colors.CoralAmber300.copy(alpha = 0.2f), Colors.CoralAmber100.copy(alpha = 0.2f), Colors.Neutral100)
    BadgeVariant.Dark -> Triple(Colors.Neutral800, Colors.Neutral600, Colors.Neutral100)
    BadgeVariant.Outline -> Triple(Color.Transparent, Colors.Neutral600, Colors.Neutral100)
    BadgeVariant.Default -> Triple(Colors.Neutral700, Colors.Neutral600, Colors.Neutral200)
  }

  Surface(
    modifier = modifier,
    color = backgroundColor ?: bgColor,
    shape = MaterialTheme.shapes.large,
    border = BorderStroke(1.dp, borderColor),
    contentColor = contentColor ?: txtColor
  ) {
    Row(
      modifier = Modifier.padding(horizontal = Dimens.PaddingS, vertical = 1.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
    ) {
      // Icon only
      if (iconResId != null && text.isNullOrEmpty()) {
        Icon(
          painter = painterResource(id = iconResId),
          contentDescription = null,
          modifier = Modifier.size(16.dp),
          tint = txtColor
        )
      }

      // Icon + Text
      if (iconResId != null && !text.isNullOrEmpty()) {
        Icon(
          painter = painterResource(id = iconResId),
          contentDescription = null,
          modifier = Modifier.size(12.dp),
          tint = txtColor
        )
        Spacer(Modifier.width(6.dp))
        Text(
          text = text,
          color = txtColor,
          fontSize = 12.sp,
          maxLines = 1
        )
      }

      // Text only
      if (iconResId == null && !text.isNullOrEmpty()) {
        Text(
          text = text,
          color = txtColor,
          fontSize = 12.sp,
          maxLines = 1
        )
      }
    }
  }
}
