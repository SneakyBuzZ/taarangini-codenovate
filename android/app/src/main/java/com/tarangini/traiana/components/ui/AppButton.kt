package com.tarangini.traiana.components.ui


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarangini.traiana.components.theme.Colors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import com.tarangini.traiana.components.theme.Dimens

enum class ButtonVariant {
  Default, Primary, Secondary, Outline, Bright, Destructive
}

@Composable
fun AppButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  variant: ButtonVariant = ButtonVariant.Default,
  enabled: Boolean = true
) {
  val (bgColor, borderColor, textColor) = when (variant) {
    ButtonVariant.Primary -> Triple(Colors.CoralBlue100, Colors.CoralBlue200, Colors.Neutral100)
    ButtonVariant.Secondary -> Triple(Colors.CoralGreen100, Colors.CoralGreen200, Colors.Neutral100)
    ButtonVariant.Default -> Triple(Colors.Neutral800, Colors.Neutral700, Colors.Neutral100)
    ButtonVariant.Destructive -> Triple(Colors.CoralRed100, Colors.CoralRed200, Colors.Neutral100)
    ButtonVariant.Outline -> Triple(Color.Transparent, Colors.Neutral700, Colors.Neutral100)
    ButtonVariant.Bright -> Triple(Colors.Neutral100, Colors.Neutral200, Colors.Neutral900)
  }

  Button(
    onClick = onClick,
    modifier = modifier
      .height(Dimens.HeightXS)
      .fillMaxWidth(),
    enabled = enabled,
    shape = MaterialTheme.shapes.medium,
    colors = ButtonDefaults.buttonColors(
      containerColor = bgColor,
      disabledContainerColor = bgColor.copy(alpha = 0.5f),
      contentColor = textColor,
      disabledContentColor = textColor.copy(alpha = 0.5f),
    ),
    border = androidx.compose.foundation.BorderStroke(1.dp, borderColor)
  ) {
    Text(text = text, fontSize = 14.sp)
  }
}

