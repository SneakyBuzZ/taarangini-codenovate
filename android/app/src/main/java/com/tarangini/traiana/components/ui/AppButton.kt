package com.tarangini.traiana.components.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.components.theme.Dimens

enum class ButtonVariant {
  Default, Primary, Secondary, Outline, Bright, Dark , Destructive
}

@Composable
fun AppButton(
  modifier: Modifier = Modifier,
  text: String? = null,
  iconResId: Int? = null,
  onClick: () -> Unit,
  variant: ButtonVariant = ButtonVariant.Default,
  enabled: Boolean = true
) {
  val (bgColor, borderColor, textColor) = when (variant) {
    ButtonVariant.Primary -> Triple(Colors.CoralBlue100, Colors.CoralBlue200, Colors.Neutral100)
    ButtonVariant.Secondary -> Triple(Colors.CoralGreen100, Colors.CoralGreen200, Colors.Neutral100)
    ButtonVariant.Default -> Triple(Colors.Neutral500, Colors.Neutral400, Colors.Neutral300)
    ButtonVariant.Dark -> Triple(Colors.Neutral800, Colors.Neutral700, Colors.Neutral200)
    ButtonVariant.Destructive -> Triple(Colors.CoralRed100, Colors.CoralRed200, Colors.Neutral100)
    ButtonVariant.Outline -> Triple(Color.Transparent, Colors.Neutral700, Colors.Neutral100)
    ButtonVariant.Bright -> Triple(Colors.Neutral400, Colors.Neutral300, Colors.Neutral500)
  }

  val finalModifier = if (iconResId != null && (text.isNullOrEmpty())) {
    modifier.size(Dimens.HeightXXS)
  } else {
    modifier
      .height(Dimens.HeightXXS)
      .fillMaxWidth()
  }

  Button(
    onClick = onClick,
    modifier = finalModifier,
    contentPadding = PaddingValues(14.dp),
    enabled = enabled,
    shape = MaterialTheme.shapes.medium,
    colors = ButtonDefaults.buttonColors(
      containerColor = bgColor,
      disabledContainerColor = bgColor.copy(alpha = 0.5f),
      contentColor = textColor,
      disabledContentColor = textColor.copy(alpha = 0.5f),
    ),
    border = BorderStroke(1.dp, borderColor)
  ) {
    when {
      // Icon only
      iconResId != null && text.isNullOrEmpty() -> {
        Icon(
          painter = painterResource(id = iconResId),
          contentDescription = null,
          modifier = Modifier.fillMaxSize(),
          tint = textColor
        )
      }
      // Icon + text
      iconResId != null && !text.isNullOrEmpty() -> {
        Row {
          Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = textColor
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(text = text, fontSize = 14.sp)
        }
      }
      // Text only
      !text.isNullOrEmpty() -> {
        Text(text = text, fontSize = 14.sp)
      }
    }
  }
}
